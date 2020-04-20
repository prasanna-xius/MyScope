package com.soargtechnologies.myscope.activities.medical_history

import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View

import android.widget.*
import androidx.annotation.RequiresApi
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Disease_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import com.google.android.gms.security.ProviderInstaller
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.disease_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*
import javax.net.ssl.SSLContext

class Disease_History : BaseActivity() {
    internal lateinit var myCalendar: Calendar
    private var startDateOrEndDAte = true
    var spinner_disease: Spinner? = null
    var boolean: Boolean? = true
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.disease_history)
        activitiesToolbar()

        header!!.text = "Disease History"
        spinner_disease = findViewById<Spinner>(R.id.spinner_disease)

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
        showLongToast(mobile_no.toString())

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.disease_arrays))
        spinner_disease!!.adapter = adapter

        myCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view:View, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            if (startDateOrEndDAte) {
                et_startDate.setText(date1)
            } else {
                et_stopDate.setText(date1)
            }
            duration(et_startDate, et_stopDate, et_noOfYrs)
        }

        et_startDate.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            startDateOrEndDAte = true
        }

        et_stopDate.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            startDateOrEndDAte = false
        }

        btn_medicalHistory.setOnClickListener {
            assignValuestoVariable()
            validate(spinner_disease!!)
//

        }

    }


    private fun assignValuestoVariable() {

        val condition = et_condition.text.toString()
        val diseases = spinner_disease!!.selectedItem.toString()
        val disease_duration = et_noOfYrs.text.toString()
        validateInput(et_condition, condition)
        validateInput(et_noOfYrs , disease_duration)
        validateSpinner(spinner_disease!!, diseases)

        if ((condition != "") &&
                (diseases != "None")
                && (disease_duration != "")) {
            showLongToast("save the details")
            sucess()

        }

        else {

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
        } catch (e: Exception) {
            e.printStackTrace();
        }



        val newDisease = Diseases()
        newDisease.known_condition = et_condition!!.text.toString().trim()
        newDisease.disease_status = spinner_disease?.getSelectedItem().toString()
        newDisease.disease_duration = et_noOfYrs!!.text.toString().trim()
        newDisease.when_started = et_startDate!!.text.toString().trim()
        newDisease.when_ended = et_stopDate!!.text.toString().trim()
        newDisease.disease_note = notes_diseaseHistory!!.text.toString().trim()
        newDisease.disease_save_on=datesetvalue()
        newDisease.mobile_no = mobile_no!!



        val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)

        //val requestCall =allergyService.addAllergy(name!!,reaction!!,treatment!!,notes!!,date!!,sprdata!!)

        val requestCall = diseaseService.addDisease(newDisease)

        requestCall.enqueue(object : Callback<Diseases> {

            override fun onResponse(call: Call<Diseases>, resp: Response<Diseases>) {

                if (resp.isSuccessful) {
                    var newbody = resp.body() // Use it or ignore it
                    Toast.makeText(applicationContext, "Successfully Added"+newbody, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
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






