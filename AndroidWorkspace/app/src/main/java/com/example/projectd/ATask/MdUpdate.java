package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.projectd.Dto.MdDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.projectd.Common.CommonMethod.ipConfig;

public class MdUpdate extends AsyncTask<Void, Void, Void> {


    String md_name,  md_category, md_rental_term, md_detail_content, md_price, md_deposit
            , member_id, md_serial_number;


    public MdUpdate (String md_name, String md_category
            ,String md_rental_term,String md_detail_content, String md_price, String md_deposit
            ,String member_id, String md_serial_number ){

        this.md_name = md_name;
        this.md_category = md_category;
        this.md_price = md_price;
        this.md_rental_term = md_rental_term;
        this.md_deposit = md_deposit;
        this.md_detail_content = md_detail_content;
        this.member_id = member_id;
        this.md_serial_number = md_serial_number;
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
            builder.addTextBody("md_name", md_name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_category", md_category, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_rental_term", md_rental_term, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_detail_content", md_detail_content, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_price", md_price, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_deposit", md_deposit, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_serial_number", md_serial_number, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anMdUpdate";
            // 전송

            InputStream inputStream = null;
            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();


        } catch (Exception e) {
            Log.d("main:mdupdate", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }//doInBackground()
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("Sub1Add:imageFilePath1", "추가성공");
    }
}
