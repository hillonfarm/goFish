package goFish;

import java.util.ArrayList;
import java.util.Random;

public class Deck{
	private ArrayList<Card> deck;
	
	public Deck(){
		deck = new ArrayList<Card>();
		for (int suitNumber = 0; suitNumber < 4; suitNumber++){
			for (int rankNumber = 0; rankNumber < 13; rankNumber++)
				deck.add(new Card(rankNumber, suitNumber));
		}
		
		shuffle();
	}
	
	public int getNumberOfCards(){
		return deck.size();
	}
	
	public boolean isDeckEmpty() {
		if(getNumberOfCards() == 0)
			return true;
		return false;
	}
	
	public Card drawFromDeck(){
		Card temp = deck.get(deck.size()-1);
		deck.remove(deck.size()-1);
		return temp;
	}
	
	public void shuffle(){
		int numCards = (getNumberOfCards() - 1);
		Random randomGenerator = new Random();
	    while (numCards > 1) {
	      	int randomInt = randomGenerator.nextInt(getNumberOfCards());
	        Card temp = deck.get(numCards);
	        deck.set(numCards, deck.get(randomInt));
	        deck.set(randomInt, temp);
	        numCards--;
	    }
		
	}

}
