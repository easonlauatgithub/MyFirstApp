package com.easontesting.myfirstapp;

public class Workout {
    private String name;
    private String description;

    private Workout(String name, String description){
        this.name = name;
        this.description = description;
    }

    public static final Workout[] workouts ={
        new Workout("PHP","Website programming language"),
        new Workout("ANDROID","Mobile app programming language for Android smartphone"),
        new Workout("IOS","Mobile app programming language for Apple smartphone")
    };

    public String getDescription(){
        return description;
    }
    public String getName(){
        return name;
    }
    public String toString(){
        return this.name;
    }
}
