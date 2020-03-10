package com.example.myscope.helpers

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private  val URL = "http://10.0.2.2:8181/common/myscope/";

    // Create Logger
    /* private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

     // Create a Custom Interceptor to apply Headers application wide
     val headerInterceptor = object: Interceptor {

         override fun intercept(chain: Interceptor.Chain): Response {

             var request = chain.request()

             request = request.newBuilder()
                     .addHeader("x-device-type", Build.DEVICE)
                     .addHeader("Accept-Language", Locale.getDefault().language)
                     .build()

             val response = chain.proceed(request)
             return response
         }
     }*/

    var gson = GsonBuilder()
            .setLenient()
            .create()

    // Create OkHttp Client
    private val okHttp = OkHttpClient.Builder()
    //.callTimeout(5, TimeUnit.SECONDS)
    //.addInterceptor(headerInterceptor)
    //.addInterceptor(logger)

    // Create Retrofit Builder
    private val builder = Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttp.build())

    // Create Retrofit Instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}


