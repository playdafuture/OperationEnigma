package rockyRoad;

/**
 * First round of column bunch. Generates "columns" into the 1stRound folder.
 * @author Future
 */
public class Step1GenerateColumns {
    static String alphabets = ".ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static int rotor = 122;
    static int r1 = 1, r2 = 1, r3 = 0;
    public static void main(String[] args) {
        String[] cList = buildList.readFile();
        printAllColumns(cList);
    }    
    
    /**
     * Print out how many columns is there to console. Analysis purpose only.
     * @param comboList 
     */
    public static void allRnR(String[] comboList){
        int[] comboCount = new int[67];
        while (true) {
            String rot = nextRotors();
            if (rot != null) {                
                while (true) {
                    String ring = nextRings();
                    if (ring != null) {                        
                        String decrypt = ENIGMA.encrypt(rot+ring, null, true);
                        int c = 0;
                        for (String comboList1 : comboList) {
                            if (decrypt.contains(comboList1)) {
                                c++;
                            }
                        }
                        comboCount[c]++;
                    } else {
                        break;
                    }                    
                }                
            } else {
                break;
            }
        }
        for (int i = 0; i < 67; i++) {
            if (i < 10) {
                System.out.print(" ");
            }
            System.out.println(i+" "+comboCount[i]);
        }
    }
    
    /**
     * Prints all "columns" to corresponding files.
     * @param comboList The list of IMPOSSIBLE 3 letter combinations (used for elimination)
     */
    public static void printAllColumns(String[] comboList) {
        while (true) {
            String rot = nextRotors();
            if (rot != null) {
                IO.APPEND.set("files/elimination/1stRound/" + rot + ".txt");
                while (true) {
                    String ring = nextRings();
                    if (ring != null) {
                        String decrypt = ENIGMA.encrypt(rot+ring, null, true);
                        IO.APPEND.println(rot+ring);
                        for (int i = 0; i < comboList.length; i++) {
                            int pos = decrypt.indexOf(comboList[i]);
                            if (pos >= 0) {
                                String fullCol = ENIGMA.cipherText.substring(pos, pos+3) 
                                                + comboList[i];
                                //reduce duplicate alphabet
                                String col = fullCol.charAt(0) + "";
                                for (int j = 1; j < 6; j++) {
                                    if (!col.contains(fullCol.charAt(j)+"")) {
                                        col += fullCol.charAt(j) + "";
                                    }
                                }
                                IO.APPEND.println(col);
                            }                            
                        }                        
                    } else {
                        break;
                    }                    
                }                
            } else {   
                IO.APPEND.close();
                break;
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
