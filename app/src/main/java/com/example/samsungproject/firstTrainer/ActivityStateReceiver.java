package com.example.samsungproject.firstTrainer;

import static com.example.samsungproject.firstTrainer.Tags.ID;
import static com.example.samsungproject.firstTrainer.Tags.ON_CREATE;
import static com.example.samsungproject.firstTrainer.Tags.ON_DESTROY;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ActivityStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(ID.getName(), 0);
        if(id == ON_CREATE.getId()) {
            String name = intent.getStringExtra(ON_CREATE.getName());
            if(name != null) {
                Toast.makeText(context,
                        "Активность " + name + " создана",
                        Toast.LENGTH_SHORT).show();
            }
        } else if(id == ON_DESTROY.getId()){
            String name = intent.getStringExtra(ON_DESTROY.getName());
            if(name != null) {
                Toast.makeText(context,
                        "Активность " + name + " уничтожена",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}