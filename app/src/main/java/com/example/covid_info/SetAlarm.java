package com.example.covid_info;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SetAlarm extends Activity {
    /* 해당 시간에 알림을 설정 */

    public static void setAlarm(Context context){
        int alarmCycle_HOUR = PopupActivity.hour_text;
        int alarmCycle_MINUTE = PopupActivity.min_text;
        // PopupActivity에서 설정된 시간을 가져옴
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, receiveAlarm.class);
        PendingIntent sendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        // 알림을 설정한 후 ReceiveAlarm으로 넘겨줌
        Calendar calendar = Calendar.getInstance();

        calendar.set(calendar.HOUR_OF_DAY, alarmCycle_HOUR);
        calendar.set(calendar.MINUTE, alarmCycle_MINUTE);
        // 설정된 시간을 캘린더에 세팅
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sendingIntent);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm:ss");
        // 알람에 설정된 시간을 해당 형식으로 포맷
        String setTargetTime = format.format(new Date(calendar.getTimeInMillis()));
        Toast myToast = Toast.makeText(context, "업데이트 일자 : " + setTargetTime, Toast.LENGTH_LONG);
        // 설정된 시간을 토스트 알림으로 나타냄
        myToast.show();
    }

}

