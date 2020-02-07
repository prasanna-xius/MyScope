package com.example.myscope.activities.medical_history

import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle

import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.medicationhistory.*
import java.text.SimpleDateFormat
import java.util.*

@TargetApi(Build.VERSION_CODES.N)
class MedicationHistory : AppCompatActivity(), AdapterView.OnItemSelectedListener {



    var namemed: String? = null;
    var dose: String? = null;
    var reason: String? = null;
    var notesmed: String? = null;

    var datestartmed: String? = null;
    var dateendmed: String? = null
    private var isSpinnerOneInitial = true;
    private var isSpinnertwoInitial = true
    private var isSpinnerThreeInitial = true;
    private var isSpinnerFourInitial = true
    var etname_med: EditText? = null;
    var etdose_med: EditText? = null;
    var etreason_med: EditText? = null
    var etnotes_med: EditText? = null
    var flag: Boolean? = true
    var sprOneItem: String? = null
    var sprThreeItem: String? = null;
    var sprFourItem: String? = null
    var sprTwoItem: String? = null
    var sdf1: SimpleDateFormat? = null
    var sdf2: SimpleDateFormat? = null
    var button_date_StartMh: Button? = null
    var button_date_EndMh: Button? = null
    var textview_Startdate: TextView? = null
    var textview_Enddate: TextView? = null
    var cal_StartMh = Calendar.getInstance()
    var cal_EndMh = Calendar.getInstance()
    internal var spinnerMedication1: Spinner? = null
    internal var spinnerMedication2: Spinner? = null
    internal var spinnerMedication3: Spinner? = null
    internal var spinnerMedication4: Spinner? = null
    //internal var spinnerMedication: Spinner? = null
    var btn_medication: Button? = null


