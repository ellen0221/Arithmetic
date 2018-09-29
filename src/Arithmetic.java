import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Arithmetic {

    static List<String> e = new ArrayList<String>();   // 用于存放题目
    static List<String> a = new ArrayList<String>();   // 用于按题号顺序存放答案
    static String[] opr = {"+", "-", "x", "÷"};
    static ArrayList<String> exp = new ArrayList<String>();	// 表达式
    static int  num = 10;    // 题目数量，默认为10道
    static int range = 0;   // 数值范围
    static String error = null;    // 错误信息
    static String efile = "src" + File.separator;   // 给定的题目文件：Exercise.txt
    static String afile = "src" + File.separator;   // 给定的答案文件：Answer.txt
    static String gfile = "src" + File.separator;   // 结果统计文件：Grade.txt

    static void main(String[] argv) {
    
    	
        // 根据功能处理参数
        for (int i=0; i<argv.length; i++) {
            if (argv[i].equals("-r")) {
                range = Integer.parseInt(argv[i+1]);
            } else if (argv[i].equals("-n"))
            {
                num = Integer.parseInt(argv[i+1]);
            } else if (argv[i].equals("-h")) {  // 帮助信息
                System.out.println("-r 参数: 控制题目中数值（自然数、真分数和真分数分母）的范围 \n例如: Myapp.exe -r 10 将生成10以内(不包括10)的四则运算题目");
                System.out.println("-n 参数: 控制生成题目的个数 \n例如: Myapp.exe -n 10  将生成10个题目");
                System.out.println("支持对给定的题目文件和答案文件，判定答案中的对错并进行数量统计 具体参数如下:\nMyapp.exe -e <exercisefile>.txt -a <answerfile>.txt");
            } else if (argv[i].equals("-e")) {
                efile = efile + argv[i+1];
                range = 10; // 防止报错
            } else if (argv[i].equals("-a")) {
                afile = afile + argv[i+1];
                range = 10;
            }
        }
        if (able(num, range)) {
            for (int i=0; i<num; i++) {
                e.add(subject().toString());
            }
            save(e);
        }else {
            System.out.println(error);
        }
    }

    // 检查输入的题目数量与数值范围是否可实现,并修改
    static boolean able(int n, int r) {
        if (n>10000) {
            error = "最大题数为10000";
            return false;
        } else if (n<=0) {
            error = "题数不合法";
            return false;
        } else if (range == 0) {
            error = "请给定数值范围，详细操作通过 -h 查看";
            return false;
        } else if (range<0) {
            error = "数值范围不合法";
            return false;
        } else if (range<5) {
            error = "数值范围过小，至少为5";
            return false;
        }
        return true;
    }

    // 生成表达式
    static ArrayList<String> subject() {
        // 运算符个数 1-3 个
        int opr_n = (int) (Math.random()*3 + 1);
        switch(opr_n) {
            case 1:
                exp.add(createnum());
                exp.add(createopr1());
                exp.add(createnum());
                break;
            case 2:
                // 括号起始位置
                int bkt_s = (int) (Math.random()*3);
                // 括号结束位置
                int bkt_e = 0;
                // 无括号
                if (bkt_s == 0) {
                    bkt_e = 0;
                } else {
                    bkt_e = bkt_s + 1;
                }
                for (int i = 1; i <= 3; i++) {
                    if (bkt_s == i) {
                        exp.add("(");
                    }
                    exp.add(createnum());
                    if (bkt_e == i) {
                        exp.add(")");
                    }
                    exp.add(createopr1());
                }
                // 处理括号无意义情况
                checkbkt(bkt_s, bkt_e);
                exp.remove(exp.size()-1);	// 删除最后多加入的一个运算符
                break;
            case 3:
                // 括号起始位置
                bkt_s = (int) (Math.random()*4);
                // 括号结束位置
                bkt_e = 0;
                // 无括号
                if (bkt_s == 0) {
                    bkt_e = 0;
                } else if (bkt_s == 3){
                    bkt_e = 4;
                } else {
                    bkt_e = bkt_s + (int) (Math.random()*2 + 1);    // [1,3)
                }
                for (int i = 1; i <= 4; i++) {
                    if (bkt_s == i) {
                        exp.add("(");
                    }
                    exp.add(createnum());
                    if (bkt_e == i) {
                        exp.add(")");
                    }
                    exp.add(createopr1());
                }
                // 处理括号无意义情况
                checkbkt(bkt_s, bkt_e);
                exp.remove(exp.size()-1);	// 删除最后多加入的一个运算符
                break;
        }
        return exp;
    }

    // 产生随机数
    public static String createnum() {
        int a = (int) (Math.random()*range*range);
        int b = (int) (Math.random()*range);
        num n = new num(a,b);
        easy(n);
        return n.tostring();
    }

    public static num easy(num n) {    // 约分
        if (n.a == 0) {
            return new num(0, 1);
        } else {
            int m = n.gcd(n.a, n.b);
            if (m == 1) {
                return n;
            } else  {
                n.a = n.a/m;
                n.b = n.b/m;
                return n;
            }
        }
    }

    // 随机产生运算符
    public static String createopr1() {
        return opr[(int) (Math.random()*4)];
    }

    // 只产生 x 或 ÷
    public static String createopr2() {
        return opr[(int) (Math.random()*2 + 2)];
    }

    // 只产生 + 或 -
    public static String createopr3() {
        return opr[(int) (Math.random()*2)];
    }

    // 处理括号无意义情况
    public static void checkbkt(int bkt_s, int bkt_e) {
        if (bkt_e - bkt_s == 1) {
            if (bkt_s == 1) {
                String f = exp.get(2).toString();
                String s = exp.get(5).toString();
                if (!(f.equals("+") || (f.equals("-"))) && !(s.equals("x") || s.equals("÷"))) {
                    exp.set(2, createopr3());
                    exp.set(5, createopr2());
                }
            } else if (bkt_s == 2) {
                String f = exp.get(1).toString();
                String s = exp.get(4).toString();
                if ((f.equals("+") || (f.equals("-"))) && (s.equals("x") || (s.equals("÷")))) {	// 排除 a + ( b x c ) 此类括号无意义的情况
                    exp.set(1, createopr2());
                }
            } else if (bkt_s == 3) {
                String f = exp.get(3).toString();
                String s = exp.get(6).toString();
                if ((f.equals("+") || (f.equals("-"))) && (s.equals("x") || s.equals("÷"))) {
                    exp.set(3, createopr2());
                } else if (((f.equals("+") || f.equals("-"))) && (s.equals("+") || s.equals("-"))) {
                    exp.set(3, createopr2());
                }
            }
        } else {
            if (bkt_s == 1) {
                String f = exp.get(2).toString();
                String s = exp.get(4).toString();
                String t = exp.get(7).toString();
                if (!(t.equals("x") || t.equals("÷"))) {
                    exp.set(7, createopr2());
                    if (f.equals(s) && (f.equals("+") || f.equals("÷"))) {
                        exp.set(4, createopr2());
                    }
                }
            } else if (bkt_s == 2) {
                String f = exp.get(1).toString();
                String s = exp.get(4).toString();
                String t = exp.get(6).toString();
                if ((f.equals("+") || f.equals("-")) && (t.equals("x") || t.equals("÷"))) {
                    exp.set(1, createopr2());
                }
            }
        }
    }

    // 转换为字符串
    static String toString(Stack s) {
        Iterator<Object> i = s.iterator();
        String st = "";
        while (i.hasNext()) {
            st += i.next().toString();
        }
        return st;
    }

    // 转换为字符串
    static String toString(ArrayList<Object> list) {
        String s = "";
        for (int i=0; i<list.size(); i++) {
            s += list.get(i).toString();
        }
        return s;
    }

    // 计算表达式
    public Stack<Node> count(ArrayList<String> list) {
//        ArrayList<Object> r = new ArrayList<Object>();  // 存储后序遍历表达式结果
        Stack<Node> a = new Stack<>();  // 栈
        Stack<String> b = new Stack<>();
        String op;
        for (int i=0; i<list.size(); i++) {
            // 当前指针为数值
            String s = list.get(i);
            String peek = b.peek();
            if (!isop(s)) {
                a.push(new Node(s, null, null));
            } else {
                while (!b.isEmpty() && !(s.equals("(") || (prefer(s) == 2 && prefer(peek) == 1) || ))
            }
        }

    }

    static class Node {
        String result;
        Node right;
        Node left;

        public Node(String result, Node right, Node left) {
            this.result = result;
            this.right = right;
            this.left = left;
        }

    }

    public int prefer(String op) {
        if (op.equals("-") || op.equals("+")) {
            return 1;
        } else if (op.equals("x") || op.equals("÷")) {
            return 2;
        } else {
            return 3;
        }
    }

    public static boolean isop(String op) { // 判断是否为运算符
        if (op.equals("+") || op.equals("-")
                || op.equals("x") || op.equals("÷")
                || op.equals("(") || op.equals(")")) {
            return true;
        } else  {
            return false;
        }
    }

    // 检查是否有相同的题目，并处理
    static void ifExist(List s) {

    }


 /* //将中缀表达式转换成后缀表达式
    public static ArrayList transform(String prefix) {
        //System.out.println("transform");
        int i, len = prefix.length();
        prefix=prefix+ '#';
        Stack<Character> stack = new Stack<Character>();// 保存操作符的栈
        stack.push('#');
        ArrayList postfix = new ArrayList();

        for (i = 0; i < len + 1; i++) {
            //System.out.println(i+" "+prefix.charAt(i));
            if (Character.isDigit(prefix.charAt(i))) {
                if (Character.isDigit(prefix.charAt(i+1))) {
                    postfix.add(10 * (prefix.charAt(i)-'0') + (prefix.charAt(i+1)-'0'));
                    i++;
                } else {
                    postfix.add((prefix.charAt(i)-'0'));
                }
            } else {
                switch (prefix.charAt(i)) {
                case '(':
                    stack.push(prefix.charAt(i));
                    break;
                case ')':
                    while (stack.peek() != '(') {
                        postfix.add(stack.pop());
                    }
                    stack.pop();
                    break;
                default:// 默认情况下:+ - * /
                    while (stack.peek() != '#'
                            && compare(stack.peek(), prefix.charAt(i))) {
                        postfix.add(stack.pop());// 不断弹栈，直到当前的操作符的优先级高于栈顶操作符
                    }
                    if (prefix.charAt(i) != '#') {// 如果当前的操作符不是'#'(结束符)，那么入操作符栈
                        stack.push(prefix.charAt(i));// 最后的标识符'#'是不入栈的
                    }
                    break;
                }
            }
        }
        return postfix;
    }
    //比较运算符之间的优先级
    public static boolean compare(char peek, char cur) {
        if (peek == '*'
                && (cur == '+' || cur == '-' || cur == '/' || cur == '*')) {
            return true;
        } else if (peek == '/'
                && (cur == '+' || cur == '-' || cur == '*' || cur == '/')) {
            return true;
        } else if (peek == '+' && (cur == '+' || cur == '-')) {
            return true;
        } else if (peek == '-' && (cur == '+' || cur == '-')) {
            return true;
        } else if (cur == '#') {
            return true;
        }
        return false;
    }

    //计算后缀表达式
    public static double calculate(ArrayList postfix){
        //System.out.println("calculate");
        int i,size=postfix.size();
        double res=0;
        Stack<Double> stack_num=new Stack<Double>();
        for(i=0;i<size;i++){
            if(postfix.get(i).getClass()==Integer.class){
            	//double c=(double) postfix.get(i);
                stack_num.push(Double.parseDouble(String.valueOf( postfix.get(i))));
                //System.out.println("push"+" "+(Integer)postfix.get(i));
            }else{
                //System.out.println((Character)postfix.get(i));
            	double a=stack_num.pop();
            	double b=stack_num.pop();
                switch((Character)postfix.get(i)){
                case '+':
                    res=b+a;
                    //System.out.println("+ "+a+" "+b);
                    break;
                case '-':
                    res=b-a;
                    //System.out.println("- "+a+" "+b);
                    break;
                case '*':
                    res=b*a;
                    //System.out.println("* "+a+" "+b);
                    break;
                case '/':
                    res=b/a;
                    //System.out.println("/ "+a+" "+b);
                    break;
                }
                stack_num.push(res);
                //System.out.println("push"+" "+res);
            }
        }
        res=stack_num.pop();
        //System.out.println("res "+" "+res);
        return res;
       //if(res==24){
           // return true;
      // }
        //return false;
    }*/


    static void check(File e, File a, File g) {
        // 用于对给定的题目文件和答案文件判断答案文件中的对错并统计
        try (BufferedReader exReader = new BufferedReader(new FileReader(e));
             BufferedReader anReader = new BufferedReader(new FileReader(a));
             BufferedWriter gradeWriter = new BufferedWriter(new FileWriter(g))
        ) {
            String ex, an;
            int c = 0, w = 0;
            StringBuilder correct = new StringBuilder("Correct: %d (");
            StringBuilder wrong = new StringBuilder("Wrong: %d (");
            while ((ex = exReader.readLine()) != null && (an = anReader.readLine()) != null) {
                int exPoint = ex.indexOf(".");
                int anPoint = an.indexOf(".");
                if (exPoint != -1 && anPoint != -1) {
                    int i = Integer.valueOf(ex.substring(0,exPoint).trim());
                    String expression = ex.substring(exPoint + 2);
                    String answer = an.substring(anPoint + 2);
                    if (expression.calculate(postfix).equals(answer.toString())) {
                    	   /*需要拿到计算的结果这个方法才能用
                    	   上面注释掉的是我从别人博客Copy来的相关代码*/
                       c++;
                        correct.append(" ").append(i);
                        if (c % 20 == 0) {
                            correct.append("\n");
                        }
                    } else {
                        w++;
                        wrong.append(" ").append(i);
                        if (w % 20 == 0) {
                            wrong.append("\n");
                        }
                    }
                }
            }
            gradeWriter.write(String.format(correct.append(" )\n").toString(),c));
            gradeWriter.write(String.format(wrong.append(" )\n").toString(),w));
            gradeWriter.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    static void save(List<String> s) {
        // 用于将题目存入当前目录下的Exercises.txt文件
        File question = new File("./Exercises.txt");
        if (!question.exists()) {
            System.out.println("文件不存在，创建文件: Exercises.txt" );
            try {
                question.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("文件已存在，文件为: Exercises.txt" );
        }
        FileWriter fw;
        try {
            fw = new FileWriter(question);
            BufferedWriter bw =new BufferedWriter(fw);
            for(int i=0;i<s.size();i++){
                String t = i+1 + ". "+ s.get(i);
                bw.write( t);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void answer(List<String> a) {
        // 用于将题目答案存入当前目录下的Answer.txt文件
        File answer = new File("./Answer.txt");
        if (!answer.exists()) {
            System.out.println("文件不存在，创建文件: Answer.txt" );
            try {
                answer.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("文件已存在，文件为: Answer.txt" );
        }
        FileWriter fw;
        try {
            fw = new FileWriter(answer);
            BufferedWriter bw =new BufferedWriter(fw);
            for(int i=0;i<a.size();i++){
                String t =i+1+". "+ a.get(i);
                bw.write( t);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}