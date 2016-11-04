package FrequencyAnalysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {
    public static final int minInterval = 3;
    public static final int maxInterval = 12;
    public static String encryptedText;
    public static frequencyChart standardChart;
    public static void main(String[] args) {
        solve1();
    }
    
    public static void solve1() {
        try {
            load("plainText.txt","emptyFreqChart.txt");
        } catch (IOException ex) {
            System.out.println("IO Error");
        }
        int interval = 1;
        
        if (interval == 0) { //test all intervals between (and including) min-max
            double[] differences = new double[maxInterval-minInterval+1];
            for (int i = 0; i <= maxInterval - minInterval; i++) {
                frequencyChart etc = analyze(encryptedText,i+minInterval,0); //encrypted text chart
                differences[i] = etc.difference(standardChart);
                System.out.println("Interval = " + (i+minInterval) + ": " + differences[i]);
            }
        } else { //test only the specified interval and print result
            frequencyChart etc = analyze(encryptedText,interval,0);
            etc.printChart();
        }
    }
    
    public static void load(String eFileName, String cFileName) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(eFileName),"UTF-16"));
        encryptedText = br.readLine();
        //System.out.println(encryptedText);
        br.close();
        br = new BufferedReader(new InputStreamReader(new FileInputStream(cFileName),"UTF-16"));
        int lineCounter = 0;
        br.mark(100000);
        String line = "";
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            lineCounter++;
        }
        //System.out.println(lineCounter);
        
        standardChart = new frequencyChart(lineCounter);
        int i = 0;
        br.reset();
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            char c = line.charAt(0);
            double f = 0;
            if (line.charAt(line.length()-1) == '%') {
                f = Double.parseDouble(line.substring(2, line.length()-1));
                f /= 100;
            } else {
                f = Double.parseDouble(line.substring(2, line.length()));
            }            
            standardChart.set(i, c, f);
            i++;
        }
    }
    
    /**
     * Takes in a string and analyze the frequency of each character. 
     * The result will be stored in a "frequencyChart" object.
     * e.g. "aabc" will return a .5, b .25, c .25
     * The standard chart should be created before calling this function 
     * to make sure it has proper size.
     * @param s The text to analyze
     * @param interval also known as the key length. 
     * returns the AVERAGE when interval > 1.
     * @param detail level of detailed information to display
     * @return "frequencyChart" object
     */
    public static frequencyChart analyze(String s, int interval, int detail) {        
        linkedList[] list = new linkedList[interval];
        frequencyChart results = new frequencyChart(standardChart.size);
        for (int i = 0; i < interval; i++) {
            
            list[i] = new linkedList();            
            for (int j = i; j < s.length(); j+=interval) {
                list[i].add(s.charAt(j));
            }
            list[i].sort();
            list[i].calculateFrequency();            
            results.add(list[i].head);
            if (detail == 2) {
                System.out.println("-Start-of-list " + (i+1) +"-");
                list[i].printFrequency(); //detailed frequecy info for each position
                System.out.println("^End-of-list " + (i+1) +"^");
            } else if (detail == 1) {
                System.out.println((i+1) + ":" + list[i].top8());
                System.out.println((i+1) + ":" + list[i].bot4());
            } else {
                System.out.println((i+1) + ":" + list[i].bot4());
            }
        }
        results.divide(interval);
        //System.out.println("End of Interval = " + interval);        
        return results;     
    }
    
}
