package com.soargtechnologies.myscope.activities.self_monitering

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.android.gms.security.ProviderInstaller
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Self_monitoring_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_blood__pressure.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*
import javax.net.ssl.SSLContext

class Blood_Pressure : BaseActivity() {

    var spinner_pressure: Spinner? = null
    var boolean: Boolean? = true
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    internal lateinit var myCalendar: Calendar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood__pressure)

        activitiesToolbar()
        header!!.text = "Blood Pressure"

        spinner_pressure = findViewById(R.id.spinner_irregular_heartbeats)

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

       // showLongToast(mobile_no.toString())

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.blood_pressure_dropdown))

        spinner_pressure!!.adapter = adapter

        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            date_of_bp.setText(date1)
        }
        date_of_bp.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btn_blood_pressure.setOnClickListener {
            assignValuestoVariable()
            validate(spinner_irregular_heartbeats!!)
//

        }

    }

    private fun assignValuestoVariable() {

        val date_of_Bp = date_of_bp.text.toString()
        val Irregular_heartbeats = spinner_irregular_heartbeats!!.selectedItem.toString()
        val Systolic = et_systolic.text.toString()
        val Diastolic = et_diastolic.text.toString()
        val Pulse = et_pulse.text.toString()
        val Heart_rate = et_heart_rate.text.toString()

        //  validateInput(date_of_test, condition)
        validateInput(et_systolic , Systolic)
        validateInput(et_diastolic , Diastolic)
        validateInput(et_pulse , Pulse)
        validateInput(et_heart_rate , Heart_rate)
        validateSpinner(spinner_irregular_heartbeats!!, Irregular_heartbeats)

        if ((date_of_Bp != "") && (Systolic != "") && (Diastolic != "") && (Pulse != "") &&
                (Irregular_heartbeats != "None")
                && (Heart_rate != "")) {
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



        val newPressure = Self_dataClass()
        newPressure.date_of_pressure = date_of_bp!!.text.toString().trim()
        newPressure.irregular_heart_beat = spinner_irregular_heartbeats?.getSelectedItem().toString()
        newPressure.systolic = et_systolic!!.text.toString().trim()
        newPressure.diastrlic = et_diastolic!!.text.toString().trim()
        newPressure.pulse = et_pulse!!.text.toString().trim()
        newPressure.heart_rate = et_heart_rate!!.text.toString().trim()
        newPressure.pressure_notes = notes_blood_pressure!!.text.toString().trim()
        newPressure.pressure_save_on=datesetvalue()
        newPressure.mobile_no = mobile_no!!



        val pressureService = ServiceBuilder.buildService(Self_monitoring_service::class.java)

        val requestCall = pressureService.addPressure(newPressure)

        requestCall.enqueue(object : Callback<Self_dataClass> {

            override fun onResponse(call: Call<Self_dataClass>, resp: Response<Self_dataClass>) {

                if (resp.isSuccessful) {
                    var newbody = resp.body() // Use it or ignore it
       //             Toast.makeText(applicationContext, "Successfully Added"+newbody, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
     //               Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
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
