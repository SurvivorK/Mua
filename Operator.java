package mua;

import java.io.*;
import java.util.*;

public enum Operator {

    make(2) {
        public Data execute(Data[] args) {
            String str = args[0].getValue();
            if (Variables.getValue(str) != null) Variables.erase(str);
            Variables.insert(args[0].getValue(), args[1]);
            return args[1];
        }
    },
    print(1) {
        public Data execute(Data[] args) {
            System.out.println(args[0].getValue());
            return args[0];
        }
    },
    thing(1) {
        public Data execute(Data[] args) {
            Data res = Variables.getValue(args[0].getValue());
            return res;
        }
    },
    read(0) {
        public Data execute(Data[] args) {
           // Scanner sc = new Scanner(System.in);
            String str = Main.sc.next();
            //sc.close();
            return new Data(str);
        }
    },
    readlist(0) {
        @Override
        Data execute(Data[] args) {
            String str = "[" + Main.sc.nextLine() + "]";
            return new Data(str);
        }
    },
    add(2) {
        public Data execute(Data[] args) {
            double sum = args[0].toNumber() + args[1].toNumber();
            return new Data(String.valueOf(sum));
        }
    },
    sub(2) {
        public Data execute(Data[] args) {
            double sum = args[0].toNumber() - args[1].toNumber();
            return new Data(String.valueOf(sum));
        }
    },
    mul(2) {
        public Data execute(Data[] args) {
            double sum = args[0].toNumber() * args[1].toNumber();
            return new Data(String.valueOf(sum));
        }
    },
    div(2) {
        public Data execute(Data[] args) {
            double sum = args[0].toNumber() / args[1].toNumber();
            return new Data(String.valueOf(sum));
        }
    },
    mod(2) {
        public Data execute(Data[] args) {
            int sum = (int) (args[0].toNumber()) % (int)(args[1].toNumber());
            return new Data(String.valueOf(sum));
        }
    }, 
    and(2) {
        public Data execute(Data[] args) {
            boolean res = (args[0].toBool()) & (args[1].toBool());
            if (res) return new Data("true");
            else return new Data("false");
        }
    },
    or(2) {
        public Data execute(Data[] args) {
            boolean res = (args[0].toBool()) | (args[1].toBool());
            if (res) return new Data("true");
            else return new Data("false");
        }
    },
    not(1) {
        public Data execute(Data[] args) {
            if (args[0].not()) return new Data("true");
            else return new Data("false");
        }
    },
    gt(2) {
        public Data execute(Data[] args) {
            if (args[0].compareTo(args[1]) == 1) return new Data("true");
            else return new Data("false");
        }
    },
    lt(2) {
        public Data execute(Data[] args) {
            if (args[0].compareTo(args[1]) == -1) return new Data("true");
            else return new Data("false");
        }
    },
    eq(2) {
        public Data execute(Data[] args) {
            if (args[0].compareTo(args[1]) == 0) return new Data("true");
            else return new Data("false");
        }
    }, 
    islist(1){
        public Data execute(Data[] args) {
            if (args[0].isList()) return new Data("true");
            else return new Data("false");
        }
    },
    isbool(1){
        public Data execute(Data[] args) {
            if (args[0].isBool()) return new Data("true");
            else return new Data("false");
        }
    },
    isnumber(1) {
        public Data execute(Data[] args) {
            if (args[0].isNumber()) return new Data("true");
            else return new Data("false");
        } 
    },
    isword(1) {
        public Data execute(Data[] args) {
            if (args[0].isWord()) return new Data("true");
            else return new Data("false");
        } 
    },
    isname(1) {
        public Data execute(Data[] args) {
            String str = args[0].getValue();
            if (Variables.getValue(str) != null) return new Data("true");
            else return new Data("false");
        }  
    },
    isempty(1) {
        public Data execute(Data[]  args) {
            if (args[0].isEmpty()) return new Data("true");
            else return new Data("false");
        }
    },
    erase(1) {
        public Data execute(Data[] args) {
            String str = args[0].getValue();
            Data res = Variables.getValue(str);
            Variables.erase(str);
            return res;
        } 
    },
    IF(3) {
        public Data execute(Data[] args) {
            Data[] param = new Data[1];
            if (args[0].toBool()) param[0] = args[1];
            else param[0] = args[2];
            return run.execute(param);
        } 
    },
    run(1) {
        public Data execute(Data[] args) {

            Scanner newsc = new Scanner(args[0].getValue().trim());
            parser newp = new parser(newsc, Variables);
            return newp.parseAll();
        } 
    },
    RETURN(1) {
        Data execute(Data[] args) {

            return args[0];
        }
    },
    export(1) {
        @Override
        Data execute(Data[] args) {
            Main.variables.insert(args[0].getValue(), Variables.getValue(args[0].getValue()));
            return null;
        }
    },
    random(1) {
        @Override
        Data execute(Data[] args) {
            Double ub = args[0].toNumber();
            return new Data(String.valueOf(Math.random() * ub));
        }
    },
    Int(1) {
        @Override
        Data execute(Data[] args) {
            return new Data(String.valueOf(Math.floor((args[0].toNumber()))));
        }
    },
    Sqrt(1) {
        @Override
        Data execute(Data[] args) {
            return new Data(String.valueOf(Math.sqrt(args[0].toNumber())));
        }
    },
    poall(0) {
        @Override
        Data execute(Data[] args) {
            Variables.poall();
            return null;
        }
    },
    erall(0) {
        @Override
        Data execute(Data[] args) {
            Variables.erall();
            return null;
        }
    },
    word(2) {
        @Override
        Data execute(Data[] args) {
            if (args[0].isWord() && (args[1].isWord() || args[1].isBool() || args[1].isNumber())) {
                String res = "\"" + args[0].getValue() + args[1].getValue();
                return new Data(res);
            }
            else return null;
        }
    },
    sentence(2) {
        @Override
        Data execute(Data[] args) {
            String res = "[" + args[0].getValue() + " " + args[1].getValue() + "]";
            return new Data(res);
        }
    },
    list(2) {
        @Override
        Data execute(Data[] args) {
            String op1, op2;
            if (args[0].isList()) op1 = args[0].toString();
            else op1 = args[0].getValue();
            if (args[1].isList()) op2 = args[1].toString();
            else op2 = args[1].getValue();
            return new Data("[" + op1 + " " + op2 + "]");
        }
    },
    join(2) {
        @Override
        Data execute(Data[] args) {
            String res;
            if (args[1].isList()) res = args[1].toString();
            else res = args[1].getValue();
            return new Data("[" + args[0].getValue() + " " + res + "]");
        }
    },
    first(1) {
        @Override
        Data execute(Data[] args) {
            if (args[0].isList()) {
                ArrayList<String> contains = Utils.parseList(Variables, args[0].getValue());
                return new Data(contains.get(0));
            }
            else return new Data(String.valueOf(args[0].getValue().charAt(0)));
        }
    },
    last(1) {
        @Override
        Data execute(Data[] args) {
            if (args[0].isList()) {
                ArrayList<String> contains = Utils.parseList(Variables, args[0].getValue());
                return new Data(contains.get(contains.size()-1));
            }
            else {
                String str = args[0].getValue();
                return new Data(String.valueOf(str.charAt(str.length() - 1)));
            }
        }
    },
    butfirst(1) {
        @Override
        Data execute(Data[] args) {
            if (args[0].isList()) {
                String str = "[";
                ArrayList<String> contains = Utils.parseList(Variables, args[0].getValue());
                for (int i = 1; i < contains.size(); i++) {
                    str = str + " " + contains.get(i);
                }
                str =str + "]";
                return new Data(str);
            }
            else {
                String str = args[0].getValue();
                return new Data(str.substring(1, str.length()));
            }
        }
    },
    butlast(1) {
        @Override
        Data execute(Data[] args) {
            if (args[0].isList()) {
                String str = "[";
                ArrayList<String> contains = Utils.parseList(Variables, args[0].getValue());
                for (int i = 0; i < contains.size() - 1; i++) {
                    str = str + " " + contains.get(i);
                }
                str =str + "]";
                return new Data(str);
            }
            else {
                String str = args[0].getValue();
                return new Data(str.substring(0, str.length() - 1));
            }

        }
    },
    save(1) {
        @Override
        Data execute(Data[] args) {
            String filename = args[0].getValue();
            File file = new File(filename);
            try {
                file.createNewFile();
                BufferedWriter out = new BufferedWriter(new FileWriter(file));
                HashMap<String, Data> hashMap = Main.variables.var;
                String line = "";
                for (String name : hashMap.keySet()) {
                    line = "make \"" + name + " " + hashMap.get(name) + "\n";
                    out.write(line);
                }
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    },
    load(1) {
        @Override
        Data execute(Data[] args) {
            String filename = args[0].getValue();
            File file = new File(filename);
            Long fileLength = file.length();
            byte[] fileContent = new byte[fileLength.intValue()];
            try {
                FileInputStream in = new FileInputStream(file);
                in.read(fileContent);
                String content = new String(fileContent);
                Scanner newsc = new Scanner(content);
                parser p = new parser(newsc, Main.variables);
                p.parseAll();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    },
    function(1) {
        /**
         *
         * @param args args[0]: function; name args[1] : arguments assigned
         * @return function result
         */
        public Data execute(Data[] args) {
            String name = args[0].getValue();
//          commands in format : [[] []]
            Data command = Variables.getValue(name);
            NameSpace funVar = new NameSpace();
            Scanner sc = new Scanner(command.getValue());
            Data varTable = new Data(Utils.getList("", sc, "[", "]"));
//          argument in []
            command = new Data(Utils.getList("", sc, "[", "]"));
            // argument list
            String[] vlist = varTable.getValue().split(" ");
            for (int i = 1; i < args.length; i++) {
                funVar.insert(vlist[i - 1], args[i]);
            }

            Scanner newsc = new Scanner(command.getValue());
            parser p = new parser(newsc, funVar);
            return p.parseAll();
        }


    };

    int Operand;
    Operator(int Operand) {this.Operand = Operand;}
    Operator() {}

    public int getOperand() {
        return Operand;
    }
    abstract Data execute(Data[]  args);
    public static NameSpace Variables;
}
