package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.myscope.R
import kotlinx.android.synthetic.main.otppage_main.*

class Otp_Page : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otppage_main)

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

                navigateToActivity(Intent(applicationContext,Navigation_Drawer_Blogs::class.java))
                showLongToast("Login Successfully Completed")
            }
        }
    }

    private fun onSignupFailed() {
        btn_verify_otp!!.isEnabled = true
    }

    private fun validate(): Boolean {
        var valid = true
        val otpNumber = edt_otp!!.text.toString()
        if (otpNumber.isEmpty()) {
            otp_layout!!.error = "Enter OTP number"
            valid = false
        }
        else if (otpNumber.length<6){
            otp_layout!!.error = "Please Enter Valid OTP"
            valid = false
        }else {
            otp_layout!!.error = null
        }
        return valid
    }
}