package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import com.example.projectd.Dto.MdDTO;
import com.example.projectd.SearchAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.projectd.Common.CommonMethod.ipConfig;

// doInBackground 파라미터 타입, onProgressUpdate파라미터 타입, onPostExecute 파라미터 타입 순서
// AsyncTask <Params, Progress, Result> 순서임
public class SearchSelect extends AsyncTask<Void, Void, Integer> {
    // 생성자
    ArrayList<MdDTO> items;
    SearchAdapter adapter;
    Map<String,Object> params;

    public SearchSelect(ArrayList<MdDTO> items, SearchAdapter adapter, Map<String,Object> params) {
        this.items = items;
        this.adapter = adapter;
        this.params = params;
    }

    public SearchSelect(ArrayList<MdDTO> items, SearchAdapter adapter) {
        this.items = items;
        this.adapter = adapter;
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
    protected Integer doInBackground(Void... voids) {
        items.clear();
        String result = "";
        String postURL = ipConfig + "/app/SearchSelect";


        try {
            // MultipartEntityBuild 생성
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE).;


            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            List<NameValuePair> nameValuePairs;
            if(params != null) {
                Log.d("===> ","notNull");
                Log.d("===> ",params.get("searchKeyword").toString());
                nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("searchKeyword", params.get("searchKeyword").toString()));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
            }
//            httpPost.setEntity(builder.build());
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
            Log.d("Sub1", e.getMessage());
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
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        Log.d("Sub1", "SearchSelect Complete!!!");

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
