package com.gladkikh.tinkoffsiriusapp.api;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Instance {
    private static final String DOMAIN = "https://developerslife.ru/";
    private static Retrofit.Builder builder;

    public static Retrofit getInstance() {
        if (builder == null) {
            builder = new Retrofit.Builder();

            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

            builder.baseUrl(DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .client(okHttpBuilder.build());

        }
        return builder.build();
    }
}

