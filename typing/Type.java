package typing;


import typing.Conv.Unif;

import static typing.Conv.*;


// Create enum for primitive types
public enum Type {

    INT_TYPE {
        public String toString() {
            return "int";
        }
    },
    FLOAT_TYPE {
        public String toString(){
            return "float";
        }
    },
    STRING_TYPE {
        public String toString(){
            return "string";
        }
    },
    BOOL_TYPE {
        public String toString(){
            return "bool";
        }
    },
    ARRAY_TYPE {
		public String toString() {
	    		return "array";
		}
	},
    NO_TYPE { // Indica um erro de tipos.
        public String toString() {
            return "no_type";
        }
    };
    private static Unif plus[][] ={
            { new Unif(INT_TYPE, NONE, NONE), new Unif(FLOAT_TYPE, I2R, NONE),  new Unif(INT_TYPE, NONE, B2I),   new Unif(STRING_TYPE, I2S, NONE)  },
            { new Unif(FLOAT_TYPE, NONE, I2R), new Unif(FLOAT_TYPE, NONE, NONE), new Unif(FLOAT_TYPE, NONE, B2R),  new Unif(STRING_TYPE, R2S, NONE)  },
            { new Unif(INT_TYPE, B2I, NONE),  new Unif(FLOAT_TYPE, B2R, NONE),  new Unif(BOOL_TYPE, NONE, NONE), new Unif(STRING_TYPE, B2S, NONE)  },
            { new Unif(STRING_TYPE, NONE, I2S),  new Unif(STRING_TYPE, NONE, R2S),   new Unif(STRING_TYPE, NONE, B2S),   new Unif(STRING_TYPE, NONE, NONE) }
    };

    public Unif unifyPlus(Type that) {
        return plus[this.ordinal()][that.ordinal()];
    }

    // Tabela de unificação de tipos primitivos para os demais operadores aritméticos.
    private static Unif other[][] = {
            { new Unif(INT_TYPE, NONE, NONE), new Unif(FLOAT_TYPE, I2R, NONE),  new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE) },
            { new Unif(FLOAT_TYPE, NONE, I2R), new Unif(FLOAT_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE) },
            { new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE) },
            { new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE) }
    };

    public Unif unifyOtherArith(Type that) {
        return other[this.ordinal()][that.ordinal()];
    }

    // Tabela de unificação de tipos primitivos para os operadores de comparação.
    private static Unif comp[][] = {
            { new Unif(BOOL_TYPE, NONE, NONE), new Unif(BOOL_TYPE, I2R, NONE),  new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE)   },
            { new Unif(BOOL_TYPE, NONE, I2R),  new Unif(BOOL_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE)   },
            { new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE)   },
            { new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE), new Unif(BOOL_TYPE, NONE, NONE) }
    };

    public Unif unifyComp(Type that) {
        return comp[this.ordinal()][that.ordinal()];
    }

    private static Unif index[][] = {
            { new Unif(INT_TYPE, NONE, NONE), new Unif(INT_TYPE, NONE, R2I),  new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE) }, // Int
            { new Unif(INT_TYPE, R2I, NONE), new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE) },  // Float
            { new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE) }, // String
            { new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE) } // Bool
    };

    public Unif unifyIndexArith(Type that) {
        return index[this.ordinal()][that.ordinal()];
    }
}
