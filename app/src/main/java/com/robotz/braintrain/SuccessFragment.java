package com.robotz.braintrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class SuccessFragment extends Fragment {

    private String username;
    MaterialButton gotoSignin;
    TextView yourUsername;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_success, container, false);
        yourUsername = view.findViewById(R.id.usernameOutput);
        gotoSignin = view.findViewById(R.id.goToSignIn_button);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("app", MODE_PRIVATE);
        if (sharedPreferences.getString("currentUser", null) != null) {
            String username =""+sharedPreferences.getString("currentUser", "");
            yourUsername.setText(username);
        }

        Toast.makeText(getActivity(), "Please remember your username!",Toast.LENGTH_LONG).show();

        gotoSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
//                this.addToBackStack(null);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

//                ((NavigationHost) getActivity()).navigateTo(new LoginFragment(),"",  false);
            }
        });

        return view;
    }




    }
