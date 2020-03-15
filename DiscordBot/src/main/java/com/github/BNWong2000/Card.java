package com.github.BNWong2000;

public class Card implements Suits, CardValues{


    private int suit;
    private int value;
    private boolean faceDown;

    public String getSuit() {
        return suitList[suit];
    }

    public int getCardValue(){
        return actualValues[value];
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
        this.faceDown = false;
        //System.out.print("Suit: " + suitList[suit] + " Value: " + valueList[value]);
    }

    public Card(int suit, int value, boolean faceDown){
        this.suit = suit;
        this.value = value;
        this.faceDown = faceDown;
        //System.out.print("Suit: " + suitList[suit] + " Value: " + valueList[value]);
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public void setFaceDown() {
        this.faceDown = true;
    }

    public String printCard(){
        if (faceDown) {
            return printFaceDown();
        }else{
            return printFaceUpCard();
        }
    }
    public String printFaceDown(){
        String result = printHorizontalLine() + "\n";
        int numlines = 7, numBlocks = 8;
        for(int i = 0; i < numlines; ++i){
            result += "|\u2003";
            for(int j = 0; j < numBlocks; ++j){
                result += "\u25A0";
            }
            result += "\u2003|\n";
        }
        result += printHorizontalLine();
        return result;
    }

    public String printFaceUpCard(){
        String result = printHorizontalLine();
        result += "\n";
        result += topOfCardSuit[suit];
        result += topOfCardValue[value];
        result += "|" + printWhiteSpace() + "\u2003" + "|\n";
        result += "|" + printWhiteSpace() + "\u2003" + "|\n";
        result += "|" + printWhiteSpace() + "\u2003" + "|\n";
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
        return "--------------------";
    }

    @Override
    public String toString() {
        String result;
        if(!faceDown){
            result = (valueList[value] + " of " + suitList[suit] + "\n");

        }else{
            result = "(FACEDOWN)\n";
        }
        return result;
    }

}
