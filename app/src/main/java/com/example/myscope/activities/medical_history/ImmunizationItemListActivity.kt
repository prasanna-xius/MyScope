package com.example.myscope.activities.medical_history

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myscope.R
import com.example.myscope.activities.myscope.helpers.ImmunizationAdapter
import com.example.myscope.models.MedicalHistoryModelActivity
import com.example.myscope.services.MedicalHistoryService
import com.example.myscope.services.ServiceBuilder

import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_immunization_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImmunizationItemListActivity : AppCompatActivity() {



    var fab : FloatingActionButton?=null
    var mobile_no:String ?= null
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_immunization_list)

        fab= findViewById(R.id.fab_immun)
        //fab?.setBackgroundColor(Color.parseColor("#2196F3"));
        //setSupportActionBar(toolbar as Toolbar?)
        //toolbar.title = title

        fab?.setOnClickListener {

            val intent = Intent(this@ImmunizationItemListActivity, ImmunizationHistory::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        loadDestinations()
    }

    private fun loadDestinations() {

        val immunizationService = ServiceBuilder.buildService(MedicalHistoryService::class.java)

        val filter = HashMap<String, String>()
//        filter["country"] = "India"
//        filter["count"] = "1"

       // Toast.makeText(this, "select item"+filter, Toast.LENGTH_LONG).show()


        val requestCall = immunizationService.getImmunization(mobile_no.toString())

        //Toast.makeText(this, "select item"+requestCall, Toast.LENGTH_LONG).show()

        ///service file method called (binding)

        //val requestCall = destinationService.getAllergy(filter)

        requestCall.enqueue(object: Callback<List<MedicalHistoryModelActivity>> {

            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(call: Call<List<MedicalHistoryModelActivity>>, response: Response<List<MedicalHistoryModelActivity>>) {
                if (response.isSuccessful()) {
                    // Your status code is in the range of 200's
                    val immunList = response.body()!!
                    //linearLayoutManager = LinearLayoutManager(this@AllergyItemListActivity,
                    // LinearLayoutManager.VERTICAL, false)
                    //destiny_recycler_view.adapter = AllergyAdapter(allergyList)

                     //Toast.makeText(applicationContext,"select item"+immunList,Toast.LENGTH_LONG).show()

                    val llm = LinearLayoutManager(applicationContext)
                    llm.orientation = LinearLayoutManager.VERTICAL
                    immu_recycler_view.setLayoutManager(llm)
                    immu_recycler_view.adapter = ImmunizationAdapter(immunList)



                } else if(response.code() == 401) {
                    Toast.makeText(this@ImmunizationItemListActivity,
                            "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@ImmunizationItemListActivity, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<MedicalHistoryModelActivity>>, t: Throwable) {

                Toast.makeText(this@ImmunizationItemListActivity, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}

