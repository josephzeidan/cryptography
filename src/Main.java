package assignment2;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		
		System.out.println("Welcome to the Card Deck Cryptogtaphy: ");
		Scanner sc = new Scanner(System.in);
		boolean loop= false;
		do {
			
			System.out.println("Chose the number of cards per suit (1-13): ");
			int num= sc.nextInt();
			System.out.println("Chose the number of suit (1-4): ");
			int suit = sc.nextInt();
	 		Deck deck = new Deck(num,suit);
	        deck.shuffle();
	        SolitaireCipher cipher = new SolitaireCipher(deck);
	        System.out.println("Enter the message you want to encode: ");
	        String msg= sc.next();
	        
	        String encode= cipher.encode(msg);
	        System.out.println("Encoded Message: "+ encode);
//	        System.out.println("Now we will decode the output, we will get the original string: ");
//	        String decode = cipher.decode(encode);
//	        System.out.println("Decoded Message: "+ decode);
	        
	        System.out.println("Want to try another one? (y/n)");
	        String ans= sc.next();
	        if (ans.equalsIgnoreCase("y")) {
	        	loop= true;
	        }
	       
		}while(loop);

 		sc.close();
 	}

}
