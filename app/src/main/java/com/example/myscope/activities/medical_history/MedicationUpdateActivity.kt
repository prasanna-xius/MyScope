package com.example.myscope.activities.medical_history

import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.MultiSelectionSpinner
import com.example.myscope.models.MedicalHistoryModelActivity
import com.example.myscope.models.MedicationDataClass
import com.example.myscope.services.MedicalHistoryService
import com.example.myscope.services.ServiceBuilder

import kotlinx.android.synthetic.main.medicationhistory.*

import kotlinx.android.synthetic.main.app_bar_main.*


import kotlinx.android.synthetic.main.medicationhistory_update.*
import kotlinx.android.synthetic.main.prescribed_main_view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_dose.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_dose.view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_formulation.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_formulation.view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_how_often.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_how_often.view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_prescribed.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_prescribed.view.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MedicationUpdateActivity : BaseActivity() {

    var spinner_how_often_taken: MultiSelectionSpinnerMedication? = null

    var sdf1: SimpleDateFormat? = null
    var sdf2: SimpleDateFormat? = null
    var button_date_StartMh: ImageView? = null
    var button_date_EndMh: ImageView? = null
    var textview_Startdate: TextView? = null
    var textview_Enddate: TextView? = null
    var cal_StartMh = java.util.Calendar.getInstance()
    var cal_EndMh = java.util.Calendar.getInstance()


    var formulation: Spinner? = null
    var isprescribed: Spinner? = null
    var doseunit: Spinner? = null
    //var tv_how_often_taken :TextView?=null
    var dateUpdate: String? = null
    var position: Int = 1;

    var button_date_StartMhUpdate: ImageView? = null
    var button_date_EndMhupdate: ImageView? = null
    var adapter2: ArrayAdapter<String>? = null

    var adapter3: ArrayAdapter<String>? = null;
    var adapter4: ArrayAdapter<String>? = null
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    internal lateinit var myCalendar: Calendar
    var buttondate_immuupdate: Button? = null
    var medicationid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medicationhistory_update)

        //setSupportActionBar(detail_toolbar as Toolbar?)
        // Show the Up button in the action bar.
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activitiesToolbar()
        header!!.text = "Medication History"

        spinner_how_often_taken = findViewById<MultiSelectionSpinnerMedication>(R.id.spinner_how_often_taken_update)



        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        spinner_how_often_taken!!.setItems(resources.getStringArray(R.array.how_often_taken_arrays))

        //tv_how_often_taken = findViewById(R.id.tv_how_often)

        formulation = findViewById(R.id.spinner_formulation_update)


        adapter2 = ArrayAdapter(this, R.layout.spinner_dropdown_item_formulation,
                resources.getStringArray(R.array.formulation_arrays))
        formulation!!.adapter = adapter2


        isprescribed = findViewById(R.id.spinner_is_prescribed_update)


        adapter3 = ArrayAdapter(this, R.layout.spinner_dropdown_item_prescribed,
                resources.getStringArray(R.array.is_prescribed_arrays))
        isprescribed!!.adapter = adapter3

        doseunit = findViewById(R.id.spinner_dose_unit_update)

        adapter4 = ArrayAdapter(this, R.layout.spinner_dropdown_item_dose,
                resources.getStringArray(R.array.dose_unit_arrays))
        doseunit!!.adapter = adapter4

        button_date_StartMhUpdate = this.buttonStartdate_medicalHistory_update
        button_date_EndMhupdate = this.buttonEnddate_MH_update


        val bundle: Bundle? = intent.extras

        //if (bundle?.containsKey(ARG_ITEM_ID)!!) {


        //Toast.makeText(this,"data"+ARG_ITEM_ID.toString(),Toast.LENGTH_LONG).show()

        //val id = intent.getIntExtra(ARG_ITEM_ID, 0)

        // val id : String = intent.getStringExtra(ARG_ITEM_ID)
        position = intent.getIntExtra("position", 0)


        val dateSetListenerSatar = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view_start: DatePicker, year_start: Int, monthOfYear_start: Int,
                                   dayOfMonth_start: Int) {
                cal_StartMh.set(java.util.Calendar.YEAR, year_start)
                cal_StartMh.set(java.util.Calendar.MONTH, monthOfYear_start)
                cal_StartMh.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth_start)


                updateDateInViewStart()
            }
        }

        val dateSetListenerEnd = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view_end: DatePicker, year_end: Int, monthOfYear_end: Int,
                                   dayOfMonth_end: Int) {

                cal_EndMh.set(java.util.Calendar.YEAR, year_end)
                cal_EndMh.set(java.util.Calendar.MONTH, monthOfYear_end)
                cal_EndMh.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth_end)

                updateDateInViewEnd()


                validateDate(textviewStartdate_medicalHistory_update!!, textviewEnddate_MH_update!!, true)
            }
        }


        button_date_StartMhUpdate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@MedicationUpdateActivity,
                        dateSetListenerSatar,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal_StartMh.get(java.util.Calendar.YEAR),
                        cal_StartMh.get(java.util.Calendar.MONTH),
                        cal_StartMh.get(java.util.Calendar.DAY_OF_MONTH)).show()
            }

        })


        button_date_EndMhupdate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@MedicationUpdateActivity,
                        dateSetListenerEnd,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal_EndMh.get(java.util.Calendar.YEAR),
                        cal_EndMh.get(java.util.Calendar.MONTH),
                        cal_EndMh.get(java.util.Calendar.DAY_OF_MONTH)).show()
                try {
                    validateDate(textviewStartdate_medicalHistory_update!!, textviewEnddate_MH_update!!, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }


                //Toast.makeText(this,"date value::::${textview_EndDate}",Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun updateDateInViewStart() {

        val myFormat = "dd/MM/yyyy" // mention the format you need
        sdf1 = SimpleDateFormat(myFormat, Locale.US)
        textviewStartdate_medicalHistory_update!!.text = sdf1?.format(cal_StartMh.getTime())
    }

    private fun updateDateInViewEnd() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        sdf2 = SimpleDateFormat(myFormat, Locale.US)

        textviewEnddate_MH_update!!.text = sdf2?.format(cal_EndMh.getTime())


    }


    override fun onResume() {
        super.onResume()

        // loadDestinations()

        loadDetails(mobile_no.toString(), position!!)

        initUpdateButton(mobile_no.toString())

        //initDeleteButton(id)
//        }
    }


    public fun loadDetails(id: String, position: Int) {

        val medService = ServiceBuilder.buildService(MedicalHistoryService::class.java)
        val requestCall = medService.getMedicationByid(mobile_no.toString())


        //Toast.makeText(this,"test msg "+ id,Toast.LENGTH_LONG).show()

        requestCall.enqueue(object : retrofit2.Callback<List<MedicationDataClass>> {


            override fun onResponse(call: Call<List<MedicationDataClass>>, response: Response<List<MedicationDataClass>>) {

                if (response.isSuccessful) {
                    val medRes = response.body()

                    //if (medRes!!.equals(null)) {

                    //} else {

                    val medicationdestination = medRes?.get(position)
                    medicationid = medicationdestination?.medication_id!!


                    medicationdestination.let {


                        spinner_how_often_taken_update!!.text1.setText(medicationdestination.how_often_taken)
                        spinner_formulation_update!!.formu.setText(medicationdestination.formulation)
                        spinner_is_prescribed_update!!.tv_precsribed.setText(medicationdestination.isprescribed)
                        spinner_dose_unit_update!!.tv_dose.setText(medicationdestination.doseunit)
                        et_name_medication_update.setText(medicationdestination.medicationname)
                        et_notes_medication_update.setText(medicationdestination.medicationnotes)
                        et_reason_update.setText(medicationdestination.reason)
                        et_dose_strength_update.setText(medicationdestination.strength)
                        textviewEnddate_MH_update.setText(medicationdestination.enddate)
                        textviewStartdate_medicalHistory_update.setText(medicationdestination.startdate)


                        //text1.setText(destination.spnrdata)


                        //collapsing_toolbar.title = destination.city
                    }

                } else {
                    Toast.makeText(this@MedicationUpdateActivity, "Failed to retrieve details under else", Toast.LENGTH_SHORT)
                            .show()
                }
            }

            override fun onFailure(call: Call<List<MedicationDataClass>>, t: Throwable) {
                Toast.makeText(
                        this@MedicationUpdateActivity,
                        "Failed to retrieve details " + t.toString(),
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    public fun initUpdateButton(id: String) {

        btn_medication_update.setOnClickListener {

            //validateDate(textviewStartdate_medicalHistory_update!!, textviewEnddate_MH_update!!,true)
            val itemFormulation = spinner_formulation_update.formu.text.toString()
            val itemDose = spinner_dose_unit_update!!.tv_dose.text.toString()
            val itemPrescribed = spinner_is_prescribed_update!!.tv_precsribed.text.toString()
            var itemHow = spinner_how_often_taken!!.text1.text.toString()

            val newmedication = MedicationDataClass()




            if (!itemFormulation.isEmpty()) {
                newmedication.formulation = itemFormulation

                //return true
            } else {
                newmedication.formulation = spinner_formulation_update?.getSelectedItem().toString()
                formu.setText(newmedication.formulation)
            }
            if (!itemPrescribed.isEmpty()) {
                newmedication.isprescribed = itemPrescribed
                //return true
            } else {

                newmedication.isprescribed = spinner_is_prescribed_update?.getSelectedItem().toString()
                tv_precsribed.setText(newmedication.isprescribed)
            }
            if (!itemDose.isEmpty()) {
                newmedication.doseunit = itemDose
                //return true
            } else {
                newmedication.doseunit = spinner_dose_unit_update?.getSelectedItem().toString()
                tv_dose.setText(newmedication.doseunit)
            }
            if (!itemHow.isEmpty()) {
                newmedication.how_often_taken = itemHow
                //return true
            } else {
                newmedication.how_often_taken = spinner_how_often_taken!!.selectedItemsAsString
                text1.setText(newmedication.how_often_taken)
            }


            // newmedication.how_often_taken = spinner_how_often_taken_update!!.selectedItemsAsString
            newmedication.medicationname = et_name_medication_update!!.text.toString().trim()
            newmedication.medicationnotes = et_notes_medication_update!!.text.toString().trim()
            newmedication.reason = et_reason_update!!.text.toString().trim()
            newmedication.strength = et_dose_strength_update!!.text.toString().trim()
            // newmedication.isprescribed=spinner_is_prescribed_update!!.getSelectedItem().toString()
            //newmedication.doseunit=spinner_dose_unit_update!!.getSelectedItem().toString()
            //newmedication.formulation=spinner_formulation_update!!.getSelectedItem().toString()
            newmedication.startdate = textviewStartdate_medicalHistory_update!!.text.toString().trim()
            newmedication.enddate = textviewEnddate_MH_update!!.text.toString().trim()
            newmedication.mobile_no = mobile_no.toString()

            newmedication.medication_id = medicationid



            val destinationService = ServiceBuilder.buildService(MedicalHistoryService::class.java)


            val requestCall = destinationService.updateMedication(newmedication)

            requestCall.enqueue(object : Callback<MedicationDataClass> {


                override fun onResponse(call: Call<MedicationDataClass>, response: Response<MedicationDataClass>) {

                    Log.d("tag:::::", response.toString())
                    Log.d("tag:::::", response.message())

                    var updatedDestination = response.body()
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        var updatedDestination = response.body() // Use it or ignore It
                        Toast.makeText(this@MedicationUpdateActivity,
                                "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                    } else {

                        intent = Intent(this@MedicationUpdateActivity, MedicationItemListActivity::class.java)
                        intent.putExtra("position", position)
                        startActivity(intent)
                        Toast.makeText(this@MedicationUpdateActivity, "Failed to update item1", Toast.LENGTH_SHORT).show()


                        Toast.makeText(this@MedicationUpdateActivity,
                                "Failed to update item under else", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MedicationDataClass>, t: Throwable) {
                    Toast.makeText(this@MedicationUpdateActivity,
                            "Failed to update item", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }


    private fun validateDate(textviewStartdate_medicalHistory_update: TextView, textviewEnddate_MH_update: TextView, b: Boolean): Boolean {
        if (!textviewStartdate_medicalHistory_update.text.toString().equals("") && !textviewEnddate_MH_update.text.toString().equals("")) {
            textviewStartdate_medicalHistory_update.setCompoundDrawables(null, null, null, null)
            textviewEnddate_MH_update.setCompoundDrawables(null, null, null, null)
            val startDate0 = SimpleDateFormat("dd/MM/yyyy").format(Date(textviewStartdate_medicalHistory_update.text.toString()))
            val endDate0 = SimpleDateFormat("dd/MM/yyyy").format(Date(textviewEnddate_MH_update.text.toString()))
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
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, MedicationItemListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}