package com.example.myapplication.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.DetailsActivity;
import com.example.myapplication.fragment.DetailsFragment;
import com.example.myapplication.manager.PrefsManager;
import com.example.myapplication.model.Photo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.PhotoViewHolder> {
    private Context context;
    private ArrayList<Photo> photoList = new ArrayList<>();

    public DetailsAdapter(Context context) {
        this.context = context;
    }

    public void addPhotos(ArrayList<Photo> photoList) {
        this.photoList.addAll(photoList);
        notifyDataSetChanged();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_view, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Photo photoItem = photoList.get(position);
        String photoColor = photoItem.getColor();
        String photoUrl = photoItem.getUrls().getThumb();

        holder.tvDescription.setText(photoItem.getUser().getBio());
        Glide.with(context)
                .load(photoUrl)
                .placeholder(new ColorDrawable(Color.parseColor(photoColor)))
                .into(holder.ivPhoto);

        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDetails(position);
            }
        });

        final boolean[] isLiked = {PrefsManager.getInstance(context).getBoolean(getLikedKey(photoItem.getId()), false)};

        if (isLiked[0]) {
            holder.iv_like.setImageResource(R.drawable.liked);
        } else {
            holder.iv_like.setImageResource(R.drawable.like);
        }

        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLiked[0] = !isLiked[0];

                if (isLiked[0]) {
                    holder.iv_like.setImageResource(R.drawable.liked);
                } else {
                    holder.iv_like.setImageResource(R.drawable.like);
                }

                PrefsManager.getInstance(context).saveBoolean(getLikedKey(photoItem.getId()), isLiked[0]);
            }
        });
    }

    private String getLikedKey(String photoId) {
        return "liked_" + photoId;
    }

    private void callDetails(int position) {
        Intent intent = new Intent(context, DetailsActivity.class);
        String json = new Gson().toJson(photoList);
        intent.putExtra("photoList", json);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateLikedState(String photoId, boolean isLiked) {
        for (Photo photo : photoList) {
            if (Objects.equals(photo.getId(), photoId)) {
                photo.setLiked(isLiked);
                notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        ImageView iv_like;
        TextView tvDescription;

        PhotoViewHolder(View view) {
            super(view);
            ivPhoto = view.findViewById(R.id.iv_pin);
            iv_like = view.findViewById(R.id.iv_like);
            tvDescription = view.findViewById(R.id.tv_description);

        }
    }
}
