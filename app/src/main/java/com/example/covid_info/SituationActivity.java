package com.example.covid_info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SituationActivity extends AppCompatActivity
{

    private ImageButton btnHome;
    int Confirmer;
    int Cure;
    int Dead;
    String Area = "지역";

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
    }
    public void Seoul(View v)
        {
            Area = "서울";
            Confirmer = 0;
            Cure = 0;
            Dead = 0;
            Intent intent = new Intent(this, Popup.class);
            intent.putExtra("data","확진자 : " + Confirmer + "\n완치자 : "+ Cure + "\n사망자 : " + Dead);
            intent.putExtra("data2",Area);
            startActivityForResult(intent, 1);
        }

}
