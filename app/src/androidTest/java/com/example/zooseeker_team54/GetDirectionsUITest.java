package com.example.zooseeker_team54;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@LargeTest
@RunWith(AndroidJUnit4.class)
// User Story 5,6 UI Test
public class GetDirectionsUITest {
    LocItemDao dao;
    LocDatabase testDb;
    Intent mainIntent;

    @Before
    public void setUp() {
        mainIntent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        ActivityScenario<MainActivity> mainActivityActivityScenario = ActivityScenario.launch(mainIntent);
        mainActivityActivityScenario.onActivity(Utilities::loadOldZooJson);
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
     * Adds three exhibits to a plan, creates a plan, and checks to see that the
     * directions displayed are correct, and the buttons show the correct information.
     */
    @Test
    public void getDirectionsUITest() {
        ViewInteraction clearButton = onView(
                allOf(withId(R.id.clear_btn), withText("clear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        clearButton.perform(click());

        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("lions"), closeSoftKeyboard());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.loc_name), withText("Lions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialAutoCompleteTextView2 = onView(
                allOf(withId(R.id.search_bar), withText("lions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView2.perform(replaceText("gators"));

        ViewInteraction materialAutoCompleteTextView3 = onView(
                allOf(withId(R.id.search_bar), withText("gators"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView3.perform(closeSoftKeyboard());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.loc_name), withText("Alligators"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialAutoCompleteTextView4 = onView(
                allOf(withId(R.id.search_bar), withText("gators"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView4.perform(replaceText("fox"));

        ViewInteraction materialAutoCompleteTextView5 = onView(
                allOf(withId(R.id.search_bar), withText("fox"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView5.perform(closeSoftKeyboard());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.loc_name), withText("Arctic Foxes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.plan_btn), withText("plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.direction_btn), withText("Direction"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.direction), withText("Proceed on 'Entrance Way' 10 feet towards 'Entrance Plaza' from 'Entrance and Exit Gate'.\n"),
                        withParent(withParent(withId(R.id.route_direction))),
                        isDisplayed()));
        textView.check(matches(withText("Proceed on 'Entrance Way' 10 feet towards 'Entrance Plaza' from 'Entrance and Exit Gate'.\n")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.direction), withText("Proceed on 'Reptile Road' 100 feet towards 'Alligators' from 'Entrance Plaza'.\n"),
                        withParent(withParent(withId(R.id.route_direction))),
                        isDisplayed()));
        textView2.check(matches(withText("Proceed on 'Reptile Road' 100 feet towards 'Alligators' from 'Entrance Plaza'.\n")));

        ViewInteraction button = onView(
                allOf(withId(R.id.next_btn), withText("NEXT\n------\nLIONS, 200"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT\n------\nLIONS, 200"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT\n------\nLions, 200")));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT\n------\nArctic Foxes, 600")));
        materialButton4.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.direction), withText("Proceed on 'Arctic Avenue' 300 feet towards 'Arctic Foxes' from 'Entrance Plaza'.\n"),
                        withParent(withParent(withId(R.id.route_direction))),
                        isDisplayed()));
        textView3.check(matches(withText("Proceed on 'Arctic Avenue' 300 feet towards 'Arctic Foxes' from 'Entrance Plaza'.\n")));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT\n------\nENTRANCE AND EXIT GATE, 310"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT\n------\nENTRANCE AND EXIT GATE, 310")));
        materialButton5.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.direction), withText("Proceed on 'Arctic Avenue' 300 feet towards 'Entrance Plaza' from 'Arctic Foxes'.\n"),
                        withParent(withParent(withId(R.id.route_direction))),
                        isDisplayed()));
        textView4.check(matches(withText("Proceed on 'Arctic Avenue' 300 feet towards 'Entrance Plaza' from 'Arctic Foxes'.\n")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.direction), withText("Proceed on 'Entrance Way' 10 feet towards 'Entrance and Exit Gate' from 'Entrance Plaza'.\n"),
                        withParent(withParent(withId(R.id.route_direction))),
                        isDisplayed()));
        textView5.check(matches(withText("Proceed on 'Entrance Way' 10 feet towards 'Entrance and Exit Gate' from 'Entrance Plaza'.\n")));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT\n------\nNO EXHIBITS LEFT!"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
