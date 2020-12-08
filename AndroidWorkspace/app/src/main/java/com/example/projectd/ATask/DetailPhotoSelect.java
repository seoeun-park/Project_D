package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.projectd.Dto.MdDTO;
import com.example.projectd.Dto.MemberDto;

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

public class DetailPhotoSelect extends AsyncTask<Void, Void, MdDTO> {

    String member_id;
    String md_photo_url;
    MdDTO mdDTO;
    ArrayList<MdDTO> items;
    String md_serial_number;

    public DetailPhotoSelect(String md_serial_number){
        this.md_serial_number = md_serial_number;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected MdDTO doInBackground(Void... voids) {
        //items.clear();


        try {
            // MultipartEntityBuilder 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            //builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
            //builder.addTextBody("md_photo_url", md_photo_url, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("md_serial_number", md_serial_number, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anDetailPhoto";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            mdDTO = readMessage(inputStream);
            //readJsonStream(inputStream);

        } catch (Exception e) {
            Log.d("anDetailPhoto", e.getMessage());
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
        return mdDTO;
    }//doInBackground()

    @Override
    protected void onPostExecute(MdDTO mdDTO) {
        super.onPostExecute(mdDTO);

        //adapter.notifyDataSetChanged();
    }

/*
    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                items.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }
*/


    public MdDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        String md_name = "", md_category = "", md_rental_term = "", md_detail_content = "",
                md_photo_url = "", member_id = "", md_registration_date = "", md_serial_number = "",
                md_price = "", md_deposit = "", md_fav_count = "", md_rent_status = "", md_hits = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();

            if (readStr.equals("md_name")) {
                md_name = reader.nextString();
            } else if (readStr.equals("md_category")) {
                md_category = reader.nextString();
            } else if (readStr.equals("md_rental_term")) {
                md_rental_term = reader.nextString();
            } else if (readStr.equals("md_detail_content")) {
                md_detail_content = reader.nextString();
            } else if (readStr.equals("md_photo_url")) {
                md_photo_url = reader.nextString();
            } else if (readStr.equals("member_id")) {
                member_id = reader.nextString();
            } else if (readStr.equals("md_registration_date")) {
                md_registration_date = reader.nextString();
            } else if (readStr.equals("md_serial_number")) {
                md_serial_number = reader.nextString();
            } else if (readStr.equals("md_price")) {
                md_price = reader.nextString();
            } else if (readStr.equals("md_deposit")) {
                md_deposit = reader.nextString();
            } else if (readStr.equals("md_fav_count")) {
                md_fav_count = reader.nextString();
            } else if (readStr.equals("md_rent_status")) {
                md_rent_status = reader.nextString();
            } else if (readStr.equals("md_hits")) {
                md_hits = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        reader.close();
        Log.d("main:detailPhoto : ", member_id + "," + md_photo_url);

        return new MdDTO(md_name, md_category, md_price, md_rental_term,
                md_deposit, md_detail_content, md_photo_url, member_id,
                md_fav_count, md_registration_date,
                md_serial_number, md_rent_status, md_hits);
    }


}//class
