package com.example.kunda.aqiapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Kundan on 22-09-2018.
 */
public class IndicesResponse {

    public class RootObject
    {
        private boolean success;

        public boolean getSuccess() { return this.success; }

        public void setSuccess(boolean success) { this.success = success; }

        private String error;

        public String getError() { return this.error; }

        public void setError(String error) { this.error = error; }

        private ArrayList<Response> response;

        public ArrayList<Response> getResponse() { return this.response; }

        public void setResponse(ArrayList<Response> response) { this.response = response; }
    }

    public class Loc
    {
        private double lat;

        public double getLat() { return this.lat; }

        public void setLat(double lat) { this.lat = lat; }

        @SerializedName("long")
        private double lang;

        public double getLong() { return this.lang; }

        public void setLong(double lang) { this.lang = lang; }
    }

    public class Range
    {
        private int min;

        public int getMin() { return this.min; }

        public void setMin(int min) { this.min = min; }

        private int max;

        public int getMax() { return this.max; }

        public void setMax(int max) { this.max = max; }

        private boolean reverse;

        public boolean getReverse() { return this.reverse; }

        public void setReverse(boolean reverse) { this.reverse = reverse; }
    }

    public class Current
    {
        private int timestamp;

        public int getTimestamp() { return this.timestamp; }

        public void setTimestamp(int timestamp) { this.timestamp = timestamp; }

        private String dateTimeISO;

        public String getDateTimeISO() { return this.dateTimeISO; }

        public void setDateTimeISO(String dateTimeISO) { this.dateTimeISO = dateTimeISO; }

        private int index;

        public int getIndex() { return this.index; }

        public void setIndex(int index) { this.index = index; }

        private String indexENG;

        public String getIndexENG() { return this.indexENG; }

        public void setIndexENG(String indexENG) { this.indexENG = indexENG; }
    }

    public class Indice
    {
        private String type;

        public String getType() { return this.type; }

        public void setType(String type) { this.type = type; }

        private Range range;

        public Range getRange() { return this.range; }

        public void setRange(Range range) { this.range = range; }

        private String past;

        public String getPast() { return this.past; }

        public void setPast(String past) { this.past = past; }

        private Current current;

        public Current getCurrent() { return this.current; }

        public void setCurrent(Current current) { this.current = current; }

        private String forecast;

        public String getForecast() { return this.forecast; }

        public void setForecast(String forecast) { this.forecast = forecast; }
    }

    public class Place
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

    public class Profile
    {
        private String tz;

        public String getTz() { return this.tz; }

        public void setTz(String tz) { this.tz = tz; }
    }

    public class Response
    {
        private Loc loc;

        public Loc getLoc() { return this.loc; }

        public void setLoc(Loc loc) { this.loc = loc; }

        private Indice indice;

        public Indice getIndice() { return this.indice; }

        public void setIndice(Indice indice) { this.indice = indice; }

        private Place place;

        public Place getPlace() { return this.place; }

        public void setPlace(Place place) { this.place = place; }

        private Profile profile;

        public Profile getProfile() { return this.profile; }

        public void setProfile(Profile profile) { this.profile = profile; }
    }

}
