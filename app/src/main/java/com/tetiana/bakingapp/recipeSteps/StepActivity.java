package com.tetiana.bakingapp.recipeSteps;

import android.content.Intent;
import android.os.Parcelable;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tetiana.bakingapp.JSONParse;
import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.model.Recipe;
import com.tetiana.bakingapp.model.Step;
import com.tetiana.bakingapp.recipeIngredient.IngredientActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity implements StepAdapter.ListItemClickListener{

    private boolean mTwoPane;
    List<Recipe> recipes = new JSONParse().execute().get();
    List<Step> recipe = new ArrayList<>();
    private StepAdapter stepAdapter;
    private StepDetailFragment finalStepDetailFragment;
    RecyclerView.LayoutManager layoutManager;
    Parcelable mListState;
    private static final String LIST_STATE_KEY = "list_state_key";
    private static final String MY_FRAGMENT_TAG = "my_fragment_tag";
    private static final String FRAGMENT = "fragment";
    private  Integer recipe_id;

    @BindView(R.id.ingredients)
    Button ingredient;

    @BindView(R.id.rv_step_list)
    RecyclerView mRecyclerView;

    private StepActivity.StepChangeHandler stepChangeHandler = new StepActivity.StepChangeHandler() {
        @Override
        public void onStepChanged(int stepId) {}
    };

    public StepActivity() throws ExecutionException, InterruptedException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_step_list);
        ButterKnife.bind(this);
        recipe_id = getIntent().getIntExtra("recipeID", 0);

        ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StepActivity.this, IngredientActivity.class);
                intent.putExtra("recipeID", recipe_id);
                StepActivity.this.startActivity(intent);
            }
        });

        recipe = recipes.get(recipe_id).getSteps();
        stepAdapter = new StepAdapter(recipe, getApplicationContext(), this);
        layoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(stepAdapter);
//        ImageView recipe_image = findViewById(R.id.recipe_imege_step);
        TextView recipe_title = findViewById(R.id.recipe_title_steps);
        String title = recipes.get(recipe_id).getName();
        recipe_title.setText(title);
//        String image = recipes.get(recipe_id).getImage();
//        if (!image.equals("")){
//            Picasso.with(getApplicationContext())
//                    .load(image)
//                    .into(recipe_image);
//        }else {
//            recipe_image.setImageResource(R.mipmap.ic_recipe);
//        }

        if (findViewById(R.id.lend_layout) != null){
            mTwoPane = true;
            try {
                finalStepDetailFragment = new StepDetailFragment();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
                if (savedInstanceState != null){
                    finalStepDetailFragment  = (StepDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout_step, finalStepDetailFragment, MY_FRAGMENT_TAG)
                            .commit();
                }else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout_step, finalStepDetailFragment, MY_FRAGMENT_TAG)
                            .commit();
                }
        }else {
            mTwoPane = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (mTwoPane){
            stepChangeHandler.onStepChanged(clickedItemIndex);
        }else {
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra("stepID", clickedItemIndex);
            intent.putExtra("recipeID", recipe_id);
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
        mListState = layoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);
        if (findViewById(R.id.lend_layout) != null){
            getSupportFragmentManager().putFragment(outState, FRAGMENT, finalStepDetailFragment);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null)
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mListState != null) {
            layoutManager.onRestoreInstanceState(mListState);
        }
    }
}
