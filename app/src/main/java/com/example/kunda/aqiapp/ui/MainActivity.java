package com.example.kunda.aqiapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AirQualityRepository;
import com.example.kunda.aqiapp.data.network.CountryInfoResponse;
import com.example.kunda.aqiapp.data.sync.SyncUtils;
import com.example.kunda.aqiapp.ui.fragments.HomeFragment;
import com.example.kunda.aqiapp.ui.fragments.PollutantsInfoFragment;
import com.example.kunda.aqiapp.ui.fragments.SavedLocationsFragment;
import com.example.kunda.aqiapp.ui.viewModel.MainViewModel;
import com.example.kunda.aqiapp.utils.Constants;
import com.example.kunda.aqiapp.utils.InjectorUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            setFragment(new HomeFragment());
        }
        init();

        AirQualityRepository repository = InjectorUtils.getAirQualityRepository(this);
        // Initialize firebase job dispatcher
        SyncUtils.initializeSync(this);

        repository.getCountryData("23.4306","85.4154").observe(this, new Observer<CountryInfoResponse.RootObject>() {
            @Override
            public void onChanged(@Nullable CountryInfoResponse.RootObject rootObject) {
                saveCountryInfoInPreferences(getBaseContext(),rootObject);
            }
        });
    }

    private void init(){
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        viewModel = ViewModelProviders.of(this,InjectorUtils.provideMainViewModelFactory(this)).get(MainViewModel.class);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = new HomeFragment();
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                setTitle(R.string.app_name);
                break;
            case R.id.navigation_list_places:
                fragment = new SavedLocationsFragment();
                setTitle(R.string.saved_location_title);
                break;
            case R.id.navigation_about_pollutants:
                fragment = new PollutantsInfoFragment();
                setTitle(R.string.pollutants_title);
                break;
        }
        setFragment(fragment);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_country:
                Toast.makeText(this,getCountryDataFromPreferences(this),Toast.LENGTH_SHORT).show();
                break;
        }
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

    private void saveCountryInfoInPreferences(Context context, CountryInfoResponse.RootObject countryInfo){
        CountryInfoResponse.Response countryData = countryInfo.getResponse();
        Gson gson = new Gson();
        String countryDataString = gson.toJson(countryData);
        SharedPreferences.Editor editor = Objects.requireNonNull(context).getSharedPreferences(Constants.SAVED_LOCATION_PREFS_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.SAVED_COUNTRY_DATA, countryDataString);
        editor.apply();
    }

    private String getCountryDataFromPreferences(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SAVED_LOCATION_PREFS_FILE_NAME,Context.MODE_PRIVATE);
        String countryData = sharedPreferences.getString(Constants.SAVED_COUNTRY_DATA,null);
        viewModel.setCountryData(countryData);
        return viewModel.getCountryData();
    }
}
