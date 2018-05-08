package com.tetiana.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

import com.tetiana.bakingapp.model.Ingredient;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MyProvider extends AppWidgetProvider {

    public static final String ACTION_VIEW_DETAILS =
            "com.company.android.ACTION_VIEW_DETAILS";
    public static final String EXTRA_ITEM =
            "com.company.android.CollectionWidgetProvider.EXTRA_ITEM";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        for(int i = 0; i < appWidgetIds.length; i++) {

            int widgetId = appWidgetIds[i];

            Intent intent = new Intent(context, MyService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provide);
            widgetView.setRemoteAdapter(R.id.lvList, intent);
            widgetView.setEmptyView(R.id.lvList, R.id.tvUpdate);

            Intent detailIntent = new Intent(ACTION_VIEW_DETAILS);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widgetView.setPendingIntentTemplate(R.id.lvList, pIntent);

            appWidgetManager.updateAppWidget(widgetId, widgetView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_VIEW_DETAILS)) {
            Ingredient ingredient = (Ingredient) intent.getSerializableExtra(EXTRA_ITEM);
            if(ingredient != null) {
                // Handle the click here.
                // Maybe start a details activity?
                // Maybe consider using an Activity PendingIntent instead of a Broadcast?
            }
        }
        Bundle getPrevData = intent.getExtras();
        Integer data = getPrevData.getInt("recipeID");
        super.onReceive(context, intent);
    }
}
