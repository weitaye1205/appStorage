package com.example.myapplication.manager;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {
    public static void loadLanguage(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = configuration.getLocales().get(0);
        String language = locale.getLanguage();

        if (language.equals("zh")) {
            configuration.setLocale(Locale.SIMPLIFIED_CHINESE);
        } else {
            configuration.setLocale(Locale.ENGLISH);
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}