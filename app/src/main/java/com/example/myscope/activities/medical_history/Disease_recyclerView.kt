package com.example.myscope.activities.medical_history


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
//import com.google.android.gms.common.api.Response
//import com.example.myscope.activities.Service.DiseaseService
//import com.example.myscope.activities.Service.Disease_serviceBuilder
import kotlinx.android.synthetic.main.activity_disease_recycler_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Disease_recyclerView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_recycler_view)


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

    val filter = HashMap<String, String>()
//        filter["country"] = "India"
//        filter["count"] = "1"

    val requestCall = destinationService.getDiseaseList(filter)          ///service file method called (binding)

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


               Log.e("errpr msg resp succ",response.message())

            } else if(response.code() == 401) {
                Toast.makeText(this@Disease_recyclerView, "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
            } else { // Application-level failure
                // Your status code is in the range of 300's, 400's and 500's
                Toast.makeText(this@Disease_recyclerView, "Failed to retrieve items123", Toast.LENGTH_LONG).show()
            }
        }

        // Invoked in case of Network Error or Establishing connection with Server
        // or Error Creating Http Request or Error Processing Http Response
        override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {

            Toast.makeText(this@Disease_recyclerView, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
        }
    })
}


    }


