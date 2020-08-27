package com.robotz.braintrain.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.robotz.braintrain.Entity.Duration;
import com.robotz.braintrain.Repository.DurationRepository;

import java.util.List;

public class DurationViewModel extends AndroidViewModel {

    private DurationRepository durationRepository;
    private LiveData<List<Duration>> allDurations;
    public DurationViewModel(@NonNull Application application) {
        super(application);
        durationRepository = new DurationRepository(application);
        allDurations = durationRepository.getAllDurations();
    }

    public void insert(Duration duration){
        durationRepository.insert(duration);;
    }

    public void update(Duration duration){
        durationRepository.update(duration);;
    }


    public void delete(Duration duration){
        durationRepository.delete(duration);
    }

    public void deleteAllDurations(){
        durationRepository.deleteAllDurations();
    }

    public LiveData<List<Duration>> getAllDurations(){
        return allDurations;
    }
}
