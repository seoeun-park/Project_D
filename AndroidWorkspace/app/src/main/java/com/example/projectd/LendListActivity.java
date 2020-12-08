package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectd.ATask.MdInsert;
import com.example.projectd.ATask.MdRentStatusInsert;
import com.example.projectd.Common.CommonMethod;
import com.example.projectd.Dto.MdDTO;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LendListActivity extends AppCompatActivity {

    private static final String TAG = "LendListActivity";

    RecyclerView recyclerView;
    LendAdapter adapter;

    //List<MdDTO> mdDTOS;
    LinearLayout toolbar_context;   //툴바를 감싸는 레이아웃
    String category = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_list);

        recyclerView = findViewById(R.id.recyclerView);
        toolbar_context = findViewById(R.id.toolbar_context);

        //spinner선택 아이템 값을 가져오기
        final Spinner spinner = findViewById(R.id.sp_md_category2);
        category = spinner.getSelectedItem().toString();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                category = spinner.getSelectedItem().toString();
                MdPullRequest request = new MdPullRequest(LendListActivity.this, category, LoginActivity.loginDTO.getMember_id());
                request.execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });//spinner

        // 툴바 안의 뒤로가기 버튼 클릭할 때
        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }//oncreate

    private class MdPullRequest extends AsyncTask<Void, Void, List<MdDTO>> {

        Context context;
        String category;
        String member_id;

        public MdPullRequest(Context context, String category, String member_id) {
            this.context = context;
            this.category = category;
            this.member_id = member_id;
        }

        @Override
        protected List<MdDTO> doInBackground(Void... voids) {
            Log.e(TAG, "doInBackground: 호출됨");
            String param = "category=" + category + "&member_id=" + member_id;
            List<MdDTO> list = null;
            try {
                // 연결단계
                URL url = new URL(CommonMethod.ipConfig + "app/anMdpull");
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
                MdDTO[] dtos = gson.fromJson(json.trim(), MdDTO[].class);
                list = Arrays.asList(dtos);

                Log.e(TAG, "doInBackground: 4단계");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }

        //선택된 아이템 MD디테일화면으로 보내기
        @Override
        protected void onPostExecute(final List<MdDTO> mdDTOS) {
            super.onPostExecute(mdDTOS);
            final int Lend = 1001;

            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new LendAdapter(context, mdDTOS);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnLendItemCLickListener() {
                @Override
                public void onItemClick(LendAdapter.ViewHolder holder, View view, int position) {
                    MdDTO item = adapter.getItem(position);
                    Toast.makeText(getApplicationContext(), "아이템 선택됨" + item.getMd_name(),
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MdDetailActivity.class);
                    intent.putExtra("item", item );
                    startActivityForResult(intent, Lend );

                }
            });

        }
    }

}