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
    private Player lastPlayerToBeEliminated;
    private int currentTurnIndex;
    private Deck theDeck;
    private GameStatus status;
    private boolean allPlayersStand;
    private boolean gameOver;

    public BlackJack(Player host){
        players = new ArrayList<>();
        addPlayer(host);
        currentTurnIndex = 0;
        theDeck = new Deck();
        allPlayersStand = false;
        gameOver = false;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public boolean isAllPlayersStand() {
        return allPlayersStand;
    }

    public void setAllPlayersStand(boolean allPlayersStand) {
        this.allPlayersStand = allPlayersStand;
    }

    public int getCurrentTurnIndex() {
        return currentTurnIndex;
    }

    public void setCurrentTurnIndex(int currentTurnIndex) {
        this.currentTurnIndex = currentTurnIndex;
    }

    public boolean userInGame(String userName){
        for(int i = 0; i < players.size(); ++i){
            if(players.get(i).getUser().getName() == userName){
                return true;
            }
        }
        return false;
    }

    public Player getUserByName(String userName){
        for(int i = 0; i < players.size(); ++i){
            if(players.get(i).getUser().getName() == userName){
                return players.get(i);
            }
        }
        return null;
    }

    public Player getLastPlayerToBeEliminated() {
        return lastPlayerToBeEliminated;
    }

    public void setLastPlayerToBeEliminated(Player lastPlayerToBeEliminated) {
        this.lastPlayerToBeEliminated = lastPlayerToBeEliminated;
    }


    public int getNumPlayers(){
        return players.size();
    }

    public String startGame(){
        String output = "";
        output += "Dealing cards...\n";
        for(int i = 0; i < players.size(); ++i){
            players.get(i).getMyHand().addCardToHand(theDeck.drawCard()); //by default, all cards are face up unless otherwise specified.
            players.get(i).getMyHand().getHandCards().get(0).setFaceDown();
            players.get(i).getMyHand().addCardToHand(theDeck.drawCard());
        }
        allPlayersStand = true;
        return output;
    }

    public String hit(){
        allPlayersStand = false;
        players.get(currentTurnIndex).getMyHand().addCardToHand(theDeck.drawCard());//by default, card is face down.
        String outputCard = players.get(currentTurnIndex).getMyHand().getHandCards().get(players.get(currentTurnIndex).getMyHand().getHandCards().size()-1).toString();
        int sum = players.get(currentTurnIndex).getMyHand().sumHand();
        if(sum > 21){
            String result = "BUST! Drew: " + outputCard + ",\n Player: ";
            lastPlayerToBeEliminated = players.get(currentTurnIndex);
            players.remove(currentTurnIndex);
            if(currentTurnIndex == (players.size())){
                currentTurnIndex = 0;
            }
            if(players.size() == 1){
                String winnerName = players.get(0).getMyName();
                endGame();
                result += "Game Over. " + winnerName + " is the winner. ";
            }
            else{
                result += players.get(currentTurnIndex).getMyName() + " is up next" ;
            }
            //currentTurnIndex--;
            return result;
        }
        return "Drew: " + outputCard;
    }

    public void endGame() {
        status = GameStatus.NONE;
        players = new ArrayList<>();
        theDeck = new Deck();
        currentTurnIndex = 0;
    }

    public String endTurn(){
        String result = "";
        if (currentTurnIndex == (players.size()-1)){
            if(allPlayersStand){
                result += "All players Stood. Game Over. ";
                gameOver = true;
            }else {
                currentTurnIndex = 0;
                allPlayersStand = true;
                result += "End of round. \n" + players.get(0).getMyName() + " is up next. ";
            }
        }else{
            currentTurnIndex++;
            result += players.get(currentTurnIndex).getMyName() + " is up next.";
        }
        return result;
    }

    public String getCurrentTurnName(){
        if(players.size() == 0){
            return "No Players in game anymore.";
        }else {
            return players.get(currentTurnIndex).getMyName();
        }
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

    public String getWinner() {
        int currentWinnerIndex = 0;
        int currentWinnerHandValue = players.get(0).getMyHand().sumHand();
        int currentWinnerNumCards = players.get(0).getMyHand().getHandCards().size();
        for(int i = 1; i < players.size(); ++i){
            if(players.get(i).getMyHand().sumHand() > currentWinnerHandValue){
                currentWinnerIndex = i;
                currentWinnerHandValue = players.get(i).getMyHand().sumHand();
                currentWinnerNumCards = players.get(i).getMyHand().getHandCards().size();
            }else if(players.get(i).getMyHand().sumHand() == currentWinnerHandValue){
                if(players.get(i).getMyHand().getHandCards().size() > currentWinnerNumCards){
                    currentWinnerIndex = i;
                    currentWinnerHandValue = players.get(i).getMyHand().sumHand();
                    currentWinnerNumCards = players.get(i).getMyHand().getHandCards().size();
                }
            }
        }
        return players.get(currentWinnerIndex).getMyName();
    }

}
