package com.example.tinkoffsiriusapp.api;

import com.example.tinkoffsiriusapp.models.Gif;
import com.example.tinkoffsiriusapp.models.Gifs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Api {

    @GET("/random?json=true")
    Call<Gif> getRandomGif();

    @GET("/latest/{page}?json=true")
    Call<Gifs> getLatestGifs(
            @Path("page") int page,
            @Query("pageSize") int pageSize,
            @Query("types") String types);

    @GET("/top/{page}?json=true")
    Call<Gifs> getTopGifs(
            @Path("page") int page,
            @Query("pageSize") int pageSize,
            @Query("types") String types);
}

