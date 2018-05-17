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
import com.tetiana.bakingapp.ReadData;
import com.tetiana.bakingapp.model.Step;
import com.tetiana.bakingapp.recipeIngredient.IngredientActivity;
import com.tetiana.bakingapp.recipeSteps.StepListFragment.StepChangeHandler;

import java.io.IOException;
import java.util.ArrayList;

public class StepListActivity extends AppCompatActivity implements StepAdapter.ListItemClickListener{

    private boolean mTwoPane;
    ArrayList<Step> steps = new ArrayList<>();
    private StepAdapter stepAdapter;
    int id;

    private StepListFragment.StepChangeHandler stepChangeHandler = new StepListFragment.StepChangeHandler() {
        @Override
        public void onStepChanged(int stepId) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        try {
            ReadData readData = new ReadData(getApplicationContext());
            steps = readData.getStepList(steps);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stepAdapter = new StepAdapter(steps, getApplicationContext(), this);
        RecyclerView mRecyclerView = findViewById(R.id.rv_step_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerView.setAdapter(stepAdapter);

        if (findViewById(R.id.lend_layout) != null){
            mTwoPane = true;
            final StepDetailFragment stepDetailFragment = new StepDetailFragment();

            setStepChangeHandler(new StepListFragment.StepChangeHandler() {
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

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout_step, stepDetailFragment)
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

    public void setStepChangeHandler(StepListFragment.StepChangeHandler stepChangeHandler) {
        this.stepChangeHandler = stepChangeHandler;
    }
}
