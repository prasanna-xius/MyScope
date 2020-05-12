package com.example.myscope.activities.prescription

import okhttp3.MultipartBody
import retrofit2.http.Multipart

data class PrescriptionDataClass(var is_prescribed: String? = null,
                                 var doctor_name: String? = null,
                                 var hospital_name: String? = null,
                                 var prescription_note: String? = null,
                                 var medical_condition: String? = null,
                                 var mobile_no: String? = null,
                                 var prescription_id: Int? = 0,
                                 var model_name :String ?=null,


                                 var formulation: String? = null,
                                 var drug_name: String? = null,
                                 var brand_name: String? = null,
                                 var dose_strength: String? = null,
                                 var dose_unit: String? = null,
                                 var time: String? = null,
                                 var how_often_taken: String? = null,
                                 var start_date: String? = null,
                                 var stop_date: String? = null,
                                 var drug_id: Int =0,

                                 var p_upload:MultipartBody.Part?=null,
                                 var downloadfile:String? = null,
                                 var p_uploadid:Int = 0,
                                 var upload_saved_on:String ?= null,
                                 var upload_type:String ?= null,
var upload_title:String?=null




)
