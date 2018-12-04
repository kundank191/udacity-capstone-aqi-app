package com.example.kunda.aqiapp.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.database.LocationData;
import com.example.kunda.aqiapp.data.network.AirQualityResponse;
import com.example.kunda.aqiapp.ui.adapters.PollutantsAdapter;
import com.example.kunda.aqiapp.ui.viewModel.MainViewModel;
import com.example.kunda.aqiapp.ui.viewModel.MainViewModelFactory;
import com.example.kunda.aqiapp.utils.ColorUtils;
import com.example.kunda.aqiapp.utils.Constants;
import com.example.kunda.aqiapp.utils.InjectorUtils;
import com.example.kunda.aqiapp.utils.PrefUtils;
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
import androidx.recyclerview.widget.RecyclerView;

import static com.example.kunda.aqiapp.utils.Constants.BASE_INDEX;

/**
 */
public class HomeFragment extends Fragment {

    private RecyclerView pollutantsDataRV;
    private PollutantsAdapter pollutantsAdapter;
    private MainViewModel mainViewModel;
    private final int LOCATION_PERMISSION_CODE = 101;

    private TextView textView;
    private TextView locationNameTV;
    private TextView dominantPollutionTV;
    private View ringView;

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
        //Initialize viewModelFactory and viewModel
        MainViewModelFactory viewModelFactory = InjectorUtils.provideMainViewModelFactory(getContext());
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        textView = rootView.findViewById(R.id.api_index_tv);
        locationNameTV = rootView.findViewById(R.id.tv_home_location);
        dominantPollutionTV = rootView.findViewById(R.id.tv_dominant_pollutant);
        ringView = rootView.findViewById(R.id.ring_view);

        // Is only required for the first time when app is launched
        if (PrefUtils.isFirstAppLaunch(Objects.requireNonNull(getContext()))) {
            firstTimeAppLaunch();
        } else {
            showHomeLocationData();
        }

        return rootView;
    }

    /**
     * @param locationData object which stores data about the home location
     *                     this function displays the data on the screen
     */
    private void displayLocationData(final LocationData locationData) {
        pollutantsAdapter = new PollutantsAdapter(getContext(), getPollutants(locationData));
        pollutantsDataRV.setAdapter(pollutantsAdapter);

        AirQualityResponse.Place place = locationData.getLocationAirQualityData().getPlace();
        AirQualityResponse.Period airQualityInfo = locationData.getLocationAirQualityData().getPeriods().get(BASE_INDEX);

        String dominantPollutant = airQualityInfo.getDominant();
        String aqiIndex = String.valueOf(airQualityInfo.getAqi());
        String airQuality = airQualityInfo.getCategory();
        String color = airQualityInfo.getColor();
        String timeUpdated = airQualityInfo.getDateTimeISO();
        String methodMeasured = airQualityInfo.getMethod();
        String placeName = place.getName();
        String country = place.getCountry();

        locationNameTV.setText(String.format("%s (%s, %s)", locationData.getLocationName(), placeName, country));
        dominantPollutionTV.setText(String.format(getString(R.string.dominant_pollutant), dominantPollutant));
        textView.setText(String.format("%s\n%s", aqiIndex, airQuality));

        GradientDrawable drawable = (GradientDrawable) ringView.getBackground();
        drawable.setStroke(Constants.AQI_INDEX_STROKE_WIDTH, ColorUtils.getColor(color));
    }

    /**
     * @param latitude  of the place
     * @param longitude of the place
     *                  <p>
     *                  request data for air quality data for the desired location
     */
    private void getLocationDataFromNetwork(String latitude, String longitude) {
        mainViewModel.getAirQualityResponse(latitude, longitude).observe(this, new Observer<AirQualityResponse.RootObject>() {
            @Override
            public void onChanged(@Nullable AirQualityResponse.RootObject rootObject) {
                if (rootObject != null) {
                    // Save location offline
                    final LocationData locationData = new LocationData(getString(R.string.current_location), rootObject.getResponse().get(BASE_INDEX));
                    mainViewModel.saveHomeLocationData(locationData);
                    // display home location
                    displayLocationData(locationData);
                } else {
                    Toast.makeText(getActivity(), R.string.network_data_null, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showHomeLocationData() {
        InjectorUtils.provideAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final LocationData homeLocationData = mainViewModel.getHomeLocationData();
                if (homeLocationData != null) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            displayLocationData(homeLocationData);
                        }
                    });
                } else {
                    checkPermissionForLocationThenGetLocation();
                }
            }
        });
    }

    /**
     * Method is called on first time launch of app, I checks if app has location permission
     * then gets the location of user , after getting the location the user , the location based air quality data is requested from the internet.
     */
    private void firstTimeAppLaunch() {
        // Only when Home fragment has been opened , app will set launched as first time to be false
        PrefUtils.appFirstTimeLaunched(getContext());
        checkPermissionForLocationThenGetLocation();
    }

    private void checkPermissionForLocationThenGetLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Objects.requireNonNull(getContext()).checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
                return;
            }
        }
        getLocationDataFromCurrentLocation();
    }

    /**
     * No need to check for permission , this function is only called in two cases
     * case 1 : android version < 23 ( Android M )
     * case 2 : the location permission has been granted
     */
    @SuppressLint("MissingPermission")
    private void getLocationDataFromCurrentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    getLocationDataFromNetwork(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                } else {
                    Toast.makeText(getContext(), R.string.location_null_error, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * @param requestCode  the permission request code
     * @param permissions  the permissions
     * @param grantResults results for the permissions
     *                     <p>
     *                     check if location permission has been granted , if granted then user's location air quality data will be retrieved
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
                    getLocationDataFromCurrentLocation();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), R.string.location_permission_denied, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * @param locationData the location data saved in database
     * @return the air quality data about pollutants in which we are interested
     */
    private ArrayList<AirQualityResponse.Pollutant> getPollutants(LocationData locationData) {
        return locationData.getLocationAirQualityData().getPeriods().get(BASE_INDEX).getPollutants();
    }
}
