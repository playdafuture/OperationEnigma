import java.util.HashMap;
import java.util.Set;

public class Trie {
    private TrieNode root;
    public HashMap<Character,TrieNode> map;
    TrieNode node;
    public Trie() {
        root = new TrieNode();
    }
 
    public void toPercent(){
		double sum=0;
	 	String out="";
	 	String key;
    	
		for(char i='A';i<='Z';i++){
			key=""+i;
			node=searchNode(key);
			if(node==null)continue;
		    sum+=node.value.doubleValue();
		}
		
		for(char i='A';i<='Z';i++){
			key=""+i;
			node=searchNode(key);
			if(node==null)continue;
		    node.value=valueDivSum(node.value,sum);
		}
		//2char
		
		for(char i='A';i<='Z';i++){
			sum=0;
			for(char j='A';j<='Z';j++){
				key=""+i+j;
	 			node=searchNode(key);
	 			if(node==null)continue;
	 			sum+=node.value.doubleValue();
			}
			if(sum==0.0)continue;
			for(char j='A';j<='Z';j++){
				key=""+i+j;
	 			node=searchNode(key);
	 			if(node==null)continue;
	 			node.value=valueDivSum(node.value,sum);
			}
		}
		//3char
		sum=0;
		for(char i='A';i<='Z';i++){
			for(char j='A';j<='Z';j++){
				sum=0;
				for(char k='A';k<='Z';k++){
					key=""+i+j+k;
	 	 			node=searchNode(key);
	 	 			if(node==null)continue;
	 	 			sum+=node.value.doubleValue();
				}	
				if(sum==0.0)continue;
				for(char k='A';k<='Z';k++){
					key=""+i+j+k;
	 	 			node=searchNode(key);
	 	 			if(node==null)continue;
	 	 			node.value=valueDivSum(node.value,sum);
				}	
			}
		}
		
		
		
	}
    

	public void insert(String string) {
        HashMap<Character, TrieNode> children = root.children;
 
        for(int i=0; i<string.length(); i++){
            char key = string.charAt(i);
            TrieNode node;
            if(children.containsKey(key)){
            		
                    node = children.get(key);
                    node.value=node.value+1;
            }else{
                node = new TrieNode(key);
                children.put(key, node);
            }
 
            children = node.children;
 
            //set leaf node
            if(i==string.length()-1){
            	node.isLeaf = true;
            }   
        }
    }
 
    public TrieNode searchNode(String str){
        HashMap<Character, TrieNode> children = root.children; 
        TrieNode node = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                node = children.get(c);
                children = node.children;
            }else{
                return null;
            }
        }
 
        return node;
    }
    public String printTrie(String str,TrieNode node){    	
    		if(node.isLeaf)return "";
    			String out="-------\n";
       		HashMap<Character,TrieNode> children=node.children;
	    		Set<Character> keys=children.keySet();
	    		for(Character key:keys){
	    			node=children.get(key);
	    			out=out+str+key+" ==> "+node.value+"\n";	
	    			out+=printTrie(str+key,node);		
	    		}
		return out;
    }
    public String toString(){
    	//return printTrie("",root);
	    	String out="";
	    	String key;
	    	
 		for(char i='A';i<='Z';i++){
 			key=""+i;
 			node=searchNode(key);
 			if(node==null)continue;
 		    out=out+key+" ==> "+node.value+"\n";
 		}
 		for(char i='A';i<='Z';i++){
 			for(char j='A';j<='Z';j++){
 				key=""+i+j;
 	 			node=searchNode(key);
 	 			if(node==null)continue;
 	 		    out=out+key+" ==> "+node.value+"\n";
 			}
 		}	
 		for(char i='A';i<='Z';i++){
 			for(char j='A';j<='Z';j++){
 				for(char k='A';k<='Z';k++){
 					key=""+i+j+k;
 	 	 			node=searchNode(key);
 	 	 			if(node==null)continue;
 	 	 		    out=out+key+" ==> "+node.value+"\n";
 				}	
 			}
 		}
	  
	    return out;
    };
    private Double valueDivSum(Double value, double sum) {
		if(sum==0.0){ 
			System.out.println("can not divide by 0");
			System.exit(1);
		}
		return new Double(value.doubleValue()/sum*1000);
	}
}