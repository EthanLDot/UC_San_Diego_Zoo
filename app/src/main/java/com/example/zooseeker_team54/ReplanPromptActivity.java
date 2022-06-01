package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Class to represent the functionality of the ReplanPromptActivity, launched when user is off-track
 */
public class ReplanPromptActivity extends AppCompatActivity {

    // initialize buttons
    private Button yesBtn;
    private Button noBtn;

    /**
     * Create the activity from a savedInstanceState and initialize everything
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replan_prompt);

        // create the "Yes" button and set an OnClickListener for it
        this.yesBtn = this.findViewById(R.id.yesButton);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                // set the replan boolean as true in the shared preferences and exit the activity
                SharedPreferences preferences = getSharedPreferences("shouldReplan", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("shouldReplan", true);
                editor.apply();
                finish();
            }
        });

        // create the "No" button and set an OnClickListener for it
        this.noBtn = this.findViewById(R.id.noButton);
        noBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                // set the replan boolean as false in the shared preferences and exit the activity
                SharedPreferences preferences = getSharedPreferences("shouldReplan", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("shouldReplan", false);
                editor.apply();
                finish();
            }
        });
    }
}