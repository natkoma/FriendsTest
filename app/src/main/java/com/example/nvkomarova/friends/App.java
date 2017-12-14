package com.example.nvkomarova.friends;

import android.app.Application;

import com.example.nvkomarova.friends.api.ApiEndpoints;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static ApiEndpoints endpoints;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        endpoints = retrofit.create(ApiEndpoints.class);
    }
    public static ApiEndpoints getEndpoints() {
        return endpoints;
    }
}