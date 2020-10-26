package com.robotz.braintrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.uni.CTG.UnityPlayerActivity;

public class GameSceneActivity extends UnityPlayerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_scene);
        Intent intent = new Intent(GameSceneActivity.this, UnityPlayerActivity.class);

        startActivity(intent);

//        UnityPlayerActivity.UnitySendMessage("SceneLoader", "LoadScene", "NextCorsi");
//        mUnityPlay
//        er.UnitySendMessage("SceneLoader", "LoadScene", "NextCorsi");
    }
}