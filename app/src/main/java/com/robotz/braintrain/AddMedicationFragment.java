package com.robotz.braintrain;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;


public class AddMedicationFragment extends Fragment implements UnitDialog.SingleChoiceListener {
/*    public static final String EXTRA_MEDICATIONNAME = "com.robotz.braintrain.EXTRA_MEDICATIONNAME";
    public static final String EXTRA_STARTDATE = "com.robotz.braintrain.EXTRA_STARTDATE";
    public static final String EXTRA_TYPE = "com.robotz.braintrain.EXTRA_TYPE";
    public static final String EXTRA_ASNEEDED = "com.robotz.braintrain.EXTRA_ASNEEDED";
    public static final String EXTRA_DURATION = "com.robotz.braintrain.EXTRA_DURATION";
    public static final String EXTRA_DURATIONTIME = "com.robotz.braintrain.EXTRA_DURATIONTIME";
    public static final String EXTRA_FREQUENCYTIME = "com.robotz.braintrain.EXTRA_FREQUENCYTIME";
    public static final String EXTRA_FREQUENCY = "com.robotz.braintrain.EXTRA_FREQUENCY";*/

    private saveMedicationData listener;

    public interface saveMedicationData{
        void getMedicationData(String medName, String type, boolean asNeeded);
    }

    LinearLayout durationLL, frequencyLL, reminderLL;
    MaterialCheckBox asneededCheckbox;
    MaterialButton savebtn;
    String medName;
    TextView pills, alarmTxtView;
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
    List<Calendar> alarmList;

    Calendar mcurrentTime, AT;

    private AlarmManager alarmManager;
    private static Intent alarmIntent;
    private static PendingIntent pendingAlarmIntent;


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
        alarmTxtView = view.findViewById(R.id.alarm);

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

        alarmTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        AT = Calendar.getInstance();
                        AT.set(Calendar.HOUR_OF_DAY, selectedHour);
                        AT.set(Calendar.MINUTE, selectedMinute);
                        AT.set(Calendar.SECOND, 0);
                        //display alarm below
                        AddAlarm(AT);
                        setAlarm(AT);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        medicationName.setText(MN);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medN = medicationName.getText().toString();
                Date sDate = null;
                SimpleDateFormat d = new SimpleDateFormat("ddMMyyyy");
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

//                Medication medication = new Medication(1, medN, type, asNeeded);
                long id = medicationDao.insert(1, medN, type, asNeeded);
                if(!aN){
                    Duration duration = new Duration((int) id, sDate, dur, durT);
                    durationDao.insert(duration);
                    Frequency frequency = new Frequency((int) id, fre, freT);
                    frequencyDao.insert(frequency);
                }
                Toast.makeText(getContext(), "medication saved" + (int) id  , Toast.LENGTH_SHORT).show();

                ((NavigationHost) getActivity()).navigateTo(new MedicationFragment(), "", false);


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

    private void AddAlarm(Calendar AT) {
        TextView alarmText;
        View alarm = LayoutInflater.from(getActivity()).inflate(R.layout.alarm, null);
        alarmText =  alarm.findViewById(R.id.alarmTime);
//        alarmText.setText(HourFormatted+":"+MinuteFormatted);
        String t = DateFormat.getTimeInstance(DateFormat.SHORT).format(AT.getTime());
        alarmText.setText(t);
        reminderLL.addView(alarm);
        alarm.findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminderLL.removeView(v);
                String s = v.toString();
                cancelAlarm();
            }
        });

    }

    private void setAlarm(Calendar at) {

        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, at.getTimeInMillis(), pendingIntent);

    }

    private void cancelAlarm(){
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);

        alarmManager.cancel(pendingIntent);


    }


  /*  @Override
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
    }*/

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