package com.example.kunda.aqiapp.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AirQualityRepository;
import com.example.kunda.aqiapp.data.AirQualityResponse;
import com.example.kunda.aqiapp.data.IndicesResponse;
import com.example.kunda.aqiapp.data.LocationInfoResponse;
import com.example.kunda.aqiapp.utils.InjectorUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private PollutantsAdapter pollutantAdapter;
    private RecyclerView pollutantsRV;
    private int BASE_INDEX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

      //  pollutantRV.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        AirQualityRepository repository = InjectorUtils.getAirQualityRepository(this);
        repository.getAirQuality("23.4306","85.4154").observe(this, new Observer<AirQualityResponse.RootObject>() {
            @Override
            public void onChanged(@Nullable AirQualityResponse.RootObject rootObject) {
                Timber.d(getPollutants(rootObject).toString());
            }
        });

        repository.getIndicesInfo("23.4306","85.4154","migraine").observe(this, new Observer<IndicesResponse.RootObject>() {
            @Override
            public void onChanged(@Nullable IndicesResponse.RootObject rootObject) {
                Timber.d(getIndicesInfo(rootObject).toString());
            }
        });

        repository.getLocationInfo("23.4306","85.4154").observe(this, new Observer<LocationInfoResponse.RootObject>() {
            @Override
            public void onChanged(@Nullable LocationInfoResponse.RootObject rootObject) {
                Timber.d(getLocationInfo(rootObject).toString());
            }
        });
    }

    private void init(){
        pollutantsRV = findViewById(R.id.rv_pollutants);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    private ArrayList<AirQualityResponse.Pollutant> getPollutants(AirQualityResponse.RootObject rootObject){
        return rootObject.getResponse().get(BASE_INDEX).getPeriods().get(BASE_INDEX).getPollutants();
    }

    private IndicesResponse.Indice getIndicesInfo(IndicesResponse.RootObject rootObject){
        return rootObject.getResponse().get(BASE_INDEX).getIndice();
    }

    private LocationInfoResponse.Place getLocationInfo(LocationInfoResponse.RootObject rootObject){
        return rootObject.getResponse().getPlace();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                Toast.makeText(getBaseContext(),R.string.about_app_data,Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigation_list_places:
                Toast.makeText(getBaseContext(),R.string.about_app_data,Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigation_about_pollutants:
                Toast.makeText(getBaseContext(),R.string.about_app_data,Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigation_about:
                Toast.makeText(getBaseContext(),R.string.about_app_data,Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}
