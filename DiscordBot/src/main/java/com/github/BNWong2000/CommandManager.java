package com.github.BNWong2000;

import java.util.List;
import java.util.Arrays;

public class CommandManager {
    private String message;
    private List<String> splitMessage;
    private enum commands{
        
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse(){
        if(message.charAt(0) != '!'){
            return null;
        }
        getWords();
        switch (splitMessage.get(0)) {
            case "!Dank":
                return "memes";
            case "!commands":
                return getCommandList();
            case "!BlackJack":
                return " Starting BlackJack Game....";
        }
        return "Invalid command. type !commands for a list of commands.";

    }

    private String getCommandList() {
        String result = "";
        result += "!BlackJack [X]- Starts a blackjack game with X number of players\n";
        result += "!commands - lists the commands\n";
        return result;
    }

    private void getWords() {
        splitMessage = Arrays.asList(message.split(" "));
    }
}
