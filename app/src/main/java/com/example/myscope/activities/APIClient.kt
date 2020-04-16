package com.soargtechnologies.myscope.activities

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    //   static final String BASE_URL = "http://10.0.2.2:8484/common/myscope/";
    const val BASE_URL = "http://10.10.19.49:8484/common/myscope/"
    private var retrofit: Retrofit? = null
    val getClient: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                Log.d("connect", "API connected")
            }
            Log.d("connect", "return API not connected")
            return retrofit
        }
}