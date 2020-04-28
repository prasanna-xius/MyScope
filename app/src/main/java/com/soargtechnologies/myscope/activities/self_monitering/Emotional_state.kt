package com.soargtechnologies.myscope.activities.self_monitering

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Toast
import com.google.android.gms.security.ProviderInstaller
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Self_monitoring_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_emotional_state.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.SSLContext

class Emotional_state : BaseActivity() {

    var spinner_Trauma: Spinner? = null
    var spinner_Emotional: Spinner? = null
    var boolean: Boolean? = true
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotional_state)

        activitiesToolbar()
        header!!.text = "Emotional State"

        spinner_Emotional = findViewById<Spinner>(R.id.spinner_emotional)
        spinner_Trauma = findViewById<Spinner>(R.id.spinner_trauma)

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)


       val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, resources.getStringArray(R.array.emotional_status_dropdown))
        spinner_Emotional!!.adapter = adapter

        val adaptor = ArrayAdapter(this, R.layout.spinner_dropdown_item, resources.getStringArray(R.array.reason_trauma_dropdown))
        spinner_Trauma!!.adapter = adaptor


       btn_emotionalState.setOnClickListener {
            assignValuestoVariable()
            validate(spinner_Emotional!!)
           validate(spinner_Trauma!!)
        }
    }

    private fun assignValuestoVariable() {

        val Score = et_score_of_wellness.text.toString()
        val Emotional = spinner_emotional!!.selectedItem.toString()
        val Trauma = spinner_trauma!!.selectedItem.toString()
        val emotional_duration = et_duration.text.toString()
        validateInput(et_score_of_wellness, Score)
        validateInput(et_duration , emotional_duration)
        validateSpinner(spinner_emotional!!, Emotional)
        validateSpinner(spinner_trauma!!, Trauma)

        if ((Score != "") &&
                (Emotional != "None")  &&
                (Trauma != "None")
                && (emotional_duration != "")) {
            //   showLongToast("save the details")
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



        val newEmotionalStatus = Self_dataClass()
        newEmotionalStatus.score_of_wellness = et_score_of_wellness!!.text.toString().trim()
        newEmotionalStatus.emotional_status = spinner_emotional?.getSelectedItem().toString()
        newEmotionalStatus.duration = et_duration!!.text.toString().trim()
        newEmotionalStatus.any_emotional_trauma = spinner_trauma?.getSelectedItem().toString()
        newEmotionalStatus.reason_of_trauma = et_reason_of_trauma!!.text.toString().trim()
        newEmotionalStatus.emotional_saved_on=datesetvalue()
        newEmotionalStatus.mobile_no = mobile_no!!



        val glucoseService = ServiceBuilder.buildService(Self_monitoring_service::class.java)

        val requestCall = glucoseService.addEmotional(newEmotionalStatus)

        requestCall.enqueue(object : Callback<Self_dataClass> {

            override fun onResponse(call: Call<Self_dataClass>, resp: Response<Self_dataClass>) {

                if (resp.isSuccessful) {
                    var newbody = resp.body() // Use it or ignore it
         //           Toast.makeText(applicationContext, "Successfully Added"+newbody, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
          //          Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Self_dataClass>, t: Throwable) {
                //finish()
//                    Log.d("errormsgfailure ::", t.message)
//                    Log.e("errorunderfailure:", t.message)
        //        Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })

    }


}
