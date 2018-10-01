package com.example.kunda.aqiapp.data;

/**
 * Created by Kundan on 01-10-2018.
 */
public class AboutPollutant {

    private int pollutantNameStringID;
    private int pollutantDescriptionStringID;
    private int pollutantInfoLinkStringID;
    private int pollutantIcon;

    public AboutPollutant(int pollutantNameStringID, int pollutantDescriptionStringID, int pollutantInfoLinkStringID, int pollutantIcon) {
        this.pollutantNameStringID = pollutantNameStringID;
        this.pollutantDescriptionStringID = pollutantDescriptionStringID;
        this.pollutantInfoLinkStringID = pollutantInfoLinkStringID;
        this.pollutantIcon = pollutantIcon;
    }

    public int getPollutantNameStringID() {
        return pollutantNameStringID;
    }

    public int getPollutantDescriptionStringID() {
        return pollutantDescriptionStringID;
    }

    public int getPollutantInfoLinkStringID() {
        return pollutantInfoLinkStringID;
    }

    public int getPollutantIcon() {
        return pollutantIcon;
    }
}
