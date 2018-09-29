import ari.num;

public class num {
	public int a;  // ����
    public int b;  // ��ĸ

    public num() {

    }
    public num(String s) {  // ������ֵ
        s = s.trim();

        int a,b;
        int x = s.indexOf("'");
        int y = s.indexOf("/");

        if (x != -1) {  // ����������
            int c = Integer.parseInt(s.substring(0, x));    // ����������������
            b = Integer.parseInt(s.substring(y+1));
            a = c * b + Integer.parseInt(s.substring(x+1,y));
        } else if (y != -1) {   // ��������
            a = Integer.parseInt(s.substring(0,y));
            b = Integer.parseInt(s.substring(y+1));
        } else {  // ��������
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
            System.out.println("��ĸ����Ϊ0");
        }
        if (a>=0 && b>0) {
            this.a = a;
            this.b = b;
        }
    }

    public num add(num n) { // �ӷ� a + b
        return new num((this.b*n.a + this.a*n.b), this.b*n.b);
    }

    public num sub(num n) { // ���� a - b
        return new num((this.a*n.b - this.b*n.a), this.b*n.b);
    }

    public num mul(num n) { // �˷� a * b
        return new num(this.a*n.a, this.b*n.b);
    }

    public num div(num n) { // ���� a / b
        return new num(this.a*n.b, this.b*n.a);
    }

    public int gcd(int a, int b) { // ��������Լ��
        if (a<b) {
            int x = b % a;
            return x == 0 ? a : gcd(a,x);
        } else if (a>b) {
            int y = a % b;
            return y == 0 ? b : gcd(b,y);
        } else return 1;
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

    public String change(int n, int d) {
        // ���ٷ���ת��Ϊ���������һ������Ϊ���ӣ��ڶ���Ϊ��ĸ
        //�ٷ���ת��������� �� ���������temp+����/��ĸ
        num a = new num(n,d);
        easy(a);
        int left=a.a/a.b;
        int up=a.a-left*a.b;
        int down=a.b;
        if(up==0) {
        	return "0";
        }
        if(left==0) {
        	return up+"/"+down;
        }
        else if(down == 1) {
        	return Integer.toString(n);
        }else {
        	return left+"'"+up+"/"+down;
        }
    }
        
        


    public String tostring() {  // ת��Ϊ�ַ���
        if (this.a>this.b) {
            return this.change(a,b);
        } else if (this.b ==1){
            return Integer.toString(this.a);
        } else {
            return this.a + "/" + this.b;
        }
    }

}


