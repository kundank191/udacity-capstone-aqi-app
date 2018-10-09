package com.example.kunda.aqiapp.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AirQualityRepository;
import com.example.kunda.aqiapp.data.network.IndicesResponse;
import com.example.kunda.aqiapp.data.network.LocationInfoResponse;
import com.example.kunda.aqiapp.ui.fragments.AboutAppDataFragment;
import com.example.kunda.aqiapp.ui.fragments.HomeFragment;
import com.example.kunda.aqiapp.ui.fragments.PollutantsInfoFragment;
import com.example.kunda.aqiapp.ui.fragments.SavedLocationsFragment;
import com.example.kunda.aqiapp.utils.InjectorUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private int BASE_INDEX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        AirQualityRepository repository = InjectorUtils.getAirQualityRepository(this);

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
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        //Initially home fragment will be shown
        setFragment(new HomeFragment());
    }

    private IndicesResponse.Indice getIndicesInfo(IndicesResponse.RootObject rootObject){
        return rootObject.getResponse().get(BASE_INDEX).getIndice();
    }

    private LocationInfoResponse.Place getLocationInfo(LocationInfoResponse.RootObject rootObject){
        return rootObject.getResponse().getPlace();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = new HomeFragment();
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_list_places:
                fragment = new SavedLocationsFragment();
                break;
            case R.id.navigation_about_pollutants:
                fragment = new PollutantsInfoFragment();
                break;
            case R.id.navigation_about:
                fragment = new AboutAppDataFragment();
                break;
        }
        setFragment(fragment);
        return true;

    }

    /**
     *
     * @param fragment the fragment which will be shown in the fragment container view
     */
    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
