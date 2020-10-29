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

public class SignUpLNFragment extends Fragment {

    private onFragmentMNContinued listner;

    private EditText mothersMaidenName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_l_n, container, false);

        MaterialButton continueButton = view.findViewById(R.id.continue_button);
        MaterialButton cancelButton = view.findViewById(R.id.cancel_button);
        mothersMaidenName = view.findViewById(R.id.lastname_edit_text);
        final TextInputLayout mnTextInput = view.findViewById(R.id.lastname_text_input);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validName(mothersMaidenName.getText())) {
                    SignUpDOBFragment DOBfrag = new SignUpDOBFragment();
                    CharSequence mothersMN = mothersMaidenName.getText();
                    listner.mothersMN(mothersMN);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.signupContainer, DOBfrag)
                            .addToBackStack(null)
                            .commit();
                }else{
                    mnTextInput.setError(getString(R.string.error_name));

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

    private boolean validName(Editable text) {
        return text != null && text.length() >= 4;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SignUpLNFragment.onFragmentMNContinued){
            listner = (SignUpLNFragment.onFragmentMNContinued) context;
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

    public interface onFragmentMNContinued{
        void mothersMN(CharSequence mothersMN);
    }
}