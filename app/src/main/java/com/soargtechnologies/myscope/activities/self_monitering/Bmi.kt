package com.soargtechnologies.myscope.activities.self_monitering

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.security.ProviderInstaller
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Self_monitoring_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_cholestrol.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*
import javax.net.ssl.SSLContext

class Bmi_self : BaseActivity() {

    var boolean: Boolean? = true
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    internal lateinit var myCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        activitiesToolbar()
        header!!.text = "Bmi"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

      //  showLongToast(mobile_no.toString())

        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            date_of_bmi.setText(date1)
        }
        date_of_bmi.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btn_bmi.setOnClickListener {
            assignValuestoVariable()

        }

        bmi1.setOnClickListener {

            bmicalculator(weight_bmi, height_bmi , bmi1)
        }
    }
    private fun assignValuestoVariable() {

        val date_of_Bmi = date_of_bmi.text.toString()
        val Weight = weight_bmi.text.toString()
        val Height = height_bmi.text.toString()
        val Bmi = bmi1.text.toString()

        //  validateInput(date_of_bmi, date_of_Bmi)
        validateInput( weight_bmi, Weight)
        validateInput(height_bmi , Height)

        if ((Bmi != "") && (Weight != "") && (Height != "") && (date_of_Bmi != "") ) {
  //          showLongToast("save the details")
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



        val newBmi = Self_dataClass()
        newBmi.date_of_bmi = date_of_bmi!!.text.toString().trim()
        newBmi.weight = weight_bmi!!.text.toString().trim()
        newBmi.height = height_bmi!!.text.toString().trim()
        newBmi.bmi = bmi1!!.text.toString().trim()
        newBmi.bmi_notes = notes_bmi!!.text.toString().trim()
        newBmi.bmi_save_on=datesetvalue()
        newBmi.mobile_no = mobile_no!!



        val glucoseService = ServiceBuilder.buildService(Self_monitoring_service::class.java)

        val requestCall = glucoseService.addBmi(newBmi)

        requestCall.enqueue(object : Callback<Self_dataClass> {

            override fun onResponse(call: Call<Self_dataClass>, resp: Response<Self_dataClass>) {

                if (resp.isSuccessful) {
                    var newbody = resp.body() // Use it or ignore it
     //               Toast.makeText(applicationContext, "Successfully Added"+newbody, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
   //                 Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Self_dataClass>, t: Throwable) {
                //finish()
//                    Log.d("errormsgfailure ::", t.message)
//                    Log.e("errorunderfailure:", t.message)
    //            Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
