package com.tetiana.bakingapp.recipeIngredient;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tetiana.bakingapp.R;

import java.util.concurrent.ExecutionException;

public class IngredientActivity extends AppCompatActivity {
    Integer recipe_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

         recipe_id = this.getIntent().getIntExtra("recipeID", 0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout_ingredient, new RecipeIngredientFragment())
                    .commit();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
