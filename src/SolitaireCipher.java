package assignment2;

public class SolitaireCipher {
	
	public static Deck key;
 
	public SolitaireCipher (Deck key) {
		this.key = new Deck(key);		 //Deep copy of the deck
	}
	
	
	public int[] getKeystream(int size) {
		
	int [] keystream_array = new int[size];

	for(int i=0; i< size; i++) {
				
				int keystream= key.generateNextKeystreamValue();
				keystream_array[i]= keystream;
				
			}
	
		
		return keystream_array;
	}
  	
	
 	public String encode(String msg) {

 		String new_msg="";
 		String encoded_msg="";
 		
 		for	(int i=0; i<msg.length();i++) {
 			boolean b2 = Character.isAlphabetic(msg.charAt(i));
 			if (b2) {
 				new_msg+=msg.substring(i, i+1);
 			}
 		}
 		
 		new_msg= new_msg.toUpperCase();

 		System.out.println(new_msg);
 		
 		int [] keystream_array= getKeystream(new_msg.length());
 		
 		
 		for	(int i=0; i<new_msg.length(); i++) {
 			encoded_msg += charRightShift(new_msg.charAt(i), keystream_array[i] );
 		}

 		return encoded_msg;
 	}
 
 
 	public String decode(String msg) {
 		
 		String decoded_msg="";
 		
 		int [] keystream_array= getKeystream(msg.length());
 		
 		for	(int i=0; i<msg.length(); i++) {
 			decoded_msg += charLeftShift(msg.charAt(i), keystream_array[i] );
 		}
 		
 		return decoded_msg;
 	}
 
 	
	private char charRightShift(char c, int i) {
		
		char final_char= 0;
		if (c < 'A' || c > 'Z') {
			return c; 
		}
		
		if (i>0) {
			int alphaPosition= c-'A';
			int finalPositionModulo = (alphaPosition + i)%26;
			int finalCharacter= finalPositionModulo+'A';
			final_char= (char) finalCharacter;
			
		}
		return final_char;
		
	}
	
	
	
	private char charLeftShift(char c, int i) {
		
		char final_char = 0;
		if (c < 'A' || c > 'Z') {
			return c; 
		}
		
		if (i>0) {
			int alphaPosition= c-'A';
			int finalPositionModulo = (alphaPosition -i) %26;
			if (finalPositionModulo<0) {
				finalPositionModulo= finalPositionModulo+26;
			}
			int finalCharacter= finalPositionModulo + 'A';
			final_char= (char) finalCharacter;	
		}
		
		return final_char;
		
	}

	
	
 
 
	
 	
}
