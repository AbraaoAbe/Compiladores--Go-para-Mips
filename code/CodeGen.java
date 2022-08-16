package code;

import static code.Instruction.INSTR_MEM_SIZE;
// import static code.OpCode.ADDf;
import static code.OpCode.ADDi;
import static code.OpCode.B2Ss;
import static code.OpCode.BOFb;
import static code.OpCode.CALL;
import static code.OpCode.CATs;
// import static code.OpCode.DIVf;
// import static code.OpCode.DIVi;
import static code.OpCode.EQUf;
import static code.OpCode.EQUi;
import static code.OpCode.EQUs;
import static code.OpCode.HALT;
import static code.OpCode.I2Ss;
import static code.OpCode.JUMP;
import static code.OpCode.LDIf;
import static code.OpCode.LDIi;
import static code.OpCode.LDWf;
import static code.OpCode.LDWi;
import static code.OpCode.LTHf;
import static code.OpCode.LTHi;
import static code.OpCode.LTHs;
// import static code.OpCode.MULf;
// import static code.OpCode.MULi;
import static code.OpCode.OROR;
import static code.OpCode.R2Ss;
import static code.OpCode.STWf;
import static code.OpCode.STWi;
// import static code.OpCode.SUBf;
// import static code.OpCode.SUBi;
import static code.OpCode.WIDf;
import static typing.Type.BOOL_TYPE;
import static typing.Type.INT_TYPE;
import static typing.Type.FLOAT_TYPE;

import ast.AST;
import ast.ASTBaseVisitor;
import tables.StrTable;
import tables.VarTable;
import typing.Type;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/*
 * Visitador da AST para geração básica de código. Funciona de
 * forma muito similar ao interpretador do laboratório anterior,
 * mas agora estamos trabalhando com um ambiente de execução 
 * com código de 3 endereços. Isto quer dizer que não existe mais
 * pilha e todas as operações são realizadas via registradores.
 * 
 * Note que não há uma área de memória de dados no código abaixo.
 * Esta área fica agora na TM, que a "arquitetura" de execução.
 */
public final class CodeGen extends ASTBaseVisitor<Integer> {

	private final Instruction code[]; // Code memory
	private final StrTable st;
	private final VarTable vt;
	private PrintWriter pw;
	private static Map<String, Integer> primitiveSizes;
	static {
		primitiveSizes = new HashMap<>();
		primitiveSizes.put("int", 4);
		primitiveSizes.put("float", 4);
		primitiveSizes.put("char", 1);
		primitiveSizes.put("bool", 1);
	}
	
	// Contadores para geração de código.
	// Próxima posição na memória de código para emit.
	private static int nextInstr;
	// Número de registradores temporários já utilizados.
	// Usamos um valor arbitrário, mas depois seria necessário
	// fazer o processo de alocação de registradores. Isto está
	// fora do escopo da disciplina.
	private static int intRegsCount;
	private static int intRegs_A_count;
	private static int intRegs_T_count;
	private static int intRegs_S_count;
	private static int floatRegsCount;
	
	public CodeGen(StrTable st, VarTable vt, String file_target) throws IOException {
		this.code = new Instruction[INSTR_MEM_SIZE];
		this.st = st;
		this.vt = vt;
		this.pw = new PrintWriter(new FileWriter(file_target));
	}
	
	// Função principal para geração de código.
	@Override
	public void execute(AST root) {
		nextInstr = 0;
		intRegsCount = 0;
		intRegs_A_count = 0;
	 	intRegs_T_count = 0;
		intRegs_S_count = 0;
		//utilizado como temporario +4 na impressão
		floatRegsCount = 0;
	    dumpStrTable();
		dumpVarTable();
	    visit(root);
	    dumpProgram();
		pw.close();
	}
	
	// ----------------------------------------------------------------------------
	// Prints ---------------------------------------------------------------------

	void dumpProgram() {

		pw.printf("\n.text\n");
	    for (int addr = 0; addr < nextInstr; addr++) {
	    	pw.printf("%s\n", code[addr].toString());
	    }
		//termino do programa
		pw.printf("li $2, 10\n");
		pw.printf("syscall");
	}

	void dumpStrTable() {
		pw.printf(".data\n");
		pw.printf("# Declare the statics strings\n");
	    for (int i = 0; i < st.size(); i++) {
	        pw.printf("str%d: .asciiz %s\n", i, st.get(i));
	    }
	}

