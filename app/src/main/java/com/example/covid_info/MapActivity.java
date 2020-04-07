package com.example.covid_info;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    private ImageButton btnHome;
    //지도가 들어가는 레이아웃
    private RelativeLayout relativeLayout;
    //지도에서 TextView가 들어있는 LinearLayout
    LinearLayout linearTextView[] = new LinearLayout[16];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //RelativeLayout에 myview(지도가 들어있음)를 추가
        RelativeLayout relative = (RelativeLayout) findViewById(R.id.relativeMap);
        MyView myview = new MyView(this);
        relative.addView(myview);

        relativeLayout = findViewById(R.id.relativeMap);

        makeLinearLayout();

    }

    /** 지도 위에 지역이름을 나타내주는 텍스트뷰를 가지는 레이아웃을 만듬 **/
    private void makeLinearLayout() {
        TextView tv[] = new TextView[16];

        //도시의 수는 16개
        for (int i = 0; i < 16; i++) {
            linearTextView[i] = new LinearLayout(this);
            tv[i] = new TextView(this);

            Drawable drawable = this.getResources().getDrawable(R.drawable.ic_chat_bubble);
            int padding = 40;

            tv[i].setText(R.string.city_10 + i);
            tv[i].setTextColor(Color.BLACK);

            linearTextView[i].addView(tv[i]);
            linearTextView[i].setOrientation(LinearLayout.VERTICAL);
            linearTextView[i].setPadding(padding, padding - 20, padding, padding);
            //텍스트뷰에 이미지를 씌우기 위한 작업
            linearTextView[i].setBackground(drawable);

            relativeLayout.addView(linearTextView[i]);
        }

        /** 좌표로 인한 하드 코딩
         * 절대 좌표(스크린상)가 아닌 상대 좌표(현재 레이아웃 기준)다
         * **/

        //경기
        linearTextView[0].setX(350);
        linearTextView[0].setY(100);
        //서울
        linearTextView[1].setX(340);
        linearTextView[1].setY(240);
        //인천
        linearTextView[2].setX(180);
        linearTextView[2].setY(140);
        //강원
        linearTextView[3].setX(600);
        linearTextView[3].setY(50);
        //충북
        linearTextView[4].setX(500);
        linearTextView[4].setY(400);
        //충남
        linearTextView[5].setX(200);
        linearTextView[5].setY(450);
        //대전
        linearTextView[6].setX(430);
        linearTextView[6].setY(600);
        //경북
        linearTextView[7].setX(800);
        linearTextView[7].setY(550);
        //경남
        linearTextView[8].setX(550);
        linearTextView[8].setY(970);
        //전북
        linearTextView[9].setX(300);
        linearTextView[9].setY(850);
        //전남
        linearTextView[10].setX(200);
        linearTextView[10].setY(1200);
        //광주
        linearTextView[11].setX(290);
        linearTextView[11].setY(1000);
        //대구
        linearTextView[12].setX(700);
        linearTextView[12].setY(750);
        //울산
        linearTextView[13].setX(850);
        linearTextView[13].setY(850);
        //부산
        linearTextView[14].setX(750);
        linearTextView[14].setY(1050);
        //제주
        linearTextView[15].setX(250);
        linearTextView[15].setY(1400);


    }

    /**
     * 10. 경기   14. 충북    18. 경남    22. 대구
     * 11. 서울   15. 충남    19. 전북    23. 울산
     * 12. 인천   16. 대전    20. 전남    24. 부산
     * 13. 강원   17. 경북    21. 광주    25. 제주
     *
     * 지도를 만드는 클래스
     * 좌표로 정확하게 만들기위해 xml에서 만들지 않고 액티비티에서 생성
     * Rect로 사각형을 만든뒤 그위에 bitmap을 씌우는 방식(좌표를 활용하려고)
     * 절대 좌표(스크린상)가 아닌 상대 좌표(현재 레이아웃 기준)다
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

            /** 좌표로 인한 하드 코딩 **/
            switch (order) {
                case 0: // 경기(9
                    left = 150;
                    top = 100;
                    break;
                case 1: // 서울(1
                    left = 210;
                    top = 270;
                    break;
                case 2: // 인천(4
                    left = 70;
                    top = 220;
                    break;
                case 3: // 강원(10
                    left = 300;
                    top = 10;
                    break;
                case 4: // 충북(11
                    left = 340;
                    top = 420;
                    break;
                case 5: // 충남(12
                    left = 50;
                    top = 480;
                    break;
                case 6: // 대전(6
                    left = 335;
                    top = 665;
                    break;
                case 7: // 경북(15
                    left = 470;
                    top = 445;
                    break;
                case 8: // 경남(16
                    left = 410;
                    top = 865;
                    break;
                case 9: // 전북(13
                    left = 120;
                    top = 770;
                    break;
                case 10: // 전남(14
                    left = -22;
                    top = 1000;
                    break;
                case 11: // 광주(5
                    left = 185;
                    top = 1070;
                    break;
                case 12: // 대구(3
                    left = 597;
                    top = 820;
                    break;
                case 13: // 울산(7
                    left = 750;
                    top = 910;
                    break;
                case 14: // 부산(2
                    left = 626;
                    top = 1025;
                    break;
                case 15: // 제주(17
                    left = 100;
                    top = 1450;
                    break;
            }
            left += 90; //지도 전체를 왼쪽으로 부터 90pixel 멀리
            return new Rect(left, top, left + bitmapWidth, top + bitmapHeight);
        }

    }
}

