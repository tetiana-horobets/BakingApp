package com.tetiana.bakingapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.JsonReader;

import com.tetiana.bakingapp.model.Recipe;
import com.tetiana.bakingapp.model.Step;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class DataReader {
    private final RecipeRepository userRepository;
    private final JsonReader reader;

    public DataReader(Context myContext) throws IOException {
        AssetManager assetManager = myContext.getAssets();
        InputStream inputStream = assetManager.open("baking.json");
        userRepository = new RecipeRepository(inputStream);
        reader = new JsonReader(new InputStreamReader(inputStream));
    }

    public List<Step> getStepList() throws IOException {
        return userRepository.getStepList(reader);
    }

    List<Recipe> getRecipe() throws IOException {
        return userRepository.read(reader);
    }

    public Recipe getRecipe(Integer id) throws IOException {
        return userRepository.findRecipeById(id);
    }
}
