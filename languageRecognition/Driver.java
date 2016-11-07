import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


public class Driver {
	static Lang_rec rec;
	static Random r;
	static int count=0;
	public static void main(String[] args) {
		rec=new Lang_rec();
		
		try {
			bestConfigurations();
			//real_english_test();
			//random_text_test();
			//ratio_test();
			filterDecryptText();
			System.out.println(count);
			
		} catch (Exception e) {e.printStackTrace();}
		
		
		
	}
	
	private static void ratio_test() {
			String x="BELIKETHENMYAPPETITEWASNOTPRINCELYGOTFORBYMYTROTHIDONOWREMEMBERTHEPOORCREATURESMALLBEER  BNLISVAJNEMYTPRNAIANWTSEKAPPIECNLYUKAFGBBJMYAQKAHIDKLKQRNMNMBXRAHNPOWRTJNTGURLSGSLLBNNR";
			System.out.println(rec.tf_lang_test(x));
	}

	private static void filterDecryptText() throws IOException {
		String path="/Users/boom/Downloads/OperationEnigma-batchTesting/files/decrypts";
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
	
	}
	
	private static void readFile(File file_name) throws IOException {
		FileInputStream fis = new FileInputStream(file_name);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		
		while ((line = br.readLine()) != null) {
			
			String text=line.substring(0, 67);
			//System.out.println(text);
			if(rec.lang_test(text))count++;
			//System.out.println(count);
			
		}
	 
		br.close();
	}

	private static void bestConfigurations() throws IOException {
		double min=6000000.0;
		double found_i=0,found_j=0;
		for(double j=9;j<30;j++){
			for(double i=1;i<2;i++){
				
				rec.MODEL1_PASS_LINE=i;
				rec.MODEL2_PASS_LINE=j;
				//System.out.println("i : "+i+"  j :"+ j);
				filterDecryptText();
				if(count<min){min=count;found_i=i;found_j=j;}
				
			}
		}
		System.out.println("min count is : "+count+" found i is : "+found_i+"found j is : " +found_j);		
		
	}

	/*
	 * English 100%
	 */
	private static void real_english_test() throws IOException {
		int NUM_STRING=6000000;
		int temp;
        String text="";
        int count_tests=0;
        double score=0;
        
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
        			count_tests++;
        			text="";
        		}
        		
        }
        double percent_of_passing=score/count_tests;
        System.out.println("the real english passing test is : " + percent_of_passing);
        inputStream.close();
		
	}
	/*
	 * Random pass rate 0.6666*E-3
	 */
	private static void random_text_test() {
		r = new Random();
		int numberOfTry=2000000;
		int score=0;
		
		for(int i=0;i<numberOfTry;i++){
			if(oneRandTry())score++;	
		}
		double percent_of_passing=score/numberOfTry;
		System.out.println("percent of random text can be passed this test is " + percent_of_passing );
	}
	private static boolean oneRandTry() {
		
		String text="";
		for(int i=0;i<67;i++){
			int gen_int=r.nextInt(26) ;
			char  letter=  (char)(gen_int + 'A');
			text+=letter;
		}
		return rec.lang_test(text);
	}

	
	
	
	
}
