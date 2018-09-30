package com.example.kunda.aqiapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AirQualityResponse;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Kundan on 28-09-2018.
 */
public class PollutantsAdapter extends RecyclerView.Adapter<PollutantsAdapter.ViewHolder>{

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pollutantImageIV;
        TextView pollutantNameTV;
        TextView pollutantDetailsTV;

        ViewHolder(View itemView) {
            super(itemView);
            pollutantImageIV = itemView.findViewById(R.id.iv_pollutant_image);
            pollutantNameTV = itemView.findViewById(R.id.tv_pollutant_name);
            pollutantDetailsTV = itemView.findViewById(R.id.tv_pollutant_details);
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
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.pollutants_row_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AirQualityResponse.Pollutant pollutant = airQualityData.get(position);
        holder.pollutantDetailsTV.setText(pollutant.getName());
        holder.pollutantNameTV.setText(String.valueOf(pollutant.getAqi()));
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
