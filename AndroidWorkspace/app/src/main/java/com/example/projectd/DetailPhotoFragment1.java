package com.example.projectd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.projectd.ATask.DetailPhotoSelect;
import com.example.projectd.Dto.MdDTO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DetailPhotoFragment1 extends Fragment {
    ImageView detail_photo1;
    Bitmap bitmap;

    MdDetailActivity activity;

    ArrayList<MdDTO> items;
    String md_photo_url;
    String member_id;
    String md_serial_number;
    MdDTO mdDTO;

    private int page;
    Bundle args = null;


    /*public static DetailPhotoFragment1 newInstance(int page, Bundle argsIn) {
        DetailPhotoFragment1 fragment = new DetailPhotoFragment1();
        args = argsIn;
        args.putInt("someInt", page);
       // args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        //title = getArguments().getString("someTitle");

    }//onCreate()*/

    public DetailPhotoFragment1(int page, Bundle args) {
        this.page = page;
        this.args = args;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_photo1, container, false);

        activity = (MdDetailActivity) getActivity();
        detail_photo1 = view.findViewById(R.id.detail_photo1);

        if(args != null) {
            md_serial_number = args.getString("md_serial_number");
        }

        DetailPhotoSelect detailPhotoSelect = new DetailPhotoSelect(md_serial_number);
        try {
            mdDTO = detailPhotoSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 사진url
        String imageUrl = mdDTO.getMd_photo_url();
        Glide.with(this).load(imageUrl)
                                .placeholder(R.drawable.spinner_icon)
                                .into(detail_photo1);

       /* Glide.with(this).load(imageUrl)
                    .placeholder(R.drawable.choonbae1)      //대신해서 들어갈 이미지
                    .error(R.drawable.heart)                //에러가 났을경우 들어갈 이미지
                    .into(detail_photo1);*/


        //사진url 띄워주기
//        Thread uThread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    //서버에 올려둔 이미지 URL
//                    URL url = new URL("https://search.pstatic.net/common/?src=http%3A%2F%2Fpost.phinf.naver.net%2F20160608_277%2F1465374681008D6Oxv_JPEG%2FIrgnqyVFZysV0FaNr626uqBJhN4M.jpg&type=sc960_832");
//                    //URL url = new URL(""+mdDTO.getMd_photo_url());
//
//                    //Web에서 이미지 가져온 후 이미지뷰에 지정할 비트맵 만들기
//
//                    //URLConnention 생성자가 protected로 선언되어 있으므로
//                    //개발자가 직접 HTTPURLConnention 객체 생성불가
//
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//                    //openConnection()메서드가 리턴하는 urlConnection 객체는
//                    //HttpURLConnection의 인스턴스가 될 수 있으므로 캐스팅해서 사용한다 *//*
//
//                    conn.setDoInput(true);  //Server 통신에서 입력 가능한 상태로 만듦
//                    conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)
//
//                    InputStream is = conn.getInputStream(); //inputStream 값 가져오기
//                    bitmap = BitmapFactory.decodeStream(is);    //Bitmap으로 반환
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        uThread.start();    //작업 스레드 실행
//
//        try {
//            //메인 Thread는 별도의 작업을 완료할 때까지 대기한다!
//            //join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다림
//            //join() 메서드는 InterruptedException을 발생시킨다.
//            uThread.join();
//
//            //작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
//            //UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지 지정
//            detail_photo1.setImageBitmap(bitmap);
//
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
        return view;
    }
}