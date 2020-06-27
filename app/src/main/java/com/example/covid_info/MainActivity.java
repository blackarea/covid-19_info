package com.example.covid_info;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton btn_map,btn_notice,btn_mask,btn_vaccine;
    private Button btn_popup;
    private Button btn_fresh;
    private long Back;
    TextView tv1,tv2,tv3,tv4;
    public String text1, text2, text3, text4, text5, text6;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //메인액티비티에대한 설정
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        buttonSet();
        Content c = new Content();
        context = this;
        c.execute();

    }
    public void buttonSet(){
            btn_map = (ImageButton) findViewById(R.id.btn_map);
            btn_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                }
            });//btn_map id가 있는 버튼 입력시 MapActivity로 이동
            btn_notice = (ImageButton) findViewById(R.id.btn_notice);
            btn_notice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                    startActivity(intent);
                }
            });//btn_info id가 있는 버튼 입력시 InfoActivity로 이동
            btn_mask = (ImageButton) findViewById(R.id.btn_mask);
            btn_mask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MaskActivity.class);
                    startActivity(intent);
                }
            });//btn_med id가 있는 버튼 입력시 MedActivity로 이동
            btn_vaccine = (ImageButton) findViewById(R.id.btn_vaccine);
            btn_vaccine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, VaccineActivity.class);
                    startActivity(intent);
                }
            });//btn_notice id가 있는 버튼 입력시 NoticeActivity로 이동
            btn_popup = (Button) findViewById(R.id.btn_popup);
            btn_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                startActivity(intent);
                }
            });


        }

    private class Content extends AsyncTask<Void,Void,Void>{
        ProgressDialog progressDialog;
        String [] parseArray =new String[4];
        String [] parseArray2 = new String[18];

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            int i = 0;
            int j = 0;

            try {

                String url = "http://ncov.mohw.go.kr/";

                //url에 접속
                Document document = Jsoup.connect(url).get();
                //select td라는 요소에 class 속성이 있는 td태그 안에 있는 모든 문자열을 Arraylist 형태로 갖고옴
                Elements links = document.select("ul.liveNum li span.num");
                //Elements는 리스트 형태이므로 Element로 변환하여 하나씩 출력
                Elements links2 = document.select("div.rpsam_graph button span.num");
                for(Element element : links){
                    parseArray[i++]=element.text();
                }
                for(Element element : links2){
                    parseArray2[j++]=element.text();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            tv1.setText("확진자: "+parseArray[0]);
            tv2.setText("완치자: "+parseArray[1]);
            tv3.setText("격리중: "+parseArray[2]);
            tv4.setText("사망자: "+parseArray[3]);

            text1 = tv1.getText().toString();
            text2 = tv2.getText().toString();
            text3 = tv3.getText().toString();
            text4 = tv4.getText().toString();

            text6 = parseArray2[0];

            progressDialog.dismiss();
        }
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
