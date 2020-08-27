package com.robotz.braintrain.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.robotz.braintrain.Entity.Frequency;
import com.robotz.braintrain.Repository.FrequencyRepository;

import java.util.List;

public class FrequencyViewModel extends AndroidViewModel {

    private FrequencyRepository frequencyRepository;
    private LiveData<List<Frequency>> allFrequencies;
    public FrequencyViewModel(@NonNull Application application) {
        super(application);
        frequencyRepository = new FrequencyRepository(application);
        allFrequencies = frequencyRepository.getAllFrequencies();
    }

    public void insert(Frequency frequency){
        frequencyRepository.insert(frequency);;
    }

    public void update(Frequency frequency){
        frequencyRepository.update(frequency);;
    }


    public void delete(Frequency frequency){
        frequencyRepository.delete(frequency);
    }

    public void deleteAllFrequencies(){
        frequencyRepository.deleteAllFrequencies();
    }

    public LiveData<List<Frequency>> getAllFrequencies(){
        return allFrequencies;
    }
}
