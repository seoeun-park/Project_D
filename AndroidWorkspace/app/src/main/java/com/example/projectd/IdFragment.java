package com.example.projectd;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectd.ATask.SearchId;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdFragment extends DialogFragment {
    private static final String TAG = "main:IdFragment";

    EditText etTel, etTelAuthNum;
    Button btnTelAuth, btnSearchId;
    String authNum, phoneNum;
    String message, title;  // 알림 정보를 담는 변수
    String state;           // 아이디 찾기 결과

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IdFragment newInstance(String param1, String param2) {
        IdFragment fragment = new IdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_id,
                viewGroup, false);

        btnSearchId = rootView.findViewById(R.id.btnSearchId);
        btnTelAuth = rootView.findViewById(R.id.btnTelAuth);
        etTel = rootView.findViewById(R.id.etTel);
        etTelAuthNum = rootView.findViewById(R.id.etTelAuthNum);

        // 인증 번호 받기 버튼 클릭 시
        btnTelAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel = etTel.getText().toString().trim();
                // 전화번호 형식 체크
                if (tel.length() == 0){  //전화번호를 입력하지 않은 경우
                    Toast.makeText(getContext(), "핸드폰 번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    etTel.requestFocus();
                    return;
                } else if (!Pattern.matches("^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$", tel)){
                    Toast.makeText(getContext(), "핸드폰 번호는 10 ~ 11자의 숫자만 사용가능합니다!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    phoneNum = etTel.getText().toString();
                    authNum = numberGen(4,2);
                    message = "인증번호는 " + authNum + " 입니다.\n" + "인증번호 입력칸에 입력해주세요!";
                    // 알림 상자로 인증번호 보여주기
                    showMessage("인증 번호", message);
                }

            } //onClick()
        }); //btnSearchId.setOnClickListener()

        // E-MAIL 찾기 버튼 클릭 시
        btnSearchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel = etTel.getText().toString().trim();
                // 전화번호 형식 체크
                if (tel.length() == 0){  //전화번호를 입력하지 않은 경우
                    Toast.makeText(getContext(), "핸드폰 번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    etTel.requestFocus();
                    return;
                } else if (!Pattern.matches("^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$", tel)){
                    Toast.makeText(getContext(), "핸드폰 번호는 10 ~ 11자의 숫자만 사용가능합니다!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (authNum.equals(etTelAuthNum.getText().toString())
                        && phoneNum.equals(etTel.getText().toString())) {
                        // 전송한 인증 번호 = 입력한 인증번호
                        // 인증번호를 보낸 휴대폰 번호 = 실제 입력한 휴대폰 번호(인증 번호를 보낸 번호를 나중에 바꿨을 경우)
                        SearchId searchId = new SearchId(tel);

                        try {
                            state = searchId.execute().get().trim();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (state.equals("")) {    //회원이 아닌 경우(아이디 x)
                            showMessage("알림", "가입된 아이디가 존재하지 않습니다");
                        } else {
                            // split : 문자열을 잘라 배열로 담음
                            // replace : 해당 문자열(매개변수 1)을 다른 문자열(매개변수 2)로 대체
                            // substring: 몇번째(매개변수 1)부터 몇번째(매개변수 2)까지의 문자열을 반환
                            String[] split = state.split("@");
                            // split[0] == 'admin' / split[1] == 'hanul.com'
                            int split0length = split[0].length();   //5
                            String email = split[0].replace(split[0].substring(split0length - 3, split0length), "***");
                            email += "@" + split[1];
                            message = "고객님의 정보와 일치하는 아이디입니다.\n" + "\n"
                                    + email;
                            showMessage("아이디 찾기", message);
                        }
                    } else {
                        Toast.makeText(getContext(), "인증번호가 맞지 않습니다.\n" + "다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                //Toast.makeText(getContext(),  etAuthNum.getText().toString() + ", " + authNum + ", " + etTel.getText().toString(), Toast.LENGTH_LONG).show();
            } //onClick()
        }); //btnSearchId.setOnClickListener()

        return rootView;
    } //onCreateView()

    // 알림 대화상자를 보여주는 메소드
    public void showMessage(String title, String message) {

        // getContext 안됨 / getActivity() 안됨 / getActivity().getApplicationContext() 안됨
        //getActivity(), R.style.Theme_AppCompat_Dialog_Alert 안됨
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

    // 인증번호 (4자리 난수) 생성 메소드
    public static String numberGen(int len, int dupCd) {
        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수

        for (int i = 0; i < len; i++) {
            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));

            if (dupCd == 1) {
                //중복 허용시 numStr에 append
                numStr += ran;
            } else if (dupCd == 2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
        }
        return numStr;
    } //numberGen()
}