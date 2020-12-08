package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.projectd.Common.CommonMethod.ipConfig;
import static com.example.projectd.LoginActivity.naverLoginDTO;

public class UpdateLocation extends AsyncTask<Void, Void, String> {
    String member_addr, member_latitude, member_longitude, member_id, member_loginType;

    public UpdateLocation(String member_addr, String member_latitude, String member_longitude,
                          String member_id, String member_loginType) {
        this.member_addr = member_addr;
        this.member_latitude = member_latitude;
        this.member_longitude = member_longitude;
        this.member_id = member_id;
        this.member_loginType = member_loginType;
    }

    // 데이터베이스에 삽입결과 0보다크면 업데이트 성공, 같거나 작으면 실패
    String state = "";

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            //문자열 및 데이터 추가
            builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_loginType", member_loginType, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_addr", member_addr, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_latitude", member_latitude, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_longitude", member_longitude, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anUpdateLocation";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 응답
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }

            state = stringBuilder.toString();

            inputStream.close();
        } catch (Exception e) {
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

        return state;
    } //doInBackground()
}
