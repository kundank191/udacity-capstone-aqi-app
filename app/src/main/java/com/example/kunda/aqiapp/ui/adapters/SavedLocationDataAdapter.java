package com.example.kunda.aqiapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.database.LocationData;
import com.example.kunda.aqiapp.data.network.AirQualityResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Kundan on 09-10-2018.
 */
public class SavedLocationDataAdapter extends RecyclerView.Adapter<SavedLocationDataAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView locationNameTV;
        TextView locationDescriptionTV;
        TextView locationDetailsTV;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            locationNameTV = itemView.findViewById(R.id.tv_location_name);
            locationDescriptionTV = itemView.findViewById(R.id.tv_location_details);
            locationDetailsTV = itemView.findViewById(R.id.tv_location_more_details);
        }
    }

    private List<LocationData> locationDataArrayList;
    private Context context;

    public SavedLocationDataAdapter(Context context, List<LocationData> locationDataArrayList){
        this.context = context;
        this.locationDataArrayList = locationDataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.saved_location_data_row_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  LocationData locationData = locationDataArrayList.get(position);

        holder.locationNameTV.setText(locationData.getLocationName());
        holder.locationDescriptionTV.setText(String.valueOf(locationData.getLocationID()));
        AirQualityResponse.Loc loc = locationData.getLocationAirQualityData().getLoc();
        String location = " location " + loc.getLat() + " : " + loc.getLongitude();
        holder.locationDetailsTV.setText(location);
    }

    @Override
    public int getItemCount() {
        return locationDataArrayList.size();
    }

    public Context getContext() {
        return context;
    }
}
