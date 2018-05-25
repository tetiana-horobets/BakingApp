package com.tetiana.bakingapp.recipeIngredient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tetiana.bakingapp.JSONParse;
import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.model.Ingredient;
import com.tetiana.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientFragment extends Fragment {
    private IngredientAdapter ingredientAdapter;
    List<Recipe> recipes = new JSONParse().execute().get();
    List<Ingredient> ingredients =new ArrayList<>();

    public RecipeIngredientFragment() throws ExecutionException, InterruptedException {
    }

    @BindView(R.id.rv_ingredient_list)
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Integer recipe_id = getActivity().getIntent().getIntExtra("recipeID", 0);
        ingredients = recipes.get(recipe_id).getIngredients();
        ingredientAdapter = new IngredientAdapter(ingredients, getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),  1));
        mRecyclerView.setAdapter(ingredientAdapter);
    }
}
