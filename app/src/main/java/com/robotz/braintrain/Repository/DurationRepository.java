package com.robotz.braintrain.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.robotz.braintrain.Dao.DurationDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.Duration;

import java.util.List;

public class DurationRepository {

    private DurationDao durationDao;
    private LiveData<List<Duration>> allDurations;
    public DurationRepository(Application application){
        BrainTrainDatabase database = BrainTrainDatabase.getInstance(application);
        durationDao = database.durationDao();
        allDurations = durationDao.getAllDurations();

    }

    public void insert(Duration duration){

        new DurationRepository.InsertDurationAsyncTask(durationDao).execute(duration);
    }
    public void update(Duration duration){
        new DurationRepository.UpdateDurationAsyncTask(durationDao).execute(duration);
    }
    public void delete(Duration duration){
        new DurationRepository.DeleteDurationAsyncTask(durationDao).execute(duration);
    }

    public void deleteAllDurations(){
        new DurationRepository.DeleteAllDurationsAsyncTask(durationDao).execute();
    }

    public LiveData<List<Duration>> getAllDurations(){
        return allDurations;

    }


    private static class InsertDurationAsyncTask extends AsyncTask<Duration, Void, Void> {
        private DurationDao durationDao;

        private InsertDurationAsyncTask(DurationDao durationDao){
            this.durationDao = durationDao;
        }
        @Override
        protected Void doInBackground(Duration... durations) {
            durationDao.insert(durations[0]);
            return null;
        }
    }

    private static class UpdateDurationAsyncTask extends AsyncTask<Duration, Void, Void> {
        private DurationDao durationDao;

        private UpdateDurationAsyncTask(DurationDao durationDao){
            this.durationDao = durationDao;
        }
        @Override
        protected Void doInBackground(Duration... durations) {
            durationDao.update(durations[0]);
            return null;
        }
    }

    private static class DeleteDurationAsyncTask extends AsyncTask<Duration, Void, Void> {
        private DurationDao durationDao;

        private DeleteDurationAsyncTask(DurationDao durationDao){
            this.durationDao = durationDao;
        }
        @Override
        protected Void doInBackground(Duration... durations) {
            durationDao.delete(durations[0]);
            return null;
        }
    }

    private static class DeleteAllDurationsAsyncTask extends AsyncTask<Void, Void, Void> {
        private DurationDao durationDao;

        private DeleteAllDurationsAsyncTask(DurationDao durationDao){
            this.durationDao = durationDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            durationDao.deleteAllDurations();
            return null;
        }
    }
}
