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
import com.example.myscope.activities.services.FamilyConditionService
import com.example.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_family_history_updated.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FamilyHistoryUpdated : BaseActivity() {


    var relationshipSpinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_family_history_updated)

        showToolbar()
        header!!.text = "Family History"

        val relationshipSpinnerUpdated = findViewById<Spinner>(R.id.spinner_familyUpdated)

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.Relationship_arrays))
        relationshipSpinnerUpdated!!.adapter = adapter


        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id : String = intent.getStringExtra(ARG_ITEM_ID)

            val position : Int? = intent.getIntExtra("position" , 0)
            showLongToast(position.toString())

            loadDetails(id , position!!)

            initUpdateButton(id)

            //initDeleteButton(id)
        }

    }


    private fun loadDetails(id: String , position : Int) {


//val filter = HashMap<String , String>()()
        val familyService = ServiceBuilder.buildService(FamilyConditionService::class.java)
        val requestCall = familyService.getFamilyCondition(id)

        showLongToast(requestCall.toString())


        requestCall.enqueue(object : retrofit2.Callback<List<FamilyCondition>> {

            override fun onResponse(call: Call<List<FamilyCondition>>, response: Response<List<FamilyCondition>>) {

                if (response.isSuccessful) {
                    val destination = response.body()
                    val familyCondition = destination?.get(position)

                    familyCondition?.let {
                        et_family_conditionUpdated.setText(familyCondition.familyCondition)
                        spinner_familyUpdated.text1.setText(familyCondition.relationship)!!
                        relationship_notesUpdated.setText(familyCondition.relationship_notes)

                    }!!
                } else {
                    Toast.makeText(this@FamilyHistoryUpdated, "Failed to retrieve details", Toast.LENGTH_SHORT)
                            .show()
                }
            }

            override fun onFailure(call: Call<List<FamilyCondition>>, t: Throwable) {
                Toast.makeText(
                        this@FamilyHistoryUpdated, "Failed to retrieve details1 " + t.toString(),
                        Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initUpdateButton(id: String) {

        btn_familyUpdated.setOnClickListener {

            val family_condition = et_family_conditionUpdated.text.toString()
            val relationship = spinner_familyUpdated.text1.text.toString()
            val relationshipNotes = relationship_notesUpdated.text.toString()
            val mobile_no = "8142529582"

            val destinationService = ServiceBuilder.buildService(FamilyConditionService::class.java)
            val requestCall = destinationService.updateFamily(mobile_no )

            requestCall.enqueue(object: Callback<FamilyCondition> {

                override fun onResponse(call: Call<FamilyCondition>, response: Response<FamilyCondition>) {
                    if (response.isSuccessful) {

                        var updatedDestination = response.body() // Use it or ignore It
                        Toast.makeText(this@FamilyHistoryUpdated, "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                        finish() // Move back to DestinationListActivity
                    } else {
                        Toast.makeText(this@FamilyHistoryUpdated, "Failed to update item1", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<FamilyCondition>, t: Throwable) {
                    Toast.makeText(this@FamilyHistoryUpdated, "Failed to update item", Toast.LENGTH_SHORT).show()
                }
            })

            assignValuestoVariable()
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, FamilyHistoryRecyclerView::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    companion object {

        val ARG_ITEM_ID:String = "item_id"
    }

    private fun assignValuestoVariable() {

        var familyCondition = et_family_conditionUpdated.text.toString()
        var relationship = relationshipSpinner!!.selectedItem.toString()
        validateInput(et_family_conditionUpdated,familyCondition)
        validateSpinner(relationshipSpinner!!,relationship)

        if ((familyCondition != "")&&
                (relationship != "None"))
        {

            showLongToast("save the details")

        }
        else{



        }


    }

}
