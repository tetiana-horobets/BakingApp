package com.tetiana.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.tetiana.bakingapp.recipeIngredient.IngredientActivity;
import com.tetiana.bakingapp.recipeSteps.StepActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class IngredientTest {
//    @Rule
//    public ActivityTestRule<StepActivity> mActivityRule =
//            new ActivityTestRule(StepActivity.class);
//
//    @Test
//    public void clickIngredientButton_OpenNewActivity(){
//        try {
//            Thread.sleep(5000); //wait 5 seconds before going on
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        onView(withId(R.id.rv_show_recipes_list))
//                .perform(actionOnItemAtPosition(0, click()));
//        Intent intent = new Intent();
//        int recipe_id = 0;
//        intent.putExtra("recipeID", recipe_id);
//        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
//        intending(toPackage("com.tetiana.bakingapp.recipeIngredient")).respondWith(result);
//        onView(withId(R.id.ingredients)).perform(click());
//        onView(withId(R.id.frame_layout_ingredient)).check(matches(isDisplayed()));
//    }
}
