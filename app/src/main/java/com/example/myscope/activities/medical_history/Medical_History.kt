package com.example.myscope.activities.medical_history

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.custom_spinner.*
import kotlinx.android.synthetic.main.medical_history.*
import java.text.SimpleDateFormat
import java.util.*

class Medical_History : BaseActivity() {

    internal lateinit var myCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medical_history)

        showToolbar1()
        header!!.text = "Medical History"

   
        var spinner_disease = findViewById(R.id.spinner_disease) as Spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.disease_arrays, R.layout.spinner_dropdown_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner_disease.setAdapter(adapter)

        errorRemove(condition)
        spinnerSet(spinner_disease,resources.getStringArray(R.array.disease_arrays))


        myCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        et_startDate.setOnClickListener {
            // TODO Auto-generated method stub
            DatePickerDialog(this, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)

        // Inflate the layout using LayoutInflater
        val view: View = layoutInflater.inflate(
                R.layout.custom_spinner,
                linearlayout, // Root layout to attach the view
                false
        )
        val label = view.findViewById<TextView>(R.id.textBox)
//         label.setText(howOftenTaken)

//        val timpicker = view.findViewById<TimePicker>(R.id.customtimepicker)
//        val checkbox = view.findViewById<View>(R.id.checkbox)
        }
    private fun updateLabel() {
        val myFormat = "MM/dd/yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        et_startDate.text = sdf.format(myCalendar.time)
        }


}
