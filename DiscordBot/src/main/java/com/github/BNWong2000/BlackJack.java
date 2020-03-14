package com.github.BNWong2000;

import java.util.ArrayList;
import net.dv8tion.jda.api.entities.MessageChannel;


public class BlackJack {

    enum GameStatus{
        NONE,
        LOOKINGFORPLAYERS,
        STARTED
    }

    private MessageChannel gameChannel;
    private ArrayList<Player> players;
    //private Player dealer;
    private Player currentTurn;
    private Deck theDeck;
    private GameStatus status;

    public BlackJack(Player host){
        players = new ArrayList<>();
        addPlayer(host);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public boolean userInGame(String userName){
        for(int i = 0; i < players.size(); ++i){
            if(players.get(i).getUser().getName() == userName){
                return true;
            }
        }
        return false;
    }

    
    public String getPlayerListString(){
        //
        String result = "";
        for(int i = 0; i < players.size(); ++i){
            result += players.get(i).getMyName();
            result += "\n";
        }
        return result;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status){
        this.status = status;
    }


    public void addPlayer(Player gamePlayer){
        players.add(gamePlayer);
    }

}
