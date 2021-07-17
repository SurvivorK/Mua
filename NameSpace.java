package mua;

import java.util.HashMap;

public class NameSpace {
    public HashMap<String, Data> var = new HashMap<>();

    NameSpace() {
        var.clear();
        insert("pi", new Data("3.14159"));
    }


    public void insert(String name, Data val) {
        var.put(name, val);
       // check();
    }
    public void erase(String name) {
        var.remove(name);
    }
    public Data getValue(String name) {
        //check();
        if (var.containsKey(name)) return var.get(name);
        else if (this != Main.variables){
            return Main.variables.getValue(name);
        }
        else return null;
        
    }
    public void export(String name) {
        Main.variables.insert(name, var.get(name));
    }

    public void eraseAllNames(){
        var.clear();
    }
    public void printAllNames(){
        for(String s: var.keySet()){
            System.out.println(s);
        }
    }
    public void check() {
        for (HashMap.Entry<String, Data> entry : var.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }
    }
    public void erall() {
        var.clear();
    }

    public void poall() {
        for (String name : var.keySet()) {
            System.out.println(name);
        }
    }
}  
