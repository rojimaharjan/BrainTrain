package com.robotz.braintrain.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "duration_table", foreignKeys = @ForeignKey(entity = Medication.class,
        parentColumns = "id",
        childColumns = "medicationId",
        onDelete = ForeignKey.CASCADE))
public class Duration {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int medicationId;
    private Date start_date;
    private Date end_date;
    private Date until_date;
    private int for_x_days;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Duration(int medicationId, Date start_date, Date end_date, Date until_date, int for_x_days) {
        this.medicationId = medicationId;
        this.start_date = start_date;
        this.end_date = end_date;
        this.until_date = until_date;
        this.for_x_days = for_x_days;
    }


    public int getMedicationId() {
        return medicationId;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public Date getUntil_date() {
        return until_date;
    }

    public int getFor_x_days() {
        return for_x_days;
    }
}
