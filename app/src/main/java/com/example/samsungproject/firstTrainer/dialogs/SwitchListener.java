package com.example.samsungproject.firstTrainer.dialogs;

import static com.example.samsungproject.firstTrainer.Tags.CLEAR_TOP;
import static com.example.samsungproject.firstTrainer.Tags.REORDER;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.samsungproject.firstTrainer.Tags;

public class SwitchListener implements AdapterView.OnItemSelectedListener {
    private Tags result;

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch ((int) id) {
            case 0:
                result = REORDER;
                break;
            case 1:
                result = CLEAR_TOP;
                break;
            default:
                Log.e("TYPE", "New dialog type found");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public Tags getResult() {
        return result;
    }
}
