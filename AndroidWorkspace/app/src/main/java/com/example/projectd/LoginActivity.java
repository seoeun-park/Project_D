package com.example.projectd;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.projectd.ATask.KakaoJoin;
import com.example.projectd.ATask.LoginSelect;
import com.example.projectd.ATask.NaverJoin;
import com.example.projectd.ATask.NaverLogin;
import com.example.projectd.Dto.MemberDto;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "main:LoginActivity";

    public static MemberDto loginDTO = null;
    public static MemberDto naverLoginDTO = null;

    EditText etId, etPw;
    TextView id_pw_search, signUp;
    Button loginSubmitBtn;
    String state = "";
    String member_id, member_nickname, member_name, member_loginType;
    ImageView naverLoginBtn, kakaoLoginBtn;

    //네이버 로그인 관련
    // https://developers.naver.com/apps/#/myapps/4HyB0M2cnGc8fif15HRb/overview
    private static String OAUTH_CLIENT_ID = "cEfqX1rKoH7meFdpxDrl"; //클라이언트 아이디
    private static String OAUTH_CLIENT_SECRET = "ulRbzFfLxf";       //Client Secret Key
    private static String OAUTH_CLIENT_NAME = "대여안대여";         //애플리케이션 이름
    public static OAuthLogin mOAuthLoginInstance;
    public static OAuthLoginButton mOAuthLoginButton;
    public static Context mContext;
    public static Map<String, String> mUserInfoMap;

    //카카오 로그인 관련
    SessionCallback sessionCallback;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkDangerousPermissions();    //위험 권한 주기

        sessionCallback = new SessionCallback(getApplicationContext());

        id_pw_search = findViewById(R.id.id_pw_search);
        signUp = findViewById(R.id.signUp);
        loginSubmitBtn = findViewById(R.id.loginSubmitBtn);
        etId = findViewById(R.id.etId);
        etPw = findViewById(R.id.etPw);
        naverLoginBtn = findViewById(R.id.naverLoginBtn);
        kakaoLoginBtn = findViewById(R.id.kakaoLoginBtn);

        mContext = LoginActivity.this;

        //네이버 로그인 초기화
        initData();

        //키 값 발급
        getHashKey();

        naverLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOAuthLoginInstance.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
            }
        });

        // 아이디/비밀번호 찾기 화면 띄우기
        id_pw_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SearchIDActivity.class);
                startActivity(intent);
            }
        }); //id_pw_search.setOnClickListener()

        // 회원가입 화면 띄우기
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpFormActivity.class);
                startActivity(intent);
            }
        }); //signUp.setOnClickListener()

        // 로그인 버튼 클릭 시 실행되는 메소드
        loginSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginDTO = null;

                if(etId.getText().toString().length() != 0 && etPw.getText().toString().length() != 0) {
                    String member_id = etId.getText().toString();
                    String member_pw = etPw.getText().toString();

                    LoginSelect loginSelect = new LoginSelect(member_id, member_pw);

                    try {
                        loginSelect.execute().get();
                        // loginDTO에 값 저장
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "아이디와 암호를 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "아이디와 암호를 모두 입력하세요.");
                    return;
                }

                if(loginDTO != null) {      //회원정보가 DB에 저장 o
                    loginDTO.setMember_loginType("M");

                    String member_nickname = loginDTO.getMember_nickname();
                    String member_addr = loginDTO.getMember_addr();
                    Toast.makeText(LoginActivity.this, loginDTO.getMember_id() + "님 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", loginDTO.getMember_id() + "님 로그인 되었습니다.");

                    if (loginDTO.getMember_addr() == null || loginDTO.getMember_addr().equals("") ||
                       loginDTO.getMember_latitude() == null || loginDTO.getMember_latitude().equals("") ||
                       loginDTO.getMember_longitude() == null || loginDTO.getMember_longitude().equals("")) {
                        //웹으로 회원가입한 회원일 경우(위치정보 저장 x)
                        // 위치 지정 화면(SocialLocationActivity)으로 이동
                        Log.d(TAG, "onClick: 위치 지정 화면으로 이동");
                        Log.d(TAG, "onClick: 아이디 : " + loginDTO.getMember_id() + ", 로그인타입 : " + loginDTO.getMember_loginType());

                        Intent intent = new Intent(getApplicationContext(), SocialLocationActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);     //로그아웃할 때 앱 전체가 꺼지는 문제가 있어 주석처리함
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("member_id", loginDTO.getMember_id());
                        intent.putExtra("member_loginType", "M");
                        startActivity(intent);
                    } else {
                        //앱으로 회원가입 한 경우, 웹으로 회원가입했는데 위치정보가 저장되어 있는 경우
                        // 메인화면으로 이동
                        Log.d(TAG, "onClick: 메인 화면으로 이동, 주소는 " + loginDTO.getMember_addr());

                        Intent intent = new Intent(LoginActivity.this, RealMainActivity.class);
                        intent.putExtra("member_nickname", member_nickname);
                        intent.putExtra("member_addr", member_addr);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "아이디나 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "아이디나 비밀번호가 일치하지 않습니다.");
                }
                etId.setText("");
                etPw.setText("");
                etId.requestFocus();
            }
        }); //loginSubmitBtn.setOnClickListener()

        // 카카오세션 콜백 등록
        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);

        // 카카오 로그인 버튼 클릭 시
        kakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naverLoginDTO = null;
                loginDTO = null;
                session.open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);
                Log.d(TAG, "onClick: 카카오로그인완료");
            }
        });

        etId.setText("");
        etPw.setText("");

    } //onCreate()

    /****************************************************************************************************************
     * Kakao
     ****************************************************************************************************************/

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 카카오세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /****************************************************************************************************************
     * Naver
     ****************************************************************************************************************/

    private void initData() {
        //초기화
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

        mOAuthLoginButton = findViewById(R.id.naverLoginBtn);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        mOAuthLoginButton.setBgResourceId(R.drawable.naver_login);
    }

    // 네이버 로그인 관련 핸들러
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);

                Toast.makeText(mContext, "success:" + accessToken, Toast.LENGTH_SHORT).show();

                Log.d(TAG, "run: accessToken > " + accessToken);
                Log.d(TAG, "run: refreshToken > " + refreshToken);
                Log.d(TAG, "run: expiresAt > " + String.valueOf(expiresAt));
                Log.d(TAG, "run: tokenType > " + tokenType);
                Log.d(TAG, "run: getState() > " + mOAuthLoginInstance.getState(mContext).toString());

                RequestApiTask rat = new RequestApiTask(accessToken);
                rat.execute();

                //redirectSignupActivity();

            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "run: error");
            }
        }
    };

    private class RequestApiTask extends AsyncTask<Void, Void, StringBuffer> {
        private String token;

        RequestApiTask(String token) {
            this.token = token;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected StringBuffer doInBackground(Void... params) {
            Log.d(TAG, "doInBackground: 들어옴");
            String header = "Bearer " + token;
            try {
                final String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", header);
                int responseCode = con.getResponseCode();

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        responseCode == 200 ? con.getInputStream() : con.getErrorStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();
                while((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                br.close();
                return response;

            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(StringBuffer response) {
            super.onPostExecute(response);

            try {
                // response는 json encoded된 상태이기 때문에 json 형식으로 decode 해줘야 한다.
                JSONObject object = new JSONObject(response.toString());
                JSONObject innerJson = new JSONObject(object.get("response").toString());

                // 만약 이메일이 필요한데 사용자가 이메일 제공을 거부하면
                // JSON 데이터에는 email이라는 키가 없고, 이걸로 제공 여부를 판단한다.
                if(!innerJson.has("id")) {
                    Log.d(TAG, "onPostExecute: 이메일 정보 제공 동의 x");
                } else {
                    member_loginType = "N";

                    member_name = innerJson.getString("name");
                    Log.d(TAG, "onPostExecute: " + member_name);

                    member_id = member_loginType + innerJson.getString("email");
                    Log.d(TAG, "onPostExecute: " + member_id);

                    member_nickname = innerJson.getString("nickname");
                    Log.d(TAG, "onPostExecute: " + member_nickname);

                    //member_loginType = "N";
                    NaverLogin naverLogin = new NaverLogin(member_id, member_loginType);

                    try {
                        naverLogin.execute().get();
                        Log.d(TAG, "onSessionOpened: " + naverLoginDTO);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(naverLoginDTO == null) {      //네이버 로그인이 처음인 경우
                        Log.d(TAG, "onPostExecute: 로그인 액티비티에서 네이버 로그인 > dto가 null > db 저장 x");
                            NaverJoin naverJoin = new NaverJoin(member_id, member_nickname, member_name, member_loginType, token);
                        try {
                            state = naverJoin.execute().get().trim();
                            Log.d(TAG, "onSessionOpened: " + state);
                        } catch (Exception e) {
                            Log.d(TAG, "onPostExecute: " + e.getMessage());
                        }

                        if(state.equals("1")) {
                            Log.d(TAG, "onSuccess1: 네이버 회원가입 됐습니다");
                        } else {
                            Log.d(TAG, "onSuccess1: 회원가입 실패");
                        }

                        Intent intent = new Intent(getApplicationContext(), SocialLocationActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("member_id", member_id);
                        intent.putExtra("member_loginType", member_loginType);
                        startActivity(intent);

                    } else {     //네이버 로그인(db에 저장되어 있을 경우)이 되어있는 경우
                        if(naverLoginDTO.getMember_addr().equals("") || naverLoginDTO.getMember_addr() == null ||
                            naverLoginDTO.getMember_latitude().equals("") || naverLoginDTO.getMember_latitude() == null ||
                            naverLoginDTO.getMember_longitude().equals("") || naverLoginDTO.getMember_longitude() == null) {
                            // 네이버 로그인 계정이 DB에 저장되어 있지만 주소는 DB에 저장되지 않았을 경우
                            Log.d(TAG, "onPostExecute: 로그인 액티비티에서 네이버 로그인 > dto가 o 그러나 주소 지정 x");
                            Log.d(TAG, "onPostExecute: 주소 지정 x : " + naverLoginDTO.getMember_id());
                            Log.d(TAG, "onPostExecute: 주소 지정 x 로그인 타입: " + naverLoginDTO.getMember_loginType());
                            Intent intent = new Intent(getApplicationContext(), SocialLocationActivity.class);
                            intent.putExtra("member_id", naverLoginDTO.getMember_id());
                            intent.putExtra("member_loginType", naverLoginDTO.getMember_loginType());
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {        //주소 지정 o
                            Intent intent = new Intent(getApplicationContext(), RealMainActivity.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Log.d(TAG, "onSuccess: " + naverLoginDTO.getMember_addr());
                        }

                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            /*
            if (mUserInfoMap.get("email") == null) {
                Toast.makeText(mContext, "로그인 실패하였습니다.  잠시후 다시 시도해 주세요!!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onPostExecute: 로그인 실패");
            } else {

                //네이버 로그인 성공
                Log.d(TAG, 6+ String.valueOf(mUserInfoMap));
                member_id = mUserInfoMap.get("id");
                member_nickname = mUserInfoMap.get("nickname");
                Log.d(TAG, "onPostExecute: member_id = "+ member_id);
                Log.d(TAG, "onPostExecute: member_nickname = "+ member_nickname);

                NaverLogin naverLogin = new NaverLogin(member_id, member_loginType);
                try {
                    naverLogin.execute().get();
                    Log.d(TAG, "onSessionOpened: try 구문 안");
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(LoginActivity.this, "네이버 로그인 됐습니다...", Toast.LENGTH_SHORT).show();

                if(naverLoginDTO == null){
                    NaverJoin naverJoin = new NaverJoin(member_id, member_nickname, member_name, member_loginType, token);
                    try {
                        String state = naverJoin.execute().get();
                        Log.d(TAG, "onSessionOpened: " + state);
                    } catch (Exception e) {
                        Log.d(TAG, "onPostExecute: " + e.getMessage());
                    }

                    Toast.makeText(LoginActivity.this, "네이버 로그인 됐습니다...", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onSuccess: 네이버 로그인 됐습니다");

                }

                Intent intent = new Intent(getApplicationContext(), LogoutActivity.class);
                startActivity(intent);

                finish();
            }*/
        }
    } //class RequestApiTask

    // 성공 후 이동할 액티비티
    protected void redirectSignupActivity() {
        final Intent intent = new Intent(this, SocialLocationActivity.class);
        intent.putExtra("naver_login", "naver");
        intent.putExtra("member_id", member_id);
        intent.putExtra("member_loginType", member_loginType);
        startActivity(intent);
        finish();
        //Toast.makeText(mContext, "NAVER LOGIN", Toast.LENGTH_SHORT).show();
    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("HashKey", "HashKey:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("HashKey", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("HashKey", "HashKey Error. signature=" + signature, e);
            }
        }
    }


    //----------------------------------------------------------------
    // 위험 권한
    private void checkDangerousPermissions () {
        String[] permissions = {
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
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
                                             int[] grantResults) {
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