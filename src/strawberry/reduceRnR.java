package strawberry;

import static strawberry.batch.nextRnR;

public class reduceRnR {
    public static void main(String[] args) {    
        while (true) {
            String key = nextRnR();
            if (key == null) {
                break;
            } else {
                System.out.println("Trying key " + key);
                forceKey(key);
            }
        }
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
        if (decrypt.length() != 67) {
            ENIGMA.reset();
            System.out.println(ENIGMA.cipherText.length());
            decrypt = ENIGMA.encrypt(key, null, true);
        }
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
        for (int i = 0; i < K.length; i++) {
            for (int j = 0; j < D.length; j++) {
                for (int k = 0; k < B.length; k++) {
                    candidates[count++] = K[i]+D[j]+B[k];
                }
            }
        }
        
        return candidates;
        
//        for (int i = 0; i < candidates.length; i++) {
//            String newKey = key+candidates[i];
//            String newDecrypt = ENIGMA.encrypt(key+candidates[i], null, true);
//            if (newDecrypt.substring(64).equals("KDB")) {
//                System.out.println(newKey);
//                System.out.println(newDecrypt);
//            }
//        }
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
}
