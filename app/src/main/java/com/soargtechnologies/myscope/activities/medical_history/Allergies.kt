package com.soargtechnologies.myscope.activities.medical_history

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle

import com.soargtechnologies.myscope.R

import com.soargtechnologies.myscope.models.AllergyDataClass

import com.soargtechnologies.myscope.activities.BaseActivity

import com.soargtechnologies.myscope.services.ServiceBuilder

import com.soargtechnologies.myscope.services.MedicalHistoryService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.allergies.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.surgeryhistory_update.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Allergies : BaseActivity(),  AdapterView.OnItemSelectedListener {

    var btnAllergies: Button? = null

    var button_date: ImageView? = null
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    var spnritem: String? = null
    private var isSpinnerInitial = true
    var spinnerAllergies: Spinner? = null
    //var name: String? = null;
    //var reaction: String? = null;
    //var treatment: String? = null;
    //var notes: String? = null

    //var date: String? = null
    var tv:Any?=null
    //var pattern :Pattern?=null
    var etname: EditText? = null;
    var etreaction: EditText? = null;
    var ettreatments: EditText? = null
    var etnotes: EditText? = null;

    internal lateinit var myCalendar: Calendar
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    private var awesomeValidation: AwesomeValidation? = null
    //var allergydata:Any?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.allergies)

        activitiesToolbar()
        header!!.text = "Allergies"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
        awesomeValidation = AwesomeValidation(ValidationStyle.BASIC);

        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            textviewdate.setText(date1)
        }
        textviewdate.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // get the references from layout file
        textview_date = this.textviewdate
      //  button_date = this.buttondate

        etname = findViewById(R.id.et_name_allergy)

        btnAllergies = findViewById(R.id.btn_allergies)


        etnotes = findViewById(R.id.et_notes_allergies)
        etreaction = findViewById(R.id.et_reaction)
        ettreatments = findViewById(R.id.et_treatment)


        awesomeValidation!!.addValidation(this, R.id.et_name,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);

        awesomeValidation!!.addValidation(this, R.id.et_reaction,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$" , R.string.nameerror);


        awesomeValidation!!.addValidation(this, R.id.et_reaction,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$" , R.string.nameerror);

        //val myptr  = """^\d{1,2}/\d{1,2}/\d{4}${'$'}""".toRegex()


        var allergydata = resources.getStringArray(R.array.allergy_arrays)

        //textview_date!!.text = ","

        spinnerAllergies = findViewById<Spinner>(R.id.spinnerAllergy)

        btnAllergies!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                ///val name =etname?.text.toString().trim()

                if (validate(et_name_allergy, true))

                    if (spnritem != null) {


                        val newAllergy = AllergyDataClass()
                        newAllergy.name = et_name_allergy!!.text.toString().trim()
                        newAllergy.reaction = etreaction!!.text.toString().trim()
                        newAllergy.treatment = ettreatments!!.text.toString().trim()
                        newAllergy.notes = etnotes!!.text.toString().trim()
                        newAllergy.date = textview_date?.text.toString().trim()
                        newAllergy.spnrdata = spnritem!!
                        newAllergy.mobile_no = mobile_no!!
                        newAllergy.allergy_saved_on = datesetvalue()
                        // newAllergy.id=1


                        val allergyService = ServiceBuilder.buildService(MedicalHistoryService::class.java)

                        //val requestCall =allergyService.addAllergy(name!!,reaction!!,treatment!!,notes!!,date!!,sprdata!!)


                        val requestCall = allergyService.addAllergy(newAllergy)
                        requestCall.enqueue(object : Callback<AllergyDataClass> {

                            override fun onResponse(call: Call<AllergyDataClass>, resp: Response<AllergyDataClass>) {


                                if (resp.isSuccessful) {

                                    // var newbody = resp.body() // Use it or ignore it
      //                              Toast.makeText(applicationContext, "Successfully Added", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else {

     //                               Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<AllergyDataClass>, t: Throwable) {
                                //finish()

    //                            Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
                            }
                        })


                        // Toast.makeText(getApplicationContext(), "data:" + name + " " + reaction + " " + treatment + " " + " " + notes + " " +
                        // spritem + " " + date, Toast.LENGTH_LONG).show()

                    } else {


                        val snack = Snackbar.make(ll!!, "Mandatory field cannot be left blank.", Snackbar.LENGTH_LONG)
                        snack.show()
                    }
            }

        })
        // create an OnDateSetListener
//        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
//            @SuppressLint("NewApi")
//            @RequiresApi(Build.VERSION_CODES.O)
//            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
//                                   dayOfMonth: Int) {
//                cal.set(Calendar.YEAR, year)
//                cal.set(Calendar.MONTH, monthOfYear)
//                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                updateDateInView()
//            }
//        }
//
//        textviewdate?.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(view: View) {
//                DatePickerDialog(this@Allergies,
//                        dateSetListener,
//                        // set DatePickerDialog to point to today's date when it loads up
//                        cal.get(Calendar.YEAR),
//                        cal.get(Calendar.MONTH),
//                        cal.get(Calendar.DAY_OF_MONTH)).show()
//            }
//
//        })




        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, allergydata)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(R.layout.spinner_item_layout)
        // Set Adapter to Spinner
        spinnerAllergies!!.setAdapter(aa)


        spinnerAllergies!!.setOnItemSelectedListener(this)


    }
    private fun validate(et_name_allergy: EditText, b:Boolean):Boolean {
        if(et_name_allergy.text.toString().isEmpty()){
            //et_name_immuupdate.setError("")
            val snack = Snackbar.make(findViewById(R.id.llallergy)!!, "Mandatory field cannot be left blank.", Snackbar.LENGTH_LONG)
            snack.show()
            return false

        }
        return true
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date!!.text = sdf.format(cal.getTime())


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.



        (view as TextView).setTextColor(Color.parseColor("#FF2196F3"))

        val pos = spinnerAllergies!!.getSelectedItemPosition()

        if (isSpinnerInitial) {
            isSpinnerInitial = false;


            //Toast.makeText(this, "select item", Toast.LENGTH_LONG).show()
        }
        if(!isSpinnerInitial && pos!=0){

            // Toast.makeText(this,"selected item"+parent?.getItemAtPosition(position),Toast.LENGTH_LONG).show()


            spnritem= parent?.getItemAtPosition(position) as String?
            //Toast.makeText(this, "selected item" + spritem, Toast.LENGTH_LONG).show()
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {



    }



}


