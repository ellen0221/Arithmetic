import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Arithmetic {

    List e = new ArrayList<String>();   // 用于存放题目
    List a = new ArrayList<String>();   // 用于按题号顺序存放答案

    static void main(String[] argv) {
        // 获取参数
        int  num = 0;    // 题目数量
        int range = 0;   // 数值范围
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < argv.length; i++){
            sb. append(argv[i]);
        }
        String ag = sb.toString();
        if (!ag.contains("-r"))
        {   // 先判断是否有指定数值范围，没有则报错
            System.out.println("请指定数值范围！例： -r 10");
        }else
        {
            for (int i=0; i<argv.length; i++)
            {
                if (argv[i].equals("-r"))
                {
                    range = Integer.parseInt(argv[i+1]);
                }
                else if (argv[i].equals("-n"))
                {
                    num = Integer.parseInt(argv[i+1]);
                }
            }
        }
        subject(num, range);    // 生成题目
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
