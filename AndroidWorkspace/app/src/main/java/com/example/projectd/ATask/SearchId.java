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

public class SearchId extends AsyncTask<Void, Void, String> {
    String member_tel;

    public SearchId(String member_tel) {
        this.member_tel = member_tel;
    }

    // DB에 조회할 때 나오는 회원 아이디
    String member_id = "";

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
            builder.addTextBody("member_tel", member_tel, ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + "/app/anSearchId";

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
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            member_id = stringBuilder.toString();

            inputStream.close();
        } catch (Exception e) {

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

        return member_id;
    } //doInBackground()

    //doInBackground() 메소드가 끝나고 실행되는 메소드
    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
    } //onPostExecute()
}
