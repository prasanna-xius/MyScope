package com.soargtechnologies.myscope.activities.medical_history

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.models.MedicalHistoryModelActivity
import com.soargtechnologies.myscope.services.MedicalHistoryService
import com.soargtechnologies.myscope.services.ServiceBuilder

import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.surgeryhistory.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SurgeryHistory_Activity : BaseActivity() {



    var button_date_surg: ImageView? = null
    var button_surg: Button? = null;
    var textview_date_surg: TextView? = null
    internal lateinit var myCalendar: Calendar
    var cal_surg = Calendar.getInstance()

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    var namesurg:String?=null;var whensurg:String?=null;var bodysurg:String?=null;var notessurg:String?=null;

    var datetextsurg :String?=null;var hospitalsurg:String?=null;var providarsurg:String?=null;

    var et_namesurg: EditText?=null;var et_whensurg: EditText?=null;var et_bodylocation: EditText?=null
    var et_notessurg: EditText?=null;var et_hospitalurg: EditText?=null;var et_providarsurg: EditText?=null

    private var awesomeValidation: AwesomeValidation? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.surgeryhistory)

        activitiesToolbar()
        header!!.text = "Surgery History"

        textview_date_surg = this.textviewdate_surg
    //    button_date_surg = this.buttondate_surg

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
   //     showLongToast(mobile_no.toString())
      //  button_date_surg= findViewById(R.id.)

        et_namesurg = findViewById(R.id.et_name_surgery_history)
        et_bodylocation = findViewById(R.id.et_body_location)
        et_providarsurg = findViewById(R.id.et_provider)
        et_hospitalurg = findViewById(R.id.et_hospital_name)
        et_notessurg = findViewById(R.id.et_notes_surgery_history)

        button_surg = findViewById(R.id.button_surgery)
        textview_date_surg = findViewById(R.id.textviewdate_surg)



        textview_date_surg!!.text = " "

        awesomeValidation = AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation!!.addValidation(this, R.id.et_name_surgery_history,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);

        //awesomeValidation!!.addValidation(this, R.id.et_body_location,
                //"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);

        //val myptr  = """^\d{1,2}/\d{1,2}/\d{4}${'$'}""".toRegex()
        //datetextsurg!!.matches(myptr)

        button_surg!!.setOnClickListener(View.OnClickListener {



            if (awesomeValidation!!.validate()) {

                val newMedicalHistoryModelActivity  = MedicalHistoryModelActivity()
                newMedicalHistoryModelActivity.surgeryname = et_name_surgery_history!!.text.toString().trim()
                newMedicalHistoryModelActivity.surgerylocation = et_body_location!!.text.toString().trim()
                newMedicalHistoryModelActivity.surgeryhospital = et_hospital_name!!.text.toString().trim()
                newMedicalHistoryModelActivity.surgerynotes = et_notes_surgery_history!!.text.toString().trim()
                newMedicalHistoryModelActivity.surgeryprovider = et_provider!!.text.toString().trim()
                newMedicalHistoryModelActivity.surgerydate = textview_date_surg?.text.toString().trim()
                newMedicalHistoryModelActivity.mobile_no=mobile_no!!
                newMedicalHistoryModelActivity.surgery_saved_on=datesetvalue()


                //newMedicalHistoryModelActivity.spnrdata =spnritem!!


                val surgeryService = ServiceBuilder.buildService(MedicalHistoryService::class.java)


                val requestCall =surgeryService.addSurgery(newMedicalHistoryModelActivity)
                requestCall.enqueue(object: Callback<MedicalHistoryModelActivity> {

                    override fun onResponse(call: Call<MedicalHistoryModelActivity>, resp: Response<MedicalHistoryModelActivity>) {


                        if (resp.isSuccessful) {

                            var newbody = resp.body() // Use it or ignore it

 //                           Toast.makeText(applicationContext, "Successfully Added", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else
                        {

//                            Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<MedicalHistoryModelActivity>, t: Throwable) {
                        //finish()
//                        Log.d("errormsgfailure ::",t.message)
//
//                        Log.e("errorunderfailure:",t.message)
//                        Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
                    }
                })



            } else {

                val snack = Snackbar.make(it!!, "Mandatory field cannot be left blank.", Snackbar.LENGTH_LONG)
                snack.show()
            }

        })


        // create an OnDateSetListener
        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            textviewdate_surg.setText(date1)
        }
        textviewdate_surg.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date_surg!!.text = sdf.format(cal_surg.getTime())
    }

}


