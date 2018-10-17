package com.example.kunda.aqiapp.data;

import android.content.Context;

import com.example.kunda.aqiapp.AppExecutors;
import com.example.kunda.aqiapp.BuildConfig;
import com.example.kunda.aqiapp.data.database.AppDatabase;
import com.example.kunda.aqiapp.data.database.LocationData;
import com.example.kunda.aqiapp.data.network.AerisApiService;
import com.example.kunda.aqiapp.data.network.AirQualityResponse;
import com.example.kunda.aqiapp.data.network.CountryInfoResponse;
import com.example.kunda.aqiapp.data.network.IndicesResponse;
import com.example.kunda.aqiapp.utils.Constants;
import com.example.kunda.aqiapp.utils.InjectorUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Kundan on 25-09-2018.
 * App repository
 */
public class AirQualityRepository {

    private static final Object LOCK = new Object();
    private static AirQualityRepository sInstance;
    private AppDatabase mDb;
    private AppExecutors executors;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_AERIS_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private AerisApiService service = retrofit.create(AerisApiService.class);

    // This repository is a singleton
    public static AirQualityRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AirQualityRepository(context);
            }
        }
        return sInstance;
    }

    // Constructor
    private AirQualityRepository(Context context) {
        mDb = InjectorUtils.provideAppDataBase(context);
        executors = InjectorUtils.provideAppExecutors();
    }

    /**
     * @param latitude  of the place
     * @param longitude of the place
     * @return a live data object of the response from the internet
     */
    public LiveData<AirQualityResponse.RootObject> getAirQuality(String latitude, String longitude) {

        final LiveData<AirQualityResponse.RootObject> liveData = new MutableLiveData<>();
        Call<AirQualityResponse.RootObject> call = service.getAirQualityData(latitude, longitude, BuildConfig.Api_ID, BuildConfig.Api_Secret_ID);

        call.enqueue(new Callback<AirQualityResponse.RootObject>() {
            @Override
            public void onResponse(@NonNull Call<AirQualityResponse.RootObject> call, @NonNull Response<AirQualityResponse.RootObject> response) {
                //post response to live data
                ((MutableLiveData<AirQualityResponse.RootObject>) liveData).postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<AirQualityResponse.RootObject> call, @NonNull Throwable t) {
                Timber.d(t, call.toString());
            }
        });
        return liveData;
    }


    /**
     * @param latitude  of the place
     * @param longitude of the place
     * @return a live data object of the response from the internet
     */
    public LiveData<CountryInfoResponse.RootObject> getCountryData(String latitude, String longitude) {

        final LiveData<CountryInfoResponse.RootObject> liveData = new MutableLiveData<>();
        String location = latitude + "," + longitude;
        Call<CountryInfoResponse.RootObject> call = service.getCountryInfo(location, BuildConfig.Api_ID, BuildConfig.Api_Secret_ID);

        call.enqueue(new Callback<CountryInfoResponse.RootObject>() {
            @Override
            public void onResponse(@NonNull Call<CountryInfoResponse.RootObject> call, @NonNull Response<CountryInfoResponse.RootObject> response) {
                //post response to live data
                ((MutableLiveData<CountryInfoResponse.RootObject>) liveData).postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CountryInfoResponse.RootObject> call, @NonNull Throwable t) {
                Timber.d(t);
            }
        });

        return liveData;
    }

    public LiveData<IndicesResponse.RootObject> getIndicesInfo(String latitude, String longitude, String indicesType) {

        final LiveData<IndicesResponse.RootObject> liveData = new MutableLiveData<>();
        String location = latitude + "," + longitude;
        Call<IndicesResponse.RootObject> call = service.getIndicesInfo(indicesType, location, BuildConfig.Api_ID, BuildConfig.Api_Secret_ID);

        call.enqueue(new Callback<IndicesResponse.RootObject>() {
            @Override
            public void onResponse(@NonNull Call<IndicesResponse.RootObject> call, @NonNull Response<IndicesResponse.RootObject> response) {
                ((MutableLiveData<IndicesResponse.RootObject>) liveData).postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<IndicesResponse.RootObject> call, @NonNull Throwable t) {
                Timber.d(t);
            }
        });

        return liveData;
    }

    /**
     *
     * @return live data list of LocationData from database
     */
    public LiveData<List<LocationData>> loadSavedLocationData() {
        return mDb.locationDataDao().loadLocationData();
    }

    /**
     * saves the location data in local repository
     * @param locationData to be saved
     */
    public void saveLocationData(LocationData locationData) {
        mDb.locationDataDao().saveLocationData(locationData);
    }

    /**
     * This function will be called by a service to refresh data of all saved locations from data base
     */
    public void syncAllLocationsData() {
        List<LocationData> listLocationData = mDb.locationDataDao().getLocationDataList();

        if (listLocationData != null) {

            for (final LocationData locationData : listLocationData) {

                String latitude = String.valueOf(locationData.getLocationAirQualityData().getLoc().getLat());
                String longitude = String.valueOf(locationData.getLocationAirQualityData().getLoc().getLongitude());

                Call<AirQualityResponse.RootObject> call = service.getAirQualityData(latitude, longitude, BuildConfig.Api_ID, BuildConfig.Api_Secret_ID);

                call.enqueue(new Callback<AirQualityResponse.RootObject>() {
                    @Override
                    public void onResponse(@NonNull Call<AirQualityResponse.RootObject> call, @NonNull Response<AirQualityResponse.RootObject> response) {

                        if ((response.body() != null ? response.body().getResponse() : null) != null) {
                            // update location data
                            locationData.setLocationAirQualityData(response.body().getResponse().get(Constants.BASE_INDEX));
                            executors.diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.locationDataDao().updateLocationData(locationData);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AirQualityResponse.RootObject> call, @NonNull Throwable t) {
                        Timber.d(t, call.toString());
                    }
                });
            }
        } else {
            Timber.d("The repository has no saved location");
        }
    }
}
