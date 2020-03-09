package com.github.BNWong2000;

public class Card{
    public static String[] Suits = {"Diamonds", "Hearts", "Clubs", "Spades"};
    public static String[] Values = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "Joker"};


    private int suit;
    private int value;

    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Card(int suit, int value){
        this.suit = suit;
        this.value = value;
    }

}
