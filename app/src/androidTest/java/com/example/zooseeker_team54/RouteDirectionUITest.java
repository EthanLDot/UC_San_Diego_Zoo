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

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RouteDirectionUITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Selects 3 exhibits (Lions, Alligators, and Arctic Foxes) and plans a route.
     */
    @Test
    public void routeDirectionUITest() {
        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("lion"), closeSoftKeyboard());

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
                allOf(withId(R.id.search_bar), withText("lion"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialAutoCompleteTextView2.perform(click());

        ViewInteraction materialAutoCompleteTextView3 = onView(
                allOf(withId(R.id.search_bar), withText("lion"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialAutoCompleteTextView3.perform(replaceText("gator"));

        ViewInteraction materialAutoCompleteTextView4 = onView(
                allOf(withId(R.id.search_bar), withText("gator"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialAutoCompleteTextView4.perform(closeSoftKeyboard());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.loc_name), withText("Alligators"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialAutoCompleteTextView5 = onView(
                allOf(withId(R.id.search_bar), withText("gator"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialAutoCompleteTextView5.perform(replaceText("fox"));

        ViewInteraction materialAutoCompleteTextView6 = onView(
                allOf(withId(R.id.search_bar), withText("fox"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialAutoCompleteTextView6.perform(closeSoftKeyboard());

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
                allOf(withId(R.id.buttonPlan), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
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
                allOf(withId(R.id.direction), withText("Proceed on 'Entrance Way' 10 meters towards 'Entrance Plaza' from 'Entrance and Exit Gate'.\n"),
                        withParent(withParent(withId(R.id.route_direction))),
                        isDisplayed()));
        textView.check(matches(withText("Proceed on 'Entrance Way' 10 meters towards 'Entrance Plaza' from 'Entrance and Exit Gate'.\n")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.direction), withText("Proceed on 'Reptile Road' 100 meters towards 'Alligators' from 'Entrance Plaza'.\n"),
                        withParent(withParent(withId(R.id.route_direction))),
                        isDisplayed()));
        textView2.check(matches(withText("Proceed on 'Reptile Road' 100 meters towards 'Alligators' from 'Entrance Plaza'.\n")));
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
