package com.example.projectd;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectd.ATask.ReviewDelete;
import com.example.projectd.Dto.ReviewDto;

import java.util.ArrayList;
import java.util.List;

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.ViewHolder>
        implements OnRentItemCLickListener {
    private static final String TAG = "RentAdapter";

    List<ReviewDto> items;
    OnRentItemCLickListener listener;
    Context mContext;
    Button bt_delete;
    String review_num;
    public RentAdapter(Context mContext, List<ReviewDto> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_rent_list_sub, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewDto item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnRentItemCLickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(RentAdapter.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_member_id;
        TextView tv_rate;
        TextView tv_content;
        TextView tv_member_nick;
        TextView tv_review_num;
        public ViewHolder(final View itemView,
                          final OnRentItemCLickListener listener){
            super(itemView);

            tv_member_id = itemView.findViewById(R.id.tv_member_id);
            tv_rate = itemView.findViewById(R.id.tv_rate);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_member_nick = itemView.findViewById(R.id.tv_member_nick);
            tv_review_num = itemView.findViewById(R.id.tv_review_num);
            bt_delete = itemView.findViewById(R.id.bt_delete);
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

        public void setItem(final ReviewDto item){
            tv_member_id.setText(item.getMember_id());
            tv_rate.setText(item.getReview_scope());
            tv_content.setText(item.getReview_content());
            tv_member_nick.setText(item.getMember_nickname());
            tv_review_num.setText(item.getReview_num());

            bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        review_num = items.get(position).getReview_num();
                        ReviewDelete reviewDelete = new ReviewDelete(review_num);
                        reviewDelete.execute();
                        Toast.makeText(mContext, "삭제성공", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


    public void addItem(ReviewDto item){
        items.add(item);
    }

    public void setItems(ArrayList<ReviewDto> items){
        this.items = items;
    }

    public ReviewDto getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, ReviewDto item){
        items.set(position, item);
    }


}

