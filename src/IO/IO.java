package IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jinqiu Liu
 */
public abstract class IO {
    public static BufferedReader inStream;
    public static PrintWriter outStream;

    public static void setIO(String inFile, String outFile) {
        try {
            inStream = new BufferedReader( new FileReader(inFile) );
            outStream = new PrintWriter( new FileOutputStream(outFile) );
        } catch(FileNotFoundException e) {
            System.out.println("FileNotFound Exception @IO.setIO()");
        }
    }
    
    public static void closeIO() {
        try {
            inStream.close();
            outStream.close();
        } catch(IOException e) {
            System.out.println("IO Exception @IO.closeIO()");
        }
    }

    /**
     * Gets the next alphabetic character of the inputStream.
     * @return The uppercase String representation of next alphabetic char
     *         null if the end of stream is reached.
     */
    public static String getChar()	{
        char c = '.';
        while (c != -1) {
            try {
                c = (char) inStream.read();
                if (c >= 'A' && c <= 'Z') {
                    return c+"";
                } else if (c >= 'a' && c <= 'z'){
                    String s = c+"";
                    return s.toUpperCase();
                }
            } catch (IOException ex) {
                System.out.println("IO Exception @IO.getChar()");
            }
        }
        return null;
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
                i = inStream.read();
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
            System.out.println("IO Exception @IO.getWord()");
        }        
        return s;
    }
    
    public static String getLine() {
        String s = "";
        try {
            s = inStream.readLine();
        } catch (IOException ex) {
            System.out.println("IO Exception @IO.getLine()");
        }
        return s;
    }

    /**
     * Append the specified string into the outputStream.
     * @param s The String to append
     */
    public static void out(String s) {
        outStream.print(s);
    }
    
    /**
     * Append the specified string and a newline into the outputStream.
     * @param s The String to append
     */
    public static void outln(String s) {
        outStream.println(s);
    }
    
}
