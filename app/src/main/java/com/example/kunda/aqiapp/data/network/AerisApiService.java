package com.example.kunda.aqiapp.data.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Kundan on 22-09-2018.
 * Retrofit service for making internet data calls
 */
public interface AerisApiService {

    String PARAM_CLIENT_ID = "client_id";
    String PARAM_CLIENT_SECRET_ID = "client_secret";
    String PATH_LATITUDE = "lat";
    String PATH_LONGITUDE = "long";

    @GET("airquality/{lat},{long}")
    Call<AirQualityResponse.RootObject> getAirQualityData(@Path(PATH_LATITUDE) String lat, @Path(PATH_LONGITUDE) String lang, @Query(PARAM_CLIENT_ID) String id, @Query(PARAM_CLIENT_SECRET_ID) String secretId);
}
