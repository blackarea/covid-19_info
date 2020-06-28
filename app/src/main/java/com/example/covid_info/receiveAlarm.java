package com.example.covid_info;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class receiveAlarm extends BroadcastReceiver {
    String loc = ((MainActivity) MainActivity.context).loc_text;
    String loc_plus = ((MainActivity) MainActivity.context).loc_text2;
    // MainActivity에서 파싱된 지역별 수치값을 가져옴
    String loc_name = PopupActivity.location_text;

    // PopupActivity에서 지역 명을 가져옴
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // API 버전이 오레오 이상인 경우 notification 채널이 있어야함
            NotificationChannel notificationChannel =
                    new NotificationChannel(
                            "alarm_channel_id",
                            "알람 테스트",
                            NotificationManager.IMPORTANCE_DEFAULT
                    );
            notificationChannel.setDescription("알람테스트");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent receiveIntent = new Intent(context, MainActivity.class);
        PendingIntent receivingIntent = PendingIntent.getBroadcast(context, 0, receiveIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        //Notification.Builder builder = new Notification.Builder(context);
        // API 26미만 빌더 API 26이상일때 채널ID 필요
        Notification.Builder builder = new Notification.Builder(context, "alarm_channel_id");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setContentTitle("선택된 지역의 확진자 수가 업데이트 되었습니다").setContentText("V V V V V").setContentIntent(receivingIntent)
                .setAutoCancel(true).setOngoing(true).setStyle(new Notification.InboxStyle()
                .addLine(loc_name + " 지역 : " + loc + "명" + "  전날 대비 " + loc_plus));
        // 푸시 알림내 메세지 설정
        notificationManager.notify(1, builder.build());
        // API 16이상 필수
    }
}
