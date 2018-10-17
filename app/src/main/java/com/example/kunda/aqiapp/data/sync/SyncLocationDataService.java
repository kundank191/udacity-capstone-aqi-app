package com.example.kunda.aqiapp.data.sync;

import android.app.IntentService;
import android.content.Intent;

import com.example.kunda.aqiapp.data.AirQualityRepository;
import com.example.kunda.aqiapp.utils.InjectorUtils;

import java.util.Objects;

import androidx.annotation.Nullable;

import static com.example.kunda.aqiapp.utils.Constants.ACTION_REFRESH_ALL_LOCATION_DATA;
import static com.example.kunda.aqiapp.utils.Constants.ACTION_REFRESH_COUNTRY_DATA;

/**
 * Created by Kundan on 17-10-2018.
 * This service syncs data between local repository and server
 */
public class SyncLocationDataService extends IntentService {

    public SyncLocationDataService() {
        super("SyncLocationDataService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Get the repository reference
        AirQualityRepository airQualityRepository = InjectorUtils.getAirQualityRepository(this);

        switch (Objects.requireNonNull(Objects.requireNonNull(intent).getAction())) {
            case ACTION_REFRESH_COUNTRY_DATA:
                break;
            case ACTION_REFRESH_ALL_LOCATION_DATA:
                airQualityRepository.syncAllLocationsData();
                break;
        }
    }
}
