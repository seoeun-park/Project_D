package com.example.projectd;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.projectd.ATask.MdInsert;
import com.example.projectd.Common.CommonMethod;
import com.example.projectd.Dto.MdDTO;
import com.example.projectd.Dto.MemberDto;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.projectd.Common.CommonMethod.ipConfig;
import static com.example.projectd.Common.CommonMethod.isNetworkConnected;

public class MdInsertActivity extends AppCompatActivity {
    private static final String TAG = "MdInsertActivity";

    LinearLayout toolbar_context;   //툴바를 감싸고 있는 레이아웃

    EditText et_md_name, et_md_price, et_md_rental_term , et_md_deposit, et_md_detail_content , et_md_serial;
    Spinner sp_md_category;
    TextView tv_member_id;

    ImageView imgVwSelected;
    Button btnImageCreate, btnImageSelection, btn_submit, btn_cancel;
    //File tempSelectFile;

    String md_name = "", md_category = "", md_rental_term = "", md_detail_content = ""
            , md_price = "", md_deposit = "", member_id = "", md_serial_number = "";

    public String md_photo_url, md_photo_real_url;

    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;
    File file = null;
    long fileSize = 0;

    java.text.SimpleDateFormat tmpDateFormat;

    Calendar myCalendar = Calendar.getInstance();

    //DATEPICKER
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    // 달력에서 지정한 날짜 EDITTEXT에 띄우는 메소드
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd까지";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_md_rental_term = (EditText) findViewById(R.id.et_md_rental_term);
        et_md_rental_term.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_insert);

        tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");

        //edit text
        et_md_name = findViewById(R.id.et_md_name);
        et_md_price = findViewById(R.id.et_md_price);
        et_md_rental_term = findViewById(R.id.et_md_rental_term);
        et_md_deposit = findViewById(R.id.et_md_deposit);
        et_md_detail_content = findViewById(R.id.et_md_detail_content);
        //et_md_serial = findViewById(R.id.et_md_serial);

        sp_md_category = findViewById(R.id.sp_md_category);

        btnImageCreate = findViewById(R.id.btnImageCreate);
        btnImageSelection = findViewById(R.id.btnImageSelection);
        btn_submit = findViewById(R.id.btn_submit);
        btn_cancel = findViewById(R.id.btn_cancel);
        imgVwSelected = findViewById(R.id.imgVwSelected);

        toolbar_context = findViewById(R.id.toolbar_context);

        //EDITTEXT 클릭 시 달력 띄우기
        EditText et_md_rental_term = (EditText) findViewById(R.id.et_md_rental_term);
        et_md_rental_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MdInsertActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //카테고리 스피너
        Spinner spinner = (Spinner)findViewById(R.id.sp_md_category);
        md_category = (String) spinner.getSelectedItem();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                md_category = (String) adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //사진촬영버튼 클릭
        btnImageCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    file = createFile();
                    Log.d("FilePath ", file.getAbsolutePath());

                }catch(Exception e){
                    Log.d("LendListAdd:filepath", "Something Wrong", e);
                }

                imgVwSelected.setVisibility(View.VISIBLE);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // API24 이상 부터
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(getApplicationContext(),
                                    getApplicationContext().getPackageName() + ".fileprovider", file));
                    Log.d("LendList:appId", getApplicationContext().getPackageName());
                }else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST);
                }
            }
        });

        //이미지로드 버튼 클릭
        btnImageSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgVwSelected.setVisibility(View.VISIBLE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });

        // 전송버튼 클릭
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSubmit();
            }
        });

        // 뒤로가기 버튼 클릭 시
        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private File createFile() throws IOException {
        String imageFileName = "My" + tmpDateFormat.format(new Date()) + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);
        return curFile;
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            try {
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                if(newBitmap != null){
                    imgVwSelected.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                md_photo_real_url = file.getAbsolutePath();
                String uploadFileName = md_photo_real_url.split("/")[md_photo_real_url.split("/").length - 1];
                md_photo_url = ipConfig + "/app/resources/" + uploadFileName;

            } catch (Exception e){
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
                if(newBitmap != null){
                    imgVwSelected.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                md_photo_real_url = path;
                Log.d("LendListAdd", "md_photo_real_url Path : " + md_photo_real_url);
                String uploadFileName = md_photo_real_url.split("/")[md_photo_real_url.split("/").length - 1];
                md_photo_url = ipConfig + "/app/resources/" + uploadFileName;

            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }


    private String getPathFromURI(Uri contentUri) {
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


    //전송버튼 클릭 시
    public void btnSubmit(){
        if(isNetworkConnected(this) == true){

            Log.d("main:mdinsertActivity", "btnSubmit: " + fileSize);


            if(fileSize <= 30000000) {  // 파일크기가 30메가 보다 작아야 업로드 할수 있음
                md_name = et_md_name.getText().toString();
                md_price = et_md_price.getText().toString();
                md_rental_term = et_md_rental_term.getText().toString();
                md_deposit = et_md_deposit.getText().toString();
                md_detail_content = et_md_detail_content.getText().toString();
                member_id = LoginActivity.loginDTO.getMember_id();

                MdInsert mdInsert = new MdInsert(md_name, md_photo_url, md_photo_real_url,  md_category
                        , md_rental_term, md_detail_content, md_price, md_deposit, member_id, md_serial_number);
                mdInsert.execute();

                Intent showIntent = new Intent(getApplicationContext(), LendListActivity.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                        Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                        Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                startActivity(showIntent);

                Toast.makeText(this, "업로드 성공" , Toast.LENGTH_SHORT).show();

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

        }else{
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void btnCancleClicked(View view){
        finish();
    }

}