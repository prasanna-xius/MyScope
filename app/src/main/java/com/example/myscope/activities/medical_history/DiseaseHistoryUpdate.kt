package com.example.myscope.activities.medical_history

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
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_disease_history_update.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.RowId
import java.text.DateFormat
import java.util.*


class DiseaseHistoryUpdate : BaseActivity() {
    var position: Int= 1;
    internal lateinit var myCalendar: Calendar
    private var startDateOrEndDAte = true
    var spinner_disease: Spinner? = null
    var diseaseid:Int=0
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_history_update)


        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        val spinner_disease = findViewById<Spinner>(R.id.spinner_disease_updated)

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.disease_arrays))
        spinner_disease!!.adapter = adapter

        myCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view: View, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            if (startDateOrEndDAte) {
                et_startDate_updated.setText(date1)
            } else {
                et_stopDate_updated.setText(date1)
            }
            duration(et_startDate_updated, et_stopDate_updated, et_noOfYrs_updated)
        }

        et_startDate_updated.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            startDateOrEndDAte = true
        }

        et_stopDate_updated.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            startDateOrEndDAte = false
        }




        val bundle: Bundle? = intent.extras
//        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

//            val id : String = intent.getStringExtra(ARG_ITEM_ID)

             position = intent.getIntExtra("position" , 0)
            showLongToast(position.toString())

             loadDetails(mobile_no.toString() , position!!)

            initUpdateButton(mobile_no.toString())

            //initDeleteButton(id)
//        }

    }

    private fun loadDetails(id: String , position : Int) {
       val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
       val requestCall = diseaseService.getdisease(mobile_no.toString())

        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>> {

            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>> ) {

                val destination = response.body()
//                Log.d("resp:", response.toString())
//                Log.e("resp:", response.toString())
                val disease = destination?.get(position)
                diseaseid = disease?.disease_id!!

                disease?.let {
                    et_condition_updated.setText(disease.known_condition)

                    spinner_disease_updated!!.text1.setText(disease.disease_status)
                    et_noOfYrs_updated.setText(disease.disease_duration)
                    et_startDate_updated.setText(disease.when_started)
                    et_stopDate_updated.setText(disease.when_ended)
                    notes_diseaseHistory_updated.setText(disease.disease_note)
                }

            }
           override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {
           Toast.makeText(this@DiseaseHistoryUpdate , "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
           }
       })

   }

    private fun initUpdateButton(id: String) {

        btn_medicalHistory_update.setOnClickListener {

            val item = spinner_disease_updated.text1.text.toString()
            showLongToast(item)

            val newDisease = Diseases()
            newDisease.known_condition = et_condition_updated!!.text.toString().trim()
            if(!item.equals(null)) {
                newDisease.disease_status = item
                    } else {
                newDisease.disease_status = spinner_disease_updated?.getSelectedItem().toString()
                text1.setText(newDisease.disease_status)
            }

            newDisease.disease_duration = et_noOfYrs_updated!!.text.toString().trim()
            newDisease.when_started = et_startDate_updated!!.text.toString().trim()
            newDisease.when_ended = et_stopDate_updated!!.text.toString().trim()
            newDisease.disease_note = notes_diseaseHistory_updated!!.text.toString().trim()
            newDisease.mobile_no = mobile_no!!
            newDisease.disease_id = diseaseid

            val destinationService = ServiceBuilder.buildService(Disease_service::class.java)
            val requestCall = destinationService.updateDisease(newDisease)

            requestCall.enqueue(object: Callback<Diseases> {

                override fun onResponse(call: Call<Diseases>, response: Response<Diseases>) {
                    val resp = response
                    if (response.isSuccessful) {

                        var updatedDestination = response.body() // Use it or ignore It
                        Toast.makeText(this@DiseaseHistoryUpdate, "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                        finish() // Move back to DestinationListActivity
                    } else {
                        intent = Intent(this@DiseaseHistoryUpdate,Disease_recyclerView::class.java)
                        intent.putExtra("position" , position)
                        startActivity(intent)
                        Toast.makeText(this@DiseaseHistoryUpdate  , "Failed to update item1", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Diseases>, t: Throwable) {
                    Toast.makeText(this@DiseaseHistoryUpdate,
                            "Failed to update item", Toast.LENGTH_SHORT).show()
                }
            })

            assignValuestoVariable()
            validate(spinner_disease_updated!!)
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, Disease_recyclerView::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    companion object {

         val ARG_ITEM_ID:String = "item_id"
    }

    private fun assignValuestoVariable() {

        val condition = et_condition_updated.text.toString()
        val diseases = spinner_disease_updated!!.selectedItem.toString()
        val disease_duration = et_noOfYrs_updated.text.toString()
        validateInput(et_condition_updated, condition)
        validateInput(et_noOfYrs_updated , disease_duration)
        validateSpinner(spinner_disease_updated!!, diseases)

        if ((condition != "") &&
                (diseases != "None")
                && (disease_duration != "")) {
            showLongToast("save the details")
        } else {

            showLongSnackBar("Please fill the required fields")

        }
    }


}
