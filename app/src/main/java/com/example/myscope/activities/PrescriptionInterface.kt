package com.example.myscope.activities

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PrescriptionInterface {
    //     @GET("list")
//    Call<List<PrescriptionManualDataClass>> getData();
    @POST("addRecord")
    fun createPatient(@Body body: SignupResponse?): Call<List<SignupResponse>?>

    @POST("list/{mobile_no}")
    fun loginPatient(@Body body: String): Call<List<SignupResponse>?>
}