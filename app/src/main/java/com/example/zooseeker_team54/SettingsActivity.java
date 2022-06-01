package com.example.zooseeker_team54;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Java class representing the functionality of the SettingsActivity that is launched when the user
 * presses the settings button on ShowDirectionActivity
 */
public class SettingsActivity extends AppCompatActivity {

    // initialize buttons
    private Button brief;
    private Button detailed;
    private Button exitBtn;

    /**
     * Create the activity from a savedInstanceState and initialize everything
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
     *
     * @param bool boolean for isBrief to be set to
     */
    public void setIsBrief(boolean bool) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isBrief", bool);
        editor.apply();
    }

    /**
     * Method to get the onClickListened of a given button
     *
     * @param otherBtn Button to get the OnClickListener for
     * @return OnClickListener of the given button
     */
    private OnClickListener getOnClickedListener(Button otherBtn) {
        return (button) -> {
            if (button == brief && !getIsBrief() || button == detailed && getIsBrief()) {
                setButton(otherBtn, false);
                setButton(button, true);
                setIsBrief(otherBtn.equals(detailed));
                System.out.println(getPreferences(MODE_PRIVATE).getBoolean("isBrief", true));
            }
        };
    }

    /**
     * Setter method for a given button
     *
     * @param button    Given button to be set
     * @param isChecked boolean for if the button should be checked or not
     */
    private void setButton(View button, boolean isChecked) {
        String color = isChecked ? "#8BC34A" : "#40737373";
        button.setBackgroundColor(Color.parseColor(color));
    }

    /**
     * Method for when the exit button is clicked, exits the activity
     *
     * @param view View to be passed in
     */
    private void onExitBtnClicked(View view) {
        finish();
    }

    /**
     * Getter method for the isBrief boolean, indicating if the directions should be brief
     *
     * @return boolean if directions should be brief
     */
    public boolean getIsBrief() {
        return getPreferences(MODE_PRIVATE).getBoolean("isBrief", true);
    }

    /**
     * Getter method for the detailed button
     *
     * @return detailed button of the activity
     */
    @VisibleForTesting
    public Button getDetailed() {
        return detailed;
    }

    /**
     * Getter method for the brief button
     *
     * @return brief button of the activity
     */
    @VisibleForTesting
    public Button getBrief() {
        return brief;
    }
}