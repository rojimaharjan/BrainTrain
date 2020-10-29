package com.robotz.braintrain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpFNFragment extends Fragment {
    private onFragmentContinued listner;

    private  TextInputLayout usernameTextInput;
    private EditText fathersFirstName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sign_up_f_n, container, false);
        MaterialButton continueButton = view.findViewById(R.id.continue_button);
        final MaterialButton cancelButton = view.findViewById(R.id.cancel_button);
        usernameTextInput = view.findViewById(R.id.firstname_text_input);
        fathersFirstName = view.findViewById(R.id.username_edit_text);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validName(fathersFirstName.getText().toString())){
                    SignUpLNFragment lnfrag = new SignUpLNFragment();
                    CharSequence fathersFN = fathersFirstName.getText();
                    listner.fathersFN(fathersFN);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.signupContainer, lnfrag)
                            .addToBackStack(null)
                            .commit();
                }
                else{
                    usernameTextInput.setError(getString(R.string.error_name));
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
        return text != "" && text != null && text.length() >= 4;
    }

/*
    public void updateEditText(CharSequence newText){
        fathersFirstName.setText(newText);
    }
*/

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SignUpFNFragment.onFragmentContinued){
            listner = (onFragmentContinued) context;
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

    public interface onFragmentContinued{
        void fathersFN(CharSequence fathersFN);
    }
}