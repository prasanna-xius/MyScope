package com.example.myscope.activities.medical_history

import android.app.DatePickerDialog
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View

import android.widget.*
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.medical_history.*
import java.text.DateFormat
import java.util.*

class Medical_History : BaseActivity() {
    internal lateinit var myCalendar: Calendar
    private var startDateOrEndDAte = true
    var spinner_disease: Spinner? = null
    var boolean: Boolean? = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medical_history)
        showToolbar()
        header!!.text = "Medical History"
        spinner_disease = findViewById<Spinner>(R.id.spinner_disease)

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.disease_arrays))
        spinner_disease!!.adapter = adapter

        myCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            if (startDateOrEndDAte) {
                et_startDate.setText(date1)
            } else {
                et_stopDate.setText(date1)
            }
        }

        et_startDate.setOnClickListener {
            // TODO Auto-generated method stub
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
            boolean = validateDate(et_startDate, et_stopDate, false)
        }

    }


    private fun assignValuestoVariable() {

        val condition = et_condition.text.toString()
        val diseases = spinner_disease!!.selectedItem.toString()
        val startDate = et_startDate.text.toString()
        val stopDate = et_stopDate.text.toString()
        validateInput(et_condition, condition)
        validateSpinner(spinner_disease!!, diseases)

        if ((condition != "") &&
                (diseases != "None") &&
                (startDate != "") &&
                (stopDate != "") &&
                boolean!!.equals(false)) {
            showLongToast("save the details")
        }
        else {
             }
    }
}


