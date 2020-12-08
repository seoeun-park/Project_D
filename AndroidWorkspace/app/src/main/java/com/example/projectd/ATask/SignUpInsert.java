package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

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
import java.nio.charset.Charset;

import static com.example.projectd.Common.CommonMethod.ipConfig;

public class SignUpInsert extends AsyncTask<Void, Void, String> {
    String member_id, member_pw, member_nickname, member_tel,
           member_addr, member_latitude, member_longitude,
           member_grade, member_name, member_token;

    public SignUpInsert(String member_id, String member_pw, String member_nickname,
                        String member_tel, String member_addr, String member_latitude,
                        String member_longitude, String member_name, String member_token) {
        this.member_id = member_id;
        this.member_pw = member_pw;
        this.member_nickname = member_nickname;
        this.member_tel = member_tel;
        this.member_addr = member_addr;
        this.member_latitude = member_latitude;
        this.member_longitude = member_longitude;
        this.member_name = member_name;
        this.member_token = member_token;
    }

    // 데이터베이스에 삽입결과 0보다크면 삽입성공, 같거나 작으면 실패
    String state = "";

    // 삽입할 때 사용할 기본 설정이므로 무조건 선언해야 한다.
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성 (초기화 단계, 무조건 작성해야 함)
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_pw", member_pw, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_nickname", member_nickname, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_tel", member_tel, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_addr", member_addr, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_latitude", member_latitude, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_longitude", member_longitude, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_name", member_name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_token", member_token, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anJoin";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 응답 (응답하고자 하는 대상의 타입이 String인 경우)
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
        } finally {         // 예외 처리와 상관없이 무조건 실행되는 코드
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
        }//try-catch-finally

        return state;
    } //doInBackground()

    //doInBackground() 메소드가 끝나고 실행되는 메소드
    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
    } //onPostExecute()
} //class
