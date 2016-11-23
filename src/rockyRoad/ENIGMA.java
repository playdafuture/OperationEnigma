package rockyRoad;

/**
 * Enigma object with main class.
 * Can be called by a different class or run itself (with little code modification)
 * @author Jinqiu Liu
 */
public class ENIGMA {       
    public static final String cipherText = 
        "CGKYTCJPXBMRRTQCQCVPYVYWVTCHEVQKCZNYXZULOPYWFCMLVPSOSYWZVDWOYAMCWMJ";
    
    /**
     * The setting string should be of the following format (with no spaces).
     * NO Enigma Type setting, since it will always be M3 for us.
     * NO reflector setting, since it will always be type B for us.
     * Left_Rotor_Type, Mid_Rotor_Type, Right_Rotor_Type (Walzenlage),
     *      use number between 1 - 8.
     * 3 letter ring (Ringstellung) setting, UPPERCASE only, "AAA" to "ZZZ".
     * NO ground setting, since it will always start at "AAA" for us.
     * Plugboard pairs (steckerbrett), UPPERCASE only, enter by pairs.
     * 
     * e.g. 256XAPQNUHJP means 
     * Rotor Type II, V, VI, 
     * Ring setting XAP,
     * Plug board connections Q-N, U-H, J-P     * 
     */
    public static String settingsString;
    
    public static byte plugBoard_1i = 0;
    public static byte plugBoard_1o = 0;
    public static byte plugBoard_2i = 0;
    public static byte plugBoard_2o = 0;
    public static byte plugBoard_3i = 0;
    public static byte plugBoard_3o = 0;
    public static byte plugBoard_4i = 0;
    public static byte plugBoard_4o = 0;
    
    public static String rotorString_l;
    public static String rotorString_m;
    public static String rotorString_r;
    
    // Left rotor knockpoints does not matter
    public static byte rotorKnockpoint_m1;
    public static byte rotorKnockpoint_m2;
    public static byte rotorKnockpoint_r1;
    public static byte rotorKnockpoint_r2;
    
    // M3 B-type reflector.
    public static final String reflector = ".YRUHQSLDPXNGOKMIEBFZCWVJAT"; 
    
    public static byte ring_l;
    public static byte ring_m;
    public static byte ring_r;
    
    // Ground setting is "AAA" by default
    public static byte ground_l = 1;
    public static byte ground_m = 1;
    public static byte ground_r = 1;
    
    // Plaintext Alphabet
    static final String alphabets = ".ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    public static void reset() {
        plugBoard_1i = 0;
        plugBoard_1o = 0;
        plugBoard_2i = 0;
        plugBoard_2o = 0;
        plugBoard_3i = 0;
        plugBoard_3o = 0;
        plugBoard_4i = 0;
        plugBoard_4o = 0;
        
