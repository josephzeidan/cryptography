package assignment2;
import java.util.Random;

public class Deck {
	
	public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
	public static Random gen = new Random();
	public int numOfCards; 		
	public Card head; 			

	

	public Deck(int numOfCardsPerSuit, int numOfSuits) {
		
		if ((numOfCardsPerSuit < 1 || numOfCardsPerSuit > 13) || (numOfSuits < 1 || numOfSuits > suitsInOrder.length)){
			throw new IllegalArgumentException();
		}	
	
		for (int i=0; i<numOfSuits; i++) {	
			for(int cardIndex =0 ; cardIndex < numOfCardsPerSuit; cardIndex++) {
				Card cur= new PlayingCard(suitsInOrder[i],cardIndex+1);
				addCard(cur);			
			}
		}
		
		Card red_joker= new Joker("red");
		Card black_joker= new Joker("black");
		addCard(red_joker);
		addCard(black_joker);
	}


	public Deck(Deck d) {
		
		Card temp= d.head;
		for(int i=0; i<d.numOfCards; i++) {
			addCard(temp.getCopy());
			temp= temp.next;
		}
	}

	public Deck() {}

 
	public void addCard(Card c) {
	 
		// edge case: starting with an empty deck 
		if(this.numOfCards==0) {
			head=c;
			c.next=c;
			c.prev=c;
		}
	 
		else {
			// c is the new tail
			// original tail should be the previous of the head
			Card original_tail= head.prev;
			original_tail.next=c; 			//1
			head.prev=c;					//2 
			c.next=head;					//3
			c.prev=original_tail;			//4
		}
	 
		numOfCards++;
	}


	public void shuffle() {
 
		Card [] my_arr= new Card[numOfCards];
	
		int index=0;
		Card cur= head;
	
		// from list to array
		while(index<numOfCards) {
			my_arr[index++]= cur;
			cur=cur.next;
		}
	 
		// shuffle
		for (int i= (my_arr.length-1) ; i > 0 ; i--) {
			int j = gen.nextInt(i+1);
			Card temp=my_arr[i];
			my_arr[i]=my_arr[j];
			my_arr[j]=temp;
		}
	 
		// from array to list
		this.numOfCards=0;
		this.head=null;
	 
		for (int i=0; i<my_arr.length; i++) {
			addCard(my_arr[i]);
		}
	}

	
	
	 public Joker locateJoker(String color) {
		 
		 Joker j = null;
		 
	     if (this.head != null){
	    	 
	         Card temp = this.head;
	         for (int i = 0; i< this.numOfCards; i++){
	             if (temp instanceof Joker){
	                 if (((Joker) temp).getColor() == color){
	                    j= (Joker) temp;
	                    break;
	                 }
	             }
	             temp = temp.next;
	         }
	         return j;
	     }
	     return null;
	 }
		
	public void moveCard(Card c, int p) {
 
		// T: Target
		// P: Target Previous
		// TN: Target Next
		// V: Victim
		// A: Victim Next
	
			
			Card T=c;
			Card P=T.prev;
			Card TN=T.next;
			Card V=T;
			
			for (int i=0; i<p; i++) {
				V= V.next;
			}
			
			Card A=V.next;
			
			P.next=TN;
			TN.prev=P;
			V.next=T;
			T.prev=V;
			T.next=A;
			A.prev=T;
			

		
	}


