package com.example.projectd;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationViewHelper {
    public static void enableNavigation(final Context context, BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab1:
                        Intent intent1 = new Intent(context, MainActivity.class); //메인
                        context.startActivity(intent1);
                        break;
                    case R.id.tab2:
                        Intent intent2 = new Intent(context, CategoryActivity.class); //카테고리
                        context.startActivity(intent2);
                        break;
                    case R.id.tab3:
                        Intent intent3 = new Intent(context, MdInsertActivity.class); //글등록
                        context.startActivity(intent3);
                        break;
                    case R.id.tab4:
                        Intent intent4 = new Intent(context, ChatActivity.class); //채팅
                        context.startActivity(intent4);
                        break;
                    case R.id.tab5:
                        Intent intent5 = new Intent(context, MypageActivity.class); //마이페이지
                        context.startActivity(intent5);
                        break;
                }
                return false;
            }
        });
    }
}
