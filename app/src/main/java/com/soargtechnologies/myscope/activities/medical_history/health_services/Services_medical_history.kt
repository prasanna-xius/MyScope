package com.soargtechnologies.myscope.activities.medical_history.health_services

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.medical_history.Disease_recyclerView
import com.soargtechnologies.myscope.activities.medical_history.Diseases
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import com.soargtechnologies.myscope.activities.prescription.Prescriptions_HomePage
import com.soargtechnologies.myscope.activities.services.Disease_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import com.soargtechnologies.myscope.services.PrescriptionInterface
import com.google.android.gms.security.ProviderInstaller
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.services_updated.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.SSLContext

//import kotlinx.android.synthetic.main.activity_add_sales.*

class Services_medical_history : BaseActivity() {

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null


//    val length = null;
//    lateinit var descText: TextView
//    lateinit var descText1: TextView
//    lateinit var descText2: TextView
//    lateinit var descText3: TextView
//    lateinit  var descText4: TextView
//    lateinit var minus: ImageButton
//    lateinit  var minus1: ImageButton
//    lateinit  var minus2: ImageButton
//    lateinit  var minus3: ImageButton
//    lateinit  var minus4: ImageButton
    var bool:Boolean = true

    @SuppressLint("ResourceAsColor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.services_updated)

        activitiesToolbar()
        header!!.text = "Services"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)


        val checkboxvalue = ServiceBuilder.buildService(Disease_service::class.java)

