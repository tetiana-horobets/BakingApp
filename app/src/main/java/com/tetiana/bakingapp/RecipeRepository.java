package com.tetiana.bakingapp;

import android.util.JsonReader;
import android.util.JsonToken;

import com.tetiana.bakingapp.model.Ingredient;
import com.tetiana.bakingapp.model.Recipe;
import com.tetiana.bakingapp.model.Step;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

class RecipeRepository {

    private final InputStream inputStream;
    RecipeRepository(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    Recipe findRecipeById(int recipeId) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        ArrayList<Recipe> recipeArrayList = read(reader);
        for(Recipe recipe: recipeArrayList) {
            if (recipeId == recipe.getId()) {
                return recipe;
            }
        }
        return null;
    }

    ArrayList<Step> getStepList(JsonReader reader) throws IOException {
        ArrayList<Step> steps = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String next = reader.nextName();
                if (next.equals("steps") && reader.peek() != JsonToken.NULL) {
                    steps = readStepArray(reader);
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        }
        reader.endArray();
        return steps;
    }

     ArrayList<Recipe> read(JsonReader reader) throws IOException {
        ArrayList<Recipe> recipes = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            recipes.add(readRecipe(reader));
        }
        reader.endArray();
        return recipes;
    }

    private Recipe readRecipe(JsonReader reader) throws IOException {
        Integer id = null;
        String name = null;
        ArrayList<Ingredient> ingredients = null;
        ArrayList<Step> steps = null;
        String servings = null;
        String image = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String next = reader.nextName();
            if (next.equals("id")) {
                id = reader.nextInt();
            } else if (next.equals("name")) {
                name = reader.nextString();
            } else if (next.equals("ingredients") && reader.peek() != JsonToken.NULL) {
                ingredients = readIngredientArray(reader);
            } else if (next.equals("steps") && reader.peek() != JsonToken.NULL) {
                steps = readStepArray(reader);
            } else if (next.equals("servings")) {
                servings = reader.nextString();
            } else if (next.equals("image")) {
                image = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new Recipe(id, name, ingredients, steps, servings, image);
    }

    private ArrayList<Step> readStepArray(JsonReader reader) throws IOException {
        ArrayList<Step> recipeSteps = new ArrayList<>();
        String id = null;
        String shortDescription = null;
        String description = null;
        String videoURL = null;
        String thumbnailURL =null;

        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String next = reader.nextName();
                switch (next) {
                    case "id":
                        id = reader.nextString();
                        break;
                    case "shortDescription":
                        shortDescription = reader.nextString();
                        break;
                    case "description":
                        description = reader.nextString();
                        break;
                    case "videoURL":
                        videoURL = reader.nextString();
                        break;
                    case "thumbnailURL":
                        thumbnailURL = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            recipeSteps.add(new Step(id, shortDescription, description, videoURL, thumbnailURL));
        }
        reader.endArray();
        return recipeSteps;
    }

    private ArrayList<Ingredient> readIngredientArray(JsonReader reader) throws IOException {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        String quantity = null;
        String measure = null;
        String ingredient = null;

        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String next = reader.nextName();
                switch (next) {
                    case "quantity":
                        quantity = reader.nextString();
                        break;
                    case "measure":
                        measure = reader.nextString();
                        break;
                    case "ingredient":
                        ingredient = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            ingredients.add(new Ingredient(quantity, measure, ingredient));
        }
        reader.endArray();
        return ingredients;
    }
}
