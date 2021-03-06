package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.projectd.Dto.MdDTO;
import com.example.projectd.MainMdAdapter;

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

// doInBackground 파라미터 타입, onProgressUpdate파라미터 타입, onPostExecute 파라미터 타입 순서
// AsyncTask <Params, Progress, Result> 순서임
public class AnMainSelect extends AsyncTask<Void, Void, Void> {
    // 생성자
    ArrayList<MdDTO> items;
    MainMdAdapter adapter;
    String member_addr;

    public AnMainSelect(ArrayList<MdDTO> items, MainMdAdapter adapter) {
        this.items = items;
        this.adapter = adapter;
    }

    //메인페이지에서 회원과 같은동네의 상품만 보여주기위해
    public AnMainSelect(ArrayList<MdDTO> items, MainMdAdapter adapter, String member_addr) {
        this.items = items;
        this.adapter = adapter;
        this.member_addr = member_addr;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        items.clear();
        String result = "";
        String postURL = ipConfig + "/app/anMainSelect";

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("member_addr", member_addr, ContentType.create("Multipart/related", "UTF-8"));

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);

            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            String jsonStr = stringBuilder.toString();

            inputStream.close();*/

        } catch (Exception e) {
            Log.d("AnMainSelect", e.getMessage());
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
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Log.d("AnMainSelect", "AnMainSelect Complete!!!");

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
    }

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

    }

}
