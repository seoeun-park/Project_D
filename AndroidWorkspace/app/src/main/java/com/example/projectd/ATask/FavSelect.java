package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.projectd.Dto.FavDto;

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
import java.nio.charset.Charset;

import static com.example.projectd.Common.CommonMethod.ipConfig;

public class FavSelect extends AsyncTask<Void, Void, FavDto> {
    String member_id, md_serial_number;
    FavDto favDto;

    public FavSelect(String member_id, String md_serial_number) {
        this.member_id = member_id;
        this.md_serial_number = md_serial_number;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected FavDto doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_serial_number", md_serial_number, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anFavSelect";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            favDto = readMessage(inputStream);

        } catch (Exception e){
            Log.d("main:favSelect", e.getMessage());
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
        return favDto;
    }//doInBackground()

    @Override
    protected void onPostExecute(FavDto favDto) {
        super.onPostExecute(favDto);
    }

    private FavDto readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        String member_id = "";
        String md_serial_number = "";
        String md_fav_status = "";

        reader.beginObject();
        while (reader.hasNext()){
            String readStr = reader.nextName();
            if (readStr.equals("member_id")){
                member_id = reader.nextString();
            } else if (readStr.equals("md_serial_number")){
                md_serial_number = reader.nextString();
            } else if (reader.equals("md_fav_status")){
                md_fav_status = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        reader.close();
        Log.d("main:favSelect : ", member_id + "," + md_serial_number);

        return new FavDto(member_id, md_serial_number, md_fav_status);

    }//readMessage()



}//class
