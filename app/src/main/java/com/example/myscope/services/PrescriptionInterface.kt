package com.example.myscope.services

import com.example.myscope.ProfileDataClass
import com.example.myscope.activities.SignupResponse
import com.example.myscope.activities.prescription.PrescriptionDataClass
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PrescriptionInterface {

    //     @GET("list")
//    Call<List<PrescriptionManualDataClass>> getData();
    @POST("addRecord")
    fun createPatient(@Body body: SignupResponse?): Call<SignupResponse>?
//
//    @POST("addpatientprofile")
//    fun createPatientprofile(@Body body: SignupResponse?): Call<SignupResponse>?

    @GET("Patientslist/{mobile_no}")
    fun loginPatient(@Path("mobile_no") mobile_no:String): Call<List<SignupResponse>>

//    @GET("Patientslist")
//    fun userprofileget(@Path("mobile") mobile:String?): Call<List<SignupResponse>>


    @Headers("Content-Type:application/json")
    @GET("Patientslist/{mobile_no}")
    fun userprofileget(@Path("mobile_no") mobile_no:String): Call<List<SignupResponse>>


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

    @Headers("Content-Type: application/json")
    @POST("addpatientprofile")
    fun addProfile(@Body newProfileDataClass: ProfileDataClass): Call<ProfileDataClass>

    @Headers("Content-Type:application/json")
    @GET("Patientsprofile/{mobile_no}")
    fun getAllprofiledata(@Path("mobile_no") mobile_no:String?): Call<List<ProfileDataClass>>


    @Multipart
    @POST("preupload")
    fun uploadImage(
            @Part file: MultipartBody.Part?, @Part("filename") name: RequestBody?
    ): Call<String>


}