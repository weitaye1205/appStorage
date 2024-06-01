package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.ProfileFragment;
import com.example.myapplication.fragment.SearchFragment;
import com.example.myapplication.service.BackgroundMusicService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends BaseActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout flFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment homeFragment = new HomeFragment();
        SearchFragment searchFragment = new SearchFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        flFragment = findViewById(R.id.fl_Fragment);

        replaceFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_home) {
                    replaceFragment(homeFragment);
                    return true;
                } else if (itemId == R.id.menu_search) {
                    replaceFragment(searchFragment);
                    return true;
                } else if (itemId == R.id.menu_profile) {
                    replaceFragment(profileFragment);
                    return true;
                }
                return false;
            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.fl_Fragment, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
