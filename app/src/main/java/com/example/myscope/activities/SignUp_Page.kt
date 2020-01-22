package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myscope.R
import com.google.android.material.textfield.TextInputLayout

class SignUp_Page : BaseActivity(), View.OnClickListener {
    var btn_SignUp: Button? = null
    var btn_login: TextView? = null
    var edt_firstname: EditText? = null
    var edt_lastname: EditText? = null
    var edt_mobile: EditText? = null
    var edt_email: EditText? = null
    var firstname_layout: TextInputLayout? = null
    var lastname_layout: TextInputLayout? = null
    var mobile_layout: TextInputLayout? = null
    var email_layout: TextInputLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signuppage_main)
        btn_SignUp = findViewById<View>(R.id.btn_SignUp) as Button
        btn_login = findViewById<View>(R.id.btn_login) as TextView
        edt_firstname = findViewById<View>(R.id.edt_firstname) as EditText
        edt_lastname = findViewById<View>(R.id.edt_lastname) as EditText
        edt_mobile = findViewById<View>(R.id.edit_mobile) as EditText
        edt_email = findViewById<View>(R.id.edt_email) as EditText
        firstname_layout = findViewById<View>(R.id.firstname_layout) as TextInputLayout
        lastname_layout = findViewById<View>(R.id.lastname_layout) as TextInputLayout
        mobile_layout = findViewById<View>(R.id.mobile_layout) as TextInputLayout
        email_layout = findViewById<View>(R.id.email_layout) as TextInputLayout
        btn_SignUp!!.setOnClickListener(this)
        btn_login!!.setOnClickListener(this)
        hideKeyBoard()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_SignUp -> signup()
            R.id.btn_login -> {
                val login_btn = Intent(applicationContext, Login_Page::class.java)
                login_btn.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(login_btn)
            }
        }
    }

    private fun signup() {
        Log.d(TAG, "SignUp_Page")
        val firstName = edt_firstname!!.text.toString().trim { it <= ' ' }
        val lastName = edt_lastname!!.text.toString().trim { it <= ' ' }
        val mobileNumber = edt_mobile!!.text.toString().trim { it <= ' ' }
        val emailId = edt_email!!.text.toString().trim { it <= ' ' }
        if (validate() == false) {
            onSignupFailed()
            return
        }
        //        sendPost(name, email, password, mobile);
        val login_btn = Intent(applicationContext, Login_Page::class.java)
        login_btn.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(login_btn)
        Toast.makeText(applicationContext, "Registration Successfully Completed", Toast.LENGTH_LONG).show()
    }

    private fun onSignupFailed() {
        Toast.makeText(baseContext, "Please Enter all fields", Toast.LENGTH_LONG).show()
        btn_SignUp!!.isEnabled = true
    }

    private fun validate(): Boolean {
        var valid = true
        val firstName = edt_firstname!!.text.toString().trim { it <= ' ' }
        val lastName = edt_lastname!!.text.toString().trim { it <= ' ' }
        val mobileNumber = edt_mobile!!.text.toString().trim { it <= ' ' }
        val emailId = edt_email!!.text.toString().trim { it <= ' ' }
        firstname_layout!!.error = null
        lastname_layout!!.error = null
        mobile_layout!!.error = null
        email_layout!!.error = null
        //        String TakenUsername = api.toString();
        if (firstName.isEmpty() || firstName.length < 3) { //            firstname_layout.setErrorEnabled(true);
            firstname_layout!!.error = "at least 3 characters"
            valid = false
        }
        //        else {
//            firstname_layout.setError(null);
//        }
        if (lastName.isEmpty() || lastName.length < 3) { //            lastname_layout.setErrorEnabled(true);
            lastname_layout!!.error = "at least 3 characters"
            valid = false
        }
        //        else {
//            lastname_layout.setError(null);
//        }
        if (mobileNumber.isEmpty() || mobileNumber.length < 10) { //            mobile_layout.setErrorEnabled(true);
            mobile_layout!!.error = "enter mobile number"
            valid = false
        }
        //        else {
//            mobile_layout.setError(null);
//        }
//        if (emailId.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
////            email_layout.setErrorEnabled(true);
//            email_layout.setError("enter a valid email address");
//
//            valid = false;
//        }
        if (TextUtils.isEmpty(emailId)) {
            email_layout!!.error = "Email is required"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            email_layout!!.error = "Enter a valid email"
            return false
        }
        //        else {
//            email_layout.setError(null);
//        }
        return valid
    }

    companion object {
        private const val TAG = "SignUp_Page"
    }
}