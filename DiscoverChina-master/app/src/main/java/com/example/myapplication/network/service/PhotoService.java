package com.example.myapplication.network.service;

import android.provider.Contacts;

import com.example.myapplication.model.Photo;
import com.example.myapplication.model.RelatedPhotos;
import com.example.myapplication.model.ResultProfiles;
import com.example.myapplication.model.Search;
import com.example.myapplication.model.Topic;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PhotoService {

    String ACCESS_KEY = "tJazrzFH2q4OV1hKlvfFYvCxzegtvjhUkRhNx6E6ekY";

//    @Headers("Authorization: Client-ID " + ACCESS_KEY)
//    @GET("photos")
//    Call<ArrayList<Photo>> getPhotos(@Query("page") int page, @Query("per_page") int per_page);

    @Headers("Authorization: Client-ID " + ACCESS_KEY)
    @GET("photos/{id}/related")
    Call<RelatedPhotos> getRelatedPhotos(@Path("id") String id);

    @Headers("Authorization: Client-ID " + ACCESS_KEY)
    @GET("search/photos")
    Call<Search> searchPhoto(@Query("query") String query, @Query("page") int page, @Query("per_page") int per_page);


}
