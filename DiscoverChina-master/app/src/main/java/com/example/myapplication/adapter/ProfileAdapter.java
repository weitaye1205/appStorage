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
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.DetailsActivity;
import com.example.myapplication.manager.PrefsManager;
import com.example.myapplication.model.Photo;
import java.util.ArrayList;
import java.util.Objects;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Photo> photoList;

    public ProfileAdapter(Context context) {
        this.context = context;
        this.photoList = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addNewPhotos(ArrayList<Photo> photoList) {
        this.photoList.clear();
        this.photoList.addAll(photoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Photo photoItem = photoList.get(position);
        String photoColor = photoItem.getColor();
        String photoUrl = Objects.requireNonNull(photoItem.getUrls()).getThumb();

        holder.tvDescription.setText(Objects.requireNonNull(photoItem.getUser()).getBio());
        Glide.with(context).load(photoUrl).placeholder(new ColorDrawable(Color.parseColor(photoColor))).into(holder.ivPhoto);

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

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    private void callDetails(int position) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("position", position);
        PrefsManager.getInstance(context).saveArrayList(PrefsManager.KEY_PHOTO_LIST, photoList);
        context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        ImageView iv_like;
        TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_pin);
            iv_like = itemView.findViewById(R.id.iv_like);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}
