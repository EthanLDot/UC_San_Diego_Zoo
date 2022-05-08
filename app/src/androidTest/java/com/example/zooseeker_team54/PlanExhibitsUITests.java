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
public class PlanExhibitsUITests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Adds one exhibit to an empty list of selected exhibits
     */
    @Test
    public void addOneExhibitToEmpty() {
        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(click());

        ViewInteraction materialAutoCompleteTextView2 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView2.perform(click());

        ViewInteraction materialAutoCompleteTextView3 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView3.perform(replaceText("fox"), closeSoftKeyboard());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.loc_name), withText("Arctic Foxes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.loc_name), withText("Arctic Foxes"),
                        withParent(withParent(withId(R.id.planned_locs))),
                        isDisplayed()));
        textView.check(matches(withText("Arctic Foxes")));
    }

    /**
     * Adds an exhibit to a list that already contains an exhibit.
     * NOTE: This test should be run separately from the previous test
     *       The clear button should be clicked before this test is run.
     */
    @Test
    public void addExhibitToNonEmptyList() {
        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("fox"), closeSoftKeyboard());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.loc_name), withText("Arctic Foxes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialAutoCompleteTextView2 = onView(
                allOf(withId(R.id.search_bar), withText("fox"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView2.perform(replaceText("gorillas"));

        ViewInteraction materialAutoCompleteTextView3 = onView(
                allOf(withId(R.id.search_bar), withText("gorillas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView3.perform(closeSoftKeyboard());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.loc_name), withText("Gorillas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.loc_name), withText("Gorillas"),
                        withParent(withParent(withId(R.id.planned_locs))),
                        isDisplayed()));
        textView.check(matches(withText("Gorillas")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.plan_size), withText("Planned (2)"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(withText("Planned (2)")));
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
