package strawberry;

import chocolate.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jinqiu Liu
 */
public class batch {
    static String alphabets = ".ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static int rotor = 122;
    static int r1 = 1, r2 = 1, r3 = 0;
    static int p1 = 1, p2 = 2, p3 = 3, p4 = 4, p5 = 5, p6 = 5;
    static String[] cribs;
    
    public static void main(String[] args) {
        try {
            cribsWordAttack(3);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(batch.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                        System.out.println(seq+"|"+ss[0]);
                        ENIGMA.reset();
                        System.out.println(ENIGMA.encrypt(ss));
                    }
                }
            }
        }
    }
    
    public static void secondBatch() throws FileNotFoundException, UnsupportedEncodingException {        
        while (true) {
            String rot = nextRotors();
            if (rot != null) {                
                PrintWriter writer = new PrintWriter("files/decrypts/"+rot+".txt", "UTF-8");
                while (true) {
                    String ring = nextRings();
                    if (ring != null) {                        
                        String[] ss = {rot+ring};
                        ENIGMA.reset();
                        writer.print(ENIGMA.encrypt(ss));
                        writer.println(ring);
                    } else {
                        writer.close();
                        break;
                    }                    
                }                
            } else {
                break;
            }
        }
    }
    
    public static void cribsWordAttack(int threshold) throws FileNotFoundException {
        loadCrib();
        PrintWriter writer = new PrintWriter("files/decrypts/crib"+threshold+".txt");
        while (true) {
            String rot = nextRotors();
            if (rot != null) {                   
                while (true) {
                    String ring = nextRings();
                    if (ring != null) {
                        String[] ss = {rot+ring};
                        ENIGMA.reset();
                        String decrypt = ENIGMA.encrypt(ss);                            
                        if (matchCribs(decrypt, threshold)) {
                            writer.print(decrypt);
                            writer.println(" "+rot+"-"+ring);
                            System.out.println(decrypt+" "+rot+"-"+ring);
                        }                            
                    } else {
                        break;                
                    }
                }
            } else {
                break;
            }            
        }
        writer.close();
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
    
    public static String nextRings() {
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

    private static boolean matchCribs(String decrypt, int threshold) {     
        int matchCount = 0;
        for (int i = 0; i < cribs.length; i++) {
            if (decrypt.contains(cribs[i])) {
                matchCount++;
            }
        }
        return matchCount >= threshold;
    }

    private static void loadCrib() {
        cribs = new String[49];
        try {
            BufferedReader inStream = new BufferedReader(new FileReader("filteredCrib.txt"));
            for (int i = 0; i < 49; i++) {
                String s = inStream.readLine();
                s = s.substring(s.indexOf(" ")+1); 
                cribs[i] = s;                          
            }  
        } catch (FileNotFoundException ex) {
            Logger.getLogger(batch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException iex) {
            Logger.getLogger(batch.class.getName()).log(Level.SEVERE, null, iex);
        }
    }
}
