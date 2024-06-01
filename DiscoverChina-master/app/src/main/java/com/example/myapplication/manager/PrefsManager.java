package com.example.myapplication.manager;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefsManager {

    private final SharedPreferences sharedPreferences;

    public PrefsManager(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    public static final String KEY_ARRAY_LIST = "arrayList";
    public static final String KEY_PHOTO_LIST = "photoList";
    public static final String KEY_RATING_PREFIX = "rating_";

    private SharedPreferences.Editor editor;


    private static PrefsManager prefsManager;

    public static PrefsManager getInstance(Context context) {
        if (prefsManager == null) {
            prefsManager = new PrefsManager(context);
        }
        return prefsManager;
    }

    public <T> void saveArrayList(String key, ArrayList<T> value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        String json = new Gson().toJson(value);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }

    public <T> ArrayList<T> getArrayList(String key, Type type) {
        String json = sharedPreferences.getString(key, null);
        if (json != null) {
            return new Gson().fromJson(json, type);
        } else {
            return new ArrayList<>();
        }
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void putFloat(String itemId, float value) {
        String key = KEY_RATING_PREFIX + itemId;
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putFloat(key, value);
        prefsEditor.apply();
    }

    public float getFloat(String itemId, float defaultValue) {
        String key = KEY_RATING_PREFIX + itemId;
        return sharedPreferences.getFloat(key, defaultValue);
    }


    public void removeData(String key) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.remove(key);
        prefsEditor.apply();
    }

}
