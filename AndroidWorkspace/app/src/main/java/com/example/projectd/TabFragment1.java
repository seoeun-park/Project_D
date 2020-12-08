package com.example.projectd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectd.ATask.AnReviewSelect;
import com.example.projectd.Dto.MdDTO;
import com.example.projectd.Dto.ReviewDto;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class  TabFragment1 extends Fragment {

    RecyclerView recyclerView;
    ReviewAdapter adapter;
    MdDetailActivity activity;
    ImageView no_review;
    ScrollView reviewScrollView;

    ArrayList<ReviewDto> reviews;
    String md_serial_number;

    /*private Context mContext = MainActivity.this;*/
    private static final int ACTIVITY_NUM = 3;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_tab1,container,false);

        activity = (MdDetailActivity) getActivity();

        if(activity.mBundle != null){
            Bundle bundle = activity.mBundle;
            md_serial_number = bundle.getString("md_serial_number");
            //회원의 다른상품탭을 보려면 null로 만들면 안됨
            //activity.mBundle = null;
        }

        reviewScrollView = rootView.findViewById(R.id.reviewScrollView); //리스트가 있을경우 표시
        no_review = rootView.findViewById(R.id.no_review); //리스트가 없다면 대신 보여줄 이미지

        reviews = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recyclerView);

        //recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ReviewAdapter(getContext(), reviews);

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnReviewItemClickListener() {
            //public static final int main = 1001;

            @Override
            public void onItemClick(ReviewAdapter.ViewHolder holder, View view, int position) {
                //ReviewDto review = adapter.getItem(position);

                //Intent intent = new Intent(getActivity(), MdDetailActivity.class);
                //intent.putExtra("item", item);
                //startActivityForResult(intent, main);
            }
        });

        int reviewsSize = 0;   //리뷰목록갯수
        AnReviewSelect anReviewSelect = new AnReviewSelect(reviews, adapter, md_serial_number);
        try {
            reviewsSize = anReviewSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(reviewsSize == 0){
            no_review.setVisibility(View.VISIBLE);
            reviewScrollView.setVisibility(View.GONE);
        }else{
            no_review.setVisibility(View.GONE);
            reviewScrollView.setVisibility(View.VISIBLE);
        }

        /*MdDetailActivity activity = (MdDetailActivity) getActivity();
        TabLayout.Tab tab = activity.tabs.getTabAt(0);
        tab.select();*/


        return  rootView;


    }
}