//E:\Masters\Game\CTGLib\CTG\build\outputs\aar\CTG-debug.aar

package com.robotz.braintrain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonArray;
import com.robotz.braintrain.Dao.MedicationDao;
import com.robotz.braintrain.Dao.ScoreDao;
import com.robotz.braintrain.Dao.UserDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.Medication;
import com.robotz.braintrain.Entity.Score;
import com.robotz.braintrain.Entity.User;
import com.robotz.braintrain.ViewModel.MedicationViewModel;
import com.robotz.braintrain.ViewModel.UserViewModel;
import com.unity3d.player.UnityPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.robotz.braintrain.ViewModel.UserViewModel;

public class MainActivity extends AppCompatActivity implements NavigationHost{
    public static final int ADD_MED_REQUEST =1;
    private static final String TAG = "MainActivity";
    Toolbar toolbar;
    private UserViewModel userViewModel;
    SharedPreferences sharedPreferences;
    String currentUser;
    private MedicationDao medicationDao;
    public static String GameData, gamePlayed;



    //    gdrive api
//    public String uploadedFiledId;
    GoogleDriverServiceHelper googleDriverServiceHelper;
    private BrainTrainDatabase connDB;
    private String uploadedFileID;
    private UserDao userDao;
    private ScoreDao scoreDao;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    DatabaseReference gameScoreRef;
    AlarmManager alarmManager;

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("app", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("RememberMe", false)!= false) {
            currentUser = sharedPreferences.getString("currentUser", "");
            savedInstanceState.putString("userInfo", currentUser);
//            Toast.makeText(this, "instance saved", Toast.LENGTH_SHORT).show();
        }

    }

    public void repeatAlarm() {
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        final int id = (int) System.currentTimeMillis();
        intent.putExtra("message", "It's time to play game.");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 07);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 	PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);


    /*    alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        final int id = (int) System.currentTimeMillis();
        System.out.println(at.getTime()+"medicationtimeadded");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, at.getTimeInMillis(),24*60*60*1000, pendingIntent);
*/

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
//        requestSignIn();

        boolean mboolean = false;
        sharedPreferences = this.getSharedPreferences("app", MODE_PRIVATE);
        mboolean = sharedPreferences.getBoolean("FIRST_RUN", false);
        if (!mboolean) {
            repeatAlarm();
            sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("FIRST_RUN", true);
            editor.commit();
        }
