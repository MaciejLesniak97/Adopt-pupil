package com.maciejlesniak;

public class Cards {
    private String userId;
    private String name;
    //Konstruktor
    public Cards (String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }
    public void setName(){
        this.name = name;
    }
}
