package com.example.myapplication.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.manager.LanguageManager;
import com.example.myapplication.service.BackgroundMusicService;

public abstract class BaseActivity extends AppCompatActivity {

    private Intent backgroundMusicIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backgroundMusicIntent = new Intent(this, BackgroundMusicService.class);
    }


    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundMusic();
    }
    @Override
    protected void onStart() {
        super.onStart();
        startBackgroundMusic();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopBackgroundMusic();
    }

    protected void startBackgroundMusic() {
        startService(backgroundMusicIntent);
    }

    protected void stopBackgroundMusic() {
        stopService(backgroundMusicIntent);
    }

    @Override
    public void onBackPressed() {
//        BackgroundMusicService.stopMusic();
        super.onBackPressed();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        LanguageManager.loadLanguage(this);
    }

    public void callHomeActivity(Context context) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
