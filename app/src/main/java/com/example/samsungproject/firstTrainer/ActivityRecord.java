package com.example.samsungproject.firstTrainer;

import com.example.samsungproject.firstTrainer.activities.Types;

public class ActivityRecord {
    private final String name;
    private final String parent;
    private final boolean isTask;
    private final Types type;
    private final Runnable finish;

    public ActivityRecord(String name, String parent, boolean isTask, Types type, Runnable finish){
        this.name = name;
        this.parent = parent;
        this.isTask = isTask;
        this.type = type;
        this.finish = finish;
    }

    public void finishActivity() {
        finish.run();
    }

    public Types getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public boolean isTask() {
        return isTask;
    }
}
