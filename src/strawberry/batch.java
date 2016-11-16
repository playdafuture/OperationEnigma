package strawberry;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import languageRecognition.Lang_rec;

/**
 *
 * @author Jinqiu Liu
 */
public class batch {
    static String alphabets = ".ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static int rotor = 122;
    static int r1 = 1, r2 = 1, r3 = 0;
    static int p1 = 1, p2 = 2, p3 = 3, p4 = 4, p5 = 5, p6 = 5;
    static String[] plugs; //total 325
    static String[] keys;
    static Lang_rec rec;
    
    public static void main(String[] args) {
        rec = new Lang_rec();
        loadPlugs();
        System.out.println("Loading keys...");
        loadFileKeys("files/highPriorityKeys.txt");
        System.out.println("Keys loaded, starting to try plugs");
        batchKeys(keys);
    }
    
    /**
     * Apply the tryPlugs method to a batch of keys, output valid results to console
     * @param keys The keys to try
     */
    public static void batchKeys(String[] keys) {
        for (int i = 0; i < keys.length; i++) {
            if (true) {
                System.out.println("Trying key#" + i + ": " + keys[i]);
            }
            String nextKey = tryPlugs(keys[i]);
//            while (nextKey != null) {
//                nextKey = tryPlugs(nextKey);
//            }
        }
    }
    
    /**
     * Load all keys from a specified file
     * @param fileName 
     */
    public static void loadFileKeys(String fileName) {
        try {
            BufferedReader inStream = new BufferedReader( new FileReader(fileName) );
            int lineCount = 0;
            String line = inStream.readLine();
            while (line != null) {
                line = inStream.readLine();
                lineCount++;
            }
            inStream.close();
            inStream = new BufferedReader( new FileReader(fileName) );
            keys = new String[lineCount];
            for (int i = 0; i < lineCount; i++) {
                line = inStream.readLine();
                keys[i] = line;
            }            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(batch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(batch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Loads ALL rings and rotor settings into the keys array to be tested
     */
    public static void loadAllKeys() {
        keys = new String[5905536];
        int i = 0;
        while (true) {
            String rot = nextRotors();
            if (rot != null) {                
                while (true) {
                    String ring = nextRings();
                    if (ring != null) {                        
                        keys[i++] = rot+ring;
                    } else {
                        break;
                    }                    
                }                
            } else {
                break;
            }
        }
    }
    
    /**
     * Given a cipher String, attempt to find out the correct plugboard settings.
     * @param key Partial key to work with
     * @return New key with the best plugs option appended
     */
    public static String tryPlugs(String key) {
        //System.out.println("Testing key " + key);
        if (key == null || key.length() > 10) {
            return null;
        }
        String[] ss = {key};
        String decrypt = ENIGMA.encrypt(key, null, true);
        double base = rec.lang_test_double(decrypt);
        double max = 0;
        String bestPlugs = "";
        for (int i = 0; i < 325; i++) {
            ss[0] = key+plugs[i];
            decrypt = ENIGMA.encrypt(key+plugs[i], null, true);
            double likelihood = rec.lang_test_double(decrypt);
            //System.out.println(plugs[i] + " " + likelihood);
            if (key.substring(6).contains(plugs[i].substring(0,1)) 
                    || key.substring(6).contains(plugs[i].substring(1,2))
                    || likelihood - base < 0) { //threashold is set to ANY positive increase
                //either repeating letter
                continue;
            } else {
                //recursively get more keys
                tryPlugs(ss[0]);
            }
            if (likelihood > 0.9) { //close enough to be english to be examined manually
                IO.APPEND.set("files/possibleDecrypt2.txt");
                IO.APPEND.print(key+bestPlugs + " " + max);
                IO.APPEND.println(decrypt);
                System.out.println(key+bestPlugs + " " + max);
                IO.APPEND.close();
            }
        }
        return key+bestPlugs;
    }
    
    /**
     * Load all possible plugBoard settings into the array for future use
     */
    public static void loadPlugs() {
        plugs = new String[325];
        char a = 'A';
        char b = 'B';
        int i = 0;
        while (true) {
            plugs[i++] = a+""+b;
            b++;
            if (b > 'Z') {
                a++;
                b = (char) (a+1);
                if (a == 'Z') {
                    return;
                }
            }
        }
    }
    
    /**
     * Create a series of files for all rings and rotor decrypts
     * based on their current likelihood to be English.
     */
    public static void distributedDecrypts() {
        rec = new Lang_rec();
        String path = "files/distributedDecrypts/";
        int[] distribution = new int[100];
        
        while (true) {
            String rot = nextRotors();
            if (rot != null) {                
                while (true) {
                    String ring = nextRings();
                    if (ring != null) {                        
                        String cipher = ENIGMA.encrypt(rot+ring, null, true);
                        String key = rot + ring;
                        double likelyhood = rec.lang_test_double(cipher)*100;
                        int roundDown = (int) likelyhood;
                        distribution[roundDown]++;
                        IO.APPEND.set(path+roundDown+".txt");
                        IO.APPEND.println(cipher + " " + key);
                        IO.APPEND.close();
                    } else {
                        break;
                    }                    
                }                
            } else {
                break;
            }
        }
        for (int i = 0; i < 100; i++) {
            if (i < 10) {
                System.out.print(" ");
            }
            System.out.println(i + " " + distribution[i]);
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
    
    public static String nextRnR() {
        String rotor = "";
        String ring;
        if (r3 == 0) {
            ring = nextRings();
        } else {
            ring = alphabets.substring(r1,r1+1)
                        +alphabets.substring(r2,r2+1)
                        +alphabets.substring(r3,r3+1);
        }
        rotor = nextRotors();
        if (rotor == null) {
            rotor = nextRotors();
            ring = nextRings();
        }
        if (ring == null) {
            return null;
        }
        return rotor+ring;
    }
}
