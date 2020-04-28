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
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Self_monitoring_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_bmi_update.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*

class BmiUpdate : BaseActivity() {


    var position: Int = 1
    var bmiId: Int = 0
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    internal lateinit var myCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_update)

        activitiesToolbar()
        header!!.text = "Bmi"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

       // showLongToast(mobile_no.toString())

        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            date_of_bmi_update.setText(date1)
        }
        date_of_bmi_update.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        bmi1_update.setOnClickListener {

            bmicalculator(weight_bmi_update , height_bmi_update , bmi1_update)

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
        val bmiService = ServiceBuilder.buildService(Self_monitoring_service::class.java)
        val requestCall = bmiService.getBmi(mobile_no.toString())

        requestCall.enqueue(object : retrofit2.Callback<List<Self_dataClass>> {

            override fun onResponse(call: Call<List<Self_dataClass>>, response: Response<List<Self_dataClass>>) {

                val destination = response.body()
//                Log.d("resp:", response.toString())
//                Log.e("resp:", response.toString())
                val bmi = destination?.get(position)
                bmiId = bmi?.bmi_id!!

                bmi?.let {


                    weight_bmi_update.setText(bmi.weight)
                    height_bmi_update.setText(bmi.height)
                    bmi1_update.setText(bmi.bmi)
                    notes_bmi_update.setText(bmi.bmi_notes)
                    date_of_bmi_update.setText(bmi.date_of_bmi)
                }

            }

            override fun onFailure(call: Call<List<Self_dataClass>>, t: Throwable) {
   //             Toast.makeText(this@BmiUpdate, "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initUpdateButton(id: String) {

        btn_bmi_update.setOnClickListener {

            assignValuestoVariable()

        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, Bmi_recyclerView::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {

        val ARG_ITEM_ID: String = "item_id"
    }

    private fun assignValuestoVariable() {

        val date_of_Bmi = date_of_bmi_update.text.toString()
        val Weight = weight_bmi_update.text.toString()
        val Height = height_bmi_update.text.toString()
        val Bmi = bmi1_update.text.toString()

        //  validateInput(date_of_bmi_update, date_of_Bmi)
        validateInput( weight_bmi_update, Weight)
        validateInput(height_bmi_update , Height)
       // validateInput(bmi1_update , Bmi)
        if ((Bmi != "") && (Weight != "") && (Height != "")  && (date_of_Bmi != "")) {
   //         showLongToast("save the details")
            sucess()

        } else {

            showLongSnackBar("Please fill the required fields")

        }
    }

    private fun sucess() {




        val newBmi = Self_dataClass()
        newBmi.date_of_bmi = date_of_bmi_update!!.text.toString().trim()

        newBmi.weight = weight_bmi_update!!.text.toString().trim()

        newBmi.height= height_bmi_update!!.text.toString().trim()
        newBmi.bmi = bmi1_update!!.text.toString().trim()
        newBmi.bmi_notes = notes_bmi_update!!.text.toString().trim()

        newBmi.bmi_update_on = datesetvalue()

        newBmi.mobile_no = mobile_no!!
        newBmi.bmi_id = bmiId

        val destinationService = ServiceBuilder.buildService(Self_monitoring_service::class.java)
        val requestCall = destinationService.updatBmi(newBmi)

        requestCall.enqueue(object: Callback<Self_dataClass> {

            override fun onResponse(call: Call<Self_dataClass>, response: Response<Self_dataClass>) {
                val resp = response
                if (response.isSuccessful) {

                    var updatedDestination = response.body() // Use it or ignore It
  //                  Toast.makeText(this@BmiUpdate, "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                    finish() // Move back to DestinationListActivity
                } else {
                    intent = Intent(this@BmiUpdate,Bmi_recyclerView::class.java)
                    intent.putExtra("position" , position)
                    startActivity(intent)
  //                  Toast.makeText(this@BmiUpdate  , "Failed to update item1", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Self_dataClass>, t: Throwable) {
  //              Toast.makeText(this@BmiUpdate,  "Failed to update item", Toast.LENGTH_SHORT).show()
            }
        })


    }
}
