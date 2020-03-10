package com.example.myscope.activities.medical_history

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.MultiSelectionSpinner
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import com.google.android.gms.security.ProviderInstaller
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.social_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.SSLContext

class Social_History : BaseActivity() {

    var spinnersmoking: Spinner? = null
    var spinnerdrinking: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_history)

        activitiesToolbar()

        header!!.text = "Social History"

        spinnersmoking= findViewById(R.id.spinner_smoking)
        spinnerdrinking= findViewById(R.id.spinner_drinking)
        val  tobaccoUsage = findViewById<MultiSelectionSpinner>(R.id.tobacco_usage)

        tobaccoUsage.setItems(resources.getStringArray(R.array.tobacco_array))


        val smokingAdapter = ArrayAdapter(this,R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.smoking_arrays))
        spinnersmoking!!.adapter= smokingAdapter

        val drinkingAdapter = ArrayAdapter(this,R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.drinking_arrays))
        spinnerdrinking!!.adapter = drinkingAdapter


        btn_social.setOnClickListener{

            assignValuestoVariable()
            validate(spinnersmoking!!)
            validate(spinnerdrinking!!)



            try {
                ProviderInstaller.installIfNeeded(getApplicationContext());
                var sslContext: SSLContext
                sslContext = SSLContext.getInstance("TLSv1.2");
                sslContext.init(null, null, null);
                sslContext.createSSLEngine();
            }
            catch (e: Exception) {
                e.printStackTrace();
            }

            val newSocialHabits = Diseases()
            newSocialHabits.smoking = spinner_smoking!!.getSelectedItem().toString()
            newSocialHabits.smoking_duration = et_smoking_yrs?.text.toString().trim()
            newSocialHabits.tobacco = tobacco_usage!!.getSelectedItem().toString()
            newSocialHabits.alcohol = spinner_drinking!!.getSelectedItem().toString()
            newSocialHabits.alcohol_duration = et_YrsOfDrinking!!.text.toString().trim()
            newSocialHabits.mobile_no = "8142529582"

            val socialService = ServiceBuilder.buildService(Disease_service::class.java)

            val requestCall = socialService.addHabit(newSocialHabits)

            requestCall.enqueue(object : Callback<Diseases> {

                override fun onResponse(call: Call<Diseases>, resp: Response<Diseases>) {

                    if (resp.isSuccessful) {
                        var newbody = resp.body() // Use it or ignore it

                        Toast.makeText(applicationContext, "Successfully Added"+newbody, Toast.LENGTH_SHORT).show()
                        //finish()
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

    private fun assignValuestoVariable() {

        var smoking = spinnersmoking!!.selectedItem.toString()
        var drinking = spinnerdrinking!!.selectedItem.toString()
        var tobaccoUsage = tobacco_usage!!.selectedItem.toString()

        validateSpinner(spinnersmoking!!, smoking)
        validateSpinner(spinnerdrinking!!, drinking)
        validateSpinner(tobacco_usage!!, tobaccoUsage)

           if ((!smoking.equals("None")) &&
                   !drinking.equals("None") &&
                   !tobaccoUsage.equals("None"))
           {
               showLongToast("save the details")
           }
        else{
               showLongSnackBar("Please fill the required fields")
           }
    }
}
