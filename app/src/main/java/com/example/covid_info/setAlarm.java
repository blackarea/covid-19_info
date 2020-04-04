package com.example.covid_info;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class setAlarm {
    private static int alarmCycle_HOUR = 0;
    private static int alarmCycle_MINUTE = 3;
    private static int alarmCycle_SECOND = 0;
    public static int cnt = 0;
    // 알림 주기 설정

    public static void setAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, receiveAlarm.class);
        PendingIntent sendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();

        calendar.add(calendar.HOUR, alarmCycle_HOUR);
        calendar.add(calendar.MINUTE, alarmCycle_MINUTE);
        calendar.add(calendar.SECOND, alarmCycle_SECOND);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sendingIntent);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd kk:mm:ss");
            // 알람에 설정된 시간을 해당 형식으로 포맷
        String setTargetTime = format.format(new Date(calendar.getTimeInMillis()));

        Toast myToast = Toast.makeText(context, "업데이트 일자 : " + setTargetTime, Toast.LENGTH_LONG);
        myToast.show();
        cnt++;

    }

}

