package com.example.kunda.aqiapp.ui.viewModel;

import com.example.kunda.aqiapp.data.AirQualityRepository;
import com.example.kunda.aqiapp.data.database.LocationData;
import com.example.kunda.aqiapp.data.network.AirQualityResponse;
import com.example.kunda.aqiapp.data.network.IndicesResponse;
import com.example.kunda.aqiapp.data.network.CountryInfoResponse;

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
    private LiveData<CountryInfoResponse.RootObject> locationInfoResponse;
    private LiveData<IndicesResponse.RootObject> indicesInfoResponse;

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

    public LiveData<CountryInfoResponse.RootObject> getLocationInfoResponse(String latitude, String longitude) {
        locationInfoResponse = repository.getCountryData(latitude,longitude);
        return locationInfoResponse;
    }

    public LiveData<IndicesResponse.RootObject> getIndicesInfoResponse(String latitude, String longitude, String indicesType) {
        indicesInfoResponse = repository.getIndicesInfo(latitude,longitude,indicesType);
        return indicesInfoResponse;
    }

    public LiveData<List<LocationData>> getSavedLocationDataList(){
        return repository.loadSavedLocationData();
    }

    public void saveNewLocationData(LocationData locationData){
        repository.saveLocationData(locationData);
    }

}
