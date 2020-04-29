package com.example.myscope.services

import com.example.myscope.activities.prescription.PrescriptionDataClass
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface ImageApiService {


    @GET("uploadedlist")
    fun getImageDetails(): Call<MutableList<PrescriptionDataClass>>


    @HTTP(method = "DELETE", path = "deleteupload", hasBody = true)
    fun deleteImageDetails(@Body newPrescriptionDrug: PrescriptionDataClass): Call<PrescriptionDataClass>

}