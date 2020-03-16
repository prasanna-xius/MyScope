package com.example.myscope.activities.medical_history

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

                    var  adverse_drugname : String? =null,
                    var adverse_reaction : String? =null,
                    var adverse_date_of_start:String? =null,
                    var adverse_treatment_taken:String? =null,


                    var  smoking : String? =null,
                    var smoking_duration : String? =null,
                    var tobacco:String? =null,
                    var alcohol:String? =null,
                    var alcohol_duration:String? =null,

                    var  family_condition : String? =null,
                    var relationship : String? =null,
                    var family_note:String? =null,
                    var family_id:Int?=0,

                    var diet:String?=null

)

