package com.soargtechnologies.myscope.activities.medical_history

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.models.MedicalHistoryModelActivity
import com.soargtechnologies.myscope.services.MedicalHistoryService
import com.soargtechnologies.myscope.services.ServiceBuilder

import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.app_bar_main.*

import kotlinx.android.synthetic.main.surgeryhistory_update.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class SurgeryUpdateActivity : BaseActivity() {

    var spnrAllergy_Update :Spinner?=null
    //var nameUpdate:String ?=null
    //var notesUpdate:String?=null
    //var eventUpdate:String?=null
    //var brandUpdate:String?=null
    var dateUpdate:String?=null
    var position: Int= 1;
    var cal = Calendar.getInstance()
    var buttondate_surg_update:ImageView?=null
    var surgeryid:Int=0

    internal lateinit var myCalendar: Calendar
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.surgeryhistory_update)

        activitiesToolbar()
        header!!.text = "Surgery History"
        //setSupportActionBar(detail_toolbar as Toolbar?)
        // Show the Up button in the action bar.
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        //spnrAllergy_Update = findViewById(R.id.spinnerAllergy_update)

        //dateUpdate = findViewById<View>(R.id.textviewdate_immuupdate).toString()

   //     buttondate_surg_update=findViewById(R.id.buttondate_surg_update)


        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            textviewdate_surgery_update.setText(date1)
        }
        textviewdate_surgery_update.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {


  //          Toast.makeText(this,"data"+ARG_ITEM_ID.toString(),Toast.LENGTH_LONG).show()

            //val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            val id : String = intent.getStringExtra(ARG_ITEM_ID)
            position = intent.getIntExtra("position" , 0)

            loadDetails(mobile_no.toString(),position)

            initUpdateButton(mobile_no.toString())

            //initDeleteButton(id)
        }



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
        textviewdate_surgery_update!!.text = sdf.format(cal.getTime())
    }

    private fun loadDetails(id: String , position : Int) {

        val immuService = ServiceBuilder.buildService(MedicalHistoryService::class.java)
        val requestCall = immuService.getSurgeryByid(id)

        progressbar()

  //      Toast.makeText(this,"test msg "+ id,Toast.LENGTH_LONG).show()

        requestCall.enqueue(object : retrofit2.Callback<List<MedicalHistoryModelActivity>> {




            override fun onResponse(call: Call<List<MedicalHistoryModelActivity>>, response: Response<List<MedicalHistoryModelActivity>>) {

                if (response.isSuccessful) {




                    val surgeryRes = response.body()

                    val surgerydestination = surgeryRes?.get(position)
                    surgeryid = surgerydestination?.surgery_id!!


                    surgerydestination?.let {
                        et_name_surgery_history_update.setText(surgerydestination.surgeryname)
                        et_body_location_update.setText(surgerydestination.surgerylocation)
                        et_hospital_name_update.setText(surgerydestination.surgeryhospital)
                        et_notes_surgery_history_update.setText(surgerydestination.surgerynotes)
                        et_provider_update.setText(surgerydestination.surgeryprovider)
                        textviewdate_surgery_update.setText(surgerydestination.surgerydate)
                        //text1.setText(destination.spnrdata)



                        //collapsing_toolbar.title = destination.city
                    }!!
                } else {
   //                 Toast.makeText(this@SurgeryUpdateActivity, "Failed to retrieve details under else", Toast.LENGTH_SHORT) .show()
                }
            }

            override fun onFailure(call: Call<List<MedicalHistoryModelActivity>>, t: Throwable) {
 //               Toast.makeText(this@SurgeryUpdateActivity,"Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT  ).show()
            }
        })
    }

    private fun initUpdateButton(id: String) {

        button_surgery_update.setOnClickListener {


            if (validate(et_name_surgery_history_update,true)) {


                val newsurgery = MedicalHistoryModelActivity()

                newsurgery.surgeryname = et_name_surgery_history_update!!.text.toString().trim()
                newsurgery.surgerynotes = et_notes_surgery_history_update!!.text.toString().trim()
                newsurgery.surgerylocation = et_body_location_update!!.text.toString().trim()
                newsurgery.surgeryprovider = et_provider_update!!.text.toString().trim()
                newsurgery.surgeryhospital = et_hospital_name_update!!.text.toString().trim()
                newsurgery.surgerydate = textviewdate_surgery_update!!.text.toString().trim()
                //newsurgery.immudate = dateUpdate.toString().trim()
                newsurgery.mobile_no = mobile_no!!
                newsurgery.surgery_id = surgeryid
                newsurgery.surgery_updated_on=datesetvalue()


                val destinationService = ServiceBuilder.buildService(MedicalHistoryService::class.java)


                val requestCall = destinationService.updateSurgery(newsurgery)

                requestCall.enqueue(object : Callback<MedicalHistoryModelActivity> {


                    override fun onResponse(call: Call<MedicalHistoryModelActivity>, response: Response<MedicalHistoryModelActivity>) {

//                        Log.d("tag:::::", response.toString())
//                        Log.d("tag:::::", response.message())

                        var updatedDestination = response.body()
                        if (response.isSuccessful) {
                            finish() // Move back to DestinationListActivity
                            var updatedDestination = response.body() // Use it or ignore It
       //                     Toast.makeText(this@SurgeryUpdateActivity,"Item Updated Successfully", Toast.LENGTH_SHORT).show()
                        } else {
 //                           Toast.makeText(this@SurgeryUpdateActivity, "Failed to update item under else", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<MedicalHistoryModelActivity>, t: Throwable) {
        //                Toast.makeText(this@SurgeryUpdateActivity,  "Failed to update item", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun validate(et_name_surgery_history_update: EditText, b:Boolean):Boolean {
        if(et_name_surgery_history_update.text.toString().isEmpty()){
            //et_name_immuupdate.setError("")
            val snack = Snackbar.make(findViewById(R.id.svSurgery)!!, "Mandatory field cannot be left blank.", Snackbar.LENGTH_LONG)
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
            navigateUpTo(Intent(this, SurgeryItemListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}