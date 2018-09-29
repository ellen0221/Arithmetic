import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Arithmetic {

    static List<String> e = new ArrayList<String>();   // ���ڴ����Ŀ
    static List<String> a = new ArrayList<String>();   // ���ڰ����˳���Ŵ�
    static String[] opr = {"+", "-", "x", "��"};
    static ArrayList<String> exp = new ArrayList<String>();	// ���ʽ
    static int  num = 10;    // ��Ŀ������Ĭ��Ϊ10��
    static int range = 0;   // ��ֵ��Χ
    static String error = null;    // ������Ϣ
    static String efile = "src" + File.separator;   // ��������Ŀ�ļ���Exercise.txt
    static String afile = "src" + File.separator;   // �����Ĵ��ļ���Answer.txt
    static String gfile = "src" + File.separator;   // ���ͳ���ļ���Grade.txt

    static void main(String[] argv) {
    
    	
        // ���ݹ��ܴ������
        for (int i=0; i<argv.length; i++) {
            if (argv[i].equals("-r")) {
                range = Integer.parseInt(argv[i+1]);
            } else if (argv[i].equals("-n"))
            {
                num = Integer.parseInt(argv[i+1]);
            } else if (argv[i].equals("-h")) {  // ������Ϣ
                System.out.println("-r ����: ������Ŀ����ֵ����Ȼ������������������ĸ���ķ�Χ \n����: Myapp.exe -r 10 ������10����(������10)������������Ŀ");
                System.out.println("-n ����: ����������Ŀ�ĸ��� \n����: Myapp.exe -n 10  ������10����Ŀ");
                System.out.println("֧�ֶԸ�������Ŀ�ļ��ʹ��ļ����ж����еĶԴ���������ͳ�� �����������:\nMyapp.exe -e <exercisefile>.txt -a <answerfile>.txt");
            } else if (argv[i].equals("-e")) {
                efile = efile + argv[i+1];
                range = 10; // ��ֹ����
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

    // ����������Ŀ��������ֵ��Χ�Ƿ��ʵ��,���޸�
    static boolean able(int n, int r) {
        if (n>10000) {
            error = "�������Ϊ10000";
            return false;
        } else if (n<=0) {
            error = "�������Ϸ�";
            return false;
        } else if (range == 0) {
            error = "�������ֵ��Χ����ϸ����ͨ�� -h �鿴";
            return false;
        } else if (range<0) {
            error = "��ֵ��Χ���Ϸ�";
            return false;
        } else if (range<5) {
            error = "��ֵ��Χ��С������Ϊ5";
            return false;
        }
        return true;
    }

    // ���ɱ��ʽ
    static ArrayList<String> subject() {
        // ��������� 1-3 ��
        int opr_n = (int) (Math.random()*3 + 1);
        switch(opr_n) {
            case 1:
                exp.add(createnum());
                exp.add(createopr1());
                exp.add(createnum());
                break;
            case 2:
                // ������ʼλ��
                int bkt_s = (int) (Math.random()*3);
                // ���Ž���λ��
                int bkt_e = 0;
                // ������
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
                // �����������������
                checkbkt(bkt_s, bkt_e);
                exp.remove(exp.size()-1);	// ɾ����������һ�������
                break;
            case 3:
                // ������ʼλ��
                bkt_s = (int) (Math.random()*4);
                // ���Ž���λ��
                bkt_e = 0;
                // ������
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
                // �����������������
                checkbkt(bkt_s, bkt_e);
                exp.remove(exp.size()-1);	// ɾ����������һ�������
                break;
        }
        return exp;
    }

    // ���������
    public static String createnum() {
        int a = (int) (Math.random()*range*range);
        int b = (int) (Math.random()*range);
        num n = new num(a,b);
        easy(n);
        return n.tostring();
    }

    public static num easy(num n) {    // Լ��
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

    // ������������
    public static String createopr1() {
        return opr[(int) (Math.random()*4)];
    }

    // ֻ���� x �� ��
    public static String createopr2() {
        return opr[(int) (Math.random()*2 + 2)];
    }

    // ֻ���� + �� -
    public static String createopr3() {
        return opr[(int) (Math.random()*2)];
    }

    // �����������������
    public static void checkbkt(int bkt_s, int bkt_e) {
        if (bkt_e - bkt_s == 1) {
            if (bkt_s == 1) {
                String f = exp.get(2).toString();
                String s = exp.get(5).toString();
                if (!(f.equals("+") || (f.equals("-"))) && !(s.equals("x") || s.equals("��"))) {
                    exp.set(2, createopr3());
                    exp.set(5, createopr2());
                }
            } else if (bkt_s == 2) {
                String f = exp.get(1).toString();
                String s = exp.get(4).toString();
                if ((f.equals("+") || (f.equals("-"))) && (s.equals("x") || (s.equals("��")))) {	// �ų� a + ( b x c ) ������������������
                    exp.set(1, createopr2());
                }
            } else if (bkt_s == 3) {
                String f = exp.get(3).toString();
                String s = exp.get(6).toString();
                if ((f.equals("+") || (f.equals("-"))) && (s.equals("x") || s.equals("��"))) {
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
                if (!(t.equals("x") || t.equals("��"))) {
                    exp.set(7, createopr2());
                    if (f.equals(s) && (f.equals("+") || f.equals("��"))) {
                        exp.set(4, createopr2());
                    }
                }
            } else if (bkt_s == 2) {
                String f = exp.get(1).toString();
                String s = exp.get(4).toString();
                String t = exp.get(6).toString();
                if ((f.equals("+") || f.equals("-")) && (t.equals("x") || t.equals("��"))) {
                    exp.set(1, createopr2());
                }
            }
        }
    }

    // ת��Ϊ�ַ���
    static String toString(Stack s) {
        Iterator<Object> i = s.iterator();
        String st = "";
        while (i.hasNext()) {
            st += i.next().toString();
        }
        return st;
    }

    // ת��Ϊ�ַ���
    static String toString(ArrayList<Object> list) {
        String s = "";
        for (int i=0; i<list.size(); i++) {
            s += list.get(i).toString();
        }
        return s;
    }

    // ������ʽ
    public Stack<Node> count(ArrayList<String> list) {
//        ArrayList<Object> r = new ArrayList<Object>();  // �洢����������ʽ���
        Stack<Node> a = new Stack<>();  // ջ
        Stack<String> b = new Stack<>();
        String op;
        for (int i=0; i<list.size(); i++) {
            // ��ǰָ��Ϊ��ֵ
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
        } else if (op.equals("x") || op.equals("��")) {
            return 2;
        } else {
            return 3;
        }
    }

    public static boolean isop(String op) { // �ж��Ƿ�Ϊ�����
        if (op.equals("+") || op.equals("-")
                || op.equals("x") || op.equals("��")
                || op.equals("(") || op.equals(")")) {
            return true;
        } else  {
            return false;
        }
    }

    // ����Ƿ�����ͬ����Ŀ��������
    static void ifExist(List s) {

    }


 /* //����׺���ʽת���ɺ�׺���ʽ
    public static ArrayList transform(String prefix) {
        //System.out.println("transform");
        int i, len = prefix.length();
        prefix=prefix+ '#';
        Stack<Character> stack = new Stack<Character>();// �����������ջ
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
                default:// Ĭ�������:+ - * /
                    while (stack.peek() != '#'
                            && compare(stack.peek(), prefix.charAt(i))) {
                        postfix.add(stack.pop());// ���ϵ�ջ��ֱ����ǰ�Ĳ����������ȼ�����ջ��������
                    }
                    if (prefix.charAt(i) != '#') {// �����ǰ�Ĳ���������'#'(������)����ô�������ջ
                        stack.push(prefix.charAt(i));// ���ı�ʶ��'#'�ǲ���ջ��
                    }
                    break;
                }
            }
        }
        return postfix;
    }
    //�Ƚ������֮������ȼ�
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

    //�����׺���ʽ
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
        // ���ڶԸ�������Ŀ�ļ��ʹ��ļ��жϴ��ļ��еĶԴ�ͳ��
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
                    	   /*��Ҫ�õ�����Ľ���������������
                    	   ����ע�͵������Ҵӱ��˲���Copy������ش���*/
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
        // ���ڽ���Ŀ���뵱ǰĿ¼�µ�Exercises.txt�ļ�
        File question = new File("./Exercises.txt");
        if (!question.exists()) {
            System.out.println("�ļ������ڣ������ļ�: Exercises.txt" );
            try {
                question.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("�ļ��Ѵ��ڣ��ļ�Ϊ: Exercises.txt" );
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
        // ���ڽ���Ŀ�𰸴��뵱ǰĿ¼�µ�Answer.txt�ļ�
        File answer = new File("./Answer.txt");
        if (!answer.exists()) {
            System.out.println("�ļ������ڣ������ļ�: Answer.txt" );
            try {
                answer.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("�ļ��Ѵ��ڣ��ļ�Ϊ: Answer.txt" );
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