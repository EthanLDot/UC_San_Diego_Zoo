package com.example.zooseeker_team54;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    private Button brief;
    private Button detailed;
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
        setButton(brief, getIsBrief());
        detailed = this.findViewById(R.id.detailedDirectionsButton);
        setButton(detailed, !getIsBrief());

        brief.setOnClickListener(getOnClickedListener(detailed));
        detailed.setOnClickListener(getOnClickedListener(brief));
    }

    /**
     * Setter method for isBrief
     * @param bool boolean for isBrief to be set to
     */
    public void setIsBrief(boolean bool) {
        SharedPreferences preferences = getSharedPreferences("isBrief", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isBrief", bool);
        editor.apply();
    }

    /**
     *
     * @param otherBtn
     * @return
     */
    private OnClickListener getOnClickedListener(Button otherBtn) {
        return (button) -> {
            if (button == brief && !getIsBrief() || button == detailed && getIsBrief()) {
                setButton(otherBtn, false);
                setButton(button, true);
                setIsBrief(otherBtn.equals(detailed));
                System.out.println(getSharedPreferences("isBrief", MODE_PRIVATE).getBoolean("isBrief", true));
            }
        };
    }

    /**
     *
     * @param button
     * @param isChecked
     */
    private void setButton(View button, boolean isChecked) {
        String color = isChecked ? "#8BC34A" : "#40737373";
        button.setBackgroundColor(Color.parseColor(color));
    }

    /**
     *
     * @param view
     */
    private void onExitBtnClicked(View view) {
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
    }

    public boolean getIsBrief() {
        return getSharedPreferences("isBrief", MODE_PRIVATE).getBoolean("isBrief", true);
    }

    @VisibleForTesting
    public Button getDetailed() {
        return detailed;
    }

    @VisibleForTesting
    public Button getBrief() {
        return brief;
    }
}