package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity {

    // booleans to detect whether to display brief or detailed directions
    private boolean isBrief;
    private boolean isDetailed;

    /**
     * Getter method for isBrief
     * @return if brief directions should be displayed
     */
    public boolean getBrief() {
        return this.isBrief;
    }

    /**
     * Getter method for isDetailed
     * @return if detailed directions should be displayed
     */
    public boolean getDetailed() {
        return this.isDetailed;
    }

    /**
     * Setter method for isBrief
     * @param bool boolean for isBrief to be set to
     */
    public void setBrief(boolean bool) {
        this.isBrief = bool;
    }

    /**
     * Setter method for isDetailed
     * @param bool boolean for isDetailed to be set to
     */
    public void setDetailed(boolean bool) {
        this.isDetailed = bool;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ToggleButton brief = this.findViewById(R.id.briefDirectionsButton);
        ToggleButton detailed = this.findViewById(R.id.detailedDirectionsButton);

        //check correct value here, BRIEF by default


        brief.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked)
            {
                if(isChecked)
                {
                    setBool(detailed, false);
                    setBrief(true);
                    Log.d("settings", "Directions: BRIEF");
                }
                else
                {
                    setBool(detailed, true);
                    Log.d("settings", "Directions: DETAILED");
                }
            }
        });
        detailed.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked)
            {
                if(isChecked)
                {
                    setBool(brief, false);
                    setDetailed(true);
                    //Log.d("settings", "Directions: DETAILED");
                }
                else
                {
                    setBool(brief, true);
                    //Log.d("settings", "Directions: BRIEF");
                }
            }
        });
    }

    protected void setBool(CompoundButton toggleButton, boolean isChecked)
    {
        toggleButton.setChecked(isChecked);
        if(isChecked)
        {
            toggleButton.setBackgroundColor(Color.parseColor("#8BC34A"));
        }
        else
        {
            toggleButton.setBackgroundColor(Color.parseColor("#40737373"));
        }
    }


    public void exitOnClick(View view) {
        finish();
    }
}