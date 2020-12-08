package com.example.projectd;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AlramSubActivity extends BaseAdapter {

    Context context;
    ArrayList<AlramSubDTO> dtos;

    LayoutInflater inflater;
    AlertDialog dialog;

    Point size;

    public AlramSubActivity(Context context, ArrayList<AlramSubDTO> dtos, Point size) {
        this.context = context;
        this.dtos = dtos;
        this.size = size;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void addDto(AlramSubDTO dto) {
        dtos.add(dto);
    }

    public void delDto(int position) {
        dtos.remove(position);
    }

    public void removeAllDto() {
        dtos.clear();
    }




    public int getCount() {
        return dtos.size();
    }


    public Object getItem(int position) {
        return dtos.get(position);
    }


    public long getItemId(int position) {
        return 0;
    }



    public View getView(final int position, View view, ViewGroup viewGroup) {
        SingerViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.activity_alram_sub, viewGroup, false);
            viewHolder = new SingerViewHolder();
            viewHolder.tvContent = view.findViewById(R.id.content);
            viewHolder.tvDate = view.findViewById(R.id.date);

            view.setTag(viewHolder);
        } else {
            viewHolder = (SingerViewHolder) view.getTag();
        }

        AlramSubDTO dto = dtos.get(position);
        String content = dto.getContent();
        final String date = dto.getMd_rental_term();


        viewHolder.tvContent.setText(content);
        viewHolder.tvDate.setText(date);

        return  view;
    }




    public class SingerViewHolder{

        public TextView tvContent, tvDate;

    }

}