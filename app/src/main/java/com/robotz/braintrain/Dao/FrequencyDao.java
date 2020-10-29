package com.robotz.braintrain.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.robotz.braintrain.Entity.Frequency;
import com.robotz.braintrain.Entity.Score;

import java.util.List;

@Dao
public interface FrequencyDao {
    @Insert
    void insert(Frequency frequency);

    @Update
    void update(Frequency frequency);

    @Delete
    void delete(Frequency frequency);

    @Query("DELETE FROM frequency_table")
    void deleteAllFrequencies();

    @Query("SELECT * FROM frequency_table WHERE id = 'id'")
    LiveData<List<Frequency>> getAllFrequencies();

    @Query("SELECT * FROM frequency_table WHERE medicationId = :medicationId")
    Frequency getFrequency(int medicationId);

    @Query("UPDATE frequency_table SET frequency_type = :fre_type, frequency_time = :time WHERE medicationId = :id")
    void updateFrequency(String fre_type, String time, int id);
}
