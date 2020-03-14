package com.github.BNWong2000;

public interface Suits {
    public final String[] suitList = {"Diamonds", "Hearts", "Clubs", "Spades"};
    public final String[] suitSymbolList  = { "\u25C6" , "\u2661" , "\u2667" ,"\u2664"};
    public final String[] topOfCardSuit = { "|\u25C6\u2003\u2003\u2003\u2003\u2003\u2003\u2009\u200A|\n" , "|\u2661\u2003\u2003\u2003\u2003\u2003\u2003\u2005\u200A|\n" ,
            "|\u2667\u2003\u2003\u2003\u2003\u2003\u2003\u2004|\n" ,"|\u2664\u2003\u2003\u2003\u2003\u2003\u2003\u2005\u200A|\n"};


    public final String[] bottomOfCardSuit = { "|\u2003\u2003\u2003\u2003\u2003\u2003\u2009\u200A\u25C6|\n" , "|\u2003\u2003\u2003\u2003\u2003\u2003\u2005\u200A\u2661|\n" ,
            "|\u2003\u2003\u2003\u2003\u2003\u2003\u2004\u2667|\n" ,"|\u2003\u2003\u2003\u2003\u2003\u2003\u2005\u200A\u2664|\n"};
}