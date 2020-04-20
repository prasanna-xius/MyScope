package com.soargtechnologies.myscope.activities.self_monitering

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import com.google.android.gms.security.ProviderInstaller
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Self_monitoring_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_adverse_drug.*
import kotlinx.android.synthetic.main.activity_cholestrol.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*
import javax.net.ssl.SSLContext

class Cholestrol : BaseActivity() {

    var boolean: Boolean? = true
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    internal lateinit var myCalendar: Calendar
    var date1:String ?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cholestrol)

        header!!.text = "Cholestrol"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
        showLongToast(mobile_no.toString())

        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            cholesterol.setText(date1)
        }
        cholesterol.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btn_cholesterol.setOnClickListener {
            assignValuestoVariable()
        }
    }

    private fun assignValuestoVariable() {

        val date_of_Cholestrol = cholesterol.text.toString()
        val Ldl = lDL.text.toString()
        val Hdl = hDL.text.toString()
        val Triglycerides = triglycerides.text.toString()
        val Total_cholesterol = total_cholesterol.text.toString()
        val Other_test = others_test.text.toString()

        //  validateInput(cholesterol, condition)
        validateInput(lDL , Ldl)
        validateInput(hDL , Hdl)
        validateInput(triglycerides , Triglycerides)
        validateInput(total_cholesterol , Total_cholesterol)
        validateInput(others_test , Other_test)

        if ((Ldl != "") && (Hdl != "") && (Triglycerides != "") &&
                (Total_cholesterol != "")
                && (Other_test != "")) {
            showLongToast("save the details")
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



        val newCholestrol = Self_dataClass()
        newCholestrol.date_of_cholestrol = cholesterol!!.text.toString().trim()
        newCholestrol.ldl = lDL!!.text.toString().trim()
        newCholestrol.hdl = hDL!!.text.toString().trim()
        newCholestrol.trigly = triglycerides!!.text.toString().trim()
        newCholestrol.total_cholestrol = total_cholesterol!!.text.toString().trim()
        newCholestrol.other_test = others_test!!.text.toString().trim()
        newCholestrol.cholestrol_notes = notes_cholesterol!!.text.toString().trim()
        newCholestrol.cholestrol_save_on=datesetvalue()
        newCholestrol.mobile_no = mobile_no!!



        val glucoseService = ServiceBuilder.buildService(Self_monitoring_service::class.java)

        val requestCall = glucoseService.addCholestrol(newCholestrol)

        requestCall.enqueue(object : Callback<Self_dataClass> {

            override fun onResponse(call: Call<Self_dataClass>, resp: Response<Self_dataClass>) {

                if (resp.isSuccessful) {
                    var newbody = resp.body() // Use it or ignore it
                    Toast.makeText(applicationContext, "Successfully Added"+newbody, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Self_dataClass>, t: Throwable) {
                //finish()
//                    Log.d("errormsgfailure ::", t.message)
//                    Log.e("errorunderfailure:", t.message)
                Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
