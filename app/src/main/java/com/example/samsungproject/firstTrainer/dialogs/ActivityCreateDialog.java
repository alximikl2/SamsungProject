package com.example.samsungproject.firstTrainer.dialogs;

import static com.example.samsungproject.firstTrainer.Tags.ACTIVITY_NAME;
import static com.example.samsungproject.firstTrainer.Tags.ID;
import static com.example.samsungproject.firstTrainer.Tags.IS_TASK;
import static com.example.samsungproject.firstTrainer.Tags.PARENT_NAME;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.samsungproject.R;
import com.example.samsungproject.firstTrainer.FirstTrainerFields;
import com.example.samsungproject.firstTrainer.activities.SingleInstanceActivity;
import com.example.samsungproject.firstTrainer.activities.SingleInstancePerTaskActivity;
import com.example.samsungproject.firstTrainer.activities.SingleTaskActivity;
import com.example.samsungproject.firstTrainer.activities.SingleTopActivity;
import com.example.samsungproject.firstTrainer.activities.StandardActivity;
import com.example.samsungproject.firstTrainer.activities.Types;
import com.example.samsungproject.popup.PopupTutorial;

public class ActivityCreateDialog extends DialogFragment {
    private final String parent;
    private boolean tutorial;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        tutorial = FirstTrainerFields.isTutorialSecond();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.create_activity_dialog, null);
        builder.setView(v);

        Spinner spinner = v.findViewById(R.id.type_selector_create);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.activity_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        CreateListener spinnerActivity = new CreateListener();
        spinner.setOnItemSelectedListener(spinnerActivity);

        EditText editText = v.findViewById(R.id.activity_name);
        CheckBox checkBox = v.findViewById(R.id.is_task);

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
                popupTutorial.putString(getResources().getString(R.string.message_popup_first_2));
                v.post(() -> {
                    popupTutorial.showAsDropDown(editText);
                });
            });
            thread.start();
        }

        Button button = v.findViewById(R.id.add_button);
        button.setOnClickListener((View view) -> {
            String name = editText.getText().toString();
            if(name.equals("")) {
                Toast.makeText(getContext(), "Enter an activity name", Toast.LENGTH_SHORT).show();
            } else if(name.contains("null")) {
                Toast.makeText(getContext(), "Null name's are reserved", Toast.LENGTH_SHORT).show();
            } else if(FirstTrainerFields.inRecords(name)){
                Toast.makeText(getContext(), "Activity with that name already exists", Toast.LENGTH_SHORT).show();
            } else {
                Types result = spinnerActivity.getResult();

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
                assert intent != null;
                intent.putExtra(ACTIVITY_NAME.getName(), editText.getText().toString());
                intent.putExtra(PARENT_NAME.getName(), parent);
                intent.putExtra(ID.getName(), result);
                if(checkBox.isChecked()){
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.putExtra(IS_TASK.getName(), true);
                } else{
                    intent.putExtra(IS_TASK.getName(), false);
                }
                intent.putExtra("boolean", tutorial);
                startActivity(intent);
                this.dismiss();
            }
        });

        return builder.create();
    }
    public ActivityCreateDialog(String parent){
        this.parent = parent;
    }
}

