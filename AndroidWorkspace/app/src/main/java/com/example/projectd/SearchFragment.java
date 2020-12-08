package com.example.projectd;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.projectd.ATask.SearchSelect;
import com.example.projectd.Dto.MdDTO;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SearchFragment extends Fragment {
    SearchActivity activity;

    RecyclerView recyclerView;
    SearchAdapter adapter;
    ArrayList<MdDTO> items;
    ImageView no_search;
    ScrollView searchScrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_search,container,false);

        searchScrollView = rootView.findViewById(R.id.searchScrollView);
        no_search = rootView.findViewById(R.id.no_search);

        activity = (SearchActivity) getActivity();
        items = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL , false);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new SearchAdapter(activity.getApplicationContext(), items);


        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnSearchItemClickListener() {
            public static final int main = 1001;
            @Override
            public void onItemClick(SearchAdapter.ViewHolder holder, View view, int position) {
                MdDTO item = adapter.getItem(position);

                Toast.makeText(getActivity(), "아이템 선택됨" + item.getMd_name(),
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(activity.getApplicationContext(), MdDetailActivity.class);
                startActivityForResult(intent, main);
            }
        });

  /*      int scrollSize = recyclerView.getScrollBarSize()
        if (scrollSize == 0){
            no_search.setVisibility(View.VISIBLE);
            searchScrollView.setVisibility(View.GONE);
        }else {
            no_search.setVisibility(View.GONE);
            searchScrollView.setVisibility(View.VISIBLE);
        }
*/


/*
        int itemSize = 0; //검색목록갯수
        SearchSelect searchSelect = new SearchSelect(items, adapter);
        try {
            itemSize = searchSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(itemSize == 0){
            no_search.setVisibility(View.VISIBLE);
            searchScrollView.setVisibility(View.GONE);
        }else{
            no_search.setVisibility(View.GONE);
            searchScrollView.setVisibility(View.VISIBLE);
        }
*/

        return  rootView;
    }
}