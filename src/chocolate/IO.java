package chocolate;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Jinqiu Liu
 */
public abstract class IO {
    public static BufferedReader inStream;
    public static PrintWriter outStream;
    public static int a; // the current input character on "inStream"
    public static char c; // used to convert the variable "a" to the char type whenever necessary
    
    public static void setIO(String inFile, String outFile) {
        try {
            inStream = new BufferedReader( new FileReader(inFile) );
            outStream = new PrintWriter( new FileOutputStream(outFile) );
            a = inStream.read();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void closeIO() {
        try {
            inStream.close();
            outStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Gets the next character of the inputStream.
     * @return The integer value of next char
     */
    public static int getNextChar() {
        try {
            return inStream.read();
        } catch(IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Gets the next non-whitespace character of the inputStream.
     * @return The integer value of next non-whitespace char
     *         -1, end-of-stream if the end of the input stream is reached.
     */
    public static int getChar()	{
        int i = getNextChar();
        while ( Character.isWhitespace((char) i) )
            i = getNextChar();
        return i;
    }

    /**
     * Append the specified string into the outputStream.
     * @param s The String to append
     */
    public static void display(String s) {
        outStream.print(s);
    }
    
    /**
     * Append the specified string and a newline into the outputStream.
     * @param s The String to append
     */
    public static void displayln(String s) {
        outStream.println(s);
    }
    
}
