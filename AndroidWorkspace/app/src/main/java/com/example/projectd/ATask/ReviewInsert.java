package com.example.projectd.ATask;

import android.content.Intent;
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

import java.io.InputStream;
import java.nio.charset.Charset;

import static com.example.projectd.Common.CommonMethod.ipConfig;

public class ReviewInsert extends AsyncTask<Void, Void, Void> {

    String member_id,review_scope, review_content , member_nickname, md_member_id , md_serial_number , member_profile, review_num;

    public ReviewInsert(String member_id, String review_scope, String review_content
            , String member_nickname, String md_member_id, String md_serial_number, String member_profile, String review_num){
        this.member_id = member_id;
        this.review_scope = review_scope;
        this.review_content = review_content;
        this.member_nickname = member_nickname;
        this.md_member_id = md_member_id;
        this.md_serial_number = md_serial_number;
        this.member_profile = member_profile;
        this.review_num = review_num;
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
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("review_scope", review_scope, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("review_content", review_content, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_nickname", member_nickname, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_member_id", md_member_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_serial_number", md_serial_number, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_profile", member_profile, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("review_num", review_num, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anReviewInsert";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

        } catch (Exception e){
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

    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("favInsert", "onPostExecute: 성공");
    }

}
