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
                val btn_mobile_change = Intent(applicationContext, Login_Page::class.java)
                btn_mobile_change.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(btn_mobile_change)
                Toast.makeText(applicationContext, "Please check your Email and change Mobile Number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onSignupFailed() {
        Toast.makeText(baseContext, "Please Enter Valid EmailId", Toast.LENGTH_LONG).show()
        btn_send_link!!.isEnabled = true
    }

    private fun validate(): Boolean {
        var valid = true
        val emailId = edt_register_email!!.text.toString()
        if (emailId.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            mobile_change_layout!!.error = "enter a valid email address"
            valid = false
        } else {
            mobile_change_layout!!.error = null
        }
        return valid
    }
}