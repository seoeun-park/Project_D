
package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.projectd.Dto.MdDTO;
import com.example.projectd.Dto.MemberDto;
import com.example.projectd.MdDetailActivity;

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

import static com.example.projectd.Common.CommonMethod.ipConfig;


public class DetailSelect extends AsyncTask<Void, Void, MemberDto> {

    String member_id;
    MemberDto memberDto;

    public DetailSelect(String member_id) {
        this.member_id = member_id;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected MemberDto doInBackground(Void... voids) {
          try {
            // MultipartEntityBuilder 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anDetail";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            memberDto = readMessage(inputStream);


        } catch (Exception e) {
            Log.d("main:detailselect", e.getMessage());
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
        return memberDto;
    }

    @Override
    protected void onPostExecute(MemberDto memberDto) {
        super.onPostExecute(memberDto);

    }

    private MemberDto readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        String member_id = "";
        String member_pw = "";
        String member_name = "";
        String member_nickname = "";
        String member_tel = "";
        String member_addr = "";
        String member_latitude = "";
        String member_longitude = "";
        String member_grade = "";
        String member_profile = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("member_id")) {
                member_id = reader.nextString();
            } else if (readStr.equals("member_pw")) {
                member_pw = reader.nextString();
            } else if (readStr.equals("member_nickname")) {
                member_nickname = reader.nextString();
            } else if (readStr.equals("member_name")) {
                member_name = reader.nextString();
            } else if (readStr.equals("member_tel")) {
                member_tel = reader.nextString();
            } else if(readStr.equals("member_addr")) {
                member_addr = reader.nextString();
            } else if(readStr.equals("member_latitude")) {
                member_latitude = reader.nextString();
            } else if(readStr.equals("member_longitude")) {
                member_longitude = reader.nextString();
            } else if(readStr.equals("member_grade")) {
                member_grade = reader.nextString();
            } else if(readStr.equals("member_profile")) {
                member_profile = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        reader.close();
        Log.d("main:detailselect : ", member_id + "," + member_name);
        return new MemberDto(member_id, member_pw, member_nickname, member_tel,
                member_addr, member_latitude, member_longitude,
                member_grade, member_name, member_profile);
    }//readMessage()

}
