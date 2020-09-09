package com.robotz.braintrain.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "medication_table")

public class Medication {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = User.class,
            parentColumns = "userId",
            childColumns = "userId",
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE)
    private int userId;
    private String med_name;
    private String type;
    private boolean as_needed;


    @ColumnInfo(defaultValue = "false")
    private boolean delete;

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    private Date modified_date;



    public Medication(int userId, String med_name, String type, boolean as_needed) {
        this.userId = userId;
        this.med_name = med_name;
        this.type = type;
        this.as_needed = as_needed;
    }


    public void setModified_date(Date modified_date) {
        this.modified_date = modified_date;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getMed_name() {
        return med_name;
    }

    public String getType() {
        return type;
    }

    public boolean isAs_needed() {
        return as_needed;
    }

    public Date getModified_date() {
        return modified_date;
    }

    public boolean isDelete() {
        return delete;
    }
}
