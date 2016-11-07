package strawberry;

public class test {
    public static void main(String[] args) {
        tryKey("134CKTPS");
    }
    
    public static void tryKey(String key) {
        String[] ss = {key};
        System.out.println(ENIGMA.encrypt(ss));
    }
}
