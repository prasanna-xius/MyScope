package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myscope.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_page_main.*
import kotlinx.android.synthetic.main.login_page_main.mobile_layout
import kotlinx.android.synthetic.main.signuppage_main.*

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
                val Phonenumber = mobile_number.getText().toString().trim { it <= ' ' }

                if (validate() == false) {
                    onSignupFailed()
                    return
                }
//                navigateToActivity(Intent(applicationContext,Otp_Page::class.java))

                val intent = Intent(this@Login_Page, Otp_Page::class.java)
                intent.putExtra("Phonenumber", Phonenumber)
                startActivity(intent)

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
        btn_otp_send!!.isEnabled = true
    }
    private fun validate(): Boolean {
        var valid = true
        val mobileNumber = mobile_number!!.text.toString().trim { it <= ' ' }
        if (mobileNumber.isEmpty()) {
            mobile_layout!!.error = "Enter mobile number"
            valid = false
        }
        else if (mobileNumber.length < 10) {
            mobile_layout!!.error = "Enter Valid mobile number"
            mobile_number.requestFocus()

            valid = false
        } else {
            mobile_layout!!.error = null
        }
        return valid
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) { //verification successful we will start the profile activity
            val intent = Intent(this@Login_Page, Navigation_Drawer_Blogs::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}