package com.easontesting.myfirstapp;

public class Pasta {
    private String name;
    private int imageResourceId;

    private Pasta(String name, int imageResourceId){
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public static final Pasta[] pastas = {
            new Pasta("Leborn James",R.drawable.leborn_james),
            new Pasta("Kelvin Love",R.drawable.kevin_love)
    };

    public String getName(){
        return name;
    }

    public int getImageResourceId(){
        return imageResourceId;
    }
}
