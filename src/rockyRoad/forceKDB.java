package rockyRoad;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static rockyRoad.batch.nextRnR;

/**
 * Currently incomplete. 
 * Supposed to reduce certain ring and rotor settings based on letter distribution.
 * @author Future
 */
public class forceKDB {
    public static void main(String[] args) { //for testing stuff in here
        getPlugs("587ASE");
        
        
//        String key = nextRnR();
//        int[] pairLength = new int[8];
//        while (key != null) {
//            System.out.println(key);
//            String[] a = forceKey(key);
//            for (int i = 0; i < a.length; i++) {
//                pairLength[a[i].length()]++;
//            }
//            key = nextRnR();
//        }
//        for (int i = 0; i < pairLength.length; i++) {
//            System.out.println(i + " " + pairLength[i]);
//        }
    }
    
    public static void moreThanX(int x) {
        //IO.APPEND.set("files/hinted/allCut4.txt");
        int count = 0;
        String rnr;
        while (true) {
            rnr = batch.nextRnR();
            if (rnr == null) {
                break;
            }
            String decrypt = ENIGMA.encrypt(rnr, null, true);
            int[] alphabetCount = new int[26];
            boolean tooMuchFlag = false;
            for (int i = 0; i < decrypt.length(); i++) {
                int a = decrypt.charAt(i)-'A';
                alphabetCount[a]++;
                if (alphabetCount[a] > x) {
                    tooMuchFlag = true;
                    break;
                }
            }
            if (!tooMuchFlag) {
                //IO.APPEND.println(rnr);
                //IO.APPEND.println(decrypt);
                count++;
            }
        }
        System.out.println(count);
    }
    
    /**
     * Given a certain key, what plug board settings would force the last position to be KDB?
     * @param key Ring and Rotor portion of the key
     * @return The plugboard portion of keys that would work
     */
    public static String[] forceKey(String key) {
        String decrypt = ENIGMA.encrypt(key, null, true);
        //     cipher = CGKYTCJPXBMRRTQCQCVPYVYWVTCHEVQKCZNYXZULOPYWFCMLVPSOSYWZVDWOYAMCWMJ
        String dummy = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKDB";        
        String eKDB = ENIGMA.encrypt(key, dummy, true);
        eKDB = eKDB.substring(64);
        String eWMJ = decrypt.substring(64);
        String[] K = {};
        String[] D = {};
        String[] B = {};
        
        if (eKDB.charAt(0) == 'W') {
            //W encrypts to K, either W and K both not affected,            1
            //                 or W and K each connects to a cipher pair    13
            K = new String[14];
            K[0] = "";
            String[] allPairs = calcAllPairs(key, 65);
            for (int i = 0; i < 13; i++) {
                K[i+1] = allPairs[i];
            }
        } else if (eKDB.charAt(0) != 'W') {
            //Either E(W) = P(K) or P(W) = E(K)                             2
            K = new String[2];
            K[0] = eWMJ.charAt(0)+"K";
            K[1] = eKDB.charAt(0)+"W";
        }
        
        if (eKDB.charAt(0) == 'M') {
            D = new String[14];
            D[0] = "";
            String[] allPairs = calcAllPairs(key, 66);
            for (int i = 0; i < 13; i++) {
                D[i+1] = allPairs[i];
            }
        } else if (eKDB.charAt(0) != 'M') {                          
            D = new String[2];
            D[0] = eWMJ.charAt(1)+"D";
            D[1] = eKDB.charAt(1)+"M";
        }
        
        if (eKDB.charAt(0) == 'J') {
            B = new String[14];
            B[0] = "";
            String[] allPairs = calcAllPairs(key, 67);
            for (int i = 0; i < 13; i++) {
                B[i+1] = allPairs[i];
            }
        } else if (eKDB.charAt(0) != 'J') {                     
            B = new String[2];
            B[0] = eWMJ.charAt(2)+"B";
            B[1] = eKDB.charAt(2)+"J";            
        }
        
        String[] candidates = new String[K.length*D.length*B.length];
        
        //fill in all candidates by the cartisian product
        int count = 0;
        int validCount = 0;
        for (int i = 0; i < K.length; i++) {
            for (int j = 0; j < D.length; j++) {
                for (int k = 0; k < B.length; k++) {
                    if (isValidPairing(K[i]+D[j]+B[k])) {
                        candidates[count++] = K[i]+D[j]+B[k];
                        validCount++;
                    } else {
                        candidates[count++] = null;
                    }                    
                }
            }
        }
        
        //eliminate invalid parings        
        String[] results = new String[validCount];
        validCount = 0;
        for (int i = 0; i < candidates.length; i++) {
            if (candidates[i] != null) {
                results[validCount++] = candidates[i];
            }
        }
        
        return results;
    }
    
