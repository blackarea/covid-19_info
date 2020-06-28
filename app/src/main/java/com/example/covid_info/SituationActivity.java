package com.example.covid_info;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SituationActivity extends AppCompatActivity {

    private ImageButton btnHome;
    int i = 0;
    String str[] = new String[95];
    String Increase;
    String Confirmer;
    String IsolCancel;
    String Dead;
    String Incidence;
    String Area = "지역";
    int areanum = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation);

        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SituationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Content c = new Content();
        c.execute();
    }

    private class Content extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        public Void doInBackground(Void... voids) {
            try {
                i = 0;
                String url = "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=13&ncvContSeq=&contSeq=&board_id=&gubun=";
                Document document = Jsoup.connect(url).get();
                Elements links = document.select("td[class]");
                for (Element element : links) {
                    str[i] = element.text();
                    i++;
                }

            } catch (IOException e) {
                Log.e("abc", "기분좋다");
                e.printStackTrace();

            }
            return null;
        }
    }

    public void Seoul() {
        int i = 0;
        int k = 1;
        if (i == 0) {
            Area = "경기";
            k = 9;
        } else if (i == 1) {
            Area = "서울";
            k = 1;
        } else if (i == 2) {
            Area = "인천";
            k = 4;
        } else if (i == 3) {
            Area = "강원";
            k = 10;
        } else if (i == 4) {
            Area = "충북";
            k = 11;
        } else if (i == 5) {
            Area = "충남";
            k = 12;
        } else if (i == 6) {
            Area = "대전";
            k = 6;
        } else if (i == 7) {
            Area = "경북";
            k = 15;
        } else if (i == 8) {
            Area = "경남";
            k = 16;
        } else if (i == 9) {
            Area = "전북";
            k = 13;
        } else if (i == 10) {
            Area = "전남";
            k = 14;
        } else if (i == 11) {
            Area = "광주";
            k = 5;
        } else if (i == 12) {
            Area = "대구";
            k = 3;
        } else if (i == 13) {
            Area = "울산";
            k = 7;
        } else if (i == 14) {
            Area = "부산";
            k = 2;
        } else if (i == 15) {
            Area = "제주";
            k = 17;
        } else if (i == 16) {
            Area = "세종";
            k = 8;
        }
        areanum = 5 * k;
        Increase = str[areanum];
        Confirmer = str[1 + areanum];
        IsolCancel = str[2 + areanum];
        Dead = str[3 + areanum];
        Incidence = str[4 + areanum];
        Intent intent = new Intent(this, Popup.class);
        intent.putExtra("data", "증감수 : " + Increase + "\n확진자 : " + Confirmer + "\n격리해제 : " + IsolCancel + "\n사망자 : " + Dead + "\n발생율 : " + Incidence);
        intent.putExtra("data2", Area);
        startActivityForResult(intent, 1);
    }

}
