package com.example.projectd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectd.ATask.ListDelete;
import com.example.projectd.ATask.LoginSelect;
import com.example.projectd.ATask.ProfileSubUpdate;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static com.example.projectd.Common.CommonMethod.isNetworkConnected;
import static com.example.projectd.LoginActivity.loginDTO;
import static com.example.projectd.LoginActivity.naverLoginDTO;

public class ProfilSubActivity extends AppCompatActivity {
    private static final int SOCAIL_LOCATION_ACTIVITY_REQUEST_CODE = 0;
    private static final String TAG = "main:ProfilSubActivity";

    EditText name1, nickname1, phone1, addr1;
    String name, nickname, tel, addr, latitude, longitude, member_loginType;
    LinearLayout toolbar_context;
    ImageView updateLocation_p;

    File file = null;
    long fileSize = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_sub);

        name1 = findViewById(R.id.name);
        nickname1 = findViewById(R.id.nickname);
        phone1 = findViewById(R.id.phone);
        addr1 = findViewById(R.id.addr);
        toolbar_context = findViewById(R.id.toolbar_context);
        updateLocation_p = findViewById(R.id.updateLocation_p);

        if(loginDTO != null) {

            String member_name = LoginActivity.loginDTO.getMember_name();
            String member_nickname = LoginActivity.loginDTO.getMember_nickname();
            String member_phone = LoginActivity.loginDTO.getMember_tel();
            String member_addr = LoginActivity.loginDTO.getMember_addr();

            name1.setText(member_name);
            nickname1.setText(member_nickname);
            phone1.setText(member_phone);
            addr1.setText(member_addr);

            member_loginType = "M";

        }else if(LoginActivity.naverLoginDTO != null){
            String member_name = LoginActivity.naverLoginDTO.getMember_name();
            String member_nickname = LoginActivity.naverLoginDTO.getMember_nickname();
            String member_phone = LoginActivity.naverLoginDTO.getMember_tel();
            String member_addr = LoginActivity.naverLoginDTO.getMember_addr();

            name1.setText(member_name);
            nickname1.setText(member_nickname);
            phone1.setText(member_phone);
            addr1.setText(member_addr);

            member_loginType = naverLoginDTO.getMember_loginType();

        }else if(SessionCallback.kakaoLoginDTO != null){
            String member_name = SessionCallback.kakaoLoginDTO.getMember_name();
            String member_nickname = SessionCallback.kakaoLoginDTO.getMember_nickname();
            String member_phone = SessionCallback.kakaoLoginDTO.getMember_tel();
            String member_addr = SessionCallback.kakaoLoginDTO.getMember_addr();

            name1.setText(member_name);
            nickname1.setText(member_nickname);
            phone1.setText(member_phone);
            addr1.setText(member_addr);

            member_loginType = SessionCallback.kakaoLoginDTO.getMember_loginType();
        }

        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //gps 버튼 클릭 시 위치 지정 화면으로 이동
        updateLocation_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SocialLocationActivity.class);
                intent.putExtra("update_location_p", true);
                intent.putExtra("member_loginType", member_loginType);
                startActivityForResult(intent, SOCAIL_LOCATION_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    //프로필 수정
    public void btnSubUpdateClicked(View view){
        if(isNetworkConnected(this) == true){


                name = name1.getText().toString();
                nickname = nickname1.getText().toString();
                tel = phone1.getText().toString();
                addr = addr1.getText().toString();

                if ( loginDTO != null){
                    String id = loginDTO.getMember_id();
                    ProfileSubUpdate profilSubUpdate = new ProfileSubUpdate(id, name, nickname, tel, addr, latitude, longitude);
                    profilSubUpdate.execute();

                    String member_id = loginDTO.getMember_id();
                    String member_pw = loginDTO.getMember_pw();
                    LoginSelect select = new LoginSelect(member_id, member_pw);
                    loginDTO = null;

                    try {
                        select.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }
                }else if( LoginActivity.naverLoginDTO != null){
                    String id = LoginActivity.naverLoginDTO.getMember_id();
                    ProfileSubUpdate profilSubUpdate = new ProfileSubUpdate(id, name, nickname, tel, addr, latitude, longitude);
                    profilSubUpdate.execute();

                    String member_id = LoginActivity.naverLoginDTO.getMember_id();
                    String member_pw = LoginActivity.naverLoginDTO.getMember_pw();
                    LoginSelect select = new LoginSelect(member_id, member_pw);
                    LoginActivity.naverLoginDTO = null;

                    try {
                        select.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }

                }else if(SessionCallback.kakaoLoginDTO != null){
                    String id = SessionCallback.kakaoLoginDTO.getMember_id();
                    ProfileSubUpdate profilSubUpdate = new ProfileSubUpdate(id, name, nickname, tel, addr, latitude, longitude);
                    profilSubUpdate.execute();

                    String member_id = SessionCallback.kakaoLoginDTO.getMember_id();
                    String member_pw = SessionCallback.kakaoLoginDTO.getMember_pw();
                    LoginSelect select = new LoginSelect(member_id, member_pw);
                    SessionCallback.kakaoLoginDTO = null;

                    try {
                        select.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }

                }

                Toast.makeText(getApplicationContext(), "수정성공", Toast.LENGTH_LONG).show();

                Intent showIntent = new Intent(getApplicationContext(), ProfilActivity.class);
    /*            showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                        Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                        Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
    */            startActivity(showIntent);

                finish();
        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }
    //계정 삭제
    public void btnDeleteClicked(View v){
        if(isNetworkConnected(this) == true){

            if(loginDTO != null){
                Log.d("Sub1 : selImg => ", loginDTO.getMember_profile());

                ListDelete listDelete = new ListDelete(loginDTO.getMember_id(), loginDTO.getMember_profile());
                listDelete.execute();
            }else if(LoginActivity.naverLoginDTO != null){
                Log.d("Sub1 : selImg => ", LoginActivity.naverLoginDTO.getMember_profile());

                ListDelete listDelete = new ListDelete(LoginActivity.naverLoginDTO.getMember_id(), LoginActivity.naverLoginDTO.getMember_profile());
                listDelete.execute();

            }else if (SessionCallback.kakaoLoginDTO != null){
                Log.d("Sub1 : selImg => ", SessionCallback.kakaoLoginDTO.getMember_profile());

                ListDelete listDelete = new ListDelete(SessionCallback.kakaoLoginDTO.getMember_id(), SessionCallback.kakaoLoginDTO.getMember_profile());
                listDelete.execute();
            }

                // 화면갱신
                Intent refresh = new Intent(this, LoginActivity.class);
                startActivity(refresh);
                this.finish(); // 화면끝내기


        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show(); // 테스트 111
        }
    }
    //취소
    public void btnCancelClicked(View view){
        finish();
    }

    // SocailLocationActivity에서 전달한 위치 정보를 받는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SOCAIL_LOCATION_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            String myAddress = data.getStringExtra("member_addr");
            //etLocation.setText(myAddress);
            addr1.setText(myAddress);
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");
            Log.d(TAG, "onActivityResult: 갱신된 위도 : " + latitude + ", 갱신된 경도 : " + longitude);
        }
    }//onActivityResult()
}