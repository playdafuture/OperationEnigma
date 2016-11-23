package rockyRoad;

/**
 * Dynamic object used to hold "columns" and reduce based on plugboard connections.
 * @author Future
 */
public class columnBunch {
    String lockedPairs; //load this from outside
    public String[] allCols; //load this from outside
    
    public columnBunch(String[] cs, String ps) {
        allCols = new String[cs.length];
        System.arraycopy(cs, 0, allCols, 0, cs.length);
        lockedPairs = ps;
    }
    
    public columnBunch(String[] cs) {
        allCols = new String[cs.length];
        System.arraycopy(cs, 0, allCols, 0, cs.length);
    }
    
    /**
     * Reduce those columns that's already satisfied by locked-in pairs.
     */
    public void pairReduction() {
        int removedCount = 0;
        for (int i = 0; i < lockedPairs.length(); i++) {
            String c = lockedPairs.charAt(i) + "";
            for (int j = 0; j < allCols.length; j++) {
                if (allCols[j].contains(c)) {
                    allCols[j] = ""; //so we don't need to worry about the damn null pointer
                    removedCount++;
                }
            }
        }
        //now remove empty columns
        String[] newCols = new String[allCols.length - removedCount];
        int q = 0;
        for (int i = 0; i < allCols.length; i++) {
            if (allCols[i].length() > 0) {
                newCols[q++] = allCols[i];
            }
        }
        allCols = newCols;
    }
    
    /**
     * Based on current locked in pairs and remaining columns, generate valid pairings.
     * This function should only be called after pairReduction();
     * This function should return an array of valid pairings back to its caller
     * The caller will then combine the RnR part of the key with each element 
     * of the array to generate potential decrypts.
     * @return complete and valid plugboard pair settings
     */
    public String[] generatePairs() {
        if (lockedPairs.length() == 6) {
            /**
             * should be most of the case.
             * this means either locked in pairs are already satisfying the columns
             * which will be checked
             * or, only one more pair is allowed
             */
            String[] extraPair = make1pair();                
            if (extraPair == null) {
                return null;
            } else {
                //make cartesian product, append partial key
                for (int i = 0; i < extraPair.length; i++) {
                    extraPair[i] += lockedPairs;
                }
                return extraPair;
            }                
        } else if (lockedPairs.length() == 4) {
            /**
             * should be less likely to happen but it will happen.
             * this means at least 1 more pair must be fulfilled, 
             * but also, at most 2 pairs
             * either "free" or decided by columns
             */
            String[] extraPairs = make2pairs();                
            if (extraPairs == null) {
                return null;
            } else {
                //make cartesian product, append partial key
                for (int i = 0; i < extraPairs.length; i++) {
                    extraPairs[i] += lockedPairs;
                }
                return extraPairs;
            }
        }
        return null;
    } //end of generate pairs
    
    /**
     * Produces 1 pair of the plugboard setting that satisfies all columns and current lockedPairs.
     * @return String[] of valid pairs, or NULL if it's impossible.
     */
    public String[] make1pair() {
        if (allCols.length > 2) {
            String[] allPoss = new String[36];
            int idx = 0;
            //load all poss
            for (int i = 0; i < allCols[0].length(); i++) {
                for (int j = 0; j < allCols[1].length(); j++) {
                    String a = allCols[0].charAt(i) + "";
                    String b = allCols[1].charAt(j) + "";
                    if (!a.equals(b)) {
                        allPoss[idx++] = a+b;
                    } //a few null in the end in case of duplicate letters or not full columns                
                }
            }
            int validCount = idx;
            for (int i = 2; i < allCols.length; i++) {
                String col = allCols[i];
                if (col != null) { //it could've been removed already
                    for (int j = 0; j < idx; j++) {
                        if (allPoss[j] != null && !satisfy(col,allPoss[j])) {
                            allPoss[j] = null;
                            validCount--;
                            if (validCount == 0) {
                                return null;
                            }
                        }
                    }
                }                
            }
            idx = 0;
            String[] result = new String[validCount];
            for (int i = 0; i < allPoss.length; i++) {
                if (allPoss[i] != null) {
                    result[idx++] = allPoss[i];
                }                
            }
            return result;
        } else if (allCols.length == 2) {
            //no need for extra column checking
            String[] allPoss = new String[36];
            int idx = 0;
            //load all poss
            for (int i = 0; i < allCols[0].length(); i++) {
                for (int j = 0; j < allCols[1].length(); j++) {
                    String a = allCols[0].charAt(i) + "";
                    String b = allCols[1].charAt(j) + "";
                    if (!a.equals(b)) {
                        allPoss[idx++] = a+b;
                    } //a few null in the end in case of duplicate letters or not full columns                
                }
            }
            String[] result = new String[idx];
            idx = 0;
            for (int i = 0; i < allPoss.length; i++) {
                if (allPoss[i] != null) {
                    result[idx++] = allPoss[i];
                }                
            }
            return result;
        } else if (allCols.length == 1) {
            //kind of a free pair
            //cannot contain: any letter in lockedPairs
            //must conaint: allCols[0]
            String[] allPoss = new String[26*26];
            int idx = 0;
            for (char a = 'A'; a <= 'Z'; a++) {
                for (char b = 'A'; b <= 'Z'; b++) {
                    if (a < b 
                            && !lockedPairs.contains(a+"") 
                            && !lockedPairs.contains(b+"")
                            && (allCols[0].contains(a+"") 
                                || allCols[0].contains(b+""))) {
                        allPoss[idx++] = a+""+b;
                    }
                }                                
            }
            String[] result = new String[idx];
            idx = 0;
            for (int i = 0; i < allPoss.length; i++) {
                if (allPoss[i] != null) {
                    result[idx++] = allPoss[i];
                }                
            }
            return result;
        } else {
            //absolutely free pair
            String[] allPoss = new String[26*26];
            int idx = 0;
            for (char a = 'A'; a <= 'Z'; a++) {
                for (char b = 'A'; b <= 'Z'; b++) {
                    if (a < b 
                            && !lockedPairs.contains(a+"") 
                            && !lockedPairs.contains(b+"")) {
                        allPoss[idx++] = a+""+b;
                    }
                }                                
            }
            String[] result = new String[idx+1];
            idx = 0;
            for (int i = 0; i < allPoss.length; i++) {
                if (allPoss[i] != null) {
                    result[idx++] = allPoss[i];
                }                
            }
            result[idx] = "";
            return result;
        }
    }
    
