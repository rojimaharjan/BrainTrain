package com.robotz.braintrain.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "frequency_table", foreignKeys = @ForeignKey(entity = Medication.class,
        parentColumns = "id",
        childColumns = "medicationId",
        onDelete = ForeignKey.CASCADE))
public class Frequency {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int medicationId;
    private int x_times_a_day;
    private int every_x_hour;
    private int every_x_days;
    private String specific_days_of_week;
    private int x_days_active;
    private int y_days_inactive;
    private Date cycle_start_day;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Frequency(int medicationId, int x_times_a_day, int every_x_hour, int every_x_days, String specific_days_of_week, int x_days_active, int y_days_inactive, Date cycle_start_day) {
        this.medicationId = medicationId;
        this.x_times_a_day = x_times_a_day;
        this.every_x_hour = every_x_hour;
        this.every_x_days = every_x_days;
        this.specific_days_of_week = specific_days_of_week;
        this.x_days_active = x_days_active;
        this.y_days_inactive = y_days_inactive;
        this.cycle_start_day = cycle_start_day;
    }

    public int getMedicationId() {
        return medicationId;
    }

    public int getX_times_a_day() {
        return x_times_a_day;
    }

    public int getEvery_x_hour() {
        return every_x_hour;
    }

    public int getEvery_x_days() {
        return every_x_days;
    }

    public String getSpecific_days_of_week() {
        return specific_days_of_week;
    }

    public int getX_days_active() {
        return x_days_active;
    }

    public int getY_days_inactive() {
        return y_days_inactive;
    }

    public Date getCycle_start_day() {
        return cycle_start_day;
    }
}
