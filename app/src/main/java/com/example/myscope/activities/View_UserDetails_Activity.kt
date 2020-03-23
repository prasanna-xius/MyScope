package com.example.myscope.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myscope.ProfileDataClass

import com.example.myscope.R
import com.example.myscope.activities.services.ServiceBuilder1
import kotlinx.android.synthetic.main.activity_prescription_manual.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.disease_history.*
import kotlinx.android.synthetic.main.view_userdetails_main.*
import kotlinx.android.synthetic.main.view_userdetails_main.et_doctor_name
import kotlinx.android.synthetic.main.view_userdetails_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        if(!et_height.text.equals(null) && !et_weight.text.equals(null)) {
            bmicalculator(et_height,et_weight,et_bmi)
            showLongToast(et_bmi.text.toString())
        }
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








        btn_userProfile.setOnClickListener{

            assignValuestoVariable()
           validate(spinnergender!!)
            validate(spinner_bloodGroup!!)
            profileapi();
        }

    }

    private fun profileapi() {
        val addPatient = ServiceBuilder1.buildService(PrescriptionInterface::class.java)
        val profileclass = ProfileDataClass();
        profileclass.first_name = et_first_name.text.toString()
        profileclass.last_name = et_last_name.text.toString()
        profileclass.gender = spinner_gender.selectedItem.toString()
        profileclass.age = et_age.text.toString()
        profileclass.blood_group = spinner_bloodGroup!!.selectedItem.toString()
        profileclass.bmi = et_bmi.text.toString()
        profileclass.doctor_name = et_doctor_name.text.toString()
        profileclass.pharmacist_name = et_pharmacist_name.text.toString()
        profileclass.weight = et_weight.text.toString()
        profileclass.height = et_height.text.toString()
        profileclass.dob = et_dob.text.toString()
        profileclass.language = languagesKnown.selectedItemsAsString.toString()
        profileclass.education = spinnereducationLevel!!.selectedItem.toString()
        profileclass.family_income = spinnerfamilyIncome!!.selectedItem.toString()
        profileclass.marrital_status = spinnermarriageStatus!!.selectedItem.toString()
        profileclass.mobile_no = "8142529582"
        profileclass.email = "pb@gmail.com"

        val requestCall = addPatient.addProfile(profileclass)

        requestCall.enqueue(object : Callback<ProfileDataClass> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onResponse(call: Call<ProfileDataClass>, resp: Response<ProfileDataClass>) {

                if (resp.isSuccessful) {
                    Toast.makeText(applicationContext, "Data added", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProfileDataClass>, t: Throwable) {

                Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })

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
