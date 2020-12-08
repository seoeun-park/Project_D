package com.example.projectd.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import static com.example.projectd.Common.CommonMethod.ipConfig;

/**
 * Created by LG on 2017-02-10.
 */

public class ProfileDelete extends AsyncTask<Void, Void, Void> {

    //생성자
    String id;
    String image;

    public ProfileDelete(String id, String image ) {
        this.id = id;
        this.image = image;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("image", image, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anProfileDeleteMulti";
            // 전송
            //InputStream inputStream = null;
            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            //inputStream = httpEntity.getContent();

            // 응답
           /* BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }*/
            //inputStream.close();

            // 응답결과
            /*String result = stringBuilder.toString();
            Log.d("response", result);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
