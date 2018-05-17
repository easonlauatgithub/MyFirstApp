package com.easontesting.myfirstapp;

public class Drink {
    private String name;
    private String description;
    private int imageResourceId;
    private Drink(String name, String description, int imageResourceId){
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }
    public static final Drink[] drinks = {
            new Drink("Latte[Drink.java]", "Latter is ...", R.drawable.coffee_latte),
            new Drink("Cappuccino[Drink.java]", "Cappuccino is ...", R.drawable.coffee_cappuccino),
            new Drink("Mocha[Drink.java]", "Mocha is ...", R.drawable.coffee_mocha)
    };
    public String getName(){ return name; }
    public String getDescription(){ return description; }
    public int getImageResourceId(){ return imageResourceId; }
    public String toString(){ return this.name; }
}
