package languageRecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;


public class Driver {
	static Lang_rec rec;
	static Random r;
	static int count=0;
	static FileWriter filteredText;
	static int likeArray[][];
	public static void main(String[] args) {

		try {
			rec=new Lang_rec();
			//real_english_test();
			//filterDecryptText();
			likeTry();
			
		} catch (Exception e) {e.printStackTrace();}
		
		
		
	}

	
	private static void likeTry() throws IOException {
		likeArray=new int[6][101];
		String file_name;
		for(int i=1;i<=5;i++){
			
			file_name=i+"c.txt";
			likelihoodTest(file_name);
		}
		for(int i=0;i<6;i++){
			System.out.println("group no."+i);
			for(int j=0;j<101;j++){	
				System.out.println(j+" % =>"+likeArray[i][j]+"  ");
			}
		
		}
		
	}
	
	private static void real_english_test() throws IOException {
		int NUM_STRING=6000000;
		int temp;
        String text="";
        int count_tests=0;
        double score=0;
        
        FileWriter out = new FileWriter("reg_english_rejected.txt");
        FileReader inputStream = new FileReader("6.txt");
        
        while ((temp = inputStream.read()) != -1) {
        		if(count_tests>NUM_STRING)break;
        		char char_current=(char)temp;
        		if(Lang_rec.isAtoZ(char_current)==false)continue;
        		char_current= Character.toUpperCase(char_current);
        		text+=char_current;
        		//do language test
        		if(text.length()==67){
        			if(rec.lang_test(text))score++;
        			else {
        				out.write(text+"\n");
        			}
        			count_tests++;
        			text="";
        		}
        		
        }
        double percent_of_passing=score/count_tests;
        System.out.println("the real english passing test is : " + percent_of_passing);
        inputStream.close();
		
	}
	private static void filterDecryptText() throws IOException {
		filteredText = new FileWriter("filteredText.txt");

		String path="/Users/boom/Downloads/OperationEnigma-batchTesting 2/files/decrypts";
		count=0;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    		String file_name=file.getName();
		    		
		    		if(!file_name.endsWith(".txt"))continue;
		        //System.out.println(file_name);
		    		//start file read
		    		readFile(file);
	            //finish file read
		    		
		    }
		}
		 System.out.println("count is : " +count);
		 System.out.println("total_tests is : " +rec.total_tests);
		 
		filteredText.close();
	
	}
	
	private static void readFile(File file_name) throws IOException {
		FileInputStream fis = new FileInputStream(file_name);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		        
		String line = null;
		
		while ((line = br.readLine()) != null) {
			
			String text=line.substring(0, 67);
			//System.out.println(text);
			if(rec.lang_test(text)){
				//System.out.println(text.length());
				count++;
				
				filteredText.write(text+"    "+file_name.getName().substring(0,4)+"   "+line.substring(67)+"\n");
				}
			//System.out.println(count);
			
		}
	 
		br.close();
	}
	public static boolean likelihoodTest(String file_name) throws IOException{
		FileInputStream fis = new FileInputStream(file_name);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		        
		String line = null;
		int index=0;
		
		
		double pair[]=new double[6];

		while ((line = reader.readLine()) != null) {
			
			if(line.length()<67)continue;
			//System.out.println("index is : "+index+"   " +line);
			if(index==6){
				index=0;
				Arrays.fill(pair, 0);
			}
			double prob=rec.lang_test_double(line)*100;
			pair[index]=prob;
			likeArray[index][((int)prob)]++;
			
			index++;
			if(index==6)for(int i=0;i<6;i++)System.out.println(i+" => "+pair[i]);
			
		}
		//System.out.println("error is : "+error+ " / "+total);
		reader.close();	
		return false;
	}
	
	
}
