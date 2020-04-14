package com.example.myscope.activities

import com.example.myscope.activities.APIClient.BASE_URL
import com.example.myscope.activities.prescription.PrescriptionDataClass

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface PrescriptionInterface {

    //     @GET("list")
//    Call<List<PrescriptionManualDataClass>> getData();
    @POST("addRecord")
    fun createPatient(@Body body: SignupResponse?): Call<SignupResponse>?

    @GET("Patientslist/{mobile_no}")
    fun loginPatient(@Path("mobile_no") mobile_no: String): Call<List<SignupResponse>>

    //end point of webservice
    @Headers("Content-Type:application/json")
    @GET("doctorlist")
    fun getDoctorList(@QueryMap filter: HashMap<String, String>): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("doctorlist/{mobile_no}/{model_name}")
    fun getDoctorListbyId(@Path("mobile_no") mobile_no: String, @Path("model_name") model_name: String): Call<List<PrescriptionDataClass>>

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

    @Multipart
    @POST("preupload")
    fun uploadImage(@Part file: MultipartBody.Part,
                    @Part("mobile_no") mobile_no: RequestBody):
            Call<PrescriptionDataClass>

    ///*@Multipart
    //@GET("uploadedlisttttt")
   // fun getImage(@Body newPrescriptionDrug: PrescriptionDataClass):
           // Call<List<PrescriptionDataClass>>*/

    /*@GET("uploadedlist")
    fun getImageDetails(): Call<List<PrescriptionDataClass>>

    companion object {
        operator fun invoke(): PrescriptionInterface {
            val URL="http://10.0.2.2:8484/common/myscope/"
            return Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PrescriptionInterface::class.java)
        }*/

    }
