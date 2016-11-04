import java.util.Random;


public class Driver {
	static Lang_rec rec;
	static Random r;
	public static void main(String[] args) {
		rec=new Lang_rec();
		randTextTry();
		regTextTry();
		
		
	}
	
	private static void regTextTry() {
		String reg_text="en engulfed in civil war. After Mike Barnicle, commentator who is often ";
		reg_text=reg_text.replaceAll("[^a-zA-Z]", "");
		reg_text=reg_text.toUpperCase();
		System.out.println(reg_text);
		System.out.println(rec.lang_test(reg_text));
		
	}

	public static void randTextTry(){
		r = new Random();
		int numberOfTry=20000;
		int n_true=0,n_false=0;
		
		for(int i=0;i<numberOfTry;i++){
			if(oneTimeTry())n_true++;
			else n_false++;
		}
		System.out.println("percent of random text can be passed this test is " + (double)n_true/numberOfTry);
		
	}
	private static boolean oneTimeTry() {
		//
		
		String text="";
		for(int i=0;i<30;i++){
			int gen_int=r.nextInt(26) ;
			//System.out.println("generated int is : "+gen_int);
			char  letter=  (char)(gen_int + 'A');
			text+=letter;
		}
		return rec.lang_test(text);
		/*
		System.out.println("text is : " + text);
		if(rec.lang_test(text))System.out.println("english.");
		else System.out.println("not english.");
		*/
	}
}
