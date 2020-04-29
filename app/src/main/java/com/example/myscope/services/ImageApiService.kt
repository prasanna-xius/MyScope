package com.example.myscope.services

import com.example.myscope.activities.prescription.PrescriptionDataClass
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path


interface ImageApiService {


    @GET("uploadedlist")
    fun getImageDetails(): Call<MutableList<PrescriptionDataClass>>


    @DELETE("deleteupload/{p_uploadid}")
    fun deleteImageDetails(@Path("p_uploadid")p_uploadid: String): Call<MutableList<PrescriptionDataClass>>

}