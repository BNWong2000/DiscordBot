package com.github.BNWong2000;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CommandManager {
    private String message;
    private List<String> splitMessage;
    private boolean needsEmbed;
    private EmbedManager embed;
    private BlackJack blackJackGame;
    private User theUser;
    private boolean gameStarted;

    public boolean getGameStarted(){
        return gameStarted;
    }

    public String getMessage(){
        return message;
    }

    public void setTheUser(User theUser){
        this.theUser = theUser;
    }

    public User getTheUser(){
        return theUser;
    }

    public BlackJack getBlackJackGame(){
        return blackJackGame;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getSplitMessage(){
        return splitMessage;
    }

    public boolean getNeedsEmbed(){
        return needsEmbed;
    }

    public void setNeedsEmbed(boolean needsEmbed){
        this.needsEmbed = needsEmbed;
    }


    public EmbedManager getEmbed(){
        return embed;
    }

    public String getResponse(){
        getWords();
        needsEmbed = false;
        String output;
        switch (splitMessage.get(0)) {
            case "!Dank":
                output = "memes";
                break;
            case "!commands":
                output = getCommandList();
                break;
            case "!BlackJack":
                if(blackJackGame != null){
                    if(blackJackGame.userInGame(theUser.getName())){
                        output = "User is already in the game!";
                    }else {
                        if(blackJackGame.getStatus() == BlackJack.GameStatus.LOOKINGFORPLAYERS){
                            blackJackGame.addPlayer(new Player(theUser));
                            needsEmbed = true;
                            output = "Adding " + theUser.getName() + " to the game...";
                        }else{
                            output = "Cannot join the game at this time.";
                        }
                    }
                }else {
                    blackJackGame = new BlackJack(new Player( theUser));
                    needsEmbed = true;
                    blackJackGame.setStatus(BlackJack.GameStatus.LOOKINGFORPLAYERS);
                    output = " Starting BlackJack Game....";
                }

                if(needsEmbed){
                    embed = new EmbedManager("BlackJack Game Started");
                    embed.setImage("http://www.mrhumagames.com/BrandenBot/BlackJackLogo.jpg");
                    startGameEmbed();
                }
                break;
            case "!Start":
                if(blackJackGame != null){
                    if(blackJackGame.userInGame(theUser.getName())){
                        if(blackJackGame.getNumPlayers() < 2){
                            output = "Need more players. ";
                        }else if(blackJackGame.getStatus() == BlackJack.GameStatus.LOOKINGFORPLAYERS){
                            blackJackGame.setStatus(BlackJack.GameStatus.STARTED);
                            output = "Starting the game...\n";
                            output += startGame();
                            needsEmbed = true;
                        }else if(blackJackGame.getStatus() == BlackJack.GameStatus.STARTED){
                            output = "Game as already started. ";
                        }else{
                            output = "Cannot start the game at this time.";
                        }
                    }else{
                        output = "Only players who have joined the game can start it. ";
                    }
                }else{
                    output = "Cannot start a game that doesn't exist. ";
                }
                if(needsEmbed){
                    embed = new EmbedManager("The Table:");
                    startTableEmbed();
                }
                break;
            default:
                output = "Invalid command. type !commands for a list of commands.";

        }
        return output;
    }

    private String startGame(){
        gameStarted = true;
        return blackJackGame.startGame();
    }

    public void endGame(){
        blackJackGame = null;
        gameStarted = false;
    }

    public String getGameResponse(){
        String output;
        getWords();
        needsEmbed = false;
        if(!getTheUser().getName().equals(blackJackGame.getCurrentTurnName())){
            if(getSplitMessage().get(0).equals("!End")){
                output = "ending game...";
                endGame();
                return output;
            }else {
                output = "Not your turn yet. ";
                return output;
            }
        }
        switch(getSplitMessage().get(0)){
            case "!Hit":
                output = "Getting your Card...";
                output += blackJackGame.hit();
                if(blackJackGame.getLastPlayerToBeEliminated() != null && blackJackGame.getLastPlayerToBeEliminated().getMyName() == getTheUser().getName()){
                    needsEmbed = true;
                    embed = new EmbedManager(getTheUser().getName() + "'s Hand:");
                    startPlayerBustHandEmbed();
                }else if(blackJackGame.getNumPlayers() > 1) {
                    needsEmbed = true;
                    embed = new EmbedManager(getTheUser().getName() + "'s Hand:");
                    startPublicHandEmbed();
                }else{
                    gameStarted = false;
                    blackJackGame = null;
                }
                break;
            case "!Stand":
                output = "Standing.\n";
                if(blackJackGame.isAllPlayersStand() && blackJackGame.getCurrentTurnIndex() == blackJackGame.getNumPlayers()-1){
                    output += "\n All Players stood last round. Game Over. Folding Cards...";
                    needsEmbed = true;
                    String winner = blackJackGame.getWinner();
                    embed = new EmbedManager("Winner: " + winner);
                    startGameOverTableEmbed();
                    //gameStarted = false;
                }
                output += blackJackGame.endTurn();
                break;
            case "!End":
                output = "ending the game.\n";
                endGame();
                break;
            default:
                output = "Invalid Command. ";
        }
        return output;
    }


    public String getDMResponse(){
        String output;
        setNeedsEmbed(false);
        getWords();
        switch(getSplitMessage().get(0)) {
            case "!Hand":
                if(blackJackGame != null){
                    if (!blackJackGame.userInGame(getTheUser().getName().toString())) {
                        output = "User is not in the game!";
                    } else {
                        output = "Your current hand: \n";
                        //output += blackJackGame.getUserByName(getTheUser().getName()).getMyHand().getHandCards().get(1).printCard();
                        needsEmbed = true;
                    }
                }else{
                    output = "No game in progress.";
                }
                if(needsEmbed){
                    embed = new EmbedManager("Your Hand: ");
                    //embed.setImage("http://www.mrhumagames.com/BrandenBot/BlackJackLogo.jpg");
                    startHandEmbed();
                }
                break;
            default:
                output = "This is a DM";
        }
        return output;
    }

    private void startGameEmbed() {
        if(embed == null){
            System.err.print("Embed not created yet. ");
            return;
        }
        ArrayList<String> names = new ArrayList<>();
        names.add("Current Players:");
        ArrayList<String> description = new ArrayList<>();
        description.add(blackJackGame.getPlayerListString());
        embed.setFields(names, description);
    }

    private void startHandEmbed(){
        if(embed == null){
            System.err.print("Embed not created yet. ");
            return;
        }

        if(blackJackGame.userInGame(getTheUser().getName())){
            ArrayList<Card> cards = blackJackGame.getUserByName(getTheUser().getName()).getMyHand().getHandCards();
            embed.printHandCardField(cards);
        }
    }

    private void startPlayerBustHandEmbed() {
        if(embed == null){
            System.err.print("Embed not created yet. ");
            return;
        }
        if(blackJackGame.getLastPlayerToBeEliminated() != null) {
            ArrayList<Card> cards = blackJackGame.getLastPlayerToBeEliminated().getMyHand().getHandCards();
            embed.printHandCardField(cards);
        }
    }

    private void startPublicHandEmbed(){
        if(embed == null){
            System.err.print("Embed not created yet. ");
            return;
        }
        if(blackJackGame.userInGame(getTheUser().getName())){
            ArrayList<Card> cards = blackJackGame.getUserByName(getTheUser().getName()).getMyHand().getHandCards();
            embed.printPublicHandCardField(cards);
        }
}

    private void startTableEmbed() {
        if(embed == null){
            System.err.print("Embed not created yet.");
            return;
        }
        ArrayList<String> names = new ArrayList<>();
        ArrayList<ArrayList<Card> > hands = new ArrayList<>();
        for(int i = 0; i < blackJackGame.getNumPlayers(); ++i){
            names.add(blackJackGame.getPlayers().get(i).getMyName());
            hands.add(blackJackGame.getPlayers().get(i).getMyHand().getHandCards());
        }
        embed.printTableField(names, hands);
    }

    private void startGameOverTableEmbed() {
        if(embed == null){
            System.err.print("Embed not created yet.");
            return;
        }
        ArrayList<String> names = new ArrayList<>();
        ArrayList<ArrayList<Card> > hands = new ArrayList<>();
        for(int i = 0; i < blackJackGame.getNumPlayers(); ++i){
            names.add(blackJackGame.getPlayers().get(i).getMyName());
            hands.add(blackJackGame.getPlayers().get(i).getMyHand().getHandCards());
        }
        embed.printGameOverTableField(names, hands);
    }

    private String getCommandList() {
        String result = "";
        result += "!BlackJack - Starts a blackjack game if it has not been created, or joins the game if it has been.\n";
        result += "!commands - lists the commands\n";
        return result;
    }

    protected void getWords() {
        splitMessage = Arrays.asList(message.split(" "));
    }

    public CommandManager(){
        gameStarted = false;
    }
}