    public static String[] getPlugs (String key) {
        String std = ENIGMA.encrypt(key, null, true);
        String[] e = {""}; 
        String[] k = calcAllPairs(key, 64);
        String eK = "", eW = "";
        for (int i = 0; i < 13; i++) {
            if (k[i].contains("K")) {
                eK = k[i].charAt((k[i].indexOf("K")+1)%2)+"";
            }
            if (k[i].contains("W")) {
                eW = k[i].charAt((k[i].indexOf("W")+1)%2)+"";
            }
        }        
        String[] k1 = {"K"+eW, "W"+eK}; //K-E(W), W-E(K)
        if (eW.equals("K")) {
            k1 = union(k1,e);
        }
        String[] k2 = new String[26];
        for (int i = 0; i < 13; i++) {
            k2[i] = "W"+k[i]+"K";
        }
        for (int i = 13; i < 26; i++) {
            k2[i] = "K"+k[i-13]+"W";
        }
        
        String[] d = calcAllPairs(key, 65);
        String eD = "", eM = "";
        for (int i = 0; i < 13; i++) {
            if (d[i].contains("D")) {
                eD = d[i].charAt((d[i].indexOf("D")+1)%2)+"";
            }
            if (d[i].contains("M")) {
                eM = d[i].charAt((d[i].indexOf("M")+1)%2)+"";
            }
        }
        String[] d1 = {"M"+eD, "D"+eM}; //D-E(M), M-E(D)
        if (eD.equals("M")) {
            d1 = union(d1,e);
        }
        String[] d2 = new String[26];
        for (int i = 0; i < 13; i++) {
            d2[i] = "M"+d[i]+"D";
        }
        for (int i = 13; i < 26; i++) {
            d2[i] = "D"+d[i-13]+"M";
        }
        
        String[] b = calcAllPairs(key, 66);
        String eB = "", eJ = "";
        for (int i = 0; i < 13; i++) {
            if (b[i].contains("B")) {
                eB = b[i].charAt((b[i].indexOf("B")+1)%2)+"";
            }
            if (b[i].contains("J")) {
                eJ = b[i].charAt((b[i].indexOf("J")+1)%2)+"";
            }
        }
        
        String[] b1 = {"J"+eB, "B"+eJ}; //J-E(B), B-E(J)
        if (eB.equals("J")) {
            b1 = union(b1,e);
        }
        String[] b2 = new String[26];
        for (int i = 0; i < 13; i++) {
            b2[i] = "J"+b[i]+"B";
        }
        for (int i = 13; i < 26; i++) {
            b2[i] = "B"+b[i-13]+"J";
        }
        
        String[] wombo = product(union(k1,k2),union(d1,d2),union(b1,b2));
        String[] buffup = {};
        for (int i = 0; i < wombo.length; i++) {
            wombo[i] = verify(wombo[i]);
            if (wombo[i] != null && wombo[i].length() == 6) {
                buffup = union(buffup,freePair(wombo[i]));
            } else if (wombo[i] != null && wombo[i].length() < 6) {
                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter("files/KDB/skippedKeys.txt",true));
                    out.append(key);
                    out.newLine();
                    out.close();
                    return null;
                } catch (IOException ex) {
                    Logger.getLogger(forceKDB.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }
        }
        wombo = cleanup(wombo);
        return union(wombo,buffup);
    }
    
    public static String[] union(String[] a, String[] b) {
        String[] union = new String[a.length+b.length];
        int i = 0;
        for (int al = 0; al < a.length; al++) {
            union[i++] = a[al];
        }       
        for (int bl = 0; bl < b.length; bl++) {
            union[i++] = b[bl];
        }
        return union;
    }
    
    public static String[] freePair(String p) {
        if (p.length() == 6) {
            int idx = 0;
            String[] fp = new String[190];
            for (char a = 'A'; a <= 'Z'; a++) {
                for (char b = 'A'; b <= 'Z'; b++) {
                    if (b <= a) {continue;}
                    if (p.contains(a+"") || p.contains(b+"")) {continue;}
                    fp[idx++] = p+a+""+b;
                }
            }
            return fp;
        } else if (p.length() == 4) {
            int idx = 0;
            String[] fp = new String[29070];
            for (char a = 'A'; a <= 'Z'; a++) {
                for (char b = 'A'; b <= 'Z'; b++) {
                    if (b <= a) {continue;}
                    for (char c = 'A'; c <= 'Z'; c++) {
                        if (c <= a) {continue;}
                        for (char d = 'A'; d <= 'Z'; d++) {
                            if (d <= c || d == b) {continue;}
                            if (p.contains(a+"") || p.contains(b+"") || p.contains(c+"") || p.contains(d+"")) {continue;}
                            fp[idx++] = p+a+""+b+""+c+""+d;                            
                        }
                    }                    
                }
            }
            return cleanup(fp);
        } else if (p.length() == 2) {
            int idx = 0;
            String[] fp = new String[3488400];
            for (char a = 'A'; a <= 'Z'; a++) {
                for (char b = 'A'; b <= 'Z'; b++) {
                    if (b <= a) {continue;}
                    for (char c = 'A'; c <= 'Z'; c++) {
                        if (c <= a) {continue;}
                        for (char d = 'A'; d <= 'Z'; d++) {
                            if (d <= c || d == b) {continue;}
                            for (char e = 'A'; e <= 'Z'; e++) {
                                if (e <= c) {continue;}
                                for (char f = 'A'; f <= 'Z'; f++) {
                                    if (f <= e || f == d || f == b) {continue;}
                                    if (p.contains(a+"") || p.contains(b+"") 
                                            || p.contains(c+"") || p.contains(d+"")
                                            || p.contains(e+"") || p.contains(f+"")
                                            ) {continue;}
                                    fp[idx++] = p+a+""+b+""+c+""+d+""+e+""+f;                                    
                                }
                            }                                                   
                        }
                    }                    
                }
            }
            return cleanup(fp);
        } else {
            System.out.println("LENGTH ZERO!?!?!");
        }
        return null;
    }
    
