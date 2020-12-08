package com.example.projectd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectd.Common.CommonMethod;
import com.example.projectd.Dto.ReviewDto;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class RentListActivity extends AppCompatActivity {
    private static final String TAG = "RentListActivity";

    RecyclerView recyclerView;
    RentAdapter adapter;
    LinearLayout toolbar_context;   //툴바를 감싸는 레이아웃


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_list);

        recyclerView = findViewById(R.id.recyclerView);
        toolbar_context = findViewById(R.id.toolbar_context);

        // 툴바 안의 뒤로가기 버튼 클릭할 때
        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ReviewRequest request = new ReviewRequest(LoginActivity.loginDTO.getMember_id());
        request.execute();
    }//oncreate

    private class ReviewRequest extends AsyncTask<Void, Void, List<ReviewDto>> {
        String member_id;


        public ReviewRequest(String member_id) {
            this.member_id = member_id;

        }

        @Override
        protected List<ReviewDto> doInBackground(Void... voids) {
            Log.e(TAG, "doInBackground: 호출됨");
            String param = "member_id=" + member_id;
            List<ReviewDto> list = null;
            try {
                // 연결단계
                //URL url = new URL(CommonMethod.ipConfig + "app/anReviewPull");
                URL url = new URL(CommonMethod.ipConfig + "app/anReviewPull");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);

                Log.e(TAG, "doInBackground: 1단계");

                // 파라미터를 보내는 단계
                OutputStream os = conn.getOutputStream();
                os.write(param.getBytes("utf-8"));
                os.flush();
                os.close();

                Log.e(TAG, "doInBackground: 2단계");

                // 데이터를 가져오는 단계
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                StringBuffer sb = new StringBuffer();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String json = sb.toString();

                Log.e(TAG, "doInBackground: 3단계");
                Log.e(TAG, "json: " + json);

                // Json 데이터를 객체로 반환
                Gson gson = new Gson();
                ReviewDto[] dtos = gson.fromJson(json.trim(), ReviewDto[].class);
                list = Arrays.asList(dtos);

                Log.e(TAG, "doInBackground: 4단계");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }

        //선택된 아이템 MD디테일화면으로 보내기
        @Override
        protected void onPostExecute(final List<ReviewDto> reviewDtos) {
            final int Rent = 1001;

            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RentAdapter(getApplicationContext(), reviewDtos);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnRentItemCLickListener() {
                @Override
                public void onItemClick(RentAdapter.ViewHolder holder, View view, int position) {
                    ReviewDto item = adapter.getItem(position);
                    Toast.makeText(getApplicationContext(), "아이템 선택됨" + item.getMember_id(),
                            Toast.LENGTH_LONG).show();


                }
            });

            super.onPostExecute(reviewDtos);

        }
    }
}