package com.soargtechnologies.myscope.models

data class MedicationDataClass (
        var medicationname:String=",",
        var startdate:String=",",
        var enddate:String=",",
        var formulation:String=",",
        var doseunit:String=",",
        var reason:String=",",
        var medicationnotes:String=",",
        var medication_id :Int=0,
        var how_often_taken:String=",",
        var isprescribed:String=",",
        var strength:String=",",
        var mobile_no:String=",",
        var medication_saved_on:String ?=null,
        var medication_updated_on:String ?=null
)
