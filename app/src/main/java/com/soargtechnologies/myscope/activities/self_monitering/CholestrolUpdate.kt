package com.soargtechnologies.myscope.activities.self_monitering

import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Self_monitoring_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_cholestrol_update.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*

class CholestrolUpdate : BaseActivity() {

    var position: Int = 1
    var cholestrolId: Int = 0
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    internal lateinit var myCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cholestrol_update)

        activitiesToolbar()
        header!!.text = "Cholesterol"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            cholesterol_update.setText(date1)
        }
        cholesterol_update.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val bundle: Bundle? = intent.extras
        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id: String = intent.getStringExtra(ARG_ITEM_ID)

            position = intent.getIntExtra("position", 0)
    //        showLongToast(position.toString())

            loadDetails(mobile_no.toString(), position!!)

            initUpdateButton(mobile_no.toString())

            //initDeleteButton(id)
        }
    }

    private fun loadDetails(id: String, position: Int) {
        val diseaseService = ServiceBuilder.buildService(Self_monitoring_service::class.java)
        val requestCall = diseaseService.getCholestrol(mobile_no.toString())

        requestCall.enqueue(object : retrofit2.Callback<List<Self_dataClass>> {

            override fun onResponse(call: Call<List<Self_dataClass>>, response: Response<List<Self_dataClass>>) {

                val destination = response.body()
//                Log.d("resp:", response.toString())
//                Log.e("resp:", response.toString())
                val blood_cholestrol = destination?.get(position)
                cholestrolId = blood_cholestrol?.cholestrol_id!!

                blood_cholestrol?.let {

                    cholesterol_update.setText(blood_cholestrol.date_of_cholestrol)
                    lDL_update.setText(blood_cholestrol.ldl)
                    hDL_update.setText(blood_cholestrol.hdl)
                    triglycerides_update.setText(blood_cholestrol.trigly)
                    total_cholesterol_update.setText(blood_cholestrol.total_cholestrol)
                    others_test_update.setText(blood_cholestrol.other_test)
                    notes_cholesterol_update.setText(blood_cholestrol.cholestrol_notes)
                }

            }

            override fun onFailure(call: Call<List<Self_dataClass>>, t: Throwable) {
    //            Toast.makeText(this@CholestrolUpdate, "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initUpdateButton(id: String) {

        btn_cholesterol_update.setOnClickListener {

            assignValuestoVariable()

        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, Cholestrol_recyclerView::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {

        val ARG_ITEM_ID: String = "item_id"
    }

    private fun assignValuestoVariable() {

        val date_of_Cholestrol = cholesterol_update.text.toString()
        val Ldl = lDL_update.text.toString()
        val Hdl = hDL_update.text.toString()
        val Triglycerides = triglycerides_update.text.toString()
        val Total_cholesterol = total_cholesterol_update.text.toString()
     //   val Other_test = others_test_update.text.toString()

        //  validateInput(cholesterol_update, condition)
        validateInput(lDL_update , Ldl)
        validateInput(hDL_update , Hdl)
        validateInput(triglycerides_update , Triglycerides)
        validateInput(total_cholesterol_update , Total_cholesterol)

        if ((date_of_Cholestrol != "") && (Ldl != "") && (Hdl != "") && (Triglycerides != "") &&
                (Total_cholesterol != "")) {
 //           showLongToast("save the details")
            sucess()

        } else {

            showLongSnackBar("Please fill the required fields")

        }
    }

    private fun sucess() {




        val newCholestrol = Self_dataClass()
        newCholestrol.date_of_cholestrol = cholesterol_update!!.text.toString().trim()

        newCholestrol.ldl = lDL_update!!.text.toString().trim()

        newCholestrol.hdl= hDL_update!!.text.toString().trim()
        newCholestrol.trigly = triglycerides_update!!.text.toString().trim()
        newCholestrol.total_cholestrol = total_cholesterol_update!!.text.toString().trim()
        newCholestrol.other_test = others_test_update!!.text.toString().trim()
        newCholestrol.cholestrol_notes = notes_cholesterol_update!!.text.toString().trim()

        newCholestrol.cholestrol_update_on = datesetvalue()

//        newCholestrol.mobile_no = mobile_no!!
        newCholestrol.cholestrol_id = cholestrolId

        val destinationService = ServiceBuilder.buildService(Self_monitoring_service::class.java)
        val requestCall = destinationService.updatCholestrol(newCholestrol)

        requestCall.enqueue(object: Callback<Self_dataClass> {

            override fun onResponse(call: Call<Self_dataClass>, response: Response<Self_dataClass>) {
                val resp = response
                if (response.isSuccessful) {

                    var updatedDestination = response.body() // Use it or ignore It
                   Toast.makeText(this@CholestrolUpdate, "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                    finish() // Move back to DestinationListActivity
                }
                else {
                    intent = Intent(this@CholestrolUpdate,Cholestrol_recyclerView::class.java)
                    intent.putExtra("position" , position)
                    startActivity(intent)
                  Toast.makeText(this@CholestrolUpdate  , "Failed to update item1", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Self_dataClass>, t: Throwable) {
                Toast.makeText(this@CholestrolUpdate,  "Failed to update item", Toast.LENGTH_SHORT).show()
            }
        })


    }
}

