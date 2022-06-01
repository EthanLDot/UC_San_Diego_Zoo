package com.example.zooseeker_team54;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
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
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

// UI Test for User Story 5, 6 in MS 2
@LargeTest
@RunWith(AndroidJUnit4.class)
public class AskToReplan {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void askToReplan() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.clear_btn), withText("clear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("f"), closeSoftKeyboard());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.loc_name), withText("Flamingos"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        1),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialAutoCompleteTextView2 = onView(
                allOf(withId(R.id.search_bar), withText("f"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView2.perform(replaceText(""));

        ViewInteraction materialAutoCompleteTextView3 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView3.perform(closeSoftKeyboard());

        ViewInteraction materialAutoCompleteTextView4 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView4.perform(click());

        ViewInteraction materialAutoCompleteTextView5 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView5.perform(replaceText("m"), closeSoftKeyboard());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.loc_name), withText("Capuchin Monkeys"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialAutoCompleteTextView6 = onView(
                allOf(withId(R.id.search_bar), withText("m"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView6.perform(click());

        ViewInteraction materialAutoCompleteTextView7 = onView(
                allOf(withId(R.id.search_bar), withText("m"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView7.perform(replaceText("g"));

        ViewInteraction materialAutoCompleteTextView8 = onView(
                allOf(withId(R.id.search_bar), withText("g"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView8.perform(closeSoftKeyboard());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.loc_name), withText("Gorillas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.plan_btn), withText("plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        pressBack();

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.plan_btn), withText("plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.direction_btn), withText("Direction"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.mock_route_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.mock_route_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("32.74529,-117.16976"), closeSoftKeyboard());

        ViewInteraction textView = onView(
                allOf(withId(R.id.direction), withText("Proceed on 'Gate Path' 1100 meters towards 'Front Street / Treetops Way' from 'Entrance and Exit Gate'.\n"),
                        withParent(withParent(withId(R.id.route_direction))),
                        isDisplayed()));
        textView.check(matches(withText("Proceed on 'Gate Path' 1100 meters towards 'Front Street / Treetops Way' from 'Entrance and Exit Gate'.\n")));

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.start_mock), withText("Mock"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.ReplanText), withText("Off-Track. Replan?"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(withText("Off-Track. Replan?")));

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.yesButton), withText("Yes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.direction), withText("Proceed on 'Monkey Trail' 1500 meters towards 'Flamingos' from 'Front Street / Monkey Trail'.\n"),
                        withParent(withParent(withId(R.id.route_direction))),
                        isDisplayed()));
        textView3.check(matches(withText("Proceed on 'Monkey Trail' 1500 meters towards 'Flamingos' from 'Front Street / Monkey Trail'.\n")));
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
