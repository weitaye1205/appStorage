package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProfileAdapter;
import com.example.myapplication.database.PinRepository;
import com.example.myapplication.helper.SpacesItemDecoration;
import com.example.myapplication.model.Photo;
import com.example.myapplication.model.Pin;
import com.example.myapplication.service.BackgroundMusicService;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private RecyclerView rvSavedPhotos;
    private ProfileAdapter photosAdapter;
    private PinRepository pinRepository;
    private TextView tvNoSavedPins;
    private ImageView iv_mute;
    private boolean isMusicPlaying;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshAdapter();
    }

    private void initViews(View view) {
        rvSavedPhotos = view.findViewById(R.id.recyclerPins);
        tvNoSavedPins = view.findViewById(R.id.tv_no_saved_pins);
        iv_mute = view.findViewById(R.id.iv_mute);

        rvSavedPhotos.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        rvSavedPhotos.addItemDecoration(decoration);
        pinRepository = new PinRepository(requireActivity().getApplication());
        photosAdapter = new ProfileAdapter(requireContext());
        rvSavedPhotos.setAdapter(photosAdapter);

        tvNoSavedPins.setVisibility(View.GONE);
        checkSavedPins();

        isMusicPlaying = BackgroundMusicService.isMusicPlaying();
        updateMuteButtonState();

        iv_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBackgroundMusic();
            }
        });
    }

    private void toggleBackgroundMusic() {
        if (isMusicPlaying) {
            BackgroundMusicService.stopMusic();
            isMusicPlaying = false;
        } else {
            BackgroundMusicService.startMusic(getContext());
            isMusicPlaying = true;
        }
        updateMuteButtonState();
    }

    private void updateMuteButtonState() {
        if (isMusicPlaying) {
            iv_mute.setImageResource(R.drawable.volume);
        } else {
            iv_mute.setImageResource(R.drawable.mute);
        }
    }
    private void refreshAdapter() {
        ArrayList<Photo> photoList = new ArrayList<>();
        for (Pin item : pinRepository.getAllSavedPhotos()) {
            if (item.getPhotoItem() != null) {
                photoList.add(item.getPhotoItem());
            }
        }
        photosAdapter.addNewPhotos(photoList);
        checkSavedPins();
    }

    private void checkSavedPins() {
        List<Pin> savedPins = pinRepository.getAllSavedPhotos();
        if (savedPins.isEmpty()) {
            tvNoSavedPins.setVisibility(View.VISIBLE);
        } else {
            tvNoSavedPins.setVisibility(View.GONE);
        }
    }
}
