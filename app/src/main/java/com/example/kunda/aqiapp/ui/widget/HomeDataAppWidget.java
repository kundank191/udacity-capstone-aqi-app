package com.example.kunda.aqiapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.view.View;
import android.widget.RemoteViews;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.database.LocationData;
import com.example.kunda.aqiapp.data.network.AirQualityResponse;
import com.example.kunda.aqiapp.utils.InjectorUtils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.kunda.aqiapp.utils.Constants.BASE_INDEX;

/**
 * Implementation of App Widget functionality.
        */
public class HomeDataAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, LocationData homeLocationData) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        
        if (homeLocationData != null) {

            AirQualityResponse.Place place = homeLocationData.getLocationAirQualityData().getPlace();
            AirQualityResponse.Period airQualityInfo = homeLocationData.getLocationAirQualityData().getPeriods().get(BASE_INDEX);

            String dominantPollutant = airQualityInfo.getDominant();
            String aqiIndex = String.valueOf(airQualityInfo.getAqi());
            String airQuality = airQualityInfo.getCategory();
            String placeName = place.getName();
            String country = place.getCountry();

            views.setTextViewText(R.id.appwidget_tv_place_name, String.format("%s (%s, %s)", homeLocationData.getLocationName(), placeName, country));
            views.setTextViewText(R.id.appwidget_tv_air_quality,String.format("%s : %s",context.getString(R.string.widget_text_air_quality),airQuality));
            views.setTextViewText(R.id.appwidget_tv_dominant_pollutant,String.format(context.getString(R.string.dominant_pollutant), dominantPollutant));
            views.setTextViewText(R.id.appwidget_tv_aqi_index,aqiIndex);
            // Hide the empty state text view
            views.setViewVisibility(R.id.widget_tv_empty_view,GONE);

        } else {
            // If there is no saved home location data
            views.setViewVisibility(R.id.widget_rv_data, View.GONE);
            views.setViewVisibility(R.id.widget_tv_empty_view,VISIBLE);
        }
        
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // Get the data from the repository
        InjectorUtils.provideAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                LocationData homeLocationData = InjectorUtils.provideAirQualityRepository(context).getHomeLocationData();
                for (int appWidgetId : appWidgetIds) {
                    // There may be multiple widgets active, so update all of them
                    updateAppWidget(context, appWidgetManager, appWidgetId,homeLocationData);
                }
            }
        });
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

