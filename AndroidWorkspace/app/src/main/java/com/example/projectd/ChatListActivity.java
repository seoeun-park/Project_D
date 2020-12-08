package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projectd.Common.CommonMethod;
import com.example.projectd.Dto.MemberDto;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    private static final String TAG = "ChatListActivity";
    RecyclerView recyclerView;
    ChatListAdapter adapter;
    String member_id = "";

    LinearLayout toolbar_context;   //툴바를 감싸고 있는 레이아웃

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_list);

        recyclerView = findViewById(R.id.recyclerView);
        toolbar_context = findViewById(R.id.toolbar_context);

        ChatPullRequest request = new ChatPullRequest(LoginActivity.loginDTO.getMember_id());
        request.execute();

        //뒤로가기 버튼 클릭 시
        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private class ChatPullRequest extends AsyncTask<Void, Void, List<MemberDto>> {

        String member_id;

        public ChatPullRequest(String member_id) {
            this.member_id = member_id;
        }

        @Override
        protected List<MemberDto> doInBackground(Void... voids) {
            Log.e(TAG, "doInBackground: 호출됨");
            String param = "member_id=" + member_id;
            List<MemberDto> list = null;
            try {
                // 연결단계
                URL url = new URL(CommonMethod.ipConfig + "app/anChatpull");
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
                MemberDto[] dtos = gson.fromJson(json.trim(), MemberDto[].class);
                list = Arrays.asList(dtos);

                Log.e(TAG, "doInBackground: 4단계");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }

        //선택된 아이템 MD디테일화면으로 보내기
        @Override
        protected void onPostExecute(final List<MemberDto> memberDtos) {
            final int Lend = 1001;

            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ChatListAdapter(getApplicationContext(), memberDtos);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new OnChatItemClickListener() {
                final int chat = 1001;

                @Override
                public void onItemClick(ChatListAdapter.ViewHolder holder, View view, int position) {
                    MemberDto item = adapter.getItem(position);

                    Toast.makeText(getApplicationContext(), "아이템 선택됨" + item.getMember_nickname(),
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    startActivityForResult(intent, chat);

                }
            });

            super.onPostExecute(memberDtos);

        }
    }

}