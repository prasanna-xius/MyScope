package com.example.myscope.activities.medical_history
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

import android.graphics.Color
import android.os.Bundle
import android.util.Log


import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.example.myscope.R
import com.example.myscope.models.MedicalHistoryModelActivity
import com.example.myscope.services.MedicalHistoryService
import com.example.myscope.services.ServiceBuilder

import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.medicationhistory.*
import kotlinx.android.synthetic.main.spinner_dropdown_item_how_often.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

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
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    private var awesomeValidation: AwesomeValidation? = null


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medicationhistory)

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

        //spinnerMedication1 = findViewById<View>(R.id.spinner_how_often_taken) as Spinner
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


        var how_oftenspinner = findViewById<MultiSelectionSpinner>(R.id.spinner_how_often_taken)

        var formulationdata = resources.getStringArray(R.array.formulation_arrays)

        var dosedata = resources.getStringArray(R.array.dose_unit_arrays)

        var prescribeddata = resources.getStringArray(R.array.is_prescribed_arrays)


        how_oftenspinner.setItems(resources.getStringArray(R.array.how_often_taken_arrays))

        val adapter1 = ArrayAdapter(this, R.layout.spinner_dropdown_item_how_often,
                resources.getStringArray(R.array.how_often_taken_arrays))
        how_oftenspinner!!.adapter = adapter1
        //spinnerMedication1!!.setAdapter(aa1)
        //spinnerMedication1!!.setOnItemSelectedListener(this)


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
       validateDate(textview_Startdate!!, textview_Enddate!!,true)
