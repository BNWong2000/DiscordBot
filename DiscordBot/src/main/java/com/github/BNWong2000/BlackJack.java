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



  public BlackJack (){
    this.theDeck = new Deck();
    this.theDeck.shuffle();
    status = GameStatus.LOOKINGFORPLAYERS;
    players = new ArrayList<Player>();


  }
  public void startGame(MessageChannel gChannel){
	  this.gameChannel = gChannel;
  }

  public void addPlayer(User newPlayer) {
	  
	  this.players.add(new Player(newPlayer.getName(),newPlayer));
	  
  }

}
