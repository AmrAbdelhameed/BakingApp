package com.example.amr.bakingapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest3 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest3() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.textingredients), withText("Ingredients: \n"),
                        withParent(withId(R.id.scrollView))));
        appCompatTextView.perform(scrollTo(), replaceText("Ingredients: \nBittersweet chocolate (60-70% cacao) : 350.0 G\nunsalted butter : 226.0 G\ngranulated sugar : 300.0 G\nlight brown sugar : 100.0 G\nlarge eggs : 5.0 UNIT\nvanilla extract : 1.0 TBLSP\nall purpose flour : 140.0 G\ncocoa powder : 40.0 G\nsalt : 1.5 TSP\nsemisweet chocolate chips : 350.0 G\n"), closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.textDescription), withText("Description:")));
        appCompatTextView2.perform(scrollTo(), replaceText("Description:\n1. Preheat the oven to 350�F. Butter the bottom and sides of a 9\"x13\" pan."), closeSoftKeyboard());

    }

}
