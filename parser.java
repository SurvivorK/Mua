package mua;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import mua.Utils;

public class parser {
    Scanner sc;
    public NameSpace variables;
    boolean ret;

    public parser(Scanner sc, NameSpace variables) {
        this.sc = sc;
        this.variables = variables;
        this.ret = false;
    }
    public Data parseAll() {
        Data res = null;
        while (sc.hasNext()) {
            sc.reset();
            res = parse();
            if (this.ret) break;
        }
        return res;
    }
    public Data parse() {
        Operator.Variables = variables;
        String str = sc.next();

        if (str.charAt(0) == '[') {
            str = Utils.getList(str, sc, "[", "]");
           // System.out.println(str);
//            if (str.equals("[1 [2 3] 4]")) {
//                System.out.println("fuck");
//            }
            return new Data(str);
        } else if (str.charAt(0) == '(') {
            str = Utils.getList(str, sc, "(", ")");

            Scanner newsc = new Scanner(Utils.expandExpr(str));
            Expression exp = new Expression(newsc, variables);
           // System.out.println(str);
            return exp.parse();
        }
        //System.out.println(str);
        if (str.charAt(0) == ':') {
            Operator op = Operator.thing;
            Data name = new Data(str.substring(1));
            Data[] args = {name};
            Operator.Variables = variables;
            return op.execute(args);
        }

//        System.out.print(str + " ");

        Data res = null;
        Data v = new Data(str);
        if (v.isOperator()) {
            Operator op = Operator.valueOf(v.toString());

            int cnt = op.getOperand();
            Data[] args = null;
            if (cnt > 0) {
                args = new Data[cnt];
                for (int i = 0; i < cnt; i++) {
                    args[i] = parse();
                    //System.out.print(args[i] + " ");
                }
                //System.out.println("");
            }
            Operator.Variables = variables;
            res = op.execute(args);
            if (v.toString() == "RETURN") {
                ret = true;
            }
        }
        else if (!v.isWord() && variables.getValue(v.getValue()) != null && variables.getValue(v.getValue()).isFunction()) {
            Operator op = Operator.function;
            int cnt = Utils.getOperand(v.getValue(), variables);
            Data[] args = new Data[cnt + 1];
            args[0] = new Data(v.getValue());
            for (int i = 1; i <= cnt; i++) {
                args[i] = parse();
//                System.out.print(args[i] + " ");
            }
//            System.out.println("");

            Operator.Variables = variables;
            res = Operator.function.execute(args);
        }
        else res = v;
        //System.out.println(v + " result: " + res);
        return res;
    }
}

class Expression {
    Scanner sc;
    NameSpace var;
    String ch;
    Expression(Scanner sc, NameSpace var){
        this.sc = sc;
        this.var = var;
    }

    Data parse() {
        Data x = parseNumber();
        for (;;) {
            if (check("+"))
                x = Operator.add.execute(Utils.wrapup(x, parseNumber()));
            else if (check("-")) x = Operator.sub.execute(Utils.wrapup(x, parseNumber()));
            else return x;
        }
    }

    Data parseNumber() {
        Data x = parseFactor();
        for (;;) {
            if (check("*")) {
                int y = 0;
                x = Operator.mul.execute(Utils.wrapup(x, parseFactor()));
            }
            else if (check("/")) x = Operator.div.execute(Utils.wrapup(x, parseFactor()));
            else if (check("%")) x = Operator.mod.execute(Utils.wrapup(x, parseFactor()));
            else return x;
        }
    }

    Data parseFactor() {
        nextpos();
        if (check("-")) {
            Data res = parseFactor();
            return new Data(String.valueOf(res.neg()));
        }
        if (check("+")) return parseFactor();
        Data x;
        if (check("(")) {
            x = (new Expression(sc, var)).parse();
            nextpos();
            check(")");
        }
        else if (ch.charAt(0) >= '0' && ch.charAt(0) <= '9') {
            int num = Integer.valueOf(ch);
           x = new Data(String.valueOf(num));
        }
        else if (ch.charAt(0) == ':') {
            String varName = ch.substring(1);
            Operator.Variables = var;
            x = Operator.thing.execute(Utils.wrapup(new Data(varName)));
            nextpos();
        }
        else {
            String all = ch + " ";
            sc.useDelimiter("");
            while (sc.hasNext()) all = all + sc.next();
            sc = new Scanner(all);
            sc.reset();
            parser p = new parser(sc, var);
            x = p.parse();
            nextpos();
        }
        return x;

    }
    void nextpos() {
        if (sc.hasNext()) ch = sc.next();
        else ch = "$";
    }
    boolean check(String x) {
        if (ch.equals(x)) return true;
        return false;
    }
}
