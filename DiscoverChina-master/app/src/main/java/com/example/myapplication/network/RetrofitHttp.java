package com.example.myapplication.network;

import com.example.myapplication.network.service.PhotoService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHttp {
    private static final boolean IS_TESTER = true;
    private static final String SERVER_DEVELOPMENT = "https://api.unsplash.com/";
    private static final String SERVER_PRODUCTION = "https://api.unsplash.com/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static String server() {
        if (IS_TESTER) {
            return SERVER_DEVELOPMENT;
        }
        return SERVER_PRODUCTION;
    }
    public static PhotoService photoService = retrofit.create(PhotoService.class);

}

