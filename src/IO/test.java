package IO;

/**
 *
 * @author Jinqiu Liu
 */
public class test {
    public static void main(String[] args) {
        IO.setIO("files/englishText/5.txt", "files/englishText/5c.txt");
        String s;
        String l = "";
        String key;
        String c1;
        String d1;
        while (true) {
            s = IO.getWord();
            if (s == null) {
                IO.closeIO();
                break;
            }
            l = l+s;
            if (l.length() > 70) {
                key = randomKey();
                String[] ss = {key, l}; //full key, l = plaintext
                strawberry.ENIGMA.reset();
                c1 = strawberry.ENIGMA.encrypt(ss); //c1 = full encrypt
                IO.outln("key =");
                IO.outln(key);
                IO.outln("cipher =");
                IO.outln(c1);
                IO.outln("plain text =");
                IO.outln(l);
                ss[1] = c1;
                //start partial pairings                
                    //3 pairs
                    key = key.substring(0, 12);
                    ss[0] = key;
                    strawberry.ENIGMA.reset();
                    d1 = strawberry.ENIGMA.encrypt(ss);
                    IO.outln("decrypt with 3 pairs of plugboard = " + key);
                    IO.outln(d1);
                    
                    //2 pairs
                    key = key.substring(0, 10);
                    key += "  ";
                    ss[0] = key;
                    strawberry.ENIGMA.reset();
                    d1 = strawberry.ENIGMA.encrypt(ss);
                    IO.outln("decrypt with 2 pairs of plugboard = " + key);
                    IO.outln(d1);
                    
                    //1 pair
                    key = key.substring(0, 8);
                    key += "    ";
                    ss[0] = key;
                    strawberry.ENIGMA.reset();
                    d1 = strawberry.ENIGMA.encrypt(ss);
                    IO.outln("decrypt with 1 pair of plugboard = " + key);
                    IO.outln(d1);
                    
                    //0 pairs
                    key = key.substring(0, 6);
                    ss[0] = key;
                    strawberry.ENIGMA.reset();
                    d1 = strawberry.ENIGMA.encrypt(ss);
                    IO.outln("decrypt with 0 pairs of plugboard = " + key);
                    IO.outln(d1);               
                
                IO.outln("");
                l = "";
            }
        }
        IO.closeIO();
    }
    
    public static String randomKey() {
        //rotors
        int a = (int) (Math.random() * 8 + 1);
        int b = (int) (Math.random() * 8 + 1);
        while (b == a) {
            b = (int) (Math.random() * 8 + 1);
        }
        int c = (int) (Math.random() * 8 + 1);
        while (c == a || c == b) {
            c = (int) (Math.random() * 8 + 1);
        }
        //ring
        String key = a+""+b+""+c;
        a = (int) (Math.random() * 26);
        char ch = (char) ('A' + a);
        key+=ch;
        a = (int) (Math.random() * 26);
        ch = (char) ('A' + a);
        key+=ch;
        a = (int) (Math.random() * 26);
        ch = (char) ('A' + a);
        key+=ch;
        //plugboards

        //4 pairs
        int[] alpha = new int[8];
        for (int i = 0; i < 8; i++) {
            Boolean dupeFound = false;
            a = (int) (Math.random() * 26 + 1);
            for (int j = 0; j < i; j++) {
                if (a == alpha[j]) {
                    i--;
                    dupeFound = true;
                    break;
                }
            }
            if (!dupeFound) {
                alpha[i] = a;
            }                
        }
        for (int i = 0; i < 8; i++) {
            ch = (char) ('A'+alpha[i]-1);
            key+= (ch);
        }
       
        return key;
    }
}
