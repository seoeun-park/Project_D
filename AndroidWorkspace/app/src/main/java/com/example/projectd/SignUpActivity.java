package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    Button signUpBtn, naverJoinBtn, kakaoJoinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpBtn = findViewById(R.id.signUpBtn);
        naverJoinBtn = findViewById(R.id.naverJoinBtn);
        kakaoJoinBtn = findViewById(R.id.kakaoJoinBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignUpFormActivity.class);
                startActivity(intent);
            }
        }); //signUpBtn.setOnClickListener()

        naverJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, "네이버 로그인", Toast.LENGTH_SHORT).show();
            }
        }); //naverJoinBtn.setOnClickListener()

        kakaoJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, "카카오 로그인", Toast.LENGTH_SHORT).show();
            }
        });


    } //onCreate()
} //class