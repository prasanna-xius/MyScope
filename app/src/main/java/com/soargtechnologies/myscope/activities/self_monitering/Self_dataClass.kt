package com.soargtechnologies.myscope.activities.self_monitering

data class Self_dataClass (

                           // blood glucose
                           var  date_of_test : String? =null,
                           var test : String? =null,
                           var test_result:String? =null,
                           var glucose_notes:String? =null,
                           var glucose_id:Int? =null,
                           var glucose_save_on:String? =null,
                           var glucose_update_on:String? =null,
                           var mobile_no:String?= null,
                        //   var glucose_id:Int?=0

                           //blood pressure
                           var  date_of_pressure : String? =null,
                           var systolic : String? =null,
                           var diastrlic:String? =null,
                           var pulse:String? =null,
                           var heart_rate:String?= null,
                           var irregular_heart_beat:String? =null,
                           var pressure_notes:String?= null,
                           var pressure_id:Int? =null,
                           var pressure_save_on:String? =null,
                           var pressure_update_on:String? =null,

                           //cholestrol
                           var  date_of_cholestrol : String? =null,
                           var ldl : String? =null,
                           var hdl:String? =null,
                           var trigly:String? =null,
                           var total_cholestrol:String?= null,
                           var other_test:String? =null,
                           var cholestrol_id:Int? =null,
                           var cholestrol_notes:String?= null,
                           var cholestrol_save_on:String? =null,
                           var cholestrol_update_on:String? =null,


        //bmi
                           var  date_of_bmi : String? =null,
                           var weight : String? =null,
                           var height:String? =null,
                           var bmi:String? =null,
                           var bmi_id:Int? =null,
                           var bmi_notes:String?= null,
                           var bmi_save_on:String? =null,
                           var bmi_update_on:String? =null,

        //Emotional Status
                           var  score_of_wellness : String? =null,
                           var emotional_status : String? =null,
                           var duration:String? =null,
                           var any_emotional_trauma:String? =null,
                           var reason_of_trauma:String?= null,
                           var emotional_id:Int? =null,
                           var emotional_saved_on:String? =null,
                           var emotional_updated_on:String? =null


){
}