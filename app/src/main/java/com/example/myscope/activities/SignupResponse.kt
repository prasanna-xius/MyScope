package com.example.myscope.activities

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class SignupResponse (
    var first_name: String? = null,
    var last_name: String? = null,
    var mobile_no: String? = null,
    var email: String? = null
//    val module_name: String = "register"
//    var loginresponse: List<SignupResponse>? = null

)

data class LoginResponse (
        var response: String,
        var loginresponse: List<DataResponse>? = null)

         class DataResponse (
          val first_name: String? = null,
          val last_name: String? = null,
          val mobile_no: String? = null,
          val email: String? = null
)
