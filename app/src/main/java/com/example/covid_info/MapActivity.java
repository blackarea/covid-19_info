package com.example.covid_info;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MapActivity extends AppCompatActivity {
    int i = 0;
    String str[] = new String[200];
    String Increase;
    String Confirmer;
    String IsolCancel;
    String Dead;
    String Incidence;
    String Area = "지역";
    int areanum = 0;
    private ImageButton btnHome;
    //지도가 들어가는 레이아웃
    private RelativeLayout relativeLayout;
    //지도에서 TextView가 들어있는 LinearLayout
    LinearLayout linearTextView[] = new LinearLayout[16];
    int numberOfCity[] = new int[19];
    private int totalCoronaNumber;

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

        relativeLayout = findViewById(R.id.relativeMap);

        //AsyncTask 클래스는 UI thread를 다뤄서 그냥 execute를 부르면 작업이 다 끝난 다음 실행이지만
        //get을 불러오면 UIthread가 일정시간 block 된다 doinbackground함수 형태를 바꿔서 get으로 값을 받을 수도 있다.
        MapParser mapParser = new MapParser();
        try {
            mapParser.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //RelativeLayout에 myview(지도가 들어있음)를 추가
        RelativeLayout relative = (RelativeLayout) findViewById(R.id.relativeMap);
        MyView myview = new MyView(this);
        relative.addView(myview);

        makeLinearLayout();

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
                e.printStackTrace();

            }
            return null;
        }
    }

    private class MapParser extends AsyncTask<Void, Void, Void> {
        String parseString = "";

        @Override
        protected Void doInBackground(Void... aInteger) {
            int i = 0;
            int translateNumber = 0; //50일 경우 제외

            String str; //숫자에 , 가 있을경우 변환하기위한 문자열
            try {
                String url = "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=13";

                //url에 접속
                Document document = Jsoup.connect(url).get();
                //총 확진자수 구하기
                //파싱한 숫자에 ,가 있을 경우 제거
                Element total = document.select("td[headers=status_con s_type1]").first();
                str = total.text();
                str = str.replaceAll("[^0-9]", "");
                totalCoronaNumber = Integer.parseInt(str);

                //도시별 확진자수
                Elements cityNumber = document.select("td[headers=status_con s_type1]");

                //Elements는 리스트 형태이므로 Element로 변환하여 하나씩 출력
                for (Element element : cityNumber) {
                    translateNumber = translateNumber(i++);

                    if (translateNumber == 50)
                        continue;
                    //파싱한 숫자에 ,가 있을 경우 제거
                    str = element.text();
                    str = str.replaceAll("[^0-9]", "");
                    numberOfCity[translateNumber] = Integer.parseInt(str);
                    parseString += element.text() + "\n";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aInteger) {
            super.onPostExecute(aInteger);
        }

    }


    /**
     * 지도 위에 지역이름을 나타내주는 텍스트뷰를 가지는 레이아웃을 만듬
     **/
    private void makeLinearLayout() {
        TextView mapTextView[] = new TextView[16];
        TextView numberTextView[] = new TextView[16];

        //도시의 수는 16개
        for (int i = 0; i < 16; i++) {
            linearTextView[i] = new LinearLayout(this);
            mapTextView[i] = new TextView(this);
            numberTextView[i] = new TextView(this);

            Drawable drawable = this.getResources().getDrawable(R.drawable.ic_chat_bubble);
            int padding = 40;

            mapTextView[i].setText(R.string.city_10 + i);
            mapTextView[i].setTextColor(Color.BLACK);
            numberTextView[i].setText(String.valueOf(numberOfCity[i]));
            numberTextView[i].setTextColor(Color.BLACK);

            linearTextView[i].addView(mapTextView[i]);
            linearTextView[i].addView(numberTextView[i]);
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
        linearTextView[0].setY(60);
        linearTextView[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(0);
            }
        });
        //서울
        linearTextView[1].setX(340);
        linearTextView[1].setY(200);
        linearTextView[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(1);
            }
        });
        //인천
        linearTextView[2].setX(180);
        linearTextView[2].setY(140);
        linearTextView[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(2);
            }
        });
        //강원
        linearTextView[3].setX(600);
        linearTextView[3].setY(100);
        linearTextView[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(3);
            }
        });
        //충북
        linearTextView[4].setX(500);
        linearTextView[4].setY(400);
        linearTextView[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(4);
            }
        });
        //충남
        linearTextView[5].setX(200);
        linearTextView[5].setY(450);
        linearTextView[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(5);
            }
        });
        //대전
        linearTextView[6].setX(430);
        linearTextView[6].setY(580);
        linearTextView[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(6);
            }
        });
        //경북
        linearTextView[7].setX(800);
        linearTextView[7].setY(550);
        linearTextView[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(7);
            }
        });
        //경남
        linearTextView[8].setX(550);
        linearTextView[8].setY(970);
        linearTextView[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(8);
            }
        });
        //전북
        linearTextView[9].setX(300);
        linearTextView[9].setY(800);
        linearTextView[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(9);
            }
        });
        //전남
        linearTextView[10].setX(200);
        linearTextView[10].setY(1150);
        linearTextView[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(10);
            }
        });
        //광주
        linearTextView[11].setX(290);
        linearTextView[11].setY(960);
        linearTextView[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(11);
            }
        });
        //대구
        linearTextView[12].setX(700);
        linearTextView[12].setY(750);
        linearTextView[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(12);
            }
        });
        //울산
        linearTextView[13].setX(850);
        linearTextView[13].setY(820);
        linearTextView[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(13);
            }
        });
        //부산
        linearTextView[14].setX(780);
        linearTextView[14].setY(1000);
        linearTextView[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(14);
            }
        });
        //제주
        linearTextView[15].setX(250);
        linearTextView[15].setY(1370);
        linearTextView[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seoul(15);
            }
        });


    }

    /**
     * 10. 경기   14. 충북    18. 경남    22. 대구
     * 11. 서울   15. 충남    19. 전북    23. 울산
     * 12. 인천   16. 대전    20. 전남    24. 부산
     * 13. 강원   17. 경북    21. 광주    25. 제주
     * <p>
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
            float percent;

            for (int i = 0; i < 16; i++) {
                rect = setRect(bitmapArr[i], i);

                Paint paint = new Paint();
                //도시의 확진자수 / 전체 확진자수 비율
                percent = (float) numberOfCity[i] / (float) totalCoronaNumber;
                ColorFilter colorFilter = null;

                //퍼센트별 색 변경
                if (percent > 0.2) {
                    colorFilter = new PorterDuffColorFilter(Color.parseColor("#FF2222"), PorterDuff.Mode.SRC_ATOP);
                } else if (percent > 0.1) {
                    colorFilter = new PorterDuffColorFilter(Color.parseColor("#FF6666"), PorterDuff.Mode.SRC_ATOP);
                } else if (percent > 0.015) {
                    colorFilter = new PorterDuffColorFilter(Color.parseColor("#FFAAAA"), PorterDuff.Mode.SRC_ATOP);
                } else if (percent > 0.01) {
                    colorFilter = new PorterDuffColorFilter(Color.parseColor("#FFBBBB"), PorterDuff.Mode.SRC_ATOP);
                } else if (percent > 0.0045) {
                    colorFilter = new PorterDuffColorFilter(Color.parseColor("#FFCCCC"), PorterDuff.Mode.SRC_ATOP);
                } else if (percent > 0.003) {
                    colorFilter = new PorterDuffColorFilter(Color.parseColor("#FFDADA"), PorterDuff.Mode.SRC_ATOP);
                } else if (percent > 0.0015) {
                    colorFilter = new PorterDuffColorFilter(Color.parseColor("#FFE2E2"), PorterDuff.Mode.SRC_ATOP);
                } else {
                    colorFilter = new PorterDuffColorFilter(Color.parseColor("#FFEFEF"), PorterDuff.Mode.SRC_ATOP);
                }

                paint.setColorFilter(colorFilter);
                canvas.drawBitmap(bitmapArr[i], null, rect, paint);

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

    /**
     * url상에 있는 숫자를 앱 내 숫자에 맞게 변경
     **/

    private int translateNumber(int urlNumber) {
        int i = 0;
        if (urlNumber == 0)
            i = 50;
        else if (urlNumber == 1) //서울
            i = 1;
        else if (urlNumber == 2) //부산
            i = 14;
        else if (urlNumber == 3) //대구
            i = 12;
        else if (urlNumber == 4) //인천
            i = 2;
        else if (urlNumber == 5) //광주
            i = 11;
        else if (urlNumber == 6) //대전
            i = 6;
        else if (urlNumber == 7) //울산
            i = 13;
        else if (urlNumber == 8) //세종
            i = 50;
        else if (urlNumber == 9) //경기
            i = 0;
        else if (urlNumber == 10) //강원
            i = 3;
        else if (urlNumber == 11) //충북
            i = 4;
        else if (urlNumber == 12) //충남
            i = 5;
        else if (urlNumber == 13) //전북
            i = 9;
        else if (urlNumber == 14) //전남
            i = 10;
        else if (urlNumber == 15) //경북
            i = 7;
        else if (urlNumber == 16) //경남
            i = 8;
        else if (urlNumber == 17) //제주
            i = 15;
        else
            i = 50;
        return i;
    }

    public void Seoul(int i) {
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
        areanum = 8 * k;
        Increase = str[areanum];
        Confirmer = str[3 + areanum];
        IsolCancel = str[5 + areanum];
        Dead = str[6 + areanum];
        Incidence = str[7 + areanum];
        Intent intent = new Intent(this, Popup.class);
        intent.putExtra("data", "증감수 : " + Increase + "\n확진자 : " + Confirmer + "\n격리해제 : " + IsolCancel + "\n사망자 : " + Dead + "\n발생율 : " + Incidence);
        intent.putExtra("data2", Area);
        startActivityForResult(intent, 1);
    }
}

