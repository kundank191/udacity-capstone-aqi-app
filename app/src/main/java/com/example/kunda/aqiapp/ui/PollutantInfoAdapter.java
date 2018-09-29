package com.example.kunda.aqiapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AirQualityResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kundan on 28-09-2018.
 */
public class PollutantInfoAdapter extends RecyclerView.Adapter<PollutantInfoAdapter.ViewHolder>{

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_pollutant_image)
        ImageView pollutantImageIV;
        @BindView(R.id.tv_pollutant_name)
        TextView pollutantNameTV;
        @BindView(R.id.tv_pollutant_details)
        TextView pollutantDetailsTV;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    private ArrayList<AirQualityResponse.Pollutant> airQualityData;
    private Context context;

    public PollutantInfoAdapter(Context context,ArrayList<AirQualityResponse.Pollutant> airQualityData) {
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
