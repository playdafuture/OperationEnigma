package vanilla;

/**
 *
 * @author Future
 */
public class batchTesting {
    public static void main(String[] args) {
        String ss;
        String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int seq = 0;
        for (int r = 111; r <= 888; r++) {
            if (r%10 == 9) { r+=2; }
            if ((r/10)%10 == 9) { r+= 20; }
                
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    for (int k = 0; k < 26; k++) {
                        ss = "";
                        ss+=r;  
                        ss+=a.substring(i,i+1);
                        ss+=a.substring(j,j+1);
                        ss+=a.substring(k,k+1);
                        ss+="AAA";
                        String[] arguments = new String[2];
                        arguments[0] = ss;
                        arguments[1] = "CGKYTCJPXBMRRTQCQCVPYVYWVTCHEVQKCZNYXZULOPYWFCMLVPSOSYWZVDWOYAMCWMJ";
                        seq++;
                        //System.out.println(seq+"|"+ss);
                        ENIGMA.encrypt(arguments);
                    }
                }
            }
        }
        
    }
}
