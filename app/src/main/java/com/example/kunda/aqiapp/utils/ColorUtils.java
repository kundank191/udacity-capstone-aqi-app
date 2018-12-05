package com.example.kunda.aqiapp.utils;

import android.graphics.Color;

/**
 * Created by Kundan on 14-11-2018.
 */
public class ColorUtils {

    /**
     *
     * @param color string value of color
     * @return integer value of color
     */
    public static int getColor(String color) {
        String priorityColor = "#" + color;
        return Color.parseColor(priorityColor);
    }
}
