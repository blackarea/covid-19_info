package com.example.covid_info;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {


    private ImageButton btn_map,btn_foreign,btn_notice,btn_med,btn_info;

    private long Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //메인액티비티에대한 설정






        btn_map=(ImageButton)findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });//btn_map id가 있는 버튼 입력시 MapActivity로 이동
        btn_info=(ImageButton)findViewById(R.id.btn_info);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                startActivity(intent);
            }
        });//btn_info id가 있는 버튼 입력시 InfoActivity로 이동
        btn_med=(ImageButton)findViewById(R.id.btn_med);
        btn_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MedActivity.class);
                startActivity(intent);
            }
        });//btn_med id가 있는 버튼 입력시 MedActivity로 이동
        btn_foreign=(ImageButton)findViewById(R.id.btn_foreign);
        btn_foreign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForeignActivity.class);
                startActivity(intent);
            }
        });//btn_foreign id가 있는 버튼 입력시 ForeignActivity로 이동
        btn_notice=(ImageButton)findViewById(R.id.btn_notice);
        btn_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NoticeActivity.class);
                startActivity(intent);
            }
        });//btn_notice id가 있는 버튼 입력시 NoticeActivity로 이동


    }
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-Back<2000){
            finish();
        }
        Toast.makeText(this,"뒤로가기 버튼을 한번더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        Back=System.currentTimeMillis();
    }

}
