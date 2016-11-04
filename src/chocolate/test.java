package chocolate;

public class test {
    public static void main(String[] args) {
        tryKey("531SQSUIAAAA");
    }
    
    public static void tryKey(String key) {
        String[] ss = {key};
        System.out.println(ENIGMA.encrypt(ss));
    }
    
    public static void test1() {
        String pt = "ABBCCCDDDDEEEEEFFFFFFGGGGGGGHHHHHHHHIIIIIIIIIJJJJJJJJJJ";
        String c1 = "YYTLHPOJNLULCFRVQOWMQFBOHOBMELLOGWALZFFMDTNBHKIYGITMQIS";
        String c2 = "KYWUKSZVOYULZFRVQOWMQFYODOYMMMEEZOYQCFFMHTNYDKIBGITMQIS";
        String k1 = "123KDB";
        String k2 = "123KDBAXBYCZDH";
        String[] t1 = {k2, c1};
        ENIGMA.encrypt(t1);
        String[] t2 = {k1, c2};
        ENIGMA.reset();
        ENIGMA.encrypt(t2);
    }
    
    public static void test2() {
        String pt = "Belikethenmyappetitewasnotprincelygotfor" +
                    "bymytrothIdonowrememberthepoorcreature" +
                    "smallbeer";
        pt = pt.toUpperCase();
        System.out.println(pt);
        String k1 = "547KDBENTAKO";
        String k2 = "547KDB";
        String ss[] = {k1, pt};
        String c1 = ENIGMA.encrypt(ss);
        System.out.println(c1);
        ENIGMA.reset();
        ss[0] = k2;
        ss[1] = c1;
        String c2 = ENIGMA.encrypt(ss);
        System.out.println(c2);
        String cr = c2.replace('E', 'n');
        cr = cr.replace('N', 'E');
        cr = cr.replace('n', 'N');
        cr = cr.replace('T', 'a');
        cr = cr.replace('A', 'T');
        cr = cr.replace('a', 'A');
        cr = cr.replace('K', 'o');
        cr = cr.replace('O', 'K');
        cr = cr.replace('o', 'O');
        System.out.println(cr);
        System.out.println(pt);
    }
}
