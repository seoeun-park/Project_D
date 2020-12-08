package com.example.projectd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>
implements OnNoticeItemClickListener {

    ArrayList<Notice> items = new ArrayList<>();
    OnNoticeItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_notice_sub, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notice item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnNoticeItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }



    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;

        public ViewHolder(View itemView, final OnNoticeItemClickListener listener){
            super(itemView);

            textView = itemView.findViewById(R.id.tv_day);
            textView2 = itemView.findViewById(R.id.tv_sub);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(Notice item){
            textView.setText(item.getDay());
            textView2.setText(item.getSub());
        }


    }


    public void addItem(Notice item){
        items.add(item);
    }

    public void setItems(ArrayList<Notice> items){
        this.items = items;
    }

    public Notice getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Notice item){
        items.set(position, item);
    }








}
