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
import java.nio.charset.Charset;

import static com.example.projectd.Common.CommonMethod.ipConfig;


public class ProfileSubUpdate extends AsyncTask<Void, Void, Void> {

    String id, name, nickname, addr, tel, latitude, longitude;

    public ProfileSubUpdate(String id, String name,
                            String nickname, String tel, String addr, String latitude, String longitude){
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.tel = tel;
        this.addr = addr;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            String postURL = "";
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("name", name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("nickname", nickname, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("tel", tel, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("addr", addr, ContentType.create("Multipart/related", "UTF-8"));
            if (latitude == "") {
                builder.addTextBody("latitude", latitude, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("longitude", longitude, ContentType.create("Multipart/related", "UTF-8"));
            }
            Log.d("id", id);
            Log.d("name", name);
            Log.d("nickname", nickname);
            Log.d("tel", tel);
            Log.d("addr", addr);


            // 이미지를 새로 선택했으면 선택한 이미지와 기존에 이미지 경로를 같이 보낸다

            Log.d("Sub1Update:postURL", postURL);
            postURL = ipConfig + "/app/anSubUpdateMulti";
            // 전송
            InputStream inputStream = null;
            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 응답
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }
            inputStream.close();

            // 응답결과
                String result = stringBuilder.toString();
                Log.d("response", result);



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //dialog.dismiss();

    }


}
