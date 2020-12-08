package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.projectd.Dto.ReviewDto;
import com.example.projectd.ReviewAdapter;

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
import java.util.ArrayList;

import static com.example.projectd.Common.CommonMethod.ipConfig;

public class AnReviewSelect extends AsyncTask<Void, Void, Integer> {
    String md_serial_number;
    ArrayList<ReviewDto> reviews;
    ReviewAdapter adapter;

    public AnReviewSelect(ArrayList<ReviewDto> reviews, ReviewAdapter adapter, String md_serial_number) {
        this.reviews = reviews;
        this.adapter = adapter;
        this.md_serial_number = md_serial_number;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Integer doInBackground(Void... voids) {
        reviews.clear();
        String result = "";
        String postURL = ipConfig + "/app/anReviewSelect";

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("md_serial_number", md_serial_number, ContentType.create("Multipart/related", "UTF-8"));

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);


        } catch (Exception e) {
            Log.d("AnReviewSelect", e.getMessage());
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
        return reviews.size();
    }//doInBackground()


    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        Log.d("AnReviewSelect", "AnReviewSelect Complete!!!");

        adapter.notifyDataSetChanged();
    }


    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                reviews.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }//readJsonStream()


    public ReviewDto readMessage(JsonReader reader) throws IOException {
        String member_id="",  review_scope="",  review_content="",
                member_nickname="",  md_member_id="", md_serial_number="", member_profile="", review_num="";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();


            if (readStr.equals("member_id")) {
                member_id = reader.nextString();
            } else if (readStr.equals("review_scope")) {
                review_scope = reader.nextString();
            } else if (readStr.equals("review_content")) {
                review_content = reader.nextString();
            } else if (readStr.equals("member_nickname")) {
                member_nickname = reader.nextString();
            } else if (readStr.equals("md_member_id")) {
                md_member_id = reader.nextString();
            } else if (readStr.equals("md_serial_number")) {
                md_serial_number = reader.nextString();
            } else if (readStr.equals("member_profile")) {
                member_profile = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new ReviewDto(member_id,  review_scope,  review_content,
                member_nickname,  md_member_id, md_serial_number, member_profile, review_num);
    }


}//class
