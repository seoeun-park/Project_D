package com.example.projectd;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.projectd.Dto.ChatDto;
import java.util.List;
import static com.example.projectd.LoginActivity.loginDTO;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatDto> mDataset;
    private String myNickName;
    private Context context;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_nickname;
        public TextView TextView_msg;
        public ImageView ImageView_profile;
        public LinearLayout linearlayout_destination;
        public LinearLayout linearlayout_main;

        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            TextView_nickname = v.findViewById(R.id.TextView_nickname);
            TextView_msg = v.findViewById(R.id.TextView_msg);
            ImageView_profile = v.findViewById(R.id.ImageView_profile);
            linearlayout_destination = v.findViewById(R.id.messageItem_linearlayout_destination);
            linearlayout_main = v.findViewById(R.id.messageItem_linearlayout_main);
            rootView = v;

        }
    }

    private Bitmap bitmap;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(List<ChatDto> myDataset, Context context, String myNickName) {
        //{"1","2"}
        mDataset = myDataset;
        this.myNickName = myNickName;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_chat_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ChatDto chat = mDataset.get(position);

        holder.TextView_msg.setText(chat.getMsg());

        if(chat.getName().equals(this.myNickName)) {
            holder.TextView_msg.setBackgroundResource(R.drawable.rightbubble);
            holder.TextView_msg.setText(mDataset.get(position).getMsg());
            holder.linearlayout_destination.setVisibility(View.VISIBLE);
            holder.linearlayout_main.setGravity(Gravity.RIGHT);
        }
        else {

           /* Glide.with(context).asBitmap().load(loginDTO.getMember_profile())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            bitmap = resource;
                        }
                    });
            holder.ImageView_profile.setImageBitmap(bitmap);*/
            //이미지 넣기
            Glide.with(context).load(loginDTO.getMember_profile())
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.ImageView_profile);

            holder.linearlayout_destination.setVisibility(View.VISIBLE);
            holder.linearlayout_main.setGravity(Gravity.LEFT);
            holder.TextView_msg.setBackgroundResource(R.drawable.leftbubble);
            holder.TextView_msg.setText(mDataset.get(position).getMsg());
            holder.TextView_nickname.setText(chat.getName());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        //삼항 연산자
        return mDataset == null ? 0 :  mDataset.size();
    }

    public ChatDto getChat(int position) {
        return mDataset != null ? mDataset.get(position) : null;
    }

    public void addChat(ChatDto chat) {
        mDataset.add(chat);
        notifyItemInserted(mDataset.size() -1); //갱신e

    }






}