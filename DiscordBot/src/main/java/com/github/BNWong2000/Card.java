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
//        result += "|" + getSuitSymbol();
//        result +=  printWhiteSpace()  + "|\n";
        result += topOfCardSuit[suit];
//        result += "|" + getValue();
//        result +=  printWhiteSpace() + "|\n";
        result += topOfCardValue[value];
        result += "|" + printWhiteSpace() + "\u2003" + "|\n";
        result += "|" + printWhiteSpace() + "\u2003" + "|\n";
        result += "|" + printWhiteSpace() + "\u2003" + "|\n";
//        result += "|" +  printWhiteSpace() ;
//        result += getValue() + "|\n";
//        result += "|" +  printWhiteSpace() ;
//        result += getSuitSymbol() + "|\n";
        result += bottomOfCardValue[value];
        result += bottomOfCardSuit[suit];
        result += printHorizontalLine();
        return result;
    }

    private String printWhiteSpace(){
        String result = "";
        int numSpaces = 6;
        for(int i = 0; i < numSpaces; ++i) {
            result += "\u2003";
        }
        return result;
    }

    private String getSuitSymbol() {
        return suitSymbolList[suit];
    }

    private String printHorizontalLine(){
        return "---------------";
    }

    @Override
    public String toString() {
        String result = (valueList[value] + " of " + suitList[suit] + "\n");
        return result;
    }
}
