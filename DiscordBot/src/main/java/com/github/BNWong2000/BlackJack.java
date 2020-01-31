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
	 status = GameStatus.STARTED;
  }

  public void addPlayer(User newPlayer) {
	  
	  this.players.add(new Player(newPlayer.getName(),newPlayer));
	  
  }

  public String message(User p,String message) {
	  //This is where we should interpret the users message and call the appropriate function
	  String rMessage = "";
	  if(message == "!join") {
		  if(status == GameStatus.LOOKINGFORPLAYERS) {
			  boolean isIn = false;
			  for (int i = 0; i < players.size();i++) {
				  if (players.get(i).getUser() == p) {
					  isIn = true;
					  break;
				  }
			  }
			  if(isIn) {
				  rMessage = p.getName() + " is already in the game.";
			  }else {
				  players.add(new Player(p.getName(),p));
				  rMessage = p.getName() + " has joined the game.";
			  }
		  } else {
			  rMessage = "Players Cannot Join Now";
		  }
	  } else if (message == "!Start") {
		  this.startGame();
		  rMessage = "The Game Has Started!";
	  }
	  
	  return rMessage;//return what you want to print
	  
  }
}
