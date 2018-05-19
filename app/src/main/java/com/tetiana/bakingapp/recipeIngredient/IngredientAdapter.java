package com.tetiana.bakingapp.recipeIngredient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.model.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder>{

    private ArrayList<Ingredient> ingredients;
    private Context context;

    public IngredientAdapter(ArrayList<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        String ingredientName = ingredients.get(position).getIngredientName();
        String measure = ingredients.get(position).getMeasure();
        String quantity = ingredients.get(position).getQuantity();

        holder.recipeIngredientName.setText(ingredientName);
        holder.recipeMeasure.setText(measure);
        holder.recipeQuantity.setText(quantity);
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, viewGroup, false);
        return new IngredientHolder(view);
    }

    @Override
    public int getItemCount() {
        return (ingredients == null) ? 0 : ingredients.size();
    }

    class IngredientHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient)
        TextView recipeIngredientName;
        @BindView(R.id.quantity)
        TextView recipeMeasure;
        @BindView(R.id.measure)
        TextView recipeQuantity;

        IngredientHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
