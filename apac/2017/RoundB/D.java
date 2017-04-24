import java.io.PrintStream;
import java.util.*;

/**
 * APAC 2017 Round B Problem D: Sherlock and Permutation Sorting
 * Check README.md for explanation.
 */
public class Main {

    private long mode;
    private long[] prims;
    private long[] facts;

    /**
     * ��Ҫ�㷨��
     * ��prims[n]Ϊ����Ϊn�Ĳ����ٷָ�����и�����ǰ����Ϊ 0, 1, 1, 3, 13, ...)
     * ��prims[n]=n! - sigma_(i,0,n-1) {i!*prims[n-i]}
     * ��f[n, k]������Ϊn������౻�ָ�Ϊk������и���
     * ��f[n, 1]=prims[n]
     * f[n, k] = sigma_(i,0,n-1) {f[i, k-1]*prims[n-i]}
     * ����Ҫ����� ss[n] = sigma_(k,1,n) {k*k*f[n, k]}
     * ������s[n] = sigma_(k,1,n) {k*f[n, k]}
     * ��s[n] = sigma_(i,0,n-1) {(s[i]+i!)*prims[n-i]}
     * ����ss[n] = sigma_(i,0,n-1) {(ss[i]+2*s[i]+i!)*prims[n-i]}
     * ��Ϊ����O(n^2)
     */
    public String solve(Scanner scanner) {
        int n=scanner.nextInt();
        mode=scanner.nextLong();
        calFacts(n);
        calPrim(n);
        long[] s=new long[n+1], ss=new long[n+1];
        for (int i=1;i<=n;i++) {
            for (int j=0;j<=i-1;j++) {
                s[i]+=prims[i-j]*(facts[j]+s[j]);
                s[i]%=mode;
            }
        }
        for (int i=1;i<=n;i++) {
            for (int j=0;j<=i-1;j++) {
                ss[i]+=prims[i-j]*(ss[j]+2*s[j]+facts[j]);
                ss[i]%=mode;
            }
        }
        return String.valueOf(ss[n]);
    }

    private void calFacts(int n) {
        facts=new long[n+1];
        facts[0]=1;
        for (int i=1;i<=n;i++)
            facts[i]=facts[i-1]*i%mode;
    }

    private void calPrim(int n) {
        prims=new long[n+1];
        for (int i=1;i<=n;i++) {
            long u=0;
            for (int j=1;j<=i-1;j++) {
                u+=facts[j]*prims[i-j];
                u%=mode;
            }
            prims[i]=(facts[i]-u+mode)%mode;
        }
    }

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream("output.txt"));
        Scanner scanner=new Scanner(System.in);
        int times=scanner.nextInt();
        long start=System.currentTimeMillis();
        for (int t=1;t<=times;t++) {
            System.out.println(String.format("Case #%d: %s", t, new Main().solve(scanner)));
        }
        long end=System.currentTimeMillis();
        System.err.println(String.format("Time used: %.3fs", (end-start)/1000.));
    }
}
