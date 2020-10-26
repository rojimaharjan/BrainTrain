package com.robotz.braintrain.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.robotz.braintrain.Entity.Medication;
import com.robotz.braintrain.Entity.Score;

import java.util.List;

@Dao
public interface ScoreDao {

 /*   @Insert
    void insert(Score score);*/

    @Update
    void update(Score score);

    @Delete
    void delete(Score score);

    @Query("DELETE FROM score_table")
    void deleteAllScores();

    @Query("SELECT * FROM score_table WHERE id = 'id'")
    LiveData<List<Score>> getAllScores();

    @Query("INSERT INTO score_table (userId, game_name, total_score, total_time_taken, error, played_date) VALUES(:userId, :game_name, :total_score, :total_time_taken, :error, :played_date)")
    long insert(int userId, String game_name, String total_score, String total_time_taken, String error, String played_date);

}
