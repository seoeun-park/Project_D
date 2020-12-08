package com.example.projectd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;
import static com.example.projectd.LoginActivity.mContext;
import static com.example.projectd.LoginActivity.mOAuthLoginInstance;
import static com.example.projectd.LoginActivity.naverLoginDTO;
import static com.example.projectd.LoginActivity.loginDTO;
import static com.example.projectd.SessionCallback.kakaoLoginDTO;
import de.hdodenhof.circleimageview.CircleImageView;

public class MypageActivity extends Fragment {
    private static final String TAG = "main:MypageActivity";
    TextView btn_profile_update, mypage_notice, mypage_qna, mypage_logout, ratingSum, btn1;
    ImageView my_goods, my_rentlist, my_fav;
    ViewGroup viewGroup;
    ActionBar abar;
    LinearLayout Lin1, Lin2, Lin3;
    TextView user_nickname, member_addr;
    RatingBar ratingBar2;
    CircleImageView profile_photo;

    MainActivity mainActivity;
    LinearLayout toolbar_context;   //툴바를 감싸고 있는 레이아웃

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_mypage, null);

        setHasOptionsMenu(true);

        mainActivity = new MainActivity();

        btn_profile_update = viewGroup.findViewById(R.id.btn_profile_update);
        mypage_notice = viewGroup.findViewById(R.id.mypage_notice);
        mypage_qna = viewGroup.findViewById(R.id.mypage_qna);
        mypage_logout = viewGroup.findViewById(R.id.mypage_logout);
        Lin1 = viewGroup.findViewById(R.id.Lin1);
        Lin2 = viewGroup.findViewById(R.id.Lin2);
        Lin3 = viewGroup.findViewById(R.id.Lin3);
        user_nickname = viewGroup.findViewById(R.id.user_nickname);
        member_addr = viewGroup.findViewById(R.id.member_addr);
        ratingBar2 = viewGroup.findViewById(R.id.ratingBar2);
        profile_photo = viewGroup.findViewById(R.id.profile_photo);
        ratingSum = viewGroup.findViewById(R.id.ratingSum);

        toolbar_context = viewGroup.findViewById(R.id.toolbar_context);

        if( LoginActivity.loginDTO != null) {

            final String grade = LoginActivity.loginDTO.getMember_grade();
            String user = LoginActivity.loginDTO.getMember_nickname();
            user_nickname.setText(user);
            String addr = LoginActivity.loginDTO.getMember_addr();
            member_addr.setText(addr);
            ratingBar2.setRating(Float.parseFloat(grade));
            ratingSum.setText(grade);

            //프로필 사진
            Glide.with(this).load(LoginActivity.loginDTO.getMember_profile())
                    .placeholder(R.color.cardview_dark_background)
                    .into(profile_photo);

        }else if( LoginActivity.naverLoginDTO != null) {

            final String grade = LoginActivity.naverLoginDTO.getMember_grade();
            String user = LoginActivity.naverLoginDTO.getMember_nickname();
            user_nickname.setText(user);
            String addr = LoginActivity.naverLoginDTO.getMember_addr();
            member_addr.setText(addr);
            ratingBar2.setRating(Float.parseFloat(grade));
            ratingSum.setText(grade);

            //프로필 사진
            Glide.with(this).load(LoginActivity.naverLoginDTO.getMember_profile())
                    .placeholder(R.color.cardview_dark_background)
                    .into(profile_photo);

        }else if(SessionCallback.kakaoLoginDTO != null){

            final String grade = SessionCallback.kakaoLoginDTO.getMember_grade();
            String user = SessionCallback.kakaoLoginDTO.getMember_nickname();
            user_nickname.setText(user);
            String addr = SessionCallback.kakaoLoginDTO.getMember_addr();
            member_addr.setText(addr);
            ratingBar2.setRating(Float.parseFloat(grade));
            ratingSum.setText(grade);

            //프로필 사진
            Glide.with(this).load(SessionCallback.kakaoLoginDTO.getMember_profile())
                    .placeholder(R.color.cardview_dark_background)
                    .into(profile_photo);
        }

        //별점
        ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });

        //빌린 목록
        Lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LendListActivity.class);
                startActivity(intent);
            }
        });

        //빌려준 목록
        Lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RentListActivity.class);
                startActivity(intent);
            }
        });

        //찜목록
        Lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FavListActivity.class);
                startActivity(intent);
            }
        });


        //프로필 수정
        btn_profile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfilActivity.class);
                startActivity(intent);
            }
        });

        //공지사항
        mypage_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        //자주묻는질문
        mypage_qna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QnAListActivity.class);
                startActivity(intent);
            }
        });

        //로그아웃
        mypage_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(naverLoginDTO != null) {    //네이버 로그아웃
                    OAuthLogin.getInstance().logout(mContext);
                    String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                    String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                    Log.d(TAG, "onClick: " + accessToken + ", " + refreshToken);
                    naverLoginDTO = null;

                } else if(kakaoLoginDTO != null) {  //카카오 로그아웃
                    UserManagement.getInstance()
                            .requestLogout(new LogoutResponseCallback() {
                                @Override
                                public void onCompleteLogout() {
                                    Log.d(TAG, "onCompleteLogout: 카카오 로그아웃 완료");
                                    kakaoLoginDTO = null;
                                }
                            });
                    
                    //카카오 회원 탈퇴
                    UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            //회원탈퇴 실패 시 동작
                            Log.d(TAG, "onFailure: 회원탈퇴 실패");
                        }

                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            //세션이 닫혔을 시 동작.
                            Log.d(TAG, "onSessionClosed: 세션 닫힘");
                        }

                        @Override
                        public void onNotSignedUp() {
                            //가입되지 않은 계정이 회원탈퇴를 요구할 경우 동작.
                            Log.d(TAG, "onNotSignedUp: 가입된 계정이 아닙니다");
                        }

                        @Override
                        public void onSuccess(Long result) {
                            //회원탈퇴 성공 시 동작.
                            Log.d(TAG, "onSuccess: 회원탈퇴 성공!");
                        }
                    });

                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                loginDTO = null;
                getActivity().finish();
            }
        });

        // 툴바안의 뒤로가기 버튼을 클릭했을 때
        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_layout, mainActivity).commitAllowingStateLoss();
            }
        });

        return viewGroup;

    }//onCreateView()



} //class