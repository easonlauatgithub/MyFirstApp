package com.easontesting.myfirstapp;
import java.util.ArrayList;
import java.util.List;

public class BeerExpert {
    List<String> getBrands(String color){
        List<String> brands = new ArrayList<String>();

        switch(color){
            case "Golden State Warriors":
                brands.add("Stephen Curry");
                brands.add("Kevin Durant");
                brands.add("Klay Thompson");
                brands.add("Draymond Green");
                brands.add("Andre Iguodala");
                brands.add("David West");
                brands.add("JaVale McGee");
                break;
            case "Oklahoma City Thunder":
                brands.add("Russell Westbrook");
                brands.add("Paul George");
                brands.add("Carmelo Anthony");
                brands.add("Steven Adams");
                break;
            case "Cleveland Cavaliers":
                brands.add("LeBron James");
                brands.add("Kevin Love");
                brands.add("Derrick Rose");
                brands.add("Dwyane Wade");
                brands.add("JR Smith");
                break;
            default:
                brands.add("Kyrie Irving");
                brands.add("Gordon Hayward");
                brands.add("Al Horford");
        }
        /*
        if(color.equals("Golden State Warriors")){
            brands.add("Jack Amber");
            brands.add("Rex Moose Amber");
        }else if(color.equals("Oklahoma City Thunder")){
            brands.add("Jail Pale Ale");
            brands.add("Gout Stout");
        }else if(color.equals("Cleveland Cavaliers")){

        }else if(color.equals("Boston Celtics")){

        }
        */
        return brands;
    }
}