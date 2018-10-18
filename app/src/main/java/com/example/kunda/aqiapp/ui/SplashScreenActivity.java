package com.example.kunda.aqiapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.utils.PrefUtils;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.kunda.aqiapp.utils.Constants.SPLASH_TIME_OUT;

public class SplashScreenActivity extends AppCompatActivity {

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
        if (PrefUtils.isFirstAppLaunch(this)) {
            intent = new Intent(this, WelcomeActivity.class);
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
}
