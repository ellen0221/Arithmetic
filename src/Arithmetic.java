
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Arithmetic {

    static List<List<String>> e = new ArrayList<List<String>>();   // ���ڴ����Ŀ
    static List<Stack<Node>> e1 = new ArrayList<Stack<Node>>();
    static List<String> a = new ArrayList<String>();   // ���ڰ����˳���Ŵ�
    static String[] opr = {"+", "-", "x", "��"};
    static List<String> exp = new ArrayList<String>();	// ���ʽ
    static int  num = 10;    // ��Ŀ������Ĭ��Ϊ10��
    static int range = 0;   // ��ֵ��Χ
    static String error = null;    // ������Ϣ
    static String efile = null;   // ��������Ŀ�ļ���Exercise.txt
    static String afile = null;   // �����Ĵ��ļ���Answer.txt
    static String gfile = "src" + File.separator;   // ���ͳ���ļ���Grade.txt

    public static void main(String[] args) {
        // ���ݹ��ܴ������
        for (int i=0; i<args.length; i++) {
            if (args[i].equals("-r")) {
                range = Integer.parseInt(args[i+1]);
            } else if (args[i].equals("-n"))
            {
                num = Integer.parseInt(args[i+1]);
            } else if (args[i].equals("-h")) {  // ������Ϣ
                System.out.println("-r ����: ������Ŀ����ֵ����Ȼ������������������ĸ���ķ�Χ \n����: Myapp.exe -r 10 ������10����(������10)������������Ŀ");
                System.out.println("-n ����: ����������Ŀ�ĸ��� \n����: Myapp.exe -n 10  ������10����Ŀ");
                System.out.println("֧�ֶԸ�������Ŀ�ļ��ʹ��ļ����ж����еĶԴ���������ͳ�� �����������:\nMyapp.exe -e <exercisefile>.txt -a <answerfile>.txt");
            } else if (args[i].equals("-e")) {
                efile = "./src" + File.separator + args[i+1];
                range = 10; // ��ֹ����
            } else if (args[i].equals("-a")) {
                afile = "./src" + File.separator + args[i+1];
                range = 10;
            }
        }
        if ((efile == null && afile != null) || (efile != null && afile == null)) {
            error = "�����������������Ŀ�ļ��ʹ��ļ����������";
        }
        if (able(num, range) && efile == null && afile == null) {
            for (int i=0; i<num; i++) {
                subject();
                String an = count(e.get(i));
                a.add(an);
            }
            save(e);
            answer(a);
        } else if (efile != null && afile != null){
            check(new File(efile), new File(afile), new File(gfile));
        } else {
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

    // ���ɱ��ʽ����������Ŀ�б�
    static void subject() {
        // ��������� 1-3 ��
        List<String> exp = new ArrayList<String>();
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
                checkbkt(bkt_s, bkt_e,exp);
                exp.remove(exp.size()-1);	// ɾ����������һ�������
                break;
            case 3:
                // ������ʼλ��
                bkt_s = (int) (Math.random()*4);
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
                checkbkt(bkt_s, bkt_e,exp);
                exp.remove(exp.size()-1);	// ɾ����������һ�������
                break;
        }
        e.add(exp);
        System.out.println(toString(exp));
        //  exp.clear();
    }

    // ���������
    public static String createnum() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int a = rand.nextInt(range);
        int b = rand.nextInt(range);
        if (a == 0) {
            a += (int) (Math.random()*range + 1);
        }
        if (b == 0) {
            b += (int) (Math.random()*range + 1);
        }
        if (b == range) {
            b = b-1;
        }
        num n = new num(a,b);
        easy(n);
        if (n.a == n.b) {
            return Integer.toString(1);
        }
        if (n.b == 1) {
            return Integer.toString(n.a);
        }
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
    public static void checkbkt(int bkt_s, int bkt_e,List<String>exp) {
        String f;
        String s;
        String t;
        if (bkt_e - bkt_s == 1) {
            if (bkt_s == 1) {
                f = exp.get(2);
                s = exp.get(5);
                if (!(f.equals("+") || (f.equals("-"))) && !(s.equals("x") || s.equals("��"))) {
                    exp.set(2, createopr3());
                    exp.set(5, createopr2());
                }
            } else if (bkt_s == 2) {
                f = exp.get(1);
                s = exp.get(4);
                if (exp.size()<8) {
                    if ((f.equals("+") || (f.equals("-"))) && (s.equals("x") || (s.equals("��")))) {	// �ų� a + ( b x c ) ������������������
                        exp.set(1, createopr2());
                    }
                }
            } else if (bkt_s == 3) {
                f = exp.get(3);
                s = exp.get(6);
                if ((f.equals("+") || (f.equals("-"))) && (s.equals("x") || s.equals("��"))) {
                    exp.set(3, createopr2());
                } else if (((f.equals("+") || f.equals("-"))) && (s.equals("+") || s.equals("-"))) {
                    exp.set(3, createopr2());
                }
            }
        } else {
            if (bkt_s == 1) {
                f = exp.get(2);
                s = exp.get(4);
                t = exp.get(7);
                if (!(t.equals("x") || t.equals("��"))) {
                    exp.set(7, createopr2());
                    if (f.equals(s) && (f.equals("+") || f.equals("��"))) {
                        exp.set(4, createopr2());
                    }
                }
            } else if (bkt_s == 2) {
                f = exp.get(1);
                s = exp.get(4);
                t = exp.get(6);
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
    static String toString(List<String> list) {
        String s = "";
        for (int i=0; i<list.size(); i++) {
            s += (list.get(i).toString() + " ");
        }
        return s;
    }

    // ������������ʽ����������Ŀ�б�
    public static String count(List<String> list) {
        Stack<Node> a1 = new Stack<>();    // ��ֵջ
        Stack<String> b = new Stack<>();    // δ����������ջ
//        Stack<Node> c = new Stack<>();    // �����������ջ
        for (int i=0; i<list.size(); i++) {
            // ��ǰָ��Ϊ��ֵ
            String string = list.get(i);
            if (!isop(string)) {
                a1.push(new Node(string, null, null, null));
            } else {
                //�ȽϷ���ջ�еĶ�����������Ҫ��ջ
                while (!b.isEmpty() && !(string.equals("(") || (prefer(string)==2 && prefer(b.peek())==1) ||
                        (!string.equals(")") && b.peek().equals("(")))) {
                    String symbol = b.pop();

                    if (symbol.equals("(") && string.equals(")")) {
                        break;
                    }
                    push(symbol, a1);

                }
                //������Ų���")"�ͽ�ջ
                if (!string.equals(")")) {
                    b.push(string);
                }
            }
        }

        while (!b.isEmpty()) {
            push(b.pop(), a1);
        }
        negative(a1);
//        a.add(a1.peek().result);
        return a1.peek().result;
    }

    // ������
    public static void negative(Stack<Node> c) {
        if (!c.isEmpty()) {
            for (int i=0; i<c.size(); i++) {
                Node n = c.get(i);
                if (n.op.equals("-")) {
                    num l = new num(n.left.result);
                    num r = new num(n.right.result);
                    if (l.a*r.b < r.a*l.b) {
                        Node m = n.left;
                        n.left = n.right;
                        n.right = m;
                        n.setResult();
                    }
                }
            }
        }
    }

    public static void push(String op, Stack<Node> a) {
        if (!op.equals("(")) {
            Node r = a.pop();
            Node l = a.pop();
            Node o = new Node(null, r, l, op);
            o.result = count(r.result, l.result, op);
            a.push(o);
        }
    }

    public static String count(String r, String l, String op) {
        num left = new num(l);
        num right = new num(r);
        switch (op) {
            case "+":
                return left.add(right).tostring();
            case "-":
                return left.sub(right).tostring();
            case "x":
                return left.mul(right).tostring();
            case "��":
                return left.div(right).tostring();
        }
        return null;
    }

    static class Node {
        String result;
        Node right;
        Node left;
        String op;

        public Node(String result, Node right, Node left, String op) {
            this.result = result;
            this.right = right;
            this.left = left;
            this.op = op;
        }

        public void setResult() {
            if (op!=null) {
                result = count(right.result, left.result, op);
            }
        }

        public void changelr() {
            Node m = left;
            right = left;
            left = m;
        }

    }

    public static int prefer(String op) {  // �ж���������ȼ�
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

    // �����Ŀ�б����Ƿ�����ͬ����Ŀ��������
    static void ifExist(ArrayList<String> s) {
        for (int i=0; i<s.size(); i++) {
            for (int j=i+1; j<s.size();) {
                Stack<Node> f = e1.get(i);
                Stack<Node> se = e1.get(j);
                if (f.size() == se.size() && equals(f.pop(), se.pop())) {
                    s.remove(j);
                } else {
                    j++;
                }
            }
        }
    }

    static boolean equals(Node f, Node s) {

        if (fullequals(f, s)) {
            return true;
        } else if (f.op.equals(s.op) && f.result.equals(s.result)){
            if (change(f, s)) {
                return true;
            } else {
                return equals(f.left, s.left) && equals(f.right, s.right);
            }
        }
        return false;
    }

    static boolean change(Node f, Node s) {
        if (f.op.equals("+") || f.op.equals("x")) {
            f.changelr();
            f.setResult();
            if (nodeequal(f.left, s.left) && nodeequal(f.right, s.right)) {
                return true;
            }
        }
        return false;
    }

    static boolean nodeequal(Node f, Node s) {
        return f.op.equals(s.left.op) && f.result.equals(s.left.result);
    }

    static boolean fullequals(Node f, Node s) {
        if (f.op.equals(s.op) && f.result.equals(s.result)) {
            return fullequals(f.left, s.left) && fullequals(f.right, s.right);
        }
        return false;
    }

    static void check(File e, File a, File g) {
        // ���ڶԸ�������Ŀ�ļ��ʹ��ļ��жϴ��ļ��еĶԴ�ͳ��
        File g1 = new File("./src/Grade.txt");
        if (!g1.exists()){
            try {
                g1.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try (BufferedReader exReader = new BufferedReader(new FileReader(e));
             BufferedReader anReader = new BufferedReader(new FileReader(a));
             BufferedWriter gradeWriter = new BufferedWriter(new FileWriter(g1))
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

                    String[] exp = expression.split(" ");
                    List<String> al = new ArrayList<String>();
                    al = Arrays.asList(exp);
                    String realanswer = count(al);
                    String answer = an.substring(anPoint + 2);
                    if (realanswer.equals(answer.toString())) {
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

    static void save(List<List<String>> s) {
        // ���ڽ���Ŀ���뵱ǰĿ¼�µ�Exercises.txt�ļ�
        File question = new File("./src/Exercises.txt");
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
            fw=new FileWriter(question);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i=0;i<s.size();i++) {
                String temp="";
                for(int j=0;j<s.get(i).size();j++) {
                    temp+=s.get(i).get(j)+" ";
                }
                String t = (i + 1) + ". " + temp;
                bw.write(t);
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void answer(List<String> a) {
        // ���ڽ���Ŀ�𰸴��뵱ǰĿ¼�µ�Answer.txt�ļ�
        File answer = new File("./src/Answer.txt");
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
                bw.write(t);
                bw.newLine();
                bw.flush();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
