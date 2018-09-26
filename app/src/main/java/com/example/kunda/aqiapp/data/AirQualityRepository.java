package com.example.kunda.aqiapp.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.kunda.aqiapp.BuildConfig;
import com.example.kunda.aqiapp.data.network.AerisApiService;
import com.example.kunda.aqiapp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Kundan on 25-09-2018.
 */
public class AirQualityRepository {

    private static final Object LOCK = new Object();
    private static AirQualityRepository sInstance;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_AERIS_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private AerisApiService service = retrofit.create(AerisApiService.class);

    public static AirQualityRepository getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new AirQualityRepository();
            }
        }
        return sInstance;
    }

    public void getAirQuality(String latitude, String longitude){
        Call<AirQualityResponse.RootObject> call = service.getAirQualityData(latitude,longitude,BuildConfig.Api_ID,BuildConfig.Api_Secret_ID);
        call.enqueue(new Callback<AirQualityResponse.RootObject>() {
            @Override
            public void onResponse(@NonNull Call<AirQualityResponse.RootObject> call, @NonNull Response<AirQualityResponse.RootObject> response) {
                Timber.d(response.message());
            }

            @Override
            public void onFailure(@NonNull Call<AirQualityResponse.RootObject> call, @NonNull Throwable t) {
                Timber.d(t);
            }
        });
    }

    public void getLocationInfo(String latitude, String longitude){
        String location = latitude + "," + longitude;
        Call<LocationInfoResponse.RootObject> call1 = service.getLocation(location,BuildConfig.Api_ID,BuildConfig.Api_Secret_ID);
        call1.enqueue(new Callback<LocationInfoResponse.RootObject>() {
            @Override
            public void onResponse(@NonNull Call<LocationInfoResponse.RootObject> call, @NonNull Response<LocationInfoResponse.RootObject> response) {
                Timber.d(response.message());
            }

            @Override
            public void onFailure(@NonNull Call<LocationInfoResponse.RootObject> call, @NonNull Throwable t) {

            }
        });
    }

    public void getIndicesInfo(String latitude, String longitude, String indicesType){
        String location = latitude + "," + longitude;
        Call<IndicesResponse.RootObject> call2 = service.getIndicesInfo(indicesType,location,BuildConfig.Api_ID,BuildConfig.Api_Secret_ID);
        call2.enqueue(new Callback<IndicesResponse.RootObject>() {
            @Override
            public void onResponse(@NonNull Call<IndicesResponse.RootObject> call, @NonNull Response<IndicesResponse.RootObject> response) {
                Timber.d(response.message());
            }

            @Override
            public void onFailure(@NonNull Call<IndicesResponse.RootObject> call, @NonNull Throwable t) {

            }
        });
    }
}
