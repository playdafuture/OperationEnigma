
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/*
 * Language Recognition class
 */
public class Lang_rec {
	char[] vowel_array={'A','E','I','O','U'};
	char char1,char2,char_current;
	Trie model1;
	
	String str;
	TrieNode node;
	double total_tests=0;
	double score=0;
	double MODEL1_PASS_LINE=10;
	double ENGLISH_PASS_LINE=0.80;
	/*
	 * Constructor 
	 */
	public Lang_rec() throws IOException{
		
		 createModel();
		
		
		
	}
	/*
	 *  Testing English method
	 */
	public boolean lang_test(String text){
		
		 str="";
		//TrieNode node;
		 total_tests=0;
		 score=0;
		 MODEL1_PASS_LINE=1;
		 ENGLISH_PASS_LINE=0.78;
		//char1
		int charNum=1;
		//test(text,charNum);
		//char2
		test(text,++charNum);
		//char3
		test(text,++charNum);
		//is it english?
		
			double prob=score/total_tests;
			//System.out.println(prob);
			if(prob>ENGLISH_PASS_LINE){
				
				return true;
			}
			else return false;		
	}
	public double lang_test_double(String text){
		
		 str="";
		//TrieNode node;
		 total_tests=0;
		 score=0;
		 MODEL1_PASS_LINE=1;
		 ENGLISH_PASS_LINE=0.78;
		//char1
		int charNum=1;
		//test(text,charNum);
		//char2
		test(text,++charNum);
		//char3
		test(text,++charNum);
		//is it english?
		
			double prob=score/total_tests;
			//System.out.println(prob);
			return prob;		
	}
	public void test(String text,int n){
		
		for(int i=0;i<text.length()-n;i++){
			//System.out.println("node.value: ");
		    str=text.substring(i,i+n);
		   // System.out.println("str "+ str);
			node=model1.searchNode(str);
			//System.out.println("node.value: "+ node.value.doubleValue());
			if(node==null)total_tests++;
			

			else  if(node.value.doubleValue()>MODEL1_PASS_LINE){
				total_tests++;
				score++;
			}
			else total_tests++;
		}
	}
	public void createModel(){
		model1=new Trie();
		try {	
		readingBooks();
		model1.toPercent();
		print();
		} catch (IOException e) {e.printStackTrace();}
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
        
        String str="";
        while ((temp = inputStream.read()) != -1) {
        		
        		char_current=(char)temp;
        		if(!isAtoZ(char_current))continue;
        		str+=char_current;
        		
        		if(str.length()==3){
        			str=str.toUpperCase();
        			//System.out.println(str);
        			model1.insert(str);
        			str="";
        		}
        }
        inputStream.close();
	}
	
	
	public void print() throws IOException{
		 
		//model1 print
		
		
		System.out.println(model1);
	
	  
	}
	
	
	public static boolean isAtoZ(char c){return (c>='a'&&c<='z')||(c>='A'&&c<='Z');	}
	public static boolean isVowel(char x){
		return x=='A'||x=='E'||x=='I'||x=='O'||x=='U';
	}
}

