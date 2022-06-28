package typing;

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
    NULL_TYPE {
        public String toString(){
            return "nil";
        }
    }

}
