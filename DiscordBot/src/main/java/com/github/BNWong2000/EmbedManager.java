package com.github.BNWong2000;

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;

public class EmbedManager {
    EmbedBuilder myEmbed;
    String title;

    public EmbedManager(String title){
        myEmbed = new EmbedBuilder();
        myEmbed.setTitle(title);
        //title = embedTitle;
        //myEmbed.setTitle(title);
    }

    public EmbedBuilder getMyEmbed() {
        return myEmbed;
    }

    public void setImage(String pathToImage){
        myEmbed.setImage(pathToImage);
    }


    public void setFields(ArrayList<String> fieldNames, ArrayList<String> fieldDescription){
        if(fieldNames.size() > fieldDescription.size()){
            return;
        }
        for(int i = 0; i < fieldNames.size(); ++i){
            myEmbed.addField(fieldNames.get(i), fieldDescription.get(i), false);
        }
    }

    public void printHandCardField(ArrayList<Card> cards){
        for(int i = 0; i < cards.size(); ++i){
            myEmbed.addField(cards.get(i).toString(), cards.get(i).printFaceUpCard(), true);
        }
        //myEmbed.addField("Testing FaceDown", cards.get(0).printFaceDown(), true );
    }

    public void printPublicHandCardField(ArrayList<Card> cards){
        for(int i = 0; i < cards.size(); ++i){
            myEmbed.addField(cards.get(i).toString(), cards.get(i).printCard(), true);
        }
        //myEmbed.addField("Testing FaceDown", cards.get(0).printFaceDown(), true );
    }

    public void printTableField(ArrayList<String> players, ArrayList<ArrayList<Card> > hands){
        for(int i = 0; i < players.size(); ++i){
            myEmbed.addField("Cards for player: ", players.get(i), false);
            printCardField(hands.get(i));
            myEmbed.addBlankField(false);
        }
        myEmbed.addField("next Player:", players.get(0) + " Starts first. ", false);
    }

    public void printGameOverTableField(ArrayList<String> players, ArrayList<ArrayList<Card> > hands){
        for(int i = 0; i < players.size(); ++i){
            myEmbed.addField("Cards for player: ", players.get(i), false);
            printHandCardField(hands.get(i));
            myEmbed.addBlankField(false);
        }
    }



    public void printCardField(ArrayList<Card> cards){
        for(int i = 0; i < cards.size(); ++i){
            myEmbed.addField(cards.get(i).toString(), cards.get(i).printCard(), true);
        }
        //myEmbed.addField("Testing FaceDown", cards.get(0).printFaceDown(), true );
    }


}
