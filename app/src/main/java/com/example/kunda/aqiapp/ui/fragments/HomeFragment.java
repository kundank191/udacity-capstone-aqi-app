package com.example.kunda.aqiapp.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AirQualityResponse;
import com.example.kunda.aqiapp.ui.adapters.PollutantsAdapter;
import com.example.kunda.aqiapp.ui.viewModel.MainViewModel;
import com.example.kunda.aqiapp.ui.viewModel.MainViewModelFactory;
import com.example.kunda.aqiapp.utils.Constants;
import com.example.kunda.aqiapp.utils.InjectorUtils;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

/**
 */
public class HomeFragment extends Fragment {

    private int BASE_INDEX = 0;
    private RecyclerView pollutantsDataRV;
    private PollutantsAdapter pollutantsAdapter;
    private MainViewModelFactory viewModelFactory;
    private MainViewModel mainViewModel;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final int LOCATION_PERMISSION_CODE = 101;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment, Initialize views
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        pollutantsDataRV = rootView.findViewById(R.id.rv_pollutants);
        pollutantsDataRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        //Initialize viewModelFactory and viewModel
        viewModelFactory = InjectorUtils.provideMainViewModelFactory(getContext());
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        // Is only required for the first time when app is launched
        if (isFirstAppLaunch()) {
            firstTimeAppLaunch();
        }

        return rootView;
    }

    /**
     *
     * @param latitude of the place
     * @param longitude of the place
     *
     * request data for air quality data for the desired location
     */
    private void getLocationData(String latitude, String longitude){
        mainViewModel.getAirQualityResponse(latitude, longitude).observe(this, new Observer<AirQualityResponse.RootObject>() {
            @Override
            public void onChanged(@Nullable AirQualityResponse.RootObject rootObject) {
                Timber.d(getPollutants(rootObject).toString());
                pollutantsAdapter = new PollutantsAdapter(getContext(), getPollutants(rootObject));
                pollutantsDataRV.setAdapter(pollutantsAdapter);
                GravitySnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
                snapHelper.attachToRecyclerView(pollutantsDataRV);
            }
        });
    }

    /**
     * Method is called on first time launch of app, I checks if app has location permission
     * then gets the location of user , after getting the location the user , the location based air quality data is requested from the internet.
     */
    private void firstTimeAppLaunch() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Objects.requireNonNull(getContext()).checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_PERMISSION_CODE);
                return;
            }
        }
        getLocation();
    }

    /**
     * No need to check for permission , this function is only called in two cases
     * case 1 : android version < 23 ( Android M )
     * case 2 : the location permission has been granted
     */
    @SuppressLint("MissingPermission")
    private void getLocation(){
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                getLocationData(String.valueOf(location.getLatitude()),String .valueOf(location.getLongitude()));
            }
        });

    }

    /**
     *
     * @param requestCode the permission request code
     * @param permissions the permissions
     * @param grantResults results for the permissions
     *
     * check if location permission has been granted , if granted then user's location air quality data will be retrieved
     *                     else air quality data of a default location will be retrieved.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    getLocation();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     *
     * @param rootObject the response from the internet
     * @return the air quality data about pollutants in which we are interested
     */
    private ArrayList<AirQualityResponse.Pollutant> getPollutants(AirQualityResponse.RootObject rootObject){
        return rootObject.getResponse().get(BASE_INDEX).getPeriods().get(BASE_INDEX).getPollutants();
    }

    /**
     *
     * @return true if app is launched for the first time , else false
     */
    private boolean isFirstAppLaunch(){
        boolean isFirstAppLaunch;
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(Constants.SAVED_LOCATION_PREFS,Context.MODE_PRIVATE);
        isFirstAppLaunch = sharedPreferences.getBoolean(Constants.IS_FIRST_APP_LAUNCH_KEY,true);
        if (isFirstAppLaunch){
            // A variable will be set to keep track if app was launched earlier
            SharedPreferences.Editor editor = getContext().getSharedPreferences(Constants.SAVED_LOCATION_PREFS,Context.MODE_PRIVATE).edit();
            editor.putBoolean(Constants.IS_FIRST_APP_LAUNCH_KEY,false);
            editor.apply();
        }
        return isFirstAppLaunch;
    }
}
