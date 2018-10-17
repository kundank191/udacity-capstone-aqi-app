package com.example.kunda.aqiapp.data.sync;

import android.app.IntentService;
import android.content.Intent;

import com.example.kunda.aqiapp.data.AirQualityRepository;
import com.example.kunda.aqiapp.utils.InjectorUtils;

import androidx.annotation.Nullable;

/**
 * Created by Kundan on 17-10-2018.
 */
public class SyncLocationDataService extends IntentService {

    public SyncLocationDataService() {
        super("SyncLocationDataService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        AirQualityRepository airQualityRepository = InjectorUtils.getAirQualityRepository(this);
        airQualityRepository.syncAllLocationsData();
    }
}
