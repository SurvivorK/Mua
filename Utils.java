package mua;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static List<Character> oper = Arrays.asList('+', '-', '*', '/', '%', '(',')');
    public static ArrayList<String> parseList(NameSpace var, String list) {
        ArrayList<String> res = new ArrayList<>();
        Scanner newsc = new Scanner(list);
        parser p = new parser(newsc, var);
        while (newsc.hasNext()) {
            newsc.reset();
            res.add(p.parse().toString());
        }
        return res;
    }
    public static String expandExpr(String str) {
        String res = "";
        for (int i = 0; i < str.length(); i++) {
            if (oper.contains(str.charAt(i))) {
                if (!res.endsWith(" ")) res = res + " ";
                res = res + str.charAt(i);
                res = res + " ";
            }
            else res = res + str.charAt(i);

        }
        //System.out.println(res);
        return res;
    }
    public static String getList(String start, Scanner sc, String bracketl ,String bracketr) {
        sc.useDelimiter("");
        String res = start.trim();
        while (res.length() == 0) {
            res = res + sc.next();
            res = res.trim();
        }
        if (countChar(res, bracketl) == countChar(res, bracketr)) {
            sc.reset();
            return res;
        }
        //res += " " + sc.next();
        while (countChar(res, bracketl) != countChar(res, bracketr)) {
            res = res + sc.next();
        }
        sc.reset();
        return res;
    }
    public static int getOperand(String name, NameSpace Variables) {
        Data command = Variables.getValue(name);
//        System.out.println(command.toString());

        Scanner sc = new Scanner(command.getValue());
        Data varTable = new Data(Utils.getList("", sc, "[", "]"));
        if (varTable.getValue().trim().equals("")) return 0;
        //System.out.println(varTable.getValue().trim().split("").length);
        return varTable.getValue().trim().split(" ").length;

    }
    public static int countChar(String src, String ch) {
        return src.length() - src.replace(ch, "").length();
    }
    public static Data[] wrapup(Data...x) {
        List<Data> res = new ArrayList<Data>(Arrays.asList(x));
        Data[] ret = new Data[res.size()];
        res.toArray(ret);
        return ret;
    }
}