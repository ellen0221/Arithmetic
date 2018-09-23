import javax.security.auth.Subject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Arithmetic {

    List e = new ArrayList<String>();   // 用于存放题目
    List a = new ArrayList<String>();   // 用于按题号顺序存放答案
    static int  num = 10;    // 题目数量，默认为10道
    static int range = 0;   // 数值范围
    static String error = null;    // 错误信息
    static String efile = "src" + File.separator;   // 给定的题目文件
    static String afile = "src" + File.separator;   // 给定的答案文件

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

    static void check(File e, File a) {
        // 用于对给定的题目文件和答案文件判断答案文件中的对错并统计

    }

    static String change(int n, int d) {
        // 将假分数转换为真分数，第一个参数为分子，第二个为分母

        return ;
    }

    static void save(List s) {
        // 用于将题目存入当前目录下的Exercises.txt文件

    }

    static void answer(List a) {
        // 用于将题目答案存入当前目录下的Answer.txt文件

    }

}
