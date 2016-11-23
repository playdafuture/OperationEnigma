package coconut;

import languageRecognition.Lang_rec;

/**
 *
 * @author Future
 */
public class bruteForce {
    public static void main(String[] args) {
        String[] keys = IO.LOAD.readFile("files/KDB/bfKeys.txt");
        System.out.println("Total of " + keys.length + " keys");                
        String[] pb6 = batch.set3Plugs();        
        Lang_rec rec = new Lang_rec();
        for (int i = 0; i < keys.length; i++) {
            double start = System.currentTimeMillis();
            IO.APPEND.set("files/KDB/" + keys[i].substring(0,3) + "bf.txt");
            for (int j = 0; j < pb6.length; j++) {
                String fullKey = keys[i]+pb6[j];
                String decrypt = ENIGMA.encrypt(fullKey, null, true);
                double likely = rec.lang_test_double(decrypt.substring(0, 64));
                if (likely > 0.85) {
                    System.out.println(decrypt + " " + fullKey + " " + likely);                    
                    IO.APPEND.println(decrypt + " " + fullKey + " " + likely);
                }
            }
            double end = System.currentTimeMillis();
            System.out.println("Time for last key = " + (end - start)/1000 + " seconds");
        }
    }
}
