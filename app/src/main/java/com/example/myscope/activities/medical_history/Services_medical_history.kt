package com.example.myscope.activities.medical_history

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.Dash_Board_Activity
import com.example.myscope.activities.PrescriptionInterface
import com.example.myscope.activities.prescription.PrescriptionDataClass
import com.example.myscope.activities.prescription.Prescriptions_HomePage
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import com.google.android.gms.security.ProviderInstaller
import kotlinx.android.synthetic.main.activity_services_medical_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.SSLContext
//import kotlinx.android.synthetic.main.activity_add_sales.*

class Services_medical_history : BaseActivity() {


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


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services_medical_history)


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

        val requestCall = checkboxvalue.getService("8142529582" )

        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>>{


            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>>) {

                val length = response.body()?.size


                if (length!! == 1){

              loadDetails()

                }
                else{

//                    showLongToast("else part")

                    btn_services.setOnClickListener {
                        CheckBoxDiseasevalues()

                        CheckBoxPrescriptionsvalues()

                        val builder = AlertDialog.Builder(this@Services_medical_history)

                        builder.setTitle(R.string.dialogTitle)


                        builder.setMessage(R.string.dialogMessage)

                        builder.setPositiveButton("OK") { dialogInterface, which ->

                            val intent = Intent(this@Services_medical_history, Dash_Board_Activity::class.java)
                            startActivity(intent)
                        }
                        val alertDialog: AlertDialog = builder.create()
                        // Set other dialog properties
                        alertDialog.setCancelable(false)
                        alertDialog.show()

                        alertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(R.color.colorBackground));



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
                        newServices.prescription_aduit = checkbox_Prescription_Audit!!.isChecked().toString().toBoolean()
                        newServices.adverse_drug_monitering = checkbox_Adverse_Drug_Event_Monitoring!!.isChecked().toString().toBoolean()
                        newServices.post_dicharge_package = checkbox_Post_discharge_Care_Package!!.isChecked().toString().toBoolean()
                        newServices.mobile_no = "8142529582"

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

            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {

                showLongToast("failure")
            }
        })





    }



    private fun loadDetails() {



        val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
        val requestCall = diseaseService.getService("8142529582")

        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>> {

            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>> ) {

                val destination = response.body()

                val services = destination?.first()
//                Log.d("resp:", response.toString())
//                Log.e("resp:", response.toString())


                services?.let {


                    services.patient_counselling?.let { it1 -> checkbox_patient_counseling?.setChecked(it1) }

                    services.drug_interaction?.let { it1 -> checkbox_Drug_Interaction?.setChecked(it1) }

                    services.prescription_aduit?.let { it1 -> checkbox_Prescription_Audit?.setChecked(it1) }

                    services.adverse_drug_monitering?.let { it1 -> checkbox_Adverse_Drug_Event_Monitoring?.setChecked(it1) }

                    services.patient_counselling?.let { it1 -> checkbox_Post_discharge_Care_Package?.setChecked(it1) }


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
            newServices.prescription_aduit = checkbox_Prescription_Audit!!.isChecked().toString().toBoolean()
            newServices.adverse_drug_monitering = checkbox_Adverse_Drug_Event_Monitoring!!.isChecked().toString().toBoolean()
            newServices.post_dicharge_package = checkbox_Post_discharge_Care_Package!!.isChecked().toString().toBoolean()




            newServices.mobile_no = "8142529582"


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

    private fun CheckBoxPrescriptionsvalues() {

        val checkBoxPrescriptionsvalue = ServiceBuilder.buildService(PrescriptionInterface::class.java)

        val requestCall = checkBoxPrescriptionsvalue.getDoctorListbyId("8142529582" )

        requestCall.enqueue(object : retrofit2.Callback<List<PrescriptionDataClass>>{


            override fun onResponse(call: Call<List<PrescriptionDataClass>>, response: Response<List<PrescriptionDataClass>>) {

                val length = response.body()?.size

              showLongToast("No of lists in Prescription:"+length.toString())





                if (length!!.equals(null)){


                    showLongToast("add Prescription")

                    val  intent = Intent(this@Services_medical_history, Prescriptions_HomePage::class.java)
                    startActivity(intent)

                }
                else{
                    showLongToast("successful")
                }
            }

            override fun onFailure(call: Call<List<PrescriptionDataClass>>, t: Throwable) {

                showLongToast("add Prescription")
            }
        })

    }

    private fun CheckBoxDiseasevalues() {

        val checkboxvalue = ServiceBuilder.buildService(Disease_service::class.java)

        val requestCall = checkboxvalue.getdisease("8142529582" )

        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>>{


            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>>) {

                val length = response.body()?.size

                showLongToast("No of lists in DiseaseHistory:"+length.toString())



                if (length!! > 0){

                    showLongToast("successful")

                }
                else{
                    showLongToast("add Disease History")

                    val  intent = Intent(this@Services_medical_history,Disease_recyclerView::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {

                showLongToast("add Disease History")
            }
        })


    }
}


