
package com.robotz.braintrain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.robotz.braintrain.Dao.MedicationDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.Medication;

import org.w3c.dom.Text;

import java.sql.SQLOutput;
import java.util.List;


public class HomeFragment extends Fragment {

    MaterialButton AddBtn;
    ImageButton CognitiveBtn, XcogBtn;
    String currentUser ;
    TextView medCount;
    private BrainTrainDatabase connDB;
    private MedicationDao medicationDao;
    int medNumber = 0;
    TextView medicationname;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView uname = view.findViewById(R.id.currentUser);
        medCount = view.findViewById(R.id.MedicationTotal);
        connDB = Room.databaseBuilder(getContext(), BrainTrainDatabase.class, connDB.DBNAME).allowMainThreadQueries().build();
        medicationDao = connDB.medicationDao();
        List<Medication> medications = medicationDao.getAllMedications();
        medNumber = medications.size();
        String medicationAvailable = Integer.toString(medNumber) + " Medication saved";
        medCount.setText(medicationAvailable);
        medicationname = view.findViewById(R.id.MedicationName);
        medicationname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new MedicationFragment(), "",  true);
            }
        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("app", Context.MODE_PRIVATE);
        currentUser = sharedPreferences.getString("currentUser", "");
        uname.setText(currentUser);
        ((NavigationHost) getActivity()).setUpToolbar(view);

        AddBtn = view.findViewById(R.id.AddMed_button);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to medication fragment
                ((NavigationHost) getActivity()).navigateTo(new MedicationNameFragment(), "Medication",  true);
            }
        });


        CognitiveBtn = view.findViewById(R.id.CognitiveTesting);
        CognitiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), GameSceneActivity.class));
            }
        });

//        getActivity().moveTaskToBack(true);
//        getActivity().finish();
        return view;
    }

//    private void setUpToolbar(View view) {
//        Toolbar toolbar = view.findViewById(R.id.app_bar);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        if (activity != null) {
//            activity.setSupportActionBar(toolbar);
//        }
//
//
//        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
//                getContext(),
//                view.findViewById(R.id.homefragment),
//                new AccelerateDecelerateInterpolator(),
//                getContext().getResources().getDrawable(R.drawable.shr_menu), // Menu open icon
//                getContext().getResources().getDrawable(R.drawable.shr_close_menu))); // Menu close icon
//
//
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.shr_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }



}