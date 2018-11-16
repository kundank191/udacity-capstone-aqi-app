package com.example.kunda.aqiapp.ui.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.kunda.aqiapp.data.AirQualityRepository;
import com.example.kunda.aqiapp.data.database.LocationData;
import com.example.kunda.aqiapp.utils.InjectorUtils;

/**
 * Created by Kundan on 16-11-2018.
 */
public class HomeLocationDataWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        AirQualityRepository repository = InjectorUtils.provideAirQualityRepository(this);
        LocationData homeLocationData = repository.getHomeLocationData();

        return new com.example.kunda.aqiapp.ui.widget.RemoteViewsFactory(intent,homeLocationData,getPackageName());
    }
}
