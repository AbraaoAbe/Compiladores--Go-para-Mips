package ast;

// Enumeração dos tipos de nós de uma AST.
// Adaptado da versão original em C.
// Algumas pessoas podem preferir criar uma hierarquia de herança para os
// nós para deixar o código "mais OO". Particularmente eu não sou muito
// fã, acho que só complica mais as coisas. Imagine uma classe abstrata AST
// com mais de 20 classes herdando dela, uma classe para cada tipo de nó...
public enum NodeKind {
	ASSIGN_NODE {
		public String toString() {
            return "=";
        }
	},
    EQ_NODE {
		public String toString() {
            return "==";
        }
	},
    BLOCK_NODE {
		public String toString() {
            return "block";
        }
	},
    BOOL_VAL_NODE {
		public String toString() {
            return "";
        }
	},
    IF_NODE {
		public String toString() {
            return "if";
        }
	},
    INT_VAL_NODE {
		public String toString() {
            return "";
        }
	},
    MINUS_NODE {
		public String toString() {
            return "-";
        }
	},
    DIV_NODE {
		public String toString() {
            return "/";
        }
	},
    MOD_NODE {
        public String toString() {
            return "%";
        }
    },
    PLUS_NODE {
		public String toString() {
            return "+";
        }
	},
    PROGRAM_NODE {
		public String toString() {
            return "program";
        }
	},
    REAL_VAL_NODE {
		public String toString() {
            return "";
        }
	},
    STR_VAL_NODE {
		public String toString() {
            return "";
        }
	},
    TIMES_NODE {
		public String toString() {
            return "*";
        }
	},
    LESS_NODE {
        public String toString() {
            return "<";
        }
    },
    GREATER_NODE {
        public String toString() {
            return ">";
        }
    },
    LESS_OR_EQUALS_NODE {
        public String toString() {
            return "<=";
        }
    },
    GREATER_OR_EQUALS_NODE {
        public String toString() {
            return ">=";
        }
    },
    VAR_DECL_NODE {
		public String toString() {
            return "var_decl";
        }
	},
    VAR_LIST_NODE {
		public String toString() {
            return "var_list";
        }
	},
    VAR_USE_NODE {
		public String toString() {
            return "var_use";
        }
	},
    FUNC_DECL_NODE {
		public String toString() {
            return "func_decl";
        }
	},
    FUNC_USE_NODE {
		public String toString() {
            return "func_use";
        }
	},
    B2I_NODE { // Type conversion.
		public String toString() {
            return "B2I";
        }
	},
    B2R_NODE {
		public String toString() {
            return "B2R";
        }
	},
    B2S_NODE {
		public String toString() {
            return "B2S";
        }
	},
    I2R_NODE {
		public String toString() {
            return "I2R";
        }
	},
    I2S_NODE {
		public String toString() {
            return "I2S";
        }
	},
    R2S_NODE {
		public String toString() {
            return "R2S";
        }
	},
    PRINT_NODE {
        public String toString() {
            return "print";
        }
    },
    SCAN_NODE {
        public String toString() {
            return "scan";
        }
    },
    ARRAY_NODE {
        public String toString() {
            return "arr_";
        }
    },
    FUNC_NODE {
        public String toString() {
            return "";
        }
    },
    // Relational operations
    // --------------------
    AND_NODE {
        public String toString() {
            return "&&";
        }
    },
    OR_NODE {
        public String toString() {
            return "||";
        }
    },
    NEQ_NODE {
        public String toString() {
            return "!=";
        }
    };
	
	public static boolean hasData(NodeKind kind) {
		switch(kind) {
	        case BOOL_VAL_NODE:
	        case INT_VAL_NODE:
	        case REAL_VAL_NODE:
	        case STR_VAL_NODE:
	        case VAR_DECL_NODE:
	        case VAR_USE_NODE:
            case FUNC_NODE:
            case ARRAY_NODE:
	            return true;
	        default:
	            return false;
		}
	}
}
