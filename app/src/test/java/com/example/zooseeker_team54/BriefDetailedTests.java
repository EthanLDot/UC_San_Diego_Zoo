package com.example.zooseeker_team54;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

// MS 2 User Story 2 Unit Tests
@RunWith(AndroidJUnit4.class)
public class BriefDetailedTests {
    Intent settingsIntent;
    ActivityScenario<SettingsActivity> scenario;

    @Before
    public void setUp() {
        settingsIntent = new Intent(ApplicationProvider.getApplicationContext(), SettingsActivity.class);
        scenario = ActivityScenario.launch(SettingsActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);
    }

    @Test
    public void changeBriefToDetailed() {
        scenario.onActivity(activity -> {
            activity.setIsBrief(true);
            activity.getDetailed().performClick();
            assertFalse(activity.getIsBrief());
        });
    }

    @Test
    public void changeDetailedToBrief() {
        scenario.onActivity(activity -> {
            activity.setIsBrief(false);
            activity.getBrief().performClick();
            assertTrue(activity.getIsBrief());
        });
    }

    @Test
    public void noChangeDetailed() {
        scenario.onActivity(activity -> {
            activity.setIsBrief(false);
            assertFalse(activity.getIsBrief());
        });
    }

    @Test
    public void noChangeBrief() {
        scenario.onActivity(activity -> {
            activity.setIsBrief(true);
            assertTrue(activity.getIsBrief());
        });
    }
}
