import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;


public class Lang_rec {
	HashMap<Character, HashMap<Character,Integer>> count;
	HashMap<String,HashMap<Character,Integer>> two_vowels_count;
	int threshold_1vowel=1100;
	int threshold_2vowel=7;
	int total_char_num=0;
	char[] vowel_array={'A','E','I','O','U'};
	char c1,c2,c;//c1,c2,c are the chars we reading c1 ->c2->c
	public Lang_rec(){
		//String file_name="1.txt";
		create();
		//print();
		toPercent();
		print();
	}
	public boolean lang_test(String text){
		char l1,l2,l3;
		int num_test=2;
		int result=0;
		int weight=3;
		int total_weight=0;
		//boolean[] result = new boolean[num_test];
		//for(int i=0;i<num_test;i++)result[i]=false;
		int count_test=0;
		//find two consecutive vowels
		for(int i=0;i<text.length()-2;i++){
			l1=text.charAt(i);
			l2=text.charAt(i+1);
			l3=text.charAt(i+2);
			
			/*one vowels test*/
			if(count_test<num_test&&isVowel(l1)){
				int percent=count.get(l1).get(l2);
				System.out.println("checking"+l1+" followed by "+l2); 
				if(percent>threshold_1vowel)result++;//if it is english
				total_weight++;
				count_test++;
				
			}
			/*two vowels test*/
			
			if(count_test<num_test&&isVowel(l1)&&isVowel(l2)){
				System.out.println("checking"+l1+l2+" followed by "+l3); 
				//search two_vowels_tree
				int percent=two_vowels_count.get(""+l1+l2).get(l3).intValue();
				//System.out.println(""+l1+l2+" value is " + percent);
				
				if(percent>threshold_2vowel)result+=weight;
				total_weight+=weight;
				count_test++;
				
			}
			
		}
		//System.out.println(result[0]);
		if(count_test!=0&&(double)result/total_weight>0.8)return true;
		else return false;
		
		
	}
	public void create(){
		 int numberOfBooks=5;
		 
		 count=new HashMap<Character, HashMap<Character,Integer>>();
		 two_vowels_count=new HashMap<String,HashMap<Character,Integer>>();
	     //initialize hashmap
	     for(int i=0;i<vowel_array.length;i++){
	    	 	HashMap<Character,Integer> alphabetMap=new HashMap<Character,Integer>();
	    	 	for(char j='A';j<='Z';j++)alphabetMap.put(j, new Integer(0));
	    	 	count.put(new Character(vowel_array[i]), alphabetMap);
	     }
	    


	     try {
	    	 		
	    	 		for(int file_index=1;file_index<=numberOfBooks;file_index++){
	    	 			String file_name=file_index+".txt";
		    	 		oneBookReading(file_name);
	    	 		}
	    	 		

	            
	     } catch(Exception e){
	    	 	System.out.print(e);
	     }
	     //System.out.print(isVowel('K'));
	     
	     
	     
	}
	public void oneBookReading(String file_name) throws IOException{
		int temp;
        FileReader inputStream = null;
		   inputStream = new FileReader(file_name);
           
        while ((temp = inputStream.read()) != -1) {
        		
        		c=(char)temp;//single char that read in
        		
        		
        		if(((c>='a'&&c<='z')||(c>='A'&&c<='Z'))==false)continue;//if not letter skip
        		total_char_num++;
        		c= Character.toUpperCase(c);
        		//System.out.print(c);
        		//single vowel followed by char initializing 
        		//if no 
        		HashMap<Character, Integer>  alphabetMap=count.get(new Character(c2));
        		if(alphabetMap!=null)alphabetMap.put(new Character(c), new Integer(alphabetMap.get(c).intValue()+1));
        		
        		if(isVowel(c1)&&isVowel(c2)){
        			String two_key=""+c1+c2;
        			/*initialize with that two vowels*/
        			if(two_vowels_count.get(two_key)==null){//if two vowels not exsist as a key
            			HashMap<Character,Integer> m= new  HashMap<Character,Integer>();
            			for(char j='A';j<='Z';j++)m.put(j, new Integer(0));
            			two_vowels_count.put(two_key,m );
        			}
        			
        			/*add one - count*/
        			else{
            			alphabetMap=two_vowels_count.get(two_key);
	            		alphabetMap.put(new Character(c), new Integer(alphabetMap.get(c).intValue()+1));
            		}
        			
        		}
        
        		/*update previous chars*/
        		c1=c2;
        		c2=c;
        }
        inputStream.close();
	}
	public boolean isVowel(char x){
		return x=='A'||x=='E'||x=='I'||x=='O'||x=='U';
	}
	public void print(){
		//single vowel
		for (int i=0;i<count.size();i++) {
			 HashMap<Character, Integer> alphaMap=count.get(vowel_array[i]);
			for(int j=0;j<alphaMap.size();j++){
				char key=(char)('A'+j);
				int value=alphaMap.get(key).intValue();
				if(value!=0)System.out.println("after vowel " + vowel_array[i] + " the letter " + key + " come " + value);
			}
		  
		}
		//double vowels
		
	   Set<String> keys=two_vowels_count.keySet();
	   for (String k:keys) {
		   
			HashMap<Character, Integer> alphaMap=two_vowels_count.get(k);
			for(int j=0;j<alphaMap.size();j++){
				char key=(char)('A'+j);
				int value=alphaMap.get(key).intValue();
				if(value!=0)System.out.println("after vowel " + k + " the letter " + key + " come " + value);
			}
		  
		}
	   System.out.println("total number of chars is " + total_char_num);
	   
	
		
	}
	public void toPercent(){
		//single vowel
		Set<Character> k1v=count.keySet();
		for (Character vowel:k1v) {
			 HashMap<Character, Integer> alphaMap=count.get(vowel);
			for(int j=0;j<alphaMap.size();j++){
				char key=(char)('A'+j);
				int value=alphaMap.get(key).intValue();
				//System.out.println("after vowel " + vowel + " the letter " + key + " come " + value);
				
				if(value!=0)alphaMap.put(key, new Integer((int)((double)value/total_char_num*100000)));
				
			}
		  
		}
		//double vowels
		
	   Set<String> k2v=two_vowels_count.keySet();
	   for (String k:k2v) {
			HashMap<Character, Integer> alphaMap=two_vowels_count.get(k);
			for(int j=0;j<alphaMap.size();j++){
				char key=(char)('A'+j);
				int value=alphaMap.get(key).intValue();
				if(value!=0)alphaMap.put(key, new Integer((int)((double)value/total_char_num*100000)));
				
			}
		  
		}
	}
	public boolean isAtoZ(char x){
		return x>='A'&&x<='Z';
		
	}
}
