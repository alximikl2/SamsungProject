package com.example.samsungproject.secondTrainer.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.samsungproject.secondTrainer.ButtonRow;
import com.example.samsungproject.secondTrainer.MainFragment;
import com.example.samsungproject.R;
import com.google.android.material.button.MaterialButton;

public class AddDialog extends DialogFragment {
    private final ButtonRow buttonRow;

    public AddDialog(ButtonRow buttonRow) {
        this.buttonRow = buttonRow;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.add_dialog, null);
        builder.setView(v);

        Spinner spinner = v.findViewById(R.id.fragment_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.fragment_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        FragmentListener listener = new FragmentListener();
        spinner.setOnItemSelectedListener(listener);

        EditText editText = v.findViewById(R.id.fragment_name);
        MaterialButton button = v.findViewById(R.id.add_button);

        FragmentManager fragmentManager = getParentFragmentManager();

        button.setOnClickListener((View view) -> {
            String name = editText.getText().toString();
            if(!name.equals("")){
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MainFragment fragment = new MainFragment(buttonRow, listener.getResult(), name);
                for (MainFragment fr: buttonRow.getFragments()){
                    switch (fr.getType()){
                        case REMOVE:
                            fragmentTransaction.remove(fr);
                            break;
                        case ATTACH_DETACH:
                            if(!fr.isDetached()){
                                fragmentTransaction.detach(fr);
                            }
                            break;
                        case SHOW_HIDE:
                            if(!fr.isHidden()) {
                                fragmentTransaction.hide(fr);
                            }
                            break;
                        default:
                            throw new NoSuchFieldError("No such fragment type");
                    }
                }
                fragmentTransaction.add(R.id.include, fragment, name);
                fragmentTransaction.commit();
            } else {
                Toast.makeText(getContext(), "Enter A Fragment Name", Toast.LENGTH_SHORT).show();
            }
        });


        return builder.create();
    }
}
