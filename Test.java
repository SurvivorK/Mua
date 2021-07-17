package mua;

import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
        String fun = "[ \n" +
                " \t [ n ] \n" +
                " \t [ \n" +
                " \t \t i f   l t   : n   2 \n" +
                " \t \t \t [ r e t u r n   1 ] \n" +
                " \t \t \t [ r e t u r n   ( : n   *   f a c t o r i a l   ( : n - 1 ) ) ] \n" +
                " \t ] \n" +
                " ]";
        // System.out.print(fun.replaceAll("\\[", " "));
        if (Pattern.matches("^\\[[\\s\\S]*\\[[\\s\\S]*\\][\\s\\S]*\\[[\\s\\S]*\\][\\s\\S]*\\]$", fun)) System.out.println("yes");
        else System.out.println("no");
    }
}
