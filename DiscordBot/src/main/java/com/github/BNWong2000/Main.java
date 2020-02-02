package com.github.BNWong2000;

import jdk.nashorn.internal.scripts.JD;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import javax.security.auth.login.LoginException;


public class Main extends ListenerAdapter {
    private static GameStatus status;
    //private int numPlayers;
    private String host; //TODO: delete this and use the dealerID object instead.
    //private static ArrayList<String> players; //TODO: delete this and use the playerIDs arrayList instead.
    private static ArrayList<User> playerIDs;
    private static ArrayList<Player> players;
    private User dealerID;
    private ArrayList<BlackJack> bj = new ArrayList<BlackJack>();
    
    enum GameStatus{
        NONE,
        LOOKINGFORPLAYERS,
        STARTED
    }

    public void startGame(){
        if(Main.status != GameStatus.STARTED){
            System.out.println("A game hasn't been started.");
            return;
        }
        //players.get(0); //
    }

    public static void main(String[] args) throws LoginException{
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = args[0];
        status = GameStatus.NONE;
        players = new ArrayList<Player>();
        playerIDs = new ArrayList<User>();
       

        builder.setToken(token);
        builder.addEventListeners(new Main());
        builder.build();
    }

    @Override
    /*
    public void onPrivateMessageReceivedEvent(PrivateMessageReceivedEvent event){
        return;
    }

     */

    public void onMessageReceived(MessageReceivedEvent event){
        String Message = event.getMessage().getContentRaw();
        if(event.isFromType(ChannelType.TEXT)) {
            switch (Message) {
                case "Dank":
                    event.getChannel().sendMessage("Memes").queue();
                    break;
                case "!BlackJack":
                	boolean isGame = false;
                	for(int i = 0; i < bj.size(); i ++) {
                		if (bj.get(i).getChannel() == event.getChannel()) {
                			isGame = true;
                			break;
                		}
                	}
                	if (isGame) {
                		event.getChannel().sendMessage("A Game Is Already In Progress In This Channel.").queue();
                	} else {
                		bj.add(new BlackJack(event.getChannel()));
                		event.getChannel().sendMessage("A Game Has Been Started. Type !join To Join The Game.").queue();
                	}
                    break;
                case "!join":
                	for(int i = 0; i < bj.size(); i ++) {
                		if (bj.get(i).getChannel() == event.getChannel()) {
                			event.getChannel().sendMessage( bj.get(i).message(event.getAuthor(),Message)).queue();
                			break;
                		}
                	}
                    break;
                case "!Start":
                	for(int i = 0; i < bj.size(); i ++) {
                		if (bj.get(i).getChannel() == event.getChannel()) {
                			event.getChannel().sendMessage( bj.get(i).message(event.getAuthor(),Message)).queue();
                			break;
                		}
                	}
                    break;
                case "!End":
                    Main.status = GameStatus.NONE;
                    event.getChannel().sendMessage("Ending " + host + "'s game...").queue();
                    players.clear();
                    host = null;
                    break;
                case "!List":
                    event.getChannel().sendMessage(" Current Players:").queue();
                    for (int i = 0; i < players.size(); ++i) {
                        event.getChannel().sendMessage(playerIDs.get(i).getName()).queue();
                    }
                    break;

            }
        }
        else if (event.isFromType(ChannelType.PRIVATE)){

            //
        }
    }

}
