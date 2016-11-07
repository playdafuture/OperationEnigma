
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/*
 * Language Recognition class
 * 
 */
public class Lang_rec {
	char[] vowel_array={'A','E','I','O','U'};
	HashMap<Character, HashMap<Character,Double>> model1;
	HashMap<String,HashMap<Character,Double>> model2;
	HashMap<Character, Double> hash_a_z;
	String two_vowel;
	char char1,char2,char_current;
	
	double MODEL1_PASS_LINE=40;
	double MODEL2_PASS_LINE=10;
	//int MODEL1_NUM_TEST=0;
	//int MODEL2_NUM_TEST=0;
	double ENGLISH_PASS_LINE=0.78;
	
	
	/*
	 * Constructor 
	 */
	public Lang_rec(){
		createModel();
		
	}
	/*
	 *  Testing English method
	 */
	public boolean lang_test(String text){
		char char3;
		double score=0;
	
		double model1_test_times=0;
		double model2_test_times=0;
		double weight=2.0;
		//read chars
		
		for(int i=0;i<text.length()-2;i++){
			char1=text.charAt(i);
			char2=text.charAt(i+1);
			char3=text.charAt(i+2);
			double  prob;
			// test in model1
			if(!isVowel(char1))continue;
			prob=model1.get(char1).get(char2).doubleValue();
			if(prob>MODEL1_PASS_LINE)score++;
			model1_test_times++;
			
			// test in model2
			if(!isVowel(char2))continue;
			two_vowel=""+char1+char2;
			prob=model2.get(two_vowel).get(char3).doubleValue();
			if(prob>MODEL2_PASS_LINE)score+=weight;
			model2_test_times++;
					
		}
		//is it english?
	
		double prob=score/(model1_test_times+weight*model2_test_times);
		//System.out.println(prob);
		if(prob>ENGLISH_PASS_LINE)return true;
		else return false;
		
	}
	public void createModel(){
		buildTwoModel();
		
		try {	
		readingBooks();
		toPercent();
		print();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	private void buildTwoModel() {
		// create model1
		model1=new HashMap<Character, HashMap<Character,Double>>();
	     
		for(int i=0;i<vowel_array.length;i++){
			hash_a_z=new HashMap<Character,Double>();
    	 		for(char j='A';j<='Z';j++)hash_a_z.put(j, new Double(0));
    	 		model1.put(new Character(vowel_array[i]), hash_a_z);
		}
		// create model2
		 model2=new HashMap<String,HashMap<Character,Double>>();
		 
		 for(int i=0;i<vowel_array.length;i++){
			 for(int k=0;k<vowel_array.length;k++){
				two_vowel=""+vowel_array[i]+vowel_array[k];
				hash_a_z=new HashMap<Character,Double>();
	    	 		for(char j='A';j<='Z';j++)hash_a_z.put(j, new Double(0));
	    	 		model2.put(two_vowel, hash_a_z); 
			 }
		 }
			
	}
	private void readingBooks() throws IOException {
		int numberOfBooks=5;
 		for(int index=1;index<=numberOfBooks;index++){
 			String file_name=index+".txt";
    	 		oneBookReading(file_name);
 		}	
	}
	public void oneBookReading(String file_name) throws IOException{
		int temp;
        FileReader inputStream = new FileReader(file_name);
           
        while ((temp = inputStream.read()) != -1) {
        		char_current=(char)temp;
        		if(isAtoZ(char_current)==false)continue;
        		char_current= Character.toUpperCase(char_current);
        		
        		insertToModel(char_current);
        		/*update previous chars*/
        		char1=char2;
        		char2=char_current;
        }
        inputStream.close();
	}
	
	private void insertToModel(char c) {
		// model1 count ( 1vowel follows a character ) 
		if(!isVowel(char2))return;
		hash_a_z=model1.get(new Character(char2));
		hash_a_z.put(new Character(c), new Double(hash_a_z.get(c).doubleValue()+1));
		
		// model2 count ( 2vowel follows a character )  
		if(!(isVowel(char1)))return;
		String two_vowels=""+char1+char2;
		hash_a_z=model2.get(two_vowels);
		hash_a_z.put(new Character(c), new Double(hash_a_z.get(c).doubleValue()+1));
		
		
	}
	public void print() throws IOException{
		 FileWriter model1_out = new FileWriter("model1.txt");
		 FileWriter model2_out = new FileWriter("model2.txt");
    
		//model1 print
		for (int i=0;i<model1.size();i++) {
			 hash_a_z=model1.get(vowel_array[i]);
			for(int j=0;j<hash_a_z.size();j++){
				char key=(char)('A'+j);
				double value=hash_a_z.get(key).doubleValue();
				if(value!=0){
					String out="After vowel " + vowel_array[i] + " the letter " + key + " come " + value+"\n";
					model1_out.write(out);
					System.out.print(out);
				}
			}
				  
		}
		//model2 print	
	   Set<String> keys=model2.keySet();
	   for (String k:keys) {
		   	hash_a_z=model2.get(k);
			for(int j=0;j<hash_a_z.size();j++){
				char key=(char)('A'+j);
				double value=hash_a_z.get(key).doubleValue();
				if(value!=0){
					String out="After vowel " + k + " the letter " + key + " come " + value+"\n";
					model2_out.write(out);
					System.out.print(out);
				}
			}
	   }
	   model1_out.close();
	   model2_out.close();
	}
	public void toPercent(){
		double sum=0;
		//model1 to percentage
		Set<Character> keyset_model1=model1.keySet();
		for (Character vowel:keyset_model1) {
			 hash_a_z=model1.get(vowel);
			 //get sum
			 for(int i=0;i<hash_a_z.size();i++){
				char key=(char)('A'+i);
				double value=hash_a_z.get(key).doubleValue();
				sum+=value;
			 }
			 //percent
			 for(int j=0;j<hash_a_z.size();j++){
				char key=(char)('A'+j);
				double value=hash_a_z.get(key).doubleValue();	
				if(value!=0)hash_a_z.put(key, new Double(value/sum*10000));
				}
		}
		//model2 to percentage
		Set<String> keyset_model2=model2.keySet();
		   for (String two_vowel:keyset_model2) {
				hash_a_z=model2.get(two_vowel);
				//count total of each vowel
				 sum=0;
				 for(int j=0;j<hash_a_z.size();j++){
					char key=(char)('A'+j);
					double value=hash_a_z.get(key).doubleValue();
					sum+=value;
				 }
				 
				for(int j=0;j<hash_a_z.size();j++){
					char key=(char)('A'+j);
					double value=hash_a_z.get(key).doubleValue();
					if(value!=0)hash_a_z.put(key, new Double(value/sum*10000));
					
				}
			  
			}
	}
	public double tf_lang_test(String text){
		char char3;
		double score=0;
	
		double model1_test_times=0;
		double model2_test_times=0;
		//double weight=2.0;
		//read chars
		
		for(int i=0;i<text.length()-2;i++){
			char1=text.charAt(i);
			char2=text.charAt(i+1);
			char3=text.charAt(i+2);
			double  prob;
			// test in model1
			if(!isVowel(char1))continue;
			prob=model1.get(char1).get(char2).doubleValue();
			if(prob>MODEL1_PASS_LINE)score++;
			model1_test_times++;
			
			// test in model2
			if(!isVowel(char2))continue;
			two_vowel=""+char1+char2;
			prob=model2.get(two_vowel).get(char3).doubleValue();
			if(prob>MODEL2_PASS_LINE)score++;
			model2_test_times++;
					
		}
		//is it english?
	
		double prob=score/(model1_test_times+model2_test_times);
		//System.out.println(prob);
		return prob;
		
	}
	public static boolean isAtoZ(char c){return (c>='a'&&c<='z')||(c>='A'&&c<='Z');	}
	public static boolean isVowel(char x){
		return x=='A'||x=='E'||x=='I'||x=='O'||x=='U';
	}
}

