package languageRecognition;

import java.util.HashMap;

class TrieNode {
    char c;
    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    Double count;
    Double value=0.0;
    boolean isLeaf;
    public TrieNode() {}
    public TrieNode(char key_char){
    		if(!isAtoZ(key_char)){
    			System.out.println("key "+ key_char+" is not in A-Z");
    			System.exit(1);
    		}
        this.c =key_char ;
    }
    
    public static boolean isAtoZ(char c){return (c>='A'&&c<='Z');	}
	public static boolean isVowel(char x){
		return x=='A'||x=='E'||x=='I'||x=='O'||x=='U';
	}
}