package com.example.kunda.aqiapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

/**
 * Created by Kundan on 17-10-2018.
 */
public class PrefUtils {

    /**
     *
     * @param context the context to access shared preferences
     * @return a true if app is launched for the first time
     */
    public static boolean isFirstAppLaunch(Context context){
        boolean isFirstAppLaunch;
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SAVED_LOCATION_PREFS_FILE_NAME,Context.MODE_PRIVATE);
        isFirstAppLaunch = sharedPreferences.getBoolean(Constants.IS_FIRST_APP_LAUNCH_KEY,true);
        // Default value will be true on first app launch , when home fragment is opened by main activity then , this value will be set to false
        return isFirstAppLaunch;
    }

    /**
     * to keep track of the app first launch variable
     * @param context to access shared preferences
     */
    public static void appFirstTimeLaunched(Context context){
        // A variable will be set to keep track if app was launched earlier
        SharedPreferences.Editor editor = Objects.requireNonNull(context).getSharedPreferences(Constants.SAVED_LOCATION_PREFS_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Constants.IS_FIRST_APP_LAUNCH_KEY, false);
        editor.apply();
    }

}
