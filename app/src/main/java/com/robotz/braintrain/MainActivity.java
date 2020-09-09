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

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
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
import com.robotz.braintrain.Dao.MedicationDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.Medication;
import com.robotz.braintrain.ViewModel.MedicationViewModel;
import com.robotz.braintrain.ViewModel.UserViewModel;

import java.io.FileNotFoundException;
import java.util.Collections;

//import com.robotz.braintrain.ViewModel.UserViewModel;

public class MainActivity extends AppCompatActivity implements NavigationHost, AddMedicationFragment.saveMedicationData{
    public static final int ADD_MED_REQUEST =1;
    Toolbar toolbar;
    private UserViewModel userViewModel;
    SharedPreferences sharedPreferences;
    String currentUser;
    private MedicationDao medicationDao;

//    gdrive api
    public String uploadedFiledId;
    GoogleDriverServiceHelper googleDriverServiceHelper;
    private BrainTrainDatabase connDB;


    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("app", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("rememberme", true)) {
            currentUser = sharedPreferences.getString("currentUser", "");
            savedInstanceState.putString("userInfo", currentUser);
//            Toast.makeText(this, "instance saved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*requestSignIn();*/
        sharedPreferences = this.getSharedPreferences("app", MODE_PRIVATE);
//        boolean isNull = (sharedPreferences.getBoolean("rememeberme", ));
        if(sharedPreferences.getBoolean("rememeberme", false)!= false) {
            currentUser = sharedPreferences.getString("currentUser", "");
//            savedInstanceState.putString("userInfo", currentUser);
        }
        if (currentUser == "" || currentUser == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
        else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new HomeFragment())
                    .commit();

//            String message = savedInstanceState.getString("UserInfo");
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        }

        connDB = Room.databaseBuilder(this, BrainTrainDatabase.class, connDB.DBNAME).allowMainThreadQueries().build();
//        medicationViewModel.getAllMedications();

        /*toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ProductGridFragment frag  = new ProductGridFragment();
        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                MainActivity.this,
                findViewById(R.id.container_frag),
                new AccelerateDecelerateInterpolator(),
                MainActivity.this.getResources().getDrawable(R.drawable.shr_menu), // Menu open icon
                MainActivity.this.getResources().getDrawable(R.drawable.shr_close_menu))); // Menu close icon*/


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
                        .replace(R.id.container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
//        toolbar.setTitle(title);
    }

    public void gotoFragment(View view) {
        switch (view.getId()) {
            case (R.id.medication):
                navigateTo(new MedicationFragment(), "", true);
                /*Intent intent = new Intent(MainActivity.this, AddMedicationFragment.class);
                startActivityForResult(intent, ADD_MED_REQUEST);*/
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
                if (sharedPreferences.getBoolean("RememberMe", false)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
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



/*@Override
    public void setDateValue(String date) {
        Fragment forXDaysFragment = new ForXDaysFragment();
        ((ForXDaysFragment) forXDaysFragment).ChangeDayValue(date);
    }*/
/*    @Override
    public void setDateValue(String date) {
        Fragment untildate = new UntilDateFragment();
        ((UntilDateFragment) untildate).setDateValue(date);
    }*/




/*    @Override
    public void setDayValue(int day) {
        forXDaysFragment.ChangeDayValue(day);
    }*/

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_MED_REQUEST && resultCode == RESULT_OK){
            String medName = data.getStringExtra(AddMedicationFragment.EXTRA_MEDICATIONNAME);
            String type = data.getStringExtra(AddMedicationFragment.EXTRA_TYPE);
            String startDate = data.getStringExtra(AddMedicationFragment.EXTRA_TYPE);
            boolean asneeded = data.getBooleanExtra(AddMedicationFragment.EXTRA_ASNEEDED, false);
            String duration = data.getStringExtra(AddMedicationFragment.EXTRA_DURATION);
            String durationTime = data.getStringExtra(AddMedicationFragment.EXTRA_DURATIONTIME);
            String frequency = data.getStringExtra(AddMedicationFragment.EXTRA_FREQUENCY);
            String frequencyTime = data.getStringExtra(AddMedicationFragment.EXTRA_FREQUENCYTIME);
            Medication medication = new Medication(1, medName, type, asneeded);
            AsyncTask<Medication, Void, Long> id = medicationViewModel.insert(medication);
            Toast.makeText(this, "id" + id, Toast.LENGTH_SHORT).show();
            if(!asneeded)
            {
                Toast.makeText(this, "id" + asneeded, Toast.LENGTH_SHORT).show();
            }


        }
        else{
            Toast.makeText(this, "not saved", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public void getMedicationData(String medName, String type, boolean asNeeded) {
        medicationDao = Room.databaseBuilder(this, BrainTrainDatabase.class, "main_database").allowMainThreadQueries().build().medicationDao();
        Medication medication = new Medication(1, medName, type, asNeeded);
        Long id = medicationDao.insert(medication);
        Toast.makeText(this, "medication saved"  , Toast.LENGTH_SHORT).show();
    }


}