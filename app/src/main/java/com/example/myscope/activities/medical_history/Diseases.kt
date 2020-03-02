package com.example.myscope.activities.medical_history


data class Diseases(var  known_condition : String? =null,
                    var disease_status : String? =null,
                    var disease_duration:String? =null,
                    var when_started:String? =null,
                    var when_ended:String? =null,
                    var disease_note:String? =null,
                    var mobile_no:String?= null)
{
}