//        scheduleJob(findViewById(android.R.id.content).getRootView());


        connDB = Room.databaseBuilder(this, BrainTrainDatabase.class, connDB.DBNAME).allowMainThreadQueries().build();
        userDao = connDB.userDao();
        scoreDao = connDB.scoreDao();

        sharedPreferences = this.getSharedPreferences("app", MODE_PRIVATE);

        if(sharedPreferences.getBoolean("RememberMe", false) != false) {
            currentUser = sharedPreferences.getString("currentUser", "");
//            savedInstanceState.putString("userInfo", currentUser);
        }


        gamePlayed = sharedPreferences.getString("GamePlayed", "");
        System.out.println(gamePlayed);
        try {
            if (gamePlayed.equals("yes")) {
                if (sharedPreferences.getString("GameDataArray", null) != null) {
                    Log.d("JsonArray Output", ""+ sharedPreferences.getAll());
                    String st = ""+sharedPreferences.getString("GameDataArray", "");
                    JSONArray gameDataArray = new JSONArray(st);
                    SaveInDatabase(gameDataArray);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(gamePlayed, "no");
                    editor.remove("GameDataArray");
                    editor.apply();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        boolean isNull = (sharedPreferences.getBoolean("rememeberme", ));

        if (currentUser == "" || currentUser == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
        else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new HomeFragment(), "home")
                    .commit();

//            String message = savedInstanceState.getString("UserInfo");
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        }

    }

/*    public void scheduleJob(View v) {
        ComponentName componentName = new ComponentName(this, NotificationJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(false)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(10 * 1000)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }
    public void cancelJob(View v) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancelled");
    }*/
    //save game score here
    private void SaveInDatabase(JSONArray gameDataArray) {
        User userid = userDao.currentuserid(currentUser);
        if(gameDataArray != null)
        {
            try {
                for(int i = 0; i < gameDataArray.length(); i++){
                    JSONObject cv = gameDataArray.getJSONObject(i);
                    final Date currentTime = Calendar.getInstance().getTime();
                    final int userId = userid.getUserId();
                    final String gameName = cv.getString("gameId");
                    final String totalTime = cv.getString("totalTime");
                    final String totalScore = cv.getString("totalScore");
                    final String totalError = cv.getString("totalError");
                    final String playedDate = cv.getString("playedTime");
//                    Score score = new Score(userId, gameName, totalScore, totalTime, totalError, playedDate);
                    Long scoreId= scoreDao.insert(userId, gameName, totalScore, totalTime, totalError, playedDate);
                    gameScoreRef = myRef.child(currentUser).child("Score").child(scoreId.toString());
                    Map<String, String> gameScoreMap = new HashMap<>();
                    gameScoreMap.put("game_name", gameName);
                    gameScoreMap.put("totalTime", totalTime);
                    gameScoreMap.put("totalScore", totalScore);
                    gameScoreMap.put("totalError", totalError);
                    gameScoreMap.put("playedDate", playedDate);
                    gameScoreRef.setValue(gameScoreMap);
                    Toast.makeText(this, "Score Saved", Toast.LENGTH_SHORT).show();


                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shr_toolbar_menu, menu);
        return true;
    }

    @Override
    public void navigateTo(Fragment fragment, String title, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment, title);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
//        toolbar.setTitle(title);
    }

    public void gotoFragment(View view) {
        switch (view.getId()) {
            case (R.id.home):
                navigateTo(new HomeFragment(), "home", true);
                break;
            case (R.id.medication):
                navigateTo(new MedicationFragment(), "medication", true);
                break;

            case (R.id.userinfo):
                navigateTo(new UserInfoFragment(), "", true);
                break;

            case (R.id.game):
                navigateTo(new GameFragment(), "", true);
                break;

            case (R.id.licence):
                navigateTo(new LicenceFragment(), "", true);
                break;

            case (R.id.privacypolicy):
                navigateTo(new PrivacyPolicyFragment(), "", true);
                break;

            case (R.id.about):
                navigateTo(new AboutFragment(), "", true);
                break;

            case (R.id.logout):
                navigateTo(new LoginFragment(), "", false);
                sharedPreferences = this.getSharedPreferences("app", MODE_PRIVATE);
                if (sharedPreferences.getBoolean("RememberMe", false)!= true) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("RememberMe");
                    editor.remove("currentUser");
                    editor.apply();
                }
                break;
        }

    }

    public void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) MainActivity.this;
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }


        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                MainActivity.this,
                view.findViewById(R.id.mainlayout),
                new AccelerateDecelerateInterpolator(),
                MainActivity.this.getResources().getDrawable(R.drawable.shr_menu), // Menu open icon
                MainActivity.this.getResources().getDrawable(R.drawable.shr_close_menu))); // Menu close icon



    }

    public void setUpBackBtn(View view){
        Toolbar backbtn = view.findViewById(R.id.app_bar);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(new HomeFragment(), "home", false);
            }
        });
    }

    public void setUpBackBtnAddMed(View view){
        Toolbar backbtn = view.findViewById(R.id.app_bar);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*navigateTo(new AddMedicationFragment(), "home", false);*/
                getFragmentManager().popBackStackImmediate();
            }
        });
    }





    //    gdrive upload
    public void requestSignIn() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);
        startActivityForResult(client.getSignInIntent(), 400);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 400:
                if (resultCode == RESULT_OK) {
                    handleSignIntent(data);
                }
                break;
        }
    }

    private void handleSignIntent(Intent data) {
        GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        GoogleAccountCredential credential = GoogleAccountCredential.
                                usingOAuth2(MainActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
                        credential.setSelectedAccount(googleSignInAccount.getAccount());

                        Drive googleDriveService = new Drive.Builder(
                                AndroidHttp.newCompatibleTransport(),
                                new GsonFactory(),
                                credential)
                                .setApplicationName("BrainTrain GD Backup")
                                .build();

                        googleDriverServiceHelper = new GoogleDriverServiceHelper(googleDriveService);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void backupDB(View view) {
        userDao.checkpoint(new SimpleSQLiteQuery("pragma wal_checkpoint(full)"));
        String filePath = this.getDatabasePath(connDB.DBNAME).getAbsolutePath();
        currentUser = sharedPreferences.getString("currentUser", "");

        if(!isConnected()){
            Toast.makeText(this, "Please connect to the internet.", Toast.LENGTH_SHORT).show();
            return;
        }
        googleDriverServiceHelper.callUploader(filePath).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                myRef = database.getReference("users").child(currentUser).getRef();
                DatabaseReference googleFileUploadedId = myRef.child("googleFileUploadedId");
                googleFileUploadedId.setValue(s);

                Toast.makeText(getApplicationContext(), connDB.DBNAME+" is uploaded successfully", Toast.LENGTH_LONG).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), filePath+ "\n check you gdrive api key", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void downloadDB(String userName) throws FileNotFoundException {

//        requestSignIn();
//        String fileId = "11RIqrEladA8uwaSB_4rUz7bmU10EeVQmdd";
        String filePath = this.getDatabasePath(connDB.DBNAME).getAbsolutePath();
        System.out.println("user is: "+userName);

        DatabaseReference ref = database.getReference("users").child(userName).getRef();

    // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("googleFileUploadedId").exists()){
                    uploadedFileID = dataSnapshot.child("googleFileUploadedId").getValue().toString();
                    System.out.println(uploadedFileID);
                    System.out.println(filePath);
                }else {
                    System.out.println("usernot avaiable");
                    Toast.makeText(getApplicationContext(), uploadedFileID+"Backup not available", Toast.LENGTH_LONG).show();


                }

                restoreDBCheck();
                try {
                    googleDriverServiceHelper.callDownload(uploadedFileID, filePath).addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {
//                            System.out.println(s);
                            soutUsers();
                            Toast.makeText(getApplicationContext(), uploadedFileID+" Data is downloaded successfully", Toast.LENGTH_LONG).show();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), uploadedFileID+" please check your ID or gd api key", Toast.LENGTH_LONG).show();
                                }
                            });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), uploadedFileID+"Backup not available", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }



    public void restoreDBCheck() {
        //        deleteing exiting db
        File databases = new File(this.getApplicationInfo().dataDir+"/databases");
        File db = new File(databases, connDB.DBNAME);
        File db_shm = new File(databases, connDB.DBNAME+"-shm");
        File db_wal = new File(databases, connDB.DBNAME+"-wal");

        if(db.exists()) {
            if(db.delete()) {
                if (db_shm.exists() && db_wal.exists()) {
                    db_shm.delete();
                    db_wal.delete();
                }
                if (!this.getDatabasePath(connDB.DBNAME).exists()) {
    //                Toast.makeText(this, "DB file deleted", Toast.LENGTH_SHORT).show();
                }
        }
        } else {
            Toast.makeText(this, "unable to delete DB file", Toast.LENGTH_SHORT).show();
        }
    }

