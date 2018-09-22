package com.example.kunda.aqiapp.data;

import java.util.ArrayList;

/**
 * Created by Kundan on 22-09-2018.
 */
public class LocationInfoResponse {

    public class RootObject
    {
        private boolean success;

        public boolean getSuccess() { return this.success; }

        public void setSuccess(boolean success) { this.success = success; }

        private String error;

        public String getError() { return this.error; }

        public void setError(String error) { this.error = error; }

        private Response response;

        public Response getResponse() { return this.response; }

        public void setResponse(Response response) { this.response = response; }
    }

    public class Place
    {
        private String name;

        public String getName() { return this.name; }

        public void setName(String name) { this.name = name; }

        private String iso;

        public String getIso() { return this.iso; }

        public void setIso(String iso) { this.iso = iso; }

        private String iso3;

        public String getIso3() { return this.iso3; }

        public void setIso3(String iso3) { this.iso3 = iso3; }

        private String isoNum;

        public String getIsoNum() { return this.isoNum; }

        public void setIsoNum(String isoNum) { this.isoNum = isoNum; }

        private String fips;

        public String getFips() { return this.fips; }

        public void setFips(String fips) { this.fips = fips; }

        private String continent;

        public String getContinent() { return this.continent; }

        public void setContinent(String continent) { this.continent = continent; }

        private String continentFull;

        public String getContinentFull() { return this.continentFull; }

        public void setContinentFull(String continentFull) { this.continentFull = continentFull; }
    }

    public class Profile
    {
        private String capital;

        public String getCapital() { return this.capital; }

        public void setCapital(String capital) { this.capital = capital; }

        private int areaKM;

        public int getAreaKM() { return this.areaKM; }

        public void setAreaKM(int areaKM) { this.areaKM = areaKM; }

        private double areaMI;

        public double getAreaMI() { return this.areaMI; }

        public void setAreaMI(double areaMI) { this.areaMI = areaMI; }

        private int pop;

        public int getPop() { return this.pop; }

        public void setPop(int pop) { this.pop = pop; }

        private ArrayList<String> neighbors;

        public ArrayList<String> getNeighbors() { return this.neighbors; }

        public void setNeighbors(ArrayList<String> neighbors) { this.neighbors = neighbors; }
    }

    public class Response
    {
        private Place place;

        public Place getPlace() { return this.place; }

        public void setPlace(Place place) { this.place = place; }

        private Profile profile;

        public Profile getProfile() { return this.profile; }

        public void setProfile(Profile profile) { this.profile = profile; }
    }

}
