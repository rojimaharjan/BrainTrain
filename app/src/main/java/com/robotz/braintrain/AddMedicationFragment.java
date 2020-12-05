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
import androidx.room.RoomDatabase;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robotz.braintrain.Dao.AlarmDao;
import com.robotz.braintrain.Dao.DurationDao;
import com.robotz.braintrain.Dao.FrequencyDao;
import com.robotz.braintrain.Dao.MedicationDao;
import com.robotz.braintrain.Dao.UserDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.Alarm;
import com.robotz.braintrain.Entity.Duration;
import com.robotz.braintrain.Entity.Frequency;
import com.robotz.braintrain.Entity.Medication;
import com.robotz.braintrain.Entity.User;
import com.robotz.braintrain.ViewModel.MedicationViewModel;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;


public class AddMedicationFragment extends Fragment implements UnitDialog.SingleChoiceListener{

    private saveMedicationData listener;

/*    @Override
    public void onDeleteClick(int position) {
        removeItem(position);
    }

    private void removeItem(int position) {
        Toast.makeText(getActivity(), ""+ position, Toast.LENGTH_SHORT).show();
    }*/

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
    private AlarmDao alarmDao;
    public BrainTrainDatabase connDB;
    List<Calendar> alarmList = new ArrayList<>();
    private String currentUser;
    public Medication currentMedication;
    String medStartDate;
    boolean isEditing = false;
    boolean edited= false;

    Calendar mcurrentTime, AT;

