package com.example.projectd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectd.ATask.SignUpCheckId;
import com.example.projectd.ATask.SignUpCheckNickName;
import com.example.projectd.ATask.SignUpInsert;
import com.example.projectd.Dto.MemberDto;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFormActivity extends AppCompatActivity implements View.OnClickListener, Dialog.OnCancelListener{
    private static final String TAG = "main:SignUpFormActivity";

    static GMailSender sender = new GMailSender("dteam0420@gmail.com", "hanul123");

    public static MemberDto idCheckDTO = null;
    public static MemberDto nicknamecheckDTO = null;

    Button btnEmailAuth, btnLocSearch, btnSubmit;
    EditText etId, etName, etPw, etNickName,
            etTel, etLocation, etPwCheck, etDetailAdd;
    TextView tvIdCheck, tvNameCheck, tvPwCheck1, tvPwCheck2, tvNickNameCheck, tvTelCheck;
    String state;
    String member_id, member_pw, member_name,
            member_nickname, member_tel, member_addr,
            member_latitude, member_longitude, member_loginType, member_token;
    private static final int LOCATION_ACTIVITY_REQUEST_CODE = 0;
    LinearLayout toolbar_context;   // 툴바를 감싸는 레이아웃

    //중복 확인 & 이메일 인증 여부를 확인하는 변수
    boolean idCheck = false;
    String checkIdStr = "";

    //Dialog에 관련된 변수
    LayoutInflater dialog;      //LayoutInflater
    View dialogLayout;          //layout을 담을 View
    Dialog authDialog;          //dialog 객체

    //카운트 다운 타이머에 관련된 변수
    TextView time_counter;      //시간을 보여주는 TextView
    EditText emailAuth_number;  //인증 번호를 입력 하는 칸
    Button emailAuth_btn;       // 인증버튼
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);

        btnEmailAuth = findViewById(R.id.btnTelAuth);
        btnLocSearch = findViewById(R.id.btnLocSearch);
        btnSubmit = findViewById(R.id.btnSubmit);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etPw = findViewById(R.id.etPw);
        etNickName = findViewById(R.id.etNickName);
        etTel = findViewById(R.id.etTel);
        etLocation = findViewById(R.id.etLocation);
        etPwCheck = findViewById(R.id.etPwCheck);
        etDetailAdd = findViewById(R.id.etDetailAdd);

        tvIdCheck = findViewById(R.id.tvIdCheck);
        tvNameCheck = findViewById(R.id.tvNameCheck);
        tvPwCheck1 = findViewById(R.id.tvPwCheck1);
        tvPwCheck2 = findViewById(R.id.tvPwCheck2);
        tvNickNameCheck = findViewById(R.id.tvNickNameCheck);
        tvTelCheck = findViewById(R.id.tvTelCheck);

        toolbar_context = findViewById(R.id.toolbar_context);

        // 아이디(이메일) 입력 칸에 값을 입력한 후 발생하는 key 리스너
       etId.setOnKeyListener(new View.OnKeyListener() {
            @Override  // 키입력 처리 메서드
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String id = etId.getText().toString();

                if(event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER){
                    if(id.length() == 0 ){  //아이디를 입력하지 않은 경우
                        tvIdCheck.setText("아이디를 입력하세요!");
                        tvIdCheck.setVisibility(View.VISIBLE);
                        etId.requestFocus();
                        return false;
                    } else if (!checkEmail(id)) { //이메일 형식에 맞지 않는 경우
                        tvIdCheck.setText("아이디는 이메일로 입력해주세요!");
                        tvIdCheck.setVisibility(View.VISIBLE);
                        etId.setText("");
                        etId.requestFocus();
                        return false;

                    } else {
                        //아이디 중복 체크
                        SignUpCheckId signUpCheckId = new SignUpCheckId(id);

                        try {
                            signUpCheckId.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (idCheckDTO != null) {
                            tvIdCheck.setText("사용 불가능한 아이디 입니다!");
                            tvIdCheck.setVisibility(View.VISIBLE);
                            idCheckDTO = null;
                            return false;
                        } else {
                            tvIdCheck.setText("인증하기 버튼을 눌러주세요!");
                            tvIdCheck.setVisibility(View.VISIBLE);
                            idCheck = true;
                            idCheckDTO = null;
                        }
                    }
                }
                return false;
            } //onKey()
        }); //etId.setOnKeyListener()

        // (이메일) 인증하기 버튼 클릭 리스너 > 하단 클릭 메소드 참고
        btnEmailAuth.setOnClickListener(this);

        // 이름 입력 칸에 값을 입력한 후 발생하는 key 리스너
        etName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                String name = etName.getText().toString();

                if(event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER){
                    if(name.length() == 0 ){  //이름을 입력하지 않은 경우
                        tvNameCheck.setText("이름을 입력하세요!");
                        tvNameCheck.setVisibility(View.VISIBLE);
                        etName.requestFocus();
                        return false;
                    }
                }
                return false;
            } //onKey()
        }); //etName.setOnKeyListener()

        //메일전송을 위한 준비
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        // 비밀번호 일치 여부 확인
        etPwCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 변수는 onTextChanged 메소드 안에서 정의를 해야 실시간으로 값이 갱신된다.
                String pwText = etPw.getText().toString();
                String pwCheckText = etPwCheck.getText().toString();

                if (!pwText.equals(pwCheckText)) {
                    tvPwCheck2.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onTextChanged: " + pwText + ", " + pwCheckText);
                } else {
                    tvPwCheck2.setVisibility(View.GONE);
                    Log.d(TAG, "onTextChanged: " + pwText + ", " + pwCheckText);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        }); //pwValueCheck.addTextChangedListener()

        // 비밀번호 입력 칸에 값을 입력한 후 발생하는 key 리스너
        etPw.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String pw = etPw.getText().toString().trim();

                if(event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(pw.length() == 0 ){  //아이디를 입력하지 않은 경우
                        tvPwCheck1.setText("비밀번호를 입력하세요!");
                        tvPwCheck1.setVisibility(View.VISIBLE);
                        etPw.requestFocus();
                        return false;
                    } else if (!isValidPassword(pw)) { //비밀번호 형식에 맞지 않는 경우
                        tvPwCheck1.setText("비밀번호는 최소 8자 이상, 알파벳 소문자, 숫자 및 특수 문자를 사용하십시오!");
                        tvPwCheck1.setVisibility(View.VISIBLE);
                        etPw.setText("");
                        etPw.requestFocus();
                        return false;
                    } else {
                        tvPwCheck1.setVisibility(View.GONE);
                    }
                }
                return false;
            } //onKey()
        }); //etPw.setOnKeyListener()

        // 닉네임 입력 칸에 값을 입력한 후 발생하는 key 리스너
        etNickName.setOnKeyListener(new View.OnKeyListener() {
            @Override  // 키입력 처리 메서드
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String nickname = etNickName.getText().toString();

                if(event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER){
                    if(nickname.length() == 0 ) {  //닉네임을 입력하지 않은 경우
                        tvNickNameCheck.setText("닉네임을 입력하세요!");
                        tvNickNameCheck.setVisibility(View.VISIBLE);
                        etNickName.setText("");
                        etNickName.requestFocus();
                        return false;
                    } else if (!Pattern.matches("^[a-zA-Z가-힣ㄱ-ㅎ]{2,8}$", nickname)) {
                        //이메일 형식에 맞지 않는 경우
                        Toast.makeText(SignUpFormActivity.this, "닉네임 : " + Pattern.matches("/^[\\w\\Wㄱ-ㅎ|가-힣]{2,8}$/", nickname), Toast.LENGTH_SHORT).show();
                        tvNickNameCheck.setText("닉네임은 2~8 글자, 영문 대소문자,\n숫자, 한글만 사용가능 합니다.");
                        tvNickNameCheck.setVisibility(View.VISIBLE);
                        etNickName.setText("");
                        etNickName.requestFocus();
                        return false;

                    } else {
                        //닉네임 중복 체크
                        Log.d(TAG, "onKey: " + nickname);
                        SignUpCheckNickName signUpCheckNickName = new SignUpCheckNickName(nickname);

                        try {
                            signUpCheckNickName.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (nicknamecheckDTO != null) {
                            tvNickNameCheck.setText("사용 불가능한 닉네임입니다!");
                            tvNickNameCheck.setVisibility(View.VISIBLE);
                            nicknamecheckDTO = null;
                            return false;
                        } else {
                            tvNickNameCheck.setText("사용 가능한 닉네임입니다!");
                            tvNickNameCheck.setVisibility(View.VISIBLE);
                            //tvNickNameCheck.setVisibility(View.GONE);
                            nicknamecheckDTO = null;
                        }
                    }
                }
                return false;
            } //onKey()
        }); //etId.setOnKeyListener()

        // 휴대폰 번호 입력 칸에 값을 입력한 후 발생하는 key 리스너
        etTel.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String tel = etTel.getText().toString().trim();

                if(event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER){
                    if(tel.length() == 0) {  //아이디를 입력하지 않은 경우
                        tvTelCheck.setText("전화번호를 입력하세요!");
                        tvTelCheck.setVisibility(View.VISIBLE);
                        etTel.setText("");
                        etTel.requestFocus();
                        return false;
                    } else if (!Pattern.matches("^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$", tel)) {
                        //이메일 형식에 맞지 않는 경우
                        tvTelCheck.setText("전화번호를 잘못 입력하셨습니다!");
                        tvTelCheck.setVisibility(View.VISIBLE);
                        etTel.setText("");
                        etTel.requestFocus();
                        return false;

                    } else {
                        tvTelCheck.setText("전화번호를 제대로 입력하셨습니다!");
                        tvTelCheck.setVisibility(View.VISIBLE);
                    }
                }

                return false;
            }//onKey()
        });

        // 주소 가져오기
        Intent intent = getIntent();
        etLocation.setText(intent.getStringExtra("myAddress"));
        
        //위치 찾기 버튼 클릭했을 때
        btnLocSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpFormActivity.this,
                        LocationActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, LOCATION_ACTIVITY_REQUEST_CODE);
            }
        }); //locationSearchBtn.setOnClickListener()

        // 회원 가입 버튼 클릭했을 때
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                member_id = etId.getText().toString();
                member_pw = etPw.getText().toString();
                member_name = etName.getText().toString();
                member_nickname = etNickName.getText().toString();
                member_tel = etTel.getText().toString();
                member_addr = etLocation.getText().toString();

                // 이메일 중복체크 여부 & 이메일 인증이 안되어 있을 경우 회원가입x
                if(member_id.length() == 0) {
                    Toast.makeText(SignUpFormActivity.this, "아이디를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    etId.requestFocus();
                    return;
                } else if(!checkEmail(member_id)) {
                    Toast.makeText(SignUpFormActivity.this, "아이디는 이메일 형식으로 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    //아이디 중복 체크
                    SignUpCheckId signUpCheckId = new SignUpCheckId(member_id);

                    try {
                        signUpCheckId.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (idCheckDTO != null) {
                        Toast.makeText(SignUpFormActivity.this, "사용 불가능한 아이디입니다!", Toast.LENGTH_SHORT).show();
                        idCheckDTO = null;
                        return;
                    } else if (!checkIdStr.equals(member_id) || member_token == null) {
                        Toast.makeText(SignUpFormActivity.this, "이메일 인증을 진행하여 주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.d(TAG, "onClick: member_token : " + member_token);
                }

                if (member_name.length() == 0) {
                    Toast.makeText(SignUpFormActivity.this, "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                }

                //비밀번호 형식 체크
                if (member_pw.length() == 0){   //비밀번호를 입력하지 않은경우
                    Toast.makeText(SignUpFormActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPw.requestFocus();
                    return;
                } else if (member_pw.length() < 8 &&!isValidPassword(member_pw)){   //비밀번호 형식에 맞지 않은 경우
                    Toast.makeText(SignUpFormActivity.this, "올바른 비밀번호 형식이 아닙니다!\n비밀번호는 8자 이상, 알파벳, 숫자 및 특수 문자를 포함해주세요!", Toast.LENGTH_SHORT).show();
                    etPw.setText("");
                    etPw.requestFocus();
                    return;
                }

                //비밀번호 확인 체크
                if (etPwCheck.getText().toString().trim().length() == 0){
                    Toast.makeText(SignUpFormActivity.this, "비밀번호 확인란을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    etPwCheck.requestFocus();
                    return;
                } else if(!etPwCheck.getText().toString().equals(member_pw)){
                    Toast.makeText(SignUpFormActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    etPwCheck.setText("");
                    return;
                }

                // 닉네임 유효성 검사 & 중복확인 체크
                if(member_nickname.length() == 0 ) {  //닉네임을 입력하지 않은 경우
                    Toast.makeText(SignUpFormActivity.this, "닉네임을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    etNickName.requestFocus();
                    return;
                } else if (!Pattern.matches("^[a-zA-Z가-힣ㄱ-ㅎ]{2,8}$", member_nickname)) {
                    //닉네임 형식에 맞지 않는 경우
                    Toast.makeText(SignUpFormActivity.this, "닉네임은 2~8 글자 영문 대소문자, 숫자, 한글만 사용가능 합니다.", Toast.LENGTH_SHORT).show();
                    etNickName.setText("");
                    return;

                } else {
                    //아이디 중복 체크
                    SignUpCheckNickName signUpCheckNickName = new SignUpCheckNickName(member_nickname);

                    try {
                        signUpCheckNickName.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (nicknamecheckDTO != null) {
                        Toast.makeText(SignUpFormActivity.this, "사용 불가능한 닉네임입니다!", Toast.LENGTH_SHORT).show();
                        nicknamecheckDTO = null;
                        etNickName.setText("");
                        return;
                    }
                }

                // 전화번호 형식 체크
                if (member_tel.length() == 0){  //전화번호를 입력하지 않은 경우
                    Toast.makeText(SignUpFormActivity.this, "전화번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    etTel.requestFocus();
                    return;
                } else if (!Pattern.matches("^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$", member_tel)){
                    Toast.makeText(SignUpFormActivity.this, "전화번호는 10 ~ 11자의 숫자만 사용가능합니다!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 위치 & 상세정보 체크
                String detailAddress = etDetailAdd.getText().toString().trim();

                if (member_addr.length() == 0 || member_addr.equals("주소 미입력")
                        || member_latitude == null || member_longitude == null ) {
                    Toast.makeText(SignUpFormActivity.this, "위치 찾기 버튼으로 위치를 지정해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (detailAddress.length() == 0) {
                    Toast.makeText(SignUpFormActivity.this, "상세주소를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                member_addr += " " + detailAddress;   // 상세주소 정보를 member_addr 변수에 추가

                SignUpInsert signUpInsert = new SignUpInsert(member_id, member_pw, member_nickname,
                                                             member_tel, member_addr, member_latitude, member_longitude,  member_name, member_token);
                try {
                    state = signUpInsert.execute().get().trim();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (state.equals("1")) {
                    Toast.makeText(SignUpFormActivity.this, "회원가입되셨습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "삽입성공 !!!");
                    finish();
                } else {
                    Toast.makeText(SignUpFormActivity.this, "회원가입에 실패하셨습니다. 관리자에게 문의하세요", Toast.LENGTH_LONG).show();
                    Log.d("main:joinact", "삽입실패 !!!");
                }
            } //btnSubmit.onClick()
        });

        // 툴바 안에 뒤로가기 버튼 눌렀을 때
        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    } //onCreate()

    // LocationActivity에서 전달한 위치 정보를 받는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOCATION_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            String myAddress = data.getStringExtra("myAddress");
            //etLocation.setText(myAddress);
            etLocation.setText(myAddress);
            member_latitude = String.valueOf(data.getDoubleExtra("latitude", 0));
            member_longitude = String.valueOf(data.getDoubleExtra("longitude", 0));
            etLocation.setFocusable(true);
        } else {
            etLocation.setText("주소 미입력");

        }
    }//onActivityResult()

    //이메일 형식 체크
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    //비밀번호 형식 체크 (암호는 최소 8자 이상, 알파벳 소문자 1개, 숫자 1개 및 특수 문자 1개를 사용)
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    // 이메일 인증하기 버튼을 클릭했을 때 발생하는 메소드
    @Override
    public void onClick(View view) {
        String id = etId.getText().toString().trim();

        if (id.length() == 0) {  //아이디를 입력하지 않은 경우
            tvIdCheck.setText("아이디를 입력하세요!");
            tvIdCheck.setVisibility(View.VISIBLE);
            etId.setText("");
            etId.requestFocus();
            return;
        } else if (!checkEmail(id)) { //이메일 형식에 맞지 않는 경우
            tvIdCheck.setText("이메일 형식으로 입력해주세요!");
            tvIdCheck.setVisibility(View.VISIBLE);
            etId.setText("");
            etId.requestFocus();
            return;
        } else {
            //아이디 중복 체크
            SignUpCheckId signUpCheckId = new SignUpCheckId(id);

            try {
                signUpCheckId.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (idCheckDTO != null) {
                tvIdCheck.setText("사용 불가능한 아이디 입니다!");
                tvIdCheck.setVisibility(View.VISIBLE);
                idCheckDTO = null;
                return;
            } else {
                tvIdCheck.setText("이메일을 통해 전송된 인증번호를 확인하세요!");
                tvIdCheck.setVisibility(View.VISIBLE);
                idCheck = true;
                idCheckDTO = null;

                //G메일 전송
                try {
                    final String code = sender.getEmailCode();

                    Log.d(TAG, "onClick: " + id);
                    sender.sendMail("대여 안대여 - 이메일 인증을 진행해 주세요!",
                            "이메일 인증번호는 "+ sender.getEmailCode()+ "입니다. \n인증번호를 입력해주세요!",
                            "dteam0420@gmail.com",
                            id);
                    Log.d(TAG, "onClick: " + code);


                    //mailSender(id);
                    //인증 다이얼로그 생성
                    dialog = LayoutInflater.from(this);
                    dialogLayout = dialog.inflate(R.layout.layout_auth_dialog, null); // LayoutInflater를 통해 XML에 정의된 Resource들을 View의 형태로 반환 시켜 줌
                    authDialog = new Dialog(this); //Dialog 객체 생성
                    authDialog.setContentView(dialogLayout); //Dialog에 inflate한 View를 탑재 하여줌
                    authDialog.setCanceledOnTouchOutside(false); //Dialog 바깥 부분을 선택해도 닫히지 않게 설정함.
                    authDialog.show();
                    countDownTimer();

                    //다이얼로그 내 인증번호 입력 확인버튼 클릭시
                    emailAuth_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String user_answer = emailAuth_number.getText().toString().trim();

                            if (user_answer.equals(sender.getEmailCode())){
                                Toast.makeText(getApplicationContext(), "이메일 인증 성공", Toast.LENGTH_SHORT).show();
                                checkIdStr = etId.getText().toString();
                                member_token = code;
                                authDialog.cancel();
                            } else{
                                Toast.makeText(getApplicationContext(), "인증 번호를 다시 입력해주세요!", Toast.LENGTH_SHORT).show();
                                emailAuth_number.setText("");
                                emailAuth_number.requestFocus();
                                return;
                            }
                            tvIdCheck.setText("이메일 인증성공!");
                            tvIdCheck.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }

            }
        }

    } //onClick()

    /*
    public static void mailSender(String email) {
        try {
            sender.sendMail("대여 안대여 - 이메일 인증을 진행해 주세요!",
                    "이메일 인증번호는 "+ sender.getEmailCode()+ "입니다. \n인증번호를 입력해주세요!",
                    "dteam0420@gmail.com",
                    email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
    public void countDownTimer() { //카운트 다운 메소드

        time_counter = (TextView) dialogLayout.findViewById(R.id.emailAuth_time_counter);
        //줄어드는 시간을 나타내는 TextView
        emailAuth_number = (EditText) dialogLayout.findViewById(R.id.emailAuth_number);
        //사용자 인증 번호 입력창
        emailAuth_btn = (Button) dialogLayout.findViewById(R.id.emailAuth_btn);
        //인증하기 버튼

        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)

                long emailAuthCount = millisUntilFinished / 1000;
                Log.d("Alex", emailAuthCount + "");

                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    time_counter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    time_counter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }

                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.

            } //onTick()
            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료

                authDialog.cancel();

            }
        }.start();

        emailAuth_btn.setOnClickListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        countDownTimer.cancel();
    } //다이얼로그 닫을 때 카운트 다운 타이머의 cancel()메소드 호출


    /*
    // 액션 바 뒤로가기 기능을 추가한 메소드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();

        switch (curId) {
            case android.R.id.home :
                // → android.R.id.home : 메뉴바 상단의 아이콘(뒤로가기) 아이디
                this.finish();      // 앱 종료
                break;
        }
        return true;
    } //onOptionsItemSelected()
    */
} //class