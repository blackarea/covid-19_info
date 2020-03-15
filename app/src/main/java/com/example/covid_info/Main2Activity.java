package com.example.covid_info;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.alarmBtn);
        button.setOnClickListener(new View.OnClickListener() {      // 클릭 이벤트 구현
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) Main2Activity.this.getSystemService(Main2Activity.this.NOTIFICATION_SERVICE);
                // notification을 사용하기 위한 객체 채널 ID가 동일하면 notifiy()를 통해 알림 내용을 전달
                Intent intent1 = new Intent(Main2Activity.this, Main2Activity.class);
                // 인텐트 생성. 액티비티에서 다른 액티비티로 전환할때 사용
                Notification.Builder builder = new Notification.Builder(getApplicationContext());
                // 알림을 만들어내는 builder 객체 생성
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // 액티비티 중복을 방지하기/관리 (최상위 액티비티 호출시 최상위 액티비티 재사용 | 호출하는 액티비티를 최상위로 올리고 그 위의 액티비티 모두 제거)
                PendingIntent pendingNotificationIntent = PendingIntent.getActivity(Main2Activity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                // 인텐트와 용도는 같으나 특정한 순간에 액티비티를 이동
                builder.setSmallIcon(R.drawable.ic_launcher_foreground).setTicker("HETT").setWhen(System.currentTimeMillis())
                        .setContentTitle("푸쉬 제목").setContentText("푸쉬내용")
                        .setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);
                // 알림 메세지 설정(푸시알림 아이콘, 알림이 발생할때 나오는 글자, 알림이 발생한 시간(현재시간), 제목, 내용
                // setContentIntent는 푸시알림을 클릭했을때 액티비티가 전환되게 함
                /*  API 26이상 작동
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
                */
                notificationManager.notify(1, builder.build()); // 알림 출력
            }
        });
    }
}