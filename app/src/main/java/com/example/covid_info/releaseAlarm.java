package com.example.covid_info;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class releaseAlarm {
    public static void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent cancelIntent = new Intent(context, MainActivity.class);
        PendingIntent cancelSender = PendingIntent.getBroadcast(context, 0, cancelIntent, 0);
        alarmManager.cancel(cancelSender);
    }
}