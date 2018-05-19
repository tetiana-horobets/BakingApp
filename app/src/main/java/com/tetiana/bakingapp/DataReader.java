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
import java.util.List;

public class DataReader {
    private RecipeRepository userRepository;
    private JsonReader reader;

    public DataReader(Context myContext) throws IOException {
        AssetManager assetManager = myContext.getAssets();
        InputStream inputStream = assetManager.open("baking.json");
        userRepository = new RecipeRepository(inputStream);
        reader = new JsonReader(new InputStreamReader(inputStream));
    }

   public ArrayList<Step> getStepList() throws IOException {
       return userRepository.getStepList(reader);
    }


  public ArrayList<Recipe> getRecipe() throws IOException {
           return userRepository.read(reader);
    }

  public Recipe getRecipe(Integer id) throws IOException {
        return userRepository.findRecipeById(id);
    }
}
