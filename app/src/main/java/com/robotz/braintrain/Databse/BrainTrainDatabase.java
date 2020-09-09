package com.robotz.braintrain.Databse;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.robotz.braintrain.Dao.AlarmDao;
import com.robotz.braintrain.Dao.DurationDao;
import com.robotz.braintrain.Dao.FrequencyDao;
import com.robotz.braintrain.Dao.MedicationDao;
import com.robotz.braintrain.Dao.ScoreDao;
import com.robotz.braintrain.Dao.UserDao;
import com.robotz.braintrain.Dao.UserInfoDao;
import com.robotz.braintrain.Entity.Alarm;
import com.robotz.braintrain.Entity.Converters;
import com.robotz.braintrain.Entity.Duration;
import com.robotz.braintrain.Entity.Frequency;
import com.robotz.braintrain.Entity.Medication;
import com.robotz.braintrain.Entity.Score;
import com.robotz.braintrain.Entity.User;
import com.robotz.braintrain.Entity.UserInfo;

import static com.google.gson.internal.$Gson$Types.arrayOf;

@Database(entities = {User.class, Score.class, Medication.class, Frequency.class, Duration.class, Alarm.class, UserInfo.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class BrainTrainDatabase extends RoomDatabase {
    
    private static BrainTrainDatabase instance;
    public abstract UserDao userDao();
    public abstract ScoreDao scoreDao();
    public abstract MedicationDao medicationDao();
    public abstract FrequencyDao frequencyDao();
    public abstract DurationDao durationDao();
    public abstract AlarmDao alarmDao();
    public abstract UserInfoDao userInfoDao();
    public static final String DBNAME = "main_database.db";

    private static BrainTrainDatabase INSTANCE;

    public static synchronized BrainTrainDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    BrainTrainDatabase.class, "/storage/emulated/0/"+DBNAME)
                    .setJournalMode(JournalMode.TRUNCATE)
                    .build();
        }
        return INSTANCE ;
    }

    // close database
    public static void destroyInstance(){
        if (INSTANCE.isOpen()) INSTANCE.close();
        INSTANCE = null;
    }


}
