package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.samsungproject.databinding.ActivityMainBinding;
import com.example.samsungproject.firstTrainer.activities.FirstTrainer;
import com.example.samsungproject.secondTrainer.SecondTrainerActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.layout1.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, FirstTrainer.class);
            startActivity(intent);
        });
        binding.layout2.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, SecondTrainerActivity.class);
            startActivity(intent);
        });
    }
}
