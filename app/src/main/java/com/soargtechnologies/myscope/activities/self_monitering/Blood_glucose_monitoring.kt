package com.soargtechnologies.myscope.activities.self_monitering

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.android.gms.security.ProviderInstaller
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Self_monitoring_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_blood_glucose_monitoring.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*
import javax.net.ssl.SSLContext

class Blood_glucose_Monitoring : BaseActivity() {

    var spinner_blood: Spinner? = null
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    internal lateinit var myCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_glucose_monitoring)

        activitiesToolbar()

        header!!.text = "Blood Glucose Monitoring"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        spinner_blood = findViewById<Spinner>(R.id.spinner_glucose)

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.blood_glucose_dropdown))
        spinner_blood!!.adapter = adapter

        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            date_of_test.setText(date1)
        }
        date_of_test.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btn_blood_glucose.setOnClickListener {
            assignValuestoVariable()
            validate(spinner_glucose!!)
//

        }
    }


    private fun assignValuestoVariable() {

        val date_of_Test = date_of_test.text.toString()
        val Test = spinner_glucose!!.selectedItem.toString()
        val Test_result = test_result_glucose.text.toString()
      //  validateInput(date_of_test, condition)
        validateInput(test_result_glucose , Test_result)
        validateSpinner(spinner_glucose!!, Test)

        if ((date_of_Test != "") &&
                (Test != "None")
                && (Test_result != "")) {
     //       showLongToast("save the details")
            sucess()

        } else {

            showLongSnackBar("Please fill the required fields")

        }
    }

    private fun sucess() {



        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(getApplicationContext());
            var sslContext: SSLContext
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        }
        catch (e: Exception) {
            e.printStackTrace();
        }



        val newGlucose = Self_dataClass()
        newGlucose.date_of_test = date_of_test!!.text.toString().trim()
        newGlucose.test = spinner_glucose?.getSelectedItem().toString()
        newGlucose.test_result = test_result_glucose!!.text.toString().trim()
        newGlucose.glucose_notes = notes_blood_glucose!!.text.toString().trim()
        newGlucose.glucose_save_on=datesetvalue()
        newGlucose.mobile_no = mobile_no!!



        val glucoseService = ServiceBuilder.buildService(Self_monitoring_service::class.java)

        val requestCall = glucoseService.addGlucose(newGlucose)

        requestCall.enqueue(object : Callback<Self_dataClass> {

            override fun onResponse(call: Call<Self_dataClass>, resp: Response<Self_dataClass>) {

                if (resp.isSuccessful) {
                    var newbody = resp.body() // Use it or ignore it
       //             Toast.makeText(applicationContext, "Successfully Added"+newbody, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
      //              Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Self_dataClass>, t: Throwable) {
                //finish()
//                    Log.d("errormsgfailure ::", t.message)
//                    Log.e("errorunderfailure:", t.message)
     //           Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })

    }

}
