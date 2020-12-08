package com.example.projectd;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectd.ATask.MdDelete;
import com.example.projectd.ATask.MdRentStatusInsert;
import com.example.projectd.Dto.MdDTO;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LendAdapter extends RecyclerView.Adapter<LendAdapter.ViewHolder> implements OnLendItemCLickListener {

    private static final String TAG = "LendAdapter";

    Context context;
    List<MdDTO> items;
    OnLendItemCLickListener listener;
    String md_rent_status;
    String md_serial_number;
    public LendAdapter(Context context, List<MdDTO> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_lend_list_sub, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MdDTO item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() { return items == null ? 0 : items.size(); }

    public void setOnItemClickListener(OnLendItemCLickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(LendAdapter.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_md_lend_name;
        TextView tv_md_lender;
        TextView tv_md_category;
        TextView tv_md_serial_num;
        ImageView iv_md_img;
        Button bt_disable;
        Button bt_able;
        Button bt_md_modify;
        Button bt_md_delete;
        //Spinner spinner2;

        public ViewHolder(final View itemView, final OnLendItemCLickListener listener){
            super(itemView);

            tv_md_lend_name = itemView.findViewById(R.id.tv_md_lend_name);
            tv_md_lender = itemView.findViewById(R.id.tv_md_lender);
            tv_md_category = itemView.findViewById(R.id.tv_md_category);
            tv_md_serial_num = itemView.findViewById(R.id.tv_md_serial_num);
            iv_md_img = itemView.findViewById(R.id.iv_md_img);
            bt_disable = itemView.findViewById(R.id.bt_disable);
            bt_able = itemView.findViewById(R.id.bt_able);
            bt_md_modify = itemView.findViewById(R.id.bt_md_modify);
            bt_md_delete = itemView.findViewById(R.id.bt_md_delete);
            //spinner2 = itemView.findViewById(R.id.sp_md_nego_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                       listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });

        }

        public void setItem(final MdDTO item){
            tv_md_lend_name.setText(item.getMd_name());
            tv_md_lender.setText(item.getMember_id());
            tv_md_category.setText(item.getMd_category());
            tv_md_serial_num.setText(item.getMd_serial_number());
            Glide.with(itemView).load(item.getMd_photo_url()).into(iv_md_img);

            bt_disable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    md_rent_status = "1";
                    md_serial_number = item.getMd_serial_number();
                    MdRentStatusInsert mdRentStatusInsert = new MdRentStatusInsert(md_rent_status, md_serial_number);
                    mdRentStatusInsert.execute();
                    Toast.makeText(context, "거래완료로 변경되었습니다", Toast.LENGTH_LONG).show();
                }
            });
            bt_able.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    md_rent_status = "0";
                    md_serial_number = item.getMd_serial_number();
                    MdRentStatusInsert mdRentStatusInsert = new MdRentStatusInsert(md_rent_status, md_serial_number);
                    mdRentStatusInsert.execute();
                    Toast.makeText(context, "거래가능으로 변경되었습니다", Toast.LENGTH_LONG).show();
                }
            });
            final int position = getAdapterPosition();
            bt_md_modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "onClick: 클릭" + position);
                    Intent intent = new Intent(context, MdUpdateActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("num", items.get(position).getMd_serial_number());
                    context.startActivity(intent);
                }
            });
            bt_md_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    md_serial_number = item.getMd_serial_number();
//                    MdDelete mdDelete = new MdDelete(md_serial_number);
//                    mdDelete.execute();
//                    Toast.makeText(context, "삭제성공", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("정말로 삭제하시겠습니까?");
                    builder.setMessage("삭제하신 데이터는 되돌릴 수 없습니다");
                    builder.setPositiveButton("삭제",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                      md_serial_number = item.getMd_serial_number();
                                      MdDelete mdDelete = new MdDelete(md_serial_number);
                                      mdDelete.execute();
                                      Toast.makeText(context, "삭제성공", Toast.LENGTH_LONG).show();
                                }
                            });
                    builder.setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    Toast.makeText(context, "삭제가 취소되었습니다", Toast.LENGTH_LONG).show();
                                    ((Activity) context).finish();
                                    context.startActivity(((Activity) context).getIntent());
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });

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

