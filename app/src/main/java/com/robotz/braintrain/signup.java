package com.robotz.braintrain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;
import androidx.work.Data;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robotz.braintrain.Dao.UserDao;
import com.robotz.braintrain.Dao.UserInfoDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.User;
import com.robotz.braintrain.Entity.UserInfo;
import com.robotz.braintrain.ViewModel.UserInfoViewModel;
import com.robotz.braintrain.ViewModel.UserViewModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static android.text.TextUtils.concat;

public class signup extends AppCompatActivity implements SignUpFNFragment.onFragmentContinued, SignUpLNFragment.onFragmentMNContinued, SignUpDOBFragment.onFragmentDOBContinued, SignUpDiagnosisFragment.onFragmentDContinued {
 /*   public static final String EXTRA_FATHERSNAME ="com.robotz.braintrain.EXTRA_FATHERSNAME";
    public static final String EXTRA_MOTHERSNAME ="com.robotz.braintrain.EXTRA_MOTHERSNAME";
    public static final String EXTRA_DOB ="com.robotz.braintrain.EXTRA_DOB";
    public static final String EXTRA_DIAGNOSIS ="com.robotz.braintrain.EXTRA_DIAGNOSIS";*/

    private ActionBar mActionBar;
    private UserDao userDao;
    private UserViewModel userViewModel;
    private UserInfoViewModel userInfoViewModel;

    private UserInfoDao userInfoDao;
    private String FathersFirstName;
    private String MothersMaidenName;
    private String DateOfBirth;
    private String Diagnosis;
    Long idForUser;
    GoogleDriverServiceHelper googleDriverServiceHelper;
    private BrainTrainDatabase connDB;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    DatabaseReference userRef;
    Calendar mcurrentTime;
    CharSequence username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*verifyStoragePermissions(this);
        requestSignIn();*/
        setContentView(R.layout.activity_signup);
        setUpToolbar();

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
        FathersFirstName = fathersFN.toString();
    }

    @Override
    public void mothersMN(CharSequence mothersMN) {
        MothersMaidenName = mothersMN.toString();
    }

    @Override
    public void dob(CharSequence dob) {
        DateOfBirth = dob.toString();
        username = createUsername();
        connDB.clearAllTables();
        saveUser();


    }

    private boolean saveUser() {
        String FN = FathersFirstName;
        String MN = MothersMaidenName;
        String DOB = DateOfBirth;
        try {
            FN = encrypt(FN, username);
            MN = encrypt(MN, username);
            DOB = encrypt(DOB, username);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(FN);
        User user = new User(FN, MN, DOB, username.toString());
        idForUser = userDao.insert(FN, MN, DOB, username.toString());


        userRef = myRef.child(username.toString());
        Map<String, User> userMap = new HashMap<>();
        userMap.put("users", user);
        userRef.setValue(userMap);


        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentUser", username.toString());

        editor.apply();
        return true;
    }

    private String encrypt(String Data, CharSequence username) throws Exception {
        SecretKey key = generateKey(username);
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return  encryptedValue;
    }

    private SecretKey generateKey(CharSequence username) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String un = username.toString();
        byte[] bytes = un.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKey secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }

    @Override
    public void diagnosis(CharSequence Diagnosis) {
        Diagnosis = Diagnosis;
        boolean userCreated = saveUser();
        UserInfo userInfo = new UserInfo(idForUser.intValue(), Diagnosis.toString());
        userInfoDao.insert(userInfo);
        connDB.close();
        mcurrentTime = Calendar.getInstance();
        String medStartDate = mcurrentTime.getTime().toString();
        DatabaseReference userInfoRef = userRef.child("userInfo");
        Map<String, String> userInfoMap = new HashMap<>();
        userInfoMap.put("diagnosis", Diagnosis.toString());
        userInfoMap.put("created_on", medStartDate);
        userInfoRef.setValue(userInfoMap);

    }

    private CharSequence createUsername() {
        String mmn = MothersMaidenName;
        CharSequence fn = FathersFirstName.substring(0,4);
        CharSequence mn = mmn.substring(mmn.length()-4);
        CharSequence username = concat(fn, mn, DateOfBirth);
        return username;
    }

    public void setUpToolbar() {
        Toolbar toolbar = this.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) signup.this;
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent = new Intent(signup.this, MainActivity.class);
                                startActivity(intent);

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(signup.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setMessage("Do you want to exit?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

    }
}