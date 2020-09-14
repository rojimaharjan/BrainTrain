package com.robotz.braintrain.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.api.client.util.DateTime;

import java.util.Date;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;

    private String fathers_first_name;
    private String mothers_maiden_name;
    private String date_of_birth;
    private String username;

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    private Date created_on;

    public User(String fathers_first_name, String mothers_maiden_name,String date_of_birth, String username) {
        this.fathers_first_name = fathers_first_name;
        this.mothers_maiden_name = mothers_maiden_name;
        this.date_of_birth = date_of_birth;
        this.username = username;
    }


    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public String getFathers_first_name() {
        return fathers_first_name;
    }

    public String getMothers_maiden_name() {
        return mothers_maiden_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getUsername() {
        return username;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
