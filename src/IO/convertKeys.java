package IO;

/**
 *
 * @author Future
 */
public class convertKeys {
    public static void main(String[] args) {
        filter();
    }
    
    public static void fetch() {
        String[] toomuch = LOAD.readFile("files/KDB/evil.txt");
        APPEND.set("files/KDB/keys.txt");
        for (int i = 0; i < toomuch.length; i++) {
            if (toomuch[i].length() == 6) {
                APPEND.println(toomuch[i]);
            }            
        }
        APPEND.close();
    }
    
    public static void filter() {
        APPEND.set("files/KDB/keys.txt");
        String[] all = LOAD.readFile("files/KDB/newkeys.txt");
        String[] used = LOAD.readFile("files/KDB/usedkeys.txt");
        for (int i = 0; i < all.length; i++) {
            boolean match = false;
            for (int j = 0; j < used.length; j++) {
                if (all[i].equals(used[j])) {
                    //keymatch                   
                    match = true;
                    break;
                }
            }
            if (!match) {
                APPEND.println(all[i]);
            }
        }
        APPEND.close();
    }
}
