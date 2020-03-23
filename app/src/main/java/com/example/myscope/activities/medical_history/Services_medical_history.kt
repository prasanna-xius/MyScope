package com.example.myscope.activities.medical_history

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.Dash_Board_Activity
import com.example.myscope.activities.PrescriptionInterface
import com.example.myscope.activities.prescription.PrescriptionDataClass
import com.example.myscope.activities.prescription.Prescriptions_HomePage
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_services_medical_history.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Response

class Services_medical_history : BaseActivity() {


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

        btn_services.setOnClickListener{
            CheckBoxDiseasevalues()

            CheckBoxPrescriptionsvalues()

            val builder = AlertDialog.Builder(this)

            builder.setTitle(R.string.dialogTitle)

            builder.setMessage(R.string.dialogMessage)

            builder.setPositiveButton("OK") { dialogInterface, which ->

            val  intent = Intent(this@Services_medical_history, Dash_Board_Activity::class.java)
                startActivity(intent)
            }


            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
          alertDialog.setCancelable(false)
            alertDialog.show()

            alertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(R.color.colorBackground));

        }

    }

    private fun CheckBoxPrescriptionsvalues() {

        val checkBoxPrescriptionsvalue = ServiceBuilder.buildService(PrescriptionInterface::class.java)

        val requestCall = checkBoxPrescriptionsvalue.getDoctorListbyId("8142529582" )

        requestCall.enqueue(object : retrofit2.Callback<List<PrescriptionDataClass>>{


            override fun onResponse(call: Call<List<PrescriptionDataClass>>, response: Response<List<PrescriptionDataClass>>) {

                val length = response.body()?.size

              showLongToast("No of lists in Prescription:"+length.toString())



                if (length!! > 0){

                    showLongToast("successful")

                }
                else{
                    showLongToast("add Prescription")

                    val  intent = Intent(this@Services_medical_history, Prescriptions_HomePage::class.java)
                    startActivity(intent)
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

                  //  showLongToast("successful")

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
