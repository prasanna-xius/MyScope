package com.soargtechnologies.myscope.activities.prescription

import okhttp3.MultipartBody

data class PrescriptionDataClass(var is_prescribed: String? = null,
                                 var doctor_name: String? = null,
                                 var hospital_name: String? = null,
                                 var prescription_note: String? = null,
                                 var medical_condition: String? = null,
                                 var mobile_no: String? = null,
                                 var prescription_id: Int? = 0,
                                 var model_name: String? = null,
                                 var manual_saved_on: String? = null,
                                 var manual_updated_on: String? = null,


                                 var formulation: String? = null,
                                 var drug_name: String? = null,
                                 var brand_name: String? = null,
                                 var dose_strength: String? = null,
                                 var dose_unit: String? = null,
                                 var time: String? = null,
                                 var how_often_taken: String? = null,
                                 var start_date: String? = null,
                                 var stop_date: String? = null,
                                 var drug_saved_on: String? = null,
                                 var drug_updated_on: String? = null,
                                 var drug_id: Int = 0,

                                 var p_upload: MultipartBody.Part? = null,
                                 var downloadfile: String? = null,
                                 var p_uploadid: Int = 0,
                                 var upload_saved_on: String? = null,
                                 var upload_type: String? = null,


        //Medican Documents
                                 var document_discharge_file: MultipartBody.Part? = null,
                                 var document_discharge_id: Int = 0,
                                 var document_discharge_saved_on: String? = null,
                                 var document_discharge_type: String? = null,

                                 var document_dental_file: MultipartBody.Part? = null,
                                 var document_dental_id: Int = 0,
                                 var document_dental_saved_on: String? = null,
                                 var document_dental_type: String? = null,

                                 var document_diet_file: MultipartBody.Part? = null,
                                 var document_diet_id: Int = 0,
                                 var document_diet_saved_on: String? = null,
                                 var document_diet_type: String? = null,


                                 var document_education_file: MultipartBody.Part? = null,
                                 var document_education_id: Int = 0,
                                 var document_education_saved_on: String? = null,
                                 var document_education_type: String? = null,

                                 var document_insurance_file: MultipartBody.Part? = null,
                                 var document_insurance_id: Int = 0,
                                 var document_insurance_saved_on: String? = null,
                                 var document_insurance_type: String? = null,

                                 var document_immun_file: MultipartBody.Part? = null,
                                 var document_immunization_id: Int = 0,
                                 var document_immun_saved_on: String? = null,
                                 var document_immun_type: String? = null,

                                 var document_other_file: MultipartBody.Part? = null,
                                 var document_other_id: Int = 0,
                                 var document_other_saved_on: String? = null,
                                 var document_other_type: String? = null,
        //Education blog - image

                                 var education_imagetitle: String? = null,
                                 var education_imagesubtype: String? = null,
                                 var education_image: MultipartBody.Part? = null,
                                 var downloadimage: String? = null,
                                 var education_imagesno: Int = 0,



                                 var education_pdftitle: String? = null,
                                 var education_pdfsubtype: String? = null,
                                 var education_pdf: MultipartBody.Part? = null,
                                 var downloadpdf: String? = null,
                                 var education_pdfsno: Int = 0,

        //Education blog - weblinks

                                 var education_weblink: String? = null,
                                 var education_webtitle: String? = null,
                                 var education_websubtype: String? = null,
                                 var education_websno: Int = 0


)
