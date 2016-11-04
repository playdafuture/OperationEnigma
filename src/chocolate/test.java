package chocolate;

public class test {
    public static void main(String[] args) {
        String pt = "ABBCCCDDDDEEEEEFFFFFFGGGGGGGHHHHHHHHIIIIIIIIIJJJJJJJJJJ";
        String c1 = "YYTLHPOJNLULCFRVQOWMQFBOHOBMELLOGWALZFFMDTNBHKIYGITMQIS";
        String c2 = "KYWUKSZVOYULZFRVQOWMQFYODOYMMMEEZOYQCFFMHTNYDKIBGITMQIS";
        String k1 = "123KDB";
        String k2 = "123KDBAXBYCZDH";
        String[] t1 = {k2, c1};
        ENIGMA.main(t1);
        String[] t2 = {k1, c2};
        ENIGMA.reset();
        ENIGMA.main(t2);
    }
}