	void dumpVarTable() {
		pw.printf("# Declare the global variables strings\n");
		for (int i = 0; i < vt.size(); i++) {
			Type t = vt.getType(i);
			boolean isArray = vt.getTamArr(i) > 0;
			if (isArray){
				//alocateArray(vt, i);
			} else if (t == INT_TYPE) {
				alocateInt(vt, i);
			} 
			//else if (t == FLOAT_TYPE) {
			// 	alocateFloat(vt, i);
			// } else if (t == BOOL_TYPE) {
			// 	alocateBool(vt, i);
			// }
		}
	}
	
	// ----------------------------------------------------------------------------
	// Emits ----------------------------------------------------------------------
	
	private void emit(OpCode op, int o1, int o2, int o3) {
		Instruction instr = new Instruction(op, o1, o2, o3);
		// Em um código para o produção deveria haver uma verificação aqui...
	    code[nextInstr] = instr;
	    nextInstr++;
	}
	
	private void emit(OpCode op) {
		emit(op, 0, 0, 0);
	}
	
	private void emit(OpCode op, int o1) {
		emit(op, o1, 0, 0);
	}
	
	private void emit(OpCode op, int o1, int o2) {
		emit(op, o1, o2, 0);
	}

	private void backpatchJump(int instrAddr, int jumpAddr) {
	    code[instrAddr].o1 = jumpAddr;
	}

	private void backpatchBranch(int instrAddr, int offset) {
	    code[instrAddr].o2 = offset;
	}
	
	// ----------------------------------------------------------------------------
	// AST Traversal --------------------------------------------------------------
	
	private int newIntReg() {
		return intRegsCount++; 
	}

	private int newIntReg_T() {
		return 8 + (intRegs_T_count++); 
	}
    
	private int newFloatReg() {
		return floatRegsCount++;
	}
	
	// Funcionamento dos visitadores abaixo deve ser razoavelmente explicativo
	// neste final do curso...
	
    // assign_stmt: ID ASSIGN expr SEMI
	@Override
	protected Integer visitAssign(AST node) {
		AST varuse = node.getChild(0);
		visit(varuse);
		AST r = node.getChild(1);
	    int x = visit(r);
		//System.out.print(x);
	    int addr = node.getChild(0).intData;
	    Type varType = vt.getType(addr);
	    if (varType == FLOAT_TYPE) {
	        emit(STWf, addr, x);
	    } else { // All other types, include ints, bools and strs.
			//System.out.print("( "+addr+ " )");
	        emit(OpCode.SW, addr, x);
	    }
	    return -1; // This is not an expression, hence no value to return.
	}

	// @Override
	// protected Integer visitEq(AST node) {
	// 	AST l = node.getChild(0);
	// 	AST r = node.getChild(1);
	// 	int y = visit(l);
	// 	int z = visit(r);
	// 	int x = newIntReg();
	// 	if (r.type == FLOAT_TYPE) {  // Could equally test 'l' here.
	// 		emit(EQUf, x, y, z);
	// 	} else if (r.type == INT_TYPE) {
	// 		emit(EQUi, x, y, z);
	// 	} else { // Must be STR_TYPE
	//         emit(EQUs, x, y, z);
	//     }
	//     return x;
	// }

	// @Override
	// protected Integer visitBlock(AST node) {
	//     for (int i = 0; i < node.getChildCount(); i++) {
	//         visit(node.getChild(i));
	//     }
	//     return -1; // This is not an expression, hence no value to return.
	// }

	// @Override
	// protected Integer visitBoolVal(AST node) {
	// 	int x = newIntReg();
	//     int c = node.intData;
	//     emit(LDIi, x, c);
	//     return x;
	// }

	// @Override
	// protected Integer visitIf(AST node) {
	// 	// Code for test.
	//     int testReg = visit(node.getChild(0));
	//     int condJumpInstr = nextInstr;
	//     emit(BOFb, testReg, 0); // Leave offset empty now, will be backpatched.

	//     // Code for TRUE block.
	//     int trueBranchStart = nextInstr;
	//     visit(node.getChild(1)); // Generate TRUE block.

	//     // Code for FALSE block.
	//     int falseBranchStart;
	//     if (node.getChildCount() == 3) { // We have an else.
	//         // Emit unconditional jump for TRUE block.
	//         int uncondJumpInstr = nextInstr;
	//         emit(JUMP, 0); // Leave address empty now, will be backpatched.
	//         falseBranchStart = nextInstr;
	//         visit(node.getChild(2)); // Generate FALSE block.
	//         // Backpatch unconditional jump at end of TRUE block.
	//         backpatchJump(uncondJumpInstr, nextInstr);
	//     } else {
	//     	falseBranchStart = nextInstr;
	//     }

