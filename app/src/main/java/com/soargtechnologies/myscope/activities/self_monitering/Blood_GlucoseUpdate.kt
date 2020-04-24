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
import kotlinx.android.synthetic.main.activity_blood__glucose_update.*
import kotlinx.android.synthetic.main.activity_blood_glucose_monitoring.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*

class Blood_GlucoseUpdate : BaseActivity() {

    var position: Int = 1;
    internal lateinit var myCalendar: Calendar
    var spinner_Glucose: Spinner? = null
    var glucoseId: Int = 0
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood__glucose_update)

        activitiesToolbar()
        header!!.text = "Blood Glucose Monitoring"

        val glucoseSpinnerUpdated = findViewById<Spinner>(R.id.spinner_glucoseUpdate)

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.blood_glucose_dropdown))
        glucoseSpinnerUpdated!!.adapter = adapter

        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            date_of_test_update.setText(date1)
        }
        date_of_test_update.setOnClickListener {
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
        val requestCall = diseaseService.getGlucose(mobile_no.toString())

        requestCall.enqueue(object : retrofit2.Callback<List<Self_dataClass>> {

            override fun onResponse(call: Call<List<Self_dataClass>>, response: Response<List<Self_dataClass>>) {

                val destination = response.body()
//                Log.d("resp:", response.toString())
//                Log.e("resp:", response.toString())
                val blood_glucose = destination?.get(position)
                glucoseId = blood_glucose?.glucose_id!!

                blood_glucose?.let {

                   text1.setText(blood_glucose.test)
                    date_of_test_update.setText(blood_glucose.date_of_test)
                    test_result_glucoseUpdate.setText(blood_glucose.test_result)
                    notes_blood_glucoseUpdate.setText(blood_glucose.glucose_notes)
                }

            }

            override fun onFailure(call: Call<List<Self_dataClass>>, t: Throwable) {
                Toast.makeText(this@Blood_GlucoseUpdate, "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initUpdateButton(id: String) {

        btn_glucose_updated.setOnClickListener {

            assignValuestoVariable()
            validate(spinner_glucoseUpdate!!)

        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, Blood_Glucose_recyclerView::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {

        val ARG_ITEM_ID: String = "item_id"
    }

    private fun assignValuestoVariable() {

        val date_of_Test = date_of_test_update.text.toString()
        val Test = spinner_glucoseUpdate!!.selectedItem.toString()
        val Test_result = test_result_glucoseUpdate.text.toString()
        //  validateInput(date_of_test, condition)
        validateInput(test_result_glucoseUpdate , Test_result)
        validateSpinner(spinner_glucoseUpdate!!, Test)

        if ((date_of_Test != "") &&
                (Test != "None")
                && (Test_result != "")) {
            showLongToast("save the details")
            sucess()
        } else {

            showLongSnackBar("Please fill the required fields")

        }
    }

    private fun sucess() {


        val item = spinner_glucoseUpdate.text1.text.toString()
        showLongToast(item)

        val newGlucose = Self_dataClass()
        newGlucose.date_of_test = date_of_test_update!!.text.toString().trim()
        if(!item.equals(null)) {
            newGlucose.test = item
        }
        else {
            newGlucose.test = spinner_glucoseUpdate?.getSelectedItem().toString()
            text1.setText(newGlucose.test)
        }

        newGlucose.test_result = test_result_glucoseUpdate!!.text.toString().trim()
        newGlucose.glucose_notes = notes_blood_glucoseUpdate!!.text.toString().trim()
        newGlucose.glucose_update_on = datesetvalue()

        newGlucose.mobile_no = mobile_no!!
        newGlucose.glucose_id = glucoseId

        val destinationService = ServiceBuilder.buildService(Self_monitoring_service::class.java)
        val requestCall = destinationService.updateglucose(newGlucose)

        requestCall.enqueue(object: Callback<Self_dataClass> {

            override fun onResponse(call: Call<Self_dataClass>, response: Response<Self_dataClass>) {
                val resp = response
                if (response.isSuccessful) {

                    var updatedDestination = response.body() // Use it or ignore It
                    Toast.makeText(this@Blood_GlucoseUpdate, "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                    finish() // Move back to DestinationListActivity
                } else {
                    intent = Intent(this@Blood_GlucoseUpdate,Blood_Glucose_recyclerView::class.java)
                    intent.putExtra("position" , position)
                    startActivity(intent)
                    Toast.makeText(this@Blood_GlucoseUpdate  , "Failed to update item1", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Self_dataClass>, t: Throwable) {
                Toast.makeText(this@Blood_GlucoseUpdate,
                        "Failed to update item", Toast.LENGTH_SHORT).show()
            }
        })


    }


}
