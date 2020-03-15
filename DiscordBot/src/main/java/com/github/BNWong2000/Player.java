package com.github.BNWong2000;

import net.dv8tion.jda.api.entities.User;

//import Card;
//import Hand;

public class Player {

    private Hand myHand;
    private String myName;
    private User user;
    private boolean lost;

    public Player( User user){
        this.myName = user.getName();
        this.user = user;
        myHand = new Hand();
        lost = false;
    }

    public Hand getMyHand() {
        return myHand;
    }

    public void setMyHand(Hand myHand) {
        this.myHand = myHand;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
