package com.example.projectd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoticeAdapter adapter;
    NoticeFindActivity noticeFindActivity;

    LinearLayout toolbar_context;   //툴바를 감싸는 레이아웃

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        noticeFindActivity = new NoticeFindActivity();
        recyclerView = findViewById(R.id.recyclerView);
        toolbar_context = findViewById(R.id.toolbar_context);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoticeAdapter();

        adapter.addItem(new Notice("2020/09/07", "[공지] 추석 연휴 사기 거래 주의! 꼭 읽어주세요!"));
        adapter.addItem(new Notice("2020/09/07", "대여안대여 개인정보처리방침 개정 안내"));
        adapter.addItem(new Notice("2020/09/07", "카카로톡 아이디 포함 시 상품 삭제 정책 안내"));
        adapter.addItem(new Notice("2020/09/07", "이용약관 및 개인정보처리방침 변경 공지"));
        adapter.addItem(new Notice("2020/09/07", "대여안대여 서비스 점검 안내"));
        adapter.addItem(new Notice("2020/09/07", "상품권 및 기프트카드 거래 금지 안내"));
        adapter.addItem(new Notice("2020/09/07", "대여안대여 1주년 이벤트 당첨자 발표"));
        adapter.addItem(new Notice("2020/09/07", "대여안대여 서비스 점검 안내"));


        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnNoticeItemClickListener() {
            public static final int Notice = 1001;

            @Override
            public void onItemClick(NoticeAdapter.ViewHolder holder, View view, int position) {
                Notice item = adapter.getItem(position);
                switch (position){
                    case 0:
                        Toast.makeText(getApplicationContext(), "아이템 선택됨" + item.getSub(),
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), NoticeFindActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "아이템 선택됨" + item.getSub(),
                                Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(getApplicationContext(), NoticeFindActivity2.class);
                        startActivity(intent2);
                        break;
                }

            }
        });

        // 툴바 안의 뒤로가기 버튼 클릭할 때
        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}