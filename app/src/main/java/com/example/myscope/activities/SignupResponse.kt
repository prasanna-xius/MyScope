package com.example.myscope.activities

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class SignupResponse (
    var first_name: String? = null,
    var last_name: String? = null,
    var mobile_no: String? = null,
    var email: String? = null


)

