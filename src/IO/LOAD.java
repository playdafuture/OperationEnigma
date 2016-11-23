package IO;

public class LOAD {    
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
                fullList[idx++] = line;
            }
        }
        return fullList;
    }
}
