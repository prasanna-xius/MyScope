package com.soargtechnologies.myscope.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

//    @FormUrlEncoded
//    @POST("addRecord")
//    fun addRecord(
//            @Field("first_name") first_name: String?,
//    @Field("last_name") last_name: String?,
//    @Field("mobile_no") mobile_no: String?,
//    @Field("email") email: String?
//    ): Call<List<RegisterResponce?>>
//}

    @FormUrlEncoded
    @POST("addRecord")
    fun addRecord(
            @Field("first_name", encoded = true) first_name: String?,
            @Field("last_name", encoded = true) last_name: String?,
            @Field("mobile_no", encoded = true) mobile_no: String?,
            @Field("email", encoded = true) email: String?
    ): Call<List<RegisterResponce?>>
}