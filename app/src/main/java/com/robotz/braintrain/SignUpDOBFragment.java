package com.robotz.braintrain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpDOBFragment extends Fragment {

    private SignUpDOBFragment.onFragmentDOBContinued listner;

    private EditText dateOfBirth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_d_o_b, container, false);

        MaterialButton continueButton = view.findViewById(R.id.continue_button);
        MaterialButton cancelButton = view.findViewById(R.id.cancel_button);
        dateOfBirth = view.findViewById(R.id.dob_edit_text);
        final TextInputLayout DOBTextInput = view.findViewById(R.id.dob_text_input);


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validName(dateOfBirth.getText().toString())) {
                    CharSequence dob = dateOfBirth.getText();
                    listner.dob(dob);
                    SignUpDiagnosisFragment diagnosisfrag = new SignUpDiagnosisFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.signupContainer, diagnosisfrag)
                            .addToBackStack(null)
                            .commit();
                }else{
                    DOBTextInput.setError(getString(R.string.error_invalidDOB));
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private boolean validName(String text) {
        int dob = 0;
        boolean date;
        if(text != null && text.length() >= 4){
            dob = Integer.parseInt(text);
            if(dob >= 1920 || dob <= 2002){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SignUpDOBFragment.onFragmentDOBContinued){
            listner = (SignUpDOBFragment.onFragmentDOBContinued) context;
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

    public interface onFragmentDOBContinued{
        void dob(CharSequence dob);
    }
}