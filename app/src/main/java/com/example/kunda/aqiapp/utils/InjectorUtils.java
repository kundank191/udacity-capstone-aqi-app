package com.example.kunda.aqiapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kunda.aqiapp.AppExecutors;
import com.example.kunda.aqiapp.data.AirQualityRepository;
import com.example.kunda.aqiapp.data.database.AppDatabase;
import com.example.kunda.aqiapp.ui.viewModel.MainViewModelFactory;

/**
 * Created by Kundan on 27-09-2018.
 */
public class InjectorUtils {

    // Provide air quality repository
    public static AirQualityRepository provideAirQualityRepository(Context context){
        return AirQualityRepository.getInstance(context);
    }

    // provide main viewModel factory
    public static MainViewModelFactory provideMainViewModelFactory(Context context){
        AirQualityRepository repository = InjectorUtils.provideAirQualityRepository(context);
        return new MainViewModelFactory(repository);
    }

    // provide app database
    public static AppDatabase provideAppDataBase(Context context){
        return AppDatabase.getInstance(context);
    }

    // provide shared preferences
    public static SharedPreferences provideSharedPreferences(Context context){
        return context.getSharedPreferences(Constants.SAVED_LOCATION_PREFS_FILE_NAME,Context.MODE_PRIVATE);
    }

    // provide app executors
    public static AppExecutors provideAppExecutors(){
        return AppExecutors.getInstance();
    }

}
