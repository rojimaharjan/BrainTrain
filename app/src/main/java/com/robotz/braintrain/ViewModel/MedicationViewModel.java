package com.robotz.braintrain.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.robotz.braintrain.Repository.MedicationRepository;
import com.robotz.braintrain.Entity.Medication;

import java.util.List;

public class MedicationViewModel extends AndroidViewModel {

    private MedicationRepository medicationRepository;
    private List<Medication> allMedications;
    public MedicationViewModel(@NonNull Application application) {
        super(application);

        medicationRepository = new MedicationRepository(application);
        allMedications = medicationRepository.getAllMedications();
    }

    public AsyncTask<Medication, Void, Long> insert(Medication medication){
        return medicationRepository.insert(medication);
    }

    public void update(Medication medication){
        medicationRepository.update(medication);;
    }


    public void delete(Medication medication){
        medicationRepository.delete(medication);
    }

    public void deleteAllMedications(){
        medicationRepository.deleteAllMedications();
    }

    public List<Medication> getAllMedications(){
        return allMedications;
    }
}
