package com.example.kunda.aqiapp.ui.viewModel;

import com.example.kunda.aqiapp.data.AirQualityRepository;
import com.example.kunda.aqiapp.data.database.LocationData;
import com.example.kunda.aqiapp.data.network.AirQualityResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Kundan on 01-10-2018.
 */
public class MainViewModel extends ViewModel {


    private AirQualityRepository repository;

    public MainViewModel(AirQualityRepository appRepository) {
        this.repository = appRepository;
    }

    private String latitude;
    private String longitude;
    private LiveData<AirQualityResponse.RootObject> airQualityResponse;
    private int savedLocationFragmentRVPosition = 0;

    public LocationData getHomeLocationData(){
        return repository.getHomeLocationData();
    }

    public long getHomeLocationDataID() { return repository.getHomeLocationDataID();}

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public LiveData<AirQualityResponse.RootObject> getAirQualityResponse(String latitude, String longitude) {
        airQualityResponse = repository.getAirQuality(latitude,longitude);
        return airQualityResponse;
    }

    public LiveData<List<LocationData>> getSavedLocationDataList(){
        return repository.loadSavedLocationData();
    }

    public void saveNewLocationData(LocationData locationData){
        repository.saveLocationData(locationData);
    }

    public void saveHomeLocationData(LocationData locationData){
        repository.saveHomeLocationData(locationData);
    }

    public int getSavedLocationFragmentRVPosition() {
        return savedLocationFragmentRVPosition;
    }

    public void setSavedLocationFragmentRVPosition(int position) {
        this.savedLocationFragmentRVPosition = position;
    }
}
