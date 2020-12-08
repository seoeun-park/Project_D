package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.projectd.Dto.MemberDto;

import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.projectd.Common.CommonMethod.ipConfig;
import static com.example.projectd.LoginActivity.naverLoginDTO;

public class NaverLogin extends AsyncTask<Void, Void, Void> {
    String member_id, member_loginType;

    public NaverLogin(String member_id, String member_loginType) {
        this.member_id = member_id;
        this.member_loginType = member_loginType;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            //문자열 및 데이터 추가
            builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_loginType", member_loginType, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anNaverLogin";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            naverLoginDTO = readMessage(inputStream);

            inputStream.close();
        } catch (Exception e) {
            Log.d("main:naverlogin", e.getMessage());
            e.printStackTrace();
        }finally {
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

    public MemberDto readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        String member_id = "", member_nickname = "", member_tel = "", member_addr = "", member_latitude = "",
                member_longitude = "", member_grade = "", member_name = "", member_profile = "",
                member_loginType = "", member_token = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("member_id")) {
                member_id = reader.nextString();
            } else if (readStr.equals("member_nickname")) {
                member_nickname = reader.nextString();
            } else if (readStr.equals("member_tel")) {
                member_tel = reader.nextString();
            } else if (readStr.equals("member_addr")) {
                member_addr = reader.nextString();
            } else if (readStr.equals("member_latitude")) {
                member_latitude = reader.nextString();
            } else if (readStr.equals("member_longitude")) {
                member_longitude = reader.nextString();
            } else if (readStr.equals("member_grade")) {
                member_grade = reader.nextString();
            } else if (readStr.equals("member_name")) {
                member_name = reader.nextString();
            } else if (readStr.equals("member_profile")) {
                member_profile = reader.nextString();
            } else if (readStr.equals("member_logintype")) {
                member_loginType = reader.nextString();
            } else if (readStr.equals("member_token")) {
                member_token = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("main:naver login : ", member_id + "," + member_loginType);
        return new MemberDto(member_id, member_nickname, member_tel, member_addr, member_latitude,
                             member_longitude, member_grade, member_name, member_profile,
                             member_loginType, member_token);
    }
}

