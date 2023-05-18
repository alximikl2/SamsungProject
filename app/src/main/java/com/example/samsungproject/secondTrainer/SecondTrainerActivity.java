package com.example.samsungproject.secondTrainer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;

import com.example.samsungproject.R;
import com.example.samsungproject.databinding.SecondTrainerBinding;
import com.example.samsungproject.popup.PopupTutorial;
import com.example.samsungproject.secondTrainer.dialogs.AddDialog;
import com.google.android.material.button.MaterialButton;

public class SecondTrainerActivity extends AppCompatActivity {

    private SecondTrainerBinding binding;
    private FragmentManager fragmentManager;
    private boolean tutorial;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tutorial = SecondTrainerFields.isTutorialFirst();

        binding = SecondTrainerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentManager = getSupportFragmentManager();
        ButtonRow buttonRow = new ButtonRow(binding.row, this, fragmentManager);

        setTitle("Fragment Trainer");
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(ResourcesCompat.getDrawable(
                getResources(), R.drawable.gradient, getTheme()));

        MaterialButton button = binding.addButton;
        button.setOnClickListener((View view) -> {
            AddDialog dialog = new AddDialog(buttonRow);
            dialog.show(fragmentManager, "New Fragment Dialog");
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
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
                popupTutorial.putString(getResources().getString(R.string.message_popup_second_1));
                this.runOnUiThread(() -> {
                    popupTutorial.showAsDropDown(binding.addButton,
                            (int) (getResources().getDisplayMetrics().density * 8), 0);
                });
            });
            thread.start();
            tutorial = false;
        }
    }
}