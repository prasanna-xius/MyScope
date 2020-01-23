package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myscope.R
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
                navigateToActivity(Intent(applicationContext,Otp_Page::class.java))
            }
            R.id.btn_register -> {
                navigateToActivity(Intent(applicationContext,SignUp_Page::class.java))
            }
            R.id.change_mobile_btn -> {

                navigateToActivity(Intent(applicationContext,MobileChange_Activity::class.java))
            }
        }
    }
    private fun onSignupFailed() {
        showLongToast("Please Enter all fields")
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
        return valid
    }
}