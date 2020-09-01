package com.robotz.braintrain;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.robotz.braintrain.Entity.Frequency;


public class FrequencyFragment extends Fragment implements NumberPicker.OnValueChangeListener {

    private onDataTransferListener Listener;
    String Frequency;
    String SubFrequency;
    Boolean active, inactive;
    RadioGroup radioFrequency;
    RelativeLayout frequencyL;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frequency, container, false);
        radioFrequency = view.findViewById(R.id.radiofrequency);
        frequencyL = view.findViewById(R.id.frequencysetting);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Listener.frequencyData("Frequency", "SubFrequency");
            }
        };

        radioFrequency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                switch (i){
                    case R.id.dailyXtimesaday:
                        for (Fragment fragment : getChildFragmentManager().getFragments()) {
                            getChildFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                        Frequency = "dailyXtimesaday";
                        SubFrequency = null;
                        break;
                    case R.id.dailyeveryXhours:
                        for (Fragment fragment : getChildFragmentManager().getFragments()) {
                            getChildFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                        Frequency = "dailyeveryXhours";
                        SubFrequency = null;
                        break;

                    case R.id.everyXdays:
                        final ForXDaysFragment forXDaysFragment = new ForXDaysFragment();
                        FragmentTransaction trans = getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frequencysetting, forXDaysFragment);
                        trans.commit();
                        frequencyL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                active = inactive = false;
                                showNumberPicker(v);
                            }
                        });
                        TextView daytxt =  getActivity().findViewById(R.id.daysTxt);
                        Frequency = daytxt.toString();
                        break;
                    case R.id.specificdaysofweek:
                        /*final ForXDaysFragment  = new ForXDaysFragment();
                        FragmentTransaction trans = getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frequencysetting, forXDaysFragment);
                        trans.commit();
                        frequencyL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });*/
                        break;
                    case R.id.cycle:
                        final CycleFragment cycleFragment = new CycleFragment();
                        FragmentTransaction cycleFragmentTransaction = getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frequencysetting, cycleFragment);
                        cycleFragmentTransaction.commit();
                        frequencyL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()){
                                    case R.id.daysactive:
                                        active = true;
                                        inactive = false;
                                        showNumberPicker(v);
                                }
                            }
                        });
                        TextView activetxt =  getActivity().findViewById(R.id.daysactiveTxt);
                        TextView incativetxt =  getActivity().findViewById(R.id.daysactiveTxt);
                        Frequency = activetxt.toString();
                        SubFrequency = incativetxt.toString();
                        break;

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
        CharSequence d = Integer.toString(day) + "day(s)";
        if(active){
            TextView daytxt =  getActivity().findViewById(R.id.daysactiveTxt);
            daytxt.setText(d);
        }
        else if(inactive){
            TextView daytxt =  getActivity().findViewById(R.id.daysactiveTxt);
            daytxt.setText(d);
        }
        else {
            TextView daytxt =  getActivity().findViewById(R.id.daysTxt);
            daytxt.setText(d);
        }
        return;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FrequencyFragment.onDataTransferListener){
            Listener = (onDataTransferListener) context;
        }else {
            throw  new RuntimeException(context.toString()
                    +"must implement FragmentListner");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Listener = null;
    }

    public interface onDataTransferListener{
        void frequencyData(String frequency, String Subfrequency);
    }
}