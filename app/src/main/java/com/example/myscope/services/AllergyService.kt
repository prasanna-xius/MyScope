package com.example.myscope.services

import com.example.myscope.models.Allergy
import retrofit2.Call
import retrofit2.http.*

interface AllergyService {

    //end point of webservice
    @GET("list")
    fun getAllergyList(@QueryMap filter: HashMap<String, String>): Call<List<Allergy>>

    @GET("/list/{id}")
    fun getAllergy(@Path("id") id: Int): Call<Allergy>


    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addRecords")
    fun addAllergy(@Body newAllergy: Allergy): Call<Allergy>

    /* @FormUrlEncoded
     @Headers("Content-Type: application/json")
     @POST("addRecords")
     fun addAllergy(
             @Field("name") name: String,
             @Field("reaction") reaction: String,
             @Field("treatment") treatment: String,
             @Field("notes") notes: String,
             @Field("date") date: String,
             @Field("sprdata") sprdata: String): Call<Allergy>*/


    @FormUrlEncoded
    @PUT("list/{id}")
    fun updateAllergy(
            @Path("id") id: Int,
            @Field("name") name: String,
            @Field("reaction") reaction: String,
            @Field("treatment") treatment: String,
            @Field("notes") notes: String,
            @Field("date") date: String

    ): Call<Allergy>

    @DELETE("list/{id}")
    fun deleteAllergy(@Path("id") id: Int): Call<Unit>
}


