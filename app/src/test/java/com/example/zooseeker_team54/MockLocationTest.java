package com.example.zooseeker_team54;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

// Milestone 2 Developer Story 2 Tests
@RunWith(AndroidJUnit4.class)
public class MockLocationTest {
    Intent showDirectionIntent;
    ActivityScenario<ShowDirectionActivity> showDirectionScneario;

    @Before
    public void setUp() {
        showDirectionIntent = new Intent(ApplicationProvider.getApplicationContext(), ShowDirectionActivity.class);
        showDirectionScneario = ActivityScenario.launch(showDirectionIntent);
        showDirectionScneario.moveToState(Lifecycle.State.CREATED);
        showDirectionScneario.moveToState(Lifecycle.State.STARTED);
        showDirectionScneario.moveToState(Lifecycle.State.RESUMED);
    }

    @Test
    public void simpleInputTest() {
        showDirectionScneario.onActivity(activity -> {
            activity.mockRouteInput.setText("89.55, 133.22");
            activity.mockStep.performClick();

//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            activity.locationTracker.mockLocation(new Coord(89.55, 133.22));
            System.out.println("Result: " + activity.locationTracker.getUserCoordLive().getValue());
        });

    }
}