        val requestCall = checkboxvalue.getService(mobile_no!! )

        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>>{


            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>>) {

                val length = response.body()?.size

 showLongToast(length.toString())

                if (length!! == 1){

              loadDetails()

                }
                else{

//                    showLongToast("else part")

                    btn_Services.setOnClickListener {



                        validate()





                    }

                }
            }

            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {

                showLongToast("failure")
            }
        })





        patient_counseling_card.setOnClickListener {

            intent = Intent(this, Patient_counseling::class.java)
            startActivity(intent)

        }

        Drug_interaction_card.setOnClickListener {

            intent = Intent(this , Drug_interaction::class.java)

        }

        Adverse_Drug_Event_monitoring.setOnClickListener {

            intent = Intent(this , Adverse_Drug_Event_Monitoring::class.java)

        }

        Prescription_audit.setOnClickListener {

            intent = Intent(this , Prescription_Audit::class.java)

        }

        Post_discharge_Care_Package_card.setOnClickListener {

            intent = Intent(this , Post_discharge_Care_Package::class.java)

        }


    }

    private fun loadDetails() {



        val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
        val requestCall = diseaseService.getService(mobile_no!!)

        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>> {

            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>> ) {

                val destination = response.body()

                val services = destination?.first()
//                Log.d("resp:", response.toString())
//                Log.e("resp:", response.toString())


                services?.let {

                    if(services.patient_counselling!!.equals(true)){
                        checkbox_patient_counselling?.setChecked(true)
                    }



                    if(services.drug_interaction!!.equals(true)){
                        checkbox_drug_interaction?.setChecked(true)
                    }
                    if(services.prescription_audit!!.equals(true)){
                        checkbox_prescription_audit?.setChecked(true)
                    }

                    if(services.adverse_drug_monitering!!.equals(true)){
                        checkbox_adverse_drug_event?.setChecked(true)
                    }
                    if(services.post_dicharge_package!!.equals(true)){
                        checkbox_Post_discharge_care_package?.setChecked(true)
                    }
//                    services.prescription_aduit?.let { it1 -> checkbox_Prescription_Audit?.setChecked(it1) }

//                    services.adverse_drug_monitering?.let { it1 -> checkbox_Adverse_Drug_Event_Monitoring?.setChecked(it1) }

//                    services.patient_counselling?.let { it1 -> checkbox_Post_discharge_Care_Package?.setChecked(it1) }


                    initUpdateButton()

                }

            }
            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {
                Toast.makeText(this@Services_medical_history , "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

   private fun initUpdateButton() {

        btn_Services.setOnClickListener {


           val newServices = Diseases()
//
            newServices.patient_counselling = checkbox_patient_counselling!!.isChecked().toString().toBoolean()
            newServices.drug_interaction = checkbox_drug_interaction!!.isChecked().toString().toBoolean()
            newServices.prescription_audit = checkbox_prescription_audit!!.isChecked().toString().toBoolean()
            newServices.adverse_drug_monitering = checkbox_adverse_drug_event!!.isChecked().toString().toBoolean()
            newServices.post_dicharge_package = checkbox_Post_discharge_care_package!!.isChecked().toString().toBoolean()

            newServices.services_updated_on = datesetvalue()
            newServices.mobile_no = mobile_no


            val destinationService = ServiceBuilder.buildService(Disease_service::class.java)
            val requestCall = destinationService.updateService(newServices)

            requestCall.enqueue(object: Callback<Diseases> {

                override fun onResponse(call: Call<Diseases>, response: Response<Diseases>) {
                    val resp = response
                    if (response.isSuccessful)
                    {
                        Toast.makeText(this@Services_medical_history, "Item Updated Successfully", Toast.LENGTH_SHORT).show()

                    }
                    else {

                        Toast.makeText(this@Services_medical_history  , "Failed to update item1", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Diseases>, t: Throwable) {
                    Toast.makeText(this@Services_medical_history,
                            "Failed to update item", Toast.LENGTH_SHORT).show()
                }
            })

        }

    }

    private fun validate() :Boolean{



        val checkboxvalue = ServiceBuilder.buildService(Disease_service::class.java)

        val requestCall1 = checkboxvalue.getdisease(mobile_no!!)

        requestCall1.enqueue(object : retrofit2.Callback<List<Diseases>> {


            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>>) {

                val length = response.body()?.size

                showLongToast("No of lists in DiseaseHistory:" + length.toString())



                    if (length == 0) {

                        bool =  false
                        val builder = AlertDialog.Builder(this@Services_medical_history)

                        builder.setTitle(R.string.dialogTitle)


                        builder.setMessage("We found that Disease History data is empty. Please fill it before you proceed to our services. Do you want to fill now?")

                        builder.setPositiveButton("OK") { dialogInterface, which ->

                            val intent = Intent(this@Services_medical_history, Disease_recyclerView::class.java)
                            startActivity(intent)
                        }
                        val alertDialog: AlertDialog = builder.create()
                        // Set other dialog properties
                        alertDialog.setCancelable(false)
                        alertDialog.show()

                        //                    alertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(R.color.colorBackground));


                    }
                    else {


                        showLongToast("successful")
                        prescriptiondata()

                        if(prescriptiondata().equals(true)){
                            bool = true
                        }

                        else{

                            //apicall()
                        }

        //                    val intent = Intent(this@Services_medical_history, Disease_recyclerView::class.java)
        //                    startActivity(intent)
                    }

            }

            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {

                showLongToast("add Disease History")
            }
        })

return false
    }

    private fun apicall() {
        showLongToast("entered")

        try {

            ProviderInstaller.installIfNeeded(getApplicationContext());
            var sslContext: SSLContext
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine()

        }
        catch (e: Exception) {
            e.printStackTrace();
        }

        val newServices = Diseases()
        newServices.patient_counselling = checkbox_patient_counselling!!.isChecked().toString().toBoolean()
        newServices.drug_interaction = checkbox_drug_interaction!!.isChecked().toString().toBoolean()
        newServices.prescription_audit = checkbox_prescription_audit!!.isChecked().toString().toBoolean()
        newServices.adverse_drug_monitering = checkbox_adverse_drug_event!!.isChecked().toString().toBoolean()
        newServices.post_dicharge_package = checkbox_Post_discharge_care_package!!.isChecked().toString().toBoolean()
        newServices.services_saved_on = datesetvalue()
        newServices.mobile_no = mobile_no

        val servicesService = ServiceBuilder.buildService(Disease_service::class.java)

        val requestCall = servicesService.addServicesList(newServices)

        requestCall.enqueue(object : Callback<Diseases> {

            override fun onResponse(call: Call<Diseases>, resp: Response<Diseases>) {

                if (resp.isSuccessful) {
                    var newbody = resp.body() // Use it or ignore it

                    Toast.makeText(applicationContext, "Successfully Added" + newbody, Toast.LENGTH_SHORT).show()
                    //finish()
                } else {
                    Toast.makeText(applicationContext, "Failed at posting data.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Diseases>, t: Throwable) {
                //finish()
//                    Log.d("errormsgfailure ::", t.message)
//                    Log.e("errorunderfailure:", t.message)
                Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }


        })
    }

    private fun prescriptiondata() :Boolean {

        val checkBoxPrescriptionsvalue = ServiceBuilder.buildService(PrescriptionInterface::class.java)

        val requestCall = checkBoxPrescriptionsvalue.getDoctorListbymobileno(mobile_no.toString())

        requestCall.enqueue(object : retrofit2.Callback<List<PrescriptionDataClass>> {


            override fun onResponse(call: Call<List<PrescriptionDataClass>>, response: Response<List<PrescriptionDataClass>>) {

                val length = response.body()?.size

                showLongToast("No of lists in Prescription:" + length.toString())

                if (length == 0) {


                    val builder = AlertDialog.Builder(this@Services_medical_history)

                    builder.setTitle(R.string.dialogTitle)


                    builder.setMessage("We found that Prescription data is empty. Please fill it before you proceed to our services. Do you want to fill now?")

                    builder.setPositiveButton("OK") { dialogInterface, which ->

                        val intent = Intent(this@Services_medical_history, Prescriptions_HomePage::class.java)
                        startActivity(intent)
                    }
                    val alertDialog: AlertDialog = builder.create()
                    // Set other dialog properties
                    alertDialog.setCancelable(false)
                    alertDialog.show()

                    bool = false
//                    val  intent = Intent(this@Services_medical_history, Prescriptions_HomePage::class.java)
//                    startActivity(intent)

                } else {
                    showLongToast("successful")
                    apicall()
                    bool = true
                }
            }

            override fun onFailure(call: Call<List<PrescriptionDataClass>>, t: Throwable) {

                showLongToast("add Prescription")
            }
        })

    return false
    }



}


