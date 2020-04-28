package com.soargtechnologies.myscope.services

import com.soargtechnologies.myscope.ProfileDataClass
import com.soargtechnologies.myscope.activities.SignupResponse
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PrescriptionInterface {

    //     @GET("list")
//    Call<List<PrescriptionManualDataClass>> getData();
    @POST("addRecord")
    fun createPatient(@Body body: SignupResponse?): Call<SignupResponse>?

    @Headers("Content-Type: application/json")
    @POST("updateRecord")
    fun updateSignUppage(@Body updatePatient: SignupResponse): Call<SignupResponse>

//
//    @POST("addpatientprofile")
//    fun createPatientprofile(@Body body: SignupResponse?): Call<SignupResponse>?

    @GET("Patientslist/{mobile_no}")
    fun loginPatient(@Path("mobile_no") mobile_no: String): Call<List<SignupResponse>>

    //    @GET("Patientslist")
//    fun userprofileget(@Path("mobile") mobile:String?): Call<List<SignupResponse>>

    @Headers("Content-Type: application/json")
    @POST("addpatientprofile")
    fun addProfile(@Body newProfileDataClass: ProfileDataClass): Call<ProfileDataClass>


    @Headers("Content-Type:application/json")
    @GET("Patientslist/{mobile_no}")
    fun userprofileget(@Path("mobile_no") mobile_no: String): Call<List<SignupResponse>>

    @Headers("Content-Type:application/json")
    @GET("patientdetails/{mobile_no}")
    fun userprofilegetAllValues(@Path("mobile_no") mobile_no: String): Call<List<ProfileDataClass>>

    @Headers("Content-Type: application/json")
    @POST("updatepatientprofile")
    fun updateUser(@Body newuserProfile: ProfileDataClass): Call<ProfileDataClass>

    //end point of webservice
    @Headers("Content-Type:application/json")
    @GET("doctorlist")
    fun getDoctorList(@QueryMap filter: HashMap<String, String>): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("doctorlist/{mobile_no}")
    fun getDoctorListbymobileno(@Path("mobile_no") mobile_no: String): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("doctorlist/{mobile_no}/{model_name}")
    fun getDoctorListbyId(@Path("mobile_no") mobile_no:String, @Path("model_name") model_name: String?): Call<List<PrescriptionDataClass>>


    @Headers("Content-Type:application/json")
    @GET("doctorlist/add/{prescription_id}")
    fun getDoctorlistbyPID(@Path("prescription_id") prescription_id: Int): Call<List<PrescriptionDataClass>>


    @Headers("Content-Type: application/json")
    @POST("adddoctorlist")
    fun addDoctor(@Body newPrescriptionDoctor: PrescriptionDataClass): Call<PrescriptionDataClass>

    @Headers("Content-Type:application/json")
    @GET("druglist")
    fun getDrugList(@QueryMap filter: HashMap<String, String>): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("druglist/{drug_id}")
    fun getDrugListbyId(@Path("drug_id") drug_id: Int): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("druglist/add/{prescription_id}")
    fun getAllDrugsByPID(@Path("prescription_id") prescription_id: Int): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type: application/json")
    @POST("updatedruglist")
    fun updateDrug(@Body newPrescriptionDoctor: PrescriptionDataClass): Call<PrescriptionDataClass>

    @Headers("Content-Type: application/json")
    @POST("addDruglist")
    fun addDrug(@Body newPrescriptionDrug: PrescriptionDataClass): Call<PrescriptionDataClass>


    @Headers("Content-Type:application/json")
    @GET("Patientsprofile/{mobile_no}")
    fun getAllprofiledata(@Path("mobile_no") mobile_no: String?): Call<List<ProfileDataClass>>


    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("preupload")
    fun uploadImage(@Part file: MultipartBody.Part,
                    @Part("mobile_no") mobile_no: RequestBody,@Part ("upload_saved_on") upload_saved_on:RequestBody):
            Call<PrescriptionDataClass>

    @GET("uploadedlist")
    fun getImageDetails(): Call<List<PrescriptionDataClass>>
}