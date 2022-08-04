package tables;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import typing.Type;

public final class VarTable {

	// No mundo real isto certamente deveria ser um hash...
	// Implementação da classe não é exatamente Javanesca porque
	// tentei deixar o mais parecido possível com a original em C.
	private List<Entry> table = new ArrayList<Entry>(); 

	public int lookupVar(String s) {
		for (int i = 0; i < table.size(); i++) {
			if (table.get(i).name.equals(s)) {
				return i;
			}
		}
		return -1;
	}

	public int addVar(String s, int line, Type type, int tam_array) {
		Entry entry = new Entry(s, line, type, tam_array);
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
	
	public Type getType(int i) {
		return table.get(i).type;
	}

	public int getTamArr(int i) {
		return table.get(i).tam_array;
	}

	public void setTamArr(int i, int tam) {
		table.get(i).tam_array = tam;
	}
	public void setType(int idx, Type t){
		table.get(idx).type = t;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("Variables table:\n");
		for (int i = 0; i < table.size(); i++) {
			if (getTamArr(i) == -1) {
				f.format("Var %d -- name: %s, line: %d, type: %s\n", i,
						getName(i), getLine(i), getType(i).toString());
			} else{
				f.format("Var %d -- name: %s, line: %d, type: %s, tam_array: %s\n", i,
						getName(i), getLine(i), getType(i).toString(), getTamArr(i));
			}
		}
		f.close();
		return sb.toString();
	}
	
	private static final class Entry {
		private final String name;
		private final int line;
		private int tam_array;
		private Type type;
		
		Entry(String name, int line, Type type, int tam_array) {
			this.name = name;
			this.line = line;
			this.type = type;
			this.tam_array = tam_array;
		}
	}
}
