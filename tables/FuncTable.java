package tables;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import typing.Type;

public class FuncTable {
    // No mundo real isto certamente deveria ser um hash...
	// Implementação da classe não é exatamente Javanesca porque
	// tentei deixar o mais parecido possível com a original em C.
	private List<Entry> table = new ArrayList<Entry>(); 

	public int lookupFunc(String s) {
		for (int i = 0; i < table.size(); i++) {
			if (table.get(i).name.equals(s)) {
				return i;
			}
		}
		return -1;
	}
	
//	public int addFunc(String s, int line, Type type) {
//		Entry entry = new Entry(s, line, type);
//		int idxAdded = table.size();
//		table.add(entry);
//		return idxAdded;
//	}

	public int addFunc(String s, int line, List<Type> param, Type retorno, VarTable vt) {
		Entry entry = new Entry(s, line, param, retorno, vt);
		int idxAdded = table.size();
		table.add(entry);
		return idxAdded;
	}
	
	public String getName(int i) {
		return table.get(i).name;
	}
	
	public int getLine(int i) {
		return table.get(i).line;
	}
	
	public Type getRetorno(int i) {
		return table.get(i).retorno;
	}

	public void setRetorno(int idx, Type t){
		table.get(idx).retorno = t;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("Function table:\n");
		for (int i = 0; i < table.size(); i++) {
			f.format("Entry %d -- name: %s, line: %d, retorno: %s\n\t%s\n", i,
	                 getName(i), getLine(i), getRetorno(i).toString(), getVarTable(i).toString());
		}
		f.close();
		return sb.toString();
	}

	public List<Entry> getTable() {
		return table;
	}

	public List<Type> getTypes(int i) {
		return table.get(i).param;
	}

	public String getTypesString(int i) {
		String prms = "";
		for(Type t: table.get(i).param) {
			prms += t.toString() + " ";
		}
		return prms;

	}

	public VarTable getVarTable(int i) {
		return table.get(i).vt;
	}


	private final class Entry {
		String name;
		int line;
		List<Type> param = new ArrayList<Type>();
		Type retorno;
		VarTable vt = new VarTable();

		Entry(String name, int line, List<Type> param, Type retorno, VarTable vt) {
			this.name = name;
			this.line = line;
			this.param = param;
			this.retorno = retorno;
			this.vt = vt;
		}

	}
}
