package com.github.BNWong2000;

import java.util.ArrayList;
//import Hand;
//import Card;
//import Deck;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;


public class BlackJack {

  enum GameStatus{
       NONE,
       LOOKINGFORPLAYERS,
       STARTED
  }
  
  private MessageChannel gameChannel;
  private ArrayList<Player> players;
  private Player dealer;
  private Player currentTurn;
  private Deck theDeck;
  private GameStatus status;

  

  public BlackJack (MessageChannel gC){
    this.theDeck = new Deck();
    this.theDeck.shuffle();
    this.status = GameStatus.LOOKINGFORPLAYERS;
    this.players = new ArrayList<Player>();
    this.gameChannel = gC;
  }
  
  public MessageChannel getChannel() {
	  return gameChannel;
  }
  
  public void startGame(){
	 
  }

  public void addPlayer(User newPlayer) {
	  
	  this.players.add(new Player(newPlayer.getName(),newPlayer));
	  
  }

  public String message() {
	
	  
	  return "";
	  
  }
}