    public static String[] union(String[] a, String[] b, String[] c) {
        String[] union = new String[a.length+b.length+c.length];
        int i = 0;
        for (int al = 0; al < a.length; al++) {
            union[i++] = a[al];
        }       
        for (int bl = 0; bl < b.length; bl++) {
            union[i++] = b[bl];
        }
        for (int cl = 0; cl < c.length; cl++) {
            union[i++] = c[cl];
        }
        return union;
    }
    
    /**
     * Creates a Cartesian product of 3 arrays.
     * @param a
     * @param b
     * @param c
     * @return 
     */
    public static String[] product(String[] a, String[] b, String[] c) {
        String[] product = new String[a.length*b.length*c.length];
        int i = 0;
        for (int al = 0; al < a.length; al++) {
            for (int bl = 0; bl < b.length; bl++) {
                for (int cl = 0; cl < c.length; cl++) {
                    product[i++] = a[al]+b[bl]+c[cl];
                }
            }
        }        
        return product;
    }
    
    public static String[] cleanup(String[] s) {
        int validCount = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] != null) {
                validCount++;
            }
        }
        String[] newS = new String[validCount];
        int idx = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] != null) {
                newS[idx++] = s[i];
            }
        }
        return newS;
    }
    
    /**
     * Given a key and a specific position, calculate all 13 encrypt-decrypt pairs.
     * @param key
     * @param pos
     * @return 13 pairs as a String array
     */
    public static String[] calcAllPairs(String key, int pos) {
        String filler = "";
        for (int i = 0; i < pos; i++) {
            filler += "A";
        }
        
        String[] pairs = new String[13];
        int c = 0;
        for (int i = 0; i < 26; i++) {
            String p1 = (char)('A'+i)+"";
            String c1 = ENIGMA.encrypt(key, filler+p1, true).substring(pos);
            if (p1.charAt(0) < c1.charAt(0)) {
                pairs[c++] = p1+c1;
            }
        }        
        return pairs;
    }
    
    /**
     * Checks for repeated characters to determine if it's a valid "pairing setting".
     * @param p The pairing string you want to check
     * @return True if it's valid and false otherwise
     */
    public static boolean isValidPairing(String p) {
        for (int i = 1; i < p.length(); i++) {
            if (p.substring(0, i).contains((char)p.charAt(i)+"")) {
                return false;
            }
        }        
        return true;
    }
    
    /**
     * Checks on a key pairings, return in descending order format or null if it's invalid.
     * @param p
     * @return 
     */
    public static String verify(String p) {
        if (p == null) {
            return null;
        }
        for (int i = 1; i < p.length(); i++) {
            for (int f = 0; f < i; f++) {                
                if (p.charAt(f) == p.charAt(i)) {
                    //KMMKBA KMKMBA KMOMBA
                    //012345 012345 012345
                    // fi    f i     f i
                    if (i%2 == 1 && f%2 == 1) {
                        if (p.charAt(i-1) == p.charAt(f-1)) {
                            //remove i and i-1 from p
                            p = p.substring(0, i-1) + p.substring(i+1);
                            return verify(p);
                        } else {
                            return null;
                        }
                    } else if (i%2 == 0 && f%2 == 1) {
                        if (p.charAt(i+1) == p.charAt(f-1)) {
                            p = p.substring(0, i) + p.substring(i+2);
                            return verify(p);
                        } else {
                            return null;
                        }
                    } else if (i%2 == 1 && f%2 == 0) {
                        //KM?KBA
                        //f  i
                        if (p.charAt(f+1) == p.charAt(i-1)) {
                            p = p.substring(0, i-1) + p.substring(i+1);
                            return verify(p);
                        } else {
                            return null;
                        }
                    } else {
                        //KMK?BA
                        //f i
                        if (p.charAt(f+1) == p.charAt(i+1)) {
                            p = p.substring(0, i) + p.substring(i+2);
                            return verify(p);
                        } else {
                            return null;
                        }
                    }
                }
            }
        }
        if (p.length() <= 8) {
            return p;
        } else {
            return null;
        }        
    }
}
