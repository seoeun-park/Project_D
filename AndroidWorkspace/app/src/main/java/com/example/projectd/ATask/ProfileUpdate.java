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
import org.apache.http.entity.mime.content.FileBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.example.projectd.Common.CommonMethod.ipConfig;


public class ProfileUpdate extends AsyncTask<Void, Void, Void> {

    String id, pImgDbPathU, imageDbPathU, imageRealPathU;

    public ProfileUpdate(String id, String pImgDbPathU,
                         String imageDbPathU, String imageRealPathU){
        this.id = id;
        this.pImgDbPathU = pImgDbPathU;
        this.imageDbPathU = imageDbPathU;
        this.imageRealPathU = imageRealPathU;
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

            Log.d("id", id);
            Log.d("Sub1Update16", pImgDbPathU);
            Log.d("Sub1Update17", imageDbPathU);

            // 이미지를 새로 선택했으면 선택한 이미지와 기존에 이미지 경로를 같이 보낸다
            if(!imageRealPathU.equals("")){
                Log.d("Sub1Update:postURL", "1");
                // 기존에 있던 DB 경로
                builder.addTextBody("pDbImgPath", pImgDbPathU, ContentType.create("Multipart/related", "UTF-8"));
                // DB에 저장할 경로
                builder.addTextBody("dbImgPath", imageDbPathU, ContentType.create("Multipart/related", "UTF-8"));
                // 실제 이미지 파일
                builder.addPart("image", new FileBody(new File(imageRealPathU)));

                postURL = ipConfig + "/app/anUpdateMulti";

            }else if(imageRealPathU.equals("")){  // 이미지를 바꾸지 않았다면
                Log.d("Sub1Update:postURL", "3");
                postURL = ipConfig + "/app/anUpdateMultiNo";
            }else{
                Log.d("Sub1Update:postURL", "5 : error");
            }
            Log.d("Sub1Update:postURL", postURL);
            postURL = ipConfig + "/app/anUpdateMulti";
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
