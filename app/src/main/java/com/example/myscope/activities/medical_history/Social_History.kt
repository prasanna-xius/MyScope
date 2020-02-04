package com.example.myscope.activities.medical_history

import android.content.Intent
import android.os.Bundle

import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.MultiSelectionSpinner
import kotlinx.android.synthetic.main.activity_prescription_manual.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.social_history.*

class Social_History : BaseActivity() {

    var spinnersmoking: Spinner? = null
    var spinnerdrinking: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_history)

        activitiesToolbar()

        header!!.text = "Social History"

        spinnersmoking= findViewById(R.id.spinner_smoking)
        spinnerdrinking= findViewById(R.id.spinner_drinking)
        val  tobaccoUsage = findViewById<MultiSelectionSpinner>(R.id.tobacco_usage)

        tobaccoUsage.setItems(resources.getStringArray(R.array.tobacco_array))


        val smokingAdapter = ArrayAdapter(this,R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.smoking_arrays))
        spinnersmoking!!.adapter= smokingAdapter

        val drinkingAdapter = ArrayAdapter(this,R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.drinking_arrays))
        spinnerdrinking!!.adapter = drinkingAdapter


        btn_social.setOnClickListener{

            assignValuestoVariable()
            validate(spinnersmoking!!)
            validate(spinnerdrinking!!)

        }
    }

    private fun assignValuestoVariable() {

        var smoking = spinnersmoking!!.selectedItem.toString()
        var drinking = spinnerdrinking!!.selectedItem.toString()
        var tobaccoUsage = tobacco_usage!!.selectedItem.toString()

        validateSpinner(spinnersmoking!!, smoking)
        validateSpinner(spinnerdrinking!!, drinking)
        validateSpinner(tobacco_usage!!, tobaccoUsage)

           if ((!smoking.equals("None")) &&
                   !drinking.equals("None") &&
                   !tobaccoUsage.equals("None"))
           {


               showLongToast("save the details")

           }
        else{

               showLongSnackBar("Please fill the required fields")

           }
    }
}
