package com.example.samsungproject.secondTrainer;

public enum FragmentTypes {
    SHOW_HIDE(1,"Show/Hide"),
    ATTACH_DETACH(2,"Attach/Detach"),
    REMOVE(3,"Remove");

    private final int id;
    private final String name;
    FragmentTypes(int id, String name){
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
