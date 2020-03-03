package com.example.myscope.activities.medical_history

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.MultiSelectionSpinner
import com.example.myscope.activities.services.ServiceBuilder
import com.example.myscope.activities.services.Social_service
import kotlinx.android.synthetic.main.activity_social_history_update.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialHistoryUpdate : BaseActivity() {

    var spinnersmokingUpdated: Spinner? = null
    var spinnerdrinkingUpdated: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_history_update)
        showToolbar()
        header!!.text = "Social History"


        val spinnersmokingUpdated = findViewById<Spinner>(R.id.spinner_smokingUpdated)
        val spinnerdrinkingUpdated = findViewById<Spinner>(R.id.spinner_drinkingUpdated)
        val tobaccoUsageUpdated = findViewById<MultiSelectionSpinner>(R.id.tobacco_usageUpdated)

        tobaccoUsageUpdated.setItems(resources.getStringArray(R.array.tobacco_array))


        val smokingAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.smoking_arrays))
        spinnersmokingUpdated!!.adapter = smokingAdapter

        val drinkingAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.drinking_arrays))
        spinnerdrinkingUpdated!!.adapter = drinkingAdapter

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id: String = intent.getStringExtra(ARG_ITEM_ID).toString()

            val position: Int? = intent.getIntExtra("position", 0)
            showLongToast(position.toString())

            loadDetails(id, position!!)

            initUpdateButton(id)

            //initDeleteButton(id)

        }
    }

    private fun loadDetails(id: String , position : Int) {


//val filter = HashMap<String , String>()()
        val socialService = ServiceBuilder.buildService(Social_service::class.java)
        val requestCall = socialService.getSocialHabits(id)

        showLongToast(requestCall.toString())


        requestCall.enqueue(object : retrofit2.Callback<List<SocialHabits>> {

            override fun onResponse(call: Call<List<SocialHabits>>, response: Response<List<SocialHabits>>) {

                if (response.isSuccessful) {
                    val destination = response.body()
                    val socialHabits = destination?.get(position)

                    socialHabits?.let {
                        spinner_smokingUpdated.text1.setText(socialHabits.smoking)
                        et_smoking_yrsUpdated.setText(socialHabits.smoking_duration)!!
                        tobacco_usageUpdated.text1.setText(socialHabits.tobacco_usage)
                        spinner_drinkingUpdated.text1.setText(socialHabits.drinking)
                        et_YrsOfDrinkingUpdated.setText(socialHabits.drinking_duration)

                    }!!
                } else {
                    Toast.makeText(this@SocialHistoryUpdate, "Failed to retrieve details", Toast.LENGTH_SHORT)
                            .show()
                }
            }

            override fun onFailure(call: Call<List<SocialHabits>>, t: Throwable) {
                Toast.makeText(
                        this@SocialHistoryUpdate, "Failed to retrieve details1 " + t.toString(),
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initUpdateButton(id: String) {

        btn_socialUpdated.setOnClickListener {

            val smoking = spinner_smokingUpdated.text1.text.toString()
            val smokingDurationUpdate = et_smoking_yrsUpdated.text.toString()
            val tobaccoUsageUpdate = tobacco_usageUpdated.text1.text.toString()
            val drinking = spinner_drinkingUpdated.text1.text.toString()
            val drinkingDurationUpdate = et_YrsOfDrinkingUpdated.text.toString()
            val mobile_no = "8142529582"


            val destinationService = ServiceBuilder.buildService(Social_service::class.java)
            val requestCall = destinationService.updateSocialHabits(mobile_no )

            requestCall.enqueue(object: Callback<SocialHabits> {

                override fun onResponse(call: Call<SocialHabits>, response: Response<SocialHabits>) {
                    if (response.isSuccessful) {

                        var updatedDestination = response.body() // Use it or ignore It
                        Toast.makeText(this@SocialHistoryUpdate, "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                        finish() // Move back to DestinationListActivity
                    } else {
                        Toast.makeText(this@SocialHistoryUpdate, "Failed to update item1", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SocialHabits>, t: Throwable) {
                    Toast.makeText(this@SocialHistoryUpdate, "Failed to update item", Toast.LENGTH_SHORT).show()
                }
            })

            assignValuestoVariable()
            validate(spinnersmokingUpdated!!)
            validate(spinnerdrinkingUpdated!!)
        }


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, SocialHistoryRecyclerView::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    companion object {

        val ARG_ITEM_ID:String = "item_id"
    }

    private fun assignValuestoVariable() {

        var smoking = spinnersmokingUpdated!!.selectedItem.toString()
        var drinking = spinnerdrinkingUpdated!!.selectedItem.toString()
        var tobaccoUsage = tobacco_usageUpdated!!.selectedItem.toString()

        validateSpinner(spinnersmokingUpdated!!, smoking)
        validateSpinner(spinnerdrinkingUpdated!!, drinking)
        validateSpinner(tobacco_usageUpdated!!, tobaccoUsage)

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
