package com.github.BNWong2000;

public class Card implements Suits, CardValues{


    private int suit;
    private int value;

    public String getSuit() {
        return suitList[suit];
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public String getValue() {
        return valueList[value];
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Card(int suit, int value){
        this.suit = suit;
        this.value = value;
        //System.out.print("Suit: " + suitList[suit] + " Value: " + valueList[value]);
    }

    public String printCard(){
        String result = printHorizontalLine();
        result += "\n";
        result += "|" + getSuitSymbol();
        result += "            |\n";
        result += "|" + getValue();
        result += "            |\n";
        result += "|             |\n";
        result += "|             |\n";
        result += "|             |\n";
        result += "|            ";
        result += getValue() + "|\n";
        result += "|            ";
        result += getSuitSymbol() + "|\n";
        result += printHorizontalLine();
        return result;
    }

    private String getSuitSymbol() {
        return suitSymbolList[suit];
    }

    private String printHorizontalLine(){
        return " ---------- ";
    }

    @Override
    public String toString() {
        String result = ("Suit: " + suitList[suit] + " Value: " + valueList[value] + "\n");
        return result;
    }
}
