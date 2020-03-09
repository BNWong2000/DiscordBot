package com.github.BNWong2000;

public class DirectMessageManager extends CommandManager{

    @Override
    public String getResponse(){
        super.getWords();
        switch(getSplitMessage().get(0)){
            case "!Hand":
                return "Your current hand: ";
        }
        return "this is a DM";
    }

}
