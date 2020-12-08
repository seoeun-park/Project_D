package com.example.projectd;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.google.android.material.tabs.TabLayout;

import static com.example.projectd.SignUpFormActivity.sender;
//import static com.example.projectd.SignUpFormActivity.mailSender;

public class SearchIDActivity extends AppCompatActivity {
    private static final String TAG = "SearchIDActivity";

    //GMailSender sender = new GMailSender("dteam0420@gmail.com", "hanul123");

    Toolbar toolbar;

    IdFragment fragment1;
    PwFragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_i_d);

        //로그인가기
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }); //btn_back.setOnClickListener()

        //아이디/비밀번호 찾기 프레그 먼트 연결
        fragment1 = new IdFragment();
        fragment2 = new PwFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment1).commit();

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("아이디찾기"));
        tabs.addTab(tabs.newTab().setText("비밀번호 찾기"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);

                Fragment selected = null;
                if (position == 0){
                    selected = fragment1;
                }else if (position == 1){
                    selected = fragment2;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }//onTabSelected




            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        /*
        testemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = pwFragment_id.getText().toString().trim();
                Toast.makeText(SearchIDActivity.this, id, Toast.LENGTH_SHORT).show();

                //G메일 전송
                try {
                    Toast.makeText(SearchIDActivity.this, "이메일 보냄", Toast.LENGTH_SHORT).show();
                    sender.sendMail("대여 안대여 - 이메일 인증을 진행해 주세요!",
                            "이메일 인증번호는 "+ sender.getEmailCode()+ "입니다. \n인증번호를 입력해주세요!",
                            "dteam0420@gmail.com",
                            id);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }

                //mailSender(id);
            }
        });
        */

    }//onCreate


}//class