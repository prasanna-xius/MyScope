package com.example.myscope.activities.medical_history

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
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_adverse__drug__reaction__update.*
import kotlinx.android.synthetic.main.activity_adverse_drug.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*

class Adverse_Drug_Reaction_Update : BaseActivity() {

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    internal lateinit var myCalendar: Calendar
    var adverseDrugid: Int = 0
    var adverseDrug: Diseases? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adverse__drug__reaction__update)

        activitiesToolbar()
        header!!.text = "Adverse Drug Reaction"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
        showLongToast(mobile_no.toString())

        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            et_date_of_start_updated.setText(date1)
        }
        et_date_of_start_updated.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

//            val id: String = intent.getStringExtra(ARG_ITEM_ID)

            val position: Int? = intent.getIntExtra("position", 0)
            showLongToast(position.toString())

            loadDetails(mobile_no.toString(), position!!)

            initUpdateButton(mobile_no.toString())



        }

    }

        private fun loadDetails(id: String, position: Int) {

            val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
            val requestCall = diseaseService.getDrug(id)
            requestCall.enqueue(object : retrofit2.Callback<List<Diseases>> {
                override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>> ) {

                    val destination = response.body()
                 //   val newDrug = destination?.first()
                    adverseDrug = destination?.get(position)
                    adverseDrugid = adverseDrug?.adverse_id!!


                    adverseDrug?.let {
                        et_drug_Name_updated?.setText(adverseDrug!!.drugname) 
                        reaction_effect_updated?.setText(adverseDrug!!.reaction)
                        et_date_of_start_updated?.setText(adverseDrug!!.date_of_start)
                        et_treatment_taken_updated?.setText(adverseDrug!!.treatment_taken)

                    }
                    if (response.isSuccessful)
                    {
                        Toast.makeText(this@Adverse_Drug_Reaction_Update, "Item  Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this@Adverse_Drug_Reaction_Update  , "Failed at else part in load details", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {
                    Toast.makeText(this@Adverse_Drug_Reaction_Update , "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }



        private fun initUpdateButton(id: String)
        {
            btn_adverse_drug_updated.setOnClickListener {
                val newDrug = Diseases()
                newDrug.drugname = et_drug_Name_updated!!.text.toString().trim()
                newDrug.reaction = reaction_effect_updated!!.text.toString().trim()
                newDrug.date_of_start = et_date_of_start_updated!!.text.toString().trim()
                newDrug.treatment_taken = et_treatment_taken_updated!!.text.toString().trim()
                newDrug.mobile_no = mobile_no!!
                newDrug.adverse_id = adverseDrugid!!
                val destinationService = ServiceBuilder.buildService(Disease_service::class.java)
                val requestCall = destinationService.updateadversedrug(newDrug)
                requestCall.enqueue(object: Callback<Diseases> {
                    override fun onResponse(call: Call<Diseases>, response: Response<Diseases>) {
                        if (response.isSuccessful)
                        {
                            Toast.makeText(this@Adverse_Drug_Reaction_Update, "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                        finish()
                        }
                        else {
                            Toast.makeText(this@Adverse_Drug_Reaction_Update  , "Failed at else part in update", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<Diseases>, t: Throwable) {
                        Toast.makeText(this@Adverse_Drug_Reaction_Update, "Failed to update item", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, FamilyHistoryRecyclerView::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {

        val ARG_ITEM_ID:String = "item_id"
    }

    }

