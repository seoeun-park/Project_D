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

import java.io.File;
import java.nio.charset.Charset;

import static com.example.projectd.Common.CommonMethod.ipConfig;

public class MdInsert extends AsyncTask<Void, Void, Void> {
    String md_name, md_photo_url, md_photo_real_url, md_category, md_rental_term, md_detail_content, md_price, md_deposit
            , member_id, md_serial_number;


    public MdInsert ( String md_name, String md_photo_url, String md_photo_real_url, String md_category
            ,String md_rental_term,String md_detail_content, String md_price, String md_deposit
            ,String member_id, String md_serial_number ){
        this.md_name = md_name;
        this.md_photo_url = md_photo_url;
        this.md_photo_real_url = md_photo_real_url;
        this.md_category = md_category;
        this.md_price = md_price;
        this.md_rental_term = md_rental_term;
        this.md_deposit = md_deposit;
        this.md_detail_content = md_detail_content;
        this.member_id = member_id;
        this.md_serial_number = md_serial_number;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("main:mdinsert", "onPreExecute: " + md_photo_real_url);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("md_name", md_name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_photo_url", md_photo_url, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_category", md_category, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_rental_term", md_rental_term, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_detail_content", md_detail_content, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_price", md_price, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_deposit", md_deposit, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_serial_number", md_serial_number, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));

            builder.addPart("image", new FileBody(new File(md_photo_real_url)));

            String postURL = ipConfig + "app/anInsert";
            // 전송

            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            //inputStream = httpEntity.getContent();

            // 응답
            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            inputStream.close();*/

            // 응답결과
            /*String result = stringBuilder.toString();
            Log.d("response", result);*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("Sub1Add:imageFilePath1", "추가성공");

    }

}