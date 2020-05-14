package com.soargtechnologies.myscope.activities.medical_history

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.soargtechnologies.myscope.R

import com.soargtechnologies.myscope.models.AllergyDataClass

import com.soargtechnologies.myscope.activities.BaseActivity

import com.soargtechnologies.myscope.services.ServiceBuilder

import com.soargtechnologies.myscope.services.MedicalHistoryService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_disease_history_update.*
import kotlinx.android.synthetic.main.allergies.*
import kotlinx.android.synthetic.main.allergies_update.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_allergy.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_allergy.view.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AllergyUpdate_Activity : BaseActivity() {

    var tv: TextView? = null
    var spnrAllergy_Update: Spinner? = null
    var position: Int = 1;
    var allergyid: Int = 0
    var cal = Calendar.getInstance()
    var allergySpinner: Spinner? = null
    var buttondate_update: ImageView? = null

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.allergies_update)

        activitiesToolbar()
        header!!.text = "Allergies"
        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        //setSupportActionBar(detail_toolbar as Toolbar?)
        // Show the Up button in the action bar.
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // tv = findViewById(R.id.tv_allergy)
     //   buttondate_update = findViewById(R.id.buttondate_update)

        textviewdate_update!!.setOnClickListener {
            DatePickerDialog(this@AllergyUpdate_Activity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()

        }
        allergySpinner = findViewById<Spinner>(R.id.spinnerAllergy_update)
        val adapter2 = ArrayAdapter(this, R.layout.spinner_dropdown_item_allergy,
                resources.getStringArray(R.array.allergy_arrays))
        allergySpinner!!.adapter = adapter2

        val bundle: Bundle? = intent.extras

//        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

//            val id = intent.getStringExtra(ARG_ITEM_ID)
        position = intent.getIntExtra("position", 0)



    }
    override fun onResume() {
            super.onResume()

           // loadDestinations()


        loadDetails(mobile_no.toString(), position!!)

        initUpdateButton(mobile_no.toString())

        //initDeleteButton(id)
//        }
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
        textviewdate_update!!.text = sdf.format(cal.getTime())
    }

    private fun loadDetails(id: String, position: Int) {

        val allergyService = ServiceBuilder.buildService(MedicalHistoryService::class.java)
        val requestCall = allergyService.getAllergyByid(mobile_no.toString())

        Toast.makeText(applicationContext, "data id ::" + " " + id, Toast.LENGTH_LONG).show()

        requestCall.enqueue(object : retrofit2.Callback<List<AllergyDataClass>> {


            override fun onResponse(call: Call<List<AllergyDataClass>>, response: Response<List<AllergyDataClass>>) {


                if (response.isSuccessful) {

                    val alleryRes = response.body()

                    if (alleryRes!!.equals(null)) {

                    } else {

                        val destination = alleryRes?.get(position)

                        allergyid = destination?.allergy_id!!
                        destination.let {
                            et_name_update?.setText(destination!!.name)
                            et_reaction_update?.setText(destination!!.reaction)
                            et_treatment_update?.setText(destination!!.treatment)
                            et_notes_allergies_update?.setText(destination!!.notes)
                            textviewdate_update?.setText(destination!!.date)

                            spinnerAllergy_update.tv_allergy?.setText(destination!!.spnrdata)

  


                            //collapsing_toolbar.title = destination.city
                        }!!


                        Toast.makeText(applicationContext, "sucess::" + " " + destination.toString(), Toast.LENGTH_LONG).show()
                    }

                } else {
                    Toast.makeText(this@AllergyUpdate_Activity, "Failed to retrieve details under else", Toast.LENGTH_SHORT)
                            .show()
                }
            }

            override fun onFailure(call: Call<List<AllergyDataClass>>, t: Throwable) {
                Toast.makeText(
                        this@AllergyUpdate_Activity,
                        "Failed to retrieve details " + t.toString(),
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun initUpdateButton(id: String) {

        btn_update.setOnClickListener {

            if (validate(et_name_update, true)){

            val item = spinnerAllergy_update.tv_allergy.text.toString()
            //val et_name = et_name_update.text.toString()


            val newAllergyupdate = AllergyDataClass()



            if (!item.equals(null)) {
                newAllergyupdate.spnrdata = item
            } else {
                newAllergyupdate.spnrdata = spinnerAllergy_update?.getSelectedItem().toString()
                tv_allergy.setText(newAllergyupdate.spnrdata)

            }



                newAllergyupdate.name = et_name_update.text.toString().trim()
            newAllergyupdate.reaction = et_reaction_update.text.toString().trim()
            newAllergyupdate.treatment = et_treatment_update.text.toString().trim()
            newAllergyupdate.notes = et_notes_allergies_update.text.toString().trim()
            newAllergyupdate.date = textviewdate_update.text.toString().trim()
            //newAllergyupdate.date = textviewdate_update.text.toString().trim()
            newAllergyupdate.mobile_no = mobile_no!!
            newAllergyupdate.allergy_id = allergyid
            newAllergyupdate.allergy_updated_on = datesetvalue()
            // newAllergy.spnrdata =spnritem!


            val destinationService = ServiceBuilder.buildService(MedicalHistoryService::class.java)
            val requestCall = destinationService.updateAllergy(newAllergyupdate)

            requestCall.enqueue(object : Callback<AllergyDataClass> {

                override fun onResponse(call: Call<AllergyDataClass>, response: Response<AllergyDataClass>) {
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

                override fun onFailure(call: Call<AllergyDataClass>, t: Throwable) {
                    Toast.makeText(this@AllergyUpdate_Activity,
                            "Failed to update item", Toast.LENGTH_SHORT).show()
                }
            })

        }
        }


    }

    private fun validate(et_name_update: EditText, b:Boolean):Boolean {
        if(et_name_update.text.toString().isEmpty()){
            //et_name_immuupdate.setError("")
            val snack = Snackbar.make(findViewById(R.id.llallergyupdate)!!, "Mandatory field cannot be left blank.", Snackbar.LENGTH_LONG)
            snack.show()
            return false

        }
        return true
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
            navigateUpTo(Intent(this, AllergyItemListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}






