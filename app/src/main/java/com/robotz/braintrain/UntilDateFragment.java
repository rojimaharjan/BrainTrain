
package com.robotz.braintrain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UntilDateFragment extends Fragment implements DurationFragment.onDateChangedListener{

    TextView dateValue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.until_date, container, false);
        dateValue = view.findViewById(R.id.dateTxt);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());
        dateValue.setText((CharSequence) dateFormat.format(date));
        return  view;
    }

    @Override
    public void setDateValue(String date) {
        CharSequence value = date;
        dateValue.setText(value);
    }


   /* public void setDateValue(String date) {
        CharSequence value = date;
        dateValue.setText(value);
    }*/
}