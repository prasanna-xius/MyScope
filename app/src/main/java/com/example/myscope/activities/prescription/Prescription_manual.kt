package com.example.myscope.activities.prescription

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_prescription_manual.*
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.MultiSelectionSpinner
import java.text.DateFormat
import java.util.*



class Prescription_manual : BaseActivity() {

    /* declaring variables */
    internal lateinit var myCalendar: Calendar
    private var startDateOrEndDAte = true
    var formulation: Spinner? = null
    var dose: Spinner? = null
    var isprescribed: Spinner? = null
    var datecheck: Boolean? = true
    var hospitalName: String? = null
    var doctorName: String? = null
    var formulationName: String? = null
    var doseStrength: String? = null
    var medicalCondition: String? = null
    var formulationId: String? = null
    var doseunit: String? = null
    var isprescribedunit: String? = null
    var howoftenvalue: String? = null
    var timeofmedicine: String? = null
    var startDate: String? = null
    var stopDate: String? = null
    var timePickerDialog:TimePickerDialog? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription_manual)

        header.setText("Prescription Manual")
        back_btn.setOnClickListener(){
            navigateToActivity(Intent(applicationContext,Prescriptionmanual_recyclerview::class.java))
        }
//        editbutton.setOnClickListener() {
//            showShortToast("edit button is clicked")
////            if (!parentview.equals(null)) {
////                parentview.getBackground().setAlpha(100);
//
////            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
////            }
//        }

        /* getting id's by findviewbyid method */
        formulation = findViewById<Spinner>(R.id.formulation_id)
        dose = findViewById<Spinner>(R.id.dose_unit)
        isprescribed = findViewById<Spinner>(R.id.is_prescribed)

        val how_oftenspinner = findViewById<MultiSelectionSpinner>(R.id.how_often_taken)
//        val timeSpinner = findViewById<MultiSelectionSpinner>(R.id.time_of_taken)
        time_of_taken.setOnClickListener(){
            val c = Calendar.getInstance()
            val hour = Calendar.HOUR
            val minute = Calendar.MINUTE
            time_of_taken.setText("")
//            timePickerDialog = TimePickerDialog(Prescription_manual::., Date().hours, Date().minutes, false)
//            timePickerDialog.enableMinutes(false)
        }

        /*Custom spinner code*/
        how_oftenspinner.setItems(resources.getStringArray(R.array.how_often_taken))

//        timeSpinner.setItems(resources.getStringArray(R.array.timings))

        // formulation spinner
        val formulationadapter = ArrayAdapter(this,
                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.formulation_array))
        formulation!!.adapter = formulationadapter

        // dose unit spinner
        val doseunitadapter = ArrayAdapter(this,
                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.dose_unit))
        dose!!.adapter = doseunitadapter

        // prescribed spinner
        val isprescribedadapter = ArrayAdapter(this,
                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.is_prescribed))
        isprescribed!!.adapter = isprescribedadapter


        /*Start date amd stop date validations */
        myCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            if (startDateOrEndDAte) {
                start_date.setText(date1)
            } else {
                stop_date.setText(date1)
            }
        }
         /*onclick listener for start date */
        start_date.setOnClickListener {
            // TODO Auto-generated method stub
            DatePickerDialog(this@Prescription_manual, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            startDateOrEndDAte = true
        }

        /*onclick listener for stop date */
        stop_date.setOnClickListener {
            DatePickerDialog(this@Prescription_manual, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            startDateOrEndDAte = false

        }

        /* Implementing on click listner */
        prescription_save.setOnClickListener()
        {
            assignValuetoVariable()
            validate(formulation!!)
            validate(dose!!)
            validate(isprescribed!!)
        }
    }

    /* function for checking null values in the required fields*/
    private fun assignValuetoVariable() {

        /*getting values from edit text views */
         hospitalName = hosp_name.text.toString()
         doctorName = doctor_name.text.toString()
         formulationName = brand_name.text.toString()
         doseStrength = dose_strength.text.toString()
         medicalCondition = medical_condition.text.toString()
         formulationId = formulation!!.selectedItem.toString()
         doseunit = dose!!.selectedItem.toString()
         isprescribedunit = isprescribed!!.selectedItem.toString()
         howoftenvalue = how_often_taken!!.selectedItem.toString()
         timeofmedicine = time_of_taken!!.text.toString()
         startDate = start_date.text.toString()
         stopDate = stop_date.text.toString()

        /*validating the inputs through function call */
        validateInput(doctor_name, doctorName!!)
        validateInput(hosp_name, hospitalName!!)
        validateInput(brand_name, formulationName!!)
        validateInput(dose_strength, doseStrength!!)
        validateInput(medical_condition, medicalCondition!!)
        validateSpinner(formulation!!, formulationId!!)
        validateSpinner(dose!!, doseunit!!)
        validateSpinner(isprescribed!!, isprescribedunit!!)
        validateSpinner(how_often_taken!!, howoftenvalue!!)
        validateInput((time_of_taken as EditText?)!!, timeofmedicine!!)
        datecheck = validateDate(start_date,stop_date,true)

        /*checking if the data is empty or not */
        if ((!doctorName.equals("")) &&
                (!hospitalName.equals("")) &&
                (!formulationName.equals("")) &&
                (!doseStrength.equals("")) &&
                (!medicalCondition.equals("") &&
                        (!formulationId.equals("None")) &&
                        (!doseunit.equals("None")) &&
                        (!isprescribedunit.equals("None")) &&
                        (!howoftenvalue.equals("None")) &&
                        (!timeofmedicine.equals("None")) &&
                        (!startDate.equals("")) &&
                        (!stopDate.equals(""))) && datecheck!!.equals(true)) {
            showLongToast("Values are saved....Thank you!! ")
            val PrescriptionManualDataClass = PrescriptionManualDataClass(doctorName!!,
                    hospitalName!!, formulationName!!, doseStrength!!, medicalCondition!!, formulationId!!, doseunit!!,
                    isprescribedunit!!, howoftenvalue!!, timeofmedicine!!, startDate!!, stopDate!!)
            showLongToast(PrescriptionManualDataClass.toString())
        } else {
        }
    }
}

private operator fun Float.invoke(d: Double) {

}


