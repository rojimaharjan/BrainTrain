package com.robotz.braintrain.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.robotz.braintrain.Entity.Alarm;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    void insert(Alarm alarm);

    @Update
    void update(Alarm alarm);

    @Delete
    void delete(Alarm alarm);

    @Query("DELETE FROM alarm_table WHERE medicationId = :id")
    void deleteAlarms(int id);

    @Query("SELECT * FROM alarm_table WHERE id = 'id'")
    LiveData<List<Alarm>> getAllAlarms();

    @Query("SELECT * FROM alarm_table WHERE medicationId = :id")
    List<Alarm> getcurrentAlarms(int id);

}
