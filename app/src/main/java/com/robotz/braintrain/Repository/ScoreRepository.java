package com.robotz.braintrain.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.robotz.braintrain.Dao.ScoreDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.Score;

import java.util.List;

public class ScoreRepository {
    private ScoreDao scoreDao;
    private LiveData<List<Score>> allScores;
    public ScoreRepository(Application application){
        BrainTrainDatabase database = BrainTrainDatabase.getInstance(application);
        scoreDao = database.scoreDao();
        allScores = scoreDao.getAllScores();

    }

    public void insert(Score score){

        new ScoreRepository.InsertScoreAsyncTask(scoreDao).execute(score);
    }
    public void update(Score score){
        new ScoreRepository.UpdateScoreAsyncTask(scoreDao).execute(score);
    }
    public void delete(Score score){
        new ScoreRepository.DeleteScoreAsyncTask(scoreDao).execute(score);
    }

    public void deleteAllScores(){
        new ScoreRepository.DeleteAllScoresAsyncTask(scoreDao).execute();
    }

    public LiveData<List<Score>> getAllScores(){
        return allScores;

    }


    private static class InsertScoreAsyncTask extends AsyncTask<Score, Void, Void> {
        private ScoreDao scoreDao;

        private InsertScoreAsyncTask(ScoreDao scoreDao){
            this.scoreDao = scoreDao;
        }
        @Override
        protected Void doInBackground(Score... scores) {
            scoreDao.insert(scores[0]);
            return null;
        }
    }

    private static class UpdateScoreAsyncTask extends AsyncTask<Score, Void, Void> {
        private ScoreDao scoreDao;

        private UpdateScoreAsyncTask(ScoreDao scoreDao){
            this.scoreDao = scoreDao;
        }
        @Override
        protected Void doInBackground(Score... scores) {
            scoreDao.update(scores[0]);
            return null;
        }
    }

    private static class DeleteScoreAsyncTask extends AsyncTask<Score, Void, Void> {
        private ScoreDao scoreDao;

        private DeleteScoreAsyncTask(ScoreDao scoreDao){
            this.scoreDao = scoreDao;
        }
        @Override
        protected Void doInBackground(Score... scores) {
            scoreDao.delete(scores[0]);
            return null;
        }
    }

    private static class DeleteAllScoresAsyncTask extends AsyncTask<Void, Void, Void> {
        private ScoreDao scoreDao;

        private DeleteAllScoresAsyncTask(ScoreDao scoreDao){
            this.scoreDao = scoreDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            scoreDao.deleteAllScores();
            return null;
        }
    }

}
