package mua;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {
    
    public static InputStream stdin = System.in;
    public static Scanner sc = new Scanner(stdin);
    public static NameSpace variables = new NameSpace();
    public static void main(String [] args) {

        try {
            File file = new File("./in");
            InputStream fin = new FileInputStream(file);
            System.setIn(fin);
            sc = new Scanner(fin);
            System.setIn(stdin);
        while (sc.hasNext()) {
            parser p = new parser(sc, variables);
            p.parse();
        }
            fin.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {

        }
    }
}