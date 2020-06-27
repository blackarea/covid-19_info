package com.example.covid_info;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class PopupActivity extends Activity {
    TextView location;
    TimePicker timepicker;
    String hour, min;
    public static String location_text;
    public static int hour_text, min_text;
    private Button pressOK;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parserarray);
        location = (TextView)findViewById(R.id.location);
        timepicker = (TimePicker)findViewById(R.id.timepicker);
        Calendar cal = Calendar.getInstance();
        hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        min = String.valueOf(cal.get(Calendar.MINUTE));
        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute){
                hour = String.valueOf(hourOfDay);
                min = String.valueOf(minute);

                hour_text = hourOfDay;
                min_text = minute;
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location.setText("지역 :" +adapterView.getItemAtPosition(i));
                location_text = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        pressOK = (Button) findViewById(R.id.pressOK);
        pressOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetAlarm.setAlarm(PopupActivity.this);
            }
        });
    }
}
