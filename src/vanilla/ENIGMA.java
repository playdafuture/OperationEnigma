package vanilla;

public class ENIGMA {
    public static settings document;
    
    // Plaintext Alphabet
    static String plaintext = ".ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Rotor Wiring
    static String[] arrRotors = { 
        ".ABCDEFGHIJKLMNOPQRSTUVWXYZ", // Rotor "0"
        ".EKMFLGDQVZNTOWYHXUSPAIBRCJ", // Rotor I
        ".AJDKSIRUXBLHWTMCQGZNPYFVOE", // Rotor II
        ".BDFHJLCPRTXVZNYEIWGAKMUSQO", // Rotor III
        ".ESOVPZJAYQUIRHXLNFTGKDCMWB", // Rotor IV
        ".VZBRGITYUPSDNHLXAWMJQOFECK", // Rotor V
        ".JPGVOUMFYQBENHZRDKASXLICTW", // Rotor VI
        ".NZJHGRCXMYSWBOUFAIVLPEKQDT", // Rotor VII
        ".FKQHTLXOCBJSPDZRAMEWNIUYGV"  // Rotor VIII
    };
    
    static int[][] arrKnockpoints = {
        {},                            //   EMPTY               (R 0)
        {17, 17},                      //   Q - one knockpoint  (R I)
        { 5,  5},                      //   E - one knockpoint  (R II)
        {22, 22},                      //   V - one knockpoint  (R III)
        {10, 10},                      //   J - one knockpoint  (R IV)
        {26, 26},                      //   Z - one knockpoint  (R V)
        {26, 13},                      // Z/M - two knockpoints (R VI)
        {26, 13},                      // Z/M - two knockpoints (R VII)
        {26, 13}                       // Z/M - two knockpoints (R VIII)
    };
    
    static String arrReflector = ".YRUHQSLDPXNGOKMIEBFZCWVJAT"; // M3 B

    static String lastkeypressed = ""; // Keyboard key pressed
    static String inputmethod = "single";
    
    public static void main(String[] args) {
        String settingsString = "123AAAAAA";     //tempted to call it short as the "SS"
        document = new settings(settingsString);
        System.out.println(doCipher('A'));
        System.out.println(doCipher('A'));
        System.out.println(doCipher('A'));
    }
    
    /**
     * Swap the steckerbrett pairings.
     * e.g., pair 'AD' - if 'A' goes in, 'D' comes out and vice versa
     * @param num Numeric representation of the letter that goes in
     * @return Numeric representation of the letter that comes out
     */
    public static int swapPlugs(int num) {
        String t = plaintext.substring(num, num+1);
        int i = document.plugin.indexOf(t);
        int o = document.plugout.indexOf(t);
        if (i > -1) {
            //this letter is found in the plugin
            return i;
        } else if (o > -1) {
            //this letter is found in the plugout
            return o;
        } else {
            //this letter is not part of the plug board
            return num;
        }
    } 
    
    /**
     * Take n mod 26 and ensures it's positive.
     * @param n The index of a letter. 1, 27 for A and 0, 26 for Z.
     * @return  
     */
    public static int validLetter(int n) {
        while (n <= 0) {
            n += 26;
        }
        while (n >= 27) {
            n -= 26;
        }
        return n;
    }

    /**
     * Rotate the wheels/cogs by one letter.
     * This function directly modifies the global setting.
     */
    public static void rotateCogs() {
        String currentCog = document.ground;
        int pl = plaintext.indexOf(currentCog.substring(0, 1));
        int pm = plaintext.indexOf(currentCog.substring(1, 2));
        int pr = plaintext.indexOf(currentCog.substring(2, 3));
        
        // r = right, m = middle, l = left
        int r = document.wheelRight;
        int m = document.wheelMid;
        int l = document.wheelLeft;
        
        if (pr == arrKnockpoints[r][0] || pr == arrKnockpoints[r][1]) {
            // If the knockpoint on the right wheel is reached rotate middle wheel
            // But first check if it too is a knock point
            if (pm == arrKnockpoints[m][0] || pm == arrKnockpoints[m][1]) {
                // If the knockpoint on the middle wheel is reached rotate left wheel
                pl++;
            }
            pm++;
        } else {
            if (pm == arrKnockpoints[m][0] || pm == arrKnockpoints[m][1]) {
                pl++;
                pm++;
            }
        }
        
        // Rotate right wheel (this wheel is always rotated).
        pr++;
        
        // If rotating brings us beyond "Z" (26), then start at "A" (1) again.
        if (pr > 26) {pr = 1;}
        if (pm > 26) {pm = 1;}
        if (pl > 26) {pl = 1;}
        
        // Save the new setting back to groundwheel
        document.ground = plaintext.substring(pl, pl+1) 
                        + plaintext.substring(pm, pm+1) 
                        + plaintext.substring(pr, pr+1);
    }

