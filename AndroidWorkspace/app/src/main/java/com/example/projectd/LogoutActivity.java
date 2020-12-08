package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nhn.android.naverlogin.OAuthLogin;

import static com.example.projectd.LoginActivity.loginDTO;
import static com.example.projectd.LoginActivity.mContext;
import static com.example.projectd.LoginActivity.mOAuthLoginInstance;

public class LogoutActivity extends AppCompatActivity {
    Button logout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        logout = findViewById(R.id.logout);
        textView = findViewById(R.id.textView);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginDTO = null;
                OAuthLogin.getInstance().logout(mContext);  //네이버 로그아웃
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);

                textView.setText("accessToken : " + accessToken + ", refreshToken : " + refreshToken);
            }
        });
    } //onCreate()
}