package com.example.myscope.activities.medical_history

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
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
import kotlinx.android.synthetic.main.allergies.*
import java.text.SimpleDateFormat
import java.util.*

class Allergies : AppCompatActivity(),  AdapterView.OnItemSelectedListener {

    var btnAllergies: Button? = null

    var button_date: Button? = null
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    var spritem: String? = null
    private var isSpinnerInitial = true
    var spinnerAllergies: Spinner? = null
    var name: String? = null;
    var reaction: String? = null;
    var treatment: String? = null;
    var notes: String? = null

    var date: String? = null
    var tv:Any?=null
    //var pattern :Pattern?=null
    var etname: EditText? = null;
    var etreaction: EditText? = null;
    var ettreatments: EditText? = null
    var etnotes: EditText? = null;


    private var awesomeValidation: AwesomeValidation? = null
    //var allergydata:Any?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.allergies)

        awesomeValidation = AwesomeValidation(ValidationStyle.BASIC);


        // get the references from layout file
        textview_date = this.textviewdate
        button_date = this.buttondate

        etname = findViewById(R.id.et_name)

        btnAllergies = findViewById(R.id.btn_allergies)


        etnotes = findViewById(R.id.et_notes_allergies)
        etreaction = findViewById(R.id.et_reaction)
        ettreatments = findViewById(R.id.et_treatment)


        awesomeValidation!!.addValidation(this, R.id.et_name,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.nameerror);

        awesomeValidation!!.addValidation(this, R.id.et_reaction,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$" ,R.string.nameerror);


        awesomeValidation!!.addValidation(this, R.id.et_reaction,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$" ,R.string.nameerror);

        val myptr  = """^\d{1,2}/\d{1,2}/\d{4}${'$'}""".toRegex()

        2
        var allergydata = resources.getStringArray(R.array.allergy_arrays)

        textview_date!!.text = " "

        spinnerAllergies = findViewById<Spinner>(R.id.spinnerAllergy)

        btnAllergies!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {



                name = etname!!.getText().toString()
                reaction = etreaction!!.getText().toString()
                treatment = ettreatments!!.getText().toString()
                notes = etnotes!!.getText().toString()
                date = textview_date?.getText().toString()



                if (awesomeValidation!!.validate() && spritem != null && date!!.matches(myptr) ) {

                    //if (date!!.matches(myptr)) {

                    Toast.makeText(getApplicationContext(), "data:" + name + " " + reaction + " " + treatment + " " + " " + notes + " " +
                            spritem + " " + date, Toast.LENGTH_LONG).show()



                    // Toast.makeText(getApplicationContext(), "data:" + date, Toast.LENGTH_LONG).show()
                } else {


                    val snack = Snackbar.make(ll!!, "Mandatory field cannot be left blank.", Snackbar.LENGTH_LONG)
                    snack.show()
                }
            }
        })
        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            @SuppressLint("NewApi")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        button_date?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@Allergies,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })




        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, allergydata)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(R.layout.spinner_item_layout)
        // Set Adapter to Spinner
        spinnerAllergies!!.setAdapter(aa)


        spinnerAllergies!!.setOnItemSelectedListener(this)
    }







    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date!!.text = sdf.format(cal.getTime())



    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        (view as TextView).setTextColor(Color.parseColor("#ffffff"))

        val pos = spinnerAllergies!!.getSelectedItemPosition()

        if (isSpinnerInitial) {
            isSpinnerInitial = false;


            //Toast.makeText(this, "select item", Toast.LENGTH_LONG).show()
        }
        if(!isSpinnerInitial && pos!=0){

            // Toast.makeText(this,"selected item"+parent?.getItemAtPosition(position),Toast.LENGTH_LONG).show()


            spritem = parent?.getItemAtPosition(position) as String?
            //Toast.makeText(this, "selected item" + spritem, Toast.LENGTH_LONG).show()
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {



    }



}
