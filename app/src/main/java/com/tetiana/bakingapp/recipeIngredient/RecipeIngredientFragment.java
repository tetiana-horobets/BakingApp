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


import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.DataReader;
import com.tetiana.bakingapp.model.Ingredient;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientFragment extends Fragment {
    private IngredientAdapter ingredientAdapter;
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    public static RecipeIngredientFragment newInstance() {
        return new RecipeIngredientFragment();
    }
    @BindView(R.id.rv_ingredient_list)
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Integer recipe_id = getActivity().getIntent().getIntExtra("recipeID", 0);
        try {
            DataReader dataReader = new DataReader(getActivity().getApplicationContext());
            ingredients = dataReader.getRecipe(recipe_id).getIngredients();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
