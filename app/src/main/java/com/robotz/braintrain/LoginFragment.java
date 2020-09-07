package com.robotz.braintrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.robotz.braintrain.Dao.UserDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.User;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.System.in;

public class LoginFragment extends Fragment {
    private UserDao userDao;
    MaterialCheckBox remember;
    MaterialButton nextButton, signupButton , downloadButton;
    TextInputEditText passwordEditText;
    Boolean rememberMe = false;
    private BrainTrainDatabase connDB;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.username_text_input);
        passwordEditText = view.findViewById(R.id.username_edit_text);
        downloadButton = view.findViewById(R.id.download_button);

        userDao = Room.databaseBuilder(getActivity(), BrainTrainDatabase.class, connDB.DBNAME).allowMainThreadQueries().build().userDao();
        nextButton = view.findViewById(R.id.signIn_button);
        signupButton = view.findViewById(R.id.signUp_button);
        remember = view.findViewById(R.id.rememberme);
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("app", MODE_PRIVATE);
        if (sharedPreferences.getString("Username", null) != null) {
            String username =""+sharedPreferences.getString("Username", "");
            passwordEditText.setText(username);
        }

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                rememberMe = true;
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*((MainActivity)getActivity()).verifyStoragePermissions(getActivity());
                ((MainActivity)getActivity()).requestSignIn();*/
                System.out.println("empty function");

            }
        });
        // Set an error if the password is less than 8 characters.
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPasswordValid(passwordEditText.getText().toString())) {
                    passwordTextInput.setError(getString(R.string.error_username));
                } else {
                    passwordTextInput.setError(null); // Clear the error
//save in preference
                    SaveInSharedPreference(rememberMe);
                    ((NavigationHost) getActivity()).navigateTo(new HomeFragment(),passwordEditText.getText().toString(),  false); // Navigate to the next Fragment
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), signup.class);
                startActivity(intent);

            }
        });

        // Clear the error once more than 8 characters are typed.
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(passwordEditText.getText().toString())) {
                    passwordTextInput.setError(null); //Clear the error
                }
                return false;
            }
        });
        return view;
    }

    private void SaveInSharedPreference(boolean isChecked) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("RememberMe", isChecked);
        editor.putString("currentUser", passwordEditText.getText().toString() );
        editor.apply();
    }


    private boolean isPasswordValid(@Nullable String text) {
        boolean isValid = false;

        if(text != null)
        {
            List<User> users = userDao.getAllUsers();
            Log.d("Users ", " "+ users);
            for (User u :
                 users) {
                String un = u.getUsername();
                boolean equal = un.equals(text);
                if(equal){
                    isValid = true;
                    return isValid;
                }
            }

            isValid = false;
        }
        return isValid;
    }

/*
        @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity)getActivity()).findViewById(R.id.layout_frag).setVisibility(View.GONE);
//            ((AppCompatActivity)getActivity()).findViewById(R.id.container_frag).setVisibility(View.GONE);

    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).findViewById(R.id.layout_frag).setVisibility(View.VISIBLE);
//        ((AppCompatActivity)getActivity()).findViewById(R.id.container_frag).setVisibility(View.VISIBLE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }*/
}
