import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;


public class Driver {
	static Lang_rec rec;
	static Random r;
	public static void main(String[] args) {
		rec=new Lang_rec();
		
		try {
			bestConfigurations();
			//real_english_test();
		} catch (Exception e) {e.printStackTrace();}
		//random_text_test();
		
	}
	
	private static void bestConfigurations() throws IOException {
		for(int j=3;j<30;j=j+2){
			for(int i=1;i<30;i+=5){
				rec.MODEL1_PASS_LINE=i;
				rec.MODEL2_PASS_LINE=j;
				System.out.println("i : "+i+"  j :"+ j);
				real_english_test();
				random_text_test();
				
			}
		}
				
		
	}

	/*
	 * English 100%
	 */
	private static void real_english_test() throws IOException {
		int NUM_STRING=20000;
		int temp;
        String text="";
        int count_tests=0;
        int score=0;
        
        FileReader inputStream = new FileReader("6.txt");
        
        while ((temp = inputStream.read()) != -1) {
        		if(count_tests>NUM_STRING)break;
        		char char_current=(char)temp;
        		if(Lang_rec.isAtoZ(char_current)==false)continue;
        		char_current= Character.toUpperCase(char_current);
        		text+=char_current;
        		//do language test
        		if(text.length()==77){
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
		int numberOfTry=200000;
		int score=0;
		
		for(int i=0;i<numberOfTry;i++){
			if(oneRandTry())score++;	
		}
		double percent_of_passing=score/numberOfTry;
		System.out.println("percent of random text can be passed this test is " + percent_of_passing );
	}
	private static boolean oneRandTry() {
		
		String text="";
		for(int i=0;i<77;i++){
			int gen_int=r.nextInt(26) ;
			char  letter=  (char)(gen_int + 'A');
			text+=letter;
		}
		return rec.lang_test(text);
	}

	
	
	
	
}
