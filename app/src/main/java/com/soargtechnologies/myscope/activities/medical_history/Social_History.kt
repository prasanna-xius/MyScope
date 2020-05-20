package com.soargtechnologies.myscope.activities.medical_history

import android.content.Intent
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.MultiSelectionSpinner
import com.soargtechnologies.myscope.activities.MultiSpinnerAlcohol
import com.soargtechnologies.myscope.activities.MultiSpinnerTime
import com.soargtechnologies.myscope.activities.services.Disease_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import com.google.android.gms.security.ProviderInstaller
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.prescribed_main_view.*
import kotlinx.android.synthetic.main.social_history.*
import kotlinx.android.synthetic.main.social_multi.view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.SSLContext

class Social_History : BaseActivity() {


    var tobaccoUsage: MultiSpinnerAlcohol? = null

    var spinnersmoking: Spinner? = null
    var spinnerdrinking: Spinner? = null
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_history)

        activitiesToolbar()

        header!!.text = "Social History"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        spinnersmoking= findViewById(R.id.spinner_smoking)
        spinnerdrinking= findViewById(R.id.spinner_drinking)
        tobaccoUsage = findViewById(R.id.tobacco_usage)

        tobaccoUsage!!.setItems(resources.getStringArray(R.array.tobacco_array))


        val smokingAdapter = ArrayAdapter(this,R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.smoking_arrays))
        spinnersmoking!!.adapter= smokingAdapter

        val drinkingAdapter = ArrayAdapter(this,R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.drinking_arrays))
        spinnerdrinking!!.adapter = drinkingAdapter

      //  loadDetails()


        val checkbox = ServiceBuilder.buildService(Disease_service::class.java)

        val requestCall = checkbox.getSocialHabits(mobile_no!! )

        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>>{


            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>>) {

                val length = response.body()?.size



                if (length!! == 1){


                    loadDetails()
                }
                else{
                  //  showLongToast("else part")

                    btn_social.setOnClickListener{

                        assignValuestoVariable()
                        validate(spinnersmoking!!)
                        validate(spinnerdrinking!!)



                        try {

                            ProviderInstaller.installIfNeeded(getApplicationContext());
                            var sslContext: SSLContext
                            sslContext = SSLContext.getInstance("TLSv1.2");
                            sslContext.init(null, null, null);
                            sslContext.createSSLEngine()

                        }
                        catch (e: Exception) {
                            e.printStackTrace();
                        }

                        val newSocialHabits = Diseases()
                        newSocialHabits.smoking = spinner_smoking!!.getSelectedItem().toString()
                        newSocialHabits.smoking_duration = et_smoking_yrs?.text.toString().trim()
                        newSocialHabits.tobacco = tobacco_usage!!.selectedStrings.toString()
                        newSocialHabits.alcohol = spinner_drinking!!.getSelectedItem().toString()
                        newSocialHabits.alcohol_duration = et_YrsOfDrinking!!.text.toString().trim()
                        newSocialHabits.social_saved_on = datesetvalue()
                        newSocialHabits.mobile_no = mobile_no

                        val socialService = ServiceBuilder.buildService(Disease_service::class.java)

                        val requestCall = socialService.addHabit(newSocialHabits)

                        requestCall.enqueue(object : Callback<Diseases> {

                            override fun onResponse(call: Call<Diseases>, resp: Response<Diseases>) {

                                if (resp.isSuccessful) {
                                    var newbody = resp.body() // Use it or ignore it

    //                                Toast.makeText(applicationContext, "Successfully Added"+newbody, Toast.LENGTH_SHORT).show()
                                   finish()
                                }
                                else {
   //                                 Toast.makeText(applicationContext, "Failed at posting data.", Toast.LENGTH_SHORT).show()
                                }
                            }
                            override fun onFailure(call: Call<Diseases>, t: Throwable) {
                                //finish()
//                    Log.d("errormsgfailure ::", t.message)
//                    Log.e("errorunderfailure:", t.message)
     //                           Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
                            }



                        })
                    }



                }
            }

            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {

  //              showLongToast("failure part")
            }
        })







    }


    private fun loadDetails() {




//        val alcoholtaken = tobaccoUsage!!.social_multi.text.toString()

        val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
        val requestCall = diseaseService.getSocialHabits(mobile_no!!)
//        var timeitem = tobaccoUsage!!.social_multi.text.toString()

        progressbar()


        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>> {

            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>> ) {

                val destination = response.body()

                if(destination!!.isEmpty()){
                    btn_social_updated.visibility = View.GONE
                    btn_social.visibility = View.VISIBLE

                } else{

                val social = destination?.first()
//                Log.d("resp:", response.toString())
//                Log.e("resp:", response.toString())


                social?.let {

                    //   val timeitem = tobaccoUsage!!.social_multi.text.toString()
                    spinnersmoking?.text1?.setText(social.smoking)

                    et_smoking_yrs.setText(social.smoking_duration)

                    spinner_drinking?.text1?.setText(social.alcohol)

                    tobaccoUsage!!.social_multi?.setText(social.tobacco)

//


                    et_YrsOfDrinking.setText(social.alcohol_duration)

                    initUpdateButton()
                    btn_social_updated.visibility = View.VISIBLE
                    btn_social.visibility = View.GONE

                }
                }

            }
            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {
    //            Toast.makeText(this@Social_History , "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initUpdateButton() {

        btn_social_updated.setOnClickListener {


            //newSocialHabits.tobacco = tobacco_usage!!.selectedStrings.toString()


            val item = spinner_smoking.text1.text.toString()
            val item1 = tobacco_usage.social_multi.text.toString()
            val item2 = spinner_drinking.text1.text.toString()


            val newSocialHabits = Diseases()

            if(!item.equals(null)) {
                newSocialHabits.smoking = item
            } else {
                newSocialHabits.smoking = spinner_smoking?.getSelectedItem().toString()
                text1.setText(newSocialHabits.smoking)
            }

            if(!item1.equals(null)) {
                newSocialHabits.tobacco = item1
            } else {
                newSocialHabits.tobacco = tobacco_usage!!.selectedStrings.toString()
                text1.setText(newSocialHabits.tobacco)
            }
            if(!item2.equals(null)) {
                newSocialHabits.alcohol = item2
            } else {
                newSocialHabits.alcohol = spinner_smoking?.getSelectedItem().toString()
                text1.setText(newSocialHabits.alcohol)
            }

            newSocialHabits.smoking_duration = et_smoking_yrs!!.text.toString().trim()
            newSocialHabits.alcohol_duration = et_YrsOfDrinking!!.text.toString().trim()
            newSocialHabits.social_updated_on = datesetvalue()
            newSocialHabits.mobile_no =mobile_no!!


            val destinationService = ServiceBuilder.buildService(Disease_service::class.java)
            val requestCall = destinationService.updateHabit(newSocialHabits)

            requestCall.enqueue(object: Callback<Diseases> {

                override fun onResponse(call: Call<Diseases>, response: Response<Diseases>) {
                    val resp = response
                    if (response.isSuccessful)
                    {

  //                      Toast.makeText(this@Social_History, "Item Updated Successfully", Toast.LENGTH_SHORT).show()

                    }
                    else {

  //                      Toast.makeText(this@Social_History  , "Failed to update item1", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Diseases>, t: Throwable) {
         //           Toast.makeText(this@Social_History, "Failed to update item", Toast.LENGTH_SHORT).show()
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
   //            showLongToast("save the details")
           }
        else{
               showLongSnackBar("Please fill the required fields")
           }
    }
}
