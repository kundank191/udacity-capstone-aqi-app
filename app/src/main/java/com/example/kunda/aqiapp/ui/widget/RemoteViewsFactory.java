package com.example.kunda.aqiapp.ui.widget;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.kunda.aqiapp.data.database.LocationData;

/**
 * Created by Kundan on 16-11-2018.
 */
public class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Intent intent;
    private final LocationData homeLocationData;
    private final String packageName;

    public RemoteViewsFactory(Intent intent, LocationData homeLocationData, String packageName) {

        this.intent = intent;
        this.homeLocationData = homeLocationData;
        this.packageName = packageName;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
