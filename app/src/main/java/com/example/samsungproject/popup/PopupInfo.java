package com.example.samsungproject.popup;

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
    
    public void fillStrings(String parent, String task, String type){
        this.parent.setText(parent);
        this.task.setText(task);
        this.type.setText(type);
    }
}
