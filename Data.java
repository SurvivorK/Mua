package mua;


import java.util.Arrays;
import java.util.List;

public class Data {
    private String val;
    List<String> list = Arrays.asList( 
        "make", "thing", "erase", "isname", 
        "sentence", "list", "join", "word",
        "isempty", "islist", "isnumber", "isbool","isword",
        "save", "load", "erall",
        "first", "butfirst", "butlast","last",
        "print", "read", "readlist", "random",
        "add", "sub", "mul", "div", "mod",
        "eq", "gt", "lt",
        "and", "or", "run", "if",
        "not", "return", "export","sentence","list"
        );
    public Data(String str) {
        this.val = str.trim();
    }
    public boolean isFunction() {
        if (val.matches("^\\[[\\s\\S]*\\[[\\s\\S]*\\][\\s\\S]*\\[[\\s\\S]*\\][\\s\\S]*\\]$")) return true;
        else return false;
    }
    public boolean isOperator() {
        if (val.equals("if")) {
            val = "IF";
            return true;
        }
        if (val.equals("sqrt")) {
            val = "SQRT";
            return true;
        }
        if (val.equals("int")) {
            val = "Int";
            return true;
        }
        if (val.equals("return")) {
            val = "RETURN";
            return true;
        }
        return list.contains(val);
    }
    public boolean isWord() {
        return val.charAt(0) == '\"';
    }
    public boolean isBool() {
        if (val.equals("true") || val.equals("false"))
            return true;
        else
            return false;
    }
    public boolean isList() {
        if (val.charAt(0)=='[' && val.charAt(val.length() - 1) == ']') return true;
        return false;
    }    
    public boolean isNumber() {
        String pattern = "^-?\\d+(,\\d+)*(.\\d+(e\\d+)?)?$";
        if (val.matches(pattern) || (isWord() && getValue().matches(pattern))) return true;
        else return false;
    }
    public boolean isEmpty() {
        return this.getValue().trim().isEmpty();
    }
    public String toString() {
        return val;
    }
    public String toWord() {
        return val.substring(1);
    }
    public double toNumber() {
        if (this.isNumber()) return Double.valueOf(val);
        else if (this.isBool()) return val.equals("true") ? 1 : 0;
        else  return Double.valueOf(val.substring(1));

    }
    public boolean toBool() {
        return val.equals("true");
    }
    public String getValue() {
        if (val.charAt(0) == '\"') return val.substring(1);
        if (val.charAt(0) == '[') return val.substring(1, val.length()-1).trim();
        return val;
    }
    public boolean not() {
        if (this.isBool()) {
            return !this.toBool();
        }
        else return false;
    }
    public double neg() {
        return -this.toNumber();
    }
    public int compareTo(Data x) {
        if (x.isNumber() && this.isNumber()) {
            if (x.toNumber() == this.toNumber()) return 0;
            else if (x.toNumber() > this.toNumber()) return -1;
            else return 1;
        }
        else return this.getValue().compareTo(x.getValue());
    }
}