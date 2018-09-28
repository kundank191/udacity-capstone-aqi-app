package com.example.kunda.aqiapp.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AirQualityRepository;
import com.example.kunda.aqiapp.data.AirQualityResponse;
import com.example.kunda.aqiapp.utils.InjectorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private PollutantInfoAdapter pollutantAdapter;
    @BindView(R.id.rv_pollutants)
    RecyclerView pollutantRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        pollutantRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        AirQualityRepository repository = InjectorUtils.getAirQualityRepository(this);
        repository.getAirQuality("23.4306","85.4154").observe(this, new Observer<AirQualityResponse.RootObject>() {
            @Override
            public void onChanged(@Nullable AirQualityResponse.RootObject rootObject) {
                pollutantAdapter = new PollutantInfoAdapter(getBaseContext(),rootObject.getResponse());
                pollutantRV.setAdapter(pollutantAdapter);
            }
        });
    }
}
