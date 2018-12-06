package com.example.kunda.aqiapp.ui.fragments;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.database.LocationData;
import com.example.kunda.aqiapp.data.network.AirQualityResponse;
import com.example.kunda.aqiapp.ui.adapters.SavedLocationDataAdapter;
import com.example.kunda.aqiapp.ui.viewModel.MainViewModel;
import com.example.kunda.aqiapp.ui.viewModel.MainViewModelFactory;
import com.example.kunda.aqiapp.ui.widget.HomeDataAppWidget;
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
import androidx.constraintlayout.widget.Group;
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
    private FloatingActionButton fab;
    private MainViewModel mainViewModel;
    private long homeLocationDataID;
    private Group mainView;
    private TextView emptyStateView;

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
        mainView = rootView.findViewById(R.id.saved_location_fragment_main_view);
        emptyStateView = rootView.findViewById(R.id.saved_location_empty_state_view);
        fab = rootView.findViewById(R.id.fab_add_place);
        fab.setOnClickListener(new View.OnClickListener() {
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
        homeLocationDataID = mainViewModel.getHomeLocationDataID();
        adapter = new SavedLocationDataAdapter(getFragment(), null, homeLocationDataID);
        locationDataRV.setAdapter(adapter);

        mainViewModel.getSavedLocationDataList().observe(Objects.requireNonNull(getActivity()), new Observer<List<LocationData>>() {
            @Override
            public void onChanged(List<LocationData> locationData) {
                if (locationData != null) {
                    // Show main UI
                    showMainUI();
                    adapter.setLocationDataList(locationData);
                    locationDataRV.scrollToPosition(mainViewModel.getSavedLocationFragmentRVPosition());
                } else {
                    // No saved location
                    showEmptyStateView();
                }
            }
        });
        /*
          When the recycler view is scrolled down then the fab will be hidden.
          When the recycler view is scrolled up the fab will be shown.
         */
        locationDataRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void addNewPlace() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(Objects.requireNonNull(getActivity()));
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            Toast.makeText(getContext(), R.string.error_getting_place_info, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), R.string.error_getting_place_info, Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        alertDialog.setTitle(getString(R.string.dialog_title));
        alertDialog.setMessage(getString(R.string.dialog_subtitle));

        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton(getString(R.string.dialog_positive_button), new DialogInterface.OnClickListener() {
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
                if (rootObject != null) {
                    saveLocationData(placeName, rootObject.getResponse().get(BASE_INDEX));
                } else {
                    Toast.makeText(getContext(), R.string.error_getting_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveLocationData(String placeName, AirQualityResponse.Response locationAirQualityData) {
        final LocationData locationData = new LocationData(placeName, locationAirQualityData);
        mainViewModel.saveNewLocationData(locationData);
    }

    @Override
    public void markAsHomeLocationData(View view, LocationData locationData, int position) {
        mainViewModel.saveHomeLocationData(locationData);
        homeLocationDataID = locationData.getLocationID();
        mainViewModel.setSavedLocationFragmentRVPosition(position);
        // update widget on home location change
        updateWidget();
        Toast.makeText(getActivity(), String.format(getString(R.string.home_location_saved), locationData.getLocationName()), Toast.LENGTH_SHORT).show();
    }

    /**
     * Update widget when home location changes
     * got this function from : https://stackoverflow.com/questions/3455123/programmatically-update-widget-from-activity-service-receiver
     */
    private void updateWidget() {
        Intent intent = new Intent(getActivity(), HomeDataAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getContext())
                .getAppWidgetIds(new ComponentName(Objects.requireNonNull(getContext()), HomeDataAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        Objects.requireNonNull(getActivity()).sendBroadcast(intent);
    }

    private Fragment getFragment() {
        return this;
    }

    /**
     * Show the main UI and hide the empty state view
     */
    private void showMainUI() {
        mainView.setVisibility(View.VISIBLE);
        emptyStateView.setVisibility(View.GONE);
    }

    /**
     * Show the empty state view and hide the main UI
     */
    private void showEmptyStateView() {
        mainView.setVisibility(View.GONE);
        emptyStateView.setVisibility(View.VISIBLE);
    }
}
