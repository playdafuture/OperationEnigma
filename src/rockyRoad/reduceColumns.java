package rockyRoad;

import IO.READ;

public class reduceColumns {
    static String alphabets = ".ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static int rotor = 122;
    static int r1 = 1, r2 = 1, r3 = 0;
    static String[] columns;
    public static void main(String[] args) {
        loadColumns("123");
    }
    
    /**
     * Create 6*8 lists method and filter for each new column
     * @param ringSetting 
     */
    public static void loadColumns(String ringSetting) {
        IO.READ.set("files/elimination/1stRound/" + ringSetting + ".txt");
        //IO.APPEND.set("files/elimination/2ndRound/" + ringSetting + ".txt");
        String line = READ.getLine();
        String key = "";
        String[] worstCaseBigArray = new String[64];
        int i = 0;
        while (line != null) {            
            if (line.charAt(0) == ringSetting.charAt(0)) {
                //new key
                key = line;                 
                if (i > 0) { //flush
                    String[] shrunk = new String[i];
                    for (int j = 0; j < i; j++) {
                        shrunk[j] = worstCaseBigArray[j];
                    }
                    columnBunch.allCols = shrunk;
                    System.out.println(columnBunch.greedyReduction());
                    i = 0;
                }
                System.out.println(key);  
            } else {
                //insert into column;
                worstCaseBigArray[i++] = line;
            }
            line = READ.getLine();            
        }
            String[] shrunk = new String[i];
            for (int j = 0; j < i; j++) {
                shrunk[j] = worstCaseBigArray[j];
            }
            columnBunch.allCols = shrunk;
            System.out.println(columnBunch.greedyReduction());
        IO.READ.close();
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
