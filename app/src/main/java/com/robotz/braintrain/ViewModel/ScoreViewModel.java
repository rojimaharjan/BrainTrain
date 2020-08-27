package com.robotz.braintrain.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.robotz.braintrain.Entity.Score;
import com.robotz.braintrain.Repository.ScoreRepository;
import com.robotz.braintrain.Repository.ScoreRepository;

import java.util.List;

public class ScoreViewModel extends AndroidViewModel {

    private ScoreRepository scoreRepository;
    private LiveData<List<Score>> allScores;
    public ScoreViewModel(@NonNull Application application) {
        super(application);
        scoreRepository = new ScoreRepository(application);
        allScores = scoreRepository.getAllScores();
    }

    public void insert(Score score){
        scoreRepository.insert(score);;
    }

    public void update(Score score){
        scoreRepository.update(score);;
    }


    public void delete(Score score){
        scoreRepository.delete(score);
    }

    public void deleteAllScores(){
        scoreRepository.deleteAllScores();
    }

    public LiveData<List<Score>> getAllScores(){
        return allScores;
    }
}
