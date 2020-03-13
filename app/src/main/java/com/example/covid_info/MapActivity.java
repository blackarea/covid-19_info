package com.example.covid_info;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;



import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class MapActivity extends AppCompatActivity {

    Button btn_map,btn_situation,btn_info,btn_med,btn_foreign,btn_notice;   //버튼 변수 설정
    DrawerLayout drawerLayout;  //DrawerLayout 변수설정
    View drawerView;    //drawerView 변수 설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);




    }

}
