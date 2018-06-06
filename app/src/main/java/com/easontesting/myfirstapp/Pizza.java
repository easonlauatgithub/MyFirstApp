package com.easontesting.myfirstapp;

public class Pizza {
    private String name;
    private int imageResourceId;

    private Pizza(String name, int imageResourceId){
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public static final Pizza[] pizzas = {
            new Pizza("Stephen Curry",R.drawable.stephen_curry),
            new Pizza("Kelvin Durant",R.drawable.kelvin_durant),
            new Pizza("Klay Thompson",R.drawable.klay_thompson)
    };

    public String getName(){
        return name;
    }

    public int getImageResourceId(){
        return imageResourceId;
    }
}
