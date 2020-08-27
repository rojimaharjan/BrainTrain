package com.robotz.braintrain.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity(tableName = "alarm_table", foreignKeys = @ForeignKey(entity = Medication.class,
        parentColumns = "id",
        childColumns = "medicationId",
        onDelete = ForeignKey.CASCADE))
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int medicationId;
    private int alarm_time_hour;
    private int alarm_time_minute;

    @ColumnInfo(defaultValue = "false")
    private Boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Alarm(int medicationId, int alarm_time_hour, int alarm_time_minute) {
        this.medicationId = medicationId;
        this.alarm_time_hour = alarm_time_hour;
        this.alarm_time_minute = alarm_time_minute;
    }

    public int getMedicationId() {
        return medicationId;
    }

    public int getAlarm_time_hour() {
        return alarm_time_hour;
    }
    public int getAlarm_time_minute() {
        return alarm_time_minute;
    }

    public Boolean getDeleted() {
        return deleted;
    }
}