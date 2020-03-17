package com.example.curvepicture.activities

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity



import com.example.myscope.R
import com.example.myscope.models.MedicalHistoryModelActivity
import com.example.myscope.services.MedicalHistoryService
import com.example.myscope.services.ServiceBuilder
import com.google.android.material.snackbar.Snackbar


import kotlinx.android.synthetic.main.allergies_update.*
import kotlinx.android.synthetic.main.immunizationhistory_update.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_how_often.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class ImmunizationUpdateActivity : AppCompatActivity() {

    var spnrAllergy_Update :Spinner?=null
    var nameUpdate:String ?=null
    var notesUpdate:String?=null
    var eventUpdate:String?=null
    var brandUpdate:String?=null
    var dateUpdate:String?=null
    var position: Int= 1;
    var cal = Calendar.getInstance()
    var buttondate_immuupdate:Button?=null
    var immunid:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.immunizationhistory_update)

        //setSupportActionBar(detail_toolbar as Toolbar?)
        // Show the Up button in the action bar.
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //spnrAllergy_Update = findViewById(R.id.spinnerAllergy_update)


        nameUpdate = findViewById<View>(R.id.et_name_immuupdate).toString()

        notesUpdate = findViewById<View>(R.id.et_notes_immunupdate).toString()
        eventUpdate = findViewById<View>(R.id.et_adverse_eventupdate).toString()
        brandUpdate = findViewById<View>(R.id.et_brandupdate).toString()
        dateUpdate = findViewById<View>(R.id.textviewdate_immuupdate).toString()

        buttondate_immuupdate=findViewById(R.id.buttondate_immuupdate)


        buttondate_immuupdate!!.setOnClickListener(View.OnClickListener {

            DatePickerDialog(this@ImmunizationUpdateActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()




        })


        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {


            Toast.makeText(this,"data"+ARG_ITEM_ID.toString(),Toast.LENGTH_LONG).show()

            //val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            val id : String = intent.getStringExtra(ARG_ITEM_ID)
            position = intent.getIntExtra("position" , 0)

            loadDetails(id,position)

            initUpdateButton(id)

            //initDeleteButton(id)
        }
    }

    private fun loadDetails(id: String , position : Int) {

        val immuService = ServiceBuilder.buildService(MedicalHistoryService::class.java)
        val requestCall = immuService.getImmunization(id)


        Toast.makeText(this,"test msg "+ id,Toast.LENGTH_LONG).show()

        requestCall.enqueue(object : retrofit2.Callback<List<MedicalHistoryModelActivity>> {




            override fun onResponse(call: Call<List<MedicalHistoryModelActivity>>, response: Response<List<MedicalHistoryModelActivity>>) {

                if (response.isSuccessful) {




                    val immunRes = response.body()

                    val immundestination = immunRes?.get(position)
                    immunid = immundestination?.immun_id!!


                    immundestination?.let {
                        et_name_immuupdate.setText(immundestination.immuname)
                        et_brandupdate.setText(immundestination.immubrand)
                        et_adverse_eventupdate.setText(immundestination.immuevent)
                        et_notes_immunupdate.setText(immundestination.immunotes)
                        textviewdate_immuupdate.setText(immundestination.immudate)
                        //text1.setText(destination.spnrdata)



                        //collapsing_toolbar.title = destination.city
                    }!!
                } else {
                    Toast.makeText(this@ImmunizationUpdateActivity, "Failed to retrieve details under else", Toast.LENGTH_SHORT)
                            .show()
                }
            }

            override fun onFailure(call: Call<List<MedicalHistoryModelActivity>>, t: Throwable) {
                Toast.makeText(
                        this@ImmunizationUpdateActivity,
                        "Failed to retrieve details " + t.toString(),
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        @SuppressLint("NewApi")
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
    }
    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textviewdate_immuupdate!!.text = sdf.format(cal.getTime())
    }

    private fun initUpdateButton(id: String) {

        btn_immunizationupdate.setOnClickListener {


            val newImmunization = MedicalHistoryModelActivity()



                newImmunization.immuname = et_name_immuupdate!!.text.toString().trim()
                newImmunization.immunotes = et_notes_immunupdate!!.text.toString().trim()
                newImmunization.immuevent = et_adverse_eventupdate!!.text.toString().trim()
                newImmunization.immubrand = et_brandupdate!!.text.toString().trim()
                newImmunization.immudate = textviewdate_immuupdate?.text.toString().trim()
                //newImmunization.immudate = dateUpdate.toString().trim()
                newImmunization.mobile_no = "8142529582"
                newImmunization.immun_id = immunid


                val destinationService = ServiceBuilder.buildService(MedicalHistoryService::class.java)


                val requestCall = destinationService.updateImmunization(newImmunization)

                requestCall.enqueue(object : Callback<MedicalHistoryModelActivity> {


                    override fun onResponse(call: Call<MedicalHistoryModelActivity>, response: Response<MedicalHistoryModelActivity>) {

                        Log.d("tag:::::", response.toString())
                        Log.d("tag:::::", response.message())

                        var updatedDestination = response.body()
                        if (response.isSuccessful) {
                            finish() // Move back to DestinationListActivity
                            var updatedDestination = response.body() // Use it or ignore It
                            Toast.makeText(this@ImmunizationUpdateActivity,
                                    "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@ImmunizationUpdateActivity,
                                    "Failed to update item under else", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<MedicalHistoryModelActivity>, t: Throwable) {
                        Toast.makeText(this@ImmunizationUpdateActivity,
                                "Failed to update item", Toast.LENGTH_SHORT).show()
                    }
                })


        }
    }
    private fun initDeleteButton(id: Int) {

        /* btn_delete.setOnClickListener {

             val destinationService = ServiceBuilder.buildService(MedicalHistoryService::class.java)
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
            navigateUpTo(Intent(this, ImmunizationItemListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}