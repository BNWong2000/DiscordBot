package com.github.BNWong2000;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private Card[] deck;
    Deck(){
        deck = new Card[52];
        int N = 0;
        for(int i = 0; i < 13; ++i){
            for(int j = 0; j < 4; ++j){
                deck[N] = new Card(j, i);
                ++N;
            }
        }
        shuffle();
    }

    public void shuffle(){
        
        Random rand = new Random();
        
        for(int i = 0; i < deck.length; ++i){
            int rIndex = rand.nextInt(deck.length);
            Card temp = deck[rIndex];
            deck[rIndex] = deck[i];
            deck[i] = temp;
        }
    }

}
