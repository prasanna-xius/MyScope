package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myscope.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.login_page_main.*

class Login_Page : BaseActivity(), View.OnClickListener {
    var btn_facebook: TextView? = null
    var btn_google: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page_main)

        btn_otp_send!!.setOnClickListener(this)
        change_mobile_btn!!.setOnClickListener(this)
        btn_register!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_otp_send -> {
                if (validate() == false) {
                    onSignupFailed()
                    return
                }
                val btn_otp = Intent(applicationContext, Otp_Page::class.java)
                btn_otp.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(btn_otp)
            }
            R.id.btn_register -> {
                val btn_register = Intent(applicationContext, SignUp_Page::class.java)
                btn_register.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(btn_register)
            }
            R.id.change_mobile_btn -> {
                val change_mobile_btn = Intent(applicationContext, MobileChange_Activity::class.java)
                change_mobile_btn.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(change_mobile_btn)
            }
        }
    }

    private fun onSignupFailed() {
        Toast.makeText(baseContext, "Please Enter all fields", Toast.LENGTH_LONG).show()
        btn_otp_send!!.isEnabled = true
    }

    private fun validate(): Boolean {
        var valid = true
        val mobileNumber = mobile_number!!.text.toString().trim { it <= ' ' }
        if (mobileNumber.isEmpty() || mobileNumber.length < 10) {
            mobile_layout!!.error = "please enter mobile number"
            valid = false
        } else {
            mobile_layout!!.error = null
        }
        //        String MobilePattern = "[0-9]{10}";
//
//        if (mobileNumber.length() ==10 && mobileNumber.matches(MobilePattern) && mobileNumber.startsWith("9") || mobileNumber.startsWith("8") || mobileNumber.startsWith("7") || mobileNumber.startsWith("6") ) {
//            edtMobile.setError("enter mobile number");
//            valid = false;
//        } else {
//            edtMobile.setError(null);
//        }
        return valid
    }
}