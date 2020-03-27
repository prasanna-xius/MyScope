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
import com.example.myscope.activities.prescription.Prescription_manualDrugDialog
import com.example.myscope.activities.services.ServiceBuilder1
import kotlinx.android.synthetic.main.activity_prescription_manual.*
import com.example.myscope.services.PrescriptionInterface
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.disease_history.*
import kotlinx.android.synthetic.main.login_page_main.*
import kotlinx.android.synthetic.main.prescribed_main.*
import kotlinx.android.synthetic.main.prescribed_main_view.*
import kotlinx.android.synthetic.main.prescription_multi_item.view.*
import kotlinx.android.synthetic.main.profile_user_language.view.*
import kotlinx.android.synthetic.main.social_history.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import kotlinx.android.synthetic.main.view_userdetails_main.*
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
    var spinnergender_get: Spinner? = null
    var spinner_bloodGroup_get: Spinner? = null
    var languagesKnown_get: MultiSelectionlanuageSpinner? = null
    private var spinnereducationLevel_get: Spinner? = null
    var spinnermarriageStatus_get: Spinner? = null
    var spinnerfamilyIncome_get: Spinner? = null
    private var registerCall: Call<SignupResponse>? = null
    private var request: PrescriptionInterface? = null

    var first_name_get: EditText? = null
    var last_name_get: EditText? = null
    var mobile_number_get: EditText? = null
    var email_get: EditText? = null

    var dob_get: TextView? = null
    var age_get: TextView? = null
    var doctorname_get: EditText? = null
    var pharmacist_name_get: EditText? = null
    var height_get: EditText? = null
    var weight_get: EditText? = null
    var bmi_get: TextView? = null
    lateinit var profileclass:ProfileDataClass

    lateinit var signupclass:SignupResponse



    var mobile_no: String? = null

    //    var get_mobile:String?=null
