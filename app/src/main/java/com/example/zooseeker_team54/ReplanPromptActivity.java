package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReplanPromptActivity extends AppCompatActivity {

    private Button yesBtn;
    private Button noBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replan_prompt);

        this.yesBtn = this.findViewById(R.id.yesButton);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                SharedPreferences preferences = getSharedPreferences("shouldReplan", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("shouldReplan", true);
                editor.apply();
                finish();
            }
        });

        this.noBtn = this.findViewById(R.id.noButton);
        noBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                SharedPreferences preferences = getSharedPreferences("shouldReplan", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("shouldReplan", false);
                editor.apply();
                finish();
            }
        });
    }
}