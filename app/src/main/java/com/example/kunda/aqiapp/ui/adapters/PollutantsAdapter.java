package com.example.kunda.aqiapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AirQualityResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Kundan on 28-09-2018.
 */
public class PollutantsAdapter extends RecyclerView.Adapter<PollutantsAdapter.ViewHolder>{

    class ViewHolder extends RecyclerView.ViewHolder {

        FloatingActionButton pollutantAQI;
        TextView pollutantTitleTV;
        TextView pollutantNameTV;
        TextView pollutantUgmTV;
        TextView pollutantPpbTV;

        ViewHolder(View itemView) {
            super(itemView);
            pollutantAQI = itemView.findViewById(R.id.fab_pollutant_aqi_index);
            pollutantTitleTV = itemView.findViewById(R.id.tv_pollutant_title);
            pollutantNameTV = itemView.findViewById(R.id.tv_pollutant_name);
            pollutantUgmTV = itemView.findViewById(R.id.tv_pollutant_density);
            pollutantPpbTV = itemView.findViewById(R.id.tv_pollutant_ppb);
        }
    }


    private ArrayList<AirQualityResponse.Pollutant> airQualityData;
    private Context context;

    public PollutantsAdapter(Context context, ArrayList<AirQualityResponse.Pollutant> airQualityData) {
        this.airQualityData = airQualityData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.pollutant_row_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AirQualityResponse.Pollutant pollutant = airQualityData.get(position);
        holder.pollutantNameTV.setText(pollutant.getName());
        holder.pollutantTitleTV.setText(pollutant.getType());
        holder.pollutantUgmTV.setText("UGM");
        holder.pollutantPpbTV.setText("PPB");
    }

    @Override
    public int getItemCount() {
        return airQualityData.size();
    }

    private Context getContext() {
        return context;
    }

    public void setAirQualityData(ArrayList<AirQualityResponse.Pollutant> airQualityData) {
        this.airQualityData = airQualityData;
        notifyDataSetChanged();
    }
}
