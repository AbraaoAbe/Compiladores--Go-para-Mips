package ast;

import static typing.Type.NO_TYPE;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import tables.FuncTable;
import tables.VarTable;
import typing.Type;

// Implementação dos nós da AST.
public class AST {

	// Todos os campos são finais para simplificar, assim não precisa de getter/setter.
	// Note que não há union em Java, então aquele truque de ler
	// e/ou escrever o campo com formatos diferentes não funciona aqui.
	// Os campos 'data' NÃO ficam sincronizados!
	public  NodeKind kind;
	public  final int intData;
	public  final float floatData;

	public int sizeData;

	public  Type type;
	public  final List<AST> children; // Privado para que a manipulação da lista seja controlável.

	// Construtor completo para poder tornar todos os campos finais.
	// Privado porque não queremos os dois campos 'data' preenchidos ao mesmo tempo.
	private AST(NodeKind kind, int intData, float floatData, int sizeData, Type type) {
		this.kind = kind;
		this.intData = intData;
		this.floatData = floatData;
		this.sizeData = sizeData;
		this.type = type;
		this.children = new ArrayList<AST>();
	}

	// Cria o nó com um dado inteiro.
	public AST(NodeKind kind, int intData, Type type) {
		this(kind, intData, 0.0f, -1, type);
	}

	// Cria o nó com um dado float.
	public AST(NodeKind kind, float floatData, Type type) {
		this(kind, 0, floatData, -1, type);
	}

	// Adiciona um novo filho ao nó.
	public void addChild(AST child) {
		// A lista cresce automaticamente, então nunca vai dar erro ao adicionar.
		this.children.add(child);
	}

	public int getIntData() {
		return intData;
	}

	public Type getType(){return this.type;}

	public void setType(Type type) {
		this.type = type;
	}

	public NodeKind getKind(){return this.kind;}
	public void setKind(NodeKind kind){
		this.kind = kind;
	}

	// Retorna o filho no índice passado.
	// Não há nenhuma verificação de erros!
	public AST getChild(int idx) {
		// Claro que um código em produção precisa testar o índice antes para
		// evitar uma exceção.
	    return this.children.get(idx);
	}
	public List<AST> getListChild(){
		return this.children;
	}

	//Retorna o tamanho do filho
	public int getSizeChild(){
		return this.children.size();
	}

	// Retorna o número de filhos do nó.
	public int getChildCount() {
		return this.children.size();
	}
	

	// Cria um nó e pendura todos os filhos passados como argumento.
	public static AST newSubtree(NodeKind kind, Type type, AST... children) {
		AST node = new AST(kind, 0, type);
	    for (AST child: children) {
	    	node.addChild(child);
	    }
	    return node;
	}

	// Variáveis internas usadas para geração da saída em DOT.
	// Estáticas porque só precisamos de uma instância.
	private static int nr;
	private static VarTable vt;

	private static FuncTable ft;

	// Imprime recursivamente a codificação em DOT da subárvore começando no nó atual.
	// Usa stderr como saída para facilitar o redirecionamento, mas isso é só um hack.
	private int printNodeDot() {
		int myNr = nr++;

	    System.err.printf("node%d[label=\"", myNr);
	    if (this.type != NO_TYPE) {
	    	System.err.printf("(%s) ", this.type.toString());
	    }
	    if (this.kind == NodeKind.VAR_DECL_NODE || this.kind == NodeKind.VAR_USE_NODE) {
	    	System.err.printf("%s@", vt.getName(this.intData));
	    } else if (this.kind == NodeKind.PARAMS_NODE){
			System.err.printf("%s@", vt.getName(this.intData));
		} else {
	    	System.err.printf("%s", this.kind.toString());
	    }
	    if (NodeKind.hasData(this.kind)) {
	        if (this.kind == NodeKind.REAL_VAL_NODE) {
	        	System.err.printf("%.2f", this.floatData);
	        } else if (this.kind == NodeKind.STR_VAL_NODE) {
	        	System.err.printf("@%d", this.intData);
	        } else if (this.kind == NodeKind.ARRAY_NODE) {
				System.err.printf("@[%d]", this.intData);
			}else {
	        	System.err.printf("%d", this.intData);
	        }
	    }
	    System.err.printf("\"];\n");

	    for (int i = 0; i < this.children.size(); i++) {
			//se tirar do if da null pointer exception
			//if (this.children.get(i) != null){
				int childNr = this.children.get(i).printNodeDot();
	        	System.err.printf("node%d -> node%d;\n", myNr, childNr);
			//}
	        
	    }
	    return myNr;
	}

	// Imprime a árvore toda em stderr.
	public static void printDot(AST tree, VarTable table) {
	    nr = 0;
	    vt = table;
	    System.err.printf("digraph {\ngraph [ordering=\"out\"];\n");
	    tree.printNodeDot();
	    System.err.printf("}\n");
	}
	private int printNodeFileDot(PrintWriter pw, int func_index) {
		int myNr = nr++;
		pw.printf("node%d[label=\"", myNr);
		if (this.type != NO_TYPE) {
			pw.printf("(%s) ", this.type.toString());
		}
		if (this.kind == NodeKind.FUNC_DECL_NODE || this.kind == NodeKind.FUNC_USE_NODE){
			func_index = this.intData;
			pw.printf("%s/%s@", this.kind.toString(), ft.getName(func_index));
		} else if (this.kind == NodeKind.PARAMS_NODE){
			pw.printf("%s@", ft.getVarTable(func_index).getName(this.intData));
		} else if (this.kind == NodeKind.VAR_DECL_NODE || this.kind == NodeKind.VAR_USE_NODE) {
			VarTable tb;
			if (func_index == -1){ tb = vt; } else { tb = ft.getVarTable(func_index); }
			if (this.sizeData >= 0){
				pw.printf("%s[%d]@", tb.getName(this.intData), this.sizeData);
			} else {
				pw.printf("%s@",  tb.getName(this.intData));
			}
		} else {
			pw.printf("%s", this.kind.toString());
		}
		if (NodeKind.hasData(this.kind)) {
			if (this.kind == NodeKind.REAL_VAL_NODE) {
				pw.printf("%.2f", this.floatData);
			} else if (this.kind == NodeKind.STR_VAL_NODE) {
				pw.printf("@%d", this.intData);
			} else if (this.kind == NodeKind.BOOL_VAL_NODE){
				pw.printf("%b", this.intData);
			} else {
				pw.printf("%d", this.intData);
			}
		}
		if (this.kind == NodeKind.FOR_NODE){
			pw.printf("\", fillcolor=\"#74e882\", style=filled];\n");
		} else {
			pw.printf("\"];\n");
		}
		for (int i = 0; i < this.children.size(); i++) {
			//se tirar do if da null pointer exception
			if (this.children.get(i) != null) {
				int childNr = this.children.get(i).printNodeFileDot(pw, func_index);
				pw.printf("node%d -> node%d;\n", myNr, childNr);
			}

		}
		return myNr;
	}

	public static void printFileDot(AST tree, VarTable table, FuncTable funcT) {
		nr = 0;
		vt = table;
		ft = funcT;
		try {
			FileWriter fileWriter = new FileWriter("./tree.dot");
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.printf("digraph {\ngraph [ordering=\"out\"];\n");
			tree.printNodeFileDot(printWriter, -1);
			printWriter.printf("}\n");
			printWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
