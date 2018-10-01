package com.example.kunda.aqiapp.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kunda.aqiapp.R;
import com.example.kunda.aqiapp.data.AboutPollutant;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Kundan on 01-10-2018.
 */
public class AboutPollutantsAdapter extends RecyclerView.Adapter<AboutPollutantsAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pollutantEffectsImageIV;
        TextView pollutantNameTV;
        TextView pollutantDescriptionTV;
        TextView pollutantSubtitleTV;
        Button moreDetailsButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            pollutantNameTV = itemView.findViewById(R.id.tv_pollutant_name);
            pollutantSubtitleTV = itemView.findViewById(R.id.tv_about_pollutant_subtitle);
            pollutantDescriptionTV = itemView.findViewById(R.id.tv_pollutant_description);
            pollutantEffectsImageIV = itemView.findViewById(R.id.iv_pollutant_image);
            moreDetailsButton = itemView.findViewById(R.id.details_link_iv);
        }
    }

    private ArrayList<AboutPollutant> aboutPollutantArrayList;
    private Context context;

    public AboutPollutantsAdapter(Context context, ArrayList<AboutPollutant> aboutPollutantArrayList) {
        this.aboutPollutantArrayList = aboutPollutantArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.about_pollutants_row_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final AboutPollutant aboutPollutant = aboutPollutantArrayList.get(position);

        holder.pollutantEffectsImageIV.setImageResource(aboutPollutant.getPollutantIcon());
        holder.pollutantEffectsImageIV.setContentDescription(context.getString(aboutPollutant.getPollutantSubtitleStringID()));
        holder.pollutantNameTV.setText(aboutPollutant.getPollutantTitleStringID());
        holder.pollutantSubtitleTV.setText(aboutPollutant.getPollutantSubtitleStringID());
        holder.pollutantDescriptionTV.setText(aboutPollutant.getPollutantDescriptionStringID());
        holder.moreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url = getContext().getResources().getString(aboutPollutant.getPollutantInfoLinkStringID());
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(Color.parseColor("#2196F3"));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(getContext(), Uri.parse(url));
            }
        });
    }

    @Override
    public int getItemCount() {
        return aboutPollutantArrayList.size();
    }

    public Context getContext() {
        return context;
    }
}