//    var email: String? = null
    var sharedpreferences: SharedPreferences? = null
    var bloodGroupAdapter:ArrayAdapter<String> ?=null
    var genderAdapter:ArrayAdapter<String> ?= null
    var educationLevelAdaptor:ArrayAdapter<String> ?=null
    var marriageStatusAdaptor:ArrayAdapter<String> ?= null
    var familyIncomeAdaptor:ArrayAdapter<String> ?=null


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


        spinnergender_get = findViewById(R.id.spinner_gender)
        spinner_bloodGroup_get = findViewById<View>(R.id.spinner_bloodGroup) as Spinner
        spinnereducationLevel_get = findViewById(R.id.spinner_educationLevel)
        spinnermarriageStatus_get = findViewById(R.id.spinner_maritalStatus)
        spinnerfamilyIncome_get = findViewById(R.id.spinner_familyIncome)

        first_name_get = findViewById<View>(R.id.et_first_name) as EditText
        last_name_get = findViewById<View>(R.id.et_last_name) as EditText
        mobile_number_get = findViewById<View>(R.id.et_mobile_no) as EditText
        email_get = findViewById<View>(R.id.et_email) as EditText

        age_get = findViewById<View>(R.id.et_age) as TextView
        bmi_get = findViewById<View>(R.id.et_bmi) as TextView
        doctorname_get = findViewById<View>(R.id.et_doctor_name_profile) as EditText
        height_get = findViewById<View>(R.id.et_height) as EditText
        weight_get = findViewById<View>(R.id.et_weight) as EditText
        pharmacist_name_get = findViewById<View>(R.id.et_pharmacist_name) as EditText
        dob_get = findViewById<View>(R.id.et_dob) as TextView


         languagesKnown_get = findViewById<MultiSelectionlanuageSpinner>(R.id.languagesKnown)

        languagesKnown_get!!.setItems(resources.getStringArray(R.array.languagesKnown_array))

         genderAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.gender_array))
        spinnergender_get!!.adapter = genderAdapter

        bloodGroupAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.bloodGroup_array))
        spinner_bloodGroup!!.adapter = bloodGroupAdapter

        educationLevelAdaptor = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.educationLevel_array))
        spinnereducationLevel_get!!.adapter = educationLevelAdaptor

         marriageStatusAdaptor = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.marriageStatus_array))
         spinnermarriageStatus_get!!.adapter = marriageStatusAdaptor
       familyIncomeAdaptor = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.familyIncome_array))
        spinnerfamilyIncome_get!!.adapter = familyIncomeAdaptor
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

        getuserprofileapi()




        btn_update_userProfile.setOnClickListener {

//            assignValuestoVariable()
//            validate(spinnergender_get!!)
//            validate(spinner_bloodGroup!!)
            profileupdateapi();

            signupUpdateapi();

        }

        btn_save_userProfile.setOnClickListener {

            assignValuestoVariable()
            validate(spinnergender_get!!)
            validate(spinner_bloodGroup!!)
            profilepostapi();

        }


    }

    private fun signupUpdateapi() {
        updatevaluestosignupclass()

        val updateSignup = ServiceBuilder1.buildService(PrescriptionInterface::class.java)

        val requestCallSignup = updateSignup.updateSignUp(signupclass)


        requestCallSignup!!.enqueue(object : Callback<SignupResponse?> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onResponse(call: Call<SignupResponse?>, resp: Response<SignupResponse?>) {

                if (resp.isSuccessful) {
                    Toast.makeText(applicationContext, "Updated Successfully" , Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignupResponse?>, t: Throwable) {

                Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun getuserprofileapi() {
        val userGetProfileService = ServiceBuilder1.buildService(PrescriptionInterface::class.java)
        val requestGetValuesCall = userGetProfileService.userprofilegetAllValues(mobile_no!!)

        requestGetValuesCall.enqueue(object : Callback<List<ProfileDataClass>> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onResponse(call: Call<List<ProfileDataClass>>, resp: Response<List<ProfileDataClass>>) {

                if (resp.isSuccessful) {
                    var newbody = resp.body()
                    val userprofilevalues = newbody?.first()

                    if(userprofilevalues!!.first_name.equals(null) ||
                            userprofilevalues!!.last_name.equals(null) ||
                            userprofilevalues!!.mobile_no.equals(null) ||
                            userprofilevalues!!.email.equals(null)){
                        btn_save_userProfile.visibility = View.VISIBLE
                        btn_update_userProfile.visibility = View.GONE
                        callsignupprofile()
                    } else {
                        btn_save_userProfile.visibility = View.GONE
                        btn_update_userProfile.visibility = View.VISIBLE
                        first_name_get!!.setText(userprofilevalues!!.first_name)
                        last_name_get!!.setText(userprofilevalues.last_name)
                        mobile_number_get!!.setText(userprofilevalues.mobile_no)
                        email_get!!.setText(userprofilevalues.email)

                        age_get!!.setText(userprofilevalues.age)
                        bmi_get!!.setText(userprofilevalues.bmi)
                        height_get!!.setText(userprofilevalues.height)
                        weight_get!!.setText(userprofilevalues.weight)
                        dob_get!!.setText(userprofilevalues.dob)
                        doctorname_get!!.setText(userprofilevalues.doctor_name)
                        pharmacist_name_get!!.setText(userprofilevalues.pharmacist_name)

                        val spinner_bloodGroup_get = bloodGroupAdapter!!.getPosition(userprofilevalues.blood_group);
                        spinner_bloodGroup.setSelection(spinner_bloodGroup_get);
                        val spinnereducationLevel_get = educationLevelAdaptor!!.getPosition(userprofilevalues.education);
                        spinner_gender.setSelection(spinnereducationLevel_get);
                        val spinnergender_get = genderAdapter!!.getPosition(userprofilevalues.gender);
                        spinner_educationLevel.setSelection(spinnergender_get);
                        val spinnerfamilyIncome_get = familyIncomeAdaptor!!.getPosition(userprofilevalues.family_income);
                        spinner_maritalStatus.setSelection(spinnerfamilyIncome_get);
                        val spinnermarriageStatus_get = marriageStatusAdaptor!!.getPosition(userprofilevalues.marrital_status);
                        spinner_familyIncome.setSelection(spinnermarriageStatus_get);

                        languagesKnown_get!!.languages_multi?.setText(userprofilevalues.languages_known)

                        Toast.makeText(applicationContext, "API success", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ProfileDataClass>>, t: Throwable) {

                Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callsignupprofile() {
        val signupresponse = ServiceBuilder1.buildService(PrescriptionInterface::class.java)
        val requestCall = signupresponse.userprofileget(mobile_no!!)

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

    }

    private fun profileupdateapi() {

        savingvaluestoprofileclass()

        val profileUserservice = ServiceBuilder1.buildService(PrescriptionInterface::class.java)

        //val requestCall =allergyService.addAllergy(name!!,reaction!!,treatment!!,notes!!,date!!,sprdata!!)

        val requestCall = profileUserservice.updateUser(profileclass)

        requestCall.enqueue(object : Callback<ProfileDataClass> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onResponse(call: Call<ProfileDataClass>, resp: Response<ProfileDataClass>) {

                if (resp.isSuccessful) {
                    Toast.makeText(applicationContext, "Updated Successfully" , Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProfileDataClass>, t: Throwable) {

                Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })


    }


    private fun profilepostapi() {

        savingvaluestoprofileclass()

        val addPatient = ServiceBuilder1.buildService(PrescriptionInterface::class.java)

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

    private fun updatevaluestosignupclass() {

        val first_name = et_first_name!!.text.toString()
        val last_name = et_last_name.text.toString()
        val mobile_no = et_mobile_no.text.toString()
        val email = et_email.text.toString()

        signupclass = SignupResponse()
        if(!first_name.equals(null)) {
            signupclass.first_name = first_name
        } else {
            signupclass.first_name = et_first_name.text.toString()
        }

        if(!last_name.equals(null)) {
            signupclass.last_name = last_name
        } else {
            signupclass.last_name = et_last_name.text.toString()
        }
        if(!mobile_no.equals(null)) {
            signupclass.mobile_no = mobile_no
        } else {
            signupclass.mobile_no = et_mobile_no.text.toString()
        }
        if(!email.equals(null)) {
            signupclass.email = email
        } else {
            signupclass.email = et_email.text.toString()
        }

    }

    private fun savingvaluestoprofileclass() {
        val first_name = first_name_get!!.text.toString()
        val last_name = et_last_name.text.toString()
        val gender = spinner_gender.selectedItem.toString()
        val age = et_age.text.toString()
        val blood_group = spinner_bloodGroup!!.selectedItem.toString()
        val bmi = et_bmi.text.toString()
        val doctor_name = et_doctor_name_profile.text.toString()
        val pharmacist_name = et_pharmacist_name.text.toString()
        val weight = et_weight.text.toString()
        val height = et_height.text.toString()
        val dob = et_dob.text.toString()
        val language = languagesKnown!!.selectedItemsAsString
        val education = spinnereducationLevel_get!!.selectedItem.toString()
        val family_income = spinnerfamilyIncome_get!!.selectedItem.toString()
        val marrital_status = spinnermarriageStatus_get!!.selectedItem.toString()
        val mobile_no = et_mobile_no.text.toString()
        val email = et_email.text.toString()

        profileclass = ProfileDataClass()
        if(!first_name.equals(null)) {
            profileclass.first_name = first_name
        } else {
            profileclass.first_name = et_first_name.text.toString()
        }

        if(!last_name.equals(null)) {
            profileclass.last_name = last_name
        } else {
            profileclass.last_name = et_last_name.text.toString()
        }

        if(!gender.equals(null)) {
            profileclass.gender = gender
        } else {
            profileclass.gender = spinner_gender.selectedItem.toString()
            text1.setText(profileclass.gender)        }

        if(!age.equals(null)) {
            profileclass.age = age
        } else {
            profileclass.age = et_first_name.text.toString()
        }

        if(!blood_group.equals(null)) {
            profileclass.blood_group = blood_group
        } else {
            profileclass.blood_group = spinner_bloodGroup_get!!.selectedItem.toString()
            text1.setText(profileclass.blood_group)            }

        if(!bmi.equals(null)) {
            profileclass.bmi = bmi
        } else {
            profileclass.bmi = et_bmi.text.toString()
        }

        if(!doctor_name.equals(null)) {
            profileclass.doctor_name = doctor_name
        } else {
            profileclass.doctor_name = et_doctor_name_profile.text.toString()
        }

        if(!pharmacist_name.equals(null)) {
            profileclass.pharmacist_name = pharmacist_name
        } else {
            profileclass.pharmacist_name = et_pharmacist_name.text.toString()
        }
        if(!weight.equals(null)) {
            profileclass.weight = weight
        } else {
            profileclass.weight = et_weight.text.toString()
        }
        if(!height.equals(null)) {
            profileclass.height = height
        } else {
            profileclass.height = et_height.text.toString()
        }
        if(!dob.equals(null)) {
            profileclass.dob = dob
        } else {
            profileclass.dob = et_dob.text.toString()
        }
        if(!language.equals(null)) {
            profileclass.languages_known = language
            languagesKnown.languages_multi.setText(profileclass.languages_known)

        } else {
            profileclass.languages_known = languagesKnown!!.selectedItemsAsString
            languagesKnown.languages_multi.setText(profileclass.languages_known)
        }
        if(!education.equals(null)) {
            profileclass.education = education
        } else {
            profileclass.education = spinnereducationLevel_get!!.selectedItem.toString()
            text1.setText(profileclass.education)
        }
        if(!family_income.equals(null)) {
            profileclass.family_income = family_income
        } else {
            profileclass.family_income = spinnerfamilyIncome_get!!.selectedItem.toString()
            text1.setText(profileclass.family_income)
        }
        if(!marrital_status.equals(null)) {
            profileclass.marrital_status = marrital_status
        } else {
            profileclass.marrital_status = spinnermarriageStatus_get!!.selectedItem.toString()
            text1.setText(profileclass.marrital_status)        }
        if(!mobile_no.equals(null)) {
            profileclass.mobile_no = mobile_no
        } else {
            profileclass.mobile_no = et_mobile_no.text.toString()
        }
        if(!email.equals(null)) {
            profileclass.email = email
        } else {
            profileclass.email = et_email.text.toString()
        }


    }

    private fun assignValuestoVariable() {

        val gender = spinner_gender.selectedItem.toString()
        val bloodGroup = spinner_bloodGroup!!.selectedItem.toString()
        val firstName = et_first_name.text.toString()
        val lastName = et_last_name.text.toString()
        val doctorName = et_doctor_name_profile.text.toString()
        val pharmacistName = et_pharmacist_name.text.toString()
        val weight = et_weight.text.toString()
        val height = et_height.text.toString()
        val dob = et_dob.text.toString()

        validateSpinner(spinner_gender, gender)
        validateSpinner(spinner_bloodGroup!!, bloodGroup)
        validateInput(et_first_name, firstName)
        validateInput(et_last_name, lastName)
        validateInput(et_doctor_name_profile, doctorName)
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
