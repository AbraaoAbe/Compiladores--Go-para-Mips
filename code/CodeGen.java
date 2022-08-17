package code;

import static code.Instruction.INSTR_MEM_SIZE;
// import static code.OpCode.ADDf;
// import static code.OpCode.DIVf;
// import static code.OpCode.DIVi;
// import static code.OpCode.MULf;
// import static code.OpCode.MULi;
// import static code.OpCode.SUBf;
// import static code.OpCode.SUBi;
import static code.OpCode.*;
import static typing.Type.*;

import ast.AST;
import ast.ASTBaseVisitor;
import tables.FuncTable;
import tables.StrTable;
import tables.VarTable;
import typing.Type;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
	private VarTable vt;
    private final VarTable globalVt;
	private final FuncTable ft;
	private String func_name; // Store the name of the function seen right now

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
	//Guarda registros "importantes" o i de um for ou o lado esquerdo do assign
	private static ArrayList<Boolean> freeRegs = new ArrayList<>();

	public static int intLabelsIfElse;
	
	public CodeGen(StrTable st, VarTable vt, FuncTable ft, String file_target) throws IOException {
		this.code = new Instruction[INSTR_MEM_SIZE];
		this.st = st;
		this.globalVt = vt;
		this.vt = vt;
		this.ft = ft;
		this.func_name = "global";
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
		intLabelsIfElse = 0;


		for (int i = 0; i < 17; i++){
			freeRegs.add(Boolean.TRUE);
		}
		System.out.println(freeRegs.size());
		//utilizado como temporario +4 na impressão
		floatRegsCount = 0;

		pw.printf(".data\n");
	    dumpStrTable();

		visit(root);
		dumpVarTable(globalVt, "global");
		for (int i =0; i < ft.getTable().size(); i++){
			dumpVarTable(ft.getVarTable(i), ft.getName(i));
			pw.printf("\n");
		}
	    dumpProgram();
		pw.close();
	}
	
	// ----------------------------------------------------------------------------
	// Prints ---------------------------------------------------------------------

	void dumpProgram() {

		pw.printf("\n.text\n");
		pw.printf(".globl main\n");
	    for (int addr = 0; addr < nextInstr; addr++) {
	    	pw.printf("%s\n", code[addr].toString());
	    }
		//termino do programa
		pw.printf("li $2, 10\n");
		pw.printf("syscall");
	}

	void dumpStrTable() {
		pw.printf("# Declare the statics strings\n");
	    for (int i = 0; i < st.size(); i++) {
	        pw.printf("str%d: .asciiz %s\n", i, st.get(i));
	    }
	}

	void dumpVarTable(VarTable varT, String name_func) {
		pw.printf("# Declare the %s variables\n", name_func);
		for (int i = 0; i < varT.size(); i++) {
			Type t = varT.getType(i);
			boolean isArray = varT.getTamArr(i) > 0;
			if (!Objects.equals(name_func, "global")) {
				pw.printf("%s.", name_func);
			}
			if (isArray){
				//alocateArray(vt, i);
			} else if (t == INT_TYPE) {
				alocateInt(varT, i);
//			} else if (t == FLOAT_TYPE) {
			// 	alocateFloat(vt, i);
			 } else if (t == BOOL_TYPE) {
			 	alocateBool(vt, i);
//			 } else if (t == STRING_TYPE){
//				alocateString(vt, i);
			}
		}
	}
	
	// ----------------------------------------------------------------------------
	// Emits ----------------------------------------------------------------------
	
	private void emit(OpCode op, String o1, String o2, String o3) {
		Instruction instr = new Instruction(op, o1, o2, o3);
		// Em um código para o produção deveria haver uma verificação aqui...
	    code[nextInstr] = instr;
	    nextInstr++;
	}
	
	private void emit(OpCode op) {
		emit(op, "", "", "");
	}
	
	private void emit(OpCode op, String o1) {
		emit(op, o1, "", "");
	}
	
	private void emit(OpCode op, String o1, String o2) {
		emit(op, o1, o2, "");
	}

	private void backpatchJump(int instrAddr, int jumpAddr) {
	    code[instrAddr].o1 = String.valueOf(jumpAddr);
	}

	private void backpatchBranch(int instrAddr, int offset) {
	    code[instrAddr].o2 = String.valueOf(offset);
	}
	
	// ----------------------------------------------------------------------------
	// AST Traversal --------------------------------------------------------------
	
	private String newIntReg() {
		String s = String.valueOf(intRegsCount);
		intRegsCount++;
		return s; 
	}

	private String newIntReg_T() {
		String s = String.valueOf(intRegs_T_count + 8);
		if (getFreeReg(intRegs_T_count) == Boolean.TRUE){
			intRegs_T_count++;
			//Ultimo registrador temporario eh o 25
			if (intRegs_T_count + 8 == 25){
				intRegs_T_count = 0;
			}
		}
		else{
			intRegs_T_count++;
			if (intRegs_T_count + 8 == 25){
				intRegs_T_count = 0;
			}
			s = newIntReg_T();
		}

		return s;  
	}
    
	private String newFloatReg() {
		String s = String.valueOf(floatRegsCount);
		floatRegsCount++;
		return s;
	}

	private Boolean getFreeReg(int idx) {
		if(idx >= 17){
			System.out.println(idx);
		}
		else {
			return freeRegs.get(idx);
		}
		return Boolean.FALSE;
	}

	private Integer getFreeRegSize() {
		return freeRegs.size();
	}

	private void setFreeReg(int idx) {
		if(idx >= 17){
			System.out.println(idx);
		}
		else{
			freeRegs.set(idx, Boolean.TRUE);
		}
	}

	private void setNotFreeReg(int idx) {
		if(idx >= 17){
			System.out.println(idx);
		}
		else {
			freeRegs.set(idx, Boolean.FALSE);
		}
	}

	private String getDataVarName(String var_name){
		if (func_name != "global") { var_name = func_name + "." + var_name; }
		return var_name;
	}



	// Funcionamento dos visitadores abaixo deve ser razoavelmente explicativo
	// neste final do curso...
	
    // assign_stmt: ID ASSIGN expr SEMI
	@Override
	protected Integer visitAssign(AST node) {
		AST varuse = node.getChild(0);
		int regVar = visit(varuse);

		//seta o registrador como importante
		setNotFreeReg(regVar - 8);
		
		AST r = node.getChild(1);
	    String regValor = String.valueOf(visit(r));
		//System.out.print(reg);

	    int addr = node.getChild(0).intData;
	    Type varType = vt.getType(addr);
	    if (varType == FLOAT_TYPE) {
	        emit(STWf, String.valueOf(addr), regValor);
	    } else { // All other types, include ints, bools and strs.
			//System.out.print("( "+addr+ " )");
			//move
			emit(OpCode.MV, "$"+String.valueOf(regVar), "$"+regValor);
	        //store

			emit(OpCode.SW, "$"+String.valueOf(regVar), getDataVarName(vt.getName(addr)));
			//libera o registrador importante
			setFreeReg(regVar - 8);
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

	 @Override
	 protected Integer visitBlock(AST node) {
	     for (int i = 0; i < node.getChildCount(); i++) {
	         visit(node.getChild(i));
	     }
	     return -1; // This is not an expression, hence no value to return.
	 }

	 @Override
	 protected Integer visitBoolVal(AST node) {
	 	String x = newIntReg();
	     int c = node.intData;
	     emit(LI, x, String.valueOf(c));
	     return Integer.valueOf(x);
	 }

	 @Override
	 protected Integer visitIf(AST node) {
		String xlabel = String.valueOf(intLabelsIfElse);
	 	// Code for test.
	     int testReg = visit(node.getChild(0));
	     //int condJumpInstr = nextInstr;
		 //caso a exprexão seja falsa pula pro else (mesmo que ele seja vazio)
	     emit(BEQ, "$"+String.valueOf(testReg),"$0", "ELSE"+xlabel); // Leave offset empty now, will be backpatched.

	     // Code for TRUE block.
	     //int trueBranchStart = nextInstr;
	     visit(node.getChild(1)); // Generate TRUE block.

		 emit(JMP, "ENDIF"+xlabel);
		 //inicio do else
		 emit(LABEL, "ELSE"+xlabel);
	     // Code for FALSE block.
	     //int falseBranchStart;
	     if (node.getChildCount() == 3) { // We have an else.
	         // Emit unconditional jump for TRUE block.
	         //int uncondJumpInstr = nextInstr;
	         //emit(JUMP, 0); // Leave address empty now, will be backpatched.
	         //falseBranchStart = nextInstr;
	         visit(node.getChild(2)); // Generate FALSE block.
	         // Backpatch unconditional jump at end of TRUE block.
	         //backpatchJump(uncondJumpInstr, nextInstr);
	     } else {
	     	//falseBranchStart = nextInstr;
	     }
		 emit(LABEL, "ENDIF"+xlabel);

	     // Backpatch test.
	     //backpatchBranch(condJumpInstr, falseBranchStart - trueBranchStart + 1);
		intLabelsIfElse++;
	    return -1; // This is not an expression, hence no value to return.
	 }

	@Override
	protected Integer visitIntVal(AST node) {
		String x = newIntReg_T();
	    int c = node.intData;
	    emit(OpCode.LI, "$"+x, String.valueOf(c));
	    return Integer.valueOf(x);
	}

	@Override
	protected Integer visitLess(AST node) {
		AST l = node.getChild(0);
		AST r = node.getChild(1);
		int y = visit(l);
		int z = visit(r);
		String x = newIntReg_T();
		if (r.type == FLOAT_TYPE) {  // Could equally test 'l' here.
			emit(SLT, "$"+x, "$"+String.valueOf(y), "$"+String.valueOf(z));
		} else if (r.type == INT_TYPE) {
			emit(SLT, "$"+x, "$"+String.valueOf(y), "$"+String.valueOf(z));
		} else { // Must be STR_TYPE
	        emit(SLT, "$"+x, "$"+String.valueOf(y), "$"+String.valueOf(z));
	    }
	    return Integer.valueOf(x);
	}

	@Override
	protected Integer visitGreater(AST node) {
		AST l = node.getChild(0);
		AST r = node.getChild(1);
		int z = visit(l);
		int y = visit(r);
		String x = newIntReg_T();
		if (r.type == FLOAT_TYPE) {  // Could equally test 'l' here.
			emit(SLT, "$"+x, "$"+String.valueOf(y), "$"+String.valueOf(z));
		} else if (r.type == INT_TYPE) {
			emit(SLT, "$"+x, "$"+String.valueOf(y), "$"+String.valueOf(z));
		} else { // Must be STR_TYPE
	        emit(SLT, "$"+x, "$"+String.valueOf(y), "$"+String.valueOf(z));
	    }
	    return Integer.valueOf(x);
	}

	@Override
	protected Integer visitLess_Equals(AST node) {
		AST l = node.getChild(0);
		AST r = node.getChild(1);
		int z = visit(l);
		int y = visit(r);
		String x = newIntReg_T();
		//valor da label para nao ficar igual (da problema no mips)
		String xlabel = String.valueOf(intLabelsIfElse);
		if (r.type == FLOAT_TYPE) {  // Could equally test 'l' here.
			emit(BLE, "$"+String.valueOf(z), "$"+String.valueOf(y), "TRUE"+xlabel);
			emit(LI, "$"+x, "0");
			emit(JMP, "FALSE"+xlabel);
			emit(LABEL, "TRUE"+xlabel);
			emit(LI, "$"+x, "1");
			emit(LABEL, "FALSE"+xlabel);
		} else if (r.type == INT_TYPE) {
			emit(BLE, "$"+String.valueOf(z), "$"+String.valueOf(y), "TRUE"+xlabel);
			emit(LI, "$"+x, "0");
			emit(JMP, "FALSE"+xlabel);
			emit(LABEL, "TRUE"+xlabel);
			emit(LI, "$"+x, "1");
			emit(LABEL, "FALSE"+xlabel);
		} else { // Must be STR_TYPE
	        emit(BLE, "$"+String.valueOf(z), "$"+String.valueOf(y), "TRUE"+xlabel);
			emit(LI, "$"+x, "0");
			emit(JMP, "FALSE"+xlabel);
			emit(LABEL, "TRUE"+xlabel);
			emit(LI, "$"+x, "1");
			emit(LABEL, "FALSE"+xlabel);
	    }
	    return Integer.valueOf(x);
	}

	@Override
	protected Integer visitGreater_Equals(AST node) {
		AST l = node.getChild(0);
		AST r = node.getChild(1);
		//somente essas 2 linhas difere essa funcao da outra de cima
		int y = visit(l);
		int z = visit(r);

		//
		String x = newIntReg_T();
		//valor da label para nao ficar igual (da problema no mips)
		String xlabel = String.valueOf(intLabelsIfElse);
		if (r.type == FLOAT_TYPE) {  // Could equally test 'l' here.
			emit(BLE, "$"+String.valueOf(z), "$"+String.valueOf(y), "TRUE"+xlabel);
			emit(LI, "$"+x, "0");
			emit(JMP, "FALSE"+xlabel);
			emit(LABEL, "TRUE"+xlabel);
			emit(LI, "$"+x, "1");
			emit(LABEL, "FALSE"+xlabel);
		} else if (r.type == INT_TYPE) {
			emit(BLE, "$"+String.valueOf(z), "$"+String.valueOf(y), "TRUE"+xlabel);
			emit(LI, "$"+x, "0");
			emit(JMP, "FALSE"+xlabel);
			emit(LABEL, "TRUE"+xlabel);
			emit(LI, "$"+x, "1");
			emit(LABEL, "FALSE"+xlabel);
		} else { // Must be STR_TYPE
	        emit(BLE, "$"+String.valueOf(z), "$"+String.valueOf(y), "TRUE"+xlabel);
			emit(LI, "$"+x, "0");
			emit(JMP, "FALSE"+xlabel);
			emit(LABEL, "TRUE"+xlabel);
			emit(LI, "$"+x, "1");
			emit(LABEL, "FALSE"+xlabel);
	    }
	    return Integer.valueOf(x);
	}

	
	@Override
	protected Integer visitAnd(AST node) {
		AST l = node.getChild(0);
		AST r = node.getChild(1);
		int y = visit(l);
		int z = visit(r);
		String x = newIntReg_T();
		if (r.type == FLOAT_TYPE) {  // Could equally test 'l' here.
			emit(AND, "$"+x, "$"+String.valueOf(y), "$"+String.valueOf(z));
		} else if (r.type == INT_TYPE) {
			emit(AND, "$"+x, "$"+String.valueOf(y), "$"+String.valueOf(z));
		} else { // Must be STR_TYPE
	        emit(AND, "$"+x, "$"+String.valueOf(y), "$"+String.valueOf(z));
	    }
	    return Integer.valueOf(x);
	}

	@Override
	protected Integer visitOr(AST node) {
		AST l = node.getChild(0);
		AST r = node.getChild(1);
		int y = visit(l);
		int z = visit(r);
		String x = newIntReg_T();
		if (r.type == FLOAT_TYPE) {  // Could equally test 'l' here.
			emit(OR, "$"+x, "$"+String.valueOf(y), "$"+String.valueOf(z));
		} else if (r.type == INT_TYPE) {
			emit(OR, "$"+x, "$"+String.valueOf(y), "$"+String.valueOf(z));
		} else { // Must be STR_TYPE
	        emit(OR, "$"+x, "$"+String.valueOf(y), "$"+String.valueOf(z));
	    }
	    return Integer.valueOf(x);
	}
	
	
	

	 @Override
	 protected Integer visitMinus(AST node) {
	 	String x;
	     String y = String.valueOf(visit(node.getChild(0)));
	     String z = String.valueOf(visit(node.getChild(1)));
	     if (node.type == FLOAT_TYPE) {
	         x = newFloatReg();
	         emit(SUBf, x, y, z);
	     } else {
	         x = newIntReg_T();
	         emit(SUB, "$"+x, "$"+y, "$"+z);
	     }
	     return Integer.valueOf(x);
	 }

	@Override
	protected Integer visitTimes(AST node) {
		String x;
		String y = String.valueOf(visit(node.getChild(0)));
		String z = String.valueOf(visit(node.getChild(1)));
		if (node.type == FLOAT_TYPE) {
			x = newFloatReg();
			emit(SUBf, "$"+x, "$"+y, "$"+z);
		} else {
			x = newIntReg_T();
			emit(MUL, "$"+x, "$"+y, "$"+z);
		}
		return Integer.valueOf(x);
	}

	 @Override
	 protected Integer visitDiv(AST node) {
	 	String x;
		 String y = String.valueOf(visit(node.getChild(0)));
	     String z = String.valueOf(visit(node.getChild(1)));
	     if (node.type == FLOAT_TYPE) {
	         x = newFloatReg();
	         emit(DIV,"$"+y, "$"+z);
	     } else {
	         x = newIntReg_T();
	         emit(DIV, "$"+y, "$"+z);
			 emit(MVFL, "$"+x);
	     }
	     return Integer.valueOf(x);
	 }

	@Override
	protected Integer visitPlus(AST node) {
		String x;
	    String y = String.valueOf(visit(node.getChild(0)));
	    String z = String.valueOf(visit(node.getChild(1)));
	    if (node.type == FLOAT_TYPE) {
	        x = newFloatReg();
	        emit(OpCode.ADD, "$"+x,"$"+ y, "$"+z);
	    } else if (node.type == INT_TYPE) {
	    	x = newIntReg_T();
			emit(OpCode.ADD, "$"+x,"$"+ y, "$"+z);
	    } else if (node.type == BOOL_TYPE) {
	    	x = newIntReg_T();
	        emit(OROR, x, y, z);
	    } else { // Must be STR_TYPE
	    	x = newIntReg_T();
	        emit(CATs, x, y, z);
	    }

	    return Integer.valueOf(x);
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
		String addr = String.valueOf(node.intData);
	    String x;
		String name = getDataVarName(vt.getName(node.intData));
		//String name = "ola";
	    if (node.type == FLOAT_TYPE) {
	        x = newFloatReg();
	        emit(LDWf, x, addr);
	    } else {
	        //x = newIntReg();
			x = newIntReg_T();
	        emit(OpCode.LDW, "$"+x, name);
	    }
	    return Integer.valueOf(x);
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

	protected Integer visitParams_List(AST node) {
		return -1;
	}

     @Override
	 protected Integer visitFunc_Decl(AST node) {
        emit(LABEL, ft.getName(node.intData));
		vt = ft.getVarTable(node.getIntData());
		func_name = ft.getName(node.getIntData());
	 	visit(node.getChild(0)); // var_list
	 	visit(node.getChild(1)); // block
		 func_name = "global";
	     return -1;  // This is not an expression, hence no value to return.
	 }
//	private void alocateArray(VarTable table, int i){
//		int space_alloc = table.getTamArr(i) *
//				primitiveSizes.get(table.getType(i).toString());
//		pw.printf("array%d:	.space %d", i, space_alloc);
//	}
//
	private void alocateInt(VarTable table, int i){
		pw.printf("%s:	.word 0\n", table.getName(i));
	}

	private void alocateBool(VarTable table, int i){
		pw.printf("%s:	.word 0\n", table.getName(i));
	}

	private void alocateFloat(VarTable table, int i) {
		int space_alloc = primitiveSizes.get(table.getType(i).toString());
		pw.printf("varFloat%d:	.float", i);
	}

	private void alocateString(VarTable table, int i){
		int space_alloc = table.getTamArr(i) *
				primitiveSizes.get(table.getType(i).toString());
		pw.printf("%s:	.space %d", table.getName(i), space_alloc);
	}
}
