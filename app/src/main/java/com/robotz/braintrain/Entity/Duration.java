package com.robotz.braintrain.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "duration_table")
public class Duration {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Medication.class,
            parentColumns = "id",
            childColumns = "medicationId",
            onDelete = ForeignKey.CASCADE)
    private int medicationId;
    private Date start_date;
    private String duration_type;
    private String duration_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Duration(int medicationId, Date start_date, String duration_type, String duration_time) {
        this.medicationId = medicationId;
        this.start_date = start_date;
        this.duration_type = duration_type;
        this.duration_time = duration_time;
    }


    public int getMedicationId() {
        return medicationId;
    }

    public Date getStart_date() {
        return start_date;
    }

    public String getDuration_type() {
        return duration_type;
    }

    public String getDuration_time() {
        return duration_time;
    }

}
