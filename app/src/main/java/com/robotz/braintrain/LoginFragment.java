package com.robotz.braintrain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.robotz.braintrain.Dao.UserDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.User;

import java.io.FileNotFoundException;
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
    private String backUpUserName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.username_text_input);
        passwordEditText = view.findViewById(R.id.username_edit_text);
        downloadButton = view.findViewById(R.id.download_button);

        connDB = Room.databaseBuilder(getActivity(), BrainTrainDatabase.class, connDB.DBNAME).allowMainThreadQueries().build();
        userDao = connDB.userDao();


        nextButton = view.findViewById(R.id.signIn_button);
        signupButton = view.findViewById(R.id.signUp_button);
        remember = view.findViewById(R.id.rememberme);
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("app", MODE_PRIVATE);
        if (sharedPreferences.getString("currentUser", null) != null) {
            String currentUser =""+sharedPreferences.getString("currentUser", "");
            passwordEditText.setText(currentUser);
        }

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                rememberMe = true;
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                /*((MainActivity)getActivity()).verifyStoragePermissions(getActivity());
                ((MainActivity)getActivity()).requestSignIn();*/
//                getUserName();
                backUpUserName = passwordEditText.getText().toString();
                if(backUpUserName != null) {
                    try {
                        ((MainActivity) getActivity()).downloadDB(backUpUserName);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

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

    private void getUserName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Username");

// Set up the input
        final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                backUpUserName = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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
        userDao.checkpoint(new SimpleSQLiteQuery("pragma wal_checkpoint(full)"));
        return isValid;
    }


}
