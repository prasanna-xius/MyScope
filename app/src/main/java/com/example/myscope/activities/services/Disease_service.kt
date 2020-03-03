package com.example.myscope.activities.services


import com.example.myscope.activities.medical_history.Diseases
import retrofit2.Call
import retrofit2.http.*

interface Disease_service {

    //end point of webservice
    @Headers("Content-Type:application/json")
    @GET("diseaselist")
    fun getDiseaseList(@QueryMap filter: HashMap<String, String>): Call<List<Diseases>>

    @GET("diseaselist/{id}")
    fun getdisease(@Path("id") id: String): Call<List<Diseases>>


    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addDiseaseRecord")
    fun addDisease(@Body newDisease: Diseases): Call<Diseases>

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
    @Headers("Content-Type: application/json")
    @POST("updateDiseaseRecord")
    fun updateDisease(@Body newDisease: Diseases): Call<Diseases>

//    @FormUrlEncoded
//    @POST("diseaselist/updateDiseaseRecord")
//    fun updateDisease(
//            @Field("mobile_no") mobile_no: String,
//            @Field("known_condition") known_condition: String,
//            @Field("disease_status") disease_status: String,
//            @Field("disease_duration") disease_duration: String,
//            @Field("when_started") when_started: String,
//            @Field("when_ended") when_ended: String,
//            @Field("disease_note") disease_note: String
//    ): Call<Diseases>

//    @DELETE("list/{id}")
//    fun deleteDisease(@Path("id") id: Int): Call<Unit>
}




