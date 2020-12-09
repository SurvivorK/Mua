package mua;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import mua.Utils;

public class parser {
    Scanner sc;
    public NameSpace variables;
    public parser(Scanner sc, NameSpace variables) {
        this.sc = sc;
        this.variables = variables;
    }

    public Data parse() {
        Operator.Variables = variables;
        String str = sc.next();

            if (str.charAt(0) == '[') {
            str = Utils.getList(str, sc, "[", "]");
           // System.out.println(str);
            return new Data(str);
        } else if (str.charAt(0) == '(') {
            str = Utils.getList(str, sc, "(", ")");
            Scanner newsc = new Scanner(str.trim());
            Expression exp = new Expression(newsc, variables);
           // System.out.println(str);
            return exp.parse();
        }
        //System.out.println(str);
        if (str.charAt(0) == ':') {
            Operator op = Operator.thing;
            Data name = new Data(str.substring(1));
            Data[] args = {name};
            return op.execute(args);
        }
        Data res = null;
        Data v = new Data(str);
        if (v.isOperator()) {
            Operator op = Operator.valueOf(v.toString());

            int cnt = op.getOperand();
            Data[] args = null;
            if (cnt > 0) {
                args = new Data[cnt];
                for (int i = 0; i < cnt; i++)
                    args[i] = parse();
            }
            res = op.execute(args);
            if (v.toString() == "RETURN") {
                while (sc.hasNext()) sc.next();
            }
        }
        else if (!v.isWord() && variables.getValue(v.getValue()) != null && variables.getValue(v.getValue()).isFunction()) {
            Operator op = Operator.function;
            int cnt = Utils.getOperand(v.getValue());
            Data[] args = new Data[cnt + 1];
            args[0] = new Data(v.getValue());
            for (int i = 1; i <= cnt; i++) args[i] = parse();
            System.out.print(v.getValue());
            for (int i = 1; i <= cnt; i++) System.out.print(args[i].toString() + " ");
            System.out.println("");


            res = Operator.function.execute(args);
        }
        else res = v;
        return res;
    }
}

class Expression {
    Scanner sc;
    NameSpace var;
    char ch;
    Expression(Scanner sc, NameSpace var){
        this.sc = sc;
        sc.useDelimiter("");
        this.var = var;
    }

    Data parse() {
        Data x = parseNumber();
        for (;;) {
            if (check('+'))
                x = Operator.add.execute(Utils.wrapup(x, parseNumber()));
            else if (check('-')) x = Operator.sub.execute(Utils.wrapup(x, parseNumber()));
            else return x;
        }
    }

    Data parseNumber() {
        Data x = parseFactor();
        for (;;) {
            if (check('*')) {
                int y = 0;
                x = Operator.mul.execute(Utils.wrapup(x, parseFactor()));
            }
            else if (check('/')) x = Operator.div.execute(Utils.wrapup(x, parseFactor()));
            else if (check('%')) x = Operator.mod.execute(Utils.wrapup(x, parseFactor()));
            else return x;
        }
    }

    Data parseFactor() {
        nextpos();
        if (check('-')) {
            Data res = parseFactor();
            return new Data(String.valueOf(res.neg()));
        }
        if (check('+')) return parseFactor();
        Data x;
        if (check('(')) {
            x = (new Expression(sc, var)).parse();
            nextpos();
            check(')');
        }
        else if (ch >= '0' && ch <= '9') {
            int num = 0;
            for ( ; ch >= '0' && ch <= '9'; nextpos()) num = num * 10 + ch - '0';
           x = new Data(String.valueOf(num));
        }
        else if (ch == ':') {
            String var = "";
            for ( nextpos(); !Utils.oper.contains(ch) && ch != ' '; nextpos()) var = var + ch;
            x = Operator.thing.execute(Utils.wrapup(new Data(var)));
        }
        else {
            String all = String.valueOf(ch);
            while (sc.hasNext()) all = all + sc.next();
            sc = new Scanner(all);
            x = (new parser(sc, var)).parse();
            sc.useDelimiter("");
            nextpos();
        }
        return x;

    }
    void nextpos() {
        if (sc.hasNext()) ch = sc.next().charAt(0);
        else ch = '$';
    }
    boolean check(char x) {
        while (ch == ' ') nextpos();
        if (ch == x) return true;
        return false;
    }
}
