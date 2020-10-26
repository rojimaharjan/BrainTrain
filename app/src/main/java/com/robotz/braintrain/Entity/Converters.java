package com.robotz.braintrain.Entity;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters {

    @TypeConverter
    public static Long fromTimestamp(Long value){
        return value == null? null : value;
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null? null : Long.valueOf(Long.toString(Long.parseLong(date.toString())));
    }
}
