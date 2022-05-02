package com.example.zooseeker_team54;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
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
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchResultsTests {

    @Test
    public void displayAllExhibitsContainingQuery() {
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.search_bar)));
        appCompatEditText.perform(replaceText("bear"), closeSoftKeyboard());

        ViewInteraction textView = onView(
                allOf(withId(R.id.loc_name), withText("Black Bear"),
                        withParent(withParent(withId(R.id.search_results))),
                        isDisplayed()));
        textView.check(matches(withText("Black Bear")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.loc_name), withText("Brown Bears"),
                        withParent(withParent(withId(R.id.search_results))),
                        isDisplayed()));
        textView2.check(matches(withText("Brown Bears")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.loc_name), withText("Grizzly Bear"),
                        withParent(withParent(withId(R.id.search_results))),
                        isDisplayed()));
        textView3.check(matches(withText("Grizzly Bear")));
    }

    @Test
    public void displayNoExhibitsNotContainingQuery() {
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.search_bar)));
        appCompatEditText.perform(replaceText("Elephant"), closeSoftKeyboard());

        ViewInteraction LocItemView = onView(allOf(withId(R.id.loc_name), withText("Bear")));
//        LocItemView.check(doesNotExist());
        LocItemView.check(matches(withText("Bear")));
    }

    @Test
    public void displayNoExhibitsWithWeirdQuery() {
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.search_bar)));
        appCompatEditText.perform(replaceText("asodjifjdsa"), closeSoftKeyboard());

        ViewInteraction textView = onView(
                allOf(withId(R.id.loc_name), withText("Black Bear"),
                        withParent(withParent(withId(R.id.search_results))),
                        isDisplayed()));
        textView.check(doesNotExist());
    }

    @Test
    public void displayNoOtherThanExhibits() {
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.search_bar)));
        appCompatEditText.perform(replaceText("en"), closeSoftKeyboard());

        ViewInteraction textView = onView(
                allOf(withId(R.id.loc_name), withText("Entrance Plaza"),
                        withParent(withParent(withId(R.id.search_results))),
                        isDisplayed()));
        textView.check(doesNotExist());
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
