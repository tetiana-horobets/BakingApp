package com.tetiana.bakingapp.recipeSteps;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.model.Step;
import com.tetiana.bakingapp.recipeIngredient.IngredientActivity;
import com.tetiana.bakingapp.recipeSteps.StepListFragment.StepChangeHandler;

import java.util.ArrayList;

public class StepListActivity extends AppCompatActivity{

    private boolean mTwoPane;
    ArrayList<Step> steps = new ArrayList<>();
    private StepAdapter stepAdapter;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        final Integer recipe_id = getIntent().getIntExtra("recipeID", 0);
        Button staryIngredientActivity = (Button) findViewById(R.id.imB_ingredients);

        StepListFragment stepListFragment = new StepListFragment();
        if (findViewById(R.id.lend_layout) != null){
            mTwoPane = true;

            staryIngredientActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(StepListActivity.this, IngredientActivity.class);
                    intent.putExtra("recipeID", recipe_id);
                    StepListActivity.this.startActivity(intent);
                }
            });


            //id = getIntent().getIntExtra("stepID", 0);
            final StepDetailFragment stepDetailFragment = new StepDetailFragment();

            stepListFragment.setStepChangeHandler(new StepChangeHandler() {
                @Override
                public void onStepChanged(int stepId) {
                    stepDetailFragment.setStep_id(stepId);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .detach(stepDetailFragment)
                            .attach(stepDetailFragment)
                            .commit();
                }
            });

            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction()
                    .add(R.id.frame_layout2, stepListFragment)
                    .commit();


            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout_step, stepDetailFragment)
                    .commit();
        }else {
            mTwoPane = false;

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
                    .add(R.id.frame_layout2, stepListFragment)
                    .commit();

            Intent intent = new Intent(this, StepDetailsActivity.class);
            startActivity(intent);
        }
    }
}
