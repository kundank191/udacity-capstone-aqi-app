package com.example.kunda.aqiapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.kunda.aqiapp.R;

/**
 * Created by Kundan on 16-11-2018.
 */
public class HomeLocationDataWidget extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        Intent intent = getIngredientServiceIntent(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_data_widget);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * @param context of the application
     * @return and intent which will start IngredientWidgetService
     */
    public static Intent getIngredientServiceIntent(Context context) {
        return new Intent(context, HomeLocationDataWidgetService.class);
    }
}
