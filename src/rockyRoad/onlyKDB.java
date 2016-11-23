package rockyRoad;

import languageRecognition.Lang_rec;

/**
 *
 * @author Future
 */
public class onlyKDB {
    public static void main(String[] args) {
        Lang_rec rec = new Lang_rec();
        String[] keys = IO.LOAD.readFile("files/KDB/keys.txt");
        
        for (int i = 0; i < keys.length; i++) {
            IO.APPEND.set("files/KDB/" + keys[i].substring(0, 3) + ".txt");
            String[] plugs = forceKDB.getPlugs(keys[i]);
            if (plugs == null) {continue;} //skipping some keys for now
            for (int j = 0; j < plugs.length; j++) {
                String decrypt = ENIGMA.encrypt(keys[i]+plugs[j], null, true);
                if (decrypt.substring(64).equals("KDB")) {
                    double l = rec.lang_test_double(decrypt);
                    if (l > 0.85) {
                        IO.APPEND.println(decrypt + " " + (keys[i]+plugs[j]) + " " + l);
                        System.out.println(decrypt + " " + (keys[i]+plugs[j]) + " " + l);
                    }
                }
            }
            IO.APPEND.close();
        }
    }
}
