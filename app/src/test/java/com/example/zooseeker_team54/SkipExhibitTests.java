package com.example.zooseeker_team54;

import static org.junit.Assert.assertEquals;
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

// User Story 4 Unit Tests
@RunWith(AndroidJUnit4.class)
public class SkipExhibitTests {
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

        List<LocItem> todos = LocItem.loadJSON(context, "exhibit_info.json");
        dao = testDb.LocItemDao();
        dao.insertAll(todos);
    }

    /**
     * Pass the ShowDirectionActivity class a single exhibit's directions to display.
     */
    @Test
    public void notLastExhibitTest() {

        // Launch MainActivity class
        ActivityScenario<MainActivity> mainActivityActivityScenario = ActivityScenario.launch(mainIntent);
        mainActivityActivityScenario.onActivity(activity -> {

            Utilities.loadNewZooJson(activity);

            LocItem gorillas = dao.get("gorilla");
            LocItem orangutans = dao.get("orangutan");

            List<LocItem> testItems = new ArrayList<>();
            testItems.add(gorillas);
            testItems.add(orangutans);

            activity.getPlannedLocsPresenter().setItems(testItems);

            activity.addPlannedLoc(gorillas);
            activity.addPlannedLoc(orangutans);

            RouteInfo routeInfo = activity.findRoute(activity.getPlannedLocsPresenter().getItems());
            routeDirectionIntent.putExtra("routeInfo", routeInfo);
        });

        ActivityScenario<ShowDirectionActivity> routeDirectionActivityActivityScenario = ActivityScenario.launch(routeDirectionIntent);
        routeDirectionActivityActivityScenario.onActivity(activity -> {
            Button skipBtn = activity.findViewById(R.id.skip_btn);
            skipBtn.performClick();
            String expectedDirections = "[Proceed on 'Gate Path' 1100 feet towards 'Front Street / Treetops Way' from 'Entrance and Exit Gate'.\n" +
                    ", Proceed on 'Treetops Way' 1100 feet towards 'Treetops Way / Fern Canyon Trail' from 'Front Street / Treetops Way'.\n" +
                    ", Proceed on 'Treetops Way' 1400 feet towards 'Treetops Way / Orangutan Trail' from 'Treetops Way / Fern Canyon Trail'.\n" +
                    ", Proceed on 'Treetops Way' 1900 feet towards 'Treetops Way / Hippo Trail' from 'Treetops Way / Orangutan Trail'.\n" +
                    ", Proceed on 'Hippo Trail' 1900 feet towards 'Hippos' from 'Treetops Way / Hippo Trail'.\n" +
                    ", Proceed on 'Hippo Trail' 1100 feet towards 'Crocodiles' from 'Hippos'.\n" +
                    ", Proceed on 'Hippo Trail' 1500 feet towards 'Monkey Trail / Hippo Trail' from 'Crocodiles'.\n" +
                    ", Proceed on 'Monkey Trail' 1200 feet towards 'Scripps Aviary' from 'Monkey Trail / Hippo Trail'.\n" +
                    ", Proceed on 'Monkey Trail' 1200 feet towards 'Gorillas' from 'Scripps Aviary'.\n]";
            List<LocEdge> directions = activity.getRouteDirectionPresenter().getItems();
            assertEquals(expectedDirections, directions.toString());
        });

    }

    /**
     * Pass the ShowDirectionActivity. a plan with more than one exhibit.
     * Should only display the directions for the first exhibit in the plan.
     */
    @Test
    public void lastExhibitTest() {

        // Launch MainActivity class
        ActivityScenario<MainActivity> mainActivityActivityScenario = ActivityScenario.launch(mainIntent);
        mainActivityActivityScenario.onActivity(activity -> {

            Utilities.loadNewZooJson(activity);

            LocItem gorillas = dao.get("gorilla");
            LocItem orangutans = dao.get("orangutan");

            List<LocItem> testItems = new ArrayList<>();
            testItems.add(gorillas);
            testItems.add(orangutans);

            activity.getPlannedLocsPresenter().setItems(testItems);

            activity.addPlannedLoc(gorillas);
            activity.addPlannedLoc(orangutans);

            RouteInfo routeInfo = activity.findRoute(activity.getPlannedLocsPresenter().getItems());
            routeDirectionIntent.putExtra("routeInfo", routeInfo);
        });

        ActivityScenario<ShowDirectionActivity> routeDirectionActivityActivityScenario = ActivityScenario.launch(routeDirectionIntent);
        routeDirectionActivityActivityScenario.onActivity(activity -> {
            Button nextBtn = activity.findViewById(R.id.next_btn);
            Button skipBtn = activity.findViewById(R.id.skip_btn);
            nextBtn.performClick();
            skipBtn.performClick();
            String expectedDirections = "[Proceed on 'Orangutan Trail' 1100 feet towards 'Siamangs' from 'Orangutans'.\n" +
                    ", Proceed on 'Orangutan Trail' 1200 feet towards 'Treetops Way / Orangutan Trail' from 'Siamangs'.\n" +
                    ", Proceed on 'Treetops Way' 1400 feet towards 'Treetops Way / Fern Canyon Trail' from 'Treetops Way / Orangutan Trail'.\n" +
                    ", Proceed on 'Treetops Way' 1100 feet towards 'Front Street / Treetops Way' from 'Treetops Way / Fern Canyon Trail'.\n" +
                    ", Proceed on 'Gate Path' 1100 feet towards 'Entrance and Exit Gate' from 'Front Street / Treetops Way'.\n]";
            List<LocEdge> directions = activity.getRouteDirectionPresenter().getItems();
            assertEquals(expectedDirections, directions.toString());
        });
    }
}
