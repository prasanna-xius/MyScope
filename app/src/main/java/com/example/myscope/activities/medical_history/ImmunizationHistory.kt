package com.example.myscope.activities.medical_history

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.icu.util.Calendar.getInstance
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
import kotlinx.android.synthetic.main.immunizationhistory.*
import java.text.SimpleDateFormat
import java.util.*

class ImmunizationHistory : AppCompatActivity() {
    var btn_immunization: Button?=null
    var button_date_immun: Button? = null
    var textview_date_immun: TextView? = null
    @RequiresApi(Build.VERSION_CODES.N)
    var cal_immun = getInstance()


    var nameimmu:String?=null;var brand:String?=null;var event:String?=null;var notesimmu:String?=null;

    var datetext :String?=null

    var etname_immu: EditText?=null;var etevent: EditText?=null;var etbrand: EditText?=null
    var etnotes: EditText?=null

    private var awesomeValidation: AwesomeValidation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.immunizationhistory)


        etname_immu = findViewById(R.id.et_name)
        etevent = findViewById(R.id.et_adverse_event)
        etbrand = findViewById(R.id.et_brand)
        etnotes = findViewById(R.id.et_notes_immun)
        textview_date_immun = findViewById(R.id.textviewdate_immu)



        awesomeValidation = AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation!!.addValidation(this, R.id.et_name,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);

        awesomeValidation!!.addValidation(this, R.id.et_adverse_event,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);


        val myptr  = """^\d{1,2}/\d{1,2}/\d{4}${'$'}""".toRegex()



        btn_immunization = findViewById<View>(R.id.btn_immunization) as Button



        button_date_immun = this.buttondate_immu


        //textview_date_immun!!.text= " "

        btn_immunization!!.setOnClickListener(object: View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(v: View?) {

                nameimmu = etname_immu!!.getText().toString()
                event = etevent!!.getText().toString()
                brand = etbrand!!.getText().toString()
                notesimmu = etnotes!!.getText().toString()
                datetext= textview_date_immun?.text.toString().trim()




                if (awesomeValidation!!.validate() && datetext!!.matches(myptr)) {




                    Toast.makeText(getApplicationContext(), "data:" + nameimmu + " " + event + " " + brand + " " + notesimmu +
                            " " + datetext , Toast.LENGTH_LONG).show()


                } else {

                    val snack = Snackbar.make(v!!, "Mandatory field cannot be left blank.", Snackbar.LENGTH_LONG)
                    snack.show()
                }
            }
        })



        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal_immun.set(Calendar.YEAR, year)
                cal_immun.set(Calendar.MONTH, monthOfYear)
                cal_immun.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()

            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        button_date_immun?.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("NewApi")
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onClick(view: View) {
                DatePickerDialog(this@ImmunizationHistory,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal_immun.get(Calendar.YEAR),
                        cal_immun.get(Calendar.MONTH),
                        cal_immun.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date_immun!!.text = sdf.format(cal_immun.getTime())



    }

}