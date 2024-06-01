package com.example.myapplication.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;
import com.example.myapplication.activity.BaseActivity;

public class BackgroundMusicService extends Service {
    private MediaPlayer mediaPlayer;
    private static boolean isMusicPlaying = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        mediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        isMusicPlaying = true;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        isMusicPlaying = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static boolean isMusicPlaying() {
        return isMusicPlaying;
    }

    public static void stopMusic() {
        isMusicPlaying = false;
    }

    public static void startMusic(Context context) {
        Intent intent = new Intent(context, BackgroundMusicService.class);
        context.startService(intent);
        isMusicPlaying = true;
    }
}
