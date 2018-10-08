package com.example.kunda.aqiapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.utils.Constants;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        final Intent intent;
        // If app is launched for the first time a welcome activity will be shown
        if (isFirstAppLaunch()) {
            intent = new Intent(this, MainActivity.class);
            intent.putExtra(Constants.IS_FIRST_APP_LAUNCH_KEY,true);
        } else {
            intent = new Intent(this,MainActivity.class);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

    /**
     *
     * @return true if app is launched for the first time , else false
     */
    private boolean isFirstAppLaunch(){
        boolean isFirstAppLaunch;
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SAVED_LOCATION_PREFS_FILE_NAME,Context.MODE_PRIVATE);
        isFirstAppLaunch = sharedPreferences.getBoolean(Constants.IS_FIRST_APP_LAUNCH_KEY,true);
        if (isFirstAppLaunch){
            // A variable will be set to keep track if app was launched earlier
            SharedPreferences.Editor editor = getSharedPreferences(Constants.SAVED_LOCATION_PREFS_FILE_NAME,Context.MODE_PRIVATE).edit();
            editor.putBoolean(Constants.IS_FIRST_APP_LAUNCH_KEY,false);
            editor.apply();
        }
        return isFirstAppLaunch;
    }
}
