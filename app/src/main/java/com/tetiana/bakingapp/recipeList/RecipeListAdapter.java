package com.tetiana.bakingapp.recipeList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.gson.Gson;
import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.model.Recipe;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListHolder> {

    private ArrayList<Recipe> recipes;
    final private ListItemClickListener mOnClickListener;
    private Context context;

    private int clickedPosition;

    public RecipeListAdapter(Context context, ArrayList<Recipe> movieList, ListItemClickListener listener)
    {
        this.context = context;
        this.recipes = movieList;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public void onBindViewHolder(RecipeListHolder holder, int position) {
        String title = recipes.get(position).getName();
        String servings = recipes.get(position).getServings();

        holder.recipeTitle.setText(title);
        holder.recipe_servings.setText(servings);
    }

    @Override
    public RecipeListHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_list_item, viewGroup, false);
        return new RecipeListHolder(view);
    }
    @Override
    public int getItemCount() {
        return (recipes == null) ? 0 : recipes.size();
    }

    public class RecipeListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeTitle;
        TextView recipe_servings;
        RecipeListHolder(View itemView)
        {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.servings_label);
            recipe_servings = itemView.findViewById(R.id.recipe_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    public Integer getId() {
        return recipes.get(clickedPosition).getId();
    }

    public Integer getClickedPosition() {
        return clickedPosition;
    }
}
