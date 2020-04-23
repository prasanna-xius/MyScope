package com.soargtechnologies.myscope.activities.services

import com.soargtechnologies.myscope.activities.medical_history.Diseases
import com.soargtechnologies.myscope.activities.self_monitering.Self_dataClass
import retrofit2.Call
import retrofit2.http.*

interface Self_monitoring_service {

    //Blood Glucose

    @GET("glucoselist/{id}")
    fun getGlucose(@Path("id") id: String): Call<List<Self_dataClass>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addglucose")
    fun addGlucose(@Body newBloodGlucose: Self_dataClass): Call<Self_dataClass>

    @Headers("Content-Type: application/json")
    @POST("updateglucose")
    fun updateglucose(@Body newBloodGlucose: Self_dataClass): Call<Self_dataClass>

    //Blood Pressure

    @GET("pressurelist/{id}")
    fun getPressure(@Path("id") id: String): Call<List<Self_dataClass>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addpressure")
    fun addPressure(@Body newBloodGlucose: Self_dataClass): Call<Self_dataClass>

    @Headers("Content-Type: application/json")
    @POST("updatepressure")
    fun updatePressure(@Body newBloodGlucose: Self_dataClass): Call<Self_dataClass>

    //Cholesterol

    @GET("cholestrollist/{id}")
    fun getCholestrol(@Path("id") id: String): Call<List<Self_dataClass>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addcholestrol")
    fun addCholestrol(@Body newBloodGlucose: Self_dataClass): Call<Self_dataClass>

    @Headers("Content-Type: application/json")
    @POST("updatecholestrol")
    fun updatCholestrol(@Body newBloodGlucose: Self_dataClass): Call<Self_dataClass>

    //Bmi

    @GET("bmilist/{id}")
    fun getBmi(@Path("id") id: String): Call<List<Self_dataClass>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addbmi")
    fun addBmi(@Body newBmi: Self_dataClass): Call<Self_dataClass>

    @Headers("Content-Type: application/json")
    @POST("updatebmi")
    fun updatBmi(@Body newBmi: Self_dataClass): Call<Self_dataClass>

    //Emotional State

    @GET("emotionallist/{id}")
    fun getEmotional(@Path("id") id: String): Call<List<Self_dataClass>>

    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addemotional")
    fun addEmotional(@Body newEmotionalStatus: Self_dataClass): Call<Self_dataClass>

    @Headers("Content-Type: application/json")
    @POST("updateemotional")
    fun updateEmotional(@Body newEmotionalStatus: Self_dataClass): Call<Self_dataClass>
}