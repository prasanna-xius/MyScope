package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myscope.R
import com.google.android.material.textfield.TextInputLayout

class Otp_Page : BaseActivity(), View.OnClickListener {
    var btn_verify_otp: Button? = null
    var edtOtp: EditText? = null
    var otp_layout: TextInputLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otppage_main)
        btn_verify_otp = findViewById<View>(R.id.btn_verify_otp) as Button
        edtOtp = findViewById<View>(R.id.edt_otp) as EditText
        otp_layout = findViewById<View>(R.id.otp_layout) as TextInputLayout
        btn_verify_otp!!.setOnClickListener(this)
        hideKeyBoard()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_verify_otp -> {
                if (validate() == false) {
                    onSignupFailed()
                    return
                }
                val btn_otp = Intent(applicationContext, Navigation_Drawer_Blogs::class.java)
                btn_otp.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(btn_otp)
                showShortToast("Login Successfully Completed")
            }
        }
    }

    private fun onSignupFailed() {
        Toast.makeText(baseContext, "Please Enter valid OTP", Toast.LENGTH_LONG).show()
        btn_verify_otp!!.isEnabled = true
    }

    private fun validate(): Boolean {
        var valid = true
        val otpNumber = edtOtp!!.text.toString()
        if (otpNumber.isEmpty() || otpNumber.length < 6) {
            otp_layout!!.error = "enter OTP number"
            valid = false
        } else {
            otp_layout!!.error = null
        }
        return valid
    }
}