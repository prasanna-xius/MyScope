package com.example.myscope.activities.medical_history

import android.app.DatePickerDialog
import android.os.Bundle

import android.widget.*
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_prescription_manual.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.disease_history.*
import java.text.DateFormat
import java.util.*

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
            duration(et_startDate,et_stopDate,et_noOfYrs)
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
//            validateDate(et_startDate,et_stopDate,false)
        }

    }
    private fun assignValuestoVariable() {

        val condition = et_condition.text.toString()
        val diseases = spinner_disease!!.selectedItem.toString()
       val disease_duration = et_noOfYrs.text.toString()
        validateInput(et_condition, condition)
//        validateInput(et_noOfYrs , disease_duration)
        errorDisplay(et_noOfYrs)
        validateSpinner(spinner_disease!!, diseases)

        if ((condition != "") &&
                (diseases != "None")
                && (disease_duration != "")) {
            showLongToast("save the details")
        }
        else {
             }
    }
}
