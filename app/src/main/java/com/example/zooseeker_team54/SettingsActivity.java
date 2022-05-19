package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ToggleButton brief = this.findViewById(R.id.briefDirectionsButton);
        ToggleButton detailed = this.findViewById(R.id.detailedDirectionsButton);
        brief.setChecked(true);
        brief.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked)
            {
                if(isChecked)
                {
                    detailed.setChecked(false);
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
                    brief.setChecked(false);
                }
            }
        });
    }


    public void exitOnClick(View view) {
        finish();
    }
}