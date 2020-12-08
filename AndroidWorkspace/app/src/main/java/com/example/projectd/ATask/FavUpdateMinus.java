package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.example.projectd.Dto.MdDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.InputStream;

import static com.example.projectd.Common.CommonMethod.ipConfig;

public class FavUpdateMinus extends AsyncTask<Void, Void, MdDTO> {
    String md_serial_number;

    public FavUpdateMinus(String md_serial_number) {
        this.md_serial_number = md_serial_number;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected MdDTO doInBackground(Void... voids) {
        try {
            // MultipartEntityBuilder 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("md_serial_number", md_serial_number, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anFavUpdateMinus";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

        } catch (Exception e) {
            Log.d("main:favUpdateMinus", e.getMessage());
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
    }//doInBackground()

  /*  @Override
    protected void onPostExecute(MdDTO mdDTO) {
        super.onPostExecute(mdDTO);
    }
*/
}//class
