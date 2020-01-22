package com.example.myscope.activities

import androidx.appcompat.app.AppCompatActivity

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_prescription_manual.*
import kotlinx.android.synthetic.main.custom_spinner.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.myscope.R


 class Prescription_manual : BaseActivity() {
//     var arrayList: ArrayList<Values> = ArrayList()

     internal lateinit var myCalendar: Calendar
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_prescription_manual)
//         val multiselection = findViewById<MultiSpinner>()
         val howOftenTaken = resources.getStringArray(R.array.how_often_taken)
         val timings = resources.getStringArray(R.array.timings)
//         val ms = findViewById<multispinner>(R.id.time_of_taken)

         errorRemove(doctor_name)
         errorRemove(hosp_name)
         errorRemove(formulation_name)
         errorRemove(dose_strength)
         errorRemove(medical_condition)


         // access the spinner
         spinnerSet(formulation_id, resources.getStringArray(R.array.formulation_array))
         spinnerSet(dose_unit, resources.getStringArray(R.array.dose_unit))
         spinnerSet(is_prescribed, resources.getStringArray(R.array.is_prescribed))




         prescription_save.setOnClickListener {
             if (doctor_name.text.toString().equals("")) {
                 errorDisplay(doctor_name)
             } else if (hosp_name.text.toString().equals("")) {
                 errorDisplay(hosp_name)
             } else if (formulation_name.text.toString().equals("")) {
                 errorDisplay(formulation_name)
             } else if (dose_strength.text.toString().equals("")) {
                 errorDisplay(dose_strength)
             } else if (medical_condition.text.toString().equals("")) {
                 errorDisplay(medical_condition)
             }
         }

         myCalendar = Calendar.getInstance()
         val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
             // TODO Auto-generated method stub
             myCalendar.set(Calendar.YEAR, year)
             myCalendar.set(Calendar.MONTH, monthOfYear)
             myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
             updateLabel()
         }

         start_date.setOnClickListener {
             // TODO Auto-generated method stub
             DatePickerDialog(this@Prescription_manual, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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

         val timpicker = view.findViewById<TimePicker>(R.id.customtimepicker)
         val checkbox = view.findViewById<View>(R.id.checkbox)
         linearlayout.addView(view, 0)


     }


     private fun updateLabel() {
         val myFormat = "MM/dd/yyyy" //In which you need put here
         val sdf = SimpleDateFormat(myFormat, Locale.US)
         start_date.text = sdf.format(myCalendar.time)
     }

 }
