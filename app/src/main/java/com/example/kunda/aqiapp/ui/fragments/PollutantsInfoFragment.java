package com.example.kunda.aqiapp.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AboutPollutant;
import com.example.kunda.aqiapp.ui.adapters.AboutPollutantsAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PollutantsInfoFragment extends Fragment {

    private AboutPollutantsAdapter aboutPollutantsAdapter;
    private RecyclerView recyclerView;

    public PollutantsInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pollutants_info, container, false);
        recyclerView = view.findViewById(R.id.about_pollutant_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

        ArrayList<AboutPollutant> aboutPollutantArrayList = new ArrayList<>();
        aboutPollutantArrayList.add(new AboutPollutant(R.string.pollutant_o3_name,R.string.pollutant_o3_subtitle, R.string.pollutant_o3_description, R.string.pollutant_o3_details_link, R.drawable.about_pollutant_ozone));
        aboutPollutantArrayList.add(new AboutPollutant(R.string.pollutant_pm_name,R.string.pollutant_pm_subtitle, R.string.pollutant_pm_description, R.string.pollutant_pm_details_link, R.drawable.about_pollutant_pm));
        aboutPollutantArrayList.add(new AboutPollutant(R.string.pollutant_co_name,R.string.pollutant_co_subtitle,R.string.pollutant_co_description, R.string.pollutant_co_details_link, R.drawable.about_pollutant_co));
        aboutPollutantArrayList.add(new AboutPollutant(R.string.pollutant_no2_name,R.string.pollutant_no2_subtitle,R.string.pollutant_no2_description, R.string.pollutant_no2_details_link, R.drawable.about_pollutant_nox));
        aboutPollutantArrayList.add(new AboutPollutant(R.string.pollutant_so2_name,R.string.pollutant_so2_subtitle,R.string.pollutant_so2_description, R.string.pollutant_so2_details_link, R.drawable.about_pollutant_so));

        aboutPollutantsAdapter = new AboutPollutantsAdapter(getContext(), aboutPollutantArrayList);
        recyclerView.setAdapter(aboutPollutantsAdapter);
        return view;
    }

}
