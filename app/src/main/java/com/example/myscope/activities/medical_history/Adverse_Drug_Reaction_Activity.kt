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
class Adverse_Drug_Reaction_Activity : BaseActivity() {

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    internal lateinit var myCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adverse_drug)
        activitiesToolbar()
        header!!.text = "Adverse Drug Reaction"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
        showLongToast(mobile_no.toString())

        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            et_date_of_start.setText(date1)
        }
        et_date_of_start.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        loadDetails()
        btn_adverse_drug.setOnClickListener{
            assignValuestoVariable()
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
            newDrug.mobile_no = mobile_no!!
            val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
            val requestCall = diseaseService.addDrug(newDrug)
            requestCall.enqueue(object : Callback<Diseases> {
                /**
                 * Invoked when a network exception occurred talking to the server or when an unexpected
                 * exception occurred creating the request or processing the response.
                 */
                override fun onResponse(call: Call<Diseases>, resp: Response<Diseases>) {
                    if (resp.isSuccessful) {
                        var newbody = resp.body() // Use it or ignore it
                        Toast.makeText(applicationContext, "Successfully Added"+newbody, Toast.LENGTH_SHORT).show()
                        //  finish()
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
    private fun loadDetails() {
        btn_adverse_drug_updated.visibility = View.VISIBLE
        btn_adverse_drug.visibility = View.GONE
        val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
        val requestCall = diseaseService.getDrug(mobile_no!!)
        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>> {
            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>> ) {
                val destination = response.body()
                val newDrug = destination?.first()
                newDrug?.let {
                    et_drug_Name?.setText(newDrug.drugname)
                    reaction_effect.setText(newDrug.reaction)
                    et_date_of_start.setText(newDrug.date_of_start)
                    et_treatment_taken.setText(newDrug.treatment_taken)
                    initUpdateButton()
                }
                if (response.isSuccessful)
                {
                    Toast.makeText(this@Adverse_Drug_Reaction_Activity, "Item  Successfully", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@Adverse_Drug_Reaction_Activity  , "Failed at else part in load details", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {
                Toast.makeText(this@Adverse_Drug_Reaction_Activity , "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun initUpdateButton() {
        btn_adverse_drug_updated.setOnClickListener {
            val newDrug = Diseases()
            newDrug.drugname = et_drug_Name!!.text.toString().trim()
            newDrug.reaction = reaction_effect!!.text.toString().trim()
            newDrug.date_of_start = et_date_of_start!!.text.toString().trim()
            newDrug.treatment_taken = et_treatment_taken!!.text.toString().trim()
            newDrug.mobile_no = mobile_no!!
            val destinationService = ServiceBuilder.buildService(Disease_service::class.java)
            val requestCall = destinationService.updateadversedrug(newDrug)
            requestCall.enqueue(object: Callback<Diseases> {
                override fun onResponse(call: Call<Diseases>, response: Response<Diseases>) {
                    if (response.isSuccessful)
                    {
                        Toast.makeText(this@Adverse_Drug_Reaction_Activity, "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this@Adverse_Drug_Reaction_Activity  , "Failed at else part in update", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Diseases>, t: Throwable) {
                    Toast.makeText(this@Adverse_Drug_Reaction_Activity, "Failed to update item", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    private fun assignValuestoVariable() {
        val drugName = et_drug_Name.text.toString()
        validateInput(et_drug_Name, drugName)
        if ((drugName != "") ) {
            showLongToast("save the details")
        }
        else {
            showLongSnackBar("Please fill the required fields")
        }
    }
}