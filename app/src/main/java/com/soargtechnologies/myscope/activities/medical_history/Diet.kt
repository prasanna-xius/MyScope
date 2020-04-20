package com.soargtechnologies.myscope.activities.medical_history

import android.content.Intent
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Spinner
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Disease_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.custom_spinner.*
import kotlinx.android.synthetic.main.custom_spinner.text1
import kotlinx.android.synthetic.main.diet.*

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



       // loadDetails()


        val checkboxvalue = ServiceBuilder.buildService(Disease_service::class.java)

        val requestCall = checkboxvalue.getDiet(mobile_no!! )

        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>>{

            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>>) {

                val length = response.body()?.size

            //    showLongToast(length.toString())

                if (length!! > 0){

//                    showLongToast("successful")

                    loadDetails()

                }
                else{
                    btn_diet.setOnClickListener {

                        assignValuestoVariable()
                        validate(spinner_diet!!)
                        apicall()

                    }
                }
            }

            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {

          //      showLongToast("failureee")
            }
        })

    }

    private fun apicall() {

        val newDiet = Diseases()

        newDiet.diet = spinner_diet!!.selectedItem.toString()
        newDiet.diet_saved_on = datesetvalue()

        newDiet.mobile_no = mobile_no!!

        val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
        val requestCall = diseaseService.addDietList(newDiet)
        requestCall.enqueue(object : Callback<Diseases> {

            override fun onResponse(call: Call<Diseases>, resp: Response<Diseases>) {

                if (resp.isSuccessful) {
                   // Use it or ignore it
               //     Toast.makeText(applicationContext, "Successfully Added", Toast.LENGTH_SHORT).show()

                    finish()
                } else {
               //     Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Diseases>, t: Throwable) {
             //   Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun loadDetails() {



        btn_diet_updated.visibility = View.VISIBLE
        btn_diet.visibility = View.GONE


        val diseaseService = ServiceBuilder.buildService(Disease_service::class.java)
        val requestCall = diseaseService.getDiet(mobile_no!!)


        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>> {

            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>> ) {

                val destination = response.body()
                if(destination!!.isEmpty()){
                    btn_diet_updated.visibility = View.GONE
                    btn_diet.visibility = View.VISIBLE
                }

                val diet = destination?.first()
//                Log.d("resp:", response.toString())
//                Log.e("resp:", response.toString())


                diet?.let {

                    spinner_diet?.text1?.setText(diet.diet)
                    btn_diet_updated.visibility = View.VISIBLE
                    btn_diet.visibility = View.GONE

                    initUpdateButton()
                }

            }
            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {
         //       Toast.makeText(this@Diet , "Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initUpdateButton() {

        btn_diet_updated.setOnClickListener {



            val item = spinner_diet.text1.text.toString()
         //   showLongToast(item)

            val newDiet = Diseases()

            if(!item.equals(null)) {
                newDiet.diet = item
            } else {
                newDiet.diet = spinner_diet?.getSelectedItem().toString()
                text1.setText(newDiet.diet)
            }

            newDiet.diet_updated_on = datesetvalue()
            newDiet.mobile_no = mobile_no


            val destinationService = ServiceBuilder.buildService(Disease_service::class.java)
            val requestCall = destinationService.updateDiet(newDiet)

            requestCall.enqueue(object: Callback<Diseases> {

                override fun onResponse(call: Call<Diseases>, response: Response<Diseases>) {

                    if (response.isSuccessful)
                    {
                      finish()
                    //    Toast.makeText(this@Diet, "Item Updated Successfully", Toast.LENGTH_SHORT).show()

                    }
                    else {
                 //       Toast.makeText(this@Diet  , "Failed at else part in update", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Diseases>, t: Throwable) {
            //        Toast.makeText(this@Diet, "Failed to update item", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

    private fun assignValuestoVariable() {
        var dietS = spinner_diet!!.selectedItem.toString()

        validateSpinner(spinner_diet!!, dietS)


        if ((!dietS.equals("None"))) {
     //       showLongToast("save details")
        } else {
            showLongSnackBar("Please fill the required fields")
        }
    }

}
