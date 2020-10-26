package com.robotz.braintrain;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.api.client.util.DateTime;
import com.robotz.braintrain.Dao.UserDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserInfoFragment extends Fragment {

    private BrainTrainDatabase connDB;
    private UserDao userDao;
    TextView username, sinceDate;
    String currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        ((NavigationHost) getActivity()).setUpToolbar(view);
        username = view.findViewById(R.id.userName);
        sinceDate = view.findViewById(R.id.detail);


        SharedPreferences sharedPreferences = getContext().getSharedPreferences("app", Context.MODE_PRIVATE);
        currentUser = sharedPreferences.getString("currentUser", "");
        username.setText(currentUser);

        connDB = Room.databaseBuilder(getContext(), BrainTrainDatabase.class, connDB.DBNAME).allowMainThreadQueries().build();
        userDao = connDB.userDao();
        User user = userDao.currentuserid(currentUser);
/*
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MM dd HH:mm:ss z yyyy", Locale.ENGLISH);
//        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        Date date = null;
        try {
            date = dateFormat.parse(user.getCreated_on().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String since = dateFormat.format(user.getCreated_on());

        System.out.println(date +since);*/
        sinceDate.setText(user.getCreated_on().toString());

        return view;
    }
}