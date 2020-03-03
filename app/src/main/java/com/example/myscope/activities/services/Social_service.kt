package com.example.myscope.activities.services

import com.example.myscope.activities.medical_history.SocialHabits
import retrofit2.Call
import retrofit2.http.*

interface Social_service {

    //end point of webservice
    @Headers("Content-Type:application/json")
    @GET("Sociallist")
    fun getSocialList(@QueryMap filter: HashMap<String, String>): Call<List<SocialHabits>>

    @GET("Sociallist/{id}")
    fun getSocialHabits(@Path("id") id: String): Call<List<SocialHabits>>


    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addSocialRecord")
    fun addSocialHabits(@Body newSocialHabits: SocialHabits): Call<SocialHabits>

    /* @FormUrlEncoded
     @Headers("Content-Type: application/json")
     @POST("addRecords")
     fun addSocialHabits(
             @Field("name") name: String,
             @Field("reaction") reaction: String,
             @Field("treatment") treatment: String,
             @Field("notes") notes: String,
             @Field("date") date: String,
             @Field("sprdata") sprdata: String): Call<Allergy>*/
    @Headers("Content-Type: application/json")
    @POST("updateSocialRecord")
    fun updateSocialHabits(@Body newSocial : String): Call<SocialHabits>

//    @FormUrlEncoded
//    @POST("updateSocialRecord")
//    fun updateSocialHabits(
//            @Field("mobile_no") mobile_no: String,
//            @Field("known_condition") known_condition: String,
//            @Field("disease_status") disease_status: String,
//            @Field("disease_duration") disease_duration: String,
//            @Field("when_started") when_started: String,
//            @Field("when_ended") when_ended: String,
//            @Field("disease_note") disease_note: String
//    ): Call<SocialHabits>

//    @DELETE("list/{id}")
//    fun deleteSocial(@Path("id") id: Int): Call<SocialHabits>

}