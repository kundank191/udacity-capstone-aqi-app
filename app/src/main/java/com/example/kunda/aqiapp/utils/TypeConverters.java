package com.example.kunda.aqiapp.utils;

import com.example.kunda.aqiapp.data.network.AirQualityResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.room.TypeConverter;

/**
 * Created by Kundan on 10-10-2018.
 * used by room database
 */
public class TypeConverters {

    public static class PeriodConverter {
        static Gson gson = new Gson();

        @TypeConverter
        public static ArrayList<AirQualityResponse.Period> stringToPeriods(String data) {
            if (data == null) {
                return null;
            }

            Type listType = new TypeToken<List<AirQualityResponse.Period>>() {}.getType();

            return gson.fromJson(data, listType);
        }

        @TypeConverter
        public static String periodsArrayListToString(ArrayList<AirQualityResponse.Period> someObjects) {
            return gson.toJson(someObjects);
        }
    }
}
