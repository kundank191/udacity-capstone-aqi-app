package com.example.kunda.aqiapp.utils;

import android.content.Context;

import com.example.kunda.aqiapp.data.AirQualityRepository;

/**
 * Created by Kundan on 27-09-2018.
 */
public class InjectorUtils {

    public AirQualityRepository getAirQualityRepository(Context context){
        return AirQualityRepository.getInstance(context);
    }
}
