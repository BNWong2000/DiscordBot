package com.github.BNWong2000;

public class DirectMessageManager extends CommandManager{

    @Override
    public String getResponse(){
        String output;
        setNeedsEmbed(false);
        super.getWords();
        switch(getSplitMessage().get(0)) {
            case "!Hand":
                if(getBlackJackGame() != null){
                    if (!getBlackJackGame().userInGame(getTheUser().getName().toString())) {
                        output = "User is not in the game!";
                    } else {
                        output = "Your current hand: ";

                    }
                }else{
                    output = "No game in progress.";
                }
                break;
            default:
                output = "This is a DM";
        }
        return output;
    }

}
