package com.example.projectd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
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
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.projectd.ATask.MdInsert;
import com.example.projectd.ATask.MdUpdate;
import com.example.projectd.Common.CommonMethod;
import com.example.projectd.Dto.MdDTO;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import static com.example.projectd.Common.CommonMethod.ipConfig;
import static com.example.projectd.LoginActivity.loginDTO;


public class MdUpdateActivity extends AppCompatActivity {

    TextView tv_member_id , tv_md_serial;
    EditText et_md_name, et_md_price, et_md_rental_term , et_md_deposit, et_md_detail_content , et_md_serial;
    Spinner sp_md_category;

    Button btnImageCreate, btnImageSelection, btn_md_update, btn_cancel;

    String md_name = "", md_category = "", md_rental_term = "", md_detail_content = ""
            , md_price = "", md_deposit = "", member_id = "", md_serial_number = "";

    public String md_photo_url, md_photo_real_url;


    MdDTO item = null;

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
        setContentView(R.layout.activity_md_update);

        Intent intent = getIntent();
        // MD Serial Number 가져오기
        String num = null;
        if (intent != null) {
            num = intent.getStringExtra("num");
        }

        getMdDetail(num);

        tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");

        tv_member_id = findViewById(R.id.tv_member_id);
        tv_md_serial = findViewById(R.id.tv_md_serial);

        //edit text
        et_md_name = findViewById(R.id.et_md_name);
        et_md_price = findViewById(R.id.et_md_price);
        et_md_rental_term = findViewById(R.id.et_md_rental_term);
        et_md_deposit = findViewById(R.id.et_md_deposit);
        et_md_detail_content = findViewById(R.id.et_md_detail_content);
        sp_md_category = findViewById(R.id.sp_md_category);

        btnImageCreate = findViewById(R.id.btnImageCreate);
        btnImageSelection = findViewById(R.id.btnImageSelection);
        btn_md_update = findViewById(R.id.btn_md_update);
        btn_cancel = findViewById(R.id.btn_cancel);
        String md_name = loginDTO.getMember_id();



        //EDITTEXT 클릭 시 달력 띄우기
        EditText et_md_rental_term = (EditText) findViewById(R.id.et_md_rental_term);
        et_md_rental_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MdUpdateActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

               // 전송버튼 클릭
        btn_md_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_md_update();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_cancel();
            }
        });

    }//oncreate

    private void btn_cancel() {
        finish();
    }

    private void getMdDetail(String num) {
        String url = ipConfig + "app/anMdDetail?num=" + num;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        item = gson.fromJson(response.trim(), MdDTO.class);
                        onPostExecute();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void onPostExecute() {
        String md_name = item.getMd_name();
        String md_price = item.getMd_price();
        String md_rental_term = item.getMd_rental_term();
        String md_deposit = item.getMd_deposit();
        String md_detail_content = item.getMd_detail_content();
        String member_id = item.getMember_id();
        String md_serial_number = item.getMd_serial_number();

        et_md_name.setText(md_name);
        et_md_price.setText(md_price);
        et_md_rental_term.setText(md_rental_term);
        et_md_deposit.setText(md_deposit);
        et_md_detail_content.setText(md_detail_content);
        tv_member_id.setText(member_id);
        tv_md_serial.setText(md_serial_number);
    }



    private void btn_md_update() {
            md_name = et_md_name.getText().toString();
            md_price = et_md_price.getText().toString();
            md_rental_term = et_md_rental_term.getText().toString();
            md_deposit = et_md_deposit.getText().toString();
            md_detail_content = et_md_detail_content.getText().toString();
            member_id = LoginActivity.loginDTO.getMember_id();
            md_serial_number = item.getMd_serial_number();

            MdUpdate mdUpdate = new MdUpdate(md_name, md_category
                    , md_rental_term, md_detail_content, md_price, md_deposit, member_id, md_serial_number);
            mdUpdate.execute();

            Intent showIntent = new Intent(getApplicationContext(), LendListActivity.class);
            showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                    Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                    Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
            startActivity(showIntent);

            Toast.makeText(this, "업로드 성공" , Toast.LENGTH_SHORT).show();

            finish();
    }


}

