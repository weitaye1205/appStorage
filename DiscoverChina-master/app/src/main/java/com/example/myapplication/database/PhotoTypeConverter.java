package com.example.myapplication.database;

import androidx.room.TypeConverter;

import com.example.myapplication.model.Photo;
import com.google.gson.Gson;

public class PhotoTypeConverter {

    @TypeConverter
    public String fromPhotoItem(Photo photoItem) {
        return new Gson().toJson(photoItem);
    }

    @TypeConverter
    public Photo toPhotoItem(String json) {
        return new Gson().fromJson(json, Photo.class);
    }
}

