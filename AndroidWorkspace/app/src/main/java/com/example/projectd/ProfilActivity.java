package com.example.projectd;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.projectd.ATask.LoginSelect;
import com.example.projectd.ATask.ProfilIensert;
import com.example.projectd.ATask.ProfileDelete;
import com.example.projectd.ATask.ProfileUpdate;
import com.example.projectd.Common.CommonMethod;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.projectd.Common.CommonMethod.ipConfig;
import static com.example.projectd.Common.CommonMethod.isNetworkConnected;

public class ProfilActivity extends AppCompatActivity {
    private static final String TAG = "ProfilActivity";

    LinearLayout toolbar_context, Modified;   //툴바를 감싸는 레이아웃
    TextView profile_name, profile_nickname, profile_location, profile_phone, profile_email;
    CircleImageView profile_photo;
    Button profile_set;
    String image = "https://d1u5g7tm7q0gio.cloudfront.net/images/avatars/defaults/default.jpg";

    //String id = LoginActivity.loginDTO.getMember_id();

    public String imagePath;
    public String pImgDbPathU;
    public String imageRealPathU = "", imageDbPathU = "";
    public String imageRealPathA, imageDbPathA;

    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1011;

    File file = null;
    long fileSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        toolbar_context = findViewById(R.id.toolbar_context);
        Modified = findViewById(R.id.Modified);
        profile_name = findViewById(R.id.profile_name);
        profile_nickname = findViewById(R.id.profile_nickname);
        profile_phone = findViewById(R.id.profile_phone);
        profile_location = findViewById(R.id.profile_location);
        profile_email = findViewById(R.id.profile_email);
        profile_photo = findViewById(R.id.profile_photo);
        profile_set = findViewById(R.id.profile_set);

