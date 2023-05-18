package com.example.samsungproject.firstTrainer.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.samsungproject.R;
import com.example.samsungproject.firstTrainer.FirstTrainerFields;
import com.example.samsungproject.firstTrainer.Tags;
import com.example.samsungproject.firstTrainer.activities.SingleInstanceActivity;
import com.example.samsungproject.firstTrainer.activities.SingleInstancePerTaskActivity;
import com.example.samsungproject.firstTrainer.activities.SingleTaskActivity;
import com.example.samsungproject.firstTrainer.activities.SingleTopActivity;
import com.example.samsungproject.firstTrainer.activities.StandardActivity;
import com.example.samsungproject.firstTrainer.activities.Types;
import com.example.samsungproject.popup.PopupTutorial;

public class ActivitySwitchDialog extends DialogFragment {
    private boolean tutorial;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        tutorial = FirstTrainerFields.isTutorialThird();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.switch_activity_dialog, null);
        builder.setView(v);

        Spinner spinner = v.findViewById(R.id.type_selector_switch);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.activity_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        CreateListener spinnerActivity = new CreateListener();
        spinner.setOnItemSelectedListener(spinnerActivity);


        Spinner spinner2 = v.findViewById(R.id.switch_method_selector);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.switch_choices, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        SwitchListener switchListener = new SwitchListener();
        spinner2.setOnItemSelectedListener(switchListener);

        if (tutorial) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                View popupView = inflater.inflate(R.layout.tutorial_first_popup, null);
                PopupTutorial popupTutorial = new PopupTutorial(popupView,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        true);
                popupTutorial.putString(getResources().getString(R.string.message_popup_first_3));
                v.post(() -> {
                    popupTutorial.showAsDropDown(spinner);
                });
            });
            thread.start();
        }

        Button button = v.findViewById(R.id.switch_button);
        button.setOnClickListener((View view) -> {
            Types result = spinnerActivity.getResult();
            Tags switchType = switchListener.getResult();
            Intent intent = null;
            switch (result) {
                case STANDARD:
                    intent = new Intent(getContext(), StandardActivity.class);
                    break;
                case SINGLE_INSTANCE:
                    intent = new Intent(getContext(), SingleInstanceActivity.class);
                    break;
                case SINGLE_TOP:
                    intent = new Intent(getContext(), SingleTopActivity.class);
                    break;
                case SINGLE_TASK:
                    intent = new Intent(getContext(), SingleTaskActivity.class);
                    break;
                case SINGLE_INSTANCE_PER_TASK:
                    intent = new Intent(getContext(), SingleInstancePerTaskActivity.class);
                    break;
                default:
                    Log.e("TYPE", "New dialog type found");
            }
            switch (switchType){
                case REORDER:
                    assert intent != null;
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    break;
                case CLEAR_TOP:
                    assert intent != null;
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    break;
                case CLEAR_TASK:
                    assert intent != null;
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    break;
                default:
                    Log.e("TYPE", "New dialog type found");
            }
            startActivity(intent);
            this.dismiss();
        });

        return builder.create();
    }
}
