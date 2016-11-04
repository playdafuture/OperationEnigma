import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
	public static void main(String[] args) {
		int[] array=new int[125];
		Arrays.fill(array, -1);
		String word;
		String line = "This order was placed for QT3000! OK?";
	    String pattern = "(\\d+)";
		TextFileInput file=new TextFileInput("frequency.txt");
		word=file.readLine();
		int index=0;
		while(word!=null){
			Pattern r = Pattern.compile(pattern);

		      // Now create matcher object.
		      Matcher m = r.matcher(word);
		      if (m.find( )) {
		    	  	String s=m.group(0);
		    	  	 //System.out.println("Found value: " + s );
		    	  	array[index]=Integer.parseInt(s);
		    	  	 //System.out.println(Integer.parseInt(s) );
		    	  	index++;
		         System.out.println("Found value: " + s );
		       
		      }else {
		         System.out.println("NO MATCH");
		      }
		      word=file.readLine();
		}
		Arrays.sort(array);
		for(int i=0;i<array.length;i++){
			System.out.println(array[i]);
		}
		double percent=95.0;
		index=(int)(percent/100*125);
		System.out.println("pivit point is :" + array[index]);
        
    

	}
}
