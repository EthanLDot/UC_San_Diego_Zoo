package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingsActivity extends AppCompatActivity {

    private ToggleButton brief;
    private ToggleButton detailed;
    private Button exitBtn;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        exitBtn = this.findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(this::onExitBtnClicked);

        brief = this.findViewById(R.id.briefDirectionsButton);
        detailed = this.findViewById(R.id.detailedDirectionsButton);

        brief.setOnCheckedChangeListener(getToggleListener(detailed));
        detailed.setOnCheckedChangeListener(getToggleListener(brief));
    }

    /**
     * Setter method for isBrief
     * @param bool boolean for isBrief to be set to
     */
    public void setIsBrief(boolean bool) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isBrief", bool);
        editor.apply();
    }

    /**
     *
     * @param otherBtn
     * @return
     */
    private OnCheckedChangeListener getToggleListener(CompoundButton otherBtn) {
        return (compoundButton, isChecked) -> {
            if (isChecked) {
                setToggleButton(otherBtn, false);
                setIsBrief(otherBtn.equals(detailed));
            }
            else {
                setToggleButton(otherBtn, true);
            }
        };
    }

    /**
     *
     * @param toggleButton
     * @param isChecked
     */
    private void setToggleButton(CompoundButton toggleButton, boolean isChecked)
    {
        toggleButton.setChecked(isChecked);
        String color = isChecked ? "#8BC34A" : "#40737373";
        toggleButton.setBackgroundColor(Color.parseColor(color));
    }

    /**
     *
     * @param view
     */
    private void onExitBtnClicked(View view) {
        finish();
    }

}