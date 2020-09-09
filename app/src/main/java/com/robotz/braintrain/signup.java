package com.robotz.braintrain;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.robotz.braintrain.Dao.UserDao;
import com.robotz.braintrain.Dao.UserInfoDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.User;
import com.robotz.braintrain.Entity.UserInfo;
import com.robotz.braintrain.ViewModel.UserInfoViewModel;
import com.robotz.braintrain.ViewModel.UserViewModel;

import java.util.Collections;

import static android.text.TextUtils.concat;

public class signup extends AppCompatActivity implements SignUpFNFragment.onFragmentContinued, SignUpLNFragment.onFragmentMNContinued, SignUpDOBFragment.onFragmentDOBContinued, SignUpDiagnosisFragment.onFragmentDContinued {
 /*   public static final String EXTRA_FATHERSNAME ="com.robotz.braintrain.EXTRA_FATHERSNAME";
    public static final String EXTRA_MOTHERSNAME ="com.robotz.braintrain.EXTRA_MOTHERSNAME";
    public static final String EXTRA_DOB ="com.robotz.braintrain.EXTRA_DOB";
    public static final String EXTRA_DIAGNOSIS ="com.robotz.braintrain.EXTRA_DIAGNOSIS";*/

    private UserDao userDao;
    private UserViewModel userViewModel;
    private UserInfoViewModel userInfoViewModel;

    private UserInfoDao userInfoDao;
    private CharSequence FathersFirstName;
    private CharSequence MothersMaidenName;
    private CharSequence DateOfBirth;
    private CharSequence Diagnosis;
    Long idForUser;
    GoogleDriverServiceHelper googleDriverServiceHelper;
    private BrainTrainDatabase connDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*verifyStoragePermissions(this);
        requestSignIn();*/
        setContentView(R.layout.activity_signup);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.signupContainer, new SignUpFNFragment())
                    .commit();
        }

        connDB = Room.databaseBuilder(this, BrainTrainDatabase.class, connDB.DBNAME).allowMainThreadQueries().build();
        /*userDao = Room.databaseBuilder(this, BrainTrainDatabase.class, "main_database").allowMainThreadQueries().build().userDao();
        userInfoDao = Room.databaseBuilder(this, BrainTrainDatabase.class, "main_database").allowMainThreadQueries().build().userInfoDao();*/
        userDao = connDB.userDao();
        userInfoDao = connDB.userInfoDao();

    }





    @Override
    public void fathersFN(CharSequence fathersFN) {
        FathersFirstName = fathersFN;
    }

    @Override
    public void mothersMN(CharSequence mothersMN) {
        MothersMaidenName = mothersMN;
    }

    @Override
    public void dob(CharSequence dob) {
        DateOfBirth = dob;
        CharSequence username = createUsername();
        User user = new User(FathersFirstName.toString(), MothersMaidenName.toString(), username.toString());
        idForUser = userDao.insert(user);
        /*SuccessFragment frag = new SuccessFragment();
        frag.getUsername(username.toString());*/
        Toast.makeText(this, ""+FathersFirstName+ "  "+ MothersMaidenName +" "+ idForUser , Toast.LENGTH_LONG).show();


        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Username", username.toString());
        editor.apply();

    }

    @Override
    public void diagnosis(CharSequence Diagnosis) {
        Diagnosis = Diagnosis;
/*        CharSequence username = createUsername();
        User user = new User(FathersFirstName.toString(), MothersMaidenName.toString(), username.toString());
        idForUser = userDao.insert(user);*/
        UserInfo userInfo = new UserInfo(idForUser.intValue(), Diagnosis.toString());
        userInfoDao.insert(userInfo);
        /*db.close();*/

    }

    private void SaveUser() {
//        CharSequence username = createUsername();
        CharSequence fn = FathersFirstName.subSequence(0,3);
        CharSequence mn = MothersMaidenName.subSequence((MothersMaidenName.length()-3),3);
        CharSequence username = concat(fn, mn, DateOfBirth);

        Toast.makeText(this, ""+username+ "  "+ Diagnosis, Toast.LENGTH_LONG).show();

    }

    private CharSequence createUsername() {
        CharSequence fn = FathersFirstName.subSequence(0,3);
        CharSequence mn = MothersMaidenName.subSequence(0,3);
        CharSequence username = concat(fn, mn, DateOfBirth);
        return username;
    }



}