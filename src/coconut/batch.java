package coconut;

import languageRecognition.Lang_rec;
import rockyRoad.forceKDB;

/**
 *
 * @author Jinqiu Liu
 */
public class batch {
   
    public static void main(String[] args) {
        String testKey = "587ASE";
        String[] bf = IO.LOAD.readFile("files/4pbf.txt");
        System.out.println("Done bf");
        String[] gen = forceKDB.getPlugs(testKey);
        //see if bf is a subset of gen
        System.out.println("Bruteforce results");
        for (int i = 0; i < bf.length; i++) {
            String decrypt = ENIGMA.encrypt(testKey + bf[i], null, true);
            if (!decrypt.substring(64).equals("KDB")) {
                bf[i] = null;
            } else {
                System.out.println(bf[i]);
            }
        }
        bf = forceKDB.cleanup(bf);
        System.out.println("Bruteforce results");

        System.out.println();
        System.out.println("Differences");
        for (int i = 0; i < bf.length; i++) { //for every element in bf
            String bfi = bf[i];
            System.out.println(i + "/" + bf.length);
            for (int j = 0; j < gen.length; j++) { //try everything from gen to eliminate
                if (ENIGMA.encrypt(testKey+bfi, null, true).equals(ENIGMA.encrypt(testKey+gen[j], null, true))) {
                    bf[i] = null;
                }
            }
            if (bf[i] != null) {
                System.out.println(bf[i]);
            }
        }       
    }
    
    public static String[] set3Plugs() {
        int count = 0;
        String[] set = new String[3453450];
        for (char a = 'A'; a <= 'Z'; a++) {
            for (char b = 'A'; b <= 'Z'; b++) {
                if (b <= a) {continue; }
                for (char c = 'A'; c <= 'Z'; c++) {
                    if (c <= a) { continue; }
                    for (char d = 'A'; d <= 'Z'; d++) {
                        if (d <= c) {continue;}
                        for (char e = 'A'; e <= 'Z'; e++) {
                            if (e <= c) { continue; }
                            for (char f = 'A'; f <= 'Z'; f++) {
                                if (f <= e) {continue;}
                                String p = a+""+b+""+c+""+d+""+e+""+f;
                                if (isValidPairing(p)) {
                                    set[count++] = p;
                                }
                            }
                        }
                    }
                }
            }
        }
        return set;
    }
    
    public static String[] set4Plugs() {
        int count = 0;
        String[] set = new String[164038875];
        for (char h1 = 'A'; h1 <= 'Z'; h1++) {
            for (char t1 = 'A'; t1 <= 'Z'; t1++) {
                if (t1 <= h1) {continue; }
                for (char h2 = 'A'; h2 <= 'Z'; h2++) {
                    if (h2 <= h1) { continue; }
                    for (char t2 = 'A'; t2 <= 'Z'; t2++) {
                        if (t2 <= h2) {continue;}
                        for (char h3 = 'A'; h3 <= 'Z'; h3++) {
                            if (h3 <= h2) { continue; }
                            for (char t3 = 'A'; t3 <= 'Z'; t3++) {
                                if (t3 <= h3) {continue;}
                                for (char h4 = 'A'; h4 <= 'Z'; h4++) {
                                    if (h4 <= h3) { continue;}
                                    for (char t4 = 'A'; t4 <= 'Z'; t4++) {
                                        if (t4 <= h4) { continue; }
                                        String p = h1+""+t1+""+h2+""+t2+""+h3+""+t3+""+h4+""+t4;
                                        if (isValidPairing(p)) {
                                            set[count++] = p;                                            
                                        }
                                    }
                                }                                
                            }
                        }
                    }
                }
            }
        }
        return set;
    }
    
    public static void hope() {
        //Lang_rec rec = new Lang_rec();
        //IO.APPEND.set("files/587ASE/hope2.txt");
        System.out.println("Loading 3pair plugs");
        String[] p3 = IO.LOAD.readFile("files/3plugs.txt");
        //System.out.println("Loading 4pair plugs");
        //String[] p4 = IO.LOAD.readFile("files/4plugs.txt");
        System.out.println("Loading complete");
        for (int i = 0; i < p3.length; i++) {
            String plugs = p3[i];
            String key = "587ASE" + plugs;
            String decrypt = ENIGMA.encrypt(key, null, true);
            if (decrypt.substring(64).equals("KDB")) {
                System.out.println(decrypt + " " + key);
            }
//            double likelihood = rec.lang_test_double(decrypt);
//            if (likelihood > .85) {
//                IO.APPEND.println(decrypt+ " " + key + " " + likelihood);
//            }
        }
//        for (int i = 0; i < p4.length; i++) {
//            String plugs = p4[i];
//            String key = "587ASE" + plugs;
//            String decrypt = ENIGMA.encrypt(key, null, true);
//            double likelihood = rec.lang_test_double(decrypt);
//            if (likelihood > .85) {
//                IO.APPEND.println(decrypt+ " " + key + " " + likelihood);
//            }
//        }
        //IO.APPEND.close();
    }
    
    public static String[] test4Plugs(String key) {
        String[] bruteForce = new String[972];
        int count = 0;        
        for (char h1 = 'A'; h1 <= 'Z'; h1++) {
            for (char t1 = 'A'; t1 <= 'Z'; t1++) {
                if (t1 <= h1) {continue; }
                for (char h2 = 'A'; h2 <= 'Z'; h2++) {
                    if (h2 <= h1) { continue; }
                    for (char t2 = 'A'; t2 <= 'Z'; t2++) {
                        if (t2 <= h2) {continue;}
                        for (char h3 = 'A'; h3 <= 'Z'; h3++) {
                            if (h3 <= h2) { continue; }
                            for (char t3 = 'A'; t3 <= 'Z'; t3++) {
                                if (t3 <= h3) {continue;}
                                for (char h4 = 'A'; h4 <= 'Z'; h4++) {
                                    if (h4 <= h3) { continue;}
                                    for (char t4 = 'A'; t4 <= 'Z'; t4++) {
                                        if (t4 <= h4) { continue; }
                                        String p = h1+""+t1+""+h2+""+t2+""+h3+""+t3+""+h4+""+t4;
                                        if (isValidPairing(p)) {                                            
                                            String decrypt = ENIGMA.encrypt(key+p, null, true);
                                            if (decrypt.substring(64).equals("KDB")) {                                                
                                                bruteForce[count++] = p;                                                
                                            }
                                        }
                                    }
                                }                                
                            }
                        }
                    }
                }
            }
        }
        System.out.println(count);
        return bruteForce;
    }
    
    public static void compareTest() {
        String[] ss = forceKDB.forceKey("587ASE");
        for (int i = 0; i < ss.length; i++) {
            System.out.println(ss[i]);
        }
    }
    
    public static boolean isValidPairing(String p) {
        for (int i = 1; i < p.length(); i++) {
            if (p.substring(0, i).contains((char)p.charAt(i)+"")) {
                return false;
            }
        }
        return true;
    }  

}
