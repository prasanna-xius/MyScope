package com.example.myscope.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_prescription_manual.*

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.myscope.R
import com.example.myscope.activities.MultiSelectionSpinner


class Prescription_manual : BaseActivity() {
//     var arrayList: ArrayList<Values> = ArrayList()

     internal lateinit var myCalendar: Calendar
     @SuppressLint("ResourceAsColor")
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_prescription_manual)

         errorRemove(doctor_name)
         errorRemove(hosp_name)
         errorRemove(formulation_name)
         errorRemove(dose_strength)
         errorRemove(medical_condition)


         // access the spinner
         spinnerSet(formulation_id, resources.getStringArray(R.array.formulation_array))
         spinnerSet(dose_unit, resources.getStringArray(R.array.dose_unit))
         spinnerSet(is_prescribed, resources.getStringArray(R.array.is_prescribed))

         /*Custom spinner code*/
         val spinner = findViewById(R.id.how_often_taken) as MultiSelectionSpinner
         val timeSpinner = findViewById(R.id.time_of_taken) as MultiSelectionSpinner
         spinner.setItems(resources.getStringArray(R.array.how_often_taken))
         timeSpinner.setItems(resources.getStringArray(R.array.timings))


        /* Implementing on click listner and checking null values */
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
             showLongToast("This is prescription page "+ hosp_name.text.toString())
         }

         /*Start date amd stop date validations */
         myCalendar = Calendar.getInstance()
         val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
             // TODO Auto-generated method stub
             myCalendar.set(Calendar.YEAR, year)
             myCalendar.set(Calendar.MONTH, monthOfYear)
             myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
         }

         start_date.setOnClickListener {
             // TODO Auto-generated method stub
             DatePickerDialog(this@Prescription_manual,R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                     myCalendar.get(Calendar.DAY_OF_MONTH)).show()
             CalendarValidation()
             val myFormat = "MM/dd/yyyy" //In which you need put here
             val sdf = SimpleDateFormat(myFormat, Locale.US)
             start_date.text = sdf.format(myCalendar.time)

         }
         stop_date.setOnClickListener{
             DatePickerDialog(this@Prescription_manual,R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                     myCalendar.get(Calendar.DAY_OF_MONTH)).show()
             CalendarValidation()
             val myFormat = "MM/dd/yyyy" //In which you need put here
             val sdf = SimpleDateFormat(myFormat, Locale.US)
             stop_date.text = sdf.format(myCalendar.time)
         }

     }
     fun CalendarValidation() {
        var myCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)


        }
    }
 }
