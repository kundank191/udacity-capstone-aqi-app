package com.example.kunda.aqiapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.kunda.aqiapp.BuildConfig;
import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AirQualityResponse;
import com.example.kunda.aqiapp.data.IndicesResponse;
import com.example.kunda.aqiapp.data.LocationInfoResponse;
import com.example.kunda.aqiapp.data.network.AerisApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.aerisapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AerisApiService service = retrofit.create(AerisApiService.class);
        Call<AirQualityResponse.RootObject> call = service.getAirQualityData("23.4306","85.4154",BuildConfig.Api_ID,BuildConfig.Api_Secret_ID);
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

        Call<LocationInfoResponse.RootObject> call1 = service.getLocation("23.4306,85.4154",BuildConfig.Api_ID,BuildConfig.Api_Secret_ID);
        call1.enqueue(new Callback<LocationInfoResponse.RootObject>() {
            @Override
            public void onResponse(@NonNull Call<LocationInfoResponse.RootObject> call, @NonNull Response<LocationInfoResponse.RootObject> response) {
                Timber.d(response.message());
            }

            @Override
            public void onFailure(@NonNull Call<LocationInfoResponse.RootObject> call, @NonNull Throwable t) {

            }
        });

        Call<IndicesResponse.RootObject> call2 = service.getIndicesInfo("migraine","23.4306,85.4154",BuildConfig.Api_ID,BuildConfig.Api_Secret_ID);
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
