package com.example.myapplication.activity;

import android.app.Activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DetailsPagerAdapter;
import com.example.myapplication.fragment.DetailsFragment;
import com.example.myapplication.model.Photo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DetailsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTransparentStatusBar();
        initViews();
    }

    private void initViews() {
        ViewPager vpDetails = findViewById(R.id.vp_details);
        refreshAdapter(vpDetails, getList(), getPosition());
    }

    private void refreshAdapter(ViewPager viewPager, ArrayList<Photo> photoList, int position) {
        DetailsPagerAdapter adapter = new DetailsPagerAdapter(getSupportFragmentManager());
        for (Photo photoItem : photoList) {
            adapter.addFragment(new DetailsFragment(photoItem));
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }

    private ArrayList<Photo> getList() {
        String json = getIntent().getStringExtra("photoList");
        Type type = new TypeToken<ArrayList<Photo>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    private int getPosition() {
        return getIntent().getIntExtra("position", 0);
    }

    private void setTransparentStatusBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
}

