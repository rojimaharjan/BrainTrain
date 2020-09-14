package com.robotz.braintrain;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String alarmId = "alarmId";
    public static final String alarmName = "alarmName";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            createAlarms();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createAlarms() {
        NotificationChannel alarm = new NotificationChannel(alarmId, alarmName, NotificationManager.IMPORTANCE_DEFAULT);
        alarm.enableLights(true);
        alarm.enableVibration(true);
        alarm.setLightColor(R.color.colorPrimary);
        alarm.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(alarm);

    }

    public NotificationManager getManager(){
     if(mManager == null){
         mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

     }
     return mManager;
    }

   /* public NotificationCompat.Builder getAlarmNotificaiton(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), alarmId)
                .setContentTitle(title)
                .setContentText(message);
    }*/

    public NotificationCompat.Builder getAlarmNotificaiton(){
        return new NotificationCompat.Builder(getApplicationContext(), alarmId)
                .setContentTitle("Medication Alert")
                .setSmallIcon(R.drawable.icons8_alarm_32);
    }


}
