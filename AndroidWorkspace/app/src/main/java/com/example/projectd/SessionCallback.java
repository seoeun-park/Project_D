package com.example.projectd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.projectd.ATask.KakaoJoin;
import com.example.projectd.ATask.KakaoLogin;
import com.example.projectd.Dto.MemberDto;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

import java.util.concurrent.ExecutionException;

import static com.example.projectd.LoginActivity.loginDTO;

public class SessionCallback implements ISessionCallback {
    private static final String TAG = "main:SessionCallback";

    String member_id = null;
    String member_nickname = null;
    Context context;
    public static MemberDto kakaoLoginDTO = null;
    String member_loginType = "K";
    String member_token = "";

    String state;

    public SessionCallback(Context context) {
        this.context = context;
    }

    // 카카오로그인에 성공한 상태
    @Override
    public void onSessionOpened() {
        requestMe();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Toast.makeText(context, "카카오 로그인 됐습니다...", Toast.LENGTH_SHORT).show();

        /*LoginSelect loginSelect = new LoginSelect(member_email, "0000");
        try {
            String result = loginSelect.execute().get();
            Log.d(TAG, "onSessionOpened: "+result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(loginDTO == null){
            JoinInsert joinInsert = new JoinInsert(member_email, "0000", member_nickname,"");
            try {
                joinInsert.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loginDTO = new MemberDTO(member_email, member_nickname);
            Log.d(TAG, "onSuccess: 카카오로그인됐습니다"+loginDTO.getMember_email());
        }*/
    }

    // 로그인에 실패한 상태
    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
    }

    // 사용자 정보 요청
    public void requestMe() {
        UserManagement.getInstance()
                .me(new MeV2ResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.d("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.d("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {

                        Log.d("KAKAO_API", "사용자 아이디: " + result.getId());

                        UserAccount kakaoAccount = result.getKakaoAccount();
                        if (kakaoAccount != null) {

                            // 이메일
                            String email = kakaoAccount.getEmail();

                            if (email != null) {
                                Log.d("KAKAO_API", "email: " + email);
                                member_id = "K" + email;    //member_id는 중복값을 가지면 안되므로 앞에 K를 붙임

                            } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                                // 동의 요청 후 이메일 획득 가능
                                // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.

                            } else {
                                // 이메일 획득 불가
                            }

                            // 프로필
                            Profile profile = kakaoAccount.getProfile();

                            if (profile != null) {
                                member_nickname = profile.getNickname();

                                Log.d("KAKAO_API", "nickname: " + profile.getNickname());
                                Log.d("KAKAO_API", "profile image: " + profile.getProfileImageUrl());
                                Log.d("KAKAO_API", "thumbnail image: " + profile.getThumbnailImageUrl());

                            } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                                // 동의 요청 후 프로필 정보 획득 가능

                            } else {
                                // 프로필 획득 불가
                            }

                            KakaoLogin kakaoLogin = new KakaoLogin(member_id, member_loginType);
                            //Log.d(TAG, "onSuccess: " + kakaoLoginDTO.getMember_id() + ", " + kakaoLoginDTO.getMember_loginType());
                            try {
                                kakaoLogin.execute().get();
                                Log.d(TAG, "onSessionOpened: " + kakaoLoginDTO);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if(kakaoLoginDTO == null) {      //카카오 로그인이 처음인 경우

                                KakaoJoin kakaoJoin = new KakaoJoin(member_id, member_nickname, member_loginType, member_token);
                                try {
                                    state = kakaoJoin.execute().get().trim();
                                    Log.d(TAG, "onSessionOpened: " + result);
                                } catch (Exception e) {
                                    Log.d(TAG, "onPostExecute: " + e.getMessage());
                                }

                                if(state.equals("1")) {
                                    Log.d(TAG, "onSuccess1: 카카오 회원가입 됐습니다");
                                } else {
                                    Log.d(TAG, "onSuccess1: 회원가입 실패");
                                }

                                Intent intent = new Intent(context, SocialLocationActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("member_id", member_id);
                                intent.putExtra("member_loginType", member_loginType);
                                context.startActivity(intent);

                            } else {     //카카오 로그인이 되어있는 경우
                                if(kakaoLoginDTO.getMember_addr().equals("")) {    //주소 지정 x
                                    Intent intent = new Intent(context, SocialLocationActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("member_id", member_id);
                                    intent.putExtra("member_loginType", member_loginType);
                                    context.startActivity(intent);
                                } else {        //주소 지정 o
                                    Intent intent = new Intent(context, RealMainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                    Log.d(TAG, "onSuccess: " + kakaoLoginDTO.getMember_addr());
                                }

                            }

                        }

                    }
                });

    }
}
