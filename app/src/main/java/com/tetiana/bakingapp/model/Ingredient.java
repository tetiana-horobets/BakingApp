package com.tetiana.bakingapp.model;

public class Ingredient {
   private String quantity;

   private String measure;

    private String ingredientName;

    public Ingredient(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientName = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }
}
