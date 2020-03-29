package com.example.myscope.activities.medical_history

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Spinner
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.diet.*
import kotlinx.android.synthetic.main.disease_history.*

import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Diet : BaseActivity() {

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        var spinner_diet: Spinner? = null

        super.onCreate(savedInstanceState)
        setContentView(R.layout.diet)

        activitiesToolbar()

        header!!.text = "Diet"

        spinner_diet = findViewById(R.id.spinner_diet)

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        val dietAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.diet_arrays))
        spinner_diet.adapter = dietAdapter


        btn_diet.setOnClickListener {

            assignValuestoVariable()
            validate(spinner_diet!!)
            apicall()

        }
    }

    private fun apicall() {
        val newDiet = Diseases()
        newDiet.diet = spinner_diet!!.selectedItem.toString()
        newDiet.mobile_no = mobile_no!!
        val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
        val requestCall = diseaseService.addDietList(newDiet)
        requestCall.enqueue(object : Callback<Diseases> {

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
                Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun assignValuestoVariable() {
        var dietS = spinner_diet!!.selectedItem.toString()

        validateSpinner(spinner_diet!!, dietS)


        if ((!dietS.equals("None"))) {
            showLongToast("save details")
        } else {
            showLongSnackBar("Please fill the required fields")
        }
    }
}
