package com.robotz.braintrain.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.robotz.braintrain.Dao.FrequencyDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.Frequency;

import java.util.List;

public class FrequencyRepository {

    private FrequencyDao frequencyDao;
    private LiveData<List<Frequency>> allFrequencies;
    public FrequencyRepository(Application application){
        BrainTrainDatabase database = BrainTrainDatabase.getInstance(application);
        frequencyDao = database.frequencyDao();
        allFrequencies = frequencyDao.getAllFrequencies();

    }

    public void insert(Frequency frequency){

        new FrequencyRepository.InsertFrequencyAsyncTask(frequencyDao).execute(frequency);
    }
    public void update(Frequency frequency){
        new FrequencyRepository.UpdateFrequencyAsyncTask(frequencyDao).execute(frequency);
    }
    public void delete(Frequency frequency){
        new FrequencyRepository.DeleteFrequencyAsyncTask(frequencyDao).execute(frequency);
    }

    public void deleteAllFrequencies(){
        new FrequencyRepository.DeleteAllFrequenciesAsyncTask(frequencyDao).execute();
    }

    public LiveData<List<Frequency>> getAllFrequencies(){
        return allFrequencies;

    }


    private static class InsertFrequencyAsyncTask extends AsyncTask<Frequency, Void, Void> {
        private FrequencyDao frequencyDao;

        private InsertFrequencyAsyncTask(FrequencyDao frequencyDao){
            this.frequencyDao = frequencyDao;
        }
        @Override
        protected Void doInBackground(Frequency... frequencies) {
            frequencyDao.insert(frequencies[0]);
            return null;
        }
    }

    private static class UpdateFrequencyAsyncTask extends AsyncTask<Frequency, Void, Void> {
        private FrequencyDao frequencyDao;

        private UpdateFrequencyAsyncTask(FrequencyDao frequencyDao){
            this.frequencyDao = frequencyDao;
        }
        @Override
        protected Void doInBackground(Frequency... frequencies) {
            frequencyDao.update(frequencies[0]);
            return null;
        }
    }

    private static class DeleteFrequencyAsyncTask extends AsyncTask<Frequency, Void, Void> {
        private FrequencyDao frequencyDao;

        private DeleteFrequencyAsyncTask(FrequencyDao frequencyDao){
            this.frequencyDao = frequencyDao;
        }
        @Override
        protected Void doInBackground(Frequency... frequencies) {
            frequencyDao.delete(frequencies[0]);
            return null;
        }
    }

    private static class DeleteAllFrequenciesAsyncTask extends AsyncTask<Void, Void, Void> {
        private FrequencyDao frequencyDao;

        private DeleteAllFrequenciesAsyncTask(FrequencyDao frequencyDao){
            this.frequencyDao = frequencyDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            frequencyDao.deleteAllFrequencies();
            return null;
        }
    }
}
