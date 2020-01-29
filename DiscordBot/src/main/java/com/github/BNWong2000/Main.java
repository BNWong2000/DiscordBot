package com.github.BNWong2000;

import jdk.nashorn.internal.scripts.JD;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;


public class Main extends ListenerAdapter {
    private static GameStatus status;
    private int numPlayers;
    private String dealer;
    private String[] playerList;

    enum GameStatus{
        NONE,
        LOOKINGFORPLAYERS,
        STARTED
    }

    public static void main(String[] args) throws LoginException{
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NjcxMTkxODEwNDMyODI3NDA3.Xi9AAQ.rQscRG0oSBvsyC3NZBpcbsDCxEY";
        status = GameStatus.NONE;
        builder.setToken(token);
        builder.addEventListeners(new Main());
        builder.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String Message = event.getMessage().getContentRaw();
        switch (Message) {
            case "Dank" :
                event.getChannel().sendMessage("Memes").queue();
                break;
            case "!BlackJack":
                if(Main.status != GameStatus.NONE) {
                    event.getChannel().sendMessage("A game is already in progress").queue();
                }else{
                    dealer = event.getAuthor().getName();
                    event.getChannel().sendMessage(dealer + " has started a blackjack game, type !join to join this game.").queue();
                    Main.status = GameStatus.LOOKINGFORPLAYERS;
                }
                break;
            case "!join":
                if(Main.status != GameStatus.LOOKINGFORPLAYERS){
                    event.getChannel().sendMessage("You cannot join at this time.").queue();
                }else{
                    event.getChannel().sendMessage("Added " + event.getAuthor().getName() + " to " + dealer + "'s game.").queue();
                    event.getAuthor().getName();
                }
                break;
            case "!Start":
                if(numPlayers < 2) {

                }
        }
    }

}
