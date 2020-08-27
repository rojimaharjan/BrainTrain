package com.robotz.braintrain;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.radiobutton.MaterialRadioButton;

public class MedicationDetailFragment extends Fragment implements UnitDialog.SingleChoiceListener {

    private TextView UnitTxt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_medication_detail, container, false);
        UnitTxt = view.findViewById(R.id.UnitTxt);
        LinearLayout lt = view.findViewById(R.id.unit);

        lt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DialogFragment unitDialog = new UnitDialog();
                unitDialog.setCancelable(false);
                unitDialog.setTargetFragment(MedicationDetailFragment.this, 1);
                unitDialog.show(getFragmentManager(), "Unit");


            }
        });
        return view;
    }


    @Override
    public void onPositiveButtonClicked(String[] list, int position){
        UnitTxt.setText(list[position]);
        Toast.makeText(getContext(), "here", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeButtonClicked() {
        UnitTxt.setText("pill(s)");
    }

}