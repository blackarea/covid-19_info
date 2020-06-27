package com.example.covid_info;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SetAlarm extends Activity {
    public static int cnt = 0;
    // 알림 주기 설정

    public static void setAlarm(Context context){
        int alarmCycle_HOUR = PopupActivity.hour_text;
        int alarmCycle_MINUTE = PopupActivity.min_text;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, receiveAlarm.class);
        PendingIntent sendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();

        calendar.set(calendar.HOUR_OF_DAY, alarmCycle_HOUR);
        calendar.set(calendar.MINUTE, alarmCycle_MINUTE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sendingIntent);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm:ss");
            // 알람에 설정된 시간을 해당 형식으로 포맷
        String setTargetTime = format.format(new Date(calendar.getTimeInMillis()));
        Toast myToast = Toast.makeText(context, "업데이트 일자 : " + setTargetTime, Toast.LENGTH_LONG);
        myToast.show();
        cnt++;
    }

}

