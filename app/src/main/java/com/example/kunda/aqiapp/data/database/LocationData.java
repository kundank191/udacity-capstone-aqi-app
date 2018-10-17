package com.example.kunda.aqiapp.data.database;

import com.example.kunda.aqiapp.data.network.AirQualityResponse;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by Kundan on 09-10-2018.
 * Data object which will be stored in the database
 */
@Entity(tableName = AppDatabase.DATABASE_NAME)
public class LocationData {

    @PrimaryKey(autoGenerate = true)
    private int locationID;
    private String locationName;
    @Embedded
    private AirQualityResponse.Response locationAirQualityData;

    public LocationData(int locationID, String locationName, AirQualityResponse.Response locationAirQualityData) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.locationAirQualityData = locationAirQualityData;
    }

    @Ignore
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

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
}
