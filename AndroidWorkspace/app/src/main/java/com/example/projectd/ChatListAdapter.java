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
import com.example.projectd.Dto.MemberDto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder>
        implements OnChatItemClickListener {
    //ArrayList<ChatDto> items = new ArrayList<ChatDto>();
    List<MemberDto> items;
    OnChatItemClickListener listener;
    Context context;

    public ChatListAdapter(Context context, List<MemberDto> items){
        this.context = context;
        this.items = items;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_chat_list_sub, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MemberDto item = items.get(position);
        //holder.setItem(item);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnChatItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }



    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_chat_nickname;
        TextView tv_chat_addr;
        ImageView iv_chatlist_img;
        public ViewHolder(View itemView, final OnChatItemClickListener listener){
            super(itemView);

            tv_chat_nickname = itemView.findViewById(R.id.tv_chat_nickname);
            tv_chat_addr = itemView.findViewById(R.id.tv_chat_addr);
            //tv_last_msg = itemView.findViewById(R.id.tv_last_msg);
            iv_chatlist_img = itemView.findViewById(R.id.iv_chatlist_img);

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


          public void setItem(MemberDto item){
              String member_addr = item.getMember_addr();

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

              tv_chat_nickname.setText(item.getMember_nickname());
              tv_chat_addr.setText(member_addr_re);


              //Glide.with(itemView).load(item.getMember_profile()).into(iv_chatlist_img);
              //Glide.with(itemView).load(item.getMember_profile()).into(iv_chatlist_img);
          }

    }


    public void addItem(MemberDto item){
        items.add(item);
    }

    public void setItems(ArrayList<MemberDto> items){
        this.items = items;
    }

    public MemberDto getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, MemberDto item){
        items.set(position, item);
    }








}
