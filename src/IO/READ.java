package IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class READ {
    public static BufferedReader in;
    
    public static void set(String fileName) {
        try {
            in = new BufferedReader(new FileReader(fileName));
        } catch(FileNotFoundException e) {
            System.out.println("FileNotFound Exception @READ.set(fileName)");
        }
    }
    
    public static void close() {
        try {
            in.close();
        } catch (IOException ex) {
            System.out.println("IO Exception @READ.close()");
        }
    }
    
    /**
     * Gets the next alphabetic character of the inputStream.
     * @return The uppercase String representation of next alphabetic char
     *         null if the end of stream is reached.
     */
    public static String getChar()	{
        char c;
        while (true) {
            try {
                c = (char) in.read();
                if (c == 65535) {
                    return null;
                } else if (c >= 'A' && c <= 'Z') {
                    return c+"";
                } else if (c >= 'a' && c <= 'z'){
                    String s = c+"";
                    return s.toUpperCase();
                }                
            } catch (IOException ex) {
                System.out.println("IO Exception @READ.getChar()");
            }
        }
    }
    
    /**
     * Gets the next word of the inputStream, separated by any non-alphabetical character.
     * @return The uppercase String representation of next alphabetic char
     *         null if the end of stream is reached.
     */
    public static String getWord() {
        String s = "";
        try {
            int i = 0;
            while (i != -1) {
                i = in.read();
                if (i == -1 && s.length() == 0) return null;
                if (i == -1) return s;
                char c = (char) i;
                if (c >= 'A' && c <= 'Z') {
                    s = s+c;
                } else if (c >= 'a' && c <= 'z') {
                    String t = c+"";
                    s = s+t.toUpperCase();
                } else if (s.length() != 0) { 
                    //s is not empty and landed on a special char, got word
                    return s;
                } else {
                    //s is still empty but landed on a special char, 
                    //try a different starting point
                    continue;
                }                
            }
        } catch (IOException ex) {
            System.out.println("IO Exception @READ.getWord()");
        }        
        return s;
    }
    
    public static String getLine() {
        String s = "";
        try {
            s = in.readLine();
        } catch (IOException ex) {
            System.out.println("IO Exception @READ.getLine()");
        }
        return s;
    }
}
