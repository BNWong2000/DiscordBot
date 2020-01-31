package com.github.BNWong2000;

import Hand;
import Card;
import Deck;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;


public BlackJack {
  
  private Arraylist<Player> players;
  private Player currentTurn;
  private Deck theDeck;
  private 

  enum GameStatus{
       NONE,
       LOOKINGFORPLAYERS,
       STARTED
  }

  public BlackJack (){
    this.theDeck = new Deck();
    this.theDeck.shuffle();
    
    
    
  }
  
  
  
}
