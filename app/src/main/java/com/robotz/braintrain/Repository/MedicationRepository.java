package com.robotz.braintrain.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.robotz.braintrain.Dao.MedicationDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.Medication;

import java.util.List;

public class MedicationRepository {
    private MedicationDao medicationDao;
    private List<Medication> allMedications;
    public MedicationRepository(Application application){
        BrainTrainDatabase database = BrainTrainDatabase.getInstance(application);
        medicationDao = database.medicationDao();
        allMedications = medicationDao.getAllMedications();

    }

    public AsyncTask<Medication, Void, Long> insert(Medication medication){
        return new MedicationRepository.InsertMedicationAsyncTask(medicationDao).execute(medication);
    }
/*    public void insert(Medication medication){

        new MedicationRepository.InsertMedicationAsyncTask(medicationDao).execute(medication);
    }*/
    public void update(Medication medication){
        new MedicationRepository.UpdateMedicationAsyncTask(medicationDao).execute(medication);
    }
    public void delete(Medication medication){
        new MedicationRepository.DeleteMedicationAsyncTask(medicationDao).execute(medication);
    }

    public void deleteAllMedications(){
        new MedicationRepository.DeleteAllMedicationsAsyncTask(medicationDao).execute();
    }

    public List<Medication> getAllMedications(){
        return allMedications;

    }

    private static class InsertMedicationAsyncTask extends AsyncTask<Medication, Void, Long> {
        private MedicationDao medicationDao;

        private InsertMedicationAsyncTask(MedicationDao medicationDao){
            this.medicationDao = medicationDao;
        }
        @Override
        protected Long doInBackground(Medication... medications) {
            return medicationDao.insert(medications[0]);
//            return null;
        }
    }

    private static class UpdateMedicationAsyncTask extends AsyncTask<Medication, Void, Void> {
        private MedicationDao medicationDao;

        private UpdateMedicationAsyncTask(MedicationDao medicationDao){
            this.medicationDao = medicationDao;
        }
        @Override
        protected Void doInBackground(Medication... medications) {
            medicationDao.update(medications[0]);
            return null;
        }
    }

    private static class DeleteMedicationAsyncTask extends AsyncTask<Medication, Void, Void> {
        private MedicationDao medicationDao;

        private DeleteMedicationAsyncTask(MedicationDao medicationDao){
            this.medicationDao = medicationDao;
        }
        @Override
        protected Void doInBackground(Medication... medications) {
            medicationDao.delete(medications[0]);
            return null;
        }
    }

    private static class DeleteAllMedicationsAsyncTask extends AsyncTask<Void, Void, Void> {
        private MedicationDao medicationDao;

        private DeleteAllMedicationsAsyncTask(MedicationDao medicationDao){
            this.medicationDao = medicationDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            medicationDao.deleteAllMedications();
            return null;
        }
    }
}
