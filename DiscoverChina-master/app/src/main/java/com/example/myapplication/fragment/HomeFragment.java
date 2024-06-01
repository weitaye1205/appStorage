package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.HomeAdapter;
import com.example.myapplication.helper.SpacesItemDecoration;
import com.example.myapplication.model.Photo;
import com.example.myapplication.model.Search;
import com.example.myapplication.network.RetrofitHttp;
import com.example.myapplication.service.BackgroundMusicService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeAdapter adapter;

    private TextView tv_cities;
    private TextView tv_cuisine;
    private TextView tv_festivals;
    private TextView tv_kungfu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);


        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerPins);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        recyclerView.addItemDecoration(decoration);

        tv_cities = view.findViewById(R.id.tv_cities);
        tv_cuisine = view.findViewById(R.id.tv_cuisine);
        tv_festivals = view.findViewById(R.id.tv_festivals);
        tv_kungfu = view.findViewById(R.id.tv_kungfu);

        apiGetChinese("Chinese Cities");

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v == tv_cities) {
                    apiGetChinese("Chinese Cities");
                } else if (v == tv_cuisine) {
                    apiGetChinese("Chinese Food");
                } else if (v == tv_festivals) {
                    apiGetChinese("Chinese Carnivals");
                } else if (v == tv_kungfu) {
                    apiGetChinese("Chinese Kungfu");
                }
            }
        };

        tv_cities.setOnClickListener(onClickListener);
        tv_cuisine.setOnClickListener(onClickListener);
        tv_festivals.setOnClickListener(onClickListener);
        tv_kungfu.setOnClickListener(onClickListener);


    }

    private void apiGetChinese(String query) {
        RetrofitHttp.photoService.searchPhoto(query, 1, 40).enqueue(new Callback<Search>() {
            @Override
            public void onResponse(@NonNull Call<Search> call, @NonNull Response<Search> response) {
                Search body = response.body();
                if (body != null) {
                    refreshAdapter((ArrayList<Photo>) body.getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Search> call, @NonNull Throwable t) {
                Log.d("@@@", "Search -> " + t.getLocalizedMessage());
            }
        });
    }


    private void refreshAdapter(ArrayList<Photo> items) {
        adapter = new HomeAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }
}
