package com.robotz.braintrain.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.robotz.braintrain.Entity.Alarm;
import com.robotz.braintrain.Repository.AlarmRepository;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {

    private AlarmRepository alarmRepository;
    private LiveData<List<Alarm>> allAlarms;
    public AlarmViewModel(@NonNull Application application) {
        super(application);
        alarmRepository = new AlarmRepository(application);
        allAlarms = alarmRepository.getAllAlarms();
    }

    public void insert(Alarm alarm){
        alarmRepository.insert(alarm);;
    }

    public void update(Alarm alarm){
        alarmRepository.update(alarm);;
    }


    public void delete(Alarm alarm){
        alarmRepository.delete(alarm);
    }

    public void deleteAllAlarms(){
        alarmRepository.deleteAllAlarms();
    }

    public LiveData<List<Alarm>> getAllAlarms(){
        return allAlarms;
    }
}
