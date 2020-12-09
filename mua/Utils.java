package mua;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static List<Character> oper = Arrays.asList('+', '-', '*', '/', '(',')');

    public static String getList(String start, Scanner sc, String bracketl ,String bracketr) {
        sc.useDelimiter("");
        String res = start;
        if (countChar(res, bracketl) == countChar(res, bracketr)) {

            return res;
        }
        res += " " + sc.next();
        while (countChar(res, bracketl) != countChar(res, bracketr)) {
            res = res + " " + sc.next();
        }
        sc.reset();
        return res;
    }
    public static int getOperand(String name) {
        Data command = Operator.Variables.getValue(name);
//        System.out.println(command.toString());
        Scanner sc = new Scanner(command.getValue());
        Data varTable = new Data(Utils.getList(sc.next(), sc, "[", "]"));
        if (varTable.getValue() == "") return 0;
        String[] temp =varTable.getValue().split(" ");
//        for (int i = 0; i < temp.length; i++)
//            System.out.print(temp[i]);
//        System.out.println("");
        return varTable.getValue().split(" ").length;

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