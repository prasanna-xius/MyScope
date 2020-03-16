package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import com.example.myscope.R
import kotlinx.android.synthetic.main.signuppage_main.*
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.reflect.TypeToken
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.myscope.fragments.ExpandableListDataPump.data
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.util.Collections.replaceAll


class SignUp_Page : BaseActivity(), View.OnClickListener {
    var firstName: String = "";
    var lastName: String = "";
    var mobileNumber: String = "";
    var emailId: String = "";
    private var registerCall: Call<SignupResponse>? = null
    private var request: PrescriptionInterface? = null

    lateinit var jsonParser: JsonParser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signuppage_main)
        btn_SignUp!!.setOnClickListener(this)
        btn_login!!.setOnClickListener(this)
        hideKeyBoard()
    }

    override fun onClick(v: View) {
    when (v.id) {
        R.id.btn_SignUp -> signup()
        R.id.btn_login -> {
            navigateToActivity(Intent(applicationContext, Login_Page::class.java))
        }
    }
}

private fun signup() {
    Log.d(TAG, "SignUp_Page")


        if (validate() == false) {
            onSignupFailed()
            return
        }


        navigateToActivity(Intent(applicationContext, Login_Page::class.java))
        showLongToast("Registration Successfully Completed")


}

private fun onSignupFailed() {
    btn_SignUp!!.isEnabled = true
}


private fun validate(): Boolean {
    var valid = true

    firstName = edt_firstname!!.text.toString().trim { it <= ' ' }
    lastName = edt_lastname!!.text.toString().trim { it <= ' ' }
    mobileNumber = edt_mobile!!.text.toString().trim { it <= ' ' }
    emailId = edt_email!!.text.toString().trim { it <= ' ' }

    firstname_layout!!.error = null
    lastname_layout!!.error = null
    mobile_layout!!.error = null
    email_layout!!.error = null


    if (firstName.isEmpty()) {
        firstname_layout!!.error = "Please Enter First Name"
        valid = false

    } else if (firstName.length < 3) {
        firstname_layout!!.error = "At least 3 characters"
        valid = false
    }
    if (lastName.isEmpty()) {
        lastname_layout!!.error = "Please Enter Last Name"
        valid = false
    } else if (lastName.length < 3) {
        lastname_layout!!.error = "At least 3 characters"
        valid = false
    }
    if (mobileNumber.isEmpty()) {
        mobile_layout!!.error = "Enter mobile number"
        valid = false
    } else if (mobileNumber.length < 10) {
        mobile_layout!!.error = "Enter Valid mobile number"
        valid = false
    }
    if (emailId.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
        email_layout!!.error = "Enter Register Email Address"
        valid = false
    } else {
        email_layout!!.error = null
    }
    registerRetrofit2Api(firstName, lastName,mobileNumber,emailId);
    return valid
}

    private fun registerRetrofit2Api(firstname: String, lastname: String, mobileno: String, email: String) {
        var login = SignupResponse(firstname, lastname, mobileno, email)
        request = APIClient.getClient().create(PrescriptionInterface::class.java)

        try {
            var resp: SignupResponse = login

            registerCall = request?.createPatient(resp)

            (registerCall as Call<SignupResponse>?)?.enqueue(object : Callback<SignupResponse> {
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    showLongToast("Recheck and save again")
                    Log.d("Errormessage", t.message + "error")
                }

                override fun onResponse(call: Call<SignupResponse>, resp: Response<SignupResponse>) {
                    if (resp.isSuccessful && !resp.equals("")) {
                        showShortToast("Api call is working")
                        navigateToActivity(Intent(applicationContext, Login_Page::class.java))
                    }
                }
            });
        } catch (e: JSONException) {
            e.printStackTrace();
        }
    }

    companion object {
    private const val TAG = "SignUp_Page"
}


}
