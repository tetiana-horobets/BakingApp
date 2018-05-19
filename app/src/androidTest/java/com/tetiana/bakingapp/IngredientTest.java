package com.tetiana.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.tetiana.bakingapp.recipeSteps.StepActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class IngredientTest {
    @Rule
    public ActivityTestRule<StepActivity> mActivityRule =
            new ActivityTestRule<StepActivity>(StepActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, StepActivity.class);
                    result.putExtra("recipeID", 1);
                    return result;
                }
            };

    @Test
    public void clickIngredientButton_OpenNewActivity(){

        onView(withId(R.id.ingredients))
                .perform(click());

        onView(withId(R.id.rv_ingredient_list)).check(matches(isDisplayed()));
    }
}
