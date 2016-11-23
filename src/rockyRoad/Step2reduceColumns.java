package rockyRoad;

import IO.READ;
import languageRecognition.Lang_rec;

/**
 * Based on results from 1st round (analyzeRingsAndRotors), generate round 2
 * actual plugboard settings, and corresponding decrypts for lang.rec filter
 * or I3C elimination.
 * @author Future
 */
public class Step2reduceColumns {
    static String alphabets = ".ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static int rotor = 464;
    static int r1 = 1, r2 = 1, r3 = 0;
    static String[] columns;
    static Lang_rec rec;
    
    public static void main(String[] args) {
        System.out.println("Initializing");
        rec = new Lang_rec();
        long startTime = System.currentTimeMillis();
        int completed = 0;
        while (true) {            
            String r = nextRotors();
            System.out.println("Calculating " + r);
            if (r == null) {
                break;
            }
            loadColumns(r);
            completed++;
            long currentTime = System.currentTimeMillis();
            //  336/completed = T/elapsed -> T = 336*e/c
            long remainingTime = (336*(currentTime-startTime)/completed)-(currentTime-startTime);
            int remSec = (int) (remainingTime/1000);
            int remMin = remSec / 60;
            remSec %= 60;
            int remHr = remMin / 60;
            remMin %= 60;
            System.out.println("Estimated remaining time: " + remHr + ":" + remMin + ":" + remSec);
        }        
    }
    
    /**
     * Read the 1stRound file and generates the 2ndRound file.
     * @param rotorSetting specify the rotor setting aka file name e.g. "123"
     */
    public static void loadColumns(String rotorSetting) {
        IO.READ.set("files/elimination/1stRound/" + rotorSetting + ".txt");
        IO.APPEND.set("files/elimination/2ndRound/" + rotorSetting + ".txt");
        String line = READ.getLine();
        String key = "";
        String[] worstCaseBigArray = new String[64]; //by previous analyze, no one has more than 64 columns
        int i = 0;
        while (line != null) {
            if (line.charAt(0) == rotorSetting.charAt(0)) {
                //new key                                 
                if (i > 0) { //flush
                    flush(worstCaseBigArray, i, key);
                    i = 0;
                }
                key = line;
            } else {
                //insert into column;
                worstCaseBigArray[i++] = line;
            }
            line = READ.getLine();            
        }
        flush(worstCaseBigArray, i, key);
        IO.READ.close();
        IO.APPEND.close();
    }
    
    public static void flush(String[] bigArray, int i, String key) {
        String[] shrunk = new String[i];
        System.arraycopy(bigArray, 0, shrunk, 0, i); //copy from bigarray to shrunk
        String[] lp = forceKDB.forceKey(key); //locked in pairs for KDB
        for (int p = 0; p < lp.length; p++) { //"recursively" call column reduction
            columnBunch temp = new columnBunch(shrunk, lp[p]);
            //IMPORTANT!! This is where it calls the columnBunch class and the real action happens
            temp.pairReduction();
            //temp.printAllCols();
            String[] validPairs = temp.generatePairs();
            if (validPairs != null) {
                //now that you have KEY and valid pairs, what next?
                for (int j = 0; j < validPairs.length; j++) {
                    String fullKey = key+validPairs[j];
                    String decrypt = ENIGMA.encrypt(fullKey, null, true);
                    if (decrypt.contains("KDB")) {
                        double likelihood = rec.lang_test_double(decrypt);
                        if (likelihood > .8) {
                            //output
                            IO.APPEND.println(decrypt + " " + fullKey + " " + likelihood);
                            System.out.println(decrypt + " " + fullKey + " " + likelihood);
                        }
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
}
