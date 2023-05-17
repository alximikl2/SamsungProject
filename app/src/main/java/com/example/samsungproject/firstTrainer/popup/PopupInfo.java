package com.example.samsungproject.firstTrainer.popup;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.samsungproject.R;

public class PopupInfo extends PopupWindow {
    private final TextView parent;
    private final TextView task;
    private final TextView type;

    public PopupInfo(View view, int width, int height, boolean focusable){
        super(view, width, height, focusable);
        this.parent = view.findViewById(R.id.parent);
        this.task = view.findViewById(R.id.task);
        this.type = view.findViewById(R.id.type);
    }

    @SuppressLint("SetTextI18n")
    public void fillStrings(String parent, String task, String type){
        this.parent.setText("Parent: " + parent);
        this.task.setText("Task: " + task);
        this.type.setText("Type: " + type);
    }
}
