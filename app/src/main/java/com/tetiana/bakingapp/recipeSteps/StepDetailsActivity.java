package com.tetiana.bakingapp.recipeSteps;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tetiana.bakingapp.R;

import java.util.concurrent.ExecutionException;

public class StepDetailsActivity extends AppCompatActivity {
    StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        if (savedInstanceState == null){
            try {
                stepDetailFragment = new StepDetailFragment();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_step, stepDetailFragment)
                    .commit();
        }else {
            stepDetailFragment  = (StepDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "stepDetailFragment");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "stepDetailFragment", stepDetailFragment);
    }
}
