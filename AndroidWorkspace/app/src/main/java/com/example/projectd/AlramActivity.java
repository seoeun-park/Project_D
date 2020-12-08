package com.example.projectd;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AlramActivity extends AppCompatActivity {

    ListView listView;

    AlramSubActivity adapter;
    ArrayList<AlramSubDTO> dtos;

    LinearLayout toolbar_context;   //툴바를 감싸는 레이아웃

    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alram);

        Point size = getDiviceSize();

        dtos = new ArrayList<>();

        listView = findViewById(R.id.listView);
        toolbar_context = findViewById(R.id.toolbar_context);

        adapter = new AlramSubActivity(AlramActivity.this,dtos , size);
        adapter.addDto(new AlramSubDTO("내용","2020-8-18"));
        adapter.addDto(new AlramSubDTO("자전거 반납","2020-9-18"));

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlramSubDTO dto = (AlramSubDTO) adapter.getItem(position);
                Toast.makeText(AlramActivity.this, "선택"+position+"\n내용"+dto.getContent()+"\n날짜"+dto.getMd_rental_term()
                        , Toast.LENGTH_SHORT).show();


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

    private Point getDiviceSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(size);
        }
        int width = size.x;
        int height = size.y;

        return  size;
    }
}