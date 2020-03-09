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

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getSplitMessage(){
        return splitMessage;
    }

    public boolean getNeedsEmbed(){
        return needsEmbed;
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
                blackJackGame = new BlackJack(new Player(theUser.getName().toString(), theUser));
                embed = new EmbedManager("BlackJack Game:");
                embed.setImage("https://github.com/BNWong2000/DiscordBot/blob/master/DiscordBot/BlackJackLogo.jpg");
                startGameEmbed();
                needsEmbed = true;
                output = " Starting BlackJack Game....";
                break;
            default:
                output = "Invalid command. type !commands for a list of commands.";

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

    private String getCommandList() {
        String result = "";
        result += "!BlackJack [X]- Starts a blackjack game with X number of players\n";
        result += "!commands - lists the commands\n";
        return result;
    }

    protected void getWords() {
        splitMessage = Arrays.asList(message.split(" "));
    }
}
