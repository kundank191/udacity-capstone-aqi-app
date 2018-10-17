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
 */
@Dao
public interface LocationDataDao {

    @Query("SELECT * FROM locationData")
    LiveData<List<LocationData>> loadLocationData();

    @Query("SELECT * FROM locationData")
    List<LocationData> getLocationDataList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveLocationData(LocationData locationData);

    @Update
    void updateLocationData(LocationData locationData);

    @Delete
    void deleteLocationData(LocationData locationData);
}
