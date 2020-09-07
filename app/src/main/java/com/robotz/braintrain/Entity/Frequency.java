package com.robotz.braintrain.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "frequency_table")
public class Frequency {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Medication.class,
            parentColumns = "id",
            childColumns = "medicationId",
            onDelete = ForeignKey.CASCADE)
    private int medicationId;
    private String frequency_type;
    private String frequency_time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Frequency(int medicationId, String frequency_type, String frequency_time) {
        this.medicationId = medicationId;
        this.frequency_type = frequency_type;
        this.frequency_time = frequency_time;
    }

    public int getMedicationId() {
        return medicationId;
    }

    public String getFrequency_type() {
        return frequency_type;
    }
    public String getFrequency_time() {
        return frequency_time;
    }

}
