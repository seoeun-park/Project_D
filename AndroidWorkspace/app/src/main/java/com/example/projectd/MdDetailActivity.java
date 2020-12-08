package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectd.ATask.DetailSelect;
import com.example.projectd.ATask.FavDelete;
import com.example.projectd.ATask.FavInsert;
import com.example.projectd.ATask.FavSelect;
import com.example.projectd.ATask.FavUpdate;
import com.example.projectd.ATask.FavUpdateMinus;
import com.example.projectd.Dto.FavDto;
import com.example.projectd.Dto.MdDTO;
import com.example.projectd.Dto.MemberDto;
import com.google.android.material.tabs.TabLayout;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.projectd.CategoryViewActivity.selItem;
import static com.example.projectd.LoginActivity.loginDTO;

public class MdDetailActivity extends AppCompatActivity {
    public MdDTO item = null;
    public MemberDto memberDto = null;
    public FavDto favDto = null;
    Bundle mBundle = null;
    TabLayout tabs;

    FragmentPagerAdapter adapterViewPager;

    CircleImageView profile_photo;
    TextView user_nickname, member_addr, user_grade, md_name, md_price, md_deposit, md_category,
            md_Registration_date, md_hits, md_fav_count, md_detail_content;
    Button btn_chat, btn_fav, btn_review;
    RatingBar ratingBar;

    TabFragment1 fragment1;
    TabFragment2 fragment2;

    LinearLayout toolbar_context;   //툴바를 감싸는 레이아웃

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_detail);

        Intent intent = getIntent();

        if (intent != null){
            item = (MdDTO) intent.getSerializableExtra("item");

            DetailSelect detailSelect = new DetailSelect(item.getMember_id());

            try {
                memberDto = detailSelect.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            selItem = null;
        }

        profile_photo = findViewById(R.id.profile_photo);
        user_nickname = findViewById(R.id.user_nickname);
        member_addr = findViewById(R.id.member_addr);
        user_grade = findViewById(R.id.user_grade);
        md_name = findViewById(R.id.md_name);
        md_price = findViewById(R.id.md_price);
        md_deposit = findViewById(R.id.md_dedosit);
        md_category = findViewById(R.id.md_category);
        md_Registration_date = findViewById(R.id.md_Registration_date);
        //md_hits = findViewById(R.id.md_hits);
        md_fav_count = findViewById(R.id.md_fav_count);
        md_detail_content = findViewById(R.id.md_detail_content);
        ratingBar = findViewById(R.id.ratingBar);

        btn_fav = findViewById(R.id.btn_fav);           //찜하기
        btn_chat = findViewById(R.id.btn_chat);         //리뷰쓰기
        btn_review = findViewById(R.id.btn_review);     //채팅하기


        // 데이터베이스에 찜이 안 되어 있는 경우 setTag를 0 ,되어 있는경우 1로 셋팅
        FavSelect favSelect = new FavSelect(loginDTO.getMember_id(), item.getMd_serial_number());
        try {
            favDto = favSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //btn_fav.setTag("0");

        if (favDto != null){
            btn_fav.setText("찜 취소");
            btn_fav.setBackgroundColor(Color.RED);
            btn_fav.setTag("1");
        }else {
            btn_fav.setText("찜하기");
            btn_fav.setBackgroundColor(Color.parseColor("#f5f51f"));
            btn_fav.setTag("0");
        }

        toolbar_context = findViewById(R.id.toolbar_context);

        setItem(item, memberDto);

        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());

        vpPager.setAdapter(adapterViewPager);

        // 전달할 데이터 Bundle
        mBundle = new Bundle();
        mBundle.putString("member_id", item.getMember_id());  // 키값, 데이터
        mBundle.putString("md_serial_number", item.getMd_serial_number());  // 키값, 데이터

        // 탭 프래그먼트
        fragment1 = new TabFragment1();
        fragment2 = new TabFragment2();

        //탭 프래그먼트중 기본으로 표시될 프래그먼트
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        //프로필사진 동글이
        /*CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);*/

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("리뷰"));
        tabs.addTab(tabs.newTab().setText( memberDto.getMember_nickname() + "님의 다른 상품"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MdDetailActivity", "선택된 탭: " + position);

                Fragment selected = null;
                if (position == 0){
                    selected = fragment1;
                }else if (position == 1){
                    selected = fragment2;
                }
                //fragment2.setArguments(args);

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        }); //tabs.addOnTabSelectedListener()

        //TabLayout.Tab tab = tabs.getTabAt(0);
        //tab.select();

        /*//본인이 등록한 상품에는 리뷰작성 버튼이 안뜨도록 layoutWeigth값을 변경해줌
        LinearLayout.LayoutParams favParams = (LinearLayout.LayoutParams) btn_fav.getLayoutParams();
        LinearLayout.LayoutParams reviewParams = (LinearLayout.LayoutParams) btn_review.getLayoutParams();
        LinearLayout.LayoutParams chatParams = (LinearLayout.LayoutParams) btn_chat.getLayoutParams();

        if (loginDTO.getMember_id().equals(item.getMember_id())){
            favParams.weight = 5;
            reviewParams.weight = 0;
            chatParams.weight = 5;
            btn_fav.setLayoutParams(favParams);
            btn_review.setLayoutParams(reviewParams);
            btn_chat.setLayoutParams(chatParams);
        }else{
            favParams.weight = 3f;
            reviewParams.weight = 3.5f;
            chatParams.weight = 3.5f;
            btn_fav.setLayoutParams(favParams);
            btn_review.setLayoutParams(reviewParams);
            btn_chat.setLayoutParams(chatParams);
        }*/

        //리뷰쓰기
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginDTO.getMember_id().equals(item.getMember_id())){
                    Toast.makeText(MdDetailActivity.this, "본인이 등록한 상품에는 리뷰를 작성할 수 없습니다!", Toast.LENGTH_SHORT).show();
                }else {
                Intent intent = new Intent(MdDetailActivity.this, ReviewActivity.class);
                intent.putExtra("member_id", item.getMember_id());
                intent.putExtra("md_serial_number", item.getMd_serial_number());
                startActivity(intent);
                }
            }
        });


        //채팅하기
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MdDetailActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });


        //찜하기
        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 전달할 데이터 Bundle
                //mBundle = new Bundle();
                //mBundle.putString("md_serial_number", item.getMd_serial_number());  // 키값, 데이터

                if (loginDTO.getMember_id().equals(item.getMember_id())){
                    Toast.makeText(MdDetailActivity.this, "본인이 등록한 상품은 찜 할 수 없습니다", Toast.LENGTH_SHORT).show();
                }else {
                    // 찜취소
                    if (btn_fav.getTag().toString().equals("1")){
                        Toast.makeText(MdDetailActivity.this, "찜 취소했습니다.", Toast.LENGTH_SHORT).show();
                        FavUpdateMinus favUpdateMinus = new FavUpdateMinus(item.getMd_serial_number());

                        try {
                            favUpdateMinus.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //찜취소했을때 화면의 찜개수표시 새로고침(1내려감)
                        md_fav_count.setText("찜:" + item.getMd_fav_count());


                        //'찜취소' 눌렀을때 찜테이블에 담겨있는 데이터 삭제
                        FavDelete favDelete = new FavDelete(loginDTO.getMember_id(), item.getMd_serial_number());
                        try {
                            favDelete.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        btn_fav.setText("찜하기");
                        btn_fav.setBackgroundColor(Color.parseColor("#f5f51f"));
                        btn_fav.setTag("0");

                        // 찜이 아닐때
                    }else if(btn_fav.getTag().toString().equals("0")){
                        Toast.makeText(MdDetailActivity.this, "찜 목록에 넣었습니다.", Toast.LENGTH_SHORT).show();
                        FavUpdate favUpdate = new FavUpdate(item.getMd_serial_number());

                        try {
                            favUpdate.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //찜하기 눌렀을때 화면에 찜갯수 1올라간거 표시되게끔 함(화면갱신아니고 그냥 눈속임)
                        md_fav_count.setText("찜:" + (Integer.parseInt(item.getMd_fav_count())+1));

                        btn_fav.setText("찜 취소");
                        btn_fav.setBackgroundColor(Color.RED);
                        btn_fav.setTag("1");

                        //'찜하기'눌렀을때 찜테이블에 로그인아이디 & 찜상품시리얼넘버 넣기
                        FavInsert favInsert = new FavInsert(loginDTO.getMember_id(), item.getMd_serial_number());
                        try {
                            favInsert.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Log.d("main:mdDetail", "onClick: 아무것도 안탐 ");
                    }
                }

            }//onclick()
        });//btn_fav.setOnClickListener()


        // 툴바 안의 뒤로가기 버튼 클릭할 때
        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }//onCreate()


    public void setItem(MdDTO item, MemberDto memberDto){
        String member_addr1 = memberDto.getMember_addr();

        // 주소에 상세 주소가 안나오도록 하게 함(시, 도, 군, 구, 동, 면만 나오게끔)
        String[] split1 = member_addr1.split(" ");
        String member_addr_re = "";

        for (int i = 0; i < split1.length; i++) {
            if(Pattern.matches("[가-힣]+(시|도|군|구|동|면)", split1[i])) {
                member_addr_re += split1[i] + " ";
            }
        }
        member_addr_re = member_addr_re.trim();
        // 마지막 공백 제거


        user_nickname.setText(memberDto.getMember_nickname());
        member_addr.setText(member_addr_re);
        user_grade.setText(memberDto.getMember_grade() + "점");
        Glide.with(this).load(memberDto.getMember_profile()).into(profile_photo);

        //회원등급 RatingBar로 표시
        final String grade = memberDto.getMember_grade();
        ratingBar.setRating(Float.parseFloat(grade));

        // 사진url 가져오는 대표적인 방법: 글라이드 (쓰려면 그래들 추가해야됨)
       /* String imageUrl = mdDTO.getMd_photo_url();
        Glide.with(this).load(imageUrl)
                .placeholder(R.drawable.choonbae1)
                .error(R.drawable.heart)
                .into(detail_photo1);*/

        md_name.setText(item.getMd_name());
        md_price.setText(item.getMd_price() + "원");  //int형 가져올땐 ""를 추가해야됨
        md_deposit.setText(item.getMd_deposit() + "원");
        md_category.setText(item.getMd_category());
        md_Registration_date.setText(item.getMd_registration_date());   //등록일자
        //md_hits.setText("조회수:" + item.getMd_hits());
        md_fav_count.setText("찜:" + item.getMd_fav_count());
        md_detail_content.setText(item.getMd_detail_content());
    }

    //상품 상세사진 슬라이드 넘기기
    public class MyPagerAdapter extends FragmentPagerAdapter {
        //private int NUM_ITEMS = 3;
        private int NUM_ITEMS = 1;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {

            // 전달할 데이터 Bundle
            Bundle bundle = new Bundle();
            bundle.putString("md_serial_number", item.getMd_serial_number());  // 키값, 데이터
            return new DetailPhotoFragment1(0, bundle);

            /*switch (position) {
                case 0:
                    // 전달할 데이터 Bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("md_serial_number", item.getMd_serial_number());  // 키값, 데이터
                    return new DetailPhotoFragment1(0, bundle);
                case 1:
                    return DetailPhotoFragment2.newInstance(1);
                case 2:
                    return DetailPhotoFragment3.newInstance(2);
                default:
                    return null;
            }*/
        }//getItem()

    }//MyPagerAdapter()

    public void fragChanged(Bundle bundle){

    }

}//class