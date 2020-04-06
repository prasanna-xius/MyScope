package com.example.myscope.activities.services


import com.example.myscope.activities.medical_history.Diseases
import retrofit2.Call
import retrofit2.http.*

interface Disease_service {

    //Disease_History
    //end point of webservice
    @Headers("Content-Type:application/json")
    @GET("diseaselist")
    fun getDiseaseList(@QueryMap filter: HashMap<String, String>): Call<List<Diseases>>

    @GET("diseaselist/{mobile_no}")
    fun getdisease(@Path("mobile_no") mobile_no: String): Call<List<Diseases>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addDiseaseRecord")
    fun addDisease(@Body newDisease: Diseases): Call<Diseases>

    @Headers("Content-Type: application/json")
    @POST("updateDiseaseRecord")
    fun updateDisease(@Body newDisease: Diseases): Call<Diseases>


    //Social_History

    @Headers("Content-Type:application/json")
    @GET("Sociallist")
    fun getSocialList(@QueryMap filter: HashMap<String, String>): Call<List<Diseases>>

    @GET("habitlist/{id}")
    fun getSocialHabits(@Path("id") id: String): Call<List<Diseases>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addHabit")
    fun addHabit(@Body newSocialHabits: Diseases): Call<Diseases>

    @Headers("Content-Type: application/json")
    @POST("updateHabit")
    fun updateHabit(@Body newSocialHabits: Diseases): Call<Diseases>

    //Family_History

    @Headers("Content-Type:application/json")
    @GET("familylist")
    fun getFamilyList(@QueryMap filter: HashMap<String, String>): Call<List<Diseases>>

    @GET("familylist/{mobile_no}")
    fun getFamily(@Path("mobile_no") mobile_no: String): Call<List<Diseases>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addFamily")
    fun addFamilyList(@Body newFamilyCondition: Diseases): Call<Diseases>

    @Headers("Content-Type: application/json")
    @POST("updateFamily")
    fun updateFamily(@Body newFamilyCondition: Diseases): Call<Diseases>

    //Diet

    @Headers("Content-Type: application/json")
    @POST("adddiet")
    fun addDietList(@Body newDiet: Diseases): Call<Diseases>

    @GET("dietlist/{id}")
    fun getDiet(@Path("id") id: String): Call<List<Diseases>>

    @Headers("Content-Type: application/json")
    @POST("updateDiet")
    fun updateDiet(@Body newDiet: Diseases): Call<Diseases>

//

    //Adverse_Drug

    @GET("adverselist/{mobile_no}")
    fun getDrug(@Path("mobile_no") mobile_no: String): Call<List<Diseases>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addDrug")
    fun addDrug(@Body newDrug: Diseases): Call<Diseases>

    @Headers("Content-Type: application/json")
    @POST("updateadversedrug")
    fun updateadversedrug(@Body newDrug: Diseases): Call<Diseases>


    //Services

    @GET("servicelist/{id}")
    fun getService(@Path("id") id: String): Call<List<Diseases>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addservices")
    fun addServicesList(@Body newDrug: Diseases): Call<Diseases>

    @Headers("Content-Type: application/json")
    @POST("updateservices")
    fun updateService(@Body newDrug: Diseases): Call<Diseases>

}




