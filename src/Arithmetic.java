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

    List e = new ArrayList<String>();   // 用于存放题目
    List a = new ArrayList<String>();   // 用于按题号顺序存放答案
    static int  num = 10;    // 题目数量，默认为10道
    static int range = 0;   // 数值范围
    static String error = null;    // 错误信息
    static String efile = "src" + File.separator;   // 给定的题目文件
    static String afile = "src" + File.separator;   // 给定的答案文件

    public static void main(String[] argv) {
    	
    	/*String nume=change(25,15);
    	System.out.println(nume);
    	//测试假分数转换为真分数*/
    	
    	/*List<String> s = new ArrayList<String>();
		s.add("1+1=11");
		s.add("1+1=11");
		save(s);
		//测试存题目*/
    	
    	/*List<String> a = new ArrayList<String>();
		a.add("1+1=11");
		a.add("1+1=11");
		answer(a);
		//测试存答案*/
    	
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
        // 用于检查输入的题目数量与数值范围是否可实现,并修改
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

    static void subject(int n, int r) {
        // 生成指定数量题目
        String[] e = {"+", "-", "x", "÷"};

    }

    static void ifExist(List s) {
        // 用于检查是否有相同的题目，并处理

    }
    
  /*//将中缀表达式转换成后缀表达式
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


  static void check(File e, File a,File g) {
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

    static String change(int n, int d) {
        // 将假分数转换为真分数，第一个参数为分子，第二个为分母
    	//假分数转真分数方法 求 最大整除数temp+余数/分母
    	int temp=1,min=n;
        if(n%d==0){
        	return Integer.toString(n/d);
        }//分子分母余数为0返回自然数
        if(d<min){
        	min=d;
        }	        
       for(int i=min;i>1;i--){
        	if(n%i==0&&d%i==0){
        		temp=i;
        		break;
        	}
        }//求最大公约数temp
        if(temp==1){
        	if(n<d){
        		return n+"/"+d;
        	}//如果分子小于分母，直接返回该分数
        	else{
        		return n/d+"'"+n%d+"/"+d;
        	}
        }//分母大于分子返回真分数
        else{
        	if(n<d){
		        return n/temp+"/"+d/temp;
        	}//化简最大公约数
        	else{
        		return (n/temp)/(d/temp)+"'"+(n/temp)%(d/temp)+"/"+d/temp;
        	}//化简真分数的最大公约数
        }
    }

    static void save(List s) {
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

/* 每次更新代码之后在终端输入：
 * git add .
 * git commit -m "(更新了什么，进行描述)"
 * git push origin wen'min
 */

