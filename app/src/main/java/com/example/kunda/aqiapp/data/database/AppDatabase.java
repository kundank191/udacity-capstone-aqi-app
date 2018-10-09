package com.example.kunda.aqiapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by Kundan on 09-10-2018.
 */
@Database(entities = {LocationData.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;
    static final String DATABASE_NAME = "locations";

    public static AppDatabase getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract LocationDataDao locationDataDao();
}