    private var awesomeValidation: AwesomeValidation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medicationhistory)
        spinnerMedication1 = findViewById<View>(R.id.spinner_how_often_taken) as Spinner
        spinnerMedication2 = findViewById<View>(R.id.spinner_formulation) as Spinner
        spinnerMedication3 = findViewById<View>(R.id.spinner_dose_unit) as Spinner
        spinnerMedication4 = findViewById<View>(R.id.spinner_is_prescribed) as Spinner
        btn_medication = findViewById<View>(R.id.btn_medication) as Button

        textview_Enddate = this.textviewEnddate_MH
        textview_Startdate = this.textviewStartdate_medicalHistory

        button_date_StartMh = this.buttonStartdate_medicalHistory
        button_date_EndMh = this.buttonEnddate_MH

        etname_med = findViewById(R.id.et_name)
        etdose_med = findViewById(R.id.et_dose_strength)
        etreason_med = findViewById(R.id.et_reason)
        etnotes_med = findViewById(R.id.et_notes_med)


        textview_Startdate!!.text = " "
        textview_Enddate!!.text = " "

        awesomeValidation = AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation!!.addValidation(this, R.id.et_name,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);


        var howoftentakendata = resources.getStringArray(R.array.how_often_taken_arrays)

        var formulationdata = resources.getStringArray(R.array.formulation_arrays)

        var dosedata = resources.getStringArray(R.array.dose_unit_arrays)

        var prescribeddata = resources.getStringArray(R.array.is_prescribed_arrays)


        val aa1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, howoftentakendata)
        aa1.setDropDownViewResource(R.layout.spinner_item_layout)
        spinnerMedication1!!.setAdapter(aa1)
        spinnerMedication1!!.setOnItemSelectedListener(this)


        val aa2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, formulationdata)
        aa2.setDropDownViewResource(R.layout.spinner_item_layout)
        spinnerMedication2!!.setAdapter(aa2)
        spinnerMedication2!!.setOnItemSelectedListener(this)


        val aa3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, dosedata)
        aa3.setDropDownViewResource(R.layout.spinner_item_layout)
        spinnerMedication3!!.setAdapter(aa3)
        spinnerMedication3!!.setOnItemSelectedListener(this)


        val aa4 = ArrayAdapter(this, android.R.layout.simple_spinner_item, prescribeddata)
        aa4.setDropDownViewResource(R.layout.spinner_item_layout)
        spinnerMedication4!!.setAdapter(aa4)
        spinnerMedication4!!.setOnItemSelectedListener(this)



        btn_medication?.setOnClickListener {

            namemed = etname_med!!.getText().toString()
            dose = etdose_med!!.getText().toString()
            reason = etreason_med!!.getText().toString()
            notesmed = etnotes_med!!.getText().toString()
            datestartmed = textview_Startdate?.getText().toString()
            dateendmed = textview_Enddate?.getText().toString()


            if (awesomeValidation!!.validate() && sprOneItem != null && dateendmed!!.compareTo(datestartmed!!)>=0) {


                Toast.makeText(getApplicationContext(), "data:" + namemed + " " + dose + " " + reason + " " + " " + notesmed + " " +
                        sprOneItem + " " + sprTwoItem + " " + sprThreeItem + " " + sprFourItem + " " + datestartmed + " " + dateendmed, Toast.LENGTH_LONG).show()


                // Toast.makeText(getApplicationContext(), "data:" + date, Toast.LENGTH_LONG).show()
            } else {

                if(dateendmed!!.compareTo(datestartmed!!)<0) {
                    textview_Startdate!!.setText("")
                    textview_Enddate!!.setText("")
                    textview_Enddate!!.setError("")

                    val snack = Snackbar.make(it!!, "Wrong Date selected.", Snackbar.LENGTH_LONG)
                    snack.show()
                }

                val snack = Snackbar.make(it!!, "Mandatory field cannot be left blank.", Snackbar.LENGTH_LONG)
                snack.show()
            }

        }
        val dateSetListenerSatar = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view_start: DatePicker, year_start: Int, monthOfYear_start: Int,
                                   dayOfMonth_start: Int) {
                cal_StartMh.set(Calendar.YEAR, year_start)
                cal_StartMh.set(Calendar.MONTH, monthOfYear_start)
                cal_StartMh.set(Calendar.DAY_OF_MONTH, dayOfMonth_start)


                updateDateInViewStart()
            }
        }

        val dateSetListenerEnd = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view_end: DatePicker, year_end: Int, monthOfYear_end: Int,
                                   dayOfMonth_end: Int) {

                cal_EndMh.set(Calendar.YEAR, year_end)
                cal_EndMh.set(Calendar.MONTH, monthOfYear_end)
                cal_EndMh.set(Calendar.DAY_OF_MONTH, dayOfMonth_end)

                updateDateInViewEnd()
            }
        }


        button_date_StartMh!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@MedicationHistory,
                        dateSetListenerSatar,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal_StartMh.get(Calendar.YEAR),
                        cal_StartMh.get(Calendar.MONTH),
                        cal_StartMh.get(Calendar.DAY_OF_MONTH)).show()
            }

        })


        button_date_EndMh!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@MedicationHistory,
                        dateSetListenerEnd,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal_EndMh.get(Calendar.YEAR),
                        cal_EndMh.get(Calendar.MONTH),
                        cal_EndMh.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    private fun updateDateInViewStart() {

        val myFormat = "MM/dd/yyyy" // mention the format you need
        sdf1 = SimpleDateFormat(myFormat, Locale.US)
        textview_Startdate!!.text = sdf1?.format(cal_StartMh.getTime())
    }

    private fun updateDateInViewEnd() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        sdf2 = SimpleDateFormat(myFormat, Locale.US)

        textview_Enddate!!.text = sdf2?.format(cal_EndMh.getTime())


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        //sprOneItem = parent?.getItemAtPosition(position) as String?

        val posSpnrOne = spinnerMedication1!!.getSelectedItemPosition()
        val posSpnrtwo = spinnerMedication2!!.getSelectedItemPosition()
        val posSpnrthree = spinnerMedication3!!.getSelectedItemPosition()
        val posSpnrfour = spinnerMedication4!!.getSelectedItemPosition()

        (view as TextView).setTextColor(Color.parseColor("#ffffff"))

        when (parent?.id) {
            R.id.spinner_how_often_taken -> {


                if (isSpinnerOneInitial) {
                    isSpinnerOneInitial = false;
                    //Toast.makeText(this, "select item", Toast.LENGTH_LONG).show()
                }
                if (!isSpinnerOneInitial && posSpnrOne != 0) {

                    // Toast.makeText(this,"selected item"+parent?.getItemAtPosition(position),Toast.LENGTH_LONG).show()

                    sprOneItem = parent?.getItemAtPosition(position) as String?
                    //Toast.makeText(this, "selected item" + sprOneItem, Toast.LENGTH_LONG).show()
                }
            }
            R.id.spinner_formulation -> {


                if (isSpinnertwoInitial) {
                    isSpinnertwoInitial = false;
                    //Toast.makeText(this, "select item", Toast.LENGTH_LONG).show()
                }
                if (!isSpinnertwoInitial && posSpnrtwo != 0) {

                    // Toast.makeText(this,"selected item"+parent?.getItemAtPosition(position),Toast.LENGTH_LONG).show()

                    sprTwoItem = parent?.getItemAtPosition(position) as String?
                    // Toast.makeText(this, "selected item" + sprTwoItem, Toast.LENGTH_LONG).show()
                }

            }

            R.id.spinner_dose_unit -> {


                if (isSpinnerThreeInitial) {
                    isSpinnerThreeInitial = false;
                    //Toast.makeText(this, "select item", Toast.LENGTH_LONG).show()
                }
                if (!isSpinnerThreeInitial && posSpnrthree != 0) {

                    // Toast.makeText(this,"selected item"+parent?.getItemAtPosition(position),Toast.LENGTH_LONG).show()

                    sprThreeItem = parent?.getItemAtPosition(position) as String?
                    //Toast.makeText(this, "selected item" + sprThreeItem, Toast.LENGTH_LONG).show()
                }

            }

            R.id.spinner_is_prescribed -> {


                if (isSpinnerFourInitial) {
                    isSpinnerFourInitial = false;
                    //Toast.makeText(this, "select item", Toast.LENGTH_LONG).show()
                }
                if (!isSpinnerFourInitial && posSpnrfour != 0) {

                    // Toast.makeText(this,"selected item"+parent?.getItemAtPosition(position),Toast.LENGTH_LONG).show()

                    sprFourItem = parent?.getItemAtPosition(position) as String?
                    //Toast.makeText(this, "selected item" + sprFourItem, Toast.LENGTH_LONG).show()
                }

            }

        }

    }

    /*fun  dateCheck():Boolean {
        if (dateendmed!!.compareTo(datestartmed!!)>=0) {




            return true
        }
        if (cal_EndMh.time!! >= cal_StartMh.time!!) {




            return false
        }


    }*/
}
