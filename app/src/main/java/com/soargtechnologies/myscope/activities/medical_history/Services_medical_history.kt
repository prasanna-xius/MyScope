package com.soargtechnologies.myscope.activities.medical_history

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.Dash_Board_Activity
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import com.soargtechnologies.myscope.activities.prescription.Prescriptions_HomePage
import com.soargtechnologies.myscope.activities.services.Disease_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import com.soargtechnologies.myscope.services.PrescriptionInterface
import com.google.android.gms.security.ProviderInstaller
import kotlinx.android.synthetic.main.activity_services_medical_history.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.SSLContext
//import kotlinx.android.synthetic.main.activity_add_sales.*

class Services_medical_history : BaseActivity() {

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null


    val length = null;
    lateinit var descText: TextView
    lateinit var descText1: TextView
    lateinit var descText2: TextView
    lateinit var descText3: TextView
    lateinit  var descText4: TextView
    lateinit var minus: ImageButton
    lateinit  var minus1: ImageButton
    lateinit  var minus2: ImageButton
    lateinit  var minus3: ImageButton
    lateinit  var minus4: ImageButton
    var bool:Boolean = true

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services_medical_history)

        activitiesToolbar()
        header!!.text = "Services"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        descText = findViewById(R.id.description_text)
        minus = findViewById(R.id.minus)

        descText1 = findViewById(R.id.description_text1)
        minus1 = findViewById(R.id.minus1)

        descText2 = findViewById(R.id.description_text2)
        minus2 = findViewById(R.id.minus2)

        descText3 = findViewById(R.id.description_text3)
        minus3 = findViewById(R.id.minus3)

        descText4 = findViewById(R.id.description_text4)
        minus4 = findViewById(R.id.minus4)


        patient_counseling.setOnClickListener {
            descText.maxLines = Integer.MAX_VALUE
            descText1.maxLines = 0
            descText2.maxLines = 0
            descText3.maxLines = 0
            descText4.maxLines = 0
            minus.visibility = View.VISIBLE
            minus1.visibility = View.GONE
            minus2.visibility = View.GONE
            minus3.visibility = View.GONE
            minus4.visibility = View.GONE
        }

        minus.setOnClickListener {
            minus.visibility = View.GONE
            descText.maxLines = 0
        }

        Drug_Interaction.setOnClickListener {
            minus1.visibility = View.VISIBLE
            descText1.maxLines = Integer.MAX_VALUE
            descText.maxLines = 0
            descText2.maxLines = 0
            descText3.maxLines = 0
            descText4.maxLines = 0
            minus.visibility = View.GONE
            minus2.visibility = View.GONE
            minus3.visibility = View.GONE
            minus4.visibility = View.GONE
        }

        minus1.setOnClickListener {
            minus1.visibility = View.GONE
            descText1.maxLines = 0
        }

        Prescription_Audit.setOnClickListener {
            minus2.visibility = View.VISIBLE
            descText2.maxLines = Integer.MAX_VALUE
            descText1.maxLines = 0
            descText.maxLines = 0
            descText3.maxLines = 0
            descText4.maxLines = 0
            minus.visibility = View.GONE
            minus1.visibility = View.GONE
            minus3.visibility = View.GONE
            minus4.visibility = View.GONE
                }

        minus2.setOnClickListener {
            minus2.visibility = View.GONE
            descText2.maxLines = 0
        }

        Adverse_Drug_Event_Monitoring.setOnClickListener {
            minus3.visibility = View.VISIBLE
            descText3.maxLines = Integer.MAX_VALUE
            descText1.maxLines = 0
            descText2.maxLines = 0
            descText.maxLines = 0
            descText4.maxLines = 0
            minus.visibility = View.GONE
            minus2.visibility = View.GONE
            minus1.visibility = View.GONE
            minus4.visibility = View.GONE
        }

        minus3.setOnClickListener {
            minus3.visibility = View.GONE
            descText3.maxLines = 0
        }

        Post_discharge_Care_Package.setOnClickListener {
            minus4.visibility = View.VISIBLE
            descText4.maxLines = Integer.MAX_VALUE
            descText1.maxLines = 0
            descText2.maxLines = 0
            descText3.maxLines = 0
            descText.maxLines = 0
            minus.visibility = View.GONE
            minus2.visibility = View.GONE
            minus3.visibility = View.GONE
            minus1.visibility = View.GONE
        }

        minus4.setOnClickListener {
            minus4.visibility = View.GONE
            descText4.maxLines = 0
        }




        val checkboxvalue = ServiceBuilder.buildService(Disease_service::class.java)

        val requestCall = checkboxvalue.getService(mobile_no!! )

        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>>{


            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>>) {

                val length = response.body()?.size


                if (length!! == 1){

              loadDetails()

                }
                else{

//                    showLongToast("else part")

                    btn_services.setOnClickListener {
                        if (validate().equals(true)) {

                            try {

                                ProviderInstaller.installIfNeeded(getApplicationContext());
                                var sslContext: SSLContext
                                sslContext = SSLContext.getInstance("TLSv1.2");
                                sslContext.init(null, null, null);
                                sslContext.createSSLEngine()

                            } catch (e: Exception) {
                                e.printStackTrace();
                            }

                            val newServices = Diseases()
                            newServices.patient_counselling = checkbox_patient_counseling!!.isChecked().toString().toBoolean()
                            newServices.drug_interaction = checkbox_Drug_Interaction!!.isChecked().toString().toBoolean()
                            newServices.prescription_audit = checkbox_Prescription_Audit!!.isChecked().toString().toBoolean()
                            newServices.adverse_drug_monitering = checkbox_Adverse_Drug_Event_Monitoring!!.isChecked().toString().toBoolean()
                            newServices.post_dicharge_package = checkbox_Post_discharge_Care_Package!!.isChecked().toString().toBoolean()
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
                    }
                }
            }

            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {

                showLongToast("failure")
            }
        })





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
                        checkbox_patient_counseling?.setChecked(true)
                    }



                    if(services.drug_interaction!!.equals(true)){
                        checkbox_Drug_Interaction?.setChecked(true)
                    }
                    if(services.prescription_audit!!.equals(true)){
                        checkbox_Prescription_Audit?.setChecked(true)
                    }

                    if(services.adverse_drug_monitering!!.equals(true)){
                        checkbox_Adverse_Drug_Event_Monitoring?.setChecked(true)
                    }
                    if(services.post_dicharge_package!!.equals(true)){
                        checkbox_Post_discharge_Care_Package?.setChecked(true)
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



        btn_services.setOnClickListener {



//
           val newServices = Diseases()
//
//

            newServices.patient_counselling = checkbox_patient_counseling!!.isChecked().toString().toBoolean()
            newServices.drug_interaction = checkbox_Drug_Interaction!!.isChecked().toString().toBoolean()
            newServices.prescription_audit = checkbox_Prescription_Audit!!.isChecked().toString().toBoolean()
            newServices.adverse_drug_monitering = checkbox_Adverse_Drug_Event_Monitoring!!.isChecked().toString().toBoolean()
            newServices.post_dicharge_package = checkbox_Post_discharge_Care_Package!!.isChecked().toString().toBoolean()

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


                    } else {


                        showLongToast("successful")
                        prescriptiondata()
                        if(prescriptiondata().equals(true)){
                            bool = true
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


