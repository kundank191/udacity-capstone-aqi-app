package com.example.kunda.aqiapp.data.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Created by Kundan on 09-10-2018.
 */
@Dao
public interface LocationDataDao {

    @Query("SELECT * FROM locationData")
    LiveData<List<LocationData>> loadLocationData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveLocationData(LocationData locationData);

    @Delete
    void deleteLocationData(LocationData locationData);
}
