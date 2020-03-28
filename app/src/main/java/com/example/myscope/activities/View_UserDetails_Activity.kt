package com.example.myscope.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi

import com.example.myscope.R
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.disease_history.*
import kotlinx.android.synthetic.main.view_userdetails_main.*
import java.text.DateFormat
import java.time.Year
import java.util.*

class View_UserDetails_Activity : BaseActivity() {

    internal lateinit var myCalendar: Calendar
//    private var dateOfBirth = false
    var spinnergender: Spinner? = null
    var spinner_bloodGroup: Spinner? = null
    private var spinnereducationLevel: Spinner? = null
    var spinnermarriageStatus: Spinner? = null
    var spinnerfamilyIncome: Spinner? = null

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_userdetails_main)
        activitiesToolbar()
        header!!.text = "User Profile"

        spinnergender=findViewById(R.id.spinner_gender)
        spinner_bloodGroup=findViewById(R.id.spinner_bloodGroup)
        spinnereducationLevel=findViewById(R.id.spinner_educationLevel)
        spinnermarriageStatus=findViewById(R.id.spinner_maritalStatus)
        spinnerfamilyIncome=findViewById(R.id.spinner_familyIncome)
        val  languagesKnown = findViewById<MultiSelectionSpinner>(R.id.languagesKnown)
        languagesKnown.setItems(resources.getStringArray(R.array.languagesKnown_array))

        val genderAdapter = ArrayAdapter(this,R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.gender_array))
        spinnergender!!.adapter= genderAdapter

        val bloodGroupAdapter = ArrayAdapter(this,R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.bloodGroup_array))
        spinner_bloodGroup!!.adapter = bloodGroupAdapter

        val educationLevelAdaptor = ArrayAdapter(this,R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.educationLevel_array))
        spinnereducationLevel!!.adapter = educationLevelAdaptor

        val marriageStatusAdaptor = ArrayAdapter(this,R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.marriageStatus_array))
        spinnermarriageStatus!!.adapter = marriageStatusAdaptor
        val familyIncomeAdaptor = ArrayAdapter(this,R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.familyIncome_array))
        spinnerfamilyIncome!!.adapter = familyIncomeAdaptor


        myCalendar = Calendar.getInstance()


        val date= DatePickerDialog.OnDateSetListener{view, year , monthofyear , dayofmonth ->

        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthofyear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
        val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            et_dob.setText(date1)
            validateDate1(et_dob,et_age)

        }
        et_dob.setOnClickListener {
                DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            }




et_bmi.setOnClickListener {

                bmi()

               // bmicalculator(et_weight,et_height,et_bmi)
}



        btn_userProfile.setOnClickListener{

            assignValuestoVariable()
           validate(spinnergender!!)
            validate(spinner_bloodGroup!!)
         //   bmicalculator(et_weight,et_height,et_bmi)
        }

    }

    private fun bmi() {

        bmicalculator(et_weight,et_height,et_bmi)
    }

    private fun assignValuestoVariable() {

        val gender = spinner_gender.selectedItem.toString()
        val bloodGroup = spinner_bloodGroup!!.selectedItem.toString()
        val firstName = et_first_name.text.toString()
        val lastName = et_last_name.text.toString()
        val doctorName = et_doctor_name.text.toString()
        val pharmacistName = et_pharmacist_name.text.toString()
        val weight = et_weight.text.toString()
        val height = et_height.text.toString()
        val dob = et_dob.text.toString()

        validateSpinner(spinner_gender,gender)
        validateSpinner(spinner_bloodGroup!!,bloodGroup)
        validateInput(et_first_name,firstName)
        validateInput(et_last_name,lastName)
        validateInput(et_doctor_name,doctorName)
        validateInput(et_pharmacist_name,pharmacistName)
        validateInput(et_weight,weight)
        validateInput(et_height,height)


        if((firstName!="")&&
           (lastName!="")&&
           (doctorName!="")&&
           (weight!="")&&
           (height!="")&&
           (pharmacistName!="")&&
           (!gender.equals("None"))&&
           (!bloodGroup.equals("None"))&&
           (dob!= ""))
        {
            showLongToast("save all details")
        }
    }
}
