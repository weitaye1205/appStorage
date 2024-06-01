package com.example.myapplication.fragment;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapter.DetailsAdapter;
import com.example.myapplication.database.PinRepository;
import com.example.myapplication.helper.SpacesItemDecoration;
import com.example.myapplication.manager.PrefsManager;
import com.example.myapplication.model.Photo;
import com.example.myapplication.model.Pin;
import com.example.myapplication.model.RelatedPhotos;
import com.example.myapplication.network.RetrofitHttp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {
    private Photo photoItem;
    private DetailsAdapter adapter;
    private PinRepository pinRepository;
    private TextView tvRelated;

    public DetailsFragment(Photo photoItem) {
        this.photoItem = photoItem;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pinRepository = new PinRepository(requireActivity().getApplication());
        adapter = new DetailsAdapter(requireContext());
        apiRelatedPhotos(photoItem.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        initViews(view);
        return view;
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForColorStateLists"})
    private void initViews(View view) {
        ImageView ivBtnBack = view.findViewById(R.id.iv_btn_back);
        ImageView ivPhoto = view.findViewById(R.id.iv_photo);
        TextView tvBtnSave = view.findViewById(R.id.tv_btn_save);
        ImageView ivProfile = view.findViewById(R.id.iv_profile);
        ImageView iv_like = view.findViewById(R.id.iv_like);
        ImageView iv_btn_like = view.findViewById(R.id.iv_btn_like);
        TextView tvFullName = view.findViewById(R.id.tv_fullName);
        TextView tvFollowers = view.findViewById(R.id.tv_followers);
        TextView tvDescription = view.findViewById(R.id.tv_description);
        String followers = getString(R.string.followers);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        tvRelated = view.findViewById(R.id.tv_related);

        RecyclerView rvDetails = view.findViewById(R.id.rv_details);
        rvDetails.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        rvDetails.addItemDecoration(decoration);
        rvDetails.setAdapter(adapter);

        Object s1 = photoItem.getAlt_description();
        String s2 = photoItem.getDescription();
        String s3 = photoItem.getUser().getName();

        Glide.with(requireContext()).load(photoItem.getUrls().getSmall())
                .placeholder(new ColorDrawable(Color.parseColor(photoItem.getColor()))).into(ivPhoto);

        Glide.with(requireContext()).load(photoItem.getUser().getProfile_image().getLarge())
                .placeholder(new ColorDrawable(Color.parseColor(photoItem.getColor()))).into(ivProfile);

        tvFullName.setText(photoItem.getUser().getName());
        tvFollowers.setText(photoItem.getUser().getTotal_photos() + " " + followers);
        tvDescription.setText(getDescription(s1, s2, s3));

        String itemId = photoItem.getId();

        float savedRating = PrefsManager.getInstance(requireContext()).getFloat(itemId, 0f);
        ratingBar.setRating(savedRating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                PrefsManager.getInstance(requireContext()).putFloat(itemId, rating);
            }
        });

        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        tvBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinRepository.savePhoto(new Pin(0, photoItem));
                tvBtnSave.setBackgroundTintList(requireContext().getResources().getColorStateList(R.color.ic_default_color));
            }
        });

        final boolean[] isLiked = {PrefsManager.getInstance(requireContext()).getBoolean(getLikedKey(photoItem.getId()), false)};

        if (isLiked[0]) {
            iv_btn_like.setImageResource(R.drawable.liked);
        } else {
            iv_btn_like.setImageResource(R.drawable.like);
        }

        iv_btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLiked[0] = !isLiked[0];

                if (isLiked[0]) {
                    iv_btn_like.setImageResource(R.drawable.liked);
                } else {
                    iv_btn_like.setImageResource(R.drawable.like);
                }

                PrefsManager.getInstance(requireContext()).saveBoolean(getLikedKey(photoItem.getId()), isLiked[0]);
            }
        });
        adapter.updateLikedState(String.valueOf(iv_like), isLiked[0]);
    }

    private String getLikedKey(String photoId) {
        return "liked_" + photoId;
    }

    private void apiRelatedPhotos(String id) {
        RetrofitHttp.photoService.getRelatedPhotos(id).enqueue(new Callback<RelatedPhotos>() {
            @Override
            public void onResponse(Call<RelatedPhotos> call, Response<RelatedPhotos> response) {
                RelatedPhotos relatedPhotos = response.body();
                if (relatedPhotos != null) {
                    ArrayList<Photo> photoList = relatedPhotos.getResults();
                    if (photoList.size() > 0) {
                        adapter.addPhotos(photoList);
                    } else {
                        tvRelated.setText(getString(R.string.related_photo_not_found));
                    }
                }
            }

            @Override
            public void onFailure(Call<RelatedPhotos> call, Throwable t) {
                Log.e("@@@", t.getMessage());
            }
        });
    }

    private String getDescription(Object s1, String s2, String s3) {
        if (s1 != null) {
            return s1.toString();
        } else if (s2 != null) {
            return s2;
        } else {
            return "Photo was made by " + s3;
        }
    }
}

