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
                    if(blackJackGame.userInGame(theUser.getName().toString())){
                        output = "User is already in the game!";
                    }else {
                        if(blackJackGame.getStatus() == BlackJack.GameStatus.LOOKINGFORPLAYERS){
                            blackJackGame.addPlayer(new Player(theUser.getName().toString(), theUser));
                            needsEmbed = true;
                            output = "Adding " + theUser.getName().toString() + " to the game...";
                        }else{
                            output = "Cannot join the game at this time.";
                        }
                    }
                }else {
                    blackJackGame = new BlackJack(new Player(theUser.getName().toString(), theUser));
                    needsEmbed = true;
                    blackJackGame.setStatus(BlackJack.GameStatus.LOOKINGFORPLAYERS);
                    output = " Starting BlackJack Game....";
                }

                if(needsEmbed){
                    embed = new EmbedManager("BlackJack Game:");
                    embed.setImage("http://www.mrhumagames.com/BrandenBot/BlackJackLogo.jpg");
                    startGameEmbed();
                }
                break;
            default:
                output = "Invalid command. type !commands for a list of commands.";

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
                        output = "Your current hand: ";
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
}
