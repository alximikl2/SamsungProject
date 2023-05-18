package com.example.samsungproject.secondTrainer;

public enum Tags{
    ON_CREATE("On create", 1),
    ON_DESTROY("On destroy", 2);

    private final String name;
    private final int id;

    Tags(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
