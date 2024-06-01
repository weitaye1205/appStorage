package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.DetailsActivity;
import com.example.myapplication.fragment.SearchFragment;
import com.example.myapplication.manager.PrefsManager;
import com.example.myapplication.model.Photo;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private final SearchFragment context;
    private final ArrayList<Photo> items;

    public SearchAdapter(SearchFragment context, ArrayList<Photo> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Photo photoItem = items.get(position);
        String photoUrl = photoItem.getUrls().getThumb();
        String photoColor = photoItem.getColor();

        holder.tv_description.setText(Objects.requireNonNull(photoItem.getUser()).getBio());
        Glide.with(context).load(photoUrl).placeholder(new ColorDrawable(Color.parseColor(photoColor))).into(holder.iv_pin);

        holder.iv_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDetails(position);
            }
        });
        holder.iv_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDetails(position);
            }
        });

        final boolean[] isLiked = {PrefsManager.getInstance(context.getActivity()).getBoolean(getLikedKey(photoItem.getId()), false)};

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

                PrefsManager.getInstance(context.getActivity()).saveBoolean(getLikedKey(photoItem.getId()), isLiked[0]);
            }
        });
    }

    private String getLikedKey(String photoId) {
        return "liked_" + photoId;
    }

    private void callDetails(int position) {
        Intent intent = new Intent(context.getActivity(), DetailsActivity.class);
        intent.putExtra("photoList", new Gson().toJson(items));
        intent.putExtra("position", position);
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView iv_pin;
        TextView tv_description;
        ImageView iv_like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_pin = itemView.findViewById(R.id.iv_pin);
            tv_description = itemView.findViewById(R.id.tv_description);
            iv_like = itemView.findViewById(R.id.iv_like);
        }
    }
}
