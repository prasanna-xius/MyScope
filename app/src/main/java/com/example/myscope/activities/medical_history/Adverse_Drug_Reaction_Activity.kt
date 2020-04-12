package com.example.myscope.activities.medical_history

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import com.google.android.gms.security.ProviderInstaller
import kotlinx.android.synthetic.main.activity_adverse_drug.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*
import javax.net.ssl.SSLContext
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class Adverse_Drug_Reaction_Activity : BaseActivity() {

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    internal lateinit var myCalendar: Calendar
    var date1:String ?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adverse_drug)
        activitiesToolbar()
        header!!.text = "Adverse Drug Reaction"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
        showLongToast(mobile_no.toString())

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
            et_date_of_start.setText(date1)
        }
        et_date_of_start.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        btn_adverse_drug.setOnClickListener{
            assignValuestoVariable()


        }
    }




    private fun assignValuestoVariable() {
        val drugName = et_drug_Name.text.toString()
        validateInput(et_drug_Name, drugName)
        if ((drugName != "") ) {
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
        }

        catch (e: Exception) {
            e.printStackTrace();
        }

        val newDrug = Diseases()
        newDrug.drugname = et_drug_Name!!.text.toString().trim()
        newDrug.reaction = reaction_effect!!.text.toString().trim()
        newDrug.date_of_start = et_date_of_start!!.text.toString().trim()
        newDrug.treatment_taken = et_treatment_taken!!.text.toString().trim()
        newDrug.adverse_save_on = date1.toString()
        newDrug.mobile_no = mobile_no!!
        val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
        val requestCall = diseaseService.addDrug(newDrug)
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
                Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })

    }

}