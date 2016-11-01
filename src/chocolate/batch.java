package chocolate;

/**
 *
 * @author Jinqiu Liu
 */
public class batch {
    static String alphabets = ".ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static int rotor = 0;
    static int r1 = 1, r2 = 1, r3 = 0;
    static int p1 = 1, p2 = 2, p3 = 3, p4 = 4, p5 = 5, p6 = 5;
    
    public static void main(String[] args) {
        int count = 0;
        String s;
        do {
        s = next3Plugs();        
            System.out.println(s);
            count++;
        } while (s != null);
        System.out.println(count);
    }
    
    public static void firstBatch() {
        String[] ss = {""};
        String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int seq = 0;
        for (int r = 111; r <= 888; r++) {
            if (r%10 == 9) { r+=2; }
            if ((r/10)%10 == 9) { r+= 20; }
                
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    for (int k = 0; k < 26; k++) {
                        ss[0] = "";
                        ss[0]+=r;  
                        ss[0]+=a.substring(i,i+1);
                        ss[0]+=a.substring(j,j+1);
                        ss[0]+=a.substring(k,k+1);
                        seq++;
                        //System.out.println(seq+"|"+ss);
                        ENIGMA.main(ss);
                    }
                }
            }
        }
    }
    
    public static String nextRotors() {
        while (rotor < 877) {
            rotor++;
            int c = rotor%10;
            int b = rotor/10%10;
            int a = rotor/100%10;
            if (b!=0 && b!=9 && c!=0 && c!=9 && a!=b && b!=c && a!=c) {
                return rotor+"";
            }
        }
        rotor = 122;
        return null;
    }
    
    public static String nextRing() {
        while (true) {
            r3++;
            if (r3 == 27) {
                r3 = 1;
                r2++;
            }
            if (r2 == 27) {
                r2 = 1;
                r1++;
            }
            if (r1 == 27) {
                r1 = 1;
                r2 = 1;
                r3 = 0;
                return null;
            }
            return alphabets.substring(r1,r1+1)
                    +alphabets.substring(r2,r2+1)
                    +alphabets.substring(r3,r3+1);
        }
    }
    
    public static String next3Plugs() {
        while (true) {
            p6++;
            if (p6 == 27) {
                p5++;
                p6 = p5 + 1;                
            }
            if (p5 == 26) {
                p4++;
                p5 = p4 + 1;
                p6 = p5 + 1;
            }
            if (p4 == 25) {
                p3++;
                p4 = p3 + 1;
                p5 = p4 + 1;
                p6 = p5 + 1;
            }
            if (p3 == 24) {
                p2++;
                p3 = p2 + 1;
                p4 = p3 + 1;
                p5 = p4 + 1;
                p6 = p5 + 1;
            }
            if (p2 == 23) {
                p1++;
                p2 = p1 + 1;
                p3 = p2 + 1;
                p4 = p3 + 1;
                p5 = p4 + 1;
                p6 = p5 + 1;
            }
            if (p1 == 22) {
                p1 = 1;
                p2 = 2;
                p3 = 3;
                p4 = 4;
                p5 = 5;
                p6 = 5;
                return null;
            }
            return alphabets.substring(p1,p1+1)
                    +alphabets.substring(p2,p2+1)
                    +alphabets.substring(p3,p3+1)
                    +alphabets.substring(p4,p4+1)
                    +alphabets.substring(p5,p5+1)
                    +alphabets.substring(p6,p6+1);
        }
    }
}
