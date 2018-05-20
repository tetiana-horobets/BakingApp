package com.tetiana.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

@RequiresApi(api = Build.VERSION_CODES.N)
public class WidgetProvider extends AppWidgetProvider {

    public static final String EXTRA_ITEM =
            "com.company.android.CollectionWidgetProvider.EXTRA_ITEM";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        for (int widgetId : appWidgetIds) {

            Intent intent = new Intent(context, WidgetService.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provide);
            widgetView.setRemoteAdapter(R.id.lvList, intent);
            widgetView.setEmptyView(R.id.lvList, R.id.tvUpdate);

            appWidgetManager.updateAppWidget(widgetId, widgetView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
