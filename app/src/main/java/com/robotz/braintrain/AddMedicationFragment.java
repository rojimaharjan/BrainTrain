package com.robotz.braintrain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

public class AddMedicationFragment extends Fragment implements FrequencyFragment.onDataTransferListener {

    LinearLayout durationLL, frequencyLL, reminderLL;
    MaterialCheckBox asneededCheckbox;
    MaterialButton savebtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_medication, container, false);

        durationLL = view.findViewById(R.id.DurationLayout);
        frequencyLL = view.findViewById(R.id.FrequencyLayout);
        reminderLL = view.findViewById(R.id.ReminderLayout);
        TextView medicationName = view.findViewById(R.id.MedicationNameTxt);
        asneededCheckbox = (MaterialCheckBox) view.findViewById(R.id.asneeded);
        savebtn =view.findViewById(R.id.save_button);


                asneededCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked =((MaterialCheckBox) v).isChecked();
                if(checked)
                {
                    reminderLL.setVisibility(View.INVISIBLE);


                }
                else {
                    reminderLL.setVisibility(View.VISIBLE);
                }

            }
        });


        durationLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new DurationFragment(), "Duration", true);
            }
        });

        frequencyLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new FrequencyFragment(), "Frequency", true);
            }
        });

        medicationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new MedicationDetailFragment(), "Medication Details", true);
            }
        });
        return view;
    }

    @Override
    public void frequencyData(String frequency, String Subfrequency) {
        Toast.makeText(getContext(), frequency + " " + Subfrequency, Toast.LENGTH_SHORT).show();
    }
}