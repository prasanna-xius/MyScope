package com.soargtechnologies.myscope.activities.medical_history

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Disease_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_family_history_updated.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FamilyHistoryUpdated : BaseActivity() {

    var relationshipSpinner: Spinner? = null
    var familyid: Int = 0
    var family: Diseases? = null

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_family_history_updated)

        activitiesToolbar()
        header!!.text = "Family History"

        val relationshipSpinnerUpdated = findViewById<Spinner>(R.id.spinner_familyUpdated)
        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.Relationship_arrays))
        relationshipSpinnerUpdated!!.adapter = adapter


        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

//            val id: String = intent.getStringExtra(ARG_ITEM_ID)

            val position: Int? = intent.getIntExtra("position", 0)
     //       showLongToast(position.toString())

            loadDetails(mobile_no.toString(), position!!)

            initUpdateButton(mobile_no.toString())


        }

    }


    private fun loadDetails(id: String, position: Int) {


//val filter = HashMap<String , String>()()
        val familyService = ServiceBuilder.buildService(Disease_service::class.java)
        val requestCall = familyService.getFamily(id)

  //      showLongToast(requestCall.toString())


        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>> {

            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>>) {

                if (response.isSuccessful) {
                    val destination = response.body()
                    if (destination!!.equals(null)) {

                    } else {
                        family = destination?.get(position)
                        familyid = family?.family_id!!
              //          showLongToast(familyid.toString())

                        family?.let {
                            et_family_conditionUpdated.setText(family!!.family_condition)
                            text1.setText(family!!.relationship)
                            relationship_notesUpdated.setText(family!!.family_note)

                        }!!

                    }
                }else {
                //        Toast.makeText(this@FamilyHistoryUpdated, "Failed to retrieve details", Toast.LENGTH_SHORT).show()
                    }
                }
            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {
        //        Toast.makeText(this@FamilyHistoryUpdated, "Failed to retrieve details1 " + t.toString(),Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initUpdateButton(id: String) {

        btn_familyUpdated.setOnClickListener {

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
      //  var relationship = spinner_familyUpdated!!.selectedItem.toString()
        validateInput(et_family_conditionUpdated,familyCondition)
        //validateSpinner(spinner_familyUpdated!!,relationship)

        if ((familyCondition != ""))
        {

       //     showLongToast("save the details")
            sucess()

        }
        else{

        }

            }

    private fun sucess() {

        val item = spinner_familyUpdated.text1.text.toString()

        val newFamily = Diseases()
        newFamily.family_condition = et_family_conditionUpdated.text.toString()

        if(!item.equals(null)) {
            newFamily.relationship = item
        } else {
            newFamily.relationship = spinner_familyUpdated?.getSelectedItem().toString()
            text1.setText(newFamily.relationship)
        }
        newFamily.family_note = relationship_notesUpdated.text.toString()
        newFamily.family_updated_on = datesetvalue()
        newFamily.mobile_no = mobile_no!!
        newFamily.family_id = familyid


        val destinationService = ServiceBuilder.buildService(Disease_service::class.java)
        val requestCall = destinationService.updateFamily(newFamily)

        requestCall.enqueue(object: Callback<Diseases> {

            override fun onResponse(call: Call<Diseases>, response: Response<Diseases>) {
                if (response.isSuccessful) {

                    var updatedDestination = response.body() // Use it or ignore It
             //       Toast.makeText(this@FamilyHistoryUpdated, "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                    finish() // Move back to DestinationListActivity
                } else {
           //         Toast.makeText(this@FamilyHistoryUpdated, "Failed to update item1", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Diseases>, t: Throwable) {
       //         Toast.makeText(this@FamilyHistoryUpdated, "Failed to update item", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