	//     // Backpatch test.
	//     backpatchBranch(condJumpInstr, falseBranchStart - trueBranchStart + 1);

	//     return -1; // This is not an expression, hence no value to return.
	// }

	@Override
	protected Integer visitIntVal(AST node) {
		int x = newIntReg();
	    int c = node.intData;
	    emit(OpCode.LI, x, c);
	    return x;
	}

	// // @Override
	// // protected Integer visitLt(AST node) {
	// // 	AST l = node.getChild(0);
	// // 	AST r = node.getChild(1);
	// // 	int y = visit(l);
	// // 	int z = visit(r);
	// // 	int x = newIntReg();
	// // 	if (r.type == FLOAT_TYPE) {  // Could equally test 'l' here.
	// // 		emit(LTHf, x, y, z);
	// // 	} else if (r.type == INT_TYPE) {
	// // 		emit(LTHi, x, y, z);
	// // 	} else { // Must be STR_TYPE
	// //         emit(LTHs, x, y, z);
	// //     }
	// //     return x;
	// // }

	// @Override
	// protected Integer visitMinus(AST node) {
	// 	int x;
	//     int y = visit(node.getChild(0));
	//     int z = visit(node.getChild(1));
	//     if (node.type == FLOAT_TYPE) {
	//         x = newFloatReg();
	//         emit(SUBf, x, y, z);
	//     } else {
	//         x = newIntReg();
	//         emit(SUBi, x, y, z);
	//     }
	//     return x;
	// }

	// @Override
	// protected Integer visitDiv(AST node) {
	// 	int x;
	//     int y = visit(node.getChild(0));
	//     int z = visit(node.getChild(1));
	//     if (node.type == FLOAT_TYPE) {
	//         x = newFloatReg();
	//         emit(DIVf, x, y, z);
	//     } else {
	//         x = newIntReg();
	//         emit(DIVi, x, y, z);
	//     }
	//     return x;
	// }

	@Override
	protected Integer visitPlus(AST node) {
		int x;
	    int y = visit(node.getChild(0));
	    int z = visit(node.getChild(1));
	    if (node.type == FLOAT_TYPE) {
	        x = newFloatReg();
	        emit(OpCode.ADD, x, y, z);
	    } else if (node.type == INT_TYPE) {
	    	x = newIntReg();
	        emit(OpCode.ADD, x, y, z);
	    } else if (node.type == BOOL_TYPE) {
	    	x = newIntReg();
	        emit(OROR, x, y, z);
	    } else { // Must be STR_TYPE
	    	x = newIntReg();
	        emit(CATs, x, y, z);
	    }
	    return x;
	}

