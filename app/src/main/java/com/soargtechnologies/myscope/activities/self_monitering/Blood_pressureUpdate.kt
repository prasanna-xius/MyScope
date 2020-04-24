package com.soargtechnologies.myscope.activities.self_monitering

import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Self_monitoring_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_blood_pressure_update.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*

class Blood_pressureUpdate : BaseActivity() {

    var position: Int = 1
    var pressureId: Int = 0
    var spinner_Pressure: Spinner? = null
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    internal lateinit var myCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_pressure_update)

        activitiesToolbar()
        header!!.text = "Blood Pressure"

        val pressureSpinnerUpdated = findViewById<Spinner>(R.id.spinner_irregular_heartbeats_update)

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.blood_pressure_dropdown))
        pressureSpinnerUpdated!!.adapter = adapter

        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            date_of_bp_update.setText(date1)
        }
        date_of_bp_update.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val bundle: Bundle? = intent.extras
        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id: String = intent.getStringExtra(ARG_ITEM_ID)

            position = intent.getIntExtra("position", 0)
            showLongToast(position.toString())

            loadDetails(mobile_no.toString(), position!!)

            initUpdateButton(mobile_no.toString())

            //initDeleteButton(id)
        }
    }

    private fun loadDetails(id: String, position: Int) {
        val diseaseService = ServiceBuilder.buildService(Self_monitoring_service::class.java)
        val requestCall = diseaseService.getPressure(mobile_no.toString())

        requestCall.enqueue(object : retrofit2.Callback<List<Self_dataClass>> {

            override fun onResponse(call: Call<List<Self_dataClass>>, response: Response<List<Self_dataClass>>) {

                val destination = response.body()
//                Log.d("resp:", response.toString())
//                Log.e("resp:", response.toString())
                val blood_pressure = destination?.get(position)
                pressureId = blood_pressure?.pressure_id!!

                blood_pressure?.let {

                    text1!!.setText(blood_pressure.irregular_heart_beat)
                    date_of_bp_update.setText(blood_pressure.date_of_pressure)
                    et_systolic_update.setText(blood_pressure.systolic)
                    et_diastolic_update.setText(blood_pressure.diastrlic)
                    et_pulse_update.setText(blood_pressure.pulse)
                    et_heart_rate_update.setText(blood_pressure.heart_rate)
                    notes_blood_pressure_update.setText(blood_pressure.pressure_notes)
                }

            }

            override fun onFailure(call: Call<List<Self_dataClass>>, t: Throwable) {
                Toast.makeText(this@Blood_pressureUpdate, "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initUpdateButton(id: String) {

        btn_blood_pressure_update.setOnClickListener {

            assignValuestoVariable()
            validate(spinner_irregular_heartbeats_update!!)

        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, Blood_Pressure_recyclerView::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {

        val ARG_ITEM_ID: String = "item_id"
    }

    private fun assignValuestoVariable() {

        val date_of_Bp = date_of_bp_update.text.toString()
        val Irregular_HeartBeat = spinner_irregular_heartbeats_update!!.selectedItem.toString()
        val Systolic = et_systolic_update.text.toString()
        val Diastolic = et_diastolic_update.text.toString()
        val Pulse = et_pulse_update.text.toString()
        val HeartRate = et_heart_rate_update.text.toString()

       // validateInput(date_of_bp_update , date_of_Bp)
        validateInput(et_systolic_update , Systolic)
        validateInput(et_diastolic_update , Diastolic)
        validateInput(et_pulse_update , Pulse)
        validateInput(et_heart_rate_update , HeartRate)
        validateSpinner(spinner_irregular_heartbeats_update!!, Irregular_HeartBeat)

        if ((date_of_Bp != "") &&(Systolic != "") &&(Diastolic != "") &&(Pulse != "") &&(HeartRate != "") &&
                (Irregular_HeartBeat != "None")
               ) {
            showLongToast("save the details")
            sucess()
        } else {

            showLongSnackBar("Please fill the required fields")

        }
    }

    private fun sucess() {


        val item = spinner_irregular_heartbeats_update.text1.text.toString()
        showLongToast(item)

        val newPressure = Self_dataClass()
        newPressure.date_of_pressure = date_of_bp_update!!.text.toString().trim()
        if(!item.equals(null)) {
            newPressure.irregular_heart_beat = item
        }
        else {
            newPressure.irregular_heart_beat = spinner_irregular_heartbeats_update?.getSelectedItem().toString()
            text1.setText(newPressure.irregular_heart_beat)
        }

        newPressure.systolic = et_systolic_update!!.text.toString().trim()
        newPressure.diastrlic = et_diastolic_update!!.text.toString().trim()
        newPressure.pulse = et_pulse_update!!.text.toString().trim()
        newPressure.heart_rate = et_heart_rate_update!!.text.toString().trim()
        newPressure.pressure_notes = notes_blood_pressure_update!!.text.toString().trim()

        newPressure.pressure_update_on = datesetvalue()

        newPressure.mobile_no = mobile_no!!
        newPressure.pressure_id = pressureId

        val destinationService = ServiceBuilder.buildService(Self_monitoring_service::class.java)
        val requestCall = destinationService.updatePressure(newPressure)

        requestCall.enqueue(object: Callback<Self_dataClass> {

            override fun onResponse(call: Call<Self_dataClass>, response: Response<Self_dataClass>) {
                val resp = response
                if (response.isSuccessful) {

                    var updatedDestination = response.body() // Use it or ignore It
                    Toast.makeText(this@Blood_pressureUpdate, "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                    finish() // Move back to DestinationListActivity
                } else {
                    intent = Intent(this@Blood_pressureUpdate,Blood_Pressure_recyclerView::class.java)
                    intent.putExtra("position" , position)
                    startActivity(intent)
                    Toast.makeText(this@Blood_pressureUpdate  , "Failed to update item1", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Self_dataClass>, t: Throwable) {
                Toast.makeText(this@Blood_pressureUpdate,  "Failed to update item", Toast.LENGTH_SHORT).show()
            }
        })


    }


}
