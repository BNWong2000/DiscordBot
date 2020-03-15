package com.github.BNWong2000;

import jdk.nashorn.internal.scripts.JD;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import javax.security.auth.login.LoginException;

public class ChatManager extends ListenerAdapter{
    private CommandManager manager;

    public ChatManager(){
        manager = new CommandManager();
    }

    public CommandManager getManager() {
        return manager;
    }


    public void setManager(CommandManager manager){
        this.manager = manager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        Message message = event.getMessage();
        String messageStr = message.getContentRaw();

        boolean botMessage = event.getAuthor().isBot();
        if(botMessage){
            return;
        }

        getManager().setMessage(messageStr);
        getManager().setTheUser(event.getAuthor());

        if(message.isFromType(ChannelType.PRIVATE)){
            event.getChannel().sendMessage(getManager().getDMResponse()).queue();
        }else if(messageStr.startsWith("!")){
            if(!manager.getGameStarted()) {
                event.getChannel().sendMessage(getManager().getResponse()).queue();
            }else{
                event.getChannel().sendMessage(getManager().getGameResponse()).queue();
            }
        }
        if(getManager().getNeedsEmbed()){
            event.getChannel().sendMessage(getManager().getEmbed().getMyEmbed().build()).queue();
            getManager().setNeedsEmbed(false);
//            if(!getManager().getGameStarted()){
//                getManager().endGame();
//            }
        }
    }
}
