package com.example.samsungproject.firstTrainer.activities;

public enum Types {
    STANDARD(0, "Standard"),
    SINGLE_INSTANCE(1, "Single Instance"),
    SINGLE_TOP(2, "Single Top"),
    SINGLE_TASK(3, "Single Task"),
    SINGLE_INSTANCE_PER_TASK(4, "Single Instance Per Task"),
    MAIN(-1, "Main"),
    NULL(-2, "Null");

    private final int id;
    private final String name;
    Types(int id, String name){
        this.name = name;
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
