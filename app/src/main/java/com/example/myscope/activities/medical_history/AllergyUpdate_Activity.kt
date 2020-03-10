package com.example.myscope.activities.medical_history

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myscope.R
import com.example.myscope.helpers.ServiceBuilder
import com.example.myscope.models.Allergy
import com.example.myscope.services.AllergyService
import kotlinx.android.synthetic.main.allergies_update.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllergyUpdate_Activity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.allergies_update)

        //setSupportActionBar(detail_toolbar as Toolbar?)
        // Show the Up button in the action bar.
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            loadDetails(id)

            initUpdateButton(id)

            //initDeleteButton(id)
        }
    }

    private fun loadDetails(id: Int) {

        val allergyService = ServiceBuilder.buildService(AllergyService::class.java)
        val requestCall = allergyService.getAllergy(id)

        requestCall.enqueue(object : retrofit2.Callback<Allergy> {

            override fun onResponse(call: Call<Allergy>, response: Response<Allergy>) {

                if (response.isSuccessful) {
                    val destination = response.body()
                    destination?.let {
                        et_name_update.setText(destination.name)
                        et_reaction_update.setText(destination.reaction)
                        et_treatment_update.setText(destination.treatment)
                        et_name_update.setText(destination.notes)
                        //spinnerAllergy_update.getString(destination.spinerdata)

                        //collapsing_toolbar.title = destination.city
                    }!!
                } else {
                    Toast.makeText(this@AllergyUpdate_Activity, "Failed to retrieve details", Toast.LENGTH_SHORT)
                            .show()
                }
            }

            override fun onFailure(call: Call<Allergy>, t: Throwable) {
                Toast.makeText(
                        this@AllergyUpdate_Activity,
                        "Failed to retrieve details " + t.toString(),
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initUpdateButton(id: Int) {

        btn_update.setOnClickListener {

            val nameUpdate = et_name_update.text.toString()
            val reactionUpdate = et_reaction_update.text.toString()
            val treatmentUpdate = et_treatment_update.text.toString()
            val notesUpdate = et_notes_allergies_update.text.toString()
            val dateobservedUpdate = first_observed_update.text.toString()

            val destinationService = ServiceBuilder.buildService(AllergyService::class.java)
            val requestCall = destinationService.updateAllergy(id, nameUpdate,reactionUpdate, treatmentUpdate,
                    notesUpdate,dateobservedUpdate)

            requestCall.enqueue(object: Callback<Allergy> {

                override fun onResponse(call: Call<Allergy>, response: Response<Allergy>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        var updatedDestination = response.body() // Use it or ignore It
                        Toast.makeText(this@AllergyUpdate_Activity,
                                "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AllergyUpdate_Activity,
                                "Failed to update item", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Allergy>, t: Throwable) {
                    Toast.makeText(this@AllergyUpdate_Activity,
                            "Failed to update item", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun initDeleteButton(id: Int) {

        /* btn_delete.setOnClickListener {

             val destinationService = ServiceBuilder.buildService(AllergyService::class.java)
             val requestCall = destinationService.deleteAllergy(id)

             requestCall.enqueue(object: Callback<Unit> {

                 override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                     if (response.isSuccessful) {
                         finish() // Move back to DestinationListActivity
                         Toast.makeText(this@AllergyUpdateActivity, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                     } else {
                         Toast.makeText(this@AllergyUpdateActivity, "Failed to Delete", Toast.LENGTH_SHORT).show()
                     }
                 }

                 override fun onFailure(call: Call<Unit>, t: Throwable) {
                     Toast.makeText(this@AllergyUpdateActivity, "Failed to Delete", Toast.LENGTH_SHORT).show()
                 }
             })
         }*/
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, AllergyItemListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}






