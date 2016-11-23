package rockyRoad;

/**
 *
 * @author Future
 */
public class test {
    public static void main(String[] args) {
        String[] testPlugs = forceKDB.getPlugs("546OOO");
        System.out.println(ENIGMA.encrypt("546OOOKIMTJC", null, true));
        
        String[] keys = IO.LOAD.readFile("files/KDB/37_44.txt");
        System.out.println(keys.length);
    }
}
