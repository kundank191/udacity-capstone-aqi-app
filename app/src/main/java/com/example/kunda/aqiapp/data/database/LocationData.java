package com.example.kunda.aqiapp.data.database;

import com.example.kunda.aqiapp.data.network.AirQualityResponse;

/**
 * Created by Kundan on 09-10-2018.
 */
public class LocationData {

    private String locationName;
    private AirQualityResponse.Response locationAirQualityData;

    public LocationData(String locationName, AirQualityResponse.Response locationData) {
        this.locationName = locationName;
        this.locationAirQualityData = locationData;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public AirQualityResponse.Response getLocationAirQualityData() {
        return locationAirQualityData;
    }

    public void setLocationAirQualityData(AirQualityResponse.Response locationAirQualityData) {
        this.locationAirQualityData = locationAirQualityData;
    }
}
