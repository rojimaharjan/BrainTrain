package com.robotz.braintrain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.robotz.braintrain.Dao.DurationDao;
import com.robotz.braintrain.Dao.FrequencyDao;
import com.robotz.braintrain.Dao.MedicationDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.Duration;
import com.robotz.braintrain.Entity.Frequency;
import com.robotz.braintrain.Entity.Medication;
import com.robotz.braintrain.ViewModel.MedicationViewModel;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class AddMedicationFragment extends Fragment implements UnitDialog.SingleChoiceListener {
    public static final String EXTRA_MEDICATIONNAME = "com.robotz.braintrain.EXTRA_MEDICATIONNAME";
    public static final String EXTRA_STARTDATE = "com.robotz.braintrain.EXTRA_STARTDATE";
    public static final String EXTRA_TYPE = "com.robotz.braintrain.EXTRA_TYPE";
    public static final String EXTRA_ASNEEDED = "com.robotz.braintrain.EXTRA_ASNEEDED";
    public static final String EXTRA_DURATION = "com.robotz.braintrain.EXTRA_DURATION";
    public static final String EXTRA_DURATIONTIME = "com.robotz.braintrain.EXTRA_DURATIONTIME";
    public static final String EXTRA_FREQUENCYTIME = "com.robotz.braintrain.EXTRA_FREQUENCYTIME";
    public static final String EXTRA_FREQUENCY = "com.robotz.braintrain.EXTRA_FREQUENCY";

    private saveMedicationData listener;

    public interface saveMedicationData{
        void getMedicationData(String medName, String type, boolean asNeeded);
    }

    LinearLayout durationLL, frequencyLL, reminderLL;
    MaterialCheckBox asneededCheckbox;
    MaterialButton savebtn;
    String medName;
    TextView pills;
    TextView duration;
    String durationTime;
    TextView frequency;
    String frequencyTime, startDate;
    EditText medicationName;
    SharedPreferences sharedPreferences;
    boolean asNeeded = false;
    private MedicationViewModel medicationViewModel;
    private MedicationDao medicationDao;
    private FrequencyDao frequencyDao;
    private DurationDao durationDao;
    public BrainTrainDatabase connDB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_medication, container, false);
        connDB = Room.databaseBuilder(getContext(), BrainTrainDatabase.class, connDB.DBNAME).allowMainThreadQueries().build();
        medicationDao = connDB.medicationDao();
        frequencyDao = connDB.frequencyDao();
        durationDao = connDB.durationDao();

        durationLL = view.findViewById(R.id.DurationLayout);
        frequencyLL = view.findViewById(R.id.FrequencyLayout);
        reminderLL = view.findViewById(R.id.ReminderLayout);
        medicationName = view.findViewById(R.id.MedicationNameTxt);
        pills = view.findViewById(R.id.pills);
        duration = view.findViewById(R.id.DurationTxt);
        frequency = view.findViewById(R.id.FrequencyTxt);

        sharedPreferences = getContext().getSharedPreferences("app", MODE_PRIVATE);
        asneededCheckbox = (MaterialCheckBox) view.findViewById(R.id.asneeded);
        savebtn = view.findViewById(R.id.save_button);
        String MN = getArguments().getString("MedName");
        final String sD = sharedPreferences.getString("StartDate", "");
        final String d = sharedPreferences.getString("Duration", "");
        final String dt = sharedPreferences.getString("SubDuration", "");
        final String f = sharedPreferences.getString("Frequency", "");
        final String ft = sharedPreferences.getString("SubFrequency", "");

        ///do the saving process
        if (d != "") {
            duration.setText(d);
        }
        else{
            duration.setText("No end date");
        }

        if (f != "") {
            frequency.setText(f);
        }else{
            frequency.setText("Daily X times a day");
        }

        if (dt != "") {
            durationTime= (dt);
        }
        else{
            durationTime = ("n/a");
        }

        if (ft != "") {
            frequencyTime = (ft);
        }else{
            frequencyTime = ("n/a");
        }

        medicationName.setText(MN);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medN = medicationName.getText().toString();
                SimpleDateFormat d = new SimpleDateFormat("ddMMyyyy");
                Date sDate = null;
                try {
                    sDate = d.parse(sD);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean aN = asNeeded;
                String type = pills.getText().toString();
                String dur = duration.getText().toString();
                String durT = durationTime;
                String freT = frequencyTime;
                String fre = frequency.getText().toString();

//                listener.getMedicationData(medN, type, aN);

                Medication medication = new Medication(1, medN, type, asNeeded);
                Long id = medicationDao.insert(medication);
                if(!aN){
                    Duration duration = new Duration(id.intValue(), sDate, dur, durT);
                    durationDao.insert(duration);
                    Frequency frequency = new Frequency(id.intValue(), fre, freT);
                    durationDao.insert(duration);
                }
                Toast.makeText(getContext(), "medication saved" + id.intValue()  , Toast.LENGTH_SHORT).show();

                ((NavigationHost) getActivity()).navigateTo(new MedicationFragment(), "", false);
                /*Medication medication = new Medication(1, medName, type, aN);
                AsyncTask<Medication, Void, Long> id = medicationViewModel.insert(medication);
                Toast.makeText(getContext(), "id"+ id, Toast.LENGTH_SHORT).show();*/
                /*Intent data = new Intent();
                data.putExtra(EXTRA_MEDICATIONNAME, medN);
                data.putExtra(EXTRA_ASNEEDED, aN);
                data.putExtra(EXTRA_STARTDATE, sDate);
                data.putExtra(EXTRA_TYPE, type);
                data.putExtra(EXTRA_DURATION, dur);
                data.putExtra(EXTRA_DURATIONTIME, durT);
                data.putExtra(EXTRA_FREQUENCY, fre);
                data.putExtra(EXTRA_FREQUENCYTIME, freT);
                getActivity().setResult(Activity.RESULT_OK, data);
                getActivity().finish();*/


            }
        });

        asneededCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((MaterialCheckBox) v).isChecked();
                if (checked) {
                    reminderLL.setVisibility(View.INVISIBLE);
                    asNeeded = true;

                } else {
                    reminderLL.setVisibility(View.VISIBLE);
                    asNeeded = false;
                }

            }
        });


        durationLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new DurationFragment(), "Duration", true);
            }
        });

        frequencyLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new FrequencyFragment(), "Frequency", true);
            }
        });

        pills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((NavigationHost) getActivity()).navigateTo(new MedicationDetailFragment(), "Medication Details", true);
                DialogFragment unitDialog = new UnitDialog();
                unitDialog.setCancelable(false);
                unitDialog.setTargetFragment(AddMedicationFragment.this, 1);
                unitDialog.show(getFragmentManager(), "Unit");
            }
        });

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof AddMedicationFragment.saveMedicationData){
            listener = (saveMedicationData) context;
        }else {
            throw  new RuntimeException(context.toString()
                    +"must implement FragmentListner");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

/*    @Override
    public void frequencyData(String frequency, String Subfrequency) {
        Toast.makeText(getContext(), frequency + " " + Subfrequency, Toast.LENGTH_SHORT).show();
    }*/

/*    @Override
    public void medName(String mn) {
        medName = mn;
    }*/

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        pills.setText(list[position]);
        Toast.makeText(getContext(), "here", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeButtonClicked() {
//        UnitTxt.setText("pill(s)");
    }
}