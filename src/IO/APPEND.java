package IO;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class APPEND {
    public static BufferedWriter out;
    public static void set(String fileName) {
        try {
            out = new BufferedWriter(new FileWriter(fileName,true));
        } catch(FileNotFoundException e) {
            System.out.println("FileNotFound Exception @APPEND.set(fileName)");
        } catch (IOException ex) {
            System.out.println("FileNotFound Exception @APPEND.set(fileName)");
        }
    }
    
    public static void close() {
        try {
            out.close();
        } catch (IOException ex) {
            System.out.println("IO Exception @APPEND.close()");
        }
    }
    
    /**
     * Appends a string to the current set output file
     * @param line 
     */
    public static void print(String line) {
        try {
            out.append(line);
            out.flush();
        } catch (IOException ex) {
            System.out.println("IO Exception @APPEND.append(line)");
        }        
    }
    
    /**
     * Appends a string to the current set output file and start a new line
     * @param line 
     */
    public static void println(String line) {
        try {
            out.append(line);
            out.newLine();
            out.flush();
        } catch (IOException ex) {
            System.out.println("IO Exception @APPEND.append(line)");
        }        
    }
}
