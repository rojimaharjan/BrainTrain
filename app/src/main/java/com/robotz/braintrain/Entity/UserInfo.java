package com.robotz.braintrain.Entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/*@Entity(tableName = "userInfo_table", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "userId",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
        ))*/


@Entity(tableName = "userInfo_table")

public class UserInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = User.class,
            parentColumns = "userId",
            childColumns = "userId",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
    )
    private int userId;

    private String diagnosis;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserInfo(int userId, String diagnosis) {
        this.userId = userId;
        this.diagnosis = diagnosis;
    }

    public int getUserId() {
        return userId;
    }

/*    public void setUserId(int userId) {
        this.userId = userId;
    }*/

    public String getDiagnosis() {
        return diagnosis;
    }
}

