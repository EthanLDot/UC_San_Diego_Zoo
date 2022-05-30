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
import java.util.HashMap;
import java.util.List;

// User Story 5, 6 Unit Tests
@RunWith(AndroidJUnit4.class)
public class NextDirectionTests {
    LocItemDao dao;
    LocDatabase testDb;
    Intent mainIntent, routeDirectionIntent;

    @Before
    public void setUp() {
        mainIntent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        routeDirectionIntent = new Intent(ApplicationProvider.getApplicationContext(), ShowDirectionActivity.class);
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

    @Test
    public void noMoreExhibitsTest() {

        ActivityScenario<ShowDirectionActivity> routeDirectionActivityActivityScenario = ActivityScenario.launch(routeDirectionIntent);
        routeDirectionActivityActivityScenario.onActivity(activity -> {
            String nextBtnText = "NEXT\n------\n" + "No Exhibits Left!";
            Button nextBtn = activity.findViewById(R.id.next_btn);
            assertEquals(nextBtnText, nextBtn.getText());
            assertFalse(nextBtn.isEnabled());
        });

    }

    @Test
    public void MoreExhibitsTest() {
        // Launch MainActivity class
        ActivityScenario<MainActivity> mainActivityActivityScenario = ActivityScenario.launch(mainIntent);
        mainActivityActivityScenario.onActivity(activity -> {

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
            routeDirectionIntent.putExtra("routeInfo", routeInfo);
        });

        ActivityScenario<ShowDirectionActivity> routeDirectionActivityActivityScenario = ActivityScenario.launch(routeDirectionIntent);
        routeDirectionActivityActivityScenario.onActivity(activity -> {
            Button nextBtn = activity.findViewById(R.id.next_btn);
            String expectedBtnText = "NEXT\n------\nLions, 200";
            assertEquals(expectedBtnText, nextBtn.getText());
            assertTrue(nextBtn.isEnabled());
        });

    }
}
