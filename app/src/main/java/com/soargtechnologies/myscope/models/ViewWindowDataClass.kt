package com.soargtechnologies.myscope.models

data class ViewWindowDataClass (

        var name:String=","
        , var reaction:String=",",
        var treatment:String=",",
        var date:String=",",
       // var allergy_id:Int=0,
        var mobile_no:String?=null,
        var family_note:String ?=null,
       // var allergy_updated_on:String ?=null
        var surgeryname:String?=null,
        var surgerynotes:String?=null,
        var surgerydate:String?=null,

        var formulation:String?=null,
        var medicationname:String?=null,
        var strength:String?=null,
        var doseunit:String?=null,
        var  how_often_taken: String?=null,
        var family_condition:String?=null,
        var relationship:String?=null,
        var diet:String?=null,
        var smoking:String?=null,
        var smoking_duration:String?=null,
        var tobacoo:String?=null,

        var alcohol:String?=null,
        var alcohol_duration:String?=null,
        var drugname:String?=null,
        var date_of_start:String?=null,
        var treatment_taken:String=",",

        var  known_condition : String? =null,
        var disease_status : String? =null,
        var disease_duration:String? =null,
        var when_started:String? =null,
        var when_ended:String? =null,

        var immuname:String=",",
        var immudate:String=",",


        var adverse_reaction : String? =null


        )
