package com.example.covid_info;

/**
 * addr : 주소
 * code : 식별코드
 * name : 이름
 * type : 판매처 유형[약국: '01', 우체국: '02', 농협: '03']
 * lat : 위도
 * lng : 경도
 * stock_at : 입고 시간
 * remain_stat : 재고 상태[100개 이상(녹색): 'plenty' / 30개 이상 100개미만(노랑색): 'some' / 2개 이상 30개 미만(빨강색): 'few' / 1개 이하(회색): 'empty' / 판매중지: 'break']
 * created_at : 데이터 생성 일자
 */
public class Store {
    public String code;
    public String name;
    public String addr;
    public String type;
    public double lat;
    public double lng;
    public String stock_at;
    public String remain_stat;
    public String created_at;
}
