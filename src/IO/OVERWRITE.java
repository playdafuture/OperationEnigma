package IO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class OVERWRITE {
    public static PrintWriter out;
    
    public static void set(String fileName) {
        try {
            out = new PrintWriter( new FileOutputStream(fileName) );
        } catch(FileNotFoundException e) {
            System.out.println("FileNotFound Exception @OVERWRITE.set()");
        }
    }
    

    
    public static void close() {
        out.close();
    }

    /**
     * Append the specified string into the outputStream.
     * @param s The String to append
     */
    public static void print(String s) {
        out.print(s);
    }
    
    /**
     * Append the specified string and a newline into the outputStream.
     * @param s The String to append
     */
    public static void println(String s) {
        out.println(s);
    }
}
