package com.example.kunda.aqiapp.data;

/**
 * Created by Kundan on 01-10-2018.
 * POJO which related to a pollutant data
 */
public class AboutPollutant {

    private int pollutantTitleStringID;
    private int pollutantSubtitleStringID;
    private int pollutantDescriptionStringID;
    private int pollutantInfoLinkStringID;
    private int pollutantIcon;

    public AboutPollutant(int pollutantNameStringID, int pollutantSubtitleStringID,int pollutantDescriptionStringID, int pollutantInfoLinkStringID, int pollutantIcon) {
        this.pollutantTitleStringID = pollutantNameStringID;
        this.pollutantSubtitleStringID = pollutantSubtitleStringID;
        this.pollutantDescriptionStringID = pollutantDescriptionStringID;
        this.pollutantInfoLinkStringID = pollutantInfoLinkStringID;
        this.pollutantIcon = pollutantIcon;
    }

    public int getPollutantTitleStringID() {
        return pollutantTitleStringID;
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

    public int getPollutantSubtitleStringID() {
        return pollutantSubtitleStringID;
    }
}
