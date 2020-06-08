package com.soargtechnologies.myscope.services


import com.soargtechnologies.myscope.models.ViewWindowDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViewWindowInterface {

    ///get item by id
    @GET("listallergyid/{mobile_no}")
    fun getViewWindowDetailsByid(@Path("mobile_no") mobile_no: String): Call<List<ViewWindowDataClass>>
}