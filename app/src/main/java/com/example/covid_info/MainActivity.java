package com.example.covid_info;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {


    private Button btn_map,btn_situation,btn_info,btn_med,btn_foreign,btn_notice;   //버튼 변수 설정
    private final long FINISH_INTERVAL_TIME = 2000;//2초안에 뒤로가기를 한번 더 누르면 종료되도록 2000설정
    private long backPressedTime =0; //Back키를 눌렀을때 시간 0초 ~2초

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //메인액티비티에대한 설정






        btn_map=(Button)findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"moved successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });//btn_map id가 있는 버튼 입력시 MapActivity로 이동
        btn_situation=findViewById(R.id.btn_situation);
        btn_situation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"moved successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,SituationActivity.class);
                startActivity(intent);
            }
        });//btn_situation id가 있는 버튼 입력시 SituationActivity로 이동
        btn_info=findViewById(R.id.btn_info);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"moved successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                startActivity(intent);
            }
        });//btn_info id가 있는 버튼 입력시 InfoActivity로 이동
        btn_med=findViewById(R.id.btn_med);
        btn_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"moved successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,MedActivity.class);
                startActivity(intent);
            }
        });//btn_med id가 있는 버튼 입력시 MedActivity로 이동
        btn_foreign=findViewById(R.id.btn_foreign);
        btn_foreign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"moved successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,ForeignActivity.class);
                startActivity(intent);
            }
        });//btn_foreign id가 있는 버튼 입력시 ForeignActivity로 이동
        btn_notice=findViewById(R.id.btn_notice);
        btn_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"moved successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,NoticeActivity.class);
                startActivity(intent);
            }
        });//btn_notice id가 있는 버튼 입력시 NoticeActivity로 이동


    }
    @Override
    public void onBackPressed(){
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime -backPressedTime;

        if(0<=intervalTime&&FINISH_INTERVAL_TIME>=intervalTime){
            super.onBackPressed();
        }
        else{
            Toast.makeText(getApplicationContext(),"뒤로버튼을 한번더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

}
