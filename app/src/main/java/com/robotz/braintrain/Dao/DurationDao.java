package com.robotz.braintrain.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.robotz.braintrain.Entity.Duration;

import java.util.List;

@Dao
public interface DurationDao {
    @Insert
    void insert(Duration duration);

    @Update
    void update(Duration duration);

    @Delete
    void delete(Duration duration);

    @Query("DELETE FROM duration_table")
    void deleteAllDurations();

    @Query("SELECT * FROM duration_table WHERE id = 'id'")
    LiveData<List<Duration>> getAllDurations();

    @Query("SELECT * FROM duration_table WHERE medicationId = :medicationId")
    Duration getDuration(int medicationId);

    @Query("UPDATE duration_table SET start_date = :start_date, duration_type = :dur_type, duration_time = :time WHERE medicationId = :id")
    void updateDuration(String start_date, String dur_type, String time, int id);

}
