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

import java.io.InputStream;
import java.nio.charset.Charset;

import static com.example.projectd.Common.CommonMethod.ipConfig;

public class FavInsert extends AsyncTask<Void, Void, Void> {
    String member_id, md_serial_number;

    public FavInsert(String member_id, String md_serial_number) {
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
             builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
             builder.addTextBody("md_serial_number", md_serial_number, ContentType.create("Multipart/related", "UTF-8"));

             String postURL = ipConfig + "/app/anFavInsert";

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
     }//doInBackground()

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("favInsert", "onPostExecute: 찜하기성공");
    }


}//class
