package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myscope.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.mobile_change_main.*

class MobileChange_Activity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mobile_change_main)
        btn_send_link!!.setOnClickListener(this)
        hideKeyBoard()
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_send_link -> {
                if (validate() == false) {
                    onSignupFailed()
                    return
                }
                showLongToast("Please check your Email and change Mobile Number")
                navigateToActivity(Intent(applicationContext,Login_Page::class.java))
            }
        }
    }
    private fun onSignupFailed() {
        btn_send_link!!.isEnabled = true
    }
    private fun validate(): Boolean {
        var valid = true
        val emailId = edt_register_email!!.text.toString()
        if (emailId.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            mobile_change_layout!!.error = "Enter Register Email Address"
            valid = false
        } else {
            mobile_change_layout!!.error = null
        }
        return valid
    }
}