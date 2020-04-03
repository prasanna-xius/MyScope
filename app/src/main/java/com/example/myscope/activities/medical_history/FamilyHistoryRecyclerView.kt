package com.example.myscope.activities.medical_history

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_family_history_recycler_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FamilyHistoryRecyclerView : BaseActivity() {

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_family_history_recycler_view)


        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
        showLongToast(mobile_no.toString())

        fab_btn_familyHistory.setOnClickListener {

            val intent = Intent(this, Family_History::class.java)
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

        val requestCall = destinationService.getFamily(mobile_no.toString())
        ///service file method called (binding)



        requestCall.enqueue(object: Callback<List<Diseases>> {

            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>>) {

                if (response.isSuccessful()) {
                    // Your status code is in the range of 200's
                    val familyConditionList = response.body()!!

                    //linearLayoutManager = LinearLayoutManager(this@FamilyHistoryRecyclerView,
                    // LinearLayoutManager.VERTICAL, false)
                    //destiny_recycler_view.adapter = FamilyAdaptor(familyConditionList)
                    val linearlayoutmanager = LinearLayoutManager(applicationContext)
                    linearlayoutmanager.orientation = LinearLayoutManager.VERTICAL
                    family_recycler_view.setLayoutManager(linearlayoutmanager)
                    family_recycler_view.adapter = FamilyAdaptor(familyConditionList)
                    family_recycler_view.adapter!!.notifyDataSetChanged()


                    Log.e("errpr msg resp succ",response.message())

                } else if(response.code() == 401) {
                    Toast.makeText(this@FamilyHistoryRecyclerView, "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@FamilyHistoryRecyclerView, "Failed to retrieve items123", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {

                Toast.makeText(this@FamilyHistoryRecyclerView, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}
