package com.example.myapplication.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SearchAdapter;
import com.example.myapplication.helper.SpacesItemDecoration;
import com.example.myapplication.model.Photo;
import com.example.myapplication.model.Search;
import com.example.myapplication.network.RetrofitHttp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private EditText edt_search;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        edt_search = view.findViewById(R.id.et_search);
        edt_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String keyword = edt_search.getText().toString();
                apiSearchPhoto(keyword);
                return true;
            }
            return false;
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        recyclerView.addItemDecoration(decoration);

        String keyword = edt_search.getText().toString();
        apiSearchPhoto(keyword);
    }

    private void apiSearchPhoto(String query) {
        RetrofitHttp.photoService.searchPhoto(query, 1, 30).enqueue(new Callback<Search>() {
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
        adapter = new SearchAdapter(SearchFragment.this, items);
        recyclerView.setAdapter(adapter);
    }
}
