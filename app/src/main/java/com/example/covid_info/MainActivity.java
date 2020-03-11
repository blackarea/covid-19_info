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

    Button btn_map,btn_situation,btn_info,btn_med,btn_foreign,btn_notice;   //버튼 변수 설정
    DrawerLayout drawerLayout;  //DrawerLayout 변수설정
    View drawerView;    //drawerView 변수 설정
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //메인액티비티에대한 설정


        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);  //DrawerLayout 에 대한 id 설정
        drawerView = (View)findViewById(R.id.drawer);   //DrawerView 에 대한 id 설정

        Button btn_open = (Button)findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });//btn_open id가 있는 버튼을 입력했을 때 일어나는 일 설정
        Button btn_close = (Button)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });//btn_close id가 있는 버튼을 입력했을떄 일어나는 일 설정

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

        drawerLayout.setDrawerListener(listener);//drawerLayout 리스너 등록
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });//drawerView에대한 리스너 등록







    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };//DrawerLayout의 상태값에 따른 Listener설정
}
