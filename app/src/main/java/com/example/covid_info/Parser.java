package com.example.covid_info;


import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Parser extends AsyncTask<Void, Void, Void> {

    String parseString ="";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String url = "http://ncov.mohw.go.kr/";

            //url에 접속
            Document document = Jsoup.connect(url).get();
            Elements links = document.select("ul.liveNum li span.num");
            //Elements는 리스트 형태이므로 Element로 변환하여 하나씩 출력
            for(Element element : links){
                parseString += element.text()+"\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}