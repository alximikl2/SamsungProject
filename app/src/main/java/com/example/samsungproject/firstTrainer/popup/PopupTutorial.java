package com.example.samsungproject.firstTrainer.popup;

import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.samsungproject.R;

public class PopupTutorial extends PopupWindow {
    private final TextView text;
    public PopupTutorial(View view, int width, int height, boolean focusable){
        super(view, width, height, focusable);
        text = view.findViewById(R.id.message_first);
    }
    public void putString(String string){
        text.setText(string);
    }
}
