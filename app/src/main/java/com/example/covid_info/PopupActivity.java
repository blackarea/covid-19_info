package com.example.covid_info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    public static int index;
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

        /* 시간 조절을 하기 위한 TimePicker 생성
           해당 시간값은 각각 변수에 저장되어 SetAlarm로 전달됨 */

        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute){
                hour = String.valueOf(hourOfDay);
                min = String.valueOf(minute);

                hour_text = hourOfDay;
                min_text = minute;
            }
        });

        /* 지역 선택을 위한 Spinner 생성
           해당 지역이 선택되면 지역명과 *번째 지역인지 전달 */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                location.setText("지역 :" +adapterView.getItemAtPosition(position));
                if(position == 0){
                    index = 0;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 1){
                    index = 1;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 2){
                    index = 2;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 3){
                    index = 3;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 4){
                    index = 4;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 5){
                    index = 5;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 6){
                    index = 6;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 7){
                    index = 7;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 8){
                    index = 8;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 9){
                    index = 9;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 10){
                    index = 10;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 11){
                    index = 11;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 12){
                    index = 12;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 13){
                    index = 13;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 14){
                    index = 14;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 15){
                    index = 15;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 16){
                    index = 16;
                    location_text = spinner.getSelectedItem().toString();
                }
                if(position == 17){
                    index = 17;
                    location_text = spinner.getSelectedItem().toString();
                }
            }

        /* 설정 버튼을 누르면 SetAlarm이 실행되어 알람이 설정되게 함 */
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
    @Override
    public void onBackPressed() {
        finish();
    }

}
