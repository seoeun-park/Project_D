package com.example.projectd;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class QnAListActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    Button button;
    ImageButton back_btn;

    LinearLayout toolbar_context;   //툴바를 감싸는 레이아웃

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_qna_list);

            toolbar_context = findViewById(R.id.toolbar_context);
            back_btn = findViewById(R.id.btn_back);

            button = findViewById(R.id.button);
            LinearLayout layout01 = (LinearLayout) findViewById(R.id.layout01);

            recyclerview = findViewById(R.id.recyclerview);
            recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            List<QnAListAdapter.Item> data = new ArrayList<>();

            QnAListAdapter.Item places = new QnAListAdapter.Item(QnAListAdapter.HEADER, "상품 대여는 어떻게 하나요?");
            places.invisibleChildren = new ArrayList<>();
            places.invisibleChildren.add(new QnAListAdapter.Item(QnAListAdapter.CHILD, "[상품 판매 등록 방법]"));
            places.invisibleChildren.add(new QnAListAdapter.Item(QnAListAdapter.CHILD, "* 앱 > 하단의 '+' 버튼 선택 후 상품등록하기"));
            QnAListAdapter.Item places1 = new QnAListAdapter.Item(QnAListAdapter.HEADER, "거래를 하면 안되는 것들은 어떤 것이 있나요?");
            places1.invisibleChildren = new ArrayList<>();
            places1.invisibleChildren.add(new QnAListAdapter.Item(QnAListAdapter.CHILD, "몰라여"));
            QnAListAdapter.Item places2 = new QnAListAdapter.Item(QnAListAdapter.HEADER, "수제(음식,화장품)을 판매하고 싶은데 어떤 서류가 필요한가요?");
            places2.invisibleChildren = new ArrayList<>();
            places2.invisibleChildren.add(new QnAListAdapter.Item(QnAListAdapter.CHILD, "수제(음식,화장품)을 판매하실 통신판매 허기증, 사업자등록증, 신분증 필요"));
            places2.invisibleChildren.add(new QnAListAdapter.Item(QnAListAdapter.CHILD, "수제 음식 : 통신 판매 허가증/사업자 등록증/신분증"));
            places2.invisibleChildren.add(new QnAListAdapter.Item(QnAListAdapter.CHILD, "수제 화장품(비누) : 식약청 허가증/통신 판매 허가증/사업자 등록증/신분증"));

            QnAListAdapter.Item places3 = new QnAListAdapter.Item(QnAListAdapter.HEADER, "물품대여 후 교환이나 환불은 어떻게 하나요?");
            QnAListAdapter.Item places4 = new QnAListAdapter.Item(QnAListAdapter.HEADER, "14세 미만은 가입이 불가한가요?");
            QnAListAdapter.Item places5 = new QnAListAdapter.Item(QnAListAdapter.HEADER, "여러개의 계정을 만들 수 있나요?");
            QnAListAdapter.Item places6 = new QnAListAdapter.Item(QnAListAdapter.HEADER, "휴면께정 헤제는 어떻게 하나요?");
            QnAListAdapter.Item places7 = new QnAListAdapter.Item(QnAListAdapter.HEADER, "상품 수정 / 상품 삭제 방법법");

            data.add(places);
            data.add(places1);
            data.add(places2);
            data.add(places3);
            data.add(places4);
            data.add(places5);
            data.add(places6);
            data.add(places7);

        recyclerview.setAdapter(new QnAListAdapter(data));

        // 툴바 안의 뒤로가기 버튼 클릭할 때
        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}