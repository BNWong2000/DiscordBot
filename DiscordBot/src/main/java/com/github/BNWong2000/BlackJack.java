package com.github.BNWong2000;

import Hand;
import Card;
import Deck;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;


public BlackJack {
  
  enum GameStatus{
       NONE,
       LOOKINGFORPLAYERS,
       STARTED
  }
  
  private Arraylist<Player> players;
  private Player dealer;
  private Player currentTurn;
  private Deck theDeck;
  private GameStatus status;

 

  public BlackJack (){
    this.theDeck = new Deck();
    this.theDeck.shuffle();
    status = GameStatus.LOOKINGFORPLAYERS;
    
    
  }
  
  
  
}
