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



}
