package com.robotz.braintrain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class ForXDaysFragment extends Fragment{

    TextView days;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.for_x_days, container, false);
        days = view.findViewById(R.id.daysTxt);
        return view;
    }


    public void ChangeDayValue(String day) {
        days.setText(day);
    }
/*
    @Override
    public void setDayValue(int day) {
        days.setText(day);
    }*/
}