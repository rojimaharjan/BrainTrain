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

    @Query("SELECT * FROM frequency_table WHERE id = :medicationId")
    Frequency getFrequency(int medicationId);
}