	@Override
	protected Integer visitProgram(AST node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            visit(node.getChild(i));    
        }
		//visit(node.getChild(0)); // var_list
		//visit(node.getChild(1)); // block
	    return -1;  // This is not an expression, hence no value to return.
	}

	// @Override
	// protected Integer visitScan(AST node) {
	// 	AST var = node.getChild(0);
	//     int addr = var.intData;
	//     int x;
	//     if (var.type == INT_TYPE) {
	//     	x = newIntReg();
	//         emit(CALL, 0, x);
	//         emit(STWi, addr, x);
	//     } else if (var.type == FLOAT_TYPE) {
	//         x = newFloatReg();
	//         emit(CALL, 1, x);
	//         emit(STWf, addr, x);
	//     } else if (var.type == BOOL_TYPE) {
	//     	x = newIntReg();
	//         emit(CALL, 2, x);
	//         emit(STWi, addr, x);
	//     } else { // Must be STR_TYPE
	//     	x = newIntReg();
	//         emit(CALL, 3, x);
	//         emit(STWi, addr, x);
	//     }
	//     return -1;  // This is not an expression, hence no value to return.
	// }

	// @Override
	// protected Integer visitRealVal(AST node) {
	// 	int x = newFloatReg();
	//     // We need to read as an int because the TM cannot handle floats directly.
	//     // But we have a float stored in the AST, so we just convert it as an int
	//     // and magically we have a float encoded as an int... :P
	//     int c = Float.floatToIntBits(node.floatData);
	//     emit(LDIf, x, c);
	//     return x;
	// }

	// // @Override
	// // protected Integer visitRepeat(AST node) {
	// // 	int beginRepeat = nextInstr;
	// //     visit(node.getChild(0)); // Emit code for body.
	// //     int testReg = visit(node.getChild(1)); // Emit code for test.
	// //     emit(BOFb, testReg, beginRepeat - nextInstr);
	// //     return -1;  // This is not an expression, hence no value to return.
	// // }

	// @Override
	// protected Integer visitStrVal(AST node) {
	// 	int x = newIntReg();
	//     int c = node.intData;
	//     emit(LDIi, x, c);
	//     return x;
	// }

	// @Override
	// protected Integer visitTimes(AST node) {
	// 	int x;
	//     int y = visit(node.getChild(0));
	//     int z = visit(node.getChild(1));
	//     if (node.type == FLOAT_TYPE) {
	//         x = newFloatReg();
	//         emit(MULf, x, y, z);
	//     } else {
	//         x = newIntReg();
	//         emit(MULi, x, y, z);
	//     }
	//     return x;
	// }

	@Override
	protected Integer visitVarDecl(AST node) {
		// Nothing to do here.
	    return -1;  // This is not an expression, hence no value to return.
	}

	@Override
	protected Integer visitVarList(AST node) {
		// Nothing to do here.
	    return -1;  // This is not an expression, hence no value to return.
	}

	@Override
	protected Integer visitVarUse(AST node) {
		int addr = node.intData;
	    int x;
	    if (node.type == FLOAT_TYPE) {
	        x = newFloatReg();
	        emit(LDWf, x, addr);
	    } else {
	        //x = newIntReg();
			x = newIntReg_T();
	        emit(OpCode.LDW, x, addr);
	    }
	    return x;
	}

	// @Override
	// protected Integer visitPrint(AST node) {
	// 	AST expr = node.getChild(0);
	//     int x = visit(expr);
	//     switch(expr.type) {
	//         case INT_TYPE:  emit(CALL, 4, x);  break;
	//         case FLOAT_TYPE: emit(CALL, 5, x);  break;
	//         case BOOL_TYPE: emit(CALL, 6, x);  break;
	//         case STRING_TYPE:  emit(CALL, 7, x);  break;
	//         case NO_TYPE:
	//         default:
	//             System.err.printf("Invalid type: %s!\n", expr.type.toString());
	//             System.exit(1);
	//     }
	//     return -1;  // This is not an expression, hence no value to return.
	// }

	// @Override
	// protected Integer visitB2I(AST node) {
	// 	int x = visit(node.getChild(0));
	//     // Nothing else to do, a bool already is stored as an int.
	//     return x;
	// }

	// @Override
	// protected Integer visitB2R(AST node) {
	// 	int i = visit(node.getChild(0));
	//     int r = newFloatReg();
	//     emit(WIDf, r, i);
	//     return r;
	// }

	// @Override
	// protected Integer visitB2S(AST node) {
	// 	int x = newIntReg();
	//     int y = visit(node.getChild(0));
	//     emit(B2Ss, x, y);
	//     return x;
	// }

	// @Override
	// protected Integer visitI2R(AST node) {
	// 	int i = visit(node.getChild(0));
	//     int r = newFloatReg();
	//     emit(WIDf, r, i);
	//     return r;
	// }

	// @Override
	// protected Integer visitI2S(AST node) {
	// 	int x = newIntReg();
	//     int y = visit(node.getChild(0));
	//     emit(I2Ss, x, y);
	//     return x;
	// }

	// @Override
	// protected Integer visitR2S(AST node) {
	// 	int x = newIntReg();
	//     int y = visit(node.getChild(0));
	//     emit(R2Ss, x, y);
	//     return x;
	// }

    // @Override
	// protected Integer visitFunc_Decl(AST node) {
	// 	visit(node.getChild(0)); // var_list
	// 	visit(node.getChild(1)); // block
	//     return -1;  // This is not an expression, hence no value to return.
	// }
//	private void alocateArray(VarTable table, int i){
//		int space_alloc = table.getTamArr(i) *
//				primitiveSizes.get(table.getType(i).toString());
//		pw.printf("array%d:	.space %d", i, space_alloc);
//	}
//
	private void alocateInt(VarTable table, int i){
		pw.printf("%s:	.word 0", table.getName(i));
	}
//
//	private void alocateBool(VarTable table, int i){
//		int space_alloc = primitiveSizes.get(table.getType(i).toString());
//		pw.printf("varBool%d:	.space %d", i, space_alloc);
//	}
//
//	private void alocateFloat(VarTable table, int i) {
//		int space_alloc = primitiveSizes.get(table.getType(i).toString());
//		pw.printf("varFloat%d:	.float", i);
//	}

//	private void alocateString(VarTable table, int i){
//		int space_alloc = table.getTamArr(i) *
//				primitiveSizes.get(table.getType(i).toString());
//		pw.printf("array%d:	.space %d", i, space_alloc);
//	}
}
