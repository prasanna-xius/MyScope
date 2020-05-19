package com.soargtechnologies.myscope.services

import com.soargtechnologies.myscope.activities.labReports.Lab_ReportDataClass
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface LabReportsService {

                    // Blood
    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("bloodupload")
    fun uploadBloodDetails(@Part lab_blood_file: MultipartBody.Part,
                    @Part("mobile_no") mobile_no: RequestBody, @Part("lab_blood_saved") lab_blood_saved: RequestBody, @Part("lab_blood_type") lab_blood_type: RequestBody):
            Call<Lab_ReportDataClass>

    @GET("labblood")
    fun getBloodDetails(): Call<MutableList<Lab_ReportDataClass>>


    @HTTP(method = "DELETE", path = "deleteblood", hasBody = true)
    fun deleteBloodDetails(@Body newlabreport: Lab_ReportDataClass): Call<Lab_ReportDataClass>

                //Urine

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("urineupload")
    fun uploadUrineDetails(@Part lab_urine_report_file: MultipartBody.Part,
                           @Part("mobile_no") mobile_no: RequestBody, @Part("lab_urine_report_saved") lab_urine_report_saved: RequestBody, @Part("lab_urine_report_type") lab_urine_report_type: RequestBody):
            Call<Lab_ReportDataClass>

    @GET("laburine")
    fun getUrineDetails(): Call<MutableList<Lab_ReportDataClass>>


    @HTTP(method = "DELETE", path = "deleteurine", hasBody = true)
    fun deleteUrineDetails(@Body newlabreport: Lab_ReportDataClass): Call<Lab_ReportDataClass>


            //Ultrasound

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("ultrasoundupload")
    fun uploadUltrasoundDetails(@Part lab_ultrasound_file: MultipartBody.Part,
                           @Part("mobile_no") mobile_no: RequestBody, @Part("lab_ultrasound_saved") lab_ultrasound_saved: RequestBody, @Part("lab_ultrasound_type") lab_ultrasound_type: RequestBody):
            Call<Lab_ReportDataClass>

    @GET("labultrasound")
    fun getUltrasoundDetails(): Call<MutableList<Lab_ReportDataClass>>


    @HTTP(method = "DELETE", path = "deleteultrasound", hasBody = true)
    fun deleteUltrasoundDetails(@Body newlabreport: Lab_ReportDataClass): Call<Lab_ReportDataClass>

        //Xray

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("xrayupload")
    fun uploadXrayDetails(@Part lab_xray_file: MultipartBody.Part,
                                @Part("mobile_no") mobile_no: RequestBody, @Part("lab_xray_saved") lab_xray_saved: RequestBody, @Part("lab_xray_type") lab_xray_type: RequestBody):
            Call<Lab_ReportDataClass>

    @GET("labxray")
    fun getXrayDetails(): Call<MutableList<Lab_ReportDataClass>>


    @HTTP(method = "DELETE", path = "deletexray", hasBody = true)
    fun deleteXrayDetails(@Body newlabreport: Lab_ReportDataClass): Call<Lab_ReportDataClass>


        //Ctscan

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("ctscanupload")
    fun uploadCtscanDetails(@Part lab_ctscan_file: MultipartBody.Part,
                          @Part("mobile_no") mobile_no: RequestBody, @Part("lab_ctscan_saved") lab_ctscan_saved: RequestBody, @Part("lab_ctscan_type") lab_blood_type: RequestBody):
            Call<Lab_ReportDataClass>

    @GET("labctscan")
    fun getCtscanDetails(): Call<MutableList<Lab_ReportDataClass>>


    @HTTP(method = "DELETE", path = "deletectscan", hasBody = true)
    fun deleteCtscanDetails(@Body newlabreport: Lab_ReportDataClass): Call<Lab_ReportDataClass>

        //Mri

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("mriupload")
    fun uploadMriDetails(@Part lab_mri_file: MultipartBody.Part,
                            @Part("mobile_no") mobile_no: RequestBody, @Part("lab_mri_saved") lab_mri_saved: RequestBody, @Part("lab_mri_type") lab_mri_type: RequestBody):
            Call<Lab_ReportDataClass>

    @GET("labmri")
    fun getMriDetails(): Call<MutableList<Lab_ReportDataClass>>


    @HTTP(method = "DELETE", path = "deletemri", hasBody = true)
    fun deleteMriDetails(@Body newlabreport: Lab_ReportDataClass): Call<Lab_ReportDataClass>

        //Eco

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("echoupload")
    fun uploadEcoDetails(@Part lab_echo_file: MultipartBody.Part,
                            @Part("mobile_no") mobile_no: RequestBody, @Part("lab_echo_saved") lab_echo_saved: RequestBody, @Part("lab_echo_type") lab_echo_type: RequestBody):
            Call<Lab_ReportDataClass>

    @GET("labecho")
    fun getEcoDetails(): Call<MutableList<Lab_ReportDataClass>>


    @HTTP(method = "DELETE", path = "deleteecho", hasBody = true)
    fun deleteEcoDetails(@Body newlabreport: Lab_ReportDataClass): Call<Lab_ReportDataClass>

         //Ecg

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("ecgupload")
    fun uploadEcgDetails(@Part lab_ecg_file: MultipartBody.Part,
                            @Part("mobile_no") mobile_no: RequestBody, @Part("lab_ecg_saved") lab_ecg_saved: RequestBody, @Part("lab_ecg_type") lab_ecg_type: RequestBody):
            Call<Lab_ReportDataClass>

    @GET("labecg")
    fun getEcgDetails(): Call<MutableList<Lab_ReportDataClass>>


    @HTTP(method = "DELETE", path = "deleteecg", hasBody = true)
    fun deleteEcgDetails(@Body newlabreport: Lab_ReportDataClass): Call<Lab_ReportDataClass>


        //Stress

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("stressupload")
    fun uploadStressDetails(@Part lab_stresstest_file: MultipartBody.Part,
                            @Part("mobile_no") mobile_no: RequestBody, @Part("lab_stresstest_saved") lab_stresstest_saved: RequestBody, @Part("lab_stresstest_type") lab_stresstest_type: RequestBody):
            Call<Lab_ReportDataClass>

    @GET("labstress")
    fun getStressDetails(): Call<MutableList<Lab_ReportDataClass>>


    @HTTP(method = "DELETE", path = "deletestress", hasBody = true)
    fun deleteStressDetails(@Body newlabreport: Lab_ReportDataClass): Call<Lab_ReportDataClass>

    //others

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("labotherupload")
    fun uploadOthersDetails(@Part lab_others_file: MultipartBody.Part,
                            @Part("mobile_no") mobile_no: RequestBody, @Part("lab_others_saved") lab_others_saved: RequestBody, @Part("lab_others_type") lab_others_type: RequestBody):
            Call<Lab_ReportDataClass>

    @GET("labother")
    fun getOthersDetails(): Call<MutableList<Lab_ReportDataClass>>


    @HTTP(method = "DELETE", path = "deleteother", hasBody = true)
    fun deleteOthersDetails(@Body newlabreport: Lab_ReportDataClass): Call<Lab_ReportDataClass>

    //Colonoscopy

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("colonoscopyupload")
    fun uploadColonoscopyDetails(@Part lab_colonoscopy_file: MultipartBody.Part,
                            @Part("mobile_no") mobile_no: RequestBody, @Part("lab_colonoscopy_saved") lab_colonoscopy_saved: RequestBody, @Part("lab_colonoscopy_type") lab_colonoscopy_type: RequestBody):
            Call<Lab_ReportDataClass>

    @GET("labcolonoscopy")
    fun getColonoscopyDetails(): Call<MutableList<Lab_ReportDataClass>>


    @HTTP(method = "DELETE", path = "deletecolonoscopy", hasBody = true)
    fun deleteColonoscopyDetails(@Body newlabreport: Lab_ReportDataClass): Call<Lab_ReportDataClass>

}