    /**
     * Map one letter to another through current wheel
     * @param number        Numeric representation of the input letter
     * @param ringstellung  Wheel ring setting (static)
     * @param wheelposition wheel position (rotates)
     * @param wheel         current wheel
     * @param pass          pass = are we going R->L (1) or L->R (2)
     * @return 
     */
    public static int mapLetter(int number, int ringstellung, 
                                int wheelposition, int wheel, int pass) {
        // Change number according to ringstellung (ring setting)
        // Wheel turns anti-clockwise (looking from right)
        number = number - ringstellung;
        // Check number is between 1 and 26
        number = validLetter(number);

        // Change number according to wheel position
        // Wheel turns clockwise (looking from right)
        number = number + wheelposition;
        // Check number is between 1 and 26
        number = validLetter(number);

        // Do internal connection 'x' to 'y' according to direction  
        if (pass == 2) {
            char let = ENIGMA.plaintext.charAt(number);
            number = ENIGMA.arrRotors[wheel].indexOf(let);
        } else {
            char let = ENIGMA.arrRotors[wheel].charAt(number);
            number = ENIGMA.plaintext.indexOf(let);
        }

        // NOW WORK IT BACKWARDS : subtract where we added and vice versa

        // Change according to wheel position (anti-clockwise)
        number = number - wheelposition;
        // Check number is between 1 and 26
        number = validLetter(number);

        // Change according to ringstellung (clockwise)
        number = number + ringstellung;
        // Check number is between 1 and 26
        number = validLetter(number);

        return number;        
    }

    /**
     * Run the whole Enigma machine process.
     * The functions above are called from this routine.
     * @param input The input character (plaintext)
     * @return  The output character (ciphertext)
     */
    public static char doCipher(char input) {
        rotateCogs();
        
        int wheel_l = document.wheelLeft;
        int wheel_m = document.wheelRight;
        int wheel_r = document.wheelRight;
        
        int ring_l = plaintext.indexOf(document.ring.substring(0, 1));
        int ring_m = plaintext.indexOf(document.ring.substring(1, 2));
        int ring_r = plaintext.indexOf(document.ring.substring(2, 3));        
        
        int start_l = plaintext.indexOf(document.ground.substring(0, 1));
        int start_m = plaintext.indexOf(document.ground.substring(1, 2));
        int start_r = plaintext.indexOf(document.ground.substring(2, 3));
        
    /* -----------------------------------------------------------------
        Enigma Process
         1. Convert input letter to number - validate!
         2. Rotate wheels
         3. Pass through plugboard
         4. Pass through right-hand wheel
         5. Pass through middle wheel
         6. Pass through left-hand wheel
         7. Pass through reflector
         8. Pass through left-hand wheel
         9. Pass through middle wheel
        10. Pass through right-hand wheel
        11. Pass through plugboard
        12. Convert to output letter
    ----------------------------------------------------------------- */
        
        //convert input letter to number
        int number = input - 'A' + 1;
        
        //First pass - Plugboard
        number = swapPlugs(number);
        //First pass - R Wheel
        number = mapLetter(number, ring_r, start_r, wheel_r, 1);
        // First Pass - M Wheel
        number = mapLetter(number,ring_m,start_m,wheel_m, 1);
        // First Pass - L Wheel
        number = mapLetter(number,ring_l,start_l,wheel_l, 1);
        // Reflector
        char let = ENIGMA.arrReflector.charAt(number);
        number = ENIGMA.plaintext.indexOf(let);
        // Second Pass - L Wheel
        number = mapLetter(number,ring_l,start_l,wheel_l, 2);
        // Second Pass - M Wheel
        number = mapLetter(number,ring_m,start_m,wheel_m, 2);
        // Second Pass - R Wheel
        number = mapLetter(number,ring_r,start_r,wheel_r, 2);
        // Passes through ETW again

        // Second Pass - Plugboard
        number = swapPlugs(number);
        
        return plaintext.charAt(number);
    }
}

