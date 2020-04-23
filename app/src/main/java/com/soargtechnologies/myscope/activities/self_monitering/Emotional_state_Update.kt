package com.soargtechnologies.myscope.activities.self_monitering

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Self_monitoring_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_emotional_state__update.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Emotional_state_Update : BaseActivity() {

    var position: Int = 1;
    var spinner_Trauma: Spinner? = null
    var spinner_Emotional: Spinner? = null
    var emotionalId: Int = 0
    var boolean: Boolean? = true
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotional_state__update)

        activitiesToolbar()
        header!!.text = "Emotional State"

        spinner_Emotional = findViewById<Spinner>(R.id.spinner_emotional_update)
        spinner_Trauma = findViewById<Spinner>(R.id.spinner_trauma_update)

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)


        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, resources.getStringArray(R.array.emotional_status_dropdown))
        spinner_Emotional!!.adapter = adapter

        val adaptor = ArrayAdapter(this, R.layout.spinner_dropdown_item, resources.getStringArray(R.array.reason_trauma_dropdown))
        spinner_Trauma!!.adapter = adaptor


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
        val requestCall = diseaseService.getEmotional(mobile_no.toString())

        requestCall.enqueue(object : retrofit2.Callback<List<Self_dataClass>> {

            override fun onResponse(call: Call<List<Self_dataClass>>, response: Response<List<Self_dataClass>>) {

                val destination = response.body()
//                Log.d("resp:", response.toString())
//                Log.e("resp:", response.toString())
                val newEmotionalStatus = destination?.get(position)
                emotionalId = newEmotionalStatus?.emotional_id!!

                newEmotionalStatus?.let {

                    et_score_of_wellness_update.setText(newEmotionalStatus.score_of_wellness)
                   spinner_emotional_update.text1!!.setText(newEmotionalStatus.emotional_status)
                    spinner_trauma_update.text1!!.setText(newEmotionalStatus.any_emotional_trauma)
                    et_duration_update.setText(newEmotionalStatus.duration)
                    et_reason_of_trauma_update.setText(newEmotionalStatus.reason_of_trauma)
                }

            }

            override fun onFailure(call: Call<List<Self_dataClass>>, t: Throwable) {
                Toast.makeText(this@Emotional_state_Update, "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initUpdateButton(id: String) {

        btn_emotionalState_update.setOnClickListener {

            assignValuestoVariable()
            validate(spinner_emotional_update!!)
            validate(spinner_trauma_update!!)


        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, Emotional_state_recyclerView::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {

        val ARG_ITEM_ID: String = "item_id"
    }

    private fun assignValuestoVariable() {

        val Score = et_score_of_wellness_update.text.toString()
        val Emotional = spinner_emotional_update!!.selectedItem.toString()
        val Trauma = spinner_trauma_update!!.selectedItem.toString()
        val emotional_duration = et_duration_update!!.text.toString()
        validateInput(et_score_of_wellness_update!!, Score)
        validateInput(et_duration_update!! , emotional_duration)
        validateSpinner(spinner_emotional_update!!, Emotional)
        validateSpinner(spinner_trauma_update!!, Trauma)

        if ((Score != "") &&
                (Emotional != "None")  &&
                (Trauma != "None")
                && (emotional_duration != "")) {
               showLongToast("save the details")
              sucess()

        }

        else {

            showLongSnackBar("Please fill the required fields")

        }
    }

    private fun sucess() {


        val item = spinner_emotional_update.text1.text.toString()
        showLongToast(item)

        val item1 = spinner_trauma_update.text1.text.toString()
        showLongToast(item1)

        val newEmotionalStatus = Self_dataClass()

        if(!item.equals(null)) {
            newEmotionalStatus.emotional_status = item
        }
        else {
            newEmotionalStatus.emotional_status = spinner_emotional_update?.getSelectedItem().toString()
            text1.setText(newEmotionalStatus.emotional_status)
        }

        if(!item1.equals(null)) {
            newEmotionalStatus.any_emotional_trauma = item
        }
        else {
            newEmotionalStatus.any_emotional_trauma = spinner_trauma_update?.getSelectedItem().toString()
            text1.setText(newEmotionalStatus.any_emotional_trauma)
        }

        newEmotionalStatus.score_of_wellness = et_score_of_wellness_update!!.text.toString().trim()
        newEmotionalStatus.duration = et_duration_update!!.text.toString().trim()
        newEmotionalStatus.reason_of_trauma = et_reason_of_trauma_update!!.text.toString().trim()
        newEmotionalStatus.emotional_updated_on = datesetvalue()

        newEmotionalStatus.mobile_no = mobile_no!!
        newEmotionalStatus.emotional_id = emotionalId

        val destinationService = ServiceBuilder.buildService(Self_monitoring_service::class.java)
        val requestCall = destinationService.updateEmotional(newEmotionalStatus)

        requestCall.enqueue(object: Callback<Self_dataClass> {

            override fun onResponse(call: Call<Self_dataClass>, response: Response<Self_dataClass>) {
                val resp = response
                if (response.isSuccessful) {

                    var updatedDestination = response.body() // Use it or ignore It
                    Toast.makeText(this@Emotional_state_Update, "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                    finish() // Move back to DestinationListActivity
                } else {
                    intent = Intent(this@Emotional_state_Update,Emotional_state_recyclerView::class.java)
                    intent.putExtra("position" , position)
                    startActivity(intent)
                    Toast.makeText(this@Emotional_state_Update  , "Failed to update item1", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Self_dataClass>, t: Throwable) {
                Toast.makeText(this@Emotional_state_Update, "Failed to update item", Toast.LENGTH_SHORT).show()
            }
        })


    }
}

