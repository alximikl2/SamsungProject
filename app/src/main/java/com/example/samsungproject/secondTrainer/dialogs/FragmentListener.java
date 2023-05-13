package com.example.samsungproject.secondTrainer.dialogs;

import static com.example.samsungproject.secondTrainer.FragmentTypes.ATTACH_DETACH;
import static com.example.samsungproject.secondTrainer.FragmentTypes.REMOVE;
import static com.example.samsungproject.secondTrainer.FragmentTypes.SHOW_HIDE;

import android.view.View;
import android.widget.AdapterView;

import com.example.samsungproject.secondTrainer.FragmentTypes;

public class FragmentListener implements AdapterView.OnItemSelectedListener {
    private FragmentTypes result;

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch ((int) id) {
            case 0:
                result = REMOVE;
                break;
            case 1:
                result = ATTACH_DETACH;
                break;
            case 2:
                result = SHOW_HIDE;
                break;
            default:
                throw new NoSuchFieldError("No such fragment type");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public FragmentTypes getResult() {
        return result;
    }
}
