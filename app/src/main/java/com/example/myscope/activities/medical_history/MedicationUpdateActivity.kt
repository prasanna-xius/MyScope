package com.example.curvepicture.activities

import android.annotation.TargetApi
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myscope.R
import com.example.myscope.models.MedicalHistoryModelActivity
import com.example.myscope.services.MedicalHistoryService
import com.example.myscope.services.ServiceBuilder


import kotlinx.android.synthetic.main.allergies_update.*
import kotlinx.android.synthetic.main.medicationhistory.*


import kotlinx.android.synthetic.main.medicationhistory_update.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_allergy.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_allergy.view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_dose.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_dose.view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_formulation.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_formulation.view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_how_often.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_how_often.view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_prescribed.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_prescribed.view.*
import kotlinx.android.synthetic.main.surgeryhistory_update.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MedicationUpdateActivity : AppCompatActivity() {

    var spinner_how_often_taken: MultiSelectionSpinner? = null
    var formulation: Spinner? = null
    var isprescribed: Spinner? = null
    var doseunit: Spinner? = null
    var tv_how_often_taken :TextView?=null
    var dateUpdate: String? = null
    var position: Int = 1;
    internal lateinit var myCalendar: Calendar
    var buttondate_immuupdate: Button? = null
    var medicationid: Int = 0
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medicationhistory_update)

        //setSupportActionBar(detail_toolbar as Toolbar?)
        // Show the Up button in the action bar.
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        spinner_how_often_taken = findViewById<MultiSelectionSpinner>(R.id.spinner_how_often_taken_update)



        spinner_how_often_taken!!.setItems(resources.getStringArray(R.array.how_often_taken_arrays))

        //tv_how_often_taken = findViewById(R.id.tv_how_often)

        formulation = findViewById(R.id.spinner_formulation_update)
        val adapter2 = ArrayAdapter(this, R.layout.spinner_dropdown_item_formulation,
                resources.getStringArray(R.array.formulation_arrays))
        formulation!!.adapter = adapter2

        isprescribed = findViewById(R.id.spinner_is_prescribed_update)
        val adapter3 = ArrayAdapter(this, R.layout.spinner_dropdown_item_prescribed,
                resources.getStringArray(R.array.is_prescribed_arrays))
        isprescribed!!.adapter = adapter3

        doseunit = findViewById(R.id.spinner_dose_unit_update)
        val adapter4 = ArrayAdapter(this, R.layout.spinner_dropdown_item_dose,
                resources.getStringArray(R.array.dose_unit_arrays))
        doseunit!!.adapter = adapter4




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

        val medService = ServiceBuilder.buildService(MedicalHistoryService::class.java)
        val requestCall = medService.getMedicationByid(id)


        //Toast.makeText(this,"test msg "+ id,Toast.LENGTH_LONG).show()

        requestCall.enqueue(object : retrofit2.Callback<List<MedicalHistoryModelActivity>> {




            override fun onResponse(call: Call<List<MedicalHistoryModelActivity>>, response: Response<List<MedicalHistoryModelActivity>>) {

                if (response.isSuccessful) {




                    val medRes = response.body()

                    val medicationdestination = medRes?.get(position)
                    medicationid = medicationdestination?.medication_id!!


                    medicationdestination?.let {

                        //tv_how_often?.setText()

                        tv_how_often?.setText(medicationdestination.how_often_taken)
                        tv_formulation?.setText(medicationdestination.formulation)
                        tv_precsribed?.setText(medicationdestination.isprescribed)
                       tv_dose?.setText(medicationdestination.doseunit)
                        et_name_medication_update.setText(medicationdestination.medicationname)
                        et_notes_medication_update.setText(medicationdestination.medicationnotes)
                        et_reason_update.setText(medicationdestination.reason)
                        et_dose_strength_update.setText(medicationdestination.strength)
                        textviewEnddate_MH_update.setText(medicationdestination.enddate)
                        textviewStartdate_medicalHistory_update.setText(medicationdestination.startdate)

                        //text1.setText(destination.spnrdata)



                        //collapsing_toolbar.title = destination.city
                    }!!
                } else {
                    Toast.makeText(this@MedicationUpdateActivity, "Failed to retrieve details under else", Toast.LENGTH_SHORT)
                            .show()
                }
            }

            override fun onFailure(call: Call<List<MedicalHistoryModelActivity>>, t: Throwable) {
                Toast.makeText(
                        this@MedicationUpdateActivity,
                        "Failed to retrieve details " + t.toString(),
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initUpdateButton(id: String) {

        btn_medication_update.setOnClickListener {

            //validateDate(textviewStartdate_medicalHistory_update!!, textviewEnddate_MH_update!!,true)

            //val item = spinner_is_prescribed_update.tv_precsribed.text.toString()


            val newmedication = MedicalHistoryModelActivity()

            //if (validateDate(textviewStartdate_medicalHistory_update!!, textviewEnddate_MH_update!!,true))





            newmedication.how_often_taken = spinner_how_often_taken_update!!.selectedStrings.toString()
            newmedication.medicationname = et_name_medication_update!!.text.toString().trim()
            newmedication.medicationnotes = et_notes_medication_update!!.text.toString().trim()
            newmedication.reason = et_reason_update!!.text.toString().trim()
            newmedication.strength= et_dose_strength_update!!.text.toString().trim()
            newmedication.isprescribed=spinner_is_prescribed_update?.getSelectedItem().toString()
            newmedication.doseunit=spinner_dose_unit_update?.getSelectedItem().toString()
            newmedication.formulation=spinner_formulation_update?.getSelectedItem().toString()
            newmedication.startdate = textviewStartdate_medicalHistory_update!!.text.toString().trim()
            newmedication.enddate = textviewEnddate_MH_update!!.text.toString().trim()
            newmedication.mobile_no = "8142529582"
            newmedication.medication_id= medicationid


            val destinationService = ServiceBuilder.buildService(MedicalHistoryService::class.java)


            val requestCall = destinationService.updateMedication(newmedication)

            requestCall.enqueue(object: Callback<MedicalHistoryModelActivity> {




                override fun onResponse(call: Call<MedicalHistoryModelActivity>, response: Response<MedicalHistoryModelActivity>) {

                    Log.d("tag:::::",response.toString())
                    Log.d("tag:::::",response.message())

                    var updatedDestination = response.body()
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        var updatedDestination = response.body() // Use it or ignore It
                        Toast.makeText(this@MedicationUpdateActivity,
                                "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MedicationUpdateActivity,
                                "Failed to update item under else", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MedicalHistoryModelActivity>, t: Throwable) {
                    Toast.makeText(this@MedicationUpdateActivity,
                            "Failed to update item", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    /*private fun validateDate(textviewStartdate_medicalHistory_update: TextView, textviewEnddate_MH_update: TextView, b: Boolean): Boolean {
        if (!textviewStartdate_medicalHistory_update.text.toString().equals("") && !textviewEnddate_MH_update.text.toString().equals("")) {
            textviewStartdate_medicalHistory_update.setCompoundDrawables(null, null, null, null)
            textviewEnddate_MH_update.setCompoundDrawables(null, null, null, null)
            val startDate0 = SimpleDateFormat("dd-MMM-yyyy").format(Date(textviewStartdate_medicalHistory_update.text.toString()))
            val endDate0 = SimpleDateFormat("dd-MMM-yyyy").format(Date(textviewEnddate_MH_update.text.toString()))
            if (startDate0 > endDate0) {
                // date in text view is current date
                textviewEnddate_MH_update.setText("")
                //showLongSnackBar("Start date cannot be after end date")
                textviewEnddate_MH_update!!.setError("Wrong Date Selected")
                return false;
            }
        } else {
            //errorDisplayTextview(startDate)
            // errorDisplayTextview(stopDate)
            return false
        }
        return true
    }*/

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
            navigateUpTo(Intent(this, SurgeryItemListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}