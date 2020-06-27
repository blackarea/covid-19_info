package com.example.covid_info;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class receiveAlarm extends BroadcastReceiver {
    String text1 = ((MainActivity)MainActivity.context).text1;
    public void onReceive(Context context, Intent intent){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
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
        //Notification.Builder builder = new Notification.Builder(context,"alarm_channel_id");
        // API 26이상 빌더
        Notification.Builder builder = new Notification.Builder(context,"alarm_channel_id");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setContentTitle("확진자 수가 업데이트 되었습니다").setContentText("V V V V V").setContentIntent(receivingIntent)
                .setAutoCancel(true).setOngoing(true).setStyle(new Notification.InboxStyle()
                .addLine("서울 :" + text1 +"명").addLine("서울 :" + text1 +"명").addLine("서울 :" + text1 +"명"));
        notificationManager.notify(1, builder.build());
        // API 16이상 필수
    }
}
