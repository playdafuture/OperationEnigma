package coconut;

/**
 *
 * @author Future
 */
public class plugboardVerification {
    public static void main(String[] args) {
        String rnr = "123ACK";
        String [] generated = rockyRoad.forceKDB.forceKey(rnr);
        String [] bf3 = batch.set3Plugs();
        String [] bf4 = batch.set4Plugs();
        IO.APPEND.set("files/ronnietesting/bf.txt");
        for (int i = 0 ; i <  bf3.length; i++) {
            String decrypt = ENIGMA.encrypt(rnr+bf3[i], null, true);
            if (decrypt.substring(64).equals("KDB")) {
                IO.APPEND.println(decrypt);
            }
        }
        for (int i = 0 ; i < bf4.length; i++) {
            String decrypt = ENIGMA.encrypt(rnr+bf4[i], null, true);
            if (decrypt.substring(64).equals("KDB")) {
                IO.APPEND.println(decrypt);
            }
        }
        IO.APPEND.close();
        
        //change to generated
        IO.APPEND.set("files/ronnietesting/bf.txt");
        for (int i = 0 ; i <  bf3.length; i++) {
            String decrypt = ENIGMA.encrypt(rnr+bf3[i], null, true);
            if (decrypt.substring(64).equals("KDB")) {
                IO.APPEND.println(decrypt);
            }
        }
        
        //compare the two files to see if bf is a subset of gen
        
    }
}
