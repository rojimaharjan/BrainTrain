package com.robotz.braintrain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CycleFragment extends Fragment implements NumberPicker.OnValueChangeListener {

    boolean isActive;
    boolean isInActive;
    boolean isCycle;
    LinearLayout active, inactive, cycle;
    TextView daysactivetxt, daysinactivetxt, cycledays;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cycle, container, false);
        active = view.findViewById(R.id.active);
        inactive = view.findViewById(R.id.inactive);


        cycle = view.findViewById(R.id.cycle);


        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isActive = true;
                isInActive = isCycle = false;
                ShowNumberPicker();
            }
        });

        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInActive = true;
                isActive= isCycle = false;
                ShowNumberPicker();
            }
        });
        cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isActive= isInActive = false;
                isCycle = true;
                ShowNumberPicker();
            }
        });
        return view;
    }

    private void ShowNumberPicker() {
        daysOfMonth numberPicker = new daysOfMonth();
        numberPicker.setValueChangeListener(this);
        numberPicker.show(getChildFragmentManager(), "time picker");

    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        int day = i1;
        CharSequence d = Integer.toString(day);
        if (isActive){
            daysactivetxt =  getActivity().findViewById(R.id.daysactiveTxt);
            daysactivetxt.setText(d);
        }
        else  if (isInActive){
            daysinactivetxt =  getActivity().findViewById(R.id.daysinactiveTxt);

            daysinactivetxt.setText(d);
        }
        else if(isCycle){
            String a = daysactivetxt.getText().toString();
            String in = daysinactivetxt.getText().toString();
            int totaldays = Integer.parseInt(a) + Integer.parseInt(in);
            if(day <= totaldays){
                TextView daytxt =  getActivity().findViewById(R.id.totalcycleTxt);
                daytxt.setText(d);
            }
            else{
                Toast.makeText(getContext(), "Today cannot be greter than one cycle!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
