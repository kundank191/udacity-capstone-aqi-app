package com.example.kunda.aqiapp.ui.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.database.LocationData;
import com.example.kunda.aqiapp.data.network.AirQualityResponse;
import com.example.kunda.aqiapp.ui.adapters.SavedLocationDataAdapter;
import com.example.kunda.aqiapp.ui.viewModel.MainViewModel;
import com.example.kunda.aqiapp.ui.viewModel.MainViewModelFactory;
import com.example.kunda.aqiapp.utils.InjectorUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.kunda.aqiapp.utils.Constants.BASE_INDEX;

/**
 * create an instance of this fragment.
 */
public class SavedLocationsFragment extends Fragment implements SavedLocationDataAdapter.MarkAsHomeLocationDataListener {

    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private RecyclerView locationDataRV;
    private SavedLocationDataAdapter adapter;
    private MainViewModel mainViewModel;

    public SavedLocationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_saved_locations, container, false);

        locationDataRV = rootView.findViewById(R.id.rv_saved_location_data);
        locationDataRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        FloatingActionButton button = rootView.findViewById(R.id.fab_add_place);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPlace();
            }
        });
        init();
        return rootView;
    }

    private void init() {
        MainViewModelFactory viewModelFactory = InjectorUtils.provideMainViewModelFactory(getContext());
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        mainViewModel.getSavedLocationDataList().observe(getActivity(), new Observer<List<LocationData>>() {
            @Override
            public void onChanged(List<LocationData> locationData) {
                adapter = new SavedLocationDataAdapter(getFragment(), locationData);
                locationDataRV.setAdapter(adapter);
            }
        });
    }

    private void addNewPlace() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(Objects.requireNonNull(getActivity()));
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            Toast.makeText(getContext(), "Error getting place info", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Place place = PlaceAutocomplete.getPlace(Objects.requireNonNull(getContext()), data);
                    getLocationNameFromDialogBox(place);
                    break;
                case PlaceAutocomplete.RESULT_ERROR:
                    Status status = PlaceAutocomplete.getStatus(Objects.requireNonNull(getContext()), data);
                    Toast.makeText(getContext(), "Error getting place info", Toast.LENGTH_SHORT).show();
                    Timber.i(status.getStatusMessage());
                    break;
                case RESULT_CANCELED:
                    // The user canceled the operation.
                    Timber.d("Error Handled");
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getLocationNameFromDialogBox(final Place place) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Add Location");
        alertDialog.setMessage("Enter name of the new location");

        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String locationName = input.getText().toString();
                getLocationDataFromLatLng(locationName.trim(), place);
            }
        });
        alertDialog.show();
    }

    private void getLocationDataFromLatLng(final String placeName, final Place place) {
        mainViewModel.getAirQualityResponse(String.valueOf(place.getLatLng().latitude), String.valueOf(place.getLatLng().longitude)).observe(getActivity(), new Observer<AirQualityResponse.RootObject>() {
            @Override
            public void onChanged(AirQualityResponse.RootObject rootObject) {
                saveLocationData(placeName, rootObject.getResponse().get(BASE_INDEX));
            }
        });
    }

    private void saveLocationData(String placeName, AirQualityResponse.Response locationAirQualityData) {
        final LocationData locationData = new LocationData(placeName, locationAirQualityData);
        mainViewModel.saveNewLocationData(locationData);
    }

    @Override
    public void markAsHomeLocationData(View view, LocationData locationData, int position) {
        Timber.d("Hello");
        mainViewModel.saveHomeLocationData(locationData);
        locationDataRV.scrollToPosition(position);
        Toast.makeText(getActivity(),"Home location set to \"" + locationData.getLocationName() + "\"",Toast.LENGTH_SHORT).show();
    }

    private Fragment getFragment() {
        return this;
    }
}
