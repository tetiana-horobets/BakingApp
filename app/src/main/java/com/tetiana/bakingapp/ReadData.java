package com.tetiana.bakingapp;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.JsonReader;


import com.tetiana.bakingapp.model.Recipe;
import com.tetiana.bakingapp.model.Step;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadData {
    private RecipeRepository userRepository;
    private JsonReader reader;

    public ReadData(Context myContext) throws IOException {
        AssetManager assetManager = myContext.getAssets();
        InputStream inputStream = assetManager.open("baking.json");
        userRepository = new RecipeRepository(inputStream);
        reader = new JsonReader(new InputStreamReader(inputStream));

    }

   public ArrayList<Step> getStepList(ArrayList<Step> steps){
        try {
            steps = userRepository.getStepList(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return steps;
    }


  public ArrayList<Recipe> getRecipe(ArrayList<Recipe> recipes){
        try {
            recipes = userRepository.read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }

  public Recipe getRecipe(Integer id) throws IOException {
        return userRepository.findRecipeById(id);
    }
}
