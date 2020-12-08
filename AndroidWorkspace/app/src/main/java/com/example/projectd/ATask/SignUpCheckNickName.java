package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

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

import static com.example.projectd.Common.CommonMethod.ipConfig;
import static com.example.projectd.SignUpFormActivity.nicknamecheckDTO;

public class SignUpCheckNickName extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "main:SignUpCheckNickName";
    String member_nickname;

    //생성자 만들기
    public SignUpCheckNickName(String member_nickname) {
        this.member_nickname = member_nickname;
        //Log.d(TAG, "SignUpCheckNickName: "+ this.member_nickname);
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("member_nickname", member_nickname, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anNickNameCheck";
            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            nicknamecheckDTO = readMessage(inputStream);

            inputStream.close();

        } catch (Exception e) {
            Log.d("main:joincheknickname", e.getMessage());
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

    }

    public MemberDto readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        reader.setLenient(true);

        String member_nickname = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("member_nickname")) {
                member_nickname = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        reader.close();
        Log.d("main:joinchknickname : ", member_nickname );
        return new MemberDto(member_nickname);
    }
}