	public void tripleCut(Card firstCard, Card secondCard) {
		
		//  A locate the first node of the first segment (type Card) = head
		//  B locate the last node of the first segment (type Card) = fisrtCard.prev
		//  C  locate the first node of the third segment (type Card) = secondCard.next
		//  D locate the last node of the third segment (type Card) = head.prev 
		
		if(firstCard == head) {
			head= secondCard.next;
		}
		
		else if(secondCard == head.prev) {
			head= firstCard;
		}
		
		else if (firstCard == head && secondCard == head.prev) {
			
		}
		
		else {
			
			Card first_node_segment_1= head;
			Card last_node_segment_1= firstCard.prev;
			Card first_node_segment_3= secondCard.next;
			Card last_node_segment_3= head.prev;
			
			firstCard.prev=last_node_segment_3;
			secondCard.next=first_node_segment_1;
			
			last_node_segment_3.next=firstCard;
			first_node_segment_1.prev=secondCard;
			
			head=first_node_segment_3;
			head.prev=last_node_segment_1;
			last_node_segment_1.next=head;
			
			}
			
	}
	
	
	public void countCut() {
		
		Card bottom= head.prev;
		int first_n_cards= bottom.getValue() % numOfCards;
		// locate the last card from the list
		
		if (first_n_cards == numOfCards-1 || first_n_cards==0 ) {	return ;}
		
		else {
			
			Card y=head;
			Card x=head;
			for (int i=0; i<first_n_cards-1;i++) {
				x=x.next;	
			}
			
			Card w= x.next;
			Card z= bottom.prev;
			
			x.next=bottom;
			bottom.prev=x;
			y.prev=z;
			head=w;
			bottom.next=w;
			z.next=y;
			w.prev=bottom;
		}		
	}

	
	public Card lookUpCard() {
		 
		int value_of_top=head.getValue();
	 
		Card cur=head;
		for (int i=0; i<value_of_top;i++) {
			cur=cur.next;
		}
		
		if (cur instanceof Joker) {
			return null;
		}
		
		return cur;
 }



 
	public int generateNextKeystreamValue() {
 	
		Card destiny;

		Joker red_joker= locateJoker("red");
		Joker black_joker= locateJoker("black");
		
		moveCard(red_joker,1);
		moveCard(black_joker,2);
		
		Joker first_joker= null;
		Joker second_joker= null;
		Card cur=head;
		for (int i=0; i< numOfCards; i++) {
	
			if (cur instanceof Joker){ 
					first_joker= (Joker)cur;
					break;
			}
			cur=cur.next;
		}	

		String first_color= first_joker.getColor();
		
		if (first_color.equalsIgnoreCase("red")) {
			second_joker=black_joker;
		}
		else
			second_joker= red_joker;
		
		tripleCut(first_joker,second_joker);
		
		countCut();
		
		destiny= lookUpCard();
		
		if (destiny == null) {
			return generateNextKeystreamValue();
		}
		
		else 
			
			return destiny.getValue();
		
		
	}
	
 
	
 //----------------------------------------------------------------------------------------------------------------------------	
	 	
	 		 	 

 	public abstract class Card { 
 		public Card next;
 		public Card prev;

 		public abstract Card getCopy();
 		public abstract int getValue();
 	}

 	
 	
  //----------------------------------------------------------------------------------------------------------------------------	
 	 	
 	 	
 	 	
 	public class PlayingCard extends Card {
 		public String suit;
 		public int rank;

 		
 		public PlayingCard(String s, int r) {
 			this.suit = s.toLowerCase();
 			this.rank = r;
 		}

 		
 		public String toString() {
 			String info = "";
	   
 			if (this.rank == 1) {
 				//info += "Ace";
 				info += "A";
 			} 
	   
 			else if (this.rank > 10) {
 				String[] cards = {"Jack", "Queen", "King"};
 				//info += cards[this.rank - 11];
 				info += cards[this.rank - 11].charAt(0);
 			}
	   
 			else {
 				info += this.rank;
 			}
	   
		   //info += " of " + this.suit;
		   info = (info + this.suit.charAt(0)).toUpperCase();
		   return info;
	 	}
 		

 		public PlayingCard getCopy() {
 			return new PlayingCard(this.suit, this.rank);   
 		}

 		
 		public int getValue() {
 			int i;
 			for (i = 0; i < suitsInOrder.length; i++) {
 				if (this.suit.equals(suitsInOrder[i]))
 					break;
 				}

 			return this.rank + 13*i;
 			}

 		}
 	
 //----------------------------------------------------------------------------------------------------------------------------	
 	

 	public class Joker extends Card{
 		public String redOrBlack;

 		public Joker(String c) {
 			if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
 				throw new IllegalArgumentException("Jokers can only be red or black"); 
 			
 			this.redOrBlack = c.toLowerCase();
 		}

 		public String toString() {
 			//return this.redOrBlack + " Joker";
 			return (this.redOrBlack.charAt(0) + "J").toUpperCase();
 		}

 		public Joker getCopy() {
 			return new Joker(this.redOrBlack);
 		}

 		public int getValue() {
 			return numOfCards - 1;
 		}

