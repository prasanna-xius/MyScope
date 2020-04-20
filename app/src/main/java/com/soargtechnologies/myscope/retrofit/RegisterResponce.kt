package com.soargtechnologies.myscope.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterResponce(

        @Expose
        @SerializedName("mobile")
        val mobile: Integer,
        @Expose
        @SerializedName("first_name")
        val first_name: String,
        @Expose
        @SerializedName("last_name")
        val last_name: String,
        @Expose
        @SerializedName("mobile_no")
        val mobile_no: Integer,
        @Expose
        @SerializedName("email")
        val email: String)