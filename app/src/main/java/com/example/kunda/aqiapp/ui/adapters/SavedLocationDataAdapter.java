package com.example.kunda.aqiapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.database.LocationData;
import com.example.kunda.aqiapp.data.network.AirQualityResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Kundan on 09-10-2018.
 */
public class SavedLocationDataAdapter extends RecyclerView.Adapter<SavedLocationDataAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView locationNameTV;
        TextView locationDescriptionTV;
        TextView locationDetailsTV;
        ImageView markAsHomeIV;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            locationNameTV = itemView.findViewById(R.id.tv_location_name);
            locationDescriptionTV = itemView.findViewById(R.id.tv_location_details);
            locationDetailsTV = itemView.findViewById(R.id.tv_location_more_details);
            markAsHomeIV = itemView.findViewById(R.id.iv_mark_as_home);
        }
    }

    private List<LocationData> locationDataArrayList;
    private Fragment fragment;
    private long homeLocationId;
    private MarkAsHomeLocationDataListener markAsHomeLocationDataListenerListener;

    public SavedLocationDataAdapter(Fragment fragment, List<LocationData> locationDataArrayList, long homeLocationID) {
        this.fragment = fragment;
        this.locationDataArrayList = locationDataArrayList;
        this.homeLocationId = homeLocationID;
        try {
            markAsHomeLocationDataListenerListener = (MarkAsHomeLocationDataListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement MarkAsHomeLocationDataListener " + e);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.saved_location_data_row_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final LocationData locationData = locationDataArrayList.get(position);

        holder.locationNameTV.setText(locationData.getLocationName());
        AirQualityResponse.Loc loc = locationData.getLocationAirQualityData().getLoc();
        String location = " location " + loc.getLat() + " : " + loc.getLongitude();
        holder.locationDescriptionTV.setText(location);
        // If the location data is home its icon will be colored
        if (locationData.getLocationID() == homeLocationId) {
            holder.markAsHomeIV.setImageResource(R.drawable.ic_round_home_colored);
        }

        try {

            AirQualityResponse.Place place = locationData.getLocationAirQualityData().getPlace();
            AirQualityResponse.Period airQualityInfo = locationData.getLocationAirQualityData().getPeriods().get(0);

            String dominantPollutant = airQualityInfo.getDominant();
            String aqiIndexLocation = String.valueOf(airQualityInfo.getAqi());
            String airQuality = airQualityInfo.getCategory();
            String color = airQualityInfo.getColor();
            String timeUpdated = airQualityInfo.getDateTimeISO();
            String methodMeasured = airQualityInfo.getMethod();
            String placeName = place.getName();
            String country = place.getCountry();

            holder.locationDetailsTV.setText(String.format("%s \n %s \n %s \n %s \n %s \n %s \n %s \n %s", dominantPollutant, aqiIndexLocation, airQuality, color, timeUpdated, methodMeasured, placeName, country));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        holder.markAsHomeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markAsHomeLocationDataListenerListener.markAsHomeLocationData(view, locationData, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (locationDataArrayList != null)
            return locationDataArrayList.size();
        return 0;
    }

    public List<LocationData> getLocationDataArrayList() {
        return locationDataArrayList;
    }

    public void setLocationDataList(List<LocationData> locationDataArrayList) {
        this.locationDataArrayList = locationDataArrayList;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return fragment.getContext();
    }

    public Fragment getFragment() {
        return fragment;
    }

    public interface MarkAsHomeLocationDataListener {
        public void markAsHomeLocationData(View view, LocationData locationData, int position);
    }
}
