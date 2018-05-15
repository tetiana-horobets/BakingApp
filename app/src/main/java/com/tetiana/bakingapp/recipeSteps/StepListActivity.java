package com.tetiana.bakingapp.recipeSteps;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.recipeIngredient.IngredientActivity;

public class StepListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        final Integer recipe_id = getIntent().getIntExtra("recipeID", 0);
        Button staryIngredientActivity = (Button) findViewById(R.id.imB_ingredients);
        staryIngredientActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StepListActivity.this, IngredientActivity.class);
                intent.putExtra("recipeID", recipe_id);
                StepListActivity.this.startActivity(intent);
            }
        });

        FragmentManager fragmentManager2 = getSupportFragmentManager();
        fragmentManager2.beginTransaction()
                .add(R.id.frame_layout2, StepListFragment.newInstance())
                .commit();
    }


}
