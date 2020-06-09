package com.soargtechnologies.myscope.services

import com.soargtechnologies.myscope.ProfileDataClass
import com.soargtechnologies.myscope.activities.SignupResponse
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import com.soargtechnologies.myscope.models.ViewWindowDataClass
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PrescriptionInterface {

    //     @GET("list")
//    Call<List<PrescriptionManualDataClass>> getData();
    @POST("addRecord")
    fun createPatient(@Body body: SignupResponse?): Call<SignupResponse>?

    @Headers("Content-Type: application/json")
    @POST("updateRecord")
    fun updateSignUppage(@Body updatePatient: SignupResponse): Call<SignupResponse>

//
//    @POST("addpatientprofile")
//    fun createPatientprofile(@Body body: SignupResponse?): Call<SignupResponse>?

    @GET("Patientslist/{mobile_no}")
    fun loginPatient(@Path("mobile_no") mobile_no: String): Call<List<SignupResponse>>

    //    @GET("Patientslist")
//    fun userprofileget(@Path("mobile") mobile:String?): Call<List<SignupResponse>>

    @Headers("Content-Type: application/json")
    @POST("addpatientprofile")
    fun addProfile(@Body newProfileDataClass: ProfileDataClass): Call<ProfileDataClass>


    @Headers("Content-Type:application/json")
    @GET("Patientslist/{mobile_no}")
    fun userprofileget(@Path("mobile_no") mobile_no: String): Call<List<SignupResponse>>

    @Headers("Content-Type:application/json")
    @GET("patientdetails/{mobile_no}")
    fun userprofilegetAllValues(@Path("mobile_no") mobile_no: String): Call<List<ProfileDataClass>>

    @Headers("Content-Type: application/json")
    @POST("updatepatientprofile")
    fun updateUser(@Body newuserProfile: ProfileDataClass): Call<ProfileDataClass>

    //end point of webservice
    @Headers("Content-Type:application/json")
    @GET("doctorlist")
    fun getDoctorList(@QueryMap filter: HashMap<String, String>): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("doctorlist/{mobile_no}")
    fun getDoctorListbymobileno(@Path("mobile_no") mobile_no: String): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("doctorlist/{mobile_no}/{model_name}")
    fun getDoctorListbyId(@Path("mobile_no") mobile_no:String, @Path("model_name") model_name: String?): Call<List<PrescriptionDataClass>>


    @Headers("Content-Type:application/json")
    @GET("doctorlist/add/{prescription_id}")
    fun getDoctorlistbyPID(@Path("prescription_id") prescription_id: Int): Call<List<PrescriptionDataClass>>


    @Headers("Content-Type: application/json")
    @POST("adddoctorlist")
    fun addDoctor(@Body newPrescriptionDoctor: PrescriptionDataClass): Call<PrescriptionDataClass>

    @Headers("Content-Type:application/json")
    @GET("druglist")
    fun getDrugList(@QueryMap filter: HashMap<String, String>): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("druglist/{drug_id}")
    fun getDrugListbyId(@Path("drug_id") drug_id: Int): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type:application/json")
    @GET("druglist/add/{prescription_id}")
    fun getAllDrugsByPID(@Path("prescription_id") prescription_id: Int): Call<List<PrescriptionDataClass>>

    @Headers("Content-Type: application/json")
    @POST("updatedruglist")
    fun updateDrug(@Body newPrescriptionDoctor: PrescriptionDataClass): Call<PrescriptionDataClass>

    @Headers("Content-Type: application/json")
    @POST("addDruglist")
    fun addDrug(@Body newPrescriptionDrug: PrescriptionDataClass): Call<PrescriptionDataClass>


    @Headers("Content-Type:application/json")
    @GET("Patientsprofile/{mobile_no}")
    fun getAllprofiledata(@Path("mobile_no") mobile_no: String?): Call<List<ProfileDataClass>>


    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("preupload")
    fun uploadImage(@Part file: MultipartBody.Part,
                    @Part("mobile_no") mobile_no: RequestBody,@Part ("upload_saved_on") upload_saved_on:RequestBody, @Part("upload_type") upload_type:RequestBody,@Part("model_name") model_name:RequestBody):
            Call<PrescriptionDataClass>

    @GET("uploadedlist/{mobile_no}/{model_name}")
    fun getImageDetails(@Path("mobile_no") mobile_no:String,@Path("model_name") model_name:String): Call<MutableList<PrescriptionDataClass>>

    @GET("educationimg")
    fun getCovidImageDetails(): Call<MutableList<PrescriptionDataClass>>

    @GET("educationpdf")
    fun getCovidPdfDetails(): Call<MutableList<PrescriptionDataClass>>

    @GET("educationweb")
    fun getCovidWebLinkDetails(): Call<MutableList<PrescriptionDataClass>>

    @HTTP(method = "DELETE", path = "deleteupload", hasBody = true)
    fun deleteImageDetails(@Body newPrescriptionDrug: PrescriptionDataClass): Call<PrescriptionDataClass>

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("docdischargeupload")
    fun uploaddocDischarge(@Part("mobile_no") mobile_no: RequestBody,@Part file: MultipartBody.Part,@Part ("document_discharge_saved_on") upload_saved_on:RequestBody, @Part("document_discharge_type") upload_type:RequestBody):
            Call<PrescriptionDataClass>

    @GET("docdischargelist/{mobile_no}")
    fun getDischargeDetails(@Path("mobile_no") mobile_no:String): Call<MutableList<PrescriptionDataClass>>

    @HTTP(method = "DELETE", path = "docdeletedischarge", hasBody = true)
    fun deleteDischargeDetails(@Body newPrescriptionDrug: PrescriptionDataClass): Call<PrescriptionDataClass>


    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("docdentalupload")
    fun uploaddocDental(@Part("mobile_no") mobile_no: RequestBody,@Part file: MultipartBody.Part,@Part ("document_dental_saved_on") upload_saved_on:RequestBody, @Part("document_dental_type") upload_type:RequestBody):
            Call<PrescriptionDataClass>

    @GET("docdental/{mobile_no}")
    fun getDentalDetails(@Path("mobile_no") mobile_no:String): Call<MutableList<PrescriptionDataClass>>

    @HTTP(method = "DELETE", path = "docdentaldelete", hasBody = true)
    fun deleteDentalDetails(@Body newPrescriptionDrug: PrescriptionDataClass): Call<PrescriptionDataClass>


    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("dietdocupload")
    fun uploaddocDiet(@Part("mobile_no") mobile_no: RequestBody,@Part file: MultipartBody.Part,@Part ("document_diet_saved_on") upload_saved_on:RequestBody, @Part("document_diet_type") upload_type:RequestBody):
            Call<PrescriptionDataClass>

    @GET("dietdoc/{mobile_no}")
    fun getDietDetails(@Path("mobile_no") mobile_no:String): Call<MutableList<PrescriptionDataClass>>

    @HTTP(method = "DELETE", path = "deletedietdoc", hasBody = true)
    fun deleteDietDetails(@Body newPrescriptionDrug: PrescriptionDataClass): Call<PrescriptionDataClass>

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("doceducationupload")
    fun uploaddocEducation(@Part("mobile_no") mobile_no: RequestBody,@Part file: MultipartBody.Part,@Part ("document_education_saved_on") upload_saved_on:RequestBody, @Part("document_education_type") upload_type:RequestBody):
            Call<PrescriptionDataClass>

    @GET("doceducationlist/{mobile_no}")
    fun getEducationDetails(@Path("mobile_no") mobile_no:String): Call<MutableList<PrescriptionDataClass>>

    @HTTP(method = "DELETE", path = "docdeleteEducation", hasBody = true)
    fun deleteEducationDetails(@Body newPrescriptionDrug: PrescriptionDataClass): Call<PrescriptionDataClass>

    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("docinsuranceupload")
    fun uploaddocHealth(@Part("mobile_no") mobile_no: RequestBody,@Part file: MultipartBody.Part,@Part ("document_insurance_saved_on") upload_saved_on:RequestBody, @Part("document_insurance_type") upload_type:RequestBody):
            Call<PrescriptionDataClass>

    @GET("docinsurancelist/{mobile_no}")
    fun getHealthDetails(@Path("mobile_no") mobile_no:String): Call<MutableList<PrescriptionDataClass>>

    @HTTP(method = "DELETE", path = "docdeleteinsurance", hasBody = true)
    fun deleteInsuranceDetails(@Body newPrescriptionDrug: PrescriptionDataClass): Call<PrescriptionDataClass>


    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("docimmunupload")
    fun uploaddocImmun(@Part("mobile_no") mobile_no: RequestBody,@Part file: MultipartBody.Part,@Part ("document_immunization_saved_on") upload_saved_on:RequestBody, @Part("document_immunization_type") upload_type:RequestBody):
            Call<PrescriptionDataClass>

    @GET("docimmunlist/{mobile_no}")
    fun getImmunDetails(@Path("mobile_no") mobile_no:String): Call<MutableList<PrescriptionDataClass>>

    @HTTP(method = "DELETE", path = "docdeleteimmun", hasBody = true)
    fun deleteImmunDetails(@Body newPrescriptionDrug: PrescriptionDataClass): Call<PrescriptionDataClass>


    @Multipart
//    @Headers("Content-Type: application/json")
    @POST("docotherupload")
    fun uploaddocOther(@Part("mobile_no") mobile_no: RequestBody,@Part file: MultipartBody.Part,@Part ("document_other_saved_on") upload_saved_on:RequestBody, @Part("document_other_type") upload_type:RequestBody):
            Call<PrescriptionDataClass>

    @GET("otherdoclist/{mobile_no}")
    fun getOtherDetails(@Path("mobile_no") mobile_no:String): Call<MutableList<PrescriptionDataClass>>

    @HTTP(method = "DELETE", path = "deleteotherdoc", hasBody = true)
    fun deleteOtherDetails(@Body newPrescriptionDrug: PrescriptionDataClass): Call<PrescriptionDataClass>

    @GET("diseaseapilist")
    fun getViewWindowDetails(): Call<List<ViewWindowDataClass>>
}