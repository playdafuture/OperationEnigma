package rockyRoad;

import IO.READ;

public class buildList {
    public static void main(String[] args) {
        readBooks();
    }
    
    public static String[] readFile() {
        IO.READ.set("files/I3C.txt");
        int lineCount = 0;
        while (true) {
            String line = READ.getLine();            
            if (line == null) {
                break;
            } else {
                lineCount++;
            }
        }
        IO.READ.close();        
        String[] fullList = new String[lineCount];
        IO.READ.set("files/I3C.txt");
        int idx = 0;
        while (true) {
            String line = READ.getLine();            
            if (line == null) {
                break;
            } else {
                fullList[idx++] = line;
            }
        }
        return fullList;
    }
    
    public static String[] readFile(String fileName) {
        IO.READ.set(fileName);
        int lineCount = 0;
        while (true) {
            String line = READ.getLine();            
            if (line == null) {
                break;
            } else {
                lineCount++;
            }
        }
        IO.READ.close();        
        String[] fullList = new String[lineCount];
        IO.READ.set(fileName);
        int idx = 0;
        while (true) {
            String line = READ.getLine();            
            if (line == null) {
                break;
            } else {
                fullList[idx] = line;
            }
        }
        return fullList;
    }
    
    public static String[] readBooks() {        
        int[] fullList = new int[17576]; //everything goes null
        for (int i = 1; i <= 6; i++) {
            IO.READ.set("files/englishText/" + i + ".txt");
            System.out.println("Reading " + i + ".txt");
            String s = "";
            s += IO.READ.getChar();
            s += IO.READ.getChar();
            s += IO.READ.getChar();
            while (true) {
                fullList[indexOf(s)]++;
                String next = IO.READ.getChar();
                //System.out.println(next);
                if (next != null) {
                    s += next;
                    s = s.substring(1);
                    //System.out.println(s);
                } else {
                    break;
                }
            }
            IO.READ.close();
        }
        int count = 0;
        for (int i = 0; i < 17576; i++) {
            if (fullList[i] == 0) {
                count++;
            }
        }
        String[] comboList = new String[count];
        count = 0;
        for (int i = 0; i < 17576; i++) {
            if (fullList[i] == 0) {
                comboList[count++] = stringOf(i);
            }
        }
        System.out.println("List loaded, total " + comboList.length + " impossible combos");
        return comboList;
    }
    
    /**
     * AAA = 0, AAB = 1, ~~ ZZZ = 17576.
     * @param s 3 character string
     * @return  numeric value
     */
    public static int indexOf(String s) {
        return (s.charAt(0) - 'A')*26*26 + (s.charAt(1) - 'A')*26 + (s.charAt(2) - 'A');
    }
    
    public static String stringOf(int i) {
        int c = i%26;
        i = i/26;
        int b = i%26;
        i = i/26;
        int a = i;
        char x = (char) ('A'+a);
        char y = (char) ('A'+b);
        char z = (char) ('A'+c);
        return x+""+y+""+z;
    }
}
