package com.soargtechnologies.myscope.activities.medical_history

import java.sql.RowId
import java.sql.RowIdLifetime


data class Diseases(var  known_condition : String? =null,
                    var disease_status : String? =null,
                    var disease_duration:String? =null,
                    var when_started:String? =null,
                    var when_ended:String? =null,
                    var disease_note:String? =null,
                    var mobile_no:String?= null,
                   var disease_id:Int?=0,
                    var disease_save_on:String ?=null,
                    var disease_updated_on:String ?=null,


//                    var  drugname : String? =null,
//                    var reaction : String? =null,
//                    var date_of_start:String? =null,
//                    var treatment_taken:String? =null,

                    var  smoking : String? =null,
                    var smoking_duration : String? =null,
                    var tobacco:String? =null,
                    var alcohol:String? =null,
                    var alcohol_duration:String? =null,
                    var social_saved_on:String ?=null,
                    var social_updated_on:String ?=null,


                    var  family_condition : String? =null,
                    var relationship : String? =null,
                    var family_note:String? =null,
                    var family_id:Int?=0,
                    var family_save_on:String ?=null,
                    var family_updated_on:String ?=null,


                    var diet:String?=null,
                    var diet_saved_on:String ?=null,
                    var diet_updated_on:String ?=null,


                    var  drugname : String? =null,
                    var reaction : String? =null,
                    var date_of_start:String? =null,
                    var treatment_taken:String?=null,
                    var adverse_id:Int = 0 ,
                    var adverse_save_on:String?=null,
                    var adverse_updated_on:String ?=null,


                    var  patient_counselling : Boolean? =null,
                    var drug_interaction : Boolean? =null,
                    var prescription_audit:Boolean? =null,
                    var adverse_drug_monitering:Boolean?=null,
                    var post_dicharge_package:Boolean?=null,
                    var services_saved_on:String ?=null,
                    var services_updated_on:String ?=null

                    )

