package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.myscope.R
import com.google.android.gms.tasks.TaskExecutors
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.otppage_main.*
import java.util.concurrent.TimeUnit

class Otp_Page : BaseActivity(), View.OnClickListener {

    private var mVerificationId: String? = null
    //firebase auth object
    private var mAuth: FirebaseAuth? = null
    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otppage_main)

        progressBar = findViewById(R.id.progressBar)
        //initializing objects
        mAuth = FirebaseAuth.getInstance()

        val intent = intent
        val Phonenumber = intent.getStringExtra("Phonenumber")
        sendVerificationCode(Phonenumber)

        btn_verify_otp!!.setOnClickListener(this)
        hideKeyBoard()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_verify_otp -> {
                val code = edt_otp!!.text.toString().trim()

                if (validate() == false) {
                    return
                }
                verifyVerificationCode(code)

//                navigateToActivity(Intent(applicationContext,Navigation_Drawer_Blogs::class.java))
            }
        }
    }
    private fun sendVerificationCode(Phonenumber: String) {
        progressBar!!.visibility = View.VISIBLE
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91$Phonenumber",
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        )
    }
    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(s, forceResendingToken)
            //storing the verification id that is sent to the user
            mVerificationId = s
        }

        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) { //Getting the code sent by SMS
            val code = phoneAuthCredential.smsCode
            //sometime the code is not detected automatically
//in this case the code will be null
//so user has to manually enter the code
            if (code != null) {
                edt_otp!!.setText(code)
                //verifying the code
                verifyVerificationCode(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@Otp_Page, e.message, Toast.LENGTH_LONG).show()
        }
    }



    private fun validate(): Boolean {
        var valid = true
        val code = edt_otp!!.text.toString().trim()
        if (code.isEmpty()) {
            otp_layout!!.error = "Enter OTP number"
            valid = false
        }
        else if (code.length<6){
            otp_layout!!.error = "Please Enter Valid OTP"
            edt_otp.requestFocus()
            valid = false
        }else {
            otp_layout!!.error = null
        }
        return valid
    }
    private fun verifyVerificationCode(otpNumber: String) { //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, otpNumber)
        //signing the user
        signInWithPhoneAuthCredential(credential)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this@Otp_Page) { task ->
                    if (task.isSuccessful) { //verification successful we will start the profile activity
                        val intent = Intent(this@Otp_Page, Navigation_Drawer_Blogs::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    } else { //verification unsuccessful.. display an error message
                        var message = "Somthing is wrong, we will fix it soon..."
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered..."
                        }
                        val snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG)
                        snackbar.setAction("Dismiss") { }
                        snackbar.show()
                    }
                }
    }
}