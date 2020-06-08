package com.soargtechnologies.myscope.models

data class ViewWindowDataClass (

        var name:String=","
        , var reaction:String=",",
        var treatment:String=",",
        var date:String=",",
       // var allergy_id:Int=0,
        var mobile_no:String=",",
        var family_note:String ?=null,
       // var allergy_updated_on:String ?=null
        var surgeryname:String?=null,
        var surgerynotes:String?=null,
        var surgerydate:String?=null,

        var family_condition:String?=null,
        var realtionship:String?=null,
        var diet:String?=null,
        var smoking:String?=null,
        var smoking_duration:String?=null,
        var tabacoo:String?=null,

        var alcohal:String?=null,
        var alcohal_duration:String?=null,
        var drugname:String?=null,
        var date_of_start:String?=null,
        var treatment_taken:String=","



        )
