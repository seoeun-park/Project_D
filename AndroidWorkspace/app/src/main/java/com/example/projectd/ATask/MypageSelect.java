package com.example.projectd.ATask;


import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.projectd.Dto.MemberDto;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.projectd.Common.CommonMethod.ipConfig;
import static com.example.projectd.LoginActivity.loginDTO;

public class MypageSelect extends AsyncTask<Void, Void, Void> {
    String member_nickname, member_addr;

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    public MypageSelect(String member_nickname, String member_addr) {
        this.member_nickname = member_nickname;
        this.member_addr = member_addr;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // MultipartEntityBuilder 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("member_nickname", member_nickname, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_addr", member_addr, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anLogin";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            loginDTO = readMessage(inputStream);

        } catch (Exception e) {
            Log.d("main:loginselect", e.getMessage());
            e.printStackTrace();
        } finally {
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;
            }
        }

        return null;
    } //doInBackground()

    private MemberDto readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        String member_id = "";
        String member_name = "";
        String member_nickname = "";
        String member_tel = "";
        String member_addr = "";
        String member_latitude = "";
        String member_longitude = "";
        String member_grade = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("member_id")) {
                member_id = reader.nextString();
            } else if (readStr.equals("member_nickname")) {
                member_nickname = reader.nextString();
            } else if (readStr.equals("member_name")) {
                member_name = reader.nextString();
            } else if (readStr.equals("member_tel")) {
                member_tel = reader.nextString();
            } else if(readStr.equals("member_addr")) {
                member_addr = reader.nextString();
            } else if(readStr.equals("member_latitude")) {
                member_latitude = reader.nextString();
            }  else if(readStr.equals("member_longitude")) {
                member_longitude = reader.nextString();
            }  else if(readStr.equals("member_grade")) {
                member_grade = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("main:loginselect : ", member_id + "," + member_name);
        return new MemberDto(member_id, member_nickname,
                             member_tel, member_addr, member_latitude,
                             member_longitude, member_grade, member_name);
    }//readMessage()
} //class
