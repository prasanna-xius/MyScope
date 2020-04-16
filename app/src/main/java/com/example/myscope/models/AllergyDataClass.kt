package com.soargtechnologies.myscope.models

data class AllergyDataClass (

        var name:String=","
        , var reaction:String=",",
        var treatment:String=",",
        var notes:String=",",
        var date:String=",",
        var spnrdata:String=",",
        var allergy_id:Int=0,
        var mobile_no:String=",",
        var allergy_saved_on:String ?=null,
        var allergy_updated_on:String ?=null
)
