
package com.robotz.braintrain;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.sql.SQLOutput;


public class HomeFragment extends Fragment {

    MaterialButton AddBtn;
    ImageButton CognitiveBtn, XcogBtn;
    String currentUser ;



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

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("app", Context.MODE_PRIVATE);
        currentUser = sharedPreferences.getString("currentUser", "");
        uname.setText(currentUser);
        ((NavigationHost) getActivity()).setUpToolbar(view);

        AddBtn = view.findViewById(R.id.AddMed_button);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to medication fragment
                ((NavigationHost) getActivity()).navigateTo(new MedicationFragment(), "Medication",  true);
            }
        });

        CognitiveBtn = view.findViewById(R.id.CognitiveTesting);
        CognitiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to cognitive testing game
            }
        });



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