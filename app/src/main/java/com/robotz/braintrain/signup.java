package com.robotz.braintrain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.robotz.braintrain.Dao.UserDao;
import com.robotz.braintrain.Dao.UserInfoDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.User;
import com.robotz.braintrain.Entity.UserInfo;

import static android.text.TextUtils.concat;

public class signup extends AppCompatActivity implements SignUpFNFragment.onFragmentContinued, SignUpLNFragment.onFragmentMNContinued, SignUpDOBFragment.onFragmentDOBContinued, SignUpDiagnosisFragment.onFragmentDContinued {
    public static final String EXTRA_FATHERSNAME ="com.robotz.braintrain.EXTRA_FATHERSNAME";
    public static final String EXTRA_MOTHERSNAME ="com.robotz.braintrain.EXTRA_MOTHERSNAME";
    public static final String EXTRA_DOB ="com.robotz.braintrain.EXTRA_DOB";
    public static final String EXTRA_DIAGNOSIS ="com.robotz.braintrain.EXTRA_DIAGNOSIS";

    private UserDao userDao;
    private UserInfoDao userInfoDao;
    private CharSequence FathersFirstName;
    private CharSequence MothersMaidenName;
    private CharSequence DateOfBirth;
    private CharSequence Diagnosis;
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.signupContainer, new SignUpFNFragment())
                    .commit();
        }
        userDao = Room.databaseBuilder(this, BrainTrainDatabase.class, "user").allowMainThreadQueries().build().userDao();
        userInfoDao = Room.databaseBuilder(this, BrainTrainDatabase.class, "userInfo").allowMainThreadQueries().build().userInfoDao();
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
        Toast.makeText(this, ""+FathersFirstName+ "  "+ MothersMaidenName +" "+ dob , Toast.LENGTH_LONG).show();

    }

    @Override
    public void diagnosis(CharSequence Diagnosis) {
        Diagnosis = Diagnosis;
        CharSequence username = createUsername();
        User user = new User(FathersFirstName.toString(), MothersMaidenName.toString(), username.toString());
        userId = userDao.insert(user);
        Toast.makeText(this, userId + " " +username+ "  "+ Diagnosis, Toast.LENGTH_LONG).show();
        UserInfo userInfo = new UserInfo(userId.intValue(), Diagnosis.toString());
//        userInfo.setUserId(userId.intValue());
        userInfoDao.insert(userInfo);
//        SaveUser();
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