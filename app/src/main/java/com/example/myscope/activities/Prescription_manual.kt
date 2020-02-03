package com.example.myscope.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_prescription_manual.*
import com.example.myscope.R
import android.widget.Spinner
import java.text.DateFormat
import java.util.*
import kotlin.system.exitProcess

class Prescription_manual : BaseActivity() {

    /* declaring variables */
    internal lateinit var myCalendar: Calendar
    private var startDateOrEndDAte = true
    var formulation: Spinner? = null
    var dose: Spinner? = null
    var isprescribed: Spinner? = null
    var datecheck: Boolean? = true

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription_manual)

//        val linearlayout = findViewById<LinearLayout>(R.id.parentview)
//        editbutton.setOnClickListener(){
//
//                linearlayout.background!!.alpha(100)
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        }

        /* getting id's by findviewbyid method */
        formulation = findViewById<Spinner>(R.id.formulation_id)
        dose = findViewById<Spinner>(R.id.dose_unit)
        isprescribed = findViewById<Spinner>(R.id.is_prescribed)

        val how_oftenspinner = findViewById<MultiSelectionSpinner>(R.id.how_often_taken)
        val timeSpinner = findViewById<MultiSelectionSpinner>(R.id.time_of_taken)

        /*Custom spinner code*/
        how_oftenspinner.setItems(resources.getStringArray(R.array.how_often_taken))
        timeSpinner.setItems(resources.getStringArray(R.array.timings))

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
        var hospitalName = hosp_name.text.toString()
        var doctorName = doctor_name.text.toString()
        var formulationName = formulation_name.text.toString()
        var doseStrength = dose_strength.text.toString()
        var medicalCondition = medical_condition.text.toString()
        var formulationId = formulation!!.selectedItem.toString()
        var doseunit = dose!!.selectedItem.toString()
        var isprescribedunit = isprescribed!!.selectedItem.toString()
        var howoftenvalue = how_often_taken!!.selectedItem.toString()
        var timeofmedicine = time_of_taken!!.selectedItem.toString()
        var startDate = start_date.text.toString()
        var stopDate = stop_date.text.toString()

        /*validating the inputs through function call */
        validateInput(doctor_name, doctorName)
        validateInput(hosp_name, hospitalName)
        validateInput(formulation_name, formulationName)
        validateInput(dose_strength, doseStrength)
        validateInput(medical_condition, medicalCondition)
        validateSpinner(formulation!!, formulationId)
        validateSpinner(dose!!, doseunit)
        validateSpinner(isprescribed!!, isprescribedunit)
        validateSpinner(how_often_taken!!, howoftenvalue)
        validateSpinner(time_of_taken!!, timeofmedicine)
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
            val PrescriptionManualDataClass = PrescriptionManualDataClass(doctorName,
                    hospitalName,formulationName,doseStrength,medicalCondition,formulationId,doseunit,
                    isprescribedunit ,howoftenvalue,timeofmedicine,startDate,stopDate)
            showLongToast(PrescriptionManualDataClass.toString())
        } else {

        }
    }

}

//private operator fun Int.invoke(i: Int) {
//
//}

