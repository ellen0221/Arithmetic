package ari;
import javax.security.auth.Subject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Arithmetic {

    List e = new ArrayList<String>();   // ���ڴ����Ŀ
    List a = new ArrayList<String>();   // ���ڰ����˳���Ŵ�
    static int  num = 10;    // ��Ŀ������Ĭ��Ϊ10��
    static int range = 0;   // ��ֵ��Χ
    static String error = null;    // ������Ϣ
    static String efile = "src" + File.separator;   // ��������Ŀ�ļ�
    static String afile = "src" + File.separator;   // �����Ĵ��ļ�

    public static void main(String[] argv) {
    	
    	/*String nume=change(25,15);
    	System.out.println(nume);
    	//���Լٷ���ת��Ϊ�����*/
    	
    	/*List<String> s = new ArrayList<String>();
		s.add("1+1=11");
		s.add("1+1=11");
		save(s);
		//���Դ���Ŀ*/
    	
    	/*List<String> a = new ArrayList<String>();
		a.add("1+1=11");
		a.add("1+1=11");
		answer(a);
		//���Դ��*/
    	
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
            } else if (argv[i].equals("-a")) {
                afile = afile + argv[i+1];
            }
        }
        if (able(num, range)) {
            subject(num, range);
        }else {
            System.out.println(error);
        }
    }

    static boolean able(int n, int r) {
        // ���ڼ���������Ŀ��������ֵ��Χ�Ƿ��ʵ��,���޸�
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

    static void subject(int n, int r) {
        // ����ָ��������Ŀ
        String[] e = {"+", "-", "x", "��"};

    }

    static void ifExist(List s) {
        // ���ڼ���Ƿ�����ͬ����Ŀ��������

    }
    
  /*//����׺���ʽת���ɺ�׺���ʽ
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


  static void check(File e, File a,File g) {
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

    static String change(int n, int d) {
        // ���ٷ���ת��Ϊ���������һ������Ϊ���ӣ��ڶ���Ϊ��ĸ
    	//�ٷ���ת��������� �� ���������temp+����/��ĸ
    	int temp=1,min=n;
        if(n%d==0){
        	return Integer.toString(n/d);
        }//���ӷ�ĸ����Ϊ0������Ȼ��
        if(d<min){
        	min=d;
        }	        
       for(int i=min;i>1;i--){
        	if(n%i==0&&d%i==0){
        		temp=i;
        		break;
        	}
        }//�����Լ��temp
        if(temp==1){
        	if(n<d){
        		return n+"/"+d;
        	}//�������С�ڷ�ĸ��ֱ�ӷ��ظ÷���
        	else{
        		return n/d+"'"+n%d+"/"+d;
        	}
        }//��ĸ���ڷ��ӷ��������
        else{
        	if(n<d){
		        return n/temp+"/"+d/temp;
        	}//�������Լ��
        	else{
        		return (n/temp)/(d/temp)+"'"+(n/temp)%(d/temp)+"/"+d/temp;
        	}//��������������Լ��
        }
    }

    static void save(List s) {
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
    	    		String t = i+1+". "+(String) s.get(i);
    	        bw.write( t);
    	        bw.newLine();
    	    }
    	    bw.close();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
        }
    

    static void answer(List a) {
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
    	    		String t =i+1+". "+ (String) a.get(i);
    	        bw.write( t);
    	        bw.newLine();
    	    }
    	    bw.close();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
        }
    }

/* ÿ�θ��´���֮�����ն����룺
 * git add .
 * git commit -m "(������ʲô����������)"
 * git push origin wen'min
 */

