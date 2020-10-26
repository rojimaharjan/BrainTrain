package com.robotz.braintrain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class SignUpDiagnosisFragment extends Fragment {

    private onFragmentDContinued listner;
    private List<String> diagnosis = new ArrayList<String>();
    ChipGroup chipGroup;
    TextInputEditText diagnosisText;
    MaterialButton saveBtn, cancelBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up_diagnosis, container, false);
        chipGroup = view.findViewById(R.id.chipgroup);
        final TextInputEditText diagnosisText = view.findViewById(R.id.diagnosis_edit_text);
        saveBtn = view.findViewById(R.id.save_button);
        cancelBtn = view.findViewById(R.id.cancel_button);

            diagnosisText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        if ((motionEvent.getRawX() >= (diagnosisText.getRight() - diagnosisText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))) {
                            // your action here
                            String inputText = diagnosisText.getText().toString();
                            if(inputText != "") {
                                diagnosis.add(inputText);
                                Chip chip = new Chip(getActivity());
                                String chipText = diagnosisText.getText().toString();
                                chip.setText(chipText);
                                chip.setCloseIcon(getResources().getDrawable(R.drawable.shr_close_menu));
                                chip.setCloseIconVisible(true);
                                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        chipGroup.removeView(view);
                                    }
                                });

                                chipGroup.addView(chip);
                                diagnosisText.setText("");
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence Diagnosis = diagnosis.toString();
                listner.diagnosis(Diagnosis);
                SuccessFragment successfrag = new SuccessFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.signupContainer, successfrag)
                        .addToBackStack("false")
                        .commit();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

       return  view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SignUpDiagnosisFragment.onFragmentDContinued){
            listner = (SignUpDiagnosisFragment.onFragmentDContinued) context;
        }else {
            throw  new RuntimeException(context.toString()
                    +"must implement FragmentListner");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listner = null;
    }

    public interface onFragmentDContinued{
        void diagnosis(CharSequence diagnosis);
    }




}