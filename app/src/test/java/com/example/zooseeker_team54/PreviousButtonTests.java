package com.example.zooseeker_team54;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

// User Story 4 Tests for MS 2
@RunWith(AndroidJUnit4.class)
public class PreviousButtonTests {
    LocItemDao dao;
    LocDatabase testDb;
    Intent mainIntent, showDirectionIntent;

    @Before
    public void setUp() {
        mainIntent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        showDirectionIntent = new Intent(ApplicationProvider.getApplicationContext(), ShowDirectionActivity.class);
    }

    @Before
    public void resetDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(context, LocDatabase.class)
                .allowMainThreadQueries()
                .build();
        LocDatabase.injectTestDatabase(testDb);

        List<LocItem> todos = LocItem.loadJSON(context, "sample_node_info.json");
        dao = testDb.LocItemDao();
        dao.insertAll(todos);
    }

    /**
     * Adds two exhibits to the route. Clicks the next button, then the previous button
     * Checks to see if the next button still displays the correct information.
     */
    @Test
    public void previousExhibitTest() {
        // Launch MainActivity class
        ActivityScenario<MainActivity> mainScenario = ActivityScenario.launch(mainIntent);
        mainScenario.onActivity(activity -> {
            Utilities.loadOldZooJson(activity);

            LocItem lions = dao.get("lions");
            LocItem gators = dao.get("gators");

            List<LocItem> testItems = new ArrayList<>();
            testItems.add(lions);
            testItems.add(gators);
            activity.getPlannedLocsPresenter().setItems(testItems);

            activity.addPlannedLoc(lions);
            activity.addPlannedLoc(gators);

            RouteInfo routeInfo = activity.findRoute(activity.getPlannedLocsPresenter().getItems());
            showDirectionIntent.putExtra("routeInfo", routeInfo);
        });

        ActivityScenario<ShowDirectionActivity> showDirectionScenario = ActivityScenario.launch(showDirectionIntent);
        showDirectionScenario.onActivity(activity -> {
            Button nextBtn = activity.findViewById(R.id.next_btn);
            nextBtn.performClick();
            Button previousBtn = activity.findViewById(R.id.previous_btn);
            previousBtn.performClick();
            String expectedBtnText = "NEXT\n------\nLions, 200";
            assertEquals(expectedBtnText, nextBtn.getText());
            assertFalse(previousBtn.isEnabled());
            assertTrue(nextBtn.isEnabled());
        });
    }
}
