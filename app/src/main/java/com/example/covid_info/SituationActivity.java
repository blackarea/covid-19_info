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

public class SituationActivity extends AppCompatActivity
{

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
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation);

        btnHome=findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SituationActivity.this,MainActivity.class);
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
                i=0;
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
    public void Seoul(View v)
        {
            Area = "서울";
            areanum = 5;
            Increase = str[areanum];
            Confirmer = str[1+areanum];
            IsolCancel = str[2+areanum];
            Dead = str[3+areanum];
            Incidence = str[4+areanum];
            Intent intent = new Intent(this, Popup.class);
            intent.putExtra("data","증감수 : " + Increase + "\n확진자 : " + Confirmer + "\n격리해제 : "+ Increase + "\n사망자 : " + Dead + "\n발생율 : " + Incidence);
            intent.putExtra("data2",Area);
            startActivityForResult(intent, 1);
        }

}
