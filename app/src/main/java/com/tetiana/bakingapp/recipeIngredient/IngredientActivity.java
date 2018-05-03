package com.tetiana.bakingapp.recipeIngredient;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.model.Ingredient;
import com.tetiana.bakingapp.recipeSteps.StepListFragment;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {
    private IngredientAdapter ingredientAdapter;
    ArrayList<Ingredient> ingredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_layout, RecipeIngredientFragment.newInstance())
                .commit();

        FragmentManager fragmentManager2 = getSupportFragmentManager();
        fragmentManager2.beginTransaction()
                .add(R.id.frame_layout2, StepListFragment.newInstance())
                .commit();
    }
}
