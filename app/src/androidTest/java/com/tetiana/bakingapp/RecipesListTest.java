package com.tetiana.bakingapp;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipesListTest {
//        @Rule
//    public ActivityTestRule<MainActivity> mActivityRule =
//            new ActivityTestRule(MainActivity.class);
//
//        @Test
//        public void clickRecyclerViewItem_PassesRecipeNameToNextActivity() {
//            try {
//                Thread.sleep(5000); //wait 5 seconds before going on
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            onView(withId(R.id.rv_show_recipes_list).perform(RecyclerViewActions
//                    .actionOnItemAtPosition(1, click()));
//            onView(withId(R.id.recipe_title)).check(matches(withText("Nutella Pie")));
//        }



}
