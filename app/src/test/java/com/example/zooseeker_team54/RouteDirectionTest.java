package com.example.zooseeker_team54;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RouteDirectionTest {
    /**
     * Pass the RouteDirectionActivity class a single exhibit's directions to display.
     */
    @Test
    public void singleExhibitTest() {
        List<LocEdge> directions = new ArrayList<>();
        directions.add(new LocEdge("Tasmanian Devils", 200, "Zoo Lane", "entrance", "exit"));

        // Launch RouteDirectionActivity class
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), RouteDirectionActivity.class);
        intent.putExtra("directions", (ArrayList<LocEdge>) directions);
        ActivityScenario<RouteDirectionActivity> scenario = ActivityScenario.launch(intent);

        // Check for Correctness
        scenario.onActivity(activity -> {
            DirectionsDisplayRecyclerView display = activity.getDisplayView();
            assertEquals(directions.get(0), display.getDirections().get(0));
        });
    }
}
