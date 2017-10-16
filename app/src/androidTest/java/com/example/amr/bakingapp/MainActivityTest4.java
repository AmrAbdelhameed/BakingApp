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
public class MainActivityTest4 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest4() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.textingredients), withText("Ingredients: \n"),
                        withParent(withId(R.id.scrollView))));
        appCompatTextView.perform(scrollTo(), replaceText("Ingredients: \nsifted cake flour : 400.0 G\ngranulated sugar : 700.0 G\nbaking powder : 4.0 TSP\nsalt : 1.5 TSP\nvanilla extract, divided : 2.0 TBLSP\negg yolks : 8.0 UNIT\nwhole milk : 323.0 G\nunsalted butter, softened and cut into 1 in. cubes : 961.0 G\negg whites : 6.0 UNIT\nmelted and cooled bittersweet or semisweet chocolate : 283.0 G\n"), closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.textDescription), withText("Description:")));
        appCompatTextView2.perform(scrollTo(), replaceText("Description:\n1. Preheat the oven to 350°F. Butter the bottoms and sides of two 9\" round pans with 2\"-high sides. Cover the bottoms of the pans with rounds of parchment paper, and butter the paper as well."), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.textDescription), withText("Description:")));
        appCompatTextView3.perform(scrollTo(), replaceText("Description:\n2. Combine the cake flour, 400 grams (2 cups) of sugar, baking powder, and 1 teaspoon of salt in the bowl of a stand mixer. Using the paddle attachment, beat at low speed until the dry ingredients are mixed together, about one minute"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.textDescription), withText("Description:")));
        appCompatTextView4.perform(scrollTo(), replaceText("Description:\n12. With the mixer still running, pour the melted chocolate into the buttercream. Then add the remaining tablespoon of vanilla and 1/2 teaspoon of salt. Beat at high speed for 30 seconds to ensure the buttercream is well-mixed."), closeSoftKeyboard());

    }

}
