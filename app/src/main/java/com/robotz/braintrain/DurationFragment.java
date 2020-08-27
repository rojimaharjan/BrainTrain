package com.robotz.braintrain;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.picker.MaterialDatePickerDialogFragment;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.Calendar;
import java.util.Date;

public class DurationFragment extends Fragment implements DatePickerDialog.OnDateSetListener, NumberPicker.OnValueChangeListener {
    //    DatePickerDialog.OnDateSetListener start_dateListener, until_datelistener;
//    int start_year, start_month, start_day, until_year, until_month, until_day;
    boolean isStartDate, isEndDate;
    private onDateChangedListener listener;
//    private onDaysChangedListener listener;

    public CharSequence dateValue;

    public interface onDaysChangedListener {
        void setDayValue(int day);
    }

    public interface onDateChangedListener {
        void setDateValue(String date);
    }

    String Duration;
    String SubDuration;
    TextView startdate;
    Toolbar toolbar;
    RadioGroup radiodurationbtn;
    RelativeLayout durationExtraL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_duration, container, false);
        radiodurationbtn = view.findViewById(R.id.radioduration);
        durationExtraL = view.findViewById(R.id.durationExtra);
        startdate = view.findViewById(R.id.StartDateTxt);

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDailog();
                isEndDate = false;
                isStartDate = true;

            }
        });


        radiodurationbtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                switch (i) {
                    case R.id.unitldate:
                        final UntilDateFragment untilDateFragment = new UntilDateFragment();
                        FragmentTransaction transaction = getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.durationExtra, untilDateFragment);
                        transaction.commit();
                        durationExtraL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                untildatedialog();
                                showDatePickerDailog();
                                isStartDate = false;
                                isEndDate = true;

                            }
                        });
                        break;
                    case R.id.forXdays:
                        /*durationExtraL.removeAllViews();
                        durationExtraL.addView(layoutInflater.inflate(R.layout.for_x_days, null,false));*/
                        final ForXDaysFragment forXDaysFragment = new ForXDaysFragment();
                        FragmentTransaction trans = getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.durationExtra, forXDaysFragment);
                        trans.commit();
                        durationExtraL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showNumberPicker(v);

                            }
                        });
                        break;
                    case R.id.noenddate:
                        for (Fragment fragment : getChildFragmentManager().getFragments()) {
                            getChildFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                }
            }
        });


        return view;
    }

    private void showNumberPicker(View v) {
        daysOfMonth numberPicker = new daysOfMonth();
        numberPicker.setValueChangeListener(this);
        numberPicker.show(getChildFragmentManager(), "time picker");

    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        int day = i1;
        TextView daytxt =  getActivity().findViewById(R.id.daysTxt);
        daytxt.setText(Integer.toString(day));
        return;
    }


    private void showDatePickerDailog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    public void onDateSet(DatePicker datePicker, int year, int month, int daysOfMonth) {
        dateValue = daysOfMonth + "/" + month + "/" + year;
//        startdate.setText(dateValue.toString());
        if (isStartDate) {
            startdate.setText(dateValue);
            return;
        }else if(isEndDate)
        {
            TextView tct =  getActivity().findViewById(R.id.dateTxt);
            tct.setText(dateValue);
            return;
        }
    }


}