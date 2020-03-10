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

    @GET("diseaselist/{id}")
    fun getdisease(@Path("id") id: String): Call<List<Diseases>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addDiseaseRecord")
    fun addDisease(@Body newDisease: Diseases): Call<Diseases>

    @Headers("Content-Type: application/json")
    @POST("updateDiseaseRecord")
    fun updateDisease(@Body newDisease: Diseases): Call<Diseases>

    //Adverse_Drug

    @Headers("Content-Type:application/json")
    @GET("Druglist")
    fun getDrugList(@QueryMap filter: HashMap<String, String>): Call<List<Diseases>>

    @GET("druglist/{id}")
    fun getDrug(@Path("id") id: String): Call<List<Diseases>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addDrug")
    fun addDrugList(@Body newDrug: Diseases): Call<Diseases>

    //Social_History

    @Headers("Content-Type:application/json")
    @GET("Sociallist")
    fun getSocialList(@QueryMap filter: HashMap<String, String>): Call<List<Diseases>>

    @GET("Sociallist/{id}")
    fun getSocialHabits(@Path("id") id: String): Call<List<Diseases>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addHabit")
    fun addHabit(@Body newSocialHabits: Diseases): Call<Diseases>

    @Headers("Content-Type: application/json")
    @POST("updateSocialRecord")
    fun updateSocialHabits(@Body newSocial : String): Call<Diseases>

    //Family_History

    @Headers("Content-Type:application/json")
    @GET("Familylist")
    fun getFamilyList(@QueryMap filter: HashMap<String, String>): Call<List<Diseases>>

    @GET("familylist/{id}")
    fun getFamily(@Path("id") id: String): Call<List<Diseases>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("/addFamily")
    fun addFamilyList(@Body newFamilyCondition: Diseases): Call<Diseases>

    @Headers("Content-Type: application/json")
    @POST("updateFamily")
    fun updateFamily(@Body newFamilyCondition: Diseases): Call<Diseases>

    //Diet

    @Headers("Content-Type: application/json")
    @POST("adddiet")
    fun addDietList(@Body newDisease: Diseases): Call<Diseases>

}




