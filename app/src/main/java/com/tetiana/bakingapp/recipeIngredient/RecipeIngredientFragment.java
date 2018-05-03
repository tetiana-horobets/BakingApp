package com.tetiana.bakingapp.recipeIngredient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.ReadData;
import com.tetiana.bakingapp.model.Ingredient;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeIngredientFragment extends Fragment {
    private IngredientAdapter ingredientAdapter;
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    public static RecipeIngredientFragment newInstance() {
        return new RecipeIngredientFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Integer recipe_id = getActivity().getIntent().getIntExtra("recipeID", 0);
        try {
            ReadData readData = new ReadData(getActivity().getApplicationContext());
            ingredients = readData.getRecipe(recipe_id).getIngredients();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ingredientAdapter = new IngredientAdapter(ingredients, getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = view.findViewById(R.id.rv_ingredient_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),  1));
        mRecyclerView.setAdapter(ingredientAdapter);
    }
}
