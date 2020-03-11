package com.example.myscope.activities

import com.example.myscope.activities.prescription.Prescription_AddDoctor
import retrofit2.Call
import retrofit2.http.*

interface PrescriptionInterface {

    //     @GET("list")
//    Call<List<PrescriptionManualDataClass>> getData();
    @POST("addRecord")
    fun createPatient(@Body body: SignupResponse?): Call<SignupResponse>?

    @POST("list/{mobile_no}")
    fun loginPatient(@Body body: String): Call<SignupResponse>?

    //end point of webservice
    @Headers("Content-Type:application/json")
    @GET("doctorlist")
    fun getDoctorList(@QueryMap filter: HashMap<String, String>): Call<List<Prescription_AddDoctor>>

    @Headers("Content-Type:application/json")
    @GET("doctorlist/{mobile_no}")
    fun getDoctorListbyId(@Path("mobile_no") mobile_no:String): Call<List<Prescription_AddDoctor>>


    @Headers("Content-Type: application/json")
    @POST("adddoctorlist")
    fun addDoctor(@Body newPrescriptionDoctor: Prescription_AddDoctor): Call<Prescription_AddDoctor>

    @Headers("Content-Type:application/json")
    @GET("druglist")
    fun getDrugList(@QueryMap filter: HashMap<String, String>): Call<List<Prescription_AddDoctor>>

    @Headers("Content-Type: application/json")
    @POST("addDruglist")
    fun addDrug(@Body newPrescriptionDrug: Prescription_AddDoctor): Call<Prescription_AddDoctor>
}