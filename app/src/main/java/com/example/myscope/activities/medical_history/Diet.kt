package com.example.myscope.activities.medical_history

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Spinner
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.diet.*

import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*


class Diet : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        var spinner_diet: Spinner? = null

        super.onCreate(savedInstanceState)
        setContentView(R.layout.diet)

        showToolbar()
        header!!.text = "Diet"

        spinner_diet = findViewById(R.id.spinner_diet)


        val dietAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.diet_arrays))
        spinner_diet.adapter = dietAdapter


        btn_diet.setOnClickListener{

            assignValuestoVariable()
            validate(spinner_diet!!)

        }
    }

    private fun assignValuestoVariable() {
        var dietS = spinner_diet!!.selectedItem.toString()

        validateSpinner(spinner_diet!!,dietS)


        if ((!dietS.equals("None")))
        {
            showLongToast("save details")
        }
        else{
            showLongSnackBar("Please fill the required fields")
        }
    }
