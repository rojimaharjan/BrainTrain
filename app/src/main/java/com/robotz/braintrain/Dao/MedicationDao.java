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

    /*@Insert
    Long insert(Medication medication);
*/

    @Query("INSERT INTO medication_table (userId, med_name, type, as_needed) VALUES(:userId, :med_name, :type, :as_needed)")
    long insert(int userId, String med_name, String type, boolean as_needed);

    @Update
    void update(Medication medication);

    @Delete
    void delete(Medication medication);

    @Query("DELETE FROM medication_table")
    void deleteAllMedications();

    @Query("SELECT * FROM medication_table")
    List<Medication> getAllMedications();

}
