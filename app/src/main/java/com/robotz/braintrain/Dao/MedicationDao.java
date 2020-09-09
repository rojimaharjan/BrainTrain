package com.robotz.braintrain.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.robotz.braintrain.Entity.Medication;

import java.util.List;

@Dao
public interface MedicationDao {

    @Insert
    Long insert(Medication medication);

    @Update
    void update(Medication medication);

    @Delete
    void delete(Medication medication);

    @Query("DELETE FROM medication_table")
    void deleteAllMedications();

    @Query("SELECT * FROM medication_table WHERE userId = 'userid'")
    List<Medication> getAllMedications();

}
