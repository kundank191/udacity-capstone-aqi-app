package com.example.kunda.aqiapp.data.sync;

import android.content.Intent;

import com.firebase.jobdispatcher.JobService;

import static com.example.kunda.aqiapp.utils.Constants.ACTION_REFRESH_ALL_LOCATION_DATA;
import static com.example.kunda.aqiapp.utils.Constants.ACTION_REFRESH_COUNTRY_DATA;

/**
 * Created by Kundan on 17-10-2018.
 */
public class SyncDataFirebaseService extends JobService {
    @Override
    public boolean onStartJob(com.firebase.jobdispatcher.JobParameters jobParameters) {
        //Start Service to Sync all location data
        Intent intentAllData = new Intent(this,SyncLocationDataService.class);
        intentAllData.setAction(ACTION_REFRESH_ALL_LOCATION_DATA);
        startService(intentAllData);
        // Start Service to sync country data
        Intent intentCountryData = new Intent(this,SyncLocationDataService.class);
        intentCountryData.setAction(ACTION_REFRESH_COUNTRY_DATA);
        startService(intentCountryData);
        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters jobParameters) {
        return true;
    }

}