        ground_l = 1;
        ground_m = 1;
        ground_r = 1;
    }
    
    public static String encrypt(String key, String cipher, boolean reset) {
        String localCipher;
        //load settings
        settingsString = key;       //Modify this to run from here
        if (cipher != null) {
            localCipher = cipher;           //Modify this for debugging
        } else {
            localCipher = cipherText;
        }
        if (reset) {
            reset();
        }
        //Assign settings variables
        //start of rotor selections
        switch(settingsString.charAt(0)) { //left rotor
            case '1':
                rotorString_l = ".EKMFLGDQVZNTOWYHXUSPAIBRCJ";
                break;
            case '2':
                rotorString_l = ".AJDKSIRUXBLHWTMCQGZNPYFVOE";
                break;
            case '3':
                rotorString_l = ".BDFHJLCPRTXVZNYEIWGAKMUSQO";
                break;
            case '4':
                rotorString_l = ".ESOVPZJAYQUIRHXLNFTGKDCMWB";
                break;
            case '5':
                rotorString_l = ".VZBRGITYUPSDNHLXAWMJQOFECK";
                break;
            case '6':
                rotorString_l = ".JPGVOUMFYQBENHZRDKASXLICTW";
                break;
            case '7':
                rotorString_l = ".NZJHGRCXMYSWBOUFAIVLPEKQDT";
                break;
            case '8':
                rotorString_l = ".FKQHTLXOCBJSPDZRAMEWNIUYGV";
                break;
        }
        
        switch(settingsString.charAt(1)) { //mid rotor
            case '1':
                rotorString_m = ".EKMFLGDQVZNTOWYHXUSPAIBRCJ";
                rotorKnockpoint_m1 = 17;
                rotorKnockpoint_m2 = 17;
                break;
            case '2':
                rotorString_m = ".AJDKSIRUXBLHWTMCQGZNPYFVOE";
                rotorKnockpoint_m1 =  5;
                rotorKnockpoint_m2 =  5;
                break;
            case '3':
                rotorString_m = ".BDFHJLCPRTXVZNYEIWGAKMUSQO";
                rotorKnockpoint_m1 = 22;
                rotorKnockpoint_m2 = 22;
                break;
            case '4':
                rotorString_m = ".ESOVPZJAYQUIRHXLNFTGKDCMWB";
                rotorKnockpoint_m1 = 10;
                rotorKnockpoint_m2 = 10;
                break;
            case '5':
                rotorString_m = ".VZBRGITYUPSDNHLXAWMJQOFECK";
                rotorKnockpoint_m1 = 26;
                rotorKnockpoint_m2 = 26;
                break;
            case '6':
                rotorString_m = ".JPGVOUMFYQBENHZRDKASXLICTW";
                rotorKnockpoint_m1 = 26;
                rotorKnockpoint_m2 = 13;
                break;
            case '7':
                rotorString_m = ".NZJHGRCXMYSWBOUFAIVLPEKQDT";
                rotorKnockpoint_m1 = 26;
                rotorKnockpoint_m2 = 13;
                break;
            case '8':
                rotorString_m = ".FKQHTLXOCBJSPDZRAMEWNIUYGV";
                rotorKnockpoint_m1 = 26;
                rotorKnockpoint_m2 = 13;
                break;
        }
        
        switch(settingsString.charAt(2)) { //right rotor
            case '1':
                rotorString_r = ".EKMFLGDQVZNTOWYHXUSPAIBRCJ";
                rotorKnockpoint_r1 = 17;
                rotorKnockpoint_r2 = 17;
                break;
            case '2':
                rotorString_r = ".AJDKSIRUXBLHWTMCQGZNPYFVOE";
                rotorKnockpoint_r1 =  5;
                rotorKnockpoint_r2 =  5;
                break;
            case '3':
                rotorString_r = ".BDFHJLCPRTXVZNYEIWGAKMUSQO";
                rotorKnockpoint_r1 = 22;
                rotorKnockpoint_r2 = 22;
                break;
            case '4':
                rotorString_r = ".ESOVPZJAYQUIRHXLNFTGKDCMWB";
                rotorKnockpoint_r1 = 10;
                rotorKnockpoint_r2 = 10;
                break;
            case '5':
                rotorString_r = ".VZBRGITYUPSDNHLXAWMJQOFECK";
                rotorKnockpoint_r1 = 26;
                rotorKnockpoint_r2 = 26;
                break;
            case '6':
                rotorString_r = ".JPGVOUMFYQBENHZRDKASXLICTW";
                rotorKnockpoint_r1 = 26;
                rotorKnockpoint_r2 = 13;
                break;
            case '7':
                rotorString_r = ".NZJHGRCXMYSWBOUFAIVLPEKQDT";
                rotorKnockpoint_r1 = 26;
                rotorKnockpoint_r2 = 13;
                break;
            case '8':
                rotorString_r = ".FKQHTLXOCBJSPDZRAMEWNIUYGV";
                rotorKnockpoint_r1 = 26;
                rotorKnockpoint_r2 = 13;
                break;
        }
        //end of rotor selections
        
        //start of ring setting ('A' = 1, 'B' = 2, ... , 'Z' = 26)
        ring_l = (byte) (settingsString.charAt(3) - 'A' + 1);
        ring_m = (byte) (settingsString.charAt(4) - 'A' + 1);
        ring_r = (byte) (settingsString.charAt(5) - 'A' + 1);
        //end of ring setting
        
        //start of plugboard settings (optional)
        settingsString = settingsString.substring(6);
        if (settingsString.length() > 0) { //there is 1st pair of plugboard settings
            plugBoard_1i = (byte) (settingsString.charAt(0) - 'A' + 1);
            plugBoard_1o = (byte) (settingsString.charAt(1) - 'A' + 1);
            settingsString = settingsString.substring(2);
            if (settingsString.length() > 0) { //there is 2nd pair           
                plugBoard_2i = (byte) (settingsString.charAt(0) - 'A' + 1);
                plugBoard_2o = (byte) (settingsString.charAt(1) - 'A' + 1);
                settingsString = settingsString.substring(2);
                if (settingsString.length() > 0) { //there is 3rd pair      
                    plugBoard_3i = (byte) (settingsString.charAt(0) - 'A' + 1);
                    plugBoard_3o = (byte) (settingsString.charAt(1) - 'A' + 1);
                    settingsString = settingsString.substring(2);
                    if (settingsString.length() > 0) { //there is 4th pair
                        plugBoard_4i = (byte) (settingsString.charAt(0) - 'A' + 1);
                        plugBoard_4o = (byte) (settingsString.charAt(1) - 'A' + 1);
                    }
                }                
            }
        }          
        //else, no plugboard setting (for testing purpose only)
        //end of plugboard setting (optional)
        
        String decipher = "";
        for (byte i = 0; i < localCipher.length(); i++) {
            decipher += doCipher(localCipher.charAt(i));
        }
        return (decipher);
    }

    /**
     * Run the whole Enigma machine process without other function calls.
     * @param input The input character (plaintext)
     * @return  The output character (localCipher)
     */
    public static char doCipher(char input) {        
        // convert input to number format
        byte number = (byte) (input - 'A' + 1);
        
        // rotate cogs
        if (ground_r == rotorKnockpoint_r1 || ground_r == rotorKnockpoint_r2) {
            // If the knockpoint on the right wheel is reached rotate middle wheel
            // But first check if it too is a knock point
            if (ground_m == rotorKnockpoint_m1 || ground_m == rotorKnockpoint_m2) {
                // If the knockpoint on the middle wheel is reached rotate left wheel
                ground_l++;
            }
            ground_m++;
        } else {
            // Right wheel knockpoint is not met, but middle wheel is
            if (ground_m == rotorKnockpoint_m1 || ground_m == rotorKnockpoint_m2) {
                ground_l++;
                ground_m++;
            }
        }
        // right wheel should always be rotated
        ground_r++;
        //keeps everyone stay inside the cycle
        if (ground_l > 26) { ground_l -= 26; }
        if (ground_m > 26) { ground_m -= 26; }
        if (ground_r > 26) { ground_r -= 26; }
                
        //First pass - Plugboard
        if (number == plugBoard_1i) {
            number = plugBoard_1o;
        } else if (number == plugBoard_1o) {
            number = plugBoard_1i;
        } else if (number == plugBoard_2i) {
            number = plugBoard_2o;
        } else if (number == plugBoard_2o) {
            number = plugBoard_2i;
        } else if (number == plugBoard_3i) {
            number = plugBoard_3o;
        } else if (number == plugBoard_3o) {
            number = plugBoard_3i;
        } else if (number == plugBoard_4i) {
            number = plugBoard_4o;
        } else if (number == plugBoard_4o) {
            number = plugBoard_4i;
        }
        
        char temp;
        //First pass - R Wheel
        number -= ring_r;
        number += ground_r;
        if (number > 26) number -= 26;
        if (number <  1) number += 26;
        temp = rotorString_r.charAt(number);
        number = (byte) (temp - 'A' + 1);        
        number -= ground_r;
        number += ring_r;
        if (number > 26) number -= 26;
        if (number <  1) number += 26;
        
        // First Pass - M Wheel
        number -= ring_m;
        number += ground_m;
        if (number > 26) number -= 26;
        if (number <  1) number += 26;
        temp = rotorString_m.charAt(number);
        number = (byte) (temp - 'A' + 1);
        number -= ground_m;
        number += ring_m;
        if (number > 26) number -= 26;
        if (number <  1) number += 26;
        
        // First Pass - L Wheel
        number -= ring_l;
        number += ground_l;
        if (number > 26) number -= 26;
        if (number <  1) number += 26;
        temp = rotorString_l.charAt(number);
        number = (byte) (temp - 'A' + 1);
        number -= ground_l;
        number += ring_l;
        if (number > 26) number -= 26;
        if (number <  1) number += 26;
        
        // Reflector
        temp = reflector.charAt(number);
        number = (byte) (temp - 'A' + 1);
        
        // Second Pass - L Wheel
        number -= ring_l;
        number += ground_l;
        if (number > 26) number -= 26;
        if (number <  1) number += 26;
        number = (byte) rotorString_l.indexOf('A' + number - 1);
        number -= ground_l;
        number += ring_l;
        if (number > 26) number -= 26;
        if (number <  1) number += 26;
        
        // Second Pass - M Wheel
        number -= ring_m;
        number += ground_m;
        if (number > 26) number -= 26;
        if (number <  1) number += 26;
        number = (byte) rotorString_m.indexOf('A' + number - 1);
        number -= ground_m;
        number += ring_m;
        if (number > 26) number -= 26;
        if (number <  1) number += 26;
        
        // Second Pass - R Wheel
        number -= ring_r;
        number += ground_r;
        if (number > 26) number -= 26;
        if (number <  1) number += 26;
        number = (byte) rotorString_r.indexOf('A' + number - 1);
        number -= ground_r;
        number += ring_r;
        if (number > 26) number -= 26;
        if (number <  1) number += 26;

        // Second Pass - Plugboard
        if (number == plugBoard_1i) {
            number = plugBoard_1o;
        } else if (number == plugBoard_1o) {
            number = plugBoard_1i;
        } else if (number == plugBoard_2i) {
            number = plugBoard_2o;
        } else if (number == plugBoard_2o) {
            number = plugBoard_2i;
        } else if (number == plugBoard_3i) {
            number = plugBoard_3o;
        } else if (number == plugBoard_3o) {
            number = plugBoard_3i;
        } else if (number == plugBoard_4i) {
            number = plugBoard_4o;
        } else if (number == plugBoard_4o) {
            number = plugBoard_4i;
        }
        
        return (char) ('A' + number - 1);
    }
}

