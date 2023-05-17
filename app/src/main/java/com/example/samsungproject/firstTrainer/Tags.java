package com.example.samsungproject.firstTrainer;

public enum Tags{
    ON_CREATE("On create", 1),
    ON_DESTROY("On destroy", 2),
    ID("Id", -1),
    ACTIVITY_NAME("Activity name", -2),
    PARENT_NAME("Parent name", -3),
    IS_TASK("Is task", -4),
    REORDER("Reorder", 11),
    CLEAR_TOP("Clear top", 12),
    CLEAR_TASK("Clear task", 12);

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
