package com.example.samsungproject.firstTrainer.dialogs;

import static com.example.samsungproject.firstTrainer.activities.Types.SINGLE_INSTANCE;
import static com.example.samsungproject.firstTrainer.activities.Types.SINGLE_INSTANCE_PER_TASK;
import static com.example.samsungproject.firstTrainer.activities.Types.SINGLE_TASK;
import static com.example.samsungproject.firstTrainer.activities.Types.SINGLE_TOP;
import static com.example.samsungproject.firstTrainer.activities.Types.STANDARD;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.samsungproject.firstTrainer.activities.Types;

public class ActivityListener implements AdapterView.OnItemSelectedListener {
    private Types result;

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch ((int) id) {
            case 0:
                result = STANDARD;
                break;
            case 1:
                result = SINGLE_INSTANCE;
                break;
            case 2:
                result = SINGLE_TOP;
                break;
            case 3:
                result = SINGLE_TASK;
                break;
            case 4:
                result = SINGLE_INSTANCE_PER_TASK;
                break;
            default:
                Log.e("TYPE", "New dialog type found");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public Types getResult() {
        return result;
    }
}
