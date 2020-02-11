package com.example.myscope.activities.medical_history

import android.annotation.TargetApi
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle

import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.surgeryhistory.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.getInstance

@TargetApi(Build.VERSION_CODES.N)
class SurgeryHistory : AppCompatActivity() {



    var button_date_surg: Button? = null;var button_surg: Button? = null;
    var textview_date_surg: TextView? = null
    var cal_surg = getInstance()


    var namesurg:String?=null;var whensurg:String?=null;var bodysurg:String?=null;var notessurg:String?=null;

    var datetextsurg :String?=null;var hospitalsurg:String?=null;var providarsurg:String?=null;

    var et_namesurg: EditText?=null;var et_whensurg: EditText?=null;var et_bodylocation: EditText?=null
    var et_notessurg: EditText?=null;var et_hospitalurg: EditText?=null;var et_providarsurg: EditText?=null

    private var awesomeValidation: AwesomeValidation? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.surgeryhistory)

        textview_date_surg = this.textviewdate_surg
        button_date_surg = this.buttondate_surg

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

        awesomeValidation!!.addValidation(this, R.id.et_body_location,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);

        val myptr  = """^\d{1,2}/\d{1,2}/\d{4}${'$'}""".toRegex()


        button_surg!!.setOnClickListener(View.OnClickListener {

            namesurg = et_namesurg!!.getText().toString()
            bodysurg = et_bodylocation!!.getText().toString()
            hospitalsurg = et_hospitalurg!!.getText().toString()
            notessurg = et_notessurg!!.getText().toString()
            providarsurg = et_providarsurg!!.getText().toString()
            datetextsurg= textview_date_surg?.text.toString().trim()

            if (awesomeValidation!!.validate() && datetextsurg!!.matches(myptr)) {




                Toast.makeText(getApplicationContext(), "data:" + namesurg + " " + bodysurg + " "  + hospitalsurg +
                        " " + notessurg + " " + providarsurg+ " " + datetextsurg , Toast.LENGTH_LONG).show()




            } else {

                val snack = Snackbar.make(it!!, "Mandatory field cannot be left blank.", Snackbar.LENGTH_LONG)
                snack.show()
            }


        })




        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal_surg.set(Calendar.YEAR, year)
                cal_surg.set(Calendar.MONTH, monthOfYear)
                cal_surg.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        button_date_surg!!.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onClick(view: View) {
                DatePickerDialog(this@SurgeryHistory,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal_surg.get(Calendar.YEAR),
                        cal_surg.get(Calendar.MONTH),
                        cal_surg.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date_surg!!.text = sdf.format(cal_surg.getTime())
    }

}

