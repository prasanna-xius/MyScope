package com.example.myscope.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.myscope.ProfileDataClass

import com.example.myscope.R
import com.example.myscope.activities.prescription.PrescriptionDataClass
import com.example.myscope.activities.services.ServiceBuilder1
import kotlinx.android.synthetic.main.activity_prescription_manual.*
import com.example.myscope.services.PrescriptionInterface
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.disease_history.*
import kotlinx.android.synthetic.main.login_page_main.*
import kotlinx.android.synthetic.main.prescribed_main.*
import kotlinx.android.synthetic.main.prescribed_main_view.*
import kotlinx.android.synthetic.main.prescription_multi_item.view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import kotlinx.android.synthetic.main.view_userdetails_main.*
import kotlinx.android.synthetic.main.view_userdetails_main.et_doctor_name
import kotlinx.android.synthetic.main.view_userdetails_main.view.*
import org.json.JSONException
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
    var spinner_bloodGroup1: Spinner? = null
    private var spinnereducationLevel: Spinner? = null
    var spinnermarriageStatus: Spinner? = null
    var spinnerfamilyIncome: Spinner? = null
    private var registerCall: Call<SignupResponse>? = null
    private var request: PrescriptionInterface? = null
    var b: Button? = null
    var first_name_get: EditText? = null
    var last_name_get: EditText? = null
    var mobile_number_get: EditText? = null
    var email_get: EditText? = null


    var mobile_no: String? = null

    //    var get_mobile:String?=null
//    var email: String? = null
    var sharedpreferences: SharedPreferences? = null

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_userdetails_main)
        activitiesToolbar()
        header!!.text = "User Profile"

        mobile_no = "9505505093"

//        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//        get_mobile = sharedpreferences!!.getString("mobile_no", "9505505093")
//        email=sharedpreferences!!.getString("email",null)
//        showLongToast(mobile_no.toString())


        spinnergender = findViewById(R.id.spinner_gender)
        spinner_bloodGroup1 = findViewById(R.id.spinner_bloodGroup)
        spinnereducationLevel = findViewById(R.id.spinner_educationLevel)
        spinnermarriageStatus = findViewById(R.id.spinner_maritalStatus)
        spinnerfamilyIncome = findViewById(R.id.spinner_familyIncome)

        first_name_get = findViewById<View>(R.id.et_first_name) as EditText
        last_name_get = findViewById<View>(R.id.et_last_name) as EditText
        mobile_number_get = findViewById<View>(R.id.et_mobile_no) as EditText
        email_get = findViewById<View>(R.id.et_email) as EditText

        val languagesKnown = findViewById<MultiSelectionSpinner>(R.id.languagesKnown)

        languagesKnown.setItems(resources.getStringArray(R.array.languagesKnown_array))

        val genderAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.gender_array))
        spinnergender!!.adapter = genderAdapter

        val bloodGroupAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.bloodGroup_array))
        spinner_bloodGroup!!.adapter = bloodGroupAdapter

        val educationLevelAdaptor = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.educationLevel_array))
        spinnereducationLevel!!.adapter = educationLevelAdaptor

        val marriageStatusAdaptor = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.marriageStatus_array))
        spinnermarriageStatus!!.adapter = marriageStatusAdaptor
        val familyIncomeAdaptor = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.familyIncome_array))
        spinnerfamilyIncome!!.adapter = familyIncomeAdaptor
        if (!et_height.text.equals(null) && !et_weight.text.equals(null)) {
            bmicalculator(et_height, et_weight, et_bmi)
            showLongToast(et_bmi.text.toString())
        }
        myCalendar = Calendar.getInstance()


        val date = DatePickerDialog.OnDateSetListener { view, year, monthofyear, dayofmonth ->

            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            et_dob.setText(date1)

            validateDate1(et_dob, et_age)

        }
        et_dob.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

//        if(TextUtils.isEmpty(et_first_name.toString()))
//        {
//            btn_save_userProfile.setVisibility(View.GONE);
//        }
//        else
//        {
//            btn_save_userProfile.setVisibility(View.VISIBLE);
//        }


        val userProfileService = ServiceBuilder1.buildService(PrescriptionInterface::class.java)
        val requestCall = userProfileService.userprofileget(mobile_no!!)

        requestCall.enqueue(object : Callback<List<SignupResponse>> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onResponse(call: Call<List<SignupResponse>>, resp: Response<List<SignupResponse>>) {

                if (resp.isSuccessful) {
                    var newbody = resp.body()
                    val userprofileget = newbody?.first()

                    first_name_get!!.setText(userprofileget!!.first_name)
                    last_name_get!!.setText(userprofileget.last_name)
                    mobile_number_get!!.setText(userprofileget.mobile_no)
                    email_get!!.setText(userprofileget.email)


                    Toast.makeText(applicationContext, "API success" + userprofileget!!.first_name, Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<SignupResponse>>, t: Throwable) {

                Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })

        btn_update_userProfile.setOnClickListener {

            assignValuestoVariable()
            validate(spinnergender!!)
            validate(spinner_bloodGroup!!)
            profilepostapi();

        }

        btn_save_userProfile.setOnClickListener {

            assignValuestoVariable()
            validate(spinnergender!!)
            validate(spinner_bloodGroup!!)
            profilepostapi();

        }

        checkFieldsForEmptyValues();

    }

    private fun checkFieldsForEmptyValues() {


        if(first_name_get!!.equals("first_name") && last_name_get!!.equals("last_name"))
        {
            btn_save_userProfile.setEnabled(false);
        }

//        else if(!first_name_get!!.equals("")&&!last_name_get!!.equals("")){
//            btn_save_userProfile.setEnabled(false);
//        }
//
//        else if(!first_name_get!!.equals("")&&!last_name_get!!.equals(""))
//        {
//            btn_save_userProfile.setEnabled(false);
//        }

        else
        {
            btn_save_userProfile.setEnabled(true);
        }
    }

    private fun profilepostapi() {


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
        profileclass.language = languagesKnown.selectedItemsAsString
        profileclass.education = spinnereducationLevel!!.selectedItem.toString()
        profileclass.family_income = spinnerfamilyIncome!!.selectedItem.toString()
        profileclass.marrital_status = spinnermarriageStatus!!.selectedItem.toString()
        profileclass.mobile_no = et_mobile_no.text.toString()
        profileclass.email = et_email.text.toString()

        val addPatient = ServiceBuilder1.buildService(PrescriptionInterface::class.java)

        val requestCall = addPatient.addProfile(profileclass)

        Toast.makeText(applicationContext, "Data added 123", Toast.LENGTH_SHORT).show()

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

        validateSpinner(spinner_gender, gender)
        validateSpinner(spinner_bloodGroup!!, bloodGroup)
        validateInput(et_first_name, firstName)
        validateInput(et_last_name, lastName)
        validateInput(et_doctor_name, doctorName)
        validateInput(et_pharmacist_name, pharmacistName)
        validateInput(et_weight, weight)
        validateInput(et_height, height)
        bmicalculator(et_weight, et_height, et_bmi)



        if ((firstName != "") &&
                (lastName != "") &&
                (doctorName != "") &&
                (weight != "") &&
                (height != "") &&
                (pharmacistName != "") &&
                (!gender.equals("None")) &&
                (!bloodGroup.equals("None")) &&
                (dob != "")) {
        }
    }


}