        if( LoginActivity.loginDTO != null){
            String member_name = LoginActivity.loginDTO.getMember_name();
            String member_nickname = LoginActivity.loginDTO.getMember_nickname();
            String member_addr = LoginActivity.loginDTO.getMember_addr();
            String member_id = LoginActivity.loginDTO.getMember_id();
            String member_profile = LoginActivity.loginDTO.getMember_profile();
            String member_tel = LoginActivity.loginDTO.getMember_tel();

            profile_name.setText(member_name);
            profile_nickname.setText(member_nickname);
            profile_phone.setText(member_tel);
            profile_location.setText(member_addr);
            profile_email.setText(member_id);

            //마이페이지에서 프로필 사진 가져옴
            imagePath = LoginActivity.loginDTO.getMember_profile();
            pImgDbPathU = imagePath;
            imageDbPathU = imagePath;
            profile_photo.setVisibility(View.VISIBLE);

            // 선택된 이미지 보여주기
            Glide.with(this).load(LoginActivity.loginDTO.getMember_profile())
                    .placeholder(R.color.cardview_dark_background)
                    .into(profile_photo);

        }else if( LoginActivity.naverLoginDTO != null ){

            String member_name = LoginActivity.naverLoginDTO.getMember_name();
            String member_nickname = LoginActivity.naverLoginDTO.getMember_nickname();
            String member_addr = LoginActivity.naverLoginDTO.getMember_addr();
            String member_id = LoginActivity.naverLoginDTO.getMember_id();
            String member_profile = LoginActivity.naverLoginDTO.getMember_profile();
            String member_tel = LoginActivity.naverLoginDTO.getMember_tel();

            profile_name.setText(member_name);
            profile_nickname.setText(member_nickname);
            profile_phone.setText(member_tel);
            profile_location.setText(member_addr);
            profile_email.setText(member_id);

            //마이페이지에서 프로필 사진 가져옴
            imagePath = LoginActivity.naverLoginDTO.getMember_profile();
            pImgDbPathU = imagePath;
            imageDbPathU = imagePath;
            profile_photo.setVisibility(View.VISIBLE);

            // 선택된 이미지 보여주기
            Glide.with(this).load(LoginActivity.naverLoginDTO.getMember_profile())
                    .placeholder(R.color.cardview_dark_background)
                    .into(profile_photo);

        }else if( SessionCallback.kakaoLoginDTO != null ){

            String member_name = SessionCallback.kakaoLoginDTO.getMember_name();
            String member_nickname = SessionCallback.kakaoLoginDTO.getMember_nickname();
            String member_addr = SessionCallback.kakaoLoginDTO.getMember_addr();
            String member_id = SessionCallback.kakaoLoginDTO.getMember_id();
            String member_profile = SessionCallback.kakaoLoginDTO.getMember_profile();
            String member_tel = SessionCallback.kakaoLoginDTO.getMember_tel();

            profile_name.setText(member_name);
            profile_nickname.setText(member_nickname);
            profile_phone.setText(member_tel);
            profile_location.setText(member_addr);
            profile_email.setText(member_id);

            //마이페이지에서 프로필 사진 가져옴
            imagePath = SessionCallback.kakaoLoginDTO.getMember_profile();
            pImgDbPathU = imagePath;
            imageDbPathU = imagePath;
            profile_photo.setVisibility(View.VISIBLE);

            // 선택된 이미지 보여주기
            Glide.with(this).load(SessionCallback.kakaoLoginDTO.getMember_profile())
                    .placeholder(R.color.cardview_dark_background)
                    .into(profile_photo);

        }
        //프로필 눌렀을 때 팝업창
        profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    show();

            }
        });

        //프로필 수정
        Modified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfilSubActivity.class);
                startActivity(intent);
            }
        });

    }

    void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        builder.setTitle("프로필 사진");

        builder.setItems(R.array.LAN, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int pos)
            {
                String[] items = getResources().getStringArray(R.array.LAN);
                if (items[pos] == items[0]){
                    try{
                        try{
                            file = createFile();
                            Log.d("Sub1Update:FilePath ", file.getAbsolutePath());
                        }catch(Exception e){
                            Log.d("Sub1Update:error1", "Something Wrong", e);
                        }

                        profile_photo.setVisibility(View.VISIBLE);

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // API24 이상 부터
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    FileProvider.getUriForFile(getApplicationContext(),
                                            getApplicationContext().getPackageName() + ".fileprovider", file));
                            Log.d("sub1:appId", getApplicationContext().getPackageName());
                            Glide.with(getApplicationContext()).load(file)
                                    .placeholder(R.color.cardview_dark_background)
                                    .into(profile_photo);
                        }else {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        }
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, CAMERA_REQUEST);
                        }
                    }catch(Exception e){
                        Log.d("Sub1Update:error2", "Something Wrong", e);
                    }
                }else if (items[pos] == items[1]){
                    profile_photo.setVisibility(View.VISIBLE);
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);

                    Glide.with(getApplicationContext()).load(LOAD_IMAGE)
                            .placeholder(R.color.cardview_dark_background)
                            .into(profile_photo);
                }else{

                    if ( LoginActivity.loginDTO.getMember_profile() != null) {
                        String id = LoginActivity.loginDTO.getMember_id();
                        ProfileDelete profileDelete = new ProfileDelete(id, image);
                        profileDelete.execute();
                        Glide.with(getApplicationContext()).load(image)
                                .into(profile_photo);
                    }else if( LoginActivity.naverLoginDTO.getMember_profile() != null ){
                        String id = LoginActivity.naverLoginDTO.getMember_id();
                        ProfileDelete profileDelete = new ProfileDelete(id, image);
                        profileDelete.execute();
                        Glide.with(getApplicationContext()).load(image)
                                .into(profile_photo);
                    }else if( SessionCallback.kakaoLoginDTO != null ){
                        String id = SessionCallback.kakaoLoginDTO.getMember_id();
                        ProfileDelete profileDelete = new ProfileDelete(id, image);
                        profileDelete.execute();
                        Glide.with(getApplicationContext()).load(image)
                                .into(profile_photo);
                    }
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        builder.show();
    }

    private File createFile () throws IOException {
        java.text.SimpleDateFormat tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");

        String imageFileName = "My" + tmpDateFormat.format(new Date()) + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);

        return curFile;
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            try {
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                if (newBitmap != null) {
                    profile_photo.setImageBitmap(newBitmap);
                } else {
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPathU = file.getAbsolutePath();
                imageRealPathA = file.getAbsolutePath();
                String uploadFileName = imageRealPathU.split("/")[imageRealPathU.split("/").length - 1];
                imageDbPathU = ipConfig + "/app/resources/" + uploadFileName;
                imageDbPathA = ipConfig + "/app/resources/" + uploadFileName;

                ImageView profile_photo = (ImageView) findViewById(R.id.profile_photo);
                profile_photo.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

                Log.d("Sub1Update:picPath", file.getAbsolutePath());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {

            try {
                String path = "";
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                }

                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
                if (newBitmap != null) {
                    profile_photo.setImageBitmap(newBitmap);
                } else {
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPathU = path;
                imageRealPathA = path;
                String uploadFileName = imageRealPathU.split("/")[imageRealPathU.split("/").length - 1];
                imageDbPathU = ipConfig + "/app/resources/" + uploadFileName;
                imageDbPathA = ipConfig + "/app/resources/" + uploadFileName;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public String getPathFromURI (Uri contentUri){
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    //확인버튼 눌렀을때
    public void btnUpdateClicked(View view){

        if ( LoginActivity.loginDTO.getMember_profile() != null){

            if(isNetworkConnected(this) == true) {
                if(fileSize <= 30000000) {  // 파일크기가 30메가 보다 작아야 업로드 할수 있음
                    Toast.makeText(getApplicationContext(), LoginActivity.loginDTO.getMember_profile(), Toast.LENGTH_LONG).show();
                    if (LoginActivity.loginDTO.getMember_profile() != image) {

                        String id = LoginActivity.loginDTO.getMember_id();
                        ProfileUpdate profilUpdate = new ProfileUpdate(id, pImgDbPathU, imageDbPathU, imageRealPathU);
                        profilUpdate.execute();
                    }
                    //Toast.makeText(getApplicationContext(), "수정성공", Toast.LENGTH_LONG).show();
                    Intent showIntent = new Intent(getApplicationContext(), RealMainActivity.class);
                    showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                            Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                            Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                    showIntent.putExtra("imageDbPathU", imageDbPathU);
                    showIntent.putExtra("pImgDbPathU", pImgDbPathU);
                    startActivity(showIntent);

                    finish();
                }else{
                    // 알림창 띄움
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("알림");
                    builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }

            }else {
                Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            }

        }else if( LoginActivity.naverLoginDTO.getMember_profile() != null ){

            if(isNetworkConnected(this) == true){
                if(fileSize <= 30000000) {  // 파일크기가 30메가 보다 작아야 업로드 할수 있음
                    if (LoginActivity.naverLoginDTO.getMember_profile() != image) {
                        String id = LoginActivity.naverLoginDTO.getMember_id();
                        ProfileUpdate profilUpdate = new ProfileUpdate(id, pImgDbPathU, imageDbPathU, imageRealPathU);
                        profilUpdate.execute();
                    }
                    //Toast.makeText(getApplicationContext(), "수정성공", Toast.LENGTH_LONG).show();
                    Intent showIntent = new Intent(getApplicationContext(), RealMainActivity.class);
                    showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                            Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                            Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                    showIntent.putExtra("imageDbPathU", imageDbPathU);
                    showIntent.putExtra("pImgDbPathU", pImgDbPathU);
                    startActivity(showIntent);

                    finish();
                }else{
                    // 알림창 띄움
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("알림");
                    builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }

            }else {
                Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }else if(SessionCallback.kakaoLoginDTO != null){

            if(isNetworkConnected(this) == true){
                if(fileSize <= 30000000) {  // 파일크기가 30메가 보다 작아야 업로드 할수 있음

                    if (SessionCallback.kakaoLoginDTO.getMember_profile() != image) {
                        String id = SessionCallback.kakaoLoginDTO.getMember_id();
                        ProfileUpdate profilUpdate = new ProfileUpdate(id, pImgDbPathU, imageDbPathU, imageRealPathU);
                        profilUpdate.execute();
                    }
                    //Toast.makeText(getApplicationContext(), "수정성공", Toast.LENGTH_LONG).show();
                    Intent showIntent = new Intent(getApplicationContext(), RealMainActivity.class);
                    showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                            Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                            Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                    showIntent.putExtra("imageDbPathU", imageDbPathU);
                    showIntent.putExtra("pImgDbPathU", pImgDbPathU);
                    startActivity(showIntent);

                    finish();
                }else{
                    // 알림창 띄움
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("알림");
                    builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }

            }else {
                Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            }

        } else{
            if(isNetworkConnected(this) == true){
                if(fileSize <= 30000000){  // 파일크기가 30메가 보다 작아야 업로드 할수 있음

                    //ProfilIensert profilInsert = new ProfilIensert(id, imageDbPathA, imageRealPathA);
                    //profilInsert.execute();

                    Intent showIntent = new Intent(getApplicationContext(), RealMainActivity.class);
                    showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                            Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                            Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                    startActivity(showIntent);

                    finish();
                }else{
                    // 알림창 띄움
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("알림");
                    builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }

            }else {
                Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    //뒤로가기 눌렀을때
    public void ModifieClicked (View view){
        finish();
    }
}