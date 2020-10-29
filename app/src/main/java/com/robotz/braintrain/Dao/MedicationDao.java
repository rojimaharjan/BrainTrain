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

    @Query("UPDATE medication_table SET `delete` = :delete WHERE id = :id")
    void updateDelete(boolean delete, int id);

    @Query("UPDATE medication_table SET med_name = :med_name, type = :type, as_needed = :asNeeded WHERE id = :id")
    void updateMedication(String med_name, String type, boolean asNeeded, int id);

    @Delete
    void delete(Medication medication);

    @Query("DELETE FROM medication_table")
    void deleteAllMedications();

    @Query("SELECT * FROM medication_table WHERE `delete` = 'false'")
    List<Medication> getAllMedications();

    @Query("SELECT * FROM medication_table WHERE id = :id")
    Medication getMedication(int id);


}
