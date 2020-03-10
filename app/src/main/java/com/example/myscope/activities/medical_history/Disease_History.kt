package com.example.myscope.activities.medical_history

import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View

import android.widget.*
import androidx.annotation.RequiresApi
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import com.google.android.gms.security.ProviderInstaller
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.disease_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*
import javax.net.ssl.SSLContext

class Disease_History : BaseActivity() {
    internal lateinit var myCalendar: Calendar
    private var startDateOrEndDAte = true
    var spinner_disease: Spinner? = null
    var boolean: Boolean? = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.disease_history)
        showToolbar()
        header!!.text = "Disease History"
        spinner_disease = findViewById<Spinner>(R.id.spinner_disease)

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.disease_arrays))
        spinner_disease!!.adapter = adapter

        myCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view:View, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            if (startDateOrEndDAte) {
                et_startDate.setText(date1)
            } else {
                et_stopDate.setText(date1)
            }
            duration(et_startDate, et_stopDate, et_noOfYrs)
        }

        et_startDate.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            startDateOrEndDAte = true
        }

        et_stopDate.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            startDateOrEndDAte = false
        }

        btn_medicalHistory.setOnClickListener {
            assignValuestoVariable()
            validate(spinner_disease!!)
//            validateDate(et_startDate,et_stopDate,false)

            /*name = etname!!.getText().toString()
            reaction = etreaction!!.getText().toString()
            treatment = ettreatments!!.getText().toString()
            notes = etnotes!!.getText().toString()
            date = textview_date?.getText().toString()*/

            //var newAllergy: Any? =null

            try {
                // Google Play will install latest OpenSSL
                ProviderInstaller.installIfNeeded(getApplicationContext());
                var sslContext: SSLContext
                sslContext = SSLContext.getInstance("TLSv1.2");
                sslContext.init(null, null, null);
                sslContext.createSSLEngine();
            } catch (e: Exception) {
                e.printStackTrace();
            }



            val newDisease = Diseases()
            newDisease.known_condition = et_condition!!.text.toString().trim()
            newDisease.disease_status = spinner_disease?.getSelectedItem().toString()
            newDisease.disease_duration = et_noOfYrs!!.text.toString().trim()
            newDisease.when_started = et_startDate!!.text.toString().trim()
            newDisease.when_ended = et_stopDate!!.text.toString().trim()
            newDisease.disease_note = notes_diseaseHistory!!.text.toString().trim()
            newDisease.mobile_no = "8142529582"



            val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)

            //val requestCall =allergyService.addAllergy(name!!,reaction!!,treatment!!,notes!!,date!!,sprdata!!)

            val requestCall = diseaseService.addDisease(newDisease)

            requestCall.enqueue(object : Callback<Diseases> {
                /**
                 * Invoked when a network exception occurred talking to the server or when an unexpected
                 * exception occurred creating the request or processing the response.
                 */
                override fun onResponse(call: Call<Diseases>, resp: Response<Diseases>) {

                    if (resp.isSuccessful) {
                        var newbody = resp.body() // Use it or ignore it
                        Toast.makeText(applicationContext, "Successfully Added"+newbody, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Diseases>, t: Throwable) {
                    //finish()
//                    Log.d("errormsgfailure ::", t.message)
//                    Log.e("errorunderfailure:", t.message)
                    Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
                }
            })
            // Toast.makeText(getApplicationContext(), "data:" + name + " " + reaction + " " + treatment + " " + " " + notes + " " +
            // spritem + " " + date, Toast.LENGTH_LONG).show()

        }

//          editbtn_disease.setOnClickListener {
//
//
//
//            btn_medicalHistory.setText("UPDATE")
//
//            val bundle: Bundle? = intent.extras
//
//            if (bundle?.containsKey(ARG_ITEM_ID)!!) {
//
//                val id = intent.getIntExtra(ARG_ITEM_ID, 0)
//
////                loadDetails(id)
//
//                initUpdateButton(id)
//
//                //initDeleteButton(id)
//            }
//
//
//        }


    }

    /*private fun loadDetails(id: Int) {


   //val filter = HashMap<String , String>()()
           val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
           val requestCall = diseaseService.getdisease("null")
           requestCall.enqueue(object : retrofit2.Callback<Diseases>{

               override fun onResponse(call: Call<Diseases>, response: Response<Diseases>) {

                   if (response.isSuccessful) {
                       val destination = response.body()
                       destination?.let {
                           et_condition.setText(destination.known_condition)
   //                       spinner_disease?.setText(destination.disease_status)
                           et_noOfYrs.setText(destination.disease_duration)
                           et_startDate.setText(destination.when_started)
                           et_stopDate.setText(destination.when_ended)
                           notes_diseaseHistory.setText(destination.disease_note)

                           //spinnerAllergy_update.getString(destination.spinerdata)

                           //collapsing_toolbar.title = destination.city
                       }!!
                   } else {
                       Toast.makeText(this@Disease_History, "Failed to retrieve details", Toast.LENGTH_SHORT).show()
                   }
               }

               override fun onFailure(call: Call<Diseases>, t: Throwable) {
               Toast.makeText(this@Disease_History, "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
               }
           })

       }*/

//    private fun initUpdateButton(id: Int) {
//        btn_medicalHistory.setOnClickListener {
//
//            val known_conditionUpdate = et_condition.text.toString()
//            val disease_statusUpdate = et_noOfYrs.text.toString()
//            val disease_durationUpdate = et_condition.text.toString()
//            val when_startedUpdate = et_noOfYrs.text.toString()
//            val when_endedUpdate = et_condition.text.toString()
//            val disease_noteUpdate = et_noOfYrs.text.toString()
//            val number:String = "8142529582"
//
//
//            val destinationService = ServiceBuilder.buildService(Disease_service::class.java)
//            val requestCall = destinationService.updateDisease(number.toInt(), known_conditionUpdate,disease_statusUpdate,disease_durationUpdate,when_startedUpdate,
//                    when_endedUpdate,disease_noteUpdate  )
//
//            requestCall.enqueue(object: Callback<Diseases> {
//
//                override fun onResponse(call: Call<Diseases>, response: Response<Diseases>) {
//                    if (response.isSuccessful) {
//                        finish() // Move back to DestinationListActivity
//                        var updatedDestination = response.body() // Use it or ignore It
//                        Toast.makeText(this@Disease_History  , "Item Updated Successfully", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(this@Disease_History  , "Failed to update item1", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<Diseases>, t: Throwable) {
//                    Toast.makeText(this@Disease_History  , "Failed to update item", Toast.LENGTH_SHORT).show()
//                }
//            })
//        }
//    }



    private fun assignValuestoVariable() {

        val condition = et_condition.text.toString()
        val diseases = spinner_disease!!.selectedItem.toString()
        val disease_duration = et_noOfYrs.text.toString()
        validateInput(et_condition, condition)
        validateInput(et_noOfYrs , disease_duration)
        validateSpinner(spinner_disease!!, diseases)

        if ((condition != "") &&
                (diseases != "None")
                && (disease_duration != "")) {
            showLongToast("save the details")
        } else {

            showLongSnackBar("Please fill the required fields")

        }
    }


//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        if (id == android.R.id.home) {
//            navigateUpTo(Intent(this, Disease_recyclerView::class.java))
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//
//
//    companion object {
//
//        const val ARG_ITEM_ID = "item_id"
//    }
}






