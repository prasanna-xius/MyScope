package com.example.myscope.activities.medical_history

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_disease_history_update.*
import kotlinx.android.synthetic.main.activity_disease_history_update.view.*
import kotlinx.android.synthetic.main.disease_history.*
import kotlinx.android.synthetic.main.immunizationhistory.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.FieldPosition


class DiseaseHistoryUpdate : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_history_update)

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.disease_arrays))
        spinner_disease_updated!!.adapter = adapter


        val bundle: Bundle? = intent.extras



        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id : String = intent.getStringExtra(ARG_ITEM_ID)

            val position : Int? = intent.getIntExtra("position" , 0)
            showLongToast(position.toString())

             loadDetails(id , position!!)

            initUpdateButton(id)

            //initDeleteButton(id)
        }

    }

    private fun loadDetails(id: String , position : Int) {


//val filter = HashMap<String , String>()()
       val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
       val requestCall = diseaseService.getdisease(id)
        showLongToast(requestCall.toString())
       requestCall.enqueue(object : retrofit2.Callback<Diseases>{

           override fun onResponse(call: Call<Diseases>, response: Response<Diseases>) {

               if (response.isSuccessful) {

                   val destination = response.body()

                   Toast.makeText(this@DiseaseHistoryUpdate , "update"+destination.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("resp:",response.toString())
                   Log.e("resp:",response.toString())

                   val disease = Diseases()
                   //destination!!.get(position)
//                    destination?.let (
//                       et_condition_updated.setText(Diseases.known_condition)
//                       //spinner_disease_updated?.text1?.setText(disease.disease_status)
////                       text1.setText(disease.disease_status)
//                         et_noOfYrs_updated.setText(disease.disease_duration)
//                       et_startDate_updated.setText(disease.when_started)
//                       et_stopDate_updated.setText(disease.when_ended)
//                       notes_diseaseHistory_updated.setText(disease.disease_note)


//                   val destination = response.body()
//                   val disease = Diseases()
//                   destination?.let {
//                       et_condition_updated.setText(disease.known_condition)
//                       et_noOfYrs_updated.setText(disease.disease_duration)
//                       et_startDate_updated.setText(disease.when_started)
//                       et_stopDate_updated.setText(disease.when_ended)
//                       notes_diseaseHistory_updated.setText(disease.disease_note)
//                       //spinnerAllergy_update.getString(destination.spinerdata)
//
//                       //collapsing_toolbar.title = destination.city
//                   }!!






               } else {
                   Toast.makeText(this@DiseaseHistoryUpdate , "Failed to retrieve details", Toast.LENGTH_SHORT).show()
               }
           }

           override fun onFailure(call: Call<Diseases>, t: Throwable) {
           Toast.makeText(this@DiseaseHistoryUpdate , "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
           }
       })

   }

    private fun initUpdateButton(id: String) {
        btn_medicalHistory_update.setOnClickListener {

            val newDisease = Diseases()
            newDisease.known_condition = et_condition_updated!!.text.toString().trim()
            newDisease.disease_status = spinner_disease_updated?.getSelectedItem().toString()
            text1.setText(newDisease.disease_status)
            newDisease.disease_duration = et_noOfYrs_updated!!.text.toString().trim()
            newDisease.when_started = et_startDate_updated!!.text.toString().trim()
            newDisease.when_ended = et_stopDate_updated!!.text.toString().trim()
            newDisease.disease_note = notes_diseaseHistory_updated!!.text.toString().trim()
            newDisease.mobile_no = "8142529582"


            val destinationService = ServiceBuilder.buildService(Disease_service::class.java)
//            val requestCall = destinationService.updateDisease(mobile_no , known_conditionUpdate,disease_statusUpdate,disease_durationUpdate,when_startedUpdate,
//                    when_endedUpdate,disease_noteUpdate  )

            val requestCall = destinationService.updateDisease(newDisease)
            showLongToast(requestCall.toString())


            requestCall.enqueue(object: Callback<Diseases> {

                override fun onResponse(call: Call<Diseases>, response: Response<Diseases>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        var updatedDestination = response.body() // Use it or ignore It
                        intent = Intent(this@DiseaseHistoryUpdate,Disease_recyclerView::class.java)
                        startActivity(intent)
                        Toast.makeText(this@DiseaseHistoryUpdate  , "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@DiseaseHistoryUpdate  , "Failed to update item1", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Diseases>, t: Throwable) {
                    Toast.makeText(this@DiseaseHistoryUpdate  , "Failed to update item", Toast.LENGTH_SHORT).show()
                }
            })
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


}
