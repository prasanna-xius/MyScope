package com.soargtechnologies.myscope.activities.medical_history

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.models.MedicalHistoryModelActivity
import com.soargtechnologies.myscope.services.MedicalHistoryService
import com.soargtechnologies.myscope.services.ServiceBuilder

import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_adverse_drug.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.immunizationhistory.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ImmunizationHistory : BaseActivity() {
    var btn_immunization: Button?=null
    var button_date_immun: ImageView? = null
    var textview_date_immun: TextView? = null
    internal lateinit var myCalendar: Calendar
    var date1:String ?=null;
    var cal_immun = Calendar.getInstance()

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    var nameimmu:String?=null;var brand:String?=null;var event:String?=null;var notesimmu:String?=null;

    var datetext :String?=null

    var etname_immu: EditText?=null;var etevent: EditText?=null;var etbrand: EditText?=null
    var etnotes: EditText?=null

    private var awesomeValidation: AwesomeValidation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.immunizationhistory)

        activitiesToolbar()
        header!!.text = "Immunization History"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        etname_immu = findViewById(R.id.et_name)
        etevent = findViewById(R.id.et_adverse_event_immun)
        etbrand = findViewById(R.id.et_brand_immun)
        etnotes = findViewById(R.id.et_notes_immun)
        //textview_date_immun = findViewById(R.id.textviewdate_immu)

        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        date1 = day.toString() + "/" + (month + 1) + "/" + year


        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date = DateFormat.getDateInstance().format(myCalendar.getTime())
            buttondate_immu.setText(date1)
        }
        buttondate_immu.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        awesomeValidation = AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation!!.addValidation(this, R.id.et_name,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);

       // awesomeValidation!!.addValidation(this, R.id.et_adverse_event,
                //"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);


        //val myptr  = """^\d{1,2}/\d{1,2}/\d{4}${'$'}""".toRegex()



        btn_immunization = findViewById<View>(R.id.btn_immunization) as Button



    //    button_date_immun = this.buttondate_immu


    //textview_date_immun!!.text= " "

        btn_immunization!!.setOnClickListener(object: View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(v: View?) {

               /* nameimmu = etname_immu!!.getText().toString()
                event = etevent!!.getText().toString()
                brand = etbrand!!.getText().toString()
                notesimmu = etnotes!!.getText().toString()
               datetext= textview_date_immun?.text.toString().trim()*/



                if (awesomeValidation!!.validate()) {


                    val newMedicalHistoryModelActivity  = MedicalHistoryModelActivity()
                    newMedicalHistoryModelActivity.immuname = et_name!!.text.toString().trim()
                    newMedicalHistoryModelActivity.immuevent = et_adverse_event_immun!!.text.toString().trim()
                    newMedicalHistoryModelActivity.immubrand = et_brand_immun!!.text.toString().trim()
                    newMedicalHistoryModelActivity.immunotes = et_notes_immun!!.text.toString().trim()
                    newMedicalHistoryModelActivity.immudate = buttondate_immu?.text.toString().trim()
                    newMedicalHistoryModelActivity.mobile_no=mobile_no!!
                    newMedicalHistoryModelActivity.immun_saved_on=datesetvalue()

                    //newMedicalHistoryModelActivity.spnrdata =spnritem!!


                    val immunService = ServiceBuilder.buildService(MedicalHistoryService::class.java)


                    val requestCall =immunService.addImmunization(newMedicalHistoryModelActivity)
                    requestCall.enqueue(object: Callback<MedicalHistoryModelActivity> {

                        override fun onResponse(call: Call<MedicalHistoryModelActivity>, resp: Response<MedicalHistoryModelActivity>) {


                            if (resp.isSuccessful) {

                                var newbody = resp.body() // Use it or ignore it

                                Toast.makeText(applicationContext, "Successfully Added", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            else
                            {

                                Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<MedicalHistoryModelActivity>, t: Throwable) {
                            //finish()
                            Log.d("errormsgfailure ::",t.message)

                            Log.e("errorunderfailure:",t.message)
                            Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
                        }
                    })




                    //Toast.makeText(getApplicationContext(), "data:" + nameimmu + " " + event + " " + brand + " " + notesimmu +
                            //" " + datetext , Toast.LENGTH_LONG).show()


                } else {

                    val snack = Snackbar.make(v!!, "Mandatory field cannot be left blank.", Snackbar.LENGTH_LONG)
                    snack.show()
                }
            }
        })



    // create an OnDateSetListener
//    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
//        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
//                               dayOfMonth: Int) {
//            cal_immun.set(Calendar.YEAR, year)
//            cal_immun.set(Calendar.MONTH, monthOfYear)
//            cal_immun.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//            updateDateInView()
//
//        }
//    }

    // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
//    button_date_immun?.setOnClickListener(object : View.OnClickListener {
//        override fun onClick(view: View) {
//            DatePickerDialog(this@ImmunizationHistory,
//                    dateSetListener,
//                    // set DatePickerDialog to point to today's date when it loads up
//                    cal_immun.get(Calendar.YEAR),
//                    cal_immun.get(Calendar.MONTH),
//                    cal_immun.get(Calendar.DAY_OF_MONTH)).show()
//        }
//
//    })

    }

//private fun updateDateInView() {
//    val myFormat = "dd-MM-yyyy" // mention the format you need
//    val sdf = SimpleDateFormat(myFormat, Locale.US)
//    date1!!.text = sdf.format(cal_immun.getTime())
//
//
//
//}

}













