package com.tetiana.bakingapp.recipeList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.tetiana.bakingapp.NumberOfColumns;
import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.ReadData;
import com.tetiana.bakingapp.model.Recipe;
import com.tetiana.bakingapp.recipeIngredient.IngredientActivity;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeList extends Fragment implements RecipeListAdapter.ListItemClickListener{

    ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private RecipeListAdapter movieAdapter;
    RecyclerView mRecyclerView;

    public static RecipeList newInstance() {
        return new RecipeList();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ReadData readData = new ReadData(getActivity().getApplicationContext());
            recipes = readData.getRecipe(recipes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recipes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.rv_show_recipes_list);
        movieAdapter = new RecipeListAdapter(getActivity().getApplicationContext(), recipes, this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),  new NumberOfColumns().numberOfColumns(getActivity())));
        mRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Integer id = movieAdapter.getId();
        Intent intent = new Intent(getActivity(), IngredientActivity.class);
        intent.putExtra("recipeID", id);
        startActivity(intent);
    }


}