    private AlarmManager alarmManager;
    private static Intent alarmIntent;
    private static PendingIntent pendingAlarmIntent;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    DatabaseReference medicationRef;
    private UserDao userDao;
    public String MN, sD, d, dt, f, ft, type;
    public String edit= new String();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_medication, container, false);
        connDB = Room.databaseBuilder(getContext(), BrainTrainDatabase.class, connDB.DBNAME).allowMainThreadQueries().build();


        medicationDao = connDB.medicationDao();
        frequencyDao = connDB.frequencyDao();
        durationDao = connDB.durationDao();
        alarmDao = connDB.alarmDao();

        //get the views from templete
        durationLL = view.findViewById(R.id.DurationLayout);
        frequencyLL = view.findViewById(R.id.FrequencyLayout);
        reminderLL = view.findViewById(R.id.ReminderLayout);
        medicationName = view.findViewById(R.id.MedicationNameTxt);
        pills = view.findViewById(R.id.pills);
        duration = view.findViewById(R.id.DurationTxt);
        frequency = view.findViewById(R.id.FrequencyTxt);
        alarmTxtView = view.findViewById(R.id.alarm);

        //get value from shared preference
        sharedPreferences = getContext().getSharedPreferences("app", MODE_PRIVATE);
        asneededCheckbox = (MaterialCheckBox) view.findViewById(R.id.asneeded);
        savebtn = view.findViewById(R.id.save_button);

        currentUser = sharedPreferences.getString("currentUser", "");

        /*try{
            edit = getArguments().getString("edit");
        } catch(Exception ex){
            edit = "false";
        }*/


        if(!isEditing) {
            edit = getArguments().getString("edit");
            edited = Boolean.parseBoolean(edit);
        }

        if(edited) {
            edited = false;
            isEditing = true;
            String position = getArguments().getString("currentMedication");
            int currentPosition = Integer.parseInt(position);
            currentMedication = medicationDao.getMedication(currentPosition);
            MN = currentMedication.getMed_name().toString();
            type = currentMedication.getType().toString();
            pills.setText(type);
            boolean asNeeded = currentMedication.isAs_needed();
            //if as needed is clicked
            if(!asNeeded){
                Duration currentDuration = durationDao.getDuration(currentPosition);
                if(currentDuration != null){
                    d = currentDuration.getDuration_type();
                    dt= currentDuration.getDuration_time();
                }

                Frequency currentFrequency = frequencyDao.getFrequency(currentPosition);
                if(currentFrequency != null){
                    f = currentFrequency.getFrequency_type();
                    ft = currentFrequency.getFrequency_time();
                }

                List<Alarm> currentAlarmList = alarmDao.getcurrentAlarms(currentPosition);
                if(currentAlarmList.size() >0){
                    for(int i = 0; i < currentAlarmList.size(); i++)
                    {
                        TextView alarmText;
                        View alarm = LayoutInflater.from(getActivity()).inflate(R.layout.alarm, null);
                        alarmText =  alarm.findViewById(R.id.alarmTime);
                        int h = currentAlarmList.get(i).getAlarm_time_hour();
                        int m = currentAlarmList.get(i).getAlarm_time_minute();
                        Calendar c = Calendar.getInstance();
                        c.set(c.HOUR_OF_DAY, h);
                        c.set(c.MINUTE, m);
                        String time = h + ":"+m;
                        DisplayAlarm(c);
                    }

                }
            }else{
                asneededCheckbox.setChecked(true);
                reminderLL.setVisibility(View.INVISIBLE);
            }

        }else{
            MN = getArguments().getString("MedName");
            sD = sharedPreferences.getString("StartDate", "");
            d = sharedPreferences.getString("Duration", "");
            dt = sharedPreferences.getString("SubDuration", "");
            f = sharedPreferences.getString("Frequency", "");
            ft = sharedPreferences.getString("SubFrequency", "");
        }


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

        if(sD != ""){
            medStartDate = sD;
        }else{
            mcurrentTime = Calendar.getInstance();
            medStartDate = mcurrentTime.getTime().toString();
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
                        DisplayAlarm(AT);
//                        setAlarm(AT);
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

                boolean aN = asNeeded;
                String type = pills.getText().toString();
                String dur = duration.getText().toString();
                String durT = durationTime;
                String freT = frequencyTime;
                String fre = frequency.getText().toString();

                userDao = connDB.userDao();
                User user = userDao.currentuserid(currentUser);
                Medication medication = new Medication(user.getUserId(), medN, type, asNeeded);
//                long medId= medication.insert


                //*****medication edit start****************8//
                if(edit.equals("true")){
                    int medId = currentMedication.getId();
                    medicationDao.updateMedication(medN, type, asNeeded, medId);
                    DatabaseReference medicationRef = myRef.child(currentUser).child("medication").child(String.valueOf(medId));
                    Map<String, String> medicationMap = new HashMap<>();
                    medicationMap.put("medication_name", medN);
                    medicationMap.put("type", type);
                    medicationMap.put("asNeeded", Boolean.toString(asNeeded));
                    medicationMap.put("isDeleted", "false");
                    medicationRef.setValue(medicationMap);

                    if(!asNeeded){
                        Duration currentduration = durationDao.getDuration(medId);
                        durationDao.updateDuration(medStartDate, dur, durT, medId);
                        DatabaseReference durationRef = medicationRef.child("currentduration");
                        Map<String, String> durationMap = new HashMap<>();
                        durationMap.put("start_date", sD);
                        durationMap.put("duration_type", dur);
                        durationMap.put("duration_time", durT);
                        durationRef.setValue(durationMap);


                        DatabaseReference frequencyRef = medicationRef.child("frequency");
                        Map<String, String> frequencyMap = new HashMap<>();
                        frequencyDao.updateFrequency(fre, freT, medId);
                        frequencyMap.put("frequency_type", fre);
                        frequencyMap.put("frequency_time", freT);
                        frequencyRef.setValue(frequencyMap);


                        //delete alarm and save new one

                        List<Alarm> alarms = alarmDao.getcurrentAlarms((int) medId);
                        if(alarms != null){
                            alarmDao.deleteAlarms((int) medId);
                        }

                        int alarmcount = alarmList.size();
                        AddAlarm(medId);



                    }

                    clearSharedPreference();

                }
                else{
                    //save medication data
                    long medid = medicationDao.insert(userDao.currentuserid(currentUser).getUserId(), medN, type, asNeeded);
                    String medicationId =Integer.toString((int) medid);
                    DatabaseReference medicationRef = myRef.child(currentUser).child("medication").child(medicationId);
                    Map<String, String> medicationMap = new HashMap<>();
                    medicationMap.put("medication_name", medN);
                    medicationMap.put("type", type);
                    medicationMap.put("asNeeded", Boolean.toString(asNeeded));
                    medicationMap.put("isDeleted", "false");
                    medicationRef.setValue(medicationMap);


                    if(!aN){
                        Duration duration = new Duration((int) medid, sD, dur, durT);
                        durationDao.insert(duration);
                        DatabaseReference durationRef = medicationRef.child("duration");
                        Map<String, String> durationMap = new HashMap<>();
                        durationMap.put("start_date", sD);
                        durationMap.put("duration_type", dur);
                        durationMap.put("duration_time", durT);
                        durationRef.setValue(durationMap);


                        Frequency frequency = new Frequency((int) medid, fre, freT);
                        DatabaseReference frequencyRef = medicationRef.child("frequency");
                        Map<String, String> frequencyMap = new HashMap<>();
                        frequencyDao.insert(frequency);
                        frequencyMap.put("frequency_type", fre);
                        frequencyMap.put("frequency_time", freT);
                        frequencyRef.setValue(frequencyMap);

                        //                Toast.makeText(getContext(), "medication saved" + (int) id  , Toast.LENGTH_SHORT).show();
                        //save alarm and set alarm
                        AddAlarm(medid);

                    }


                    clearSharedPreference();
                }

                //*********save medication ends//

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

        ((NavigationHost) getActivity()).setUpBackBtn(view);
        return view;
    }

    private void clearSharedPreference() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("StartDate");
        editor.remove("Duration");
        editor.remove("SubDuration");
        editor.remove("Frequency");
        editor.remove("SubFrequency");
        editor.apply();
    }

    private void AddAlarm(long medid) {
        for(int i = 0; i < alarmList.size(); i++ ){
            Calendar at = alarmList.get(i);
            setAlarm((int) medid, at);
        }
    }

    private void DisplayAlarm(Calendar AT) {
        TextView alarmText;
        View alarm = LayoutInflater.from(getActivity()).inflate(R.layout.alarm, null);
        alarmText =  alarm.findViewById(R.id.alarmTime);
//        alarmText.setText(HourFormatted+":"+MinuteFormatted);
        String t = DateFormat.getTimeInstance(DateFormat.SHORT).format(AT.getTime());
        alarmText.setText(t);

        reminderLL.addView(alarm);
        alarmList.add(AT);
        System.out.println(alarmList);
        alarm.findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminderLL.removeViewInLayout(alarm);
                alarmList.remove(AT);
            }
        });

    }

    private void setAlarm(int medid, Calendar at) {
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Medication med = medicationDao.getMedication(medid);
        medName = med.getMed_name();
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        intent.putExtra("message", medName);
        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, at.getTimeInMillis(),24*60*60*1000, pendingIntent);
        if (android.text.format.DateFormat.is24HourFormat(getContext())){
            String hournadminute = DateFormat.getTimeInstance(DateFormat.SHORT).format(AT.getTime());
            String[] hourMin = hournadminute.split(":");
            int hour = Integer.parseInt(hourMin[0]);
            int mins = Integer.parseInt(hourMin[1]);
            Alarm alarm = new Alarm(medid, id, hour, mins);
            alarmDao.insert(alarm);
        }
        else {

            Alarm alarm = new Alarm(medid, id, at.HOUR_OF_DAY, at.MINUTE);
            alarmDao.insert(alarm);
        }



        DatabaseReference alarmRef = myRef.child(currentUser).child("alarm").child(String.valueOf(id));
        Map<String, String> alarmMap = new HashMap<>();
        alarmMap.put("medication_Id", String.valueOf(medid));
        alarmMap.put("time", at.getTime().toString());
        alarmRef.setValue(alarmMap);

    }

    private void cancelAlarm(){
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
        alarmManager.cancel(pendingIntent);
    }


    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        pills.setText(list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {
//        UnitTxt.setText("pill(s)");
    }
}