package com.tetiana.bakingapp.recipeSteps;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.DataReader;
import com.tetiana.bakingapp.model.Step;
import com.tetiana.bakingapp.recipeIngredient.IngredientActivity;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity implements StepAdapter.ListItemClickListener{

    private boolean mTwoPane;
    ArrayList<Step> steps = new ArrayList<>();
    private StepAdapter stepAdapter;
    private StepDetailFragment finalStepDetailFragment;

    @BindView(R.id.ingredients)
    Button ingredient;

    @BindView(R.id.rv_step_list)
    RecyclerView mRecyclerView;

    private StepActivity.StepChangeHandler stepChangeHandler = new StepActivity.StepChangeHandler() {
        @Override
        public void onStepChanged(int stepId) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        ButterKnife.bind(this);
        final Integer recipe_id = getIntent().getIntExtra("recipeID", 0);

        ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StepActivity.this, IngredientActivity.class);
                intent.putExtra("recipeID", recipe_id);
                StepActivity.this.startActivity(intent);
            }
        });

        try {
            DataReader dataReader = new DataReader(getApplicationContext());
            steps = dataReader.getStepList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stepAdapter = new StepAdapter(steps, getApplicationContext(), this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerView.setAdapter(stepAdapter);

        if (findViewById(R.id.lend_layout) != null){
            mTwoPane = true;
            finalStepDetailFragment = new StepDetailFragment();
            if (savedInstanceState == null){
                setStepChangeHandler(new StepActivity.StepChangeHandler() {
                    @Override
                    public void onStepChanged(int stepId) {
                        finalStepDetailFragment.setStep_id(stepId);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .detach(finalStepDetailFragment)
                                .attach(finalStepDetailFragment)
                                .commit();
                    }
                });
            }else {
                finalStepDetailFragment = (StepDetailFragment) getSupportFragmentManager()
                        .findFragmentByTag("MY_FRAGMENT_TAG");

            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout_step, finalStepDetailFragment, "MY_FRAGMENT_TAG")
                    .commit();
        }else {
            mTwoPane = false;
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        int id = stepAdapter.getId();
        if (mTwoPane){
            stepChangeHandler.onStepChanged(id);
        }else {
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra("stepID", id);
            startActivity(intent);
        }
    }

    public interface StepChangeHandler {
        void onStepChanged(int stepId);
    }

    public void setStepChangeHandler(StepActivity.StepChangeHandler stepChangeHandler) {
        this.stepChangeHandler = stepChangeHandler;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
