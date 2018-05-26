package com.tetiana.bakingapp;

import android.os.AsyncTask;
import android.util.Log;

import com.tetiana.bakingapp.model.Ingredient;
import com.tetiana.bakingapp.model.Recipe;
import com.tetiana.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParse extends AsyncTask<Void, Void, List<Recipe>> {

    @Override
    protected List<Recipe> doInBackground(Void... params) {
        List<Recipe> recipes = new ArrayList<Recipe>();
        try {
            JSONObject jo;
            JSONArray ja2 = DataLoader.getJSONfromURL();

            for (int i = 0; i < ja2.length(); i++) {
                jo = ja2.getJSONObject(i);
                Integer id = Integer.parseInt(jo.getString("id"));
                String name = jo.getString("name");

                List<Ingredient> ingredients = new ArrayList<Ingredient>();
                JSONArray ingredientArray = jo.getJSONArray("ingredients");
                for (int j = 0; j < ingredientArray.length(); j++) {
                    JSONObject jsonObject = ingredientArray.getJSONObject(j);
                    String quantity = jsonObject.getString("quantity");
                    String measure = jsonObject.getString("measure");
                    String ingredientName = jsonObject.getString("ingredient");
                    Ingredient ingredient = new Ingredient(quantity, measure, ingredientName);
                    ingredients.add(ingredient);
                }

                List<Step> steps = new ArrayList<>();
                JSONArray stepArray = jo.getJSONArray("steps");
                for (int j = 0; j < stepArray.length(); j++) {
                    JSONObject stepObject = stepArray.getJSONObject(j);
                    String stepId = stepObject.getString("id");
                    String shortDescription = stepObject.getString("shortDescription");
                    String description = stepObject.getString("description");
                    String videoURL = stepObject.getString("videoURL");
                    String thumbnailURL = stepObject.getString("thumbnailURL");
                    Step step = new Step(stepId, shortDescription, description, videoURL, thumbnailURL);
                    steps.add(step);
                }
                String servings = jo.getString("servings");
                String image = jo.getString("image");
                Recipe recipe = new Recipe(id, name, ingredients, steps, servings, image);
                recipes.add(recipe);
            }

        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return recipes;

    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
        super.onPostExecute(recipes);
    }
}
