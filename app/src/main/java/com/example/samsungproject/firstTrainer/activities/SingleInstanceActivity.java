package com.example.samsungproject.firstTrainer.activities;

public class SingleInstanceActivity extends AnyActivity {
    @Override
    protected void setTypeByName(){
        setType(Types.SINGLE_INSTANCE);
    }
}
