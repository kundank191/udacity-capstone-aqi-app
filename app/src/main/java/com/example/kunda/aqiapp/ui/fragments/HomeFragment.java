package com.example.kunda.aqiapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AirQualityRepository;
import com.example.kunda.aqiapp.data.AirQualityResponse;
import com.example.kunda.aqiapp.ui.adapters.PollutantsAdapter;
import com.example.kunda.aqiapp.utils.InjectorUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

/**
 */
public class HomeFragment extends Fragment {

    private int BASE_INDEX = 0;
    private RecyclerView pollutantsDataRV;
    private PollutantsAdapter pollutantsAdapter;

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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        pollutantsDataRV = rootView.findViewById(R.id.rv_pollutants);
        pollutantsDataRV.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        AirQualityRepository repository = InjectorUtils.getAirQualityRepository(getContext());
        repository.getAirQuality("23.4306","85.4154").observe(this, new Observer<AirQualityResponse.RootObject>() {
            @Override
            public void onChanged(@Nullable AirQualityResponse.RootObject rootObject) {
                Timber.d(getPollutants(rootObject).toString());
                pollutantsAdapter = new PollutantsAdapter(getContext(),getPollutants(rootObject));
                pollutantsDataRV.setAdapter(pollutantsAdapter);
            }
        });
        return rootView;
    }


    private ArrayList<AirQualityResponse.Pollutant> getPollutants(AirQualityResponse.RootObject rootObject){
        return rootObject.getResponse().get(BASE_INDEX).getPeriods().get(BASE_INDEX).getPollutants();
    }
}
