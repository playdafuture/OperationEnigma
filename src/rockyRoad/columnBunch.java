package rockyRoad;

import java.util.Vector;

public class columnBunch {
    static Vector<String> activeColumns;
    public static String[] allCols = {
        "HEVAXF", "CQAXY", "VDWBC", "JPXBWQ",
        "MCWJD", "PYWCSJ", "VTCDKU", "KCZDVY", //8
        "YXZFGH",        "VQKFHD",        "PSOFM",        "YWZFXM",
        "OSYF",        "ULOGRJ",        "YWVGTD",        "YAMGUC",
        "QKCHDV",        "RTQIAK",        "OPYJCS",        "CWMJDZ",
        "WFCJQO",        "QCKAX",        "BMRKTL",        "TCHKUA",
        "MLVZY",        "SOMFY",        "KYTMVX",        "CMLOZ",
        "XBMQKT",        "FCMQOL",        "CGKQWM",        "LOPRJC",
        "YWFSJQ",        "WVTDK",        "CHEUAX",        "AMCUJ",
        "YTCVXM",        "GKYWMV",        "PXBWQK",        "EVQXFH",
        "TCJXMB",        "QCVXY",        "NYXFG",        "VPSYFM",
        "SYWFX",        "ZNYF"
    };
    
    public static void main(String[] args) {
        System.out.println(greedyReduction());
    }
    
    public static String greedyReduction() {
        String result = "";
        activeColumns = new Vector<String>(0);
        int max = allCols.length;
        for (int i = 0; i < max; i++) {
            activeColumns.add(allCols[i]);
        }
        while (max > 0) {
            //find the top occuring letter, eliminate all columns with that letter
            int[] freq = new int[26];
            //load char counts
            for (int i = 0; i < max; i++) {
                String s = activeColumns.get(i);
                for (int j = 0; j < s.length(); j++) {
                    int charIdx = s.charAt(j) - 'A';
                    freq[charIdx]++;
                }
            }
            //find max
            int maxVal = freq[0];
            int maxIdx = 0;
            for (int i = 1; i < 26; i++) {
                if (freq[i] > maxVal) {
                    maxVal = freq[i];
                    maxIdx = i;
                }
            }
            char mostFreqChar = (char) ('A'+maxIdx);
            //eliminate columns
            for (int i = 0; i < max; i++) {
                if (activeColumns.get(i).contains(mostFreqChar+"")) {
                    activeColumns.remove(i);
                    max--;
                    i--;
                }
            }
            result += mostFreqChar;
        }
        return result;
    }
    
    public static void bruteForceReduction() {
        activeColumns = new Vector<String>();
        for (int a = 0; a < allCols[0].length(); a++) {
            for (int b = 0; b < allCols[1].length(); b++) {
                for (int c = 0; c < allCols[2].length(); c++) {
                    for (int d = 0; d < allCols[3].length(); d++) {
                        for (int e = 0; e < allCols[4].length(); e++) {
                            for (int f = 0; f < allCols[5].length(); f++) {
                                for (int g = 0; g < allCols[6].length(); g++) {
                                    for (int h = 0; h < allCols[7].length(); h++) {
                                        String colo = "";
                                        colo+=allCols[0].charAt(a);
                                        colo+=allCols[1].charAt(b);
                                        colo+=allCols[2].charAt(c);
                                        colo+=allCols[3].charAt(d);
                                        colo+=allCols[4].charAt(e);
                                        colo+=allCols[5].charAt(f);
                                        colo+=allCols[6].charAt(g);
                                        colo+=allCols[7].charAt(h);
                                        insert(colo);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } //end of loading initial columns
        System.out.println(activeColumns.capacity());
        for (int i = 8; i < allCols.length; i++) {
            for (int j = 0; j < activeColumns.capacity(); j++) {
                String s = activeColumns.elementAt(j);
                String t = allCols[i];
                boolean contains = true;
                for (int c = 0; c < t.length(); c++) {
                    if (s.contains(t.substring(c, c+1))) {
                        break;
                    }
                    contains = false;
                }
            }
        }
        System.out.println(activeColumns.capacity());
    }
    
    public columnBunch() {
        activeColumns = null;
    }
    
    public static void insert(String c) {
        for (int i = 1; i < 8; i++) {
            if (c.substring(0, 1).contains(c.substring(i, i+1))) {
                return;
            }
        }
        activeColumns.add(c);
    }
}