 		public String getColor() {
 			return this.redOrBlack;
 		}
 	}

 	
 	public void printDeck(){
 		
 	  Card currentCard = head;
 	  System.out.println("Previous\tCurrent\t\tNext ");
 	  
 	  for(int cardIndex = 0; cardIndex < numOfCards; cardIndex++) {
 	
 	   System.out.println(currentCard.prev + " <--------- " + currentCard + " ---------> " + currentCard.next + ", Values are: " +currentCard.prev.getValue() + " and " + currentCard.getValue() + " and " + currentCard.next.getValue());
 	   currentCard = currentCard.next;
 	  }

 	  System.out.println("Number of cards: " + numOfCards);
 	  
 	 }

 	
 		public static void main(String[] args){
 			
 			
 			
// 			sampleDeck.shuffle();
// 			sampleDeck.printDeck();
// 			sampleDeck.countCut();
// 			sampleDeck.printDeck();
// 			Deck.Joker red= sampleDeck.locateJoker("red");
// 			Deck.Joker black= sampleDeck.locateJoker("black");
// 			sampleDeck.tripleCut(black, red);
// 			sampleDeck.printDeck();
// 			System.out.println(sampleDeck.generateNextKeystreamValue());
 			
 			
 			
// 			Deck deck = new Deck();
// 			  Deck.Card rj = deck.new Joker("red");
// 			  Deck.Card bj = deck.new Joker("black");
// 			  Deck.Card ac = deck.new PlayingCard("Clubs", 1);
// 			  deck.addCard(rj);
// 			  deck.addCard(bj);
// 			  deck.addCard(ac);
// 			  System.out.println("----\nReturn value: " + deck.generateNextKeystreamValue()+"\n----");
// 			System.out.println("Create a new Deck: ");
 			
// 			new_deck.printDeck();
 			
// 			System.out.println("Create a new Copy of Deck: ");
// 			Deck copy_deck = new Deck(new_deck);
// 			copy_deck.printDeck();
 			
 			
// 			Deck new_deck = new Deck(4, 2);
// 			System.out.println("Print Shuffled Deck");
 			
// 			new_deck.shuffle();
// 			new_deck.printDeck();
// 			Deck.Joker red= new_deck.locateJoker("red");
// 			Deck.Joker black= new_deck.locateJoker("black");
// 			System.out.println(red);
// 			new_deck.moveCard(red, 1);
// 			new_deck.moveCard(black, 2);
// 			new_deck.printDeck();
 			
// 			System.out.println(new_deck.lookUpCard());
 			
// 			Card destiny= new_deck.lookUpCard().prev;
// 			System.out.println(destiny);
// 			System.out.println(new_deck.first_second());
// 			System.out.println(new_deck.generateNextKeystreamValue());
// 			new_deck.printDeck();
 			
// 			new_deck.printDeck();
// 			System.out.println("Locate Red Joker");
// 			System.out.println(new_deck.locateJoker("red"));
// 			System.out.println(new_deck.locateJoker("red").getValue());
// 			System.out.println(new_deck.locateJoker("red").getColor());
// 			System.out.println("Locate Black Joker");
// 			System.out.println(new_deck.locateJoker("black"));
// 			System.out.println(new_deck.locateJoker("black").getValue());
// 			System.out.println(new_deck.locateJoker("black").getColor());
// 			System.out.println(new_deck.locateJoker("black").getValue());
// 			Deck d = new Deck();
// 			
// 			// check if 2 cards are the same
// 			Deck.PlayingCard p1=d.new PlayingCard("Spades", 1);
// 			Deck.PlayingCard p2=d.new PlayingCard("Spades", 1);
// 			System.out.println(p1.toString().equals(p2.toString()));
			
// 			System.out.println("MOVE");
// 			Deck.PlayingCard p1=new_deck.new PlayingCard("clubs", 2);	
// 			Deck.Joker rj=new_deck.new Joker("black");	
// 			new_deck.moveCard(rj, 2);
// 			new_deck.printDeck();
// 			

// 			
// 			new_deck.shuffle();
// 			new_deck.printDeck();
 			
// 			Deck.PlayingCard _2C= new_deck.new PlayingCard("clubs", 2);	
//			Deck.PlayingCard AD=new_deck.new PlayingCard("diamonds", 1);	
//			Deck.PlayingCard _8H= new_deck.new PlayingCard("hearts", 8);	
//			new_deck.addCard(_8H);
//			new_deck.printDeck();
//			
//			Card cur= new_deck.head;
//			
//			for (int i=0; i<new_deck.numOfCards; i++) {
//				if (cur.getValue() == _2C.getValue()) {
//					_2C=(Deck.PlayingCard) cur;
//				}
//				
//				if (cur.getValue() == AD.getValue()) {
//					
//					AD=(Deck.PlayingCard) cur;
//				}
//				cur=cur.next;
				
			
//			System.out.println(AD.next);
//			System.out.println(_2C.next);
//		
			
//		
// 			new_deck.tripleCut(_2C, AD);
//			new_deck.printDeck();
//			

// 			System.out.println(red);
 			
 			
// 			new_deck.moveCard(_2C, 3 );
// 			new_deck.printDeck();
// 			
// 			System.out.println(red);
// 			System.out.println(black);
 			
 			Deck new_deck = new Deck(5, 2);
 			new_deck.shuffle();
 			new_deck.printDeck();
 			
 			Deck.Joker red= new_deck.locateJoker("red");
 			Deck.Joker black= new_deck.locateJoker("black");
 			
// 			System.out.println("FIRST ITERARTION");
// 			new_deck.moveCard(red,1);
// 			new_deck.printDeck();
// 			new_deck.moveCard(black,2);
// 			new_deck.printDeck();
// 			new_deck.tripleCut(black, red);
//			new_deck.printDeck();		
// 			new_deck.countCut();
// 			System.out.println("\nHEREEEEE");
// 			new_deck.printDeck();
// 			System.out.println(new_deck.lookUpCard());
// 			
// 			System.out.println("SECOND ITERARTION");
// 			new_deck.moveCard(red,1);
// 			new_deck.printDeck();
// 			new_deck.moveCard(black,2);
// 			new_deck.printDeck();
// 			new_deck.tripleCut(red, black);
// 			new_deck.printDeck();
// 			new_deck.countCut();
// 			System.out.println("HEREEEEE");
//			new_deck.printDeck();
//			System.out.println(new_deck.lookUpCard());
//			
//			System.out.println("THIRD ITERARTION");
//			new_deck.moveCard(red,1);
// 			new_deck.moveCard(black,2);
// 			new_deck.printDeck();
// 			new_deck.tripleCut(black, red);
// 			new_deck.printDeck();
// 			new_deck.countCut();
//			new_deck.printDeck();
//			System.out.println(new_deck.lookUpCard());
//			
//			
//			System.out.println("FOURTH ITERARTION");
//			new_deck.moveCard(red,1);
// 			new_deck.moveCard(black,2);
// 			new_deck.printDeck();
// 			new_deck.tripleCut(red, black);
// 			new_deck.printDeck();
// 			new_deck.countCut();
//			new_deck.printDeck();
//			System.out.println(new_deck.lookUpCard());
			
// 			  Deck deck = new Deck();
// 			  Deck.Card rj = deck.new Joker("red");
// 			  Deck.Card bj = deck.new Joker("black");
// 			  Deck.Card ac = deck.new PlayingCard("Clubs", 1);
// 			  deck.addCard(rj);
// 			  deck.addCard(bj);
// 			  deck.addCard(ac);
// 			  deck.printDeck();
// 			  System.out.println("----\nReturn value: " + deck.generateNextKeystreamValue()+"\n----");		
 			
 			
 			
 	
 			
 			System.out.println(new_deck.generateNextKeystreamValue());
 			System.out.println(new_deck.generateNextKeystreamValue());
 			System.out.println(new_deck.generateNextKeystreamValue());
 			System.out.println(new_deck.generateNextKeystreamValue());
 			System.out.println(new_deck.generateNextKeystreamValue());
 			System.out.println(new_deck.generateNextKeystreamValue());
 			System.out.println(new_deck.generateNextKeystreamValue());
 			System.out.println(new_deck.generateNextKeystreamValue());
 			System.out.println(new_deck.generateNextKeystreamValue());
 			System.out.println(new_deck.generateNextKeystreamValue());
 			
 			
 	
 		}	
 		

}













