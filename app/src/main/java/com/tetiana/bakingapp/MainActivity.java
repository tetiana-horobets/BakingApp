package com.tetiana.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tetiana.bakingapp.model.Recipe;
import com.tetiana.bakingapp.recipeIngredient.IngredientActivity;
import com.tetiana.bakingapp.recipeList.RecipeListAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.ListItemClickListener {
    ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private RecipeListAdapter movieAdapter;
    RecyclerView mRecyclerView;
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_list);
        assignAppWidgetId();
        try {
            ReadData readData = new ReadData(this);
            recipes = readData.getRecipe(recipes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecyclerView = findViewById(R.id.rv_show_recipes_list);
        movieAdapter = new RecipeListAdapter(this, recipes, this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,  new NumberOfColumns().numberOfColumns(this)));
        mRecyclerView.setAdapter(movieAdapter);
    }

    private void assignAppWidgetId() {
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Integer id = movieAdapter.getId();
        Intent intent = new Intent(this, IngredientActivity.class);
        intent.putExtra("recipeID", id);
        startActivity(intent);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("recipeID", id);
        editor.apply();
    }
}
