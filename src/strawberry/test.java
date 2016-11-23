package strawberry;
import java.io.IOException;
import languageRecognition.*;

public class test {
    public static void main(String[] args) throws IOException {
        tryKey("123SGDGK");
    }
    
    public static void tryKey(String key) {
        System.out.println(ENIGMA.encrypt(key, null, true));
    }
}
