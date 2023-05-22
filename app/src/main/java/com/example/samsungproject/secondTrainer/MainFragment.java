package com.example.samsungproject.secondTrainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;

import com.example.samsungproject.R;
import com.example.samsungproject.databinding.FragmentMainBinding;
import com.example.samsungproject.popup.PopupTutorial;
import com.google.android.material.button.MaterialButton;

public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private final ButtonRow buttonRow;
    private final FragmentTypes type;
    private final String name;
    private int iterator = 1;
    private boolean tutorial;
    private View viewForPopup;

    public MainFragment(ButtonRow buttonRow, FragmentTypes type, String name){
        this.buttonRow = buttonRow;
        this.type = type;
        this.name = name;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        buttonRow.createButton(this);
        tutorial = SecondTrainerFields.isTutorialThird();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        TextView textView = view.findViewById(R.id.name_textview);
        textView.setText(getString(R.string.name_string) + name);

        TextView textView2 = view.findViewById(R.id.type_textview);
        textView2.setText(getString(R.string.type_string) + type.getName());

        MaterialButton button = view.findViewById(R.id.add_something);
        LinearLayout layout = binding.newViews;

        button.setOnClickListener((View view1) -> {
            TextView newTextView = new TextView(new ContextThemeWrapper(getContext(), R.style.Theme_SecondTrainer));
            newTextView.setText(String.valueOf(iterator));
            layout.addView(newTextView);
            iterator += 1;
        });

        viewForPopup = view;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(tutorial) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                View popupView = getLayoutInflater().inflate(R.layout.tutorial_first_popup, null);
                PopupTutorial popupTutorial = new PopupTutorial(popupView,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        true);
                popupTutorial.putString(getResources().getString(R.string.message_popup_second_3));
                viewForPopup.post(() -> {
                    popupTutorial.showAsDropDown(buttonRow.getLayout(),
                            (int) (getResources().getDisplayMetrics().density * 8), 0);
                });
            });
            thread.start();
            tutorial = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        buttonRow.deleteButton(this);
        super.onDestroy();
    }

    public FragmentTypes getType() {
        return type;
    }
}