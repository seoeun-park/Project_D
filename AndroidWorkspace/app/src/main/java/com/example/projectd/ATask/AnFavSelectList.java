package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import com.example.projectd.Dto.MdDTO;
import com.example.projectd.FavMdAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import static com.example.projectd.Common.CommonMethod.ipConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AnFavSelectList extends AsyncTask<Void, Void, Integer> {
    ArrayList<MdDTO> items;
    FavMdAdapter adapter;
    String member_id;


    public AnFavSelectList(ArrayList<MdDTO> items, FavMdAdapter adapter, String member_id) {
        this.items = items;
        this.adapter = adapter;
        this.member_id = member_id;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Integer doInBackground(Void... voids) {
        items.clear();
        String result = "";
        String postURL = ipConfig + "/app/anFavSelectList";

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));

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
            Log.d("AnFavSelectList", e.getMessage());
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
        return items.size();
    }//doInBackground()

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        Log.d("AnFavSelectList", "AnFavSelectList Complete!!!");

        adapter.notifyDataSetChanged();
    }

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
    }//readJsonStream()


    public MdDTO readMessage(JsonReader reader) throws IOException {
        String md_name = "", md_category = "", md_rental_term = "", md_detail_content = "",
                md_photo_url = "", member_id = "", md_registration_date = "",
                md_serial_number = "";
        String md_price = "", md_deposit = "", md_fav_count = "", md_rent_status = "", md_hits = "";

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
        //Log.d("listselect:myitem", id + "," + name + "," + hire_date + "," + image_path);

        return new MdDTO(md_name, md_category, md_price, md_rental_term,
                md_deposit, md_detail_content, md_photo_url, member_id,
                md_fav_count, md_registration_date,
                md_serial_number, md_rent_status, md_hits);

    }//readMessage()


}//class