//                   && sprOneItem != null

            if (awesomeValidation!!.validate()&& validateDate(textview_Startdate!!, textview_Enddate!!,true)) {


                val newmedication = MedicalHistoryModelActivity()
                newmedication.medicationname = et_name!!.text.toString().trim()
                newmedication.medicationnotes = et_notes_med!!.text.toString().trim()
                //newmedication. = et_body_location_update!!.text.toString().trim()
                newmedication.reason = et_reason!!.text.toString().trim()
                newmedication.strength = et_dose_strength!!.text.toString().trim()
                newmedication.startdate = textviewStartdate_medicalHistory!!.text.toString().trim()
                newmedication.enddate = textviewEnddate_MH!!.text.toString().trim()
                newmedication.how_often_taken = spinner_how_often_taken!!.selectedStrings.toString()
                newmedication.formulation = sprTwoItem.toString().trim()
                newmedication.isprescribed = sprFourItem.toString().trim()
                newmedication.doseunit = sprThreeItem.toString().trim()
                newmedication.mobile_no = mobile_no!!
                Toast.makeText(applicationContext,newmedication.how_often_taken,Toast.LENGTH_LONG).show()

                //newMedicalHistoryModelActivity.spnrdata =spnritem!!


                val surgeryService = ServiceBuilder.buildService(MedicalHistoryService::class.java)


                val requestCall = surgeryService.addMedication(newmedication)
                requestCall.enqueue(object : Callback<MedicalHistoryModelActivity> {

                    override fun onResponse(call: Call<MedicalHistoryModelActivity>, resp: Response<MedicalHistoryModelActivity>) {


                        if (resp.isSuccessful) {

                            var newbody = resp.body() // Use it or ignore it

                            Toast.makeText(applicationContext, "Successfully Added", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {

                            Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<MedicalHistoryModelActivity>, t: Throwable) {
                        //finish()
                        Log.d("errormsgfailure ::", t.message)

                        Log.e("errorunderfailure:", t.message)
                        Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
                    }
                })


            }else if(validateDate(textview_Startdate!!, textview_Enddate!!,true)==false) {
                val snack = Snackbar.make(it!!, "Wrong Date Selected.", Snackbar.LENGTH_LONG)
                snack.show()
            }

            else {
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


        button_date_StartMh!!.setOnClickListener(object : OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@MedicationHistory,
                        dateSetListenerSatar,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal_StartMh.get(Calendar.YEAR),
                        cal_StartMh.get(Calendar.MONTH),
                        cal_StartMh.get(Calendar.DAY_OF_MONTH)).show()
            }

        })


        button_date_EndMh!!.setOnClickListener(object : OnClickListener {
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

    private fun validateDate(textviewStartdate: TextView, textviewEnddate: TextView, b: Boolean): Boolean {
        if (!textviewStartdate.text.toString().equals("") && !textviewEnddate.text.toString().equals("")) {
            textviewStartdate.setCompoundDrawables(null, null, null, null)
            textviewEnddate.setCompoundDrawables(null, null, null, null)
            val startDate0 = SimpleDateFormat("dd-MMM-yyyy").format(Date(textviewStartdate.text.toString()))
            val endDate0 = SimpleDateFormat("dd-MMM-yyyy").format(Date(textviewEnddate.text.toString()))
            if (startDate0 > endDate0) {
                // date in text view is current date
                textviewEnddate.setText("")
                //showLongSnackBar("Start date cannot be after end date")
                textview_Enddate!!.setError("Wrong Date Selected")
                return false;
            }
        } else {
            //errorDisplayTextview(startDate)
            // errorDisplayTextview(stopDate)
            return false
        }
        return true
    }


    private fun updateDateInViewStart() {

        val myFormat = "dd-MMM-yyyy" // mention the format you need
        sdf1 = SimpleDateFormat(myFormat, Locale.US)
        textview_Startdate!!.text = sdf1?.format(cal_StartMh.getTime())
    }

    private fun updateDateInViewEnd() {
        val myFormat = "dd-MMM-yyyy" // mention the format you need
        sdf2 = SimpleDateFormat(myFormat, Locale.US)

        textview_Enddate!!.text = sdf2?.format(cal_EndMh.getTime())


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        //sprOneItem = parent?.getItemAtPosition(view) as String?

        //val posSpnrOne = spinnerMedication1!!.getSelectedItemPosition()
        val posSpnrtwo = spinnerMedication2!!.getSelectedItemPosition()
        val posSpnrthree = spinnerMedication3!!.getSelectedItemPosition()
        val posSpnrfour = spinnerMedication4!!.getSelectedItemPosition()

        (view as TextView).setTextColor(Color.parseColor("#2196F3"))

        when (parent?.id) {
            R.id.spinner_how_often_taken -> {


                if (isSpinnerOneInitial) {
                    isSpinnerOneInitial = false;
                    //Toast.makeText(this, "select item", Toast.LENGTH_LONG).show()
                }
                if (!isSpinnerOneInitial) {

                    // Toast.makeText(this,"selected item"+parent?.getItemAtPosition(position),Toast.LENGTH_LONG).show()

//                    sprOneItem = parent?.getItemAtPosition(position) as String?
                    sprOneItem = spinner_how_often_taken.tv_how_often.text.toString()
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


//            fun validateDate(datestartmed: TextView, dateendmed: TextView, boolean: Any): Boolean {
//                if (!datestartmed.text.toString().equals("") && !dateendmed.text.toString().equals("")) {
//                    datestartmed.setCompoundDrawables(null, null, null, null)
//                    dateendmed.setCompoundDrawables(null, null, null, null)
//                    val startDate0 = SimpleDateFormat("dd-MMM-yyyy").format(Date(datestartmed.text.toString()))
//                    val endDate0 = SimpleDateFormat("dd-MMM-yyyy").format(Date(dateendmed.text.toString()))
//                    if (startDate0 > endDate1) {
//                        // date in text view is current date
//                        dateendmed.setText("")
//                        //showLongSnackBar("Start date cannot be after end date")
//                        textview_Enddate!!.setError("Wrong Date Selected")
//                        return false;
//                    }
//                } else {
//                    //errorDisplayTextview(startDate)
//                    // errorDisplayTextview(stopDate)
//                    return false
//                }
//                return true
//            }
        }
    }

