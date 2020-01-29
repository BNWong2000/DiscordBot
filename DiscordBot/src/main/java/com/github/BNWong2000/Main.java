package com.github.BNWong2000;

import jdk.nashorn.internal.scripts.JD;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import javax.security.auth.login.LoginException;


public class Main extends ListenerAdapter {
    private static GameStatus status;
    //private int numPlayers;
    private String dealer;
    private static ArrayList<String> players;
    private static ArrayList<User> playerIDs;
    private User dealerID;

    enum GameStatus{
        NONE,
        LOOKINGFORPLAYERS,
        STARTED
    }

    public static void main(String[] args) throws LoginException{
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "Token goes here";
        status = GameStatus.NONE;
        players = new ArrayList<String>();
        playerIDs = new ArrayList<User>();

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
                    players.add(dealer);
                    playerIDs.add(event.getAuthor());
                }
                break;
            case "!join":
                if(Main.status != GameStatus.LOOKINGFORPLAYERS){
                    event.getChannel().sendMessage("You cannot join at this time.").queue();
                }else{
                    if(players.contains(event.getAuthor().getName())){
                        event.getChannel().sendMessage(event.getAuthor().getName() + " is already a player in this game.").queue();
                    }else {
                        event.getChannel().sendMessage("Added " + event.getAuthor().getName() + " to " + dealer + "'s game.").queue();
                        players.add(event.getAuthor().getName());
                        playerIDs.add(event.getAuthor());
                    }
                }
                break;
            case "!Start":
                if(players.size() < 2 || Main.status != GameStatus.LOOKINGFORPLAYERS) {
                    event.getChannel().sendMessage("Cannot start a game at this time.").queue();
                }else{
                    Main.status = GameStatus.STARTED;
                    event.getChannel().sendMessage("Starting " + dealer + "'s game...").queue();
                }
                break;
            case "!End":
                Main.status = GameStatus.NONE;
                event.getChannel().sendMessage("Ending " + dealer + "'s game...").queue();
                players.clear();
                dealer = null;
                break;
            case "!List":
                event.getChannel().sendMessage(" Current Players:" ).queue();
                for (int i = 0; i < players.size(); ++i){
                    event.getChannel().sendMessage(playerIDs.get(i).getName() ).queue();
                }
                break;

        }
    }

}
