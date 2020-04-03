package com.example.myscope.activities.medical_history

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myscope.R
import com.example.myscope.helpers.SurgeryAdapter
import com.example.myscope.models.MedicalHistoryModelActivity
import com.example.myscope.services.MedicalHistoryService
import com.example.myscope.services.ServiceBuilder

import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_surgery_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurgeryItemListActivity : AppCompatActivity() {


    var mobile_no:String ?= null
    var fab : FloatingActionButton?=null
    //var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surgery_list)

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        fab= findViewById(R.id.fab_surgery)
        //fab?.setBackgroundColor(Color.parseColor("#2196F3"));
        //setSupportActionBar(toolbar as Toolbar?)
        //toolbar.title = title

        fab?.setOnClickListener {

            val intent = Intent(this@SurgeryItemListActivity, SurgeryHistory_Activity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        loadDestinations()
    }

    private fun loadDestinations() {

        val surgeryService = ServiceBuilder.buildService(MedicalHistoryService::class.java)

        val filter = HashMap<String, String>()
//        filter["country"] = "India"
//        filter["count"] = "1"

       // Toast.makeText(this, "select item"+filter, Toast.LENGTH_LONG).show()


        val requestCall = surgeryService.getSurgeryByid(mobile_no.toString())

        //Toast.makeText(this, "select item"+requestCall, Toast.LENGTH_LONG).show()

        ///service file method called (binding)

        //val requestCall = destinationService.getAllergy(filter)

        requestCall.enqueue(object: Callback<List<MedicalHistoryModelActivity>> {

            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(call: Call<List<MedicalHistoryModelActivity>>, response: Response<List<MedicalHistoryModelActivity>>) {
                if (response.isSuccessful()) {
                    // Your status code is in the range of 200's
                    val surgeryList = response.body()!!
                    //linearLayoutManager = LinearLayoutManager(this@AllergyItemListActivity,
                    // LinearLayoutManager.VERTICAL, false)
                    //destiny_recycler_view.adapter = AllergyAdapter(allergyList)

                     //Toast.makeText(applicationContext,"select item"+immunList,Toast.LENGTH_LONG).show()





                    val llm = LinearLayoutManager(applicationContext)
                    llm.orientation = LinearLayoutManager.VERTICAL
                    surgery_recycler_view.setLayoutManager(llm)

                                                                                        // it works second time and later

                        surgery_recycler_view.adapter = SurgeryAdapter(surgeryList)
                    surgery_recycler_view.adapter?.notifyDataSetChanged();

                } else if(response.code() == 401) {
                    Toast.makeText(this@SurgeryItemListActivity,
                            "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@SurgeryItemListActivity, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<MedicalHistoryModelActivity>>, t: Throwable) {

                Toast.makeText(this@SurgeryItemListActivity, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}

