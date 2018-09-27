public class num {
    private int a;  // 分子
    private int b;  // 分母

    public num(String s) {  // 处理数值
        s = s.trim();

        int a,b;
        int x = s.indexOf("'");
        int y = s.indexOf("/");

        if (x != -1) {  // 代分数处理
            int c = Integer.parseInt(s.substring(0, x));    // 代分数的整数部分
            b = Integer.parseInt(s.substring(y+1));
            a = c * b + Integer.parseInt(s.substring(x+1,y));
        } else if (y != -1) {   // 分数处理
            a = Integer.parseInt(s.substring(0,y));
            b = Integer.parseInt(s.substring(y+1));
        } else {  // 整数处理
            a = Integer.parseInt(s);
            b = 1;
        }
        check(a,b);
    }

    public num(int a, int b) {
        check(a,b);
    }

    public void check(int a, int b) {
        if (b == 0) {
            System.out.println("分母不能为0");
        }
        if (a>0 && b>0) {
            this.a = a;
            this.b = b;
        }
    }

    public num add(num n) { // 加法 a + b
        return new num((this.b*n.a + this.a*n.b), this.b*n.b);
    }

    public num sub(num n) { // 减法 a - b
        return new num((this.a*n.b - this.b*n.a), this.b*n.b);
    }

    public num mul(num n) { // 乘法 a * b
        return new num(this.a*n.a, this.b*n.b);
    }

    public num div(num n) { // 除法 a / b
        return new num(this.a*n.b, this.b*n.a);
    }

    public int gcd(int a, int b) { // 求分数最小公约数
        if (a<b) {
            int x = b % a;
            return x == 0 ? a : gcd(a,x);
        } else if (a>b) {
            int y = a % b;
            return y == 0 ? b : gcd(b,y);
        } else return 1;
    }
}
