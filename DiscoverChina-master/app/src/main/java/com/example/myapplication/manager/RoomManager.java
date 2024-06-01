package com.example.myapplication.manager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myapplication.database.PhotoTypeConverter;
import com.example.myapplication.database.PinDao;
import com.example.myapplication.model.Pin;


@Database(entities = {Pin.class}, version = 1)
@TypeConverters(PhotoTypeConverter.class)
public abstract class RoomManager extends RoomDatabase {

    public abstract PinDao pinDao();

    private static RoomManager INSTANCE;

    public static synchronized RoomManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RoomManager.class, "saved_photos_dp")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
