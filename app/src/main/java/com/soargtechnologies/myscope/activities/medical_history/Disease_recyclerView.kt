package com.soargtechnologies.myscope.activities.medical_history


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Disease_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import com.soargtechnologies.myscope.helpers.Disease_Adaptor
//import com.google.android.gms.common.api.Response
//import com.soargtechnologies.myscope.activities.Service.DiseaseService
//import com.soargtechnologies.myscope.activities.Service.Disease_serviceBuilder
import kotlinx.android.synthetic.main.activity_disease_recycler_view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Disease_recyclerView : BaseActivity() {
    var sharedpreferences: SharedPreferences? = null
    var mobile_no: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_recycler_view)
        activitiesToolbar()

        header!!.text = "Disease History"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
        fab_btn.setOnClickListener {

            val intent = Intent(this, Disease_History::class.java)
            startActivity(intent)

        }
    }


override fun onResume() {
    super.onResume()

    loadValues()
}

private fun loadValues() {

    val destinationService = ServiceBuilder.buildService(Disease_service::class.java)

    val requestCall = destinationService.getdisease(mobile_no.toString())
    ///service file method called (binding)

    //val requestCall = destinationService.getAllergy(filter)

    requestCall.enqueue(object: Callback<List<Diseases>> {
        /**
         * Invoked when a network exception occurred talking to the server or when an unexpected
         * exception occurred creating the request or processing the response.
         */


        // If you receive a HTTP Response, then this method is executed
        // Your STATUS Code will decide if your Http Response is a Success or Error
        override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>>) {


            //Log.e("errpr msg resp",response.message())

            //Log.d("errpr msg resp",response.message())


           if (response.isSuccessful()) {
                // Your status code is in the range of 200's
                val diseaseList = response.body()!!

                //linearLayoutManager = LinearLayoutManager(this@AllergyItemListActivity,
                // LinearLayoutManager.VERTICAL, false)
                //destiny_recycler_view.adapter = AllergyAdapter(diseaseList)
                val linearlayoutmanager = LinearLayoutManager(applicationContext)
                linearlayoutmanager.orientation = LinearLayoutManager.VERTICAL
                disease_recycler_view.setLayoutManager(linearlayoutmanager)
                disease_recycler_view.adapter = Disease_Adaptor(diseaseList)

               disease_recycler_view.adapter!!.notifyDataSetChanged()


   //            Log.e("errpr msg resp succ",response.message())

            } else if(response.code() == 401) {
   //             Toast.makeText(this@Disease_recyclerView, "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
            } else { // Application-level failure
                // Your status code is in the range of 300's, 400's and 500's
  //              Toast.makeText(this@Disease_recyclerView, "Failed to retrieve items123", Toast.LENGTH_LONG).show()
            }
        }

        // Invoked in case of Network Error or Establishing connection with Server
        // or Error Creating Http Request or Error Processing Http Response
        override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {

   //         Toast.makeText(this@Disease_recyclerView, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
        }
    })
}


    }


