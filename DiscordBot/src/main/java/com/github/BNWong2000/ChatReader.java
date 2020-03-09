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

public class ChatReader extends ListenerAdapter{
    private CommandManager manager;

    public ChatReader(){
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
        String message = event.getMessage().getContentRaw();
        getManager().setMessage(message);
        event.getChannel().sendMessage(getManager().getResponse()).queue();
    }
}
