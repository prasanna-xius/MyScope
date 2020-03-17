package com.example.myscope.activities.prescription

import retrofit2.http.Multipart

data class PrescriptionDataClass(var is_prescribed: String? = null,
                                 var doctor_name: String? = null,
                                 var hospital_name: String? = null,
                                 var prescription_note: String? = null,
                                 var medical_condition: String? = null,
                                 var mobile_no: String? = null,
                                 var prescription_id: Int? = 0,


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

                                 var uploadfile:Multipart? = null,
                                 var upload_id:Int = 0



)
