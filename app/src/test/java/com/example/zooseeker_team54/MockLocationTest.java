package com.example.zooseeker_team54;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;

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
    Intent routeDirectionIntent;

    @Before
    public void setUp() {
        routeDirectionIntent = new Intent(ApplicationProvider.getApplicationContext(), RouteDirectionActivity.class);
    }

    @Test
    public void simpleInputTest() {
        ActivityScenario<RouteDirectionActivity> routeDirectionActivityActivityScenario = ActivityScenario.launch(routeDirectionIntent);
        routeDirectionActivityActivityScenario.onActivity(activity -> {
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
