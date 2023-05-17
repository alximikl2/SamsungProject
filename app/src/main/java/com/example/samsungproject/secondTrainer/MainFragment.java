package com.example.samsungproject.secondTrainer;

import android.annotation.SuppressLint;
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
import com.google.android.material.button.MaterialButton;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private final ButtonRow buttonRow;
    private final FragmentTypes type;
    private final String name;
    private int iterator = 1;

    public MainFragment(ButtonRow buttonRow, FragmentTypes type, String name){
        this.buttonRow = buttonRow;
        this.type = type;
        this.name = name;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        buttonRow.createButton(this);
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        TextView textView = view.findViewById(R.id.name_textview);
        textView.setText("Name: " + name);

        TextView textView2 = view.findViewById(R.id.type_textview);
        textView2.setText("Type: " + type.getName());

        MaterialButton button = view.findViewById(R.id.add_something);
        LinearLayout layout = binding.newViews;

        button.setOnClickListener((View view1) -> {
            TextView newTextView = new TextView(new ContextThemeWrapper(getContext(), R.style.Theme_SecondTrainer));
            newTextView.setText(String.valueOf(iterator));
            layout.addView(newTextView);
            iterator += 1;
        });

        return view;
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