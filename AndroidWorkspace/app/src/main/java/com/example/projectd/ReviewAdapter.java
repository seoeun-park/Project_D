package com.example.projectd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectd.Dto.ReviewDto;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>
            implements OnReviewItemClickListener {

    static Context context1;
    String md_serial_number;
    ArrayList<ReviewDto> reviews;
    OnReviewItemClickListener listener;

    public ReviewAdapter(Context context, String md_serial_number, ArrayList<ReviewDto> reviews) {
        this.context1 = context;
        this.md_serial_number = md_serial_number;
        this.reviews = reviews;
    }

    public ReviewAdapter(Context context, ArrayList<ReviewDto> reviews) {
        this.context1 = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.review_sub, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewDto review = reviews.get(position);
        holder.setItem(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setOnItemClickListener(OnReviewItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_nickname, review_text, review_scope;
        CircleImageView review_profile_photo;
        RatingBar review_ratingBar;

        public ViewHolder(View itemView, final OnReviewItemClickListener listener) {
            super(itemView);

            user_nickname = itemView.findViewById(R.id.user_nickname);
            review_text = itemView.findViewById(R.id.review_text);
            review_scope = itemView.findViewById(R.id.review_scope);
            review_profile_photo = itemView.findViewById(R.id.review_profile_photo);
            review_ratingBar = itemView.findViewById(R.id.review_ratingBar);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(ReviewDto review){
            user_nickname.setText(review.getMember_nickname());
            review_text.setText(review.getReview_content());
            review_scope.setText(review.getReview_scope() + "점");
            Glide.with(context1).load(review.getMember_profile()).placeholder(R.drawable.person).into(review_profile_photo);
            //review_profile_photo
            review_ratingBar.setRating(Float.parseFloat(review.getReview_scope()));
        }
    }

        public void removeAll(){
            reviews.clear();
    }

        public void addItem(ReviewDto review){
            reviews.add(review);
        }

        public void setItems(ArrayList<ReviewDto> reviews){
            this.reviews = reviews;
        }

        public ReviewDto getItem(int position){
            return reviews.get(position);
        }

        public void setItem(int position, ReviewDto review){
            reviews.set(position, review);
        }

    }

