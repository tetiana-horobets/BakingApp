package com.tetiana.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.tetiana.bakingapp.model.Ingredient;
import com.tetiana.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final int ID_CONSTANT = 0x0101010;

    private List<Ingredient> ingredients = new ArrayList<>();
    private Context mContext;

    WidgetFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @SuppressLint("NewApi")
    @Override
    public RemoteViews getViewAt(int position) {

        Ingredient article = ingredients.get(position);

        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_list_item);

        itemView.setTextViewText(R.id.measure, article.getMeasure());
        itemView.setTextViewText(R.id.quantity, article.getQuantity());
        itemView.setTextViewText(R.id.ingredient, article.getIngredientName());

        Intent intent = new Intent();
        intent.putExtra(WidgetProvider.EXTRA_ITEM, article);
        itemView.setOnClickFillInIntent(R.id.lvList, intent);

        return itemView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SqlDateTypeAdapter sqlAdapter = new SqlDateTypeAdapter();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(java.sql.Date.class, sqlAdapter)
                .setDateFormat("yyyy-MM-dd")
                .create();
        String json = preferences.getString("ingredients", "");

        if (!json.isEmpty()) {
            Recipe recipe = gson.fromJson(json, new TypeToken<Recipe>() {
            }.getType());
            ingredients = recipe.getIngredients();
        }
    }

    @Override
    public void onDestroy() {
        ingredients.clear();
    }
}
