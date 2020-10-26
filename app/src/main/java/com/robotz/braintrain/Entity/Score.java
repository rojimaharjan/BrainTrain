package com.robotz.braintrain.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "score_table")
public class Score {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = User.class,
            parentColumns = "userId",
            childColumns = "userId",
            onDelete =ForeignKey.NO_ACTION)
    private int userId;
    private String game_name;
    private String total_score;
    private String total_time_taken;
    private String error;
    private String played_date;

    public void setId(int id) {
        this.id = id;
    }

    /*public void setPlayed_date(Long played_date) {
        this.played_date = played_date;
    }*/


    public Score(int userId, String game_name, String total_score, String total_time_taken, String error, String played_date) {
        this.userId = userId;
        this.game_name = game_name;
        this.total_score = total_score;
        this.total_time_taken = total_time_taken;
        this.error = error;
        this.played_date = played_date;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getGame_name() {
        return game_name;
    }

    public String getTotal_score() {
        return total_score;
    }

    public String getTotal_time_taken() {
        return total_time_taken;
    }

    public String getError() {
        return error;
    }

    public String getPlayed_date() {
        return played_date;
    }

   /* public Long getPlayed_date() {
        return played_date;
    }*/
}
