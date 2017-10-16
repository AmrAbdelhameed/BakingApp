package com.example.amr.bakingapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.textingredients), withText("Ingredients: \n"),
                        withParent(withId(R.id.scrollView))));
        appCompatTextView.perform(scrollTo(), replaceText("Ingredients: \nBittersweet chocolate (60-70% cacao) : 350.0 G\nunsalted butter : 226.0 G\ngranulated sugar : 300.0 G\nlight brown sugar : 100.0 G\nlarge eggs : 5.0 UNIT\nvanilla extract : 1.0 TBLSP\nall purpose flour : 140.0 G\ncocoa powder : 40.0 G\nsalt : 1.5 TSP\nsemisweet chocolate chips : 350.0 G\n"), closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.textDescription), withText("Description:")));
        appCompatTextView2.perform(scrollTo(), replaceText("Description:\n3. Mix both sugars into the melted chocolate in a large mixing bowl until mixture is smooth and uniform."), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.buttonNext),
                        withParent(allOf(withId(R.id.activity_details),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.textDescription), withText("Description:")));
        appCompatTextView3.perform(scrollTo(), replaceText("Description:\n4. Sift together the flour, cocoa, and salt in a small bowl and whisk until mixture is uniform and no clumps remain. "), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.buttonNext),
                        withParent(allOf(withId(R.id.activity_details),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.textDescription), withText("Description:")));
        appCompatTextView4.perform(scrollTo(), replaceText("Description:\n5. Crack 3 eggs into the chocolate mixture and carefully fold them in. Crack the other 2 eggs in and carefully fold them in. Fold in the vanilla."), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.buttonPrevious),
                        withParent(allOf(withId(R.id.activity_details),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.textDescription), withText("Description:")));
        appCompatTextView5.perform(scrollTo(), replaceText("Description:\n4. Sift together the flour, cocoa, and salt in a small bowl and whisk until mixture is uniform and no clumps remain. "), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.buttonPrevious),
                        withParent(allOf(withId(R.id.activity_details),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(R.id.textDescription), withText("Description:")));
        appCompatTextView6.perform(scrollTo(), replaceText("Description:\n3. Mix both sugars into the melted chocolate in a large mixing bowl until mixture is smooth and uniform."), closeSoftKeyboard());

    }

}
