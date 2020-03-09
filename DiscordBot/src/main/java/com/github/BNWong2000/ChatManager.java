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
    private DirectMessageManager dMManager;

    public ChatManager(){
        manager = new CommandManager();
        dMManager = new DirectMessageManager();
    }

    public CommandManager getManager() {
        return manager;
    }

    public DirectMessageManager getdMManager(){
        return dMManager;
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
        if(message.isFromType(ChannelType.PRIVATE)){
            getdMManager().setMessage(messageStr);
            String response = getdMManager().getResponse();
            event.getChannel().sendMessage(response).queue();
        }else if(messageStr.startsWith("!")){
            getManager().setMessage(messageStr);
            getManager().setTheUser(event.getAuthor());
            event.getChannel().sendMessage(getManager().getResponse()).queue();

            if(getManager().getNeedsEmbed()){
                event.getChannel().sendMessage(getManager().getEmbed().getMyEmbed().build()).queue();
            }
        }
    }
}
