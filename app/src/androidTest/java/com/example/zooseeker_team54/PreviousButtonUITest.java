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
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PreviousButtonUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void previousButtonUITest() {
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
        materialAutoCompleteTextView.perform(replaceText("i"), closeSoftKeyboard());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.loc_name), withText("Capuchin Monkeys"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.loc_name), withText("Crocodiles"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.loc_name), withText("Flamingos"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction materialTextView4 = onView(
                allOf(withId(R.id.loc_name), withText("Gorillas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_results),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView4.perform(click());

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

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT\n------\nCapuchin Monkeys, 3100"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT\n------\nCrocodiles, 3800"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.previous_btn), withText("previous"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.direction), withText("Proceed on 'Monkey Trail' 3100 feet towards 'Flamingos' from 'Capuchin Monkeys'.\n"),
                        withParent(withParent(withId(R.id.route_direction))),
                        isDisplayed()));
        textView.check(matches(withText("Proceed on 'Monkey Trail' 3100 feet towards 'Flamingos' from 'Capuchin Monkeys'.\n")));

        ViewInteraction button = onView(
                allOf(withId(R.id.next_btn), withText("NEXT\n------\nCROCODILES, 3800"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.previous_btn), withText("previous"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.direction), withText("Proceed on 'Monkey Trail' 1500 feet towards 'Front Street / Monkey Trail' from 'Flamingos'.\n"),
                        withParent(withParent(withId(R.id.route_direction))),
                        isDisplayed()));
        textView2.check(matches(withText("Proceed on 'Monkey Trail' 1500 feet towards 'Front Street / Monkey Trail' from 'Flamingos'.\n")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.direction), withText("Proceed on 'Gate Path' 1100 feet towards 'Entrance and Exit Gate' from 'Front Street / Treetops Way'.\n"),
                        withParent(withParent(withId(R.id.route_direction))),
                        isDisplayed()));
        textView3.check(matches(withText("Proceed on 'Gate Path' 1100 feet towards 'Entrance and Exit Gate' from 'Front Street / Treetops Way'.\n")));
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
