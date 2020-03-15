package com.github.BNWong2000;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> handCards;

	public Hand(){
		handCards = new ArrayList<Card>();
//		handCards.add(new Card(0, 10));
//		handCards.add(new Card(1, 11));
//		handCards.add(new Card(2, 12));
//		handCards.add(new Card(3, 9));
	}

	public int sumHand(){
		int sum = 0;
		int numAces = 0;
		for(int i = 0; i < handCards.size(); ++i){
			if(handCards.get(i).getCardValue() == 11){
				numAces++;
			}
			sum += handCards.get(i).getCardValue();
		}
		while (sum > 21 && numAces > 0) {
			sum -= 10;
			numAces--;
		}
		return sum;
	}

	public void addCardToHand(Card newCard){
		handCards.add(newCard);
	}

	public ArrayList<Card> getHandCards(){
		return handCards;
	}
}
