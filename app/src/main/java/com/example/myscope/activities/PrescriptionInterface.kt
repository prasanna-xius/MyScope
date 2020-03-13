package com.example.myscope.activities

import com.example.myscope.activities.prescription.PrescriptionDataClass
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
    fun getDoctorList(@QueryMap filter: HashMap<String, String>): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("doctorlist/{mobile_no}")
    fun getDoctorListbyId(@Path("mobile_no") mobile_no:String): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("doctorlist/add/{prescription_id}")
    fun getDoctorlistbyPID(@Path("prescription_id") prescription_id:Int):Call<List<PrescriptionDataClass>>


    @Headers("Content-Type: application/json")
    @POST("adddoctorlist")
    fun addDoctor(@Body newPrescriptionDoctor: PrescriptionDataClass): Call<PrescriptionDataClass>

    @Headers("Content-Type:application/json")
    @GET("druglist")
    fun getDrugList(@QueryMap filter: HashMap<String, String>): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("druglist/{drug_id}")
    fun getDrugListbyId(@Path("drug_id") drug_id:Int): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("druglist/add/{prescription_id}")
    fun getAllDrugsByPID(@Path("prescription_id") prescription_id:Int): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type: application/json")
    @POST("updatedruglist")
    fun updateDrug(@Body newPrescriptionDoctor: PrescriptionDataClass): Call<PrescriptionDataClass>

    @Headers("Content-Type: application/json")
    @POST("addDruglist")
    fun addDrug(@Body newPrescriptionDrug: PrescriptionDataClass): Call<PrescriptionDataClass>
}