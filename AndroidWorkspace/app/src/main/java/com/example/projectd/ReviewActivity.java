package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectd.ATask.ReviewInsert;
import com.example.projectd.Dto.MdDTO;
import com.example.projectd.Dto.ReviewDto;

public class ReviewActivity extends AppCompatActivity {
    LinearLayout toolbar_context;   //툴바를 감싸고 있는 레이아웃
    TextView tv_review_member_id, tv_member_nick, tv_reviewnum;
    RatingBar rb_review_scope;
    EditText et_review_content;
    Button bt_review_send;

    String member_id="", review_scope="", review_content="", member_nickname="" , member_profile = "", review_num = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        // 툴바 뒤로가기 클릭시 Activity 화면 끝내기
        toolbar_context = findViewById(R.id.toolbar_context);
        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_member_nick = findViewById(R.id.tv_member_nick);
        tv_review_member_id = findViewById(R.id.tv_review_member_id);
        rb_review_scope = findViewById(R.id.rb_review_scope);
        et_review_content = findViewById(R.id.et_review_content);
        tv_reviewnum = findViewById(R.id.tv_review_num);
        bt_review_send = findViewById(R.id.bt_review_send);


        final TextView tv_ratingbar = (TextView) findViewById(R.id.tv_ratingbar);
        rb_review_scope.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tv_ratingbar.setText("" + rating);
            }
        });




        bt_review_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                member_id = LoginActivity.loginDTO.getMember_id();
                review_scope = tv_ratingbar.getText().toString();
                review_content = et_review_content.getText().toString();
                member_nickname = LoginActivity.loginDTO.getMember_nickname();
                Intent intent = getIntent();
                String md_member_id = intent.getExtras().getString("member_id");
                String md_serial_number = intent.getExtras().getString("md_serial_number");
                member_profile = LoginActivity.loginDTO.getMember_profile();

                /*Intent intent1 = getIntent();
                ReviewDto reviewDto = (ReviewDto) intent1.getSerializableExtra("item");
                review_num = reviewDto.getReview_num();*/

                ReviewInsert reviewInsert = new ReviewInsert(member_id, review_scope, review_content
                        , member_nickname, md_member_id, md_serial_number, member_profile, review_num);

                reviewInsert.execute();

                Intent showIntent = new Intent(getApplicationContext(), RentListActivity.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                        Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                        Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                startActivity(showIntent);

                Toast.makeText(ReviewActivity.this, "업로드 성공" , Toast.LENGTH_SHORT).show();

                finish();

            }
        });

    } //onCreate()


} //class