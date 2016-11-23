package languageRecognition;

public class updateI3C {
    public static void main(String[] args) {
        String[] currentI3C = rockyRoad.buildList.readFile();
        for (int i = 0; i < currentI3C.length; i++) {
            String s = currentI3C[i];
            try {
                if (s.equals("KDB") || s.substring(1).equals("KD") || s.substring(2).equals("K")) {
                    currentI3C[i] = null;
                }
            } catch (java.lang.NullPointerException e) {
                System.out.println(i);
                System.out.println(s);
            }
            
        }
        IO.OVERWRITE.set("files/I3C.txt");
        for (int i = 0; i < currentI3C.length; i++) {
            String s = currentI3C[i];
            if (s != null) {
                IO.OVERWRITE.println(s);
            }
        }
        IO.OVERWRITE.close();        
    }
}