    /**
     * Produces 2 pairs of the plugboard setting that satisfies all columns and current lockedPairs.
     * @return String[] of valid pairs, or NULL if it's impossible.
     */
    public String[] make2pairs() {
        if (allCols.length > 4) { //load all first four and use remaining for reduction
            String[] allPoss = new String[36*36];
            int idx = 0;
            //load all poss
            for (int i = 0; i < allCols[0].length(); i++) {
                for (int j = 0; j < allCols[1].length(); j++) {
                    for (int k = 0; k < allCols[2].length(); k++) {
                        for (int l = 0; l < allCols[3].length(); l++) {
                            String a = allCols[0].charAt(i) + "";
                            String b = allCols[1].charAt(j) + "";
                            String c = allCols[2].charAt(k) + "";
                            String d = allCols[3].charAt(l) + "";
                            if (isValidPairing(a+b+c+d)) {
                                allPoss[idx++] = a+b+c+d;
                            }                            
                            //a few null in the end in case of duplicate letters or not full columns   
                        }
                    }                                 
                }
            }
            int validCount = idx;
            for (int i = 5; i < allCols.length; i++) {
                String col = allCols[i];
                if (col != null) { //it could've been removed already (by lockedPairs)
                    for (int j = 0; j < idx; j++) {
                        if (allPoss[j] != null && !satisfy(col,allPoss[j])) {
                            allPoss[j] = null;
                            validCount--;
                            if (validCount == 0) {
                                return null;
                            }
                        }
                    }
                }                
            }
            idx = 0;
            String[] result = new String[validCount];
            for (int i = 0; i < allPoss.length; i++) {
                if (allPoss[i] != null) {
                    result[idx++] = allPoss[i];
                }                
            }
            return result;
        } else if (allCols.length == 4) {
            //no need for extra column checking
            String[] allPoss = new String[36*36];
            int idx = 0;
            //load all poss
            for (int i = 0; i < allCols[0].length(); i++) {
                for (int j = 0; j < allCols[1].length(); j++) {
                    for (int k = 0; k < allCols[2].length(); k++) {
                        for (int l = 0; l < allCols[3].length(); l++) {
                            String a = allCols[0].charAt(i) + "";
                            String b = allCols[1].charAt(j) + "";
                            String c = allCols[2].charAt(k) + "";
                            String d = allCols[3].charAt(l) + "";
                            if (isValidPairing(a+b+c+d)) {
                                allPoss[idx++] = a+b+c+d;
                            }                            
                            //a few null in the end in case of duplicate letters or not full columns   
                        }
                    }                                 
                }
            }
            int validCount = idx;
            idx = 0;
            String[] result = new String[validCount];
            for (int i = 0; i < allPoss.length; i++) {
                if (allPoss[i] != null) {
                    result[idx++] = allPoss[i];
                }                
            }
            return result;
        } else if (allCols.length == 3) {
            // 1 free letter hanging there
            String[] allPoss = new String[6*6*6*26];
            int idx = 0;
            for (int i = 0; i < allCols[0].length(); i++) {
                for (int j = 0; j < allCols[1].length(); j++) {
                    for (int k = 0; k < allCols[2].length(); k++) {
                        for (char l = 'A'; l <= 'Z'; l++) {
                            String a = allCols[0].charAt(i) + "";
                            String b = allCols[1].charAt(j) + "";
                            String c = allCols[2].charAt(k) + "";
                            String d = l+"";
                            if (isValidPairing(lockedPairs+a+b+c+d)) {
                                allPoss[idx++] = a+b+c+d;
                            }
                        }                            
                    }
                }
            }
            int validCount = idx;
            idx = 0;
            String[] result = new String[validCount];
            for (int i = 0; i < allPoss.length; i++) {
                if (allPoss[i] != null) {
                    result[idx++] = allPoss[i];
                }                
            }
            return result;
        } else if (allCols.length == 2) {
            // a few <36 pairs from columns and another free pair
            String[] allPoss = new String[6*6*26*26];
            int idx = 0;
            for (int i = 0; i < allCols[0].length(); i++) {
                for (int j = 0; j < allCols[1].length(); j++) {
                    for (char k = 'A'; k <= 'Z'; k++) {
                        for (char l = 'A'; l <= 'Z'; l++) {
                            if (l <= k) {
                                continue; //deal with reverse. k should always less than l
                            }
                            String a = allCols[0].charAt(i) + "";
                            String b = allCols[1].charAt(j) + "";
                            String c = k + "";
                            String d = l + "";
                            if (isValidPairing(lockedPairs+a+b+c+d)) {
                                allPoss[idx++] = a+b+c+d;
                            }
                        }                            
                    }
                }
            }
            int validCount = idx;
            idx = 0;
            String[] result = new String[validCount];
            for (int i = 0; i < allPoss.length; i++) {
                if (allPoss[i] != null) {
                    result[idx++] = allPoss[i];
                }                
            }
            return result;                 
        } else if (allCols.length == 1) {
            //all the free 2 pairs that satisfies this column
            String[] allPoss = new String[6*26*26*26];
            int idx = 0;
            for (int i = 0; i < allCols[0].length(); i++) {
                for (char j = 'A'; j <= 'Z'; j++) {
                    if (j <= allCols[0].charAt(i)) {
                        continue;
                    }
                    for (char k = 'A'; k <= 'Z'; k++) {
                        for (char l = 'A'; l <= 'Z'; l++) {
                            if (l <= k) {
                                continue; //deal with reverse. k should always less than l
                            }
                            String a = allCols[0].charAt(i) + "";
                            String b = j + "";
                            String c = k + "";
                            String d = l + "";
                            if (isValidPairing(lockedPairs+a+b+c+d)) {
                                allPoss[idx++] = a+b+c+d;
                            }
                        }                            
                    }
                }
            }
            int validCount = idx;
            idx = 0;
            String[] result = new String[validCount];
            for (int i = 0; i < allPoss.length; i++) {
                if (allPoss[i] != null) {
                    result[idx++] = allPoss[i];
                }                
            }
            return result;
        } else {
            //absolutely free pair            
            String[] allPoss = new String[26*26*26*26];
            int idx = 0;
            for (char i = 'A'; i <= 'Z'; i++) {
                for (char j = 'A'; j <= 'Z'; j++) {
                    if (j <= i) {
                        continue;
                    }
                    for (char k = 'A'; k <= 'Z'; k++) {
                        for (char l = 'A'; l <= 'Z'; l++) {
                            if (l <= k) {
                                continue; //deal with reverse. k should always less than l
                            }
                            String a = i + "";
                            String b = j + "";
                            String c = k + "";
                            String d = l + "";
                            if (isValidPairing(lockedPairs+a+b+c+d)) {
                                allPoss[idx++] = a+b+c+d;
                            }
                        }                            
                    }
                }
            }
            int validCount = idx;
            idx = 0;
            String[] result = new String[validCount];
            for (int i = 0; i < allPoss.length; i++) {
                if (allPoss[i] != null) {
                    result[idx++] = allPoss[i];
                }                
            }
            return result;
        }
    }
    
    public void printAllCols() {
        for (int i = 0; i < allCols.length; i++) {
            System.out.println(allCols[i]);
        }
    }
    
    /**
     * Checks for repeated characters to determine if it's a valid "pairing setting".
     * @param p The pairing string you want to check
     * @return True if it's valid and false otherwise
     */
    public static boolean isValidPairing(String p) {
        for (int i = 1; i < p.length(); i++) {
            if (p.substring(0, i).contains((char)p.charAt(i)+"")) {
                return false;
            }
        }        
        return true;
    }

    private boolean satisfy(String col, String potPair) {
        for (int i = 0; i < col.length(); i++) {
            if (potPair.contains(col.charAt(i)+"")) {
                return true;
            }
        }
        return false;
    }
}
