package com.example.zooseeker_team54;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class planRouteTest {
    @Test
    public void sampleTest() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)){
            scenario.moveToState(Lifecycle.State.CREATED);
            scenario.onActivity(activity -> {
                assertEquals(4, 2 + 2);
                TextView planSize = activity.findViewById(R.id.planSize);
                assertEquals("5", planSize.getText().toString());
            });
        }
    }
}