package com.example.kunda.aqiapp.data.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import androidx.room.Embedded;

/**
 * Created by Kundan on 22-09-2018.
 * POJO for the internet data response
 */
public class AirQualityResponse {

    public class RootObject
    {
        private boolean success;

        public boolean getSuccess() { return this.success; }

        public void setSuccess(boolean success) { this.success = success; }

        private String  error;

        public String getError() { return this.error; }

        public void setError(String error) { this.error = error; }

        private ArrayList<Response> response;

        public ArrayList<Response> getResponse() { return this.response; }

        public void setResponse(ArrayList<Response> response) { this.response = response; }
    }

    public static class Loc
    {
        private double lat;

        public double getLat() { return this.lat; }

        public void setLat(double lat) { this.lat = lat; }

        @SerializedName("long")
        private double longitude;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    public static class Place
    {
        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private String state;

        public String getState() { return this.state; }

        public void setState(String state) { this.state = state; }

        private String country;

        public String getCountry() { return this.country; }

        public void setCountry(String country) { this.country = country; }
    }

    public static class Pollutant
    {
        private String type;

        public String getType() { return this.type; }

        public void setType(String type) { this.type = type; }

        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private double valuePPB;

        public double getValuePPB() { return this.valuePPB; }

        public void setValuePPB(double valuePPB) { this.valuePPB = valuePPB; }

        private double valueUGM3;

        public double getValueUGM3() { return this.valueUGM3; }

        public void setValueUGM3(double valueUGM3) { this.valueUGM3 = valueUGM3; }

        private Integer aqi;

        public Integer getAqi() { return this.aqi; }

        public void setAqi(Integer aqi) { this.aqi = aqi; }

        private String category;

        public String getCategory() { return this.category; }

        public void setCategory(String category) { this.category = category; }

        private String color;

        public String getColor() { return this.color; }

        public void setColor(String color) { this.color = color; }
    }

    public static class Period
    {
        private String dateTimeISO;

        public String getDateTimeISO() { return this.dateTimeISO; }

        public void setDateTimeISO(String dateTimeISO) { this.dateTimeISO = dateTimeISO; }

        private Integer timestamp;

        public Integer getTimestamp() { return this.timestamp; }

        public void setTimestamp(Integer timestamp) { this.timestamp = timestamp; }

        private Integer aqi;

        public Integer getAqi() { return this.aqi; }

        public void setAqi(Integer aqi) { this.aqi = aqi; }

        private String category;

        public String getCategory() { return this.category; }

        public void setCategory(String category) { this.category = category; }

        private String color;

        public String getColor() { return this.color; }

        public void setColor(String color) { this.color = color; }

        private String method;

        public String getMethod() { return this.method; }

        public void setMethod(String method) { this.method = method; }

        private String dominant;

        public String getDominant() { return this.dominant; }

        public void setDominant(String dominant) { this.dominant = dominant; }

        @Embedded
        private ArrayList<Pollutant> pollutants;

        public ArrayList<Pollutant> getPollutants() { return this.pollutants; }

        public void setPollutants(ArrayList<Pollutant> pollutants) { this.pollutants = pollutants; }
    }

    public static class Source
    {
        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }
    }

    public static class Profile
    {
        private String tz;

        public String getTz() { return this.tz; }

        public void setTz(String tz) { this.tz = tz; }

        @Embedded
        private ArrayList<Source> sources;

        public ArrayList<Source> getSources() { return this.sources; }

        public void setSources(ArrayList<Source> sources) { this.sources = sources; }

    }

    public static class Response
    {
        private String id;

        public String getId() { return this.id; }

        public void setId(String id) { this.id = id; }

        @Embedded
        private Loc loc;

        public Loc getLoc() { return this.loc; }

        public void setLoc(Loc loc) { this.loc = loc; }

        @Embedded
        private Place place;

        public Place getPlace() { return this.place; }

        public void setPlace(Place place) { this.place = place; }

        private ArrayList<Period> periods;

        public ArrayList<Period> getPeriods() { return this.periods; }

        public void setPeriods(ArrayList<Period> periods) { this.periods = periods; }

        @Embedded
        private Profile profile;

        public Profile getProfile() { return this.profile; }

        public void setProfile(Profile profile) { this.profile = profile; }
    }

}
