package com.example.projectd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.projectd.ATask.CategorySelect;
import com.example.projectd.Dto.MdDTO;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class CategoryActivity extends Fragment {
    public  static MdDTO mdDTO = null;

    CategorySelect categorySelect;

    ArrayList<MdDTO> myItemArrayList;
    RecyclerView recyclerView;

    CategoryAdapter adapter;
    StringBuffer stringBuffer;
    ProgressDialog progressDialog;
    LinearLayout category1, category2, category3, category4, category5,
            category6, category7, category8, category9, category10;

    Button categoryBtn1, categoryBtn2, categoryBtn3, categoryBtn4, categoryBtn5, categoryBtn6,
            categoryBtn7, categoryBtn8, categoryBtn9, categoryBtn10;

    ViewGroup viewGroup;

    MainActivity mainActivity;

    LinearLayout toolbar_context;   //툴바를 감싸고 있는 레이아웃

    /*private Context mContext = CategoryActivity.this;*/
    private static final int ACTIVITY_NUM = 3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_category, container, false);

        category1 = viewGroup.findViewById(R.id.category1);
        category2 = viewGroup.findViewById(R.id.category2);
        category3 = viewGroup.findViewById(R.id.category3);
        category4 = viewGroup.findViewById(R.id.category4);
        category5 = viewGroup.findViewById(R.id.category5);
        category6 = viewGroup.findViewById(R.id.category6);
        category7 = viewGroup.findViewById(R.id.category7);
        category8 = viewGroup.findViewById(R.id.category8);
        category9 = viewGroup.findViewById(R.id.category9);
        category10 = viewGroup.findViewById(R.id.category10);
        toolbar_context = viewGroup.findViewById(R.id.toolbar_context);
        mainActivity = new MainActivity();

        categoryBtn1 = viewGroup.findViewById(R.id.categoryBtn1);
        categoryBtn2 = viewGroup.findViewById(R.id.categoryBtn2);
        categoryBtn3 = viewGroup.findViewById(R.id.categoryBtn3);
        categoryBtn4 = viewGroup.findViewById(R.id.categoryBtn4);
        categoryBtn5 = viewGroup.findViewById(R.id.categoryBtn5);
        categoryBtn6 = viewGroup.findViewById(R.id.categoryBtn6);
        categoryBtn7 = viewGroup.findViewById(R.id.categoryBtn7);
        categoryBtn8 = viewGroup.findViewById(R.id.categoryBtn8);
        categoryBtn9 = viewGroup.findViewById(R.id.categoryBtn9);
        categoryBtn10 = viewGroup.findViewById(R.id.categoryBtn10);


        //디지털,가전 카테고리
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn1.getText().toString());
                startActivity(intent);
            }
        });

        categoryBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn1.getText().toString());
                startActivity(intent);
            }
        });

        //유,아동 카테고리
        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn2.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        categoryBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn2.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        //생활용품 카테고리
        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn3.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        categoryBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn3.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        //스포츠,레져 카테고리
        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn4.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        categoryBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn4.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        //의류,잡화 카테고리
        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn5.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        categoryBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn5.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        //게임,취미 카테고리
        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn6.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        categoryBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn6.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        //뷰티,미용 카테고리
        category7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn7.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        categoryBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn7.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        //반려동물용품 카테고리
        category8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn8.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        categoryBtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn8.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        //기타 물품 카테고리
        category9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn9.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        categoryBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn9.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        //무료나눔 카테고리
        category10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn10.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        categoryBtn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryViewActivity.class);
                intent.putExtra("category",categoryBtn10.getText().toString());
                startActivity(intent);
            }//onClick()
        });

        // 툴바안의 뒤로가기 버튼을 클릭했을 때
        toolbar_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity fragment = new MainActivity();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_layout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return viewGroup;
    }//onCreateView()




}//class