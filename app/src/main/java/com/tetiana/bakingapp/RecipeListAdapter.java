package com.tetiana.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.tetiana.bakingapp.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListHolder> {

    private ArrayList<Recipe> recipes;
    final private ListItemClickListener mOnClickListener;
    private Context context;

    private int clickedPosition;

    RecipeListAdapter(Context context, ArrayList<Recipe> movieList, ListItemClickListener listener)
    {
        this.context = context;
        this.recipes = movieList;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListHolder holder, int position) {
        String title = recipes.get(position).getName();
        String servings = recipes.get(position).getServings();
        String image = recipes.get(position).getImage();

        holder.recipeTitle.setText(title);
        holder.recipe_servings.setText(servings);
        if (!image.equals("")){
            Picasso.with(context.getApplicationContext())
                    .load(image)
                    .into( holder.recipeImage);
        }else {
            holder.recipeImage.setImageResource(R.mipmap.ic_recipe);
        }

    }

    @NonNull
    @Override
    public RecipeListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
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
        @BindView(R.id.servings_label)
        TextView recipeTitle;
        @BindView(R.id.recipe_title)
        TextView recipe_servings;
        @BindView(R.id.recipe_image)
        ImageView recipeImage;
        RecipeListHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
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

    Integer getClickedPosition() {
        return clickedPosition;
    }
}
