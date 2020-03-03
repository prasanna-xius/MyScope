package com.example.myscope.activities.services

import com.example.myscope.activities.medical_history.FamilyCondition
import retrofit2.Call
import retrofit2.http.*

interface FamilyConditionService {

    //end point of webservice
    @Headers("Content-Type:application/json")
    @GET("Familylist")
    fun getFamilyList(@QueryMap filter: HashMap<String, String>): Call<List<FamilyCondition>>

    @GET("familylist/{id}")
    fun getFamilyCondition(@Path("id") id: String): Call<List<FamilyCondition>>


    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addFamily")
    fun addFamilyList(@Body newFamilyCondition: FamilyCondition): Call<FamilyCondition>

    /* @FormUrlEncoded
     @Headers("Content-Type: application/json")
     @POST("addRecords")
     fun addFamilyCondition(
             @Field("familyCondition") familyCondition: String,
             @Field("relationship") relationship: String,
             @Field("relationship_notes") relationship_notes: String: Call<FamilyCondition>*/
    @Headers("Content-Type: application/json")
    @POST("updateFamily")
    fun updateFamily(@Body newFamilyCondition : String): Call<FamilyCondition>

//    @FormUrlEncoded
//    @POST("updateFamilyCondition")
//    fun updateFamilyCondition(
//            @Field("mobile_no") mobile_no: String,
//            @Field("familyCondition") familyCondition: String,
//            @Field("relationship") relationship: String,
//            @Field("relationship_notes") relationship_notes: String
//    ): Call<FamilyCondition>

//    @DELETE("list/{id}")
//    fun deleteFamilyCondition(@Path("id") id: Int): Call<FamilyCondition>

}