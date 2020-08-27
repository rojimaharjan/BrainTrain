package com.robotz.braintrain.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;

    private String fathers_first_name;
    private String mothers_maiden_name;
    private String username;

    public User(String fathers_first_name, String mothers_maiden_name, String username) {
        this.fathers_first_name = fathers_first_name;
        this.mothers_maiden_name = mothers_maiden_name;
        this.username = username;
    }

    public String getFathers_first_name() {
        return fathers_first_name;
    }

    public String getMothers_maiden_name() {
        return mothers_maiden_name;
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
