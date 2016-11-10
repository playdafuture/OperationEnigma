package strawberry;
import java.io.IOException;
import languageRecognition.*;

public class test {
    public static void main(String[] args) throws IOException {
        Lang_rec rec = new Lang_rec();
        System.out.println(rec.lang_test_double("ANFOASFDIOFANOI")*100);
    }
    
    public static void tryKey(String key) {
        String[] ss = {key};
        System.out.println(ENIGMA.encrypt(ss));
    }
}
