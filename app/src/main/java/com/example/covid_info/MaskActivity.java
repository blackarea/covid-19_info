package com.example.covid_info;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MaskActivity extends AppCompatActivity implements NaverMap.OnMapClickListener, Overlay.OnClickListener, OnMapReadyCallback, NaverMap.OnCameraChangeListener, NaverMap.OnCameraIdleListener {

    private ImageButton btnHome;
    private static final int ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private List<Marker> markerList = new ArrayList<Marker>();
    private boolean isCameraAnimated = false;
    private LinearLayout linearLayout;
    private boolean state = false;
    private double longitude, latitude;
    private TextView textView_name, textView_distance, textView_remain_stat, textView_stock_at, textView_created_at, textView_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);

        linearLayout = findViewById(R.id.bottom_layout);
        textView_name = findViewById(R.id.textView_name);
        textView_distance = findViewById(R.id.textView_distance);
        textView_remain_stat = findViewById(R.id.textView_remain_stat);
        textView_stock_at = findViewById(R.id.textView_stock_at);
        textView_created_at = findViewById(R.id.textView_created_at);
        textView_search = findViewById(R.id.textView_search);


        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    /**
     * 맵을 생성할 준비가 되었을 때 가장 먼저 호출되는 오버라이드 메소드이다.
     * 이 메소드 안에서 지도의 속성을 초기화할 수 있다.
     **/
    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        this.naverMap = naverMap;

        locationSource = new FusedLocationSource(this, ACCESS_LOCATION_PERMISSION_REQUEST_CODE);
        naverMap.setLocationSource(locationSource);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        naverMap.addOnCameraChangeListener(this);
        naverMap.addOnCameraIdleListener(this);
        naverMap.setOnMapClickListener(this);


        Log.i("?", "" + latitude + " " + longitude);

        LatLng location = new LatLng(latitude, longitude);
        CameraPosition cameraPosition = new CameraPosition(location, 15);
        naverMap.setCameraPosition(cameraPosition);

        LatLng mapCenter = naverMap.getCameraPosition().target;
        fetchStoreSale(mapCenter.latitude, mapCenter.longitude, 1000);

    }


    /**
     * 카메라 이동중에는 말고 이동이 끝났을때를 상태저장한다.
     */
    @Override
    public void onCameraChange(int i, boolean animated) {
        isCameraAnimated = animated;
    }

    /**
     * 카메라 이동시 fetchStoreSale를 다시 불러와 그 위치에 맞는 마스크 정보를 다시 불러온다.
     */
    @Override
    public void onCameraIdle() {
        if (isCameraAnimated) {
            LatLng mapCenter = naverMap.getCameraPosition().target;
            fetchStoreSale(mapCenter.latitude, mapCenter.longitude, 5000);
        }
    }


    /**
     * 지도에 마스크를 표시해준다.
     *
     * @param lat 위도
     * @param lng 경도
     * @param m   반경 (현재 위치에서 몇 m까지 마스크를 표시할 건지)
     */
    private void fetchStoreSale(double lat, double lng, int m) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://8oi9s0nnth.apigw.ntruss.com").addConverterFactory(GsonConverterFactory.create()).build();
        MaskApi maskApi = retrofit.create(MaskApi.class);
        maskApi.getStoresByGeo(lat, lng, m).enqueue(new Callback<StoreSaleResult>() {
            @Override
            public void onResponse(Call<StoreSaleResult> call, Response<StoreSaleResult> response) {
                if (response.code() == 200) {
                    StoreSaleResult result = response.body();
                    updateMapMarkers(result);
                }
            }

            @Override
            public void onFailure(Call<StoreSaleResult> call, Throwable t) {

            }
        });
    }


    private void updateMapMarkers(StoreSaleResult result) {
        resetMarkerList();
        if (result.stores != null && result.stores.size() > 0) {
            for (Store store : result.stores) {
                Marker marker = new Marker();
                marker.setTag(store);
                marker.setPosition(new LatLng(store.lat, store.lng));
                if ("plenty".equalsIgnoreCase(store.remain_stat)) {
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_green));
                } else if ("some".equalsIgnoreCase(store.remain_stat)) {
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_yellow));
                } else if ("fiew".equalsIgnoreCase(store.remain_stat)) {
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_red));
                } else {
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_gray));
                }
                marker.setAnchor(new PointF(0.5f, 1.0f));
                marker.setMap(naverMap);
                marker.setOnClickListener(this);
                markerList.add(marker);
            }
        }
    }

    private void resetMarkerList() {
        if (markerList != null && markerList.size() > 0) {
            for (Marker marker : markerList) {
                marker.setMap(null);
            }
            markerList.clear();
        }
    }

    @Override
    public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
        if (state) {
            linearLayout.setVisibility(View.INVISIBLE);
            state = false;
        }

    }

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        linearLayout.setVisibility(View.VISIBLE);
        state = true;

        String distance;

        Marker marker = (Marker) overlay;
        Store store = (Store) marker.getTag();

        textView_name.setText(store.name);

        distance = CalDistance.getDistance(latitude, longitude, store.lat, store.lng);
        textView_distance.setText(distance);

        textView_remain_stat.setText(changeRemain_stat(store.remain_stat));

        textView_stock_at.setText(" : " + store.stock_at);

        textView_created_at.setText("정보 업데이트 : " + store.created_at);

        textView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_search = new Intent(Intent.ACTION_WEB_SEARCH);
                intent_search.putExtra(SearchManager.QUERY, store.name);
                startActivity(intent_search);
            }
        });

        //return true : onMapClick 이 실행되지 않음 (return false 면 onClick onMapClick 중복실행)
        return true;
    }

    public String changeRemain_stat(String remain_stat) {
        String result;

        if ("plenty".equalsIgnoreCase(remain_stat)) {
            result = "100개 이상";
        } else if ("some".equalsIgnoreCase(remain_stat)) {
            result = "30개 이상 100개 미만";
        } else if ("few".equalsIgnoreCase(remain_stat)) {
            result = "2개 이상 30개 미만";
        } else if ("empty".equalsIgnoreCase(remain_stat)) {
            result = "1개 이하";
        } else if ("break".equalsIgnoreCase(remain_stat)) {
            result = "판매중지";
        } else {
            result = null;
        }

        return result;
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
