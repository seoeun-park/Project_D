package com.example.projectd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projectd.ATask.KakaoLogin;
import com.example.projectd.ATask.NaverJoin;
import com.example.projectd.ATask.NaverLogin;
import com.example.projectd.ATask.UpdateLocation;
import com.example.projectd.R;
import com.example.projectd.SignUpFormActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static com.example.projectd.LoginActivity.loginDTO;
import static com.example.projectd.LoginActivity.naverLoginDTO;
import static com.example.projectd.SessionCallback.kakaoLoginDTO;

public class SocialLocationActivity extends AppCompatActivity {
    private static final String TAG = "main:S_LocationActivity";

    SupportMapFragment mapFragment;
    GoogleMap map;
    //Button locSearchBtn;
    Button setupBtn, submitBtn;
    EditText searchValueText, detailValue;
    String myAddress, detailAddress, state, member_addr;    //member_addr은 일반주소 + 상세주소
    LinearLayout toolbar_context;   //툴바를 감싸고 있는 레이아웃
    Double latitude, longitude;
    boolean update_location = false, update_location_p = false;    //위치정보 수정(메인에서) 여부를 저장하는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_location);

        checkDangerousPermissions();    //위험 권한 주기

        searchValueText = findViewById(R.id.searchValueText);
        detailValue = findViewById(R.id.detailValue);
        setupBtn = findViewById(R.id.setupBtn);
        //locSearchBtn = findViewById(R.id.locSearchBtn);
        submitBtn = findViewById(R.id.submitBtn);
        toolbar_context = findViewById(R.id.toolbar_context);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                // setMyLocationEnabled() permission check
                if (ActivityCompat.checkSelfPermission(SocialLocationActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(SocialLocationActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                map = googleMap;
                map.setMyLocationEnabled(true);

                // 현재 위치에 마커를 추가
                GpsTracker gpsTracker = new GpsTracker(SocialLocationActivity.this);
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                LatLng curPoint = new LatLng(latitude, longitude);  //현재 위치 반환
                addMarker(curPoint);    // 현재 위치에 마커 추가
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 18));
                // 지도를 curPoint 지점으로 확대해서 표시

                // 지도를 클릭했을 때 실행되는 메소드
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        addMarker(latLng);      // 마커를 추가
                        latitude = latLng.latitude;
                        longitude = latLng.longitude;
                    } //onMapClick()
                }); //map.setOnMapClickListener()
            }
        }); //mapFragment.getMapAsync()

        MapsInitializer.initialize(this);

        // 위치 검색 자동완성
        Places.initialize(getApplicationContext(), "AIzaSyD7wAJVRHAe2jMHZSouoMxf-8-sjJPZUn0");
        PlacesClient placesClient = Places.createClient(this);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                latitude = 0.0; longitude = 0.0;    // 검색된 장소가 다시 선택됐을 경우, 기존의 위도 경도 초기화

                //searchValueText.setText(place.getName());
                //Log.d(TAG, "onPlaceSelected: getAddress : " + place.getAddress());
                Log.d(TAG, "onPlaceSelected: 위도 : " + place.getLatLng().latitude);
                Log.d(TAG, "onPlaceSelected: 경도 : " + place.getLatLng().longitude);
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;

                //Location searchLocation = getLocationFromAddress(getApplicationContext(), place.getName());
                //latitude = searchLocation.getLatitude();
                //longitude = searchLocation.getLongitude();

                addMarker(new LatLng(latitude, longitude));

                searchValueText.setText(getCurrentAddress(latitude, longitude).substring(5));

                Log.d(TAG, "onPlaceSelected: " + place.getName()
                        + ", 위도 : " + latitude + ", 경도 : " + longitude);
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.d(TAG, "onError: " + status);
            }
        }); // 위치 검색 자동완성 끝

        // 해당 위치 설정 버튼 클릭 시
        setupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location currentLocation = currentLocation();
                //Double latitude = currentLocation.getLatitude();
                //Double longitude = currentLocation.getLongitude();

                Toast.makeText(SocialLocationActivity.this,
                        "latitude" + latitude + "\nlongitude" + longitude,
                        Toast.LENGTH_SHORT).show();
                myAddress = getCurrentAddress(latitude, longitude);
                myAddress = myAddress.substring(5);
                // substring(5) → '대한민국 '이라는 문자열을 제외한 나머지 주소만 저장

                searchValueText.setText(myAddress);

                Log.d(TAG, "addMarker: 위도는 " + latitude + ", 경도는 " + longitude);
            }
        }); //locSearchBtn.setOnClickListener()


        // 완료 버튼 클릭시
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAddress = searchValueText.getText().toString();
                detailAddress = detailValue.getText().toString();

                String s_latitude = String.valueOf(latitude);
                String s_longitude = String.valueOf(longitude);
                String member_id, member_loginType;

                if(myAddress.equals("") || s_latitude.equals("") || s_longitude.equals("") ) {
                    // 알림 대화 상자를 보여주면서 경고문 보여주기
                    showMessage("알림", "지도 우상단의 gps 버튼으로 위치 지정한 다음 해당 위치로 설정 버튼 클릭한 후 완료 버튼을 클릭해주세요!");
                    Log.d(TAG, "if:onClick: " + myAddress + ", " + latitude + ", " + longitude);
                    return;
                } else if(detailAddress.equals("")) {
                    showMessage("알림", "상세주소를 입력해주세요!");
                    return;
                } else {
                    // SocialLocationActivity로 위치 정보를 저장하는 경우는 총 2가지
                    // 1. 처음으로 소셜(kakao, naver) 로그인을 했을 때(db 저장 x, kakaoLoginDTO가 null)
                    // 2. 소셜 로그인을 했지만 위치정보가 저장이 안되어 있을 때(db 저장 O, kakaoLoginDTO가 null이 아닌 경우)
                    // 3. 웹으로 회원가입했을 때(웹에서는 addr, latitude, longitude 컬럼 값이 null)
                    // 4. 위치 정보가 저장되어있지만 위치정보를 바꾸려고 할때(메인의 gps 버튼 클릭 시)- 소셜 로그인, 일반로그인 상관없이
                    // 5. 프로필 수정부분에서 위치정보를 수정할 때 - 여기는 SocialLocationActivity에서 DB 수정하는 것이 아니라
                    //      - 값만 가져와서 프로필 수정 페이지에서 DB 업데이트를 함

                    Intent intent = getIntent();
                    member_id = intent.getExtras().getString("member_id");
                    member_loginType = intent.getExtras().getString("member_loginType");
                    member_addr = myAddress + " " + detailAddress;
                    update_location = intent.getExtras().getBoolean("update_location");
                    update_location_p = intent.getExtras().getBoolean("update_location_p");

                    Log.d(TAG, "onClick: " + member_id + ", " + member_loginType);

                    if (member_loginType.equals("K") && kakaoLoginDTO == null) {    // 1번의 경우(카카오)
                        Log.d(TAG, "onClick: 카카오 처음 로그인 : " + member_id + ", " + member_loginType);

                        KakaoLogin kakaoLogin = new KakaoLogin(member_id, member_loginType);

                        try {
                            kakaoLogin.execute().get();
                            Log.d(TAG, "onSessionOpened: " + member_loginType);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (kakaoLoginDTO != null) {
                            updateLocation(member_addr, s_latitude, s_longitude, member_id, member_loginType);
                        }
                        setLocation(member_addr, s_latitude, s_longitude, member_loginType);    //DTO에 위치 저장

                    } else if (member_loginType.equals("N") && naverLoginDTO == null) {     // 1번의 경우(네이버)
                        Log.d(TAG, "onClick: 네이버 처음 로그인 : " + member_id + ", " + member_loginType);

                        NaverLogin naverLogin = new NaverLogin(member_id, member_loginType);

                        try {
                            naverLogin.execute().get();
                            Log.d(TAG, "onSessionOpened: " + member_loginType);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (naverLoginDTO != null) {
                            updateLocation(member_addr, s_latitude, s_longitude, member_id, member_loginType);
                        }

                        setLocation(member_addr, s_latitude, s_longitude, member_loginType);    //DTO에 위치 저장

                    } else if (member_loginType.equals("K") && kakaoLoginDTO != null) {     // 2번의 경우(카카오)
                        Log.d(TAG, "onClick: 카카오 로그인 o, 위치 저장 x");
                        Log.d(TAG, "onClick: " + member_addr + ", " + s_latitude + ", " + s_longitude  + ", " + kakaoLoginDTO.getMember_id() + ", " + kakaoLoginDTO.getMember_loginType());
                        updateLocation(member_addr, s_latitude, s_longitude, kakaoLoginDTO.getMember_id(), kakaoLoginDTO.getMember_loginType());
                        setLocation(member_addr, s_latitude, s_longitude, member_loginType);    //DTO에 위치 저장

                    } else if (member_loginType.equals("N") && naverLoginDTO != null) {     // 2번의 경우(네이버)
                        Log.d(TAG, "onClick: 네이버 로그인 o, 위치 저장 x");
                        Log.d(TAG, "onClick: " + myAddress + " " + detailAddress + ", " + s_latitude + ", " + s_longitude  + ", " + naverLoginDTO.getMember_id() + ", " + naverLoginDTO.getMember_loginType());
                        updateLocation(member_addr, s_latitude, s_longitude, naverLoginDTO.getMember_id(), naverLoginDTO.getMember_loginType());
                        setLocation(member_addr, s_latitude, s_longitude, member_loginType);    //DTO에 위치 저장

                    } else if(member_loginType.equals("M") &&
                                (loginDTO.getMember_addr() == null || loginDTO.getMember_addr().equals("") ||
                                 loginDTO.getMember_latitude() == null || loginDTO.getMember_latitude().equals("") ||
                                 loginDTO.getMember_longitude() == null) || loginDTO.getMember_longitude().equals("")) {    // 3번의 경우
                        Log.d(TAG, "onClick: 웹으로 로그인 o, 위치 저장 x");
                        updateLocation(member_addr, s_latitude, s_longitude, member_id, member_loginType);
                        setLocation(member_addr, s_latitude, s_longitude, member_loginType);    //DTO에 위치 저장

                    } else if(update_location) {    //4번의 경우
                        Log.d(TAG, "onClick: 위치 정보 업데이트(메인) : 아이디는 " + member_id + ", 로그인 타입은 " + member_loginType);
                        updateLocation(member_addr, s_latitude, s_longitude, member_id, member_loginType);
                        setLocation(member_addr, s_latitude, s_longitude, member_loginType);    //DTO에 위치 저장
                        Log.d(TAG, "setLocation: 멤버 로그인 : " + loginDTO.getMember_addr() + ", " +
                                loginDTO.getMember_latitude() + ", " + loginDTO.getMember_longitude());

                        // 여기서 부터는 SocialLocationActivity가 finish되고 RealMainActivity가 나타날 때
                        // refresh 기능을 수행하게끔 하는 코드
                        //Intent intent1 = new Intent(getApplicationContext(), RealMainActivity.class);
                        //intent1.putExtra("member_addr", member_addr);
                        setResult(Activity.RESULT_OK);

                        finish();
                        return;
                    } else if(update_location_p) {      //5번의 경우
                        Intent intent1 = new Intent(getApplicationContext(), ProfilSubActivity.class);
                        intent1.putExtra("member_addr", member_addr);
                        intent1.putExtra("latitude", s_latitude);
                        intent1.putExtra("longitude", s_longitude);
                        setResult(RESULT_OK, intent1);

                        Log.d(TAG, "프로필에서 SocialLocation으로 이동 : " + member_addr + ", " + s_latitude + ", " + s_longitude);
                        finish();
                        return;
                    }
                    Intent intent2 = new Intent(getApplicationContext(), RealMainActivity.class);
                    startActivity(intent2);
                    finish();
                }

            }
        }); //submitBtn.setOnClickListener()

        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    } //onCreate()

    private void updateLocation(String addr, String latitude, String longitude, String member_id, String member_loginType) {
        UpdateLocation updateLocation = new UpdateLocation(addr, latitude, longitude, member_id, member_loginType);
        try {
            state = updateLocation.execute().get().trim();
            Log.d(TAG, "submitBtnClick: " + state);
        } catch (Exception e) {
            Log.d(TAG, "submitBtnClick: " + e.getMessage());
        }

        if(state.equals("1")) {
            Log.d(TAG, "onSuccess1: 위치 지정(소셜) 성공!");
        } else {
            Log.d(TAG, "onSuccess1: 위치 지정(소셜) 실패");
        }

    } //updateLocation()

    // 멤버 DTO에 갱신된 위치정보를 저장하는 메소드
    public void setLocation(String addr, String latitude, String longitude, String loginType) {
        if(loginType.equals("M")) {
            loginDTO.setMember_addr(addr);
            loginDTO.setMember_latitude(latitude);
            loginDTO.setMember_longitude(longitude);
            Log.d(TAG, "setLocation: 멤버 로그인 : " + loginDTO.getMember_addr() + ", " +
                            loginDTO.getMember_latitude() + ", " + loginDTO.getMember_longitude());
        } else if(loginType.equals("K")) {
            kakaoLoginDTO.setMember_addr(addr);
            kakaoLoginDTO.setMember_latitude(latitude);
            kakaoLoginDTO.setMember_longitude(longitude);
            Log.d(TAG, "setLocation: 카카오 로그인 : " + kakaoLoginDTO.getMember_addr() + ", " +
                    kakaoLoginDTO.getMember_latitude() + ", " + kakaoLoginDTO.getMember_longitude());
        } else if(loginType.equals("N")) {
            naverLoginDTO.setMember_addr(addr);
            naverLoginDTO.setMember_latitude(latitude);
            naverLoginDTO.setMember_longitude(longitude);
            Log.d(TAG, "setLocation: 네이버 로그인 : " + naverLoginDTO.getMember_addr() + ", " +
                    naverLoginDTO.getMember_latitude() + ", " + naverLoginDTO.getMember_longitude());
        }
    } //setLocation()

    private void requestMyLocation() {
        LocationManager manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            long minTime = 10000;
            float minDistance = 0;

            // 1. NETWORK_PROVIDER : 내 위치 확인하기를 누르면 구글 본사가 나옴
            /*manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime, minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            showCurrentLocation(location);
                        }//onLocationChanged()
                    }//LocationListener()
            );//manager.requestLocationUpdates()*/

            // 2. GPS_PROVIDER : 내 위치 확인하기를 누르면 실제 내 위치가 나옴
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime, minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            showCurrentLocation(location);
                        }//onLocationChanged()
                    }//LocationListener()
            );//manager.requestLocationUpdates()

            Location lastLocation =
                    manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (lastLocation != null) {
                Double latitude = lastLocation.getLatitude();
                Double longitude = lastLocation.getLongitude();

                String msg = "Latitude : " + latitude
                        + "\nLongitude : " + longitude;

                Log.d(TAG, "showCurrentLocation: " + msg);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }

        } catch (SecurityException e) {
            Log.d(TAG, "requestMyLocation: " + e.getMessage());
        }
    }//requestMyLocation()

    // 주소지 명 → 위도 & 경도 변환 메소드
    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");

        try {
            addresses = geocoder.getFromLocationName(address, 5);
            // → searchValueText에서 작성한 주소를 받아 최대 5개의 결과를 반환

            if (addresses == null || addresses.size() == 0) {
                return null;
            }
            Address addressLoc = addresses.get(0);
            // → 일치할 확률이 제일 큰 0번째 결과를 변수(addressLoc)에 저장

            resLocation.setLatitude(addressLoc.getLatitude());
            resLocation.setLongitude(addressLoc.getLongitude());

        } catch (Exception e) {
            e.getMessage();
        }
        return resLocation;
    } //getLocationFromAddress()

    /*
    // 위도 & 경도 → 주소지 명 변환 메소드 (실패)
    private void getAddressFromLocation(Context context, Double latitude, Double longitude) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = null;
        Location resLocation = new Location("");

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 5);
        } catch (Exception e) {
            e.getMessage();
        }

        if(addresses != null) {
            if (addresses.size() == 0) {
                Toast.makeText(context, "주소 찾기 오류!", Toast.LENGTH_SHORT).show();
            } else {
                //searchValueText.setText(addresses.get(0).getAddressLine(0));
                searchValueText.setText(addresses.get(0).toString());
            }
        }
    } //getAddressFromLocation()
    */

    // 위도 & 경도 → 주소지 명 변환 메소드
    public String getCurrentAddress(double latitude, double longitude) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 100);

        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        } if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }


    // 위도와 경도를 통해 지도에 표시되도록 하는 메소드
    private void showCurrentLocation (Location location){
        LatLng curPoint =
                new LatLng(location.getLatitude(), location.getLongitude());

        String msg = "Latitude : " + curPoint.latitude
                + "\nLongitude : " + curPoint.longitude;

        //Log.d(TAG, "showCurrentLocation: " + msg);
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 18));

    } //showCurrentLocation()

    // 현재 위치를 반환하는 메소드
    private Location currentLocation () {
        LocationManager manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location lastLocation = null;
        try {
            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime, minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            showCurrentLocation(location);
                        } //onLocationChanged()
                    } //LocationListener()
            ); //manager.requestLocationUpdates()

            lastLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        } catch (SecurityException e) {
            Log.d(TAG, "currentLocation: " + e.getMessage());
        }

        return lastLocation;
    } //currentLocation()

    // 마커를 추가하는 메소드
    public void addMarker(LatLng latLng) {
        // 마커 크기 설정
        int height = 100;
        int width = 100;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.map_marker_icon);
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

        map.clear();    // 이전에 찍은 마커를 사라지게끔 하기위해 맵 초기화

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(smallMarkerIcon);
        markerOptions.position(latLng); //마커위치설정
        markerOptions.title(getCurrentAddress(latLng.latitude, latLng.longitude));
        // 마커를 찍은 주소를 마커 title에 표시되도록 함

        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));   // 마커생성위치로 이동
        Marker marker = map.addMarker(markerOptions);

        latitude = latLng.latitude;     // 마커를 찍은 위치의 위도, 경도를 변수에 저장
        longitude = latLng.longitude;
    }

    // 알림 대화상자를 보여주는 메소드
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // → 대화상자를 만들기 위한 빌더 객체 생성
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    } //showMessage()

    //----------------------------------------------------------------
    // 위험 권한
    private void checkDangerousPermissions () {
        String[] permissions = {
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, String[] permissions,
                                             int[] grantResults){
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
} //class