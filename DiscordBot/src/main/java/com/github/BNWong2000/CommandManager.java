package com.github.BNWong2000;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CommandManager {
    private String message;
    private List<String> splitMessage;
    private boolean needsEmbed;
    private boolean needsPrivateMessageEmbed;
    private EmbedManager embed;
    private EmbedManager privateMessageEmbed;
    private BlackJack blackJackGame;
    private User theUser;
    private boolean gameStarted;
    private ArrayList<EmbedManager> tempHandEmbeds;

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

    public boolean isNeedsPrivateMessageEmbed() {
        return needsPrivateMessageEmbed;
    }

    public void setNeedsPrivateMessageEmbed(boolean needsPrivateMessageEmbed) {
        this.needsPrivateMessageEmbed = needsPrivateMessageEmbed;
    }

    public EmbedManager getEmbed(){
        return embed;
    }

    public EmbedManager getPrivateMessageEmbed() {
        return privateMessageEmbed;
    }

    public String getResponse(){
        getWords();
        needsEmbed = false;
        needsPrivateMessageEmbed = false;
        String output;
        switch (splitMessage.get(0)) {
            case "!DANK":
                output = "memes";
                break;
            case "!COMMANDS":
                output = getCommandList();
                break;
            case "!BLACKJACK":
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
            case "!START":
                if(blackJackGame != null){
                    if(blackJackGame.userInGame(theUser.getName())){
                        if(blackJackGame.getNumPlayers() < 2){
                            output = "Need more players. ";
                        }else if(blackJackGame.getStatus() == BlackJack.GameStatus.LOOKINGFORPLAYERS){
                            blackJackGame.setStatus(BlackJack.GameStatus.STARTED);
                            output = "Starting the game...\n";
                            output += startGame();
                            needsEmbed = true;
                            //needsPrivateMessageEmbed = true;
                            tempHandEmbeds = new ArrayList<>();
                            for(int i = 0; i < blackJackGame.getNumPlayers(); ++i){
                                tempHandEmbeds.add(new EmbedManager("Hands"));
                                ArrayList<Card> cards = blackJackGame.getPlayers().get(i).getMyHand().getHandCards();
                                tempHandEmbeds.get(i).printHandCardField(cards);
                                sendPrivateMessage(blackJackGame.getPlayers().get(i).getUser(), "Cards: ", tempHandEmbeds.get(i));
                            }
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
//                if(needsPrivateMessageEmbed){
//
//                        privateMessageEmbed = new EmbedManager("Game Started.");
//                        startPrivateHandEmbed();
//                        sendPrivateMessage( "Your Hand:");
//
//                }
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
        tempHandEmbeds = null;
    }

    public String getGameResponse(){
        String output;
        getWords();
        needsEmbed = false;
        needsPrivateMessageEmbed = false;
        if(!getTheUser().getName().equals(blackJackGame.getCurrentTurnName())){
            if(getSplitMessage().get(0).equals("!END")){
                output = "ending game...";
                endGame();
                return output;
            }else {
                output = "Not your turn yet. ";
                return output;
            }
        }
        switch(getSplitMessage().get(0)){
            case "!HIT":
                output = "Getting your Card...";
                output += blackJackGame.hit();
                if(blackJackGame.getLastPlayerToBeEliminated() != null && blackJackGame.getLastPlayerToBeEliminated().getMyName() == getTheUser().getName()){

                    if(blackJackGame.getCurrentTurnIndex() == blackJackGame.getNumPlayers()){
                        output += "\n Round Over. Revealing Cards...";
                        //needsEmbed = true;
                        String winner = blackJackGame.getWinner();
                        embed = new EmbedManager("Winner: " + winner);
                        startGameOverTableEmbed();
                    }else{
                        needsEmbed = true;
                        embed = new EmbedManager(getTheUser().getName() + "'s Hand:");
                        startPlayerBustHandEmbed();
                    }
                }else if(blackJackGame.getNumPlayers() > 1) {
                    needsEmbed = true;
                    needsPrivateMessageEmbed = true;
                    privateMessageEmbed = new EmbedManager(getTheUser().getName() + "'s Hand:");
                    embed = new EmbedManager(getTheUser().getName() + "'s Hand:");
                    startPublicHandEmbed();
                    startPrivateHandEmbed();
                    sendPrivateMessage("Your Hand: ");
                }else{
                    gameStarted = false;
                    blackJackGame = null;
                }
                break;
            case "!STAND":
                output = "Standing.\n";
                if(blackJackGame.getCurrentTurnIndex() == blackJackGame.getNumPlayers()-1){ //blackJackGame.isAllPlayersStand() &&
                    output += "\n Round Over. Revealing Cards...";
                    needsEmbed = true;
                    String winner = blackJackGame.getWinner();
                    embed = new EmbedManager("Winner: " + winner);
                    startGameOverTableEmbed();
                    //gameStarted = false;
                }
                output += blackJackGame.endTurn();
                break;
            case "!END":
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
        needsPrivateMessageEmbed = false;
        getWords();
        switch(getSplitMessage().get(0)) {
            case "!HAND":
                if(blackJackGame != null){
                    if (!blackJackGame.userInGame(getTheUser().getName().toString())) {
                        output = "User is not in the game!";
                    } else {
                        output = "Getting your current hand: \n";
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
                    //sendPrivateMessage(output);
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

    private void startPrivateHandEmbed(){
        if(embed == null){
            System.err.print("Embed not created yet. ");
            return;
        }

        if(blackJackGame.userInGame(getTheUser().getName())){
            ArrayList<Card> cards = blackJackGame.getUserByName(getTheUser().getName()).getMyHand().getHandCards();
            privateMessageEmbed.printHandCardField(cards);
        }
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

    private void startHandEmbed(User thisUser){
        if(embed == null){
            System.err.print("Embed not created yet. ");
            return;
        }

        if(blackJackGame.userInGame(thisUser.getName())){
            ArrayList<Card> cards = blackJackGame.getUserByName(thisUser.getName()).getMyHand().getHandCards();
            privateMessageEmbed.printHandCardField(cards);
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
        String temp = splitMessage.get(0);
        temp = temp.toUpperCase();
        splitMessage.set(0, temp);
    }

    public CommandManager(){
        gameStarted = false;
    }

    public void sendPrivateMessage(String content) {
        theUser.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(content))
                .queue();
        if(needsPrivateMessageEmbed) {
            theUser.openPrivateChannel().flatMap(channel -> channel.sendMessage(privateMessageEmbed.getMyEmbed().build())).queue();
        }
    }

    public void sendPrivateMessage(User user, String content, EmbedManager embed) {
        user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(content))
                .queue();
        //System.out.println(user.getName());

            user.openPrivateChannel().flatMap(channel -> channel.sendMessage(embed.getMyEmbed().build())).queue();

    }


}
