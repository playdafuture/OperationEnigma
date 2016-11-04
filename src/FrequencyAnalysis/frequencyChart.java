package FrequencyAnalysis;

public class frequencyChart {
    char[] charArray;
    double[] freqArray;
    int size;
    
    public frequencyChart(int initSize) {
        size = initSize;
        charArray = new char[size];
        freqArray = new double[size];
    }
    
    public void set(int index, char c, double f) {
        charArray[index] = c;
        freqArray[index] = f;
    }
    
    public void printChart() {
        System.out.println("Size = " + size);
        for (int i = 0; i < size; i++) {
            System.out.print(charArray[i] + " ");
            System.out.print(freqArray[i]*100 + "%");
            System.out.println();
        }
    }

    void divide(int interval) {
        if (interval <= 1) return;
        for (int i = 0; i < size; i++) {
            charArray[i] = '*'; 
            //the charArray becomes irrelavant when calculating the average
            freqArray[i] /= interval;
        }
    }

    void add(linkedList.Node n) {
        int i = 0;
        while (n != null) {
            set(i, n.getAlphabet(), freqArray[i] + n.getFrequency());
            i++;
            n = n.getNext();
        }
    }
    
    /**
     * Compares with another chart and calculates "how different" it is.
     * @param c The other chart object.
     * @return Average difference per alphabet in the set. 
     * 0.01 means average 1% difference.
     */
    double difference(frequencyChart c) {
        if (this.size != c.size) {
            System.out.println("ERROR! Different size comparison");
            return -1;
        }
        double totalDiff = 0;
        double charDiff = 0;
        for (int i = 0; i < size; i++) {
            charDiff = this.freqArray[i] - c.freqArray[i];
            if (charDiff < 0) {
                charDiff *= -1;
            }
            totalDiff += charDiff;
        }        
        return totalDiff/size;
    }
}
