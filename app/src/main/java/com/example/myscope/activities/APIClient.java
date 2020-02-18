package com.example.myscope.activities;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
//   static final String BASE_URL = "http://10.0.2.2:8484/common/myscope/";
    static final String BASE_URL = "http://10.10.19.49:8484/common/myscope/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.d("connect","API connected");
        }
        Log.d("connect","return API not connected");
        return retrofit;
    }
}
