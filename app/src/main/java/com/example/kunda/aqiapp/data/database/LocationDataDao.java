package com.example.kunda.aqiapp.data.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Kundan on 09-10-2018.
 * DAO object for room , to save LocationData
 */
@Dao
public interface LocationDataDao {

    @Query("SELECT * FROM `locationdata-new`")
    LiveData<List<LocationData>> loadLocationData();

    @Query("SELECT * FROM `locationdata-new`")
    List<LocationData> getLocationDataList();

    @Query("SELECT * FROM `locationdata-new` WHERE locationID = :locationID")
    LocationData getLocationDataByID(int locationID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveLocationData(LocationData locationData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long saveHomeLocation(LocationData locationData);

    @Update
    void updateLocationData(LocationData locationData);

    @Delete
    void deleteLocationData(LocationData locationData);
}
