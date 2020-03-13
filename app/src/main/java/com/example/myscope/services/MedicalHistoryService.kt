package com.example.myscope.services

import com.example.myscope.models.MedicalHistoryModelActivity
import retrofit2.Call
import retrofit2.http.*

interface MedicalHistoryService {

    //end point of webservice//recyclerview function
    @GET("listallergy")
    fun getAllergyList(@QueryMap filter: HashMap<String, String>): Call<List<MedicalHistoryModelActivity>>

    //end point of webservice///create item function//allergies
    @Headers("Content-Type: application/json")
    @POST("addallergyRecords")
    fun addAllergy(@Body newMedicalHistoryModelActivity: MedicalHistoryModelActivity): Call<MedicalHistoryModelActivity>

    ///get item by id
    @GET("listallergyid/{mobile_no}")
    fun getAllergyByid(@Path("mobile_no") mobile_no: String): Call<List<MedicalHistoryModelActivity>>

    //update item
    @Headers("Content-Type: application/json")
    @POST("allergyupdate")
    fun updateAllergy(@Body newAllergy: MedicalHistoryModelActivity): Call<MedicalHistoryModelActivity>

    ///Immunization history

    @GET("listimmun")
    fun getImmunizationList(@QueryMap filter: HashMap<String, String>): Call<List<MedicalHistoryModelActivity>>


    @GET("listimmuid/{mobile_no}")
    fun getImmunization(@Path("mobile_no") mobile_no: String): Call<List<MedicalHistoryModelActivity>>


    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addimmunRecords")
    fun addImmunization(@Body newMedicalHistoryModelActivity: MedicalHistoryModelActivity): Call<MedicalHistoryModelActivity>

    @Headers("Content-Type: application/json")
    @PUT("immuneupdate")
    fun updateImmunization(@Body newImmunization: MedicalHistoryModelActivity): Call<MedicalHistoryModelActivity>

    ///Surgery History


    @GET("listsurgery")
    fun getSurgeryList(@QueryMap filter: HashMap<String, String>): Call<List<MedicalHistoryModelActivity>>


    @GET("listsurgeryid/{mobile_no}")
    fun getSurgeryByid(@Path("mobile_no") mobile_no: String): Call<List<MedicalHistoryModelActivity>>


    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addsurgeryRecords")
    fun addSurgery(@Body newMedicalHistoryModelActivity: MedicalHistoryModelActivity): Call<MedicalHistoryModelActivity>

    @Headers("Content-Type: application/json")
    @POST("surgeryupdate")
    fun updateSurgery(@Body newsurgery: MedicalHistoryModelActivity): Call<MedicalHistoryModelActivity>


    //Medication History


    @GET("listmedication")
    fun getMedicationList(@QueryMap filter: HashMap<String, String>): Call<List<MedicalHistoryModelActivity>>


    @GET("listmedicationid/{mobile_no}")
    fun getMedicationByid(@Path("mobile_no") mobile_no: String): Call<List<MedicalHistoryModelActivity>>


    //end point of webservice
    @Headers("Content-Type: application/json")
    @POST("addmedicationRecords")
    fun addMedication(@Body newMedicalHistoryModelActivity: MedicalHistoryModelActivity): Call<MedicalHistoryModelActivity>

    @Headers("Content-Type: application/json")
    @POST("medicationupdate")
    fun updateMedication(@Body newmedication: MedicalHistoryModelActivity): Call<MedicalHistoryModelActivity>



}



