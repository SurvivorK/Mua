package mua;

import java.util.Scanner;

public enum Operator {

    make(2) {
        public Data execute(Data[] args) {
            Variables.insert(args[0].getValue(), args[1]);
            return null;
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

            Data res = null;
            if (newsc.hasNext())
                res = newp.parse();
            while (newsc.hasNext()) {
                Data temp = newp.parse();
                if (temp == null) break;
                else res = temp;
            }
            return res;
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
            Data varTable = new Data(Utils.getList(sc.next(), sc, "[", "]"));
//          argument in []
            command = new Data(Utils.getList(sc.next(), sc, "[", "]"));
            // argument list
            String[] vlist = varTable.getValue().split(" ");
            for (int i = 1; i < args.length; i++) {
                funVar.insert(vlist[i - 1], args[i]);
            }

            sc = new Scanner(command.getValue());
            parser p = new parser(sc, funVar);
            Data res = new Data("");
            while (sc.hasNext()) {
                res = p.parse();
            }
            return res;
        }


    };

    int Operand;
    Operator(int Operand) {this.Operand = Operand;}
    Operator() {}

    public int getOperand() {
        return Operand;
    }
    abstract Data execute(Data[]  args);
    public static NameSpace Variables = new NameSpace();
}
