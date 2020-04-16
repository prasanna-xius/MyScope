package com.soargtechnologies.myscope.models

data class MedicalHistoryModelActivity (


        //Immunization history var
                                        var immuname:String=",",
                                        var immudate:String=",",
                                        var immuevent:String=",",
                                        var immubrand:String=",",
                                        var immunotes:String=",",
                                        var mobile_no:String=",",
                                        var immun_id :Int=0,
                                        var immun_saved_on:String ?=null,
                                        var immun_updated_on:String ?=null,
        //Surgery History var
                                        var surgeryname:String=",",
                                        var surgerydate:String=",",
                                        var surgerylocation:String=",",
                                        var surgeryprovider:String=",",
                                        var surgeryhospital:String=",",
                                        var surgerynotes:String=",",
                                        var surgery_id :Int=0,
                                        var surgery_saved_on:String ?=null,
                                        var surgery_updated_on:String ?=null


)



