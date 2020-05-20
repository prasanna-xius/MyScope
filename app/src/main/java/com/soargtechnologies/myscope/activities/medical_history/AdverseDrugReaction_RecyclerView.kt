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
import com.soargtechnologies.myscope.helpers.AdverseDrugAdaptor
import kotlinx.android.synthetic.main.activity_adverse_drug_reaction__recycler_view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdverseDrugReaction_RecyclerView : BaseActivity() {

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adverse_drug_reaction__recycler_view)

        activitiesToolbar()
        header!!.text = "AdverseDrug Reaction"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
  //      showLongToast(mobile_no.toString())

        fab_btn_adverseDrug.setOnClickListener {

            val intent = Intent(this, Adverse_Drug_Reaction_Activity::class.java)
            startActivity(intent)

        }
    }

    override fun onResume() {
        super.onResume()

        loadValues()
    }

    private fun loadValues() {

        val destinationService = ServiceBuilder.buildService(Disease_service::class.java)

//        filter["country"] = "India"
//        filter["count"] = "1"

        val requestCall = destinationService.getDrug(mobile_no.toString())
        ///service file method called (binding)



        requestCall.enqueue(object: Callback<List<Diseases>> {

            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>>) {


                //Log.e("errpr msg resp",response.message())

                //Log.d("errpr msg resp",response.message())


                if (response.isSuccessful()) {
                    // Your status code is in the range of 200's
                    val adverseDrugList = response.body()!!

                    //linearLayoutManager = LinearLayoutManager(this@FamilyHistoryRecyclerView,
                    // LinearLayoutManager.VERTICAL, false)
                    //destiny_recycler_view.adapter = FamilyAdaptor(familyConditionList)
                    val linearlayoutmanager = LinearLayoutManager(applicationContext)
                    linearlayoutmanager.orientation = LinearLayoutManager.VERTICAL
                    adverseDrug_recycler_view.setLayoutManager(linearlayoutmanager)
                    adverseDrug_recycler_view.adapter = AdverseDrugAdaptor(adverseDrugList)
                    adverseDrug_recycler_view.adapter!!.notifyDataSetChanged()


 //                   Log.e("errpr msg resp succ",response.message())

                } else if(response.code() == 401) {
  //                  Toast.makeText(this@AdverseDrugReaction_RecyclerView, "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
 //                   Toast.makeText(this@AdverseDrugReaction_RecyclerView, "Failed to retrieve items123", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {

 //               Toast.makeText(this@AdverseDrugReaction_RecyclerView, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}
