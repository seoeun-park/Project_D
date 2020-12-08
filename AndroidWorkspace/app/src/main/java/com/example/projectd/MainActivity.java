package com.example.projectd;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectd.ATask.AnMainSelect;
import com.example.projectd.Dto.MdDTO;
import com.example.projectd.Dto.MemberDto;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;
import static com.example.projectd.LoginActivity.loginDTO;
import static com.example.projectd.LoginActivity.naverLoginDTO;
import static com.example.projectd.SessionCallback.kakaoLoginDTO;

public class MainActivity extends Fragment {
    private static final String TAG = "main:MainActivity";
    RecyclerView recyclerView;
    MainMdAdapter adapter;
    ViewGroup viewGroup;
    ImageButton btn_like;
    ArrayList<MdDTO> items;
    TextView tv_addr;
    String member_addr;     //회원 주소를 저장하는 변수
    String member_id, member_loginType;     //위치 정보를 수정할때 필요한 변수
    private static final int SOCAIL_LOCATION_ACTIVITY_REQUEST_CODE = 0;

    /*private Context mContext = MainActivity.this;*/
    private static final int ACTIVITY_NUM = 3;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_main, container, false);

        Log.d(TAG, "onCreateView: 들어옴!!!");

        // 상단 바 없애기
        /*if (Build.VERSION.SDK_INT >= 21) {
            getSupportActionBar().hide();
        } else if (Build.VERSION.SDK_INT < 21) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }*/


        items = new ArrayList<>();
        recyclerView = viewGroup.findViewById(R.id.recyclerView);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new MainMdAdapter(getContext(), items);

        //adapter.addItem(new Main("컴퓨터", "3500원"));
        //adapter.addItem(new Main("모니터", "3000원"));

        recyclerView.setAdapter(adapter);

        tv_addr = viewGroup.findViewById(R.id.tv_addr);

        // member_addr에 회원정보에 저장되어 있는 주소 할당
        if(loginDTO != null) {
            member_addr = loginDTO.getMember_addr();
            member_id = loginDTO.getMember_id();
            loginDTO.setMember_loginType("M");
            member_loginType = loginDTO.getMember_loginType();
        } else if(naverLoginDTO != null) {
            member_addr = naverLoginDTO.getMember_addr();
            member_id = naverLoginDTO.getMember_id();
            member_loginType = naverLoginDTO.getMember_loginType();
        } else if(kakaoLoginDTO != null) {
            member_addr = kakaoLoginDTO.getMember_addr();
            member_id = kakaoLoginDTO.getMember_id();
            member_loginType = kakaoLoginDTO.getMember_loginType();
        }

        tv_addr.setText(trimAddr(member_addr));


        /*
        // 주소에 상세 주소가 안나오도록 하게 함(시, 도, 군, 구, 동, 면만 나오게끔)
        String[] split1 = member_addr.split(" ");
        String member_addr_re = "";

        for (int i = 0; i < split1.length; i++) {
            if(Pattern.matches("[가-힣]+(시|도|군|구|동|면)", split1[i])) {
                member_addr_re += split1[i] + " ";
            }
        }
        member_addr_re = member_addr_re.trim();
        // 마지막 공백 제거

        Log.d(TAG, "onCreateView: " + member_addr_re);
       */

        //찜목록
        btn_like = viewGroup.findViewById(R.id.btn_like);
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FavListActivity.class);
                startActivity(intent);
            }
        });

        //gps
        ImageButton btn_gps = viewGroup.findViewById(R.id.btn_gps);
        btn_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SocialLocationActivity.class);
                intent.putExtra("update_location", true);
                intent.putExtra("member_id", member_id);
                intent.putExtra("member_loginType", member_loginType);
                startActivityForResult(intent, SOCAIL_LOCATION_ACTIVITY_REQUEST_CODE);
            }
        });

        //검색
        ImageButton btn_search = viewGroup.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        //알람
        ImageButton btn_alram = viewGroup.findViewById(R.id.btn_alram);
        btn_alram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlramActivity.class);
                startActivity(intent);
            }
        });


        adapter.setOnItemClickListener(new OnMainMdItemClickListener() {
            public static final int main = 1001;

            @Override
            public void onItemClick(MainMdAdapter.ViewHolder holder, View view, int position) {
                MdDTO item = adapter.getItem(position);

                //Toast.makeText(getActivity(), "아이템 선택됨" + item.getMd_name(),
                        //Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), MdDetailActivity.class);
                intent.putExtra("item", item);
                startActivityForResult(intent, main);
            }

        });

        AnMainSelect anMainSelect = new AnMainSelect(items, adapter, trimAddr(member_addr));
        anMainSelect.execute();

        return viewGroup;

    }//oncreateView()

    //SocialLocationActivity에서 보낸 주소를 받아오는 메소드
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: " + loginDTO.getMember_addr());

        if (requestCode == SOCAIL_LOCATION_ACTIVITY_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            //updated_addr = data.getStringExtra("member_addr");
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }//onActivityResult()

    // 주소에 상세 주소가 안나오도록 하게 하는 메소드 (시, 도, 군, 구, 동, 면만 나오게끔)
    public String trimAddr(String addr) {
        String[] split1 = addr.split(" ");
        String member_addr_re = "";

        for (int i = 0; i < split1.length; i++) {
            if(Pattern.matches("[가-힣]+(시|도|군|구|동|면)", split1[i])) {
                member_addr_re += split1[i] + " ";
            }
        }
        member_addr_re = member_addr_re.trim();
        // 마지막 공백 제거

        return member_addr_re;
    } //trimAddr()
}//class