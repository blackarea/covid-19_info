package com.example.covid_info;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    private ImageButton btnHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        RelativeLayout linear = (RelativeLayout) findViewById(R.id.relativeMap);
        MyView myview = new MyView(this);
        linear.addView(myview);

        btnHome=findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    /**
     * 10. 경기   14. 충북    18. 경남    22. 대구
     * 11. 서울   15. 충남    19. 전북    23. 울산
     * 12. 인천   16. 대전    20. 전남    24. 부산
     * 13. 강원   17. 경북    21. 광주    25. 제주
     **/
    private class MyView extends View {
        Bitmap[] bitmapArr = new Bitmap[16];

        public MyView(Context context) {
            super(context);

            for (int i = 0; i < 16; i++) {
                bitmapArr[i] = BitmapFactory.decodeResource(context.getResources(), R.drawable.city_10 + i);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Rect rect;

            for (int i = 0; i < 16; i++) {

                rect = setRect(bitmapArr[i], i);
                canvas.drawBitmap(bitmapArr[i], null, rect, null);
            }

            super.onDraw(canvas);
        }

        private Rect setRect(Bitmap bitmap, int order) {
            int bitmapWidth, bitmapHeight;

            bitmapWidth = bitmap.getWidth();
            bitmapHeight = bitmap.getHeight();
            //left : 화면 왼쪽으로 부터의 거리, top : 화면 위쪽으로 부터의 거리
            int left = 0, top = 0;

            switch (order) {
                case 0: // 경기
                    left = 150;
                    top = 100;
                    break;
                case 1: // 서울
                    left = 210;
                    top = 270;
                    break;
                case 2: // 인천
                    left = 70;
                    top = 220;
                    break;
                case 3: // 강원
                    left = 300;
                    top = 10;
                    break;
                case 4: // 충북
                    left = 340;
                    top = 420;
                    break;
                case 5: // 충남
                    left = 50;
                    top = 480;
                    break;
                case 6: // 대전
                    left = 335;
                    top = 665;
                    break;
                case 7: // 경북
                    left = 470;
                    top = 445;
                    break;
                case 8: // 경남
                    left = 410;
                    top = 865;
                    break;
                case 9: // 전북
                    left = 120;
                    top = 770;
                    break;
                case 10: // 전남
                    left = -22;
                    top = 1000;
                    break;
                case 11: // 광주
                    left = 185;
                    top = 1070;
                    break;
                case 12: // 대구
                    left = 597;
                    top = 820;
                    break;
                case 13: // 울산
                    left = 750;
                    top = 910;
                    break;
                case 14: // 부산
                    left = 626;
                    top = 1025;
                    break;
                case 15: // 제주
                    left = 100;
                    top = 1450;
                    break;
            }
            left+=90; //지도 전체를 왼쪽으로 부터 90pixel 멀리
            return new Rect(left, top, left + bitmapWidth, top + bitmapHeight);
        }

    }
}

