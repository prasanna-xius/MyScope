package com.soargtechnologies.myscope.activities.medical_history

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.helpers.MedicationAdapter
import com.soargtechnologies.myscope.models.MedicalHistoryModelActivity
import com.soargtechnologies.myscope.models.MedicationDataClass
import com.soargtechnologies.myscope.services.MedicalHistoryService
import com.soargtechnologies.myscope.services.ServiceBuilder

import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_allergy_list.*
import kotlinx.android.synthetic.main.activity_immunization_list.*
import kotlinx.android.synthetic.main.activity_medication_list.*
import kotlinx.android.synthetic.main.activity_surgery_list.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedicationItemListActivity : BaseActivity() {



    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    var fab : FloatingActionButton?=null

     lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_list)
        activitiesToolbar()

        header!!.text = "Medication History"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
        fab= findViewById(R.id.fab_medication)
        //fab?.setBackgroundColor(Color.parseColor("#2196F3"));
        //setSupportActionBar(toolbar as Toolbar?)
        //toolbar.title = title


        fab?.setOnClickListener {

            val intent = Intent(this@MedicationItemListActivity, MedicationHistory::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        loadDestinations()
    }

    public fun loadDestinations() {

        val medicationService = ServiceBuilder.buildService(MedicalHistoryService::class.java)

//        val filter = HashMap<String, String>()
//        filter["country"] = "India"
//        filter["count"] = "1"

       // Toast.makeText(this, "select item"+filter, Toast.LENGTH_LONG).show()


        val requestCall = medicationService.getMedicationByid(mobile_no.toString())

        //Toast.makeText(this, "select item"+requestCall, Toast.LENGTH_LONG).show()

        ///service file method called (binding)

        //val requestCall = destinationService.getAllergy(filter)

        requestCall.enqueue(object: Callback<List<MedicationDataClass>> {

            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(call: Call<List<MedicationDataClass>>, response: Response<List<MedicationDataClass>>) {
                if (response.isSuccessful()) {
                    // Your status code is in the range of 200's
                    val medicationList = response.body()!!
                    //linearLayoutManager = LinearLayoutManager(this@AllergyItemListActivity,
                    // LinearLayoutManager.VERTICAL, false)
                    //destiny_recycler_view.adapter = AllergyAdapter(allergyList)

                     //Toast.makeText(applicationContext,"select item"+immunList,Toast.LENGTH_LONG).show()

                    val llm = LinearLayoutManager(applicationContext)
                    llm.orientation = LinearLayoutManager.VERTICAL
                    medication_recycler_view.setLayoutManager(llm)
                    medication_recycler_view.adapter = MedicationAdapter(medicationList)
                    medication_recycler_view.adapter?.notifyDataSetChanged()


                } else if(response.code() == 401) {
                    Toast.makeText(this@MedicationItemListActivity,
                            "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@MedicationItemListActivity, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<MedicationDataClass>>, t: Throwable) {

                Toast.makeText(this@MedicationItemListActivity, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}

