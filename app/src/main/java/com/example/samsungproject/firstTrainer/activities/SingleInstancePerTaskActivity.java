package com.example.samsungproject.firstTrainer.activities;

public class SingleInstancePerTaskActivity extends AnyActivity {
    @Override
    protected void setTypeByName(){
        setType(Types.SINGLE_INSTANCE_PER_TASK);
    }
}