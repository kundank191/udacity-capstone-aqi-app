package com.example.kunda.aqiapp.ui;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AirQualityRepository;
import com.example.kunda.aqiapp.data.sync.SyncUtils;
import com.example.kunda.aqiapp.ui.fragments.HomeFragment;
import com.example.kunda.aqiapp.ui.fragments.PollutantsInfoFragment;
import com.example.kunda.aqiapp.ui.fragments.SavedLocationsFragment;
import com.example.kunda.aqiapp.ui.viewModel.MainViewModel;
import com.example.kunda.aqiapp.utils.InjectorUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
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

        AirQualityRepository repository = InjectorUtils.provideAirQualityRepository(this);
        // Initialize firebase job dispatcher
        SyncUtils.initializeSync(this);

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
            case R.id.about_data:
                final String url = getResources().getString(R.string.aqi_color_guide);
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor("#2196F3"));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, Uri.parse(url));
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

}