//    public void moveDB () throws IOException {
//        //   copying db from internal storage to app db location
//        String moveFrom = "/storage/emulated/0/"+connDB.DBNAME;
//        String moveTo = this.getDatabasePath(connDB.DBNAME).getPath();
////        String moveTo = "/storage/emulated/0/BrainTrain/"+connDB.DBNAME;
//
////        Files.copy
//        File src = new File(moveFrom);
//        File dest = new File(moveTo);
//
//        InputStream is = null;
//        OutputStream os = null;
//        try {
//            is = new FileInputStream(src);
//            os = new FileOutputStream(dest);
//            // buffer size 1K
//            byte[] buf = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = is.read(buf)) > 0) {
//                os.write(buf, 0, bytesRead);
//            }
//        } finally { is.close(); os.close(); }
//
//        if (!is.equals(null) && !os.equals(null) && this.getDatabasePath(connDB.DBNAME).exists()) {
//            Toast.makeText(this, "DB restored/moved successfully", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Couldn't move db file", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    public void soutUsers() {

        List<User> users = userDao.getAllUsers();
        for (User u:users) {
            String prn = "usersName: "+u.getUsername()+
                    " fatherName: "+u.getFathers_first_name()+
                    " motherName: +"+u.getMothers_maiden_name()+"\n";
            System.out.println(prn);
            Toast.makeText(this, prn, Toast.LENGTH_SHORT).show();
        }
    }


    public void verifyStoragePermissions(Activity activity) {

        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {

                //Manifest.permission.READ_EXTERNAL_STORAGE,f
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permission = ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public void goToHome(){
        navigateTo(new HomeFragment(), "home", false);
    }

    public void CallFromUnity(Context con, String abc) {
        Log.d("UnityCalled", abc);
        GameData = abc;
        try {
            JSONArray jsonArray=new JSONArray(abc);
            SharedPreferences sharedPreferences = con.getSharedPreferences("app", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("GameDataArray", jsonArray.toString());
            editor.putString("GamePlayed", "yes");
            editor.apply();

//            mUnityPlayer.quit();
            UnityPlayer.currentActivity.finish();
            Intent intent = new Intent(con, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            con.startActivity(intent);

//            navigateTo(new HomeFragment(), "", false);
//            SaveInDatabase(con, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

 /*   private Boolean exit = false;
    @Override
    public void onBackPressed() {

        if (exit) {
            finish(); // finish activity
        } else if() {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }*/
}



//C:\Users\subash\Documents\AndroidStudio\DeviceExplorer\motorola-moto_g_7__play-ZY323X35WB\data\data\com.robotz.braintrain