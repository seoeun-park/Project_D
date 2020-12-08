package com.example.projectd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectd.Dto.MdDTO;

import java.util.ArrayList;

public class MainMdAdapter extends RecyclerView.Adapter<MainMdAdapter.ViewHolder>
        implements OnMainMdItemClickListener {

    static Context context;
    ArrayList<MdDTO> items;
    OnMainMdItemClickListener listener;


    public MainMdAdapter(Context context, ArrayList<MdDTO> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.card_list, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MdDTO item = items.get(position);

        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnMainMdItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView, textView2, tv_favCount;
        ImageView iv_img, img_possible;

        public ViewHolder(View itemView, final OnMainMdItemClickListener listener){
            super(itemView);

            textView = itemView.findViewById(R.id.tv_title);
            textView2 = itemView.findViewById(R.id.tv_price);
            iv_img = itemView.findViewById(R.id.iv_img);
            img_possible = itemView.findViewById(R.id.img_possible);
            tv_favCount = itemView.findViewById(R.id.tv_favCount);

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
        public void setItem(MdDTO item){
            textView.setText(item.getMd_name());
            textView2.setText(item.getMd_price() + "원");
            tv_favCount.setText(item.getMd_fav_count());
            Glide.with(context).load(item.getMd_photo_url()).placeholder(R.drawable.spinner_icon).into(iv_img);

            //getMd_rent_status(대여상태)가 1이면 대여중, 0이면 대여가능 표시
            if (item.getMd_rent_status().equals("1")){
                img_possible.setImageResource(R.drawable.impossible);
            }else {
                img_possible.setImageResource(R.drawable.possible);
            }

        }
    }

    public void removeAll(){
        items.clear();
    }

    public void addItem(MdDTO item){
        items.add(item);
    }

    public void setItems(ArrayList<MdDTO> items){
        this.items = items;
    }

    public MdDTO getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, MdDTO item){
        items.set(position, item);
    }


}
