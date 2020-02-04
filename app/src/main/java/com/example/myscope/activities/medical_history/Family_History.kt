package com.example.myscope.activities.medical_history


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.family_history.*

import kotlinx.android.synthetic.main.medical_history.*
import java.util.*

class Family_History : BaseActivity() {


    internal lateinit var myCalendar: Calendar
    private var startDateOrEndDAte = true
    var relationshipSpinner: Spinner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.family_history)

        activitiesToolbar()

        header!!.text = "Family History"
        relationshipSpinner = findViewById<Spinner>(R.id.spinner_family)

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.Relationship_arrays))
        relationshipSpinner!!.adapter = adapter


        btn_family.setOnClickListener {

            assignValuestoVariable()
            validate(relationshipSpinner!!)
        }


    }

    private fun assignValuestoVariable() {

        var familyCondition = et_family_condition.text.toString()
        var relationship = relationshipSpinner!!.selectedItem.toString()
        validateInput(et_family_condition,familyCondition)
        validateSpinner(relationshipSpinner!!,relationship)

        if ((familyCondition != "")&&
                (relationship != "None"))
        {

            showLongToast("save the details")

        }
        else{



        }


    }
}
