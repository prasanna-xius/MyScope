package com.soargtechnologies.myscope.activities.labReports

import okhttp3.MultipartBody

data class Lab_ReportDataClass (
        var lab_blood_file: MultipartBody.Part?=null,
        var downloadfile:String? = null,
        var lab_blood_id:Int = 0,
        var lab_blood_saved:String ?= null,
        var lab_blood_type:String?=null,


        var lab_colonoscopy_file: MultipartBody.Part?=null,
        var lab_colonoscopy_id:Int = 0,
        var lab_colonoscopy_saved:String ?= null,
        var lab_colonoscopy_type:String?=null,

        var lab_ctscan_file: MultipartBody.Part?=null,
        var lab_ctscan_id:Int = 0,
        var lab_ctscan_saved:String ?= null,
        var lab_ctscan_type:String?=null,

        var lab_ecg_file: MultipartBody.Part?=null,
        var lab_ecg_id:Int = 0,
        var lab_ecg_saved:String ?= null,
        var lab_ecg_type:String?=null,

        var lab_echo_file: MultipartBody.Part?=null,
        var lab_echo_id:Int = 0,
        var lab_echo_saved:String ?= null,
        var lab_echo_type:String?=null,

        var lab_mri_file: MultipartBody.Part?=null,
        var lab_mri_id:Int = 0,
        var lab_mri_saved:String ?= null,
        var lab_mri_type:String?=null,

        var lab_xray_file: MultipartBody.Part?=null,
        var lab_xray_id:Int = 0,
        var lab_xray_saved:String ?= null,
        var lab_xray_type:String?=null,

        var lab_others_file: MultipartBody.Part?=null,
        var lab_others_id:Int = 0,
        var lab_others_saved:String ?= null,
        var lab_others_type:String?=null,

        var lab_stresstest_file: MultipartBody.Part?=null,
        var lab_stresstest_id:Int = 0,
        var lab_stresstest_saved:String ?= null,
        var lab_stresstest_type:String?=null,


        var lab_ultrasound_file: MultipartBody.Part?=null,
        var lab_ultrasound_id:Int = 0,
        var lab_ultrasound_saved:String ?= null,
        var lab_ultrasound_type:String?=null,

        var lab_urine_report_file: MultipartBody.Part?=null,
        var lab_urine_report_id:Int = 0,
        var lab_urine_report_saved:String ?= null,
        var lab_urine_report_type:String?=null,
        var mobile_no: String? = null

)
