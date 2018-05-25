package com.tetiana.bakingapp.model;

import java.util.List;

public class Recipe {
    private Integer id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private String servings;
    private String image;

    public Recipe(Integer id, String name, List<Ingredient> ingredients, List<Step> steps, String servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public List<Step> getSteps() {
        return steps;
    }
}
