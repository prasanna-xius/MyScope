package com.example.myscope.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.myscope.R
import com.example.myscope.activities.prescription.ServiceBuilder1
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_page_main.*
import kotlinx.android.synthetic.main.login_page_main.mobile_layout
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.lang.System.exit

class Login_Page : BaseActivity(), View.OnClickListener {

    var btn_facebook: TextView? = null
    var btn_google: TextView? = null
    var mobileNumber: String? = null
    var alertdialog: AlertDialog? = null

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
                 mobileNumber = mobile_number.getText().toString().trim { it <= ' ' }


                if (validate() == false) {
                    onSignupFailed()
                    return
                }

//                navigateToActivity(Intent(applicationContext,Otp_Page::class.java))

//                val intent = Intent(this@Login_Page, Otp_Page::class.java)
//                intent.putExtra("Phonenumber", mobileNumber)
//                startActivity(intent)
            }
            R.id.btn_register -> {
                navigateToActivity(Intent(applicationContext, SignUp_Page::class.java))
            }
            R.id.change_mobile_btn -> {

                navigateToActivity(Intent(applicationContext, MobileChange_Activity::class.java))
            }
        }
    }

    // Login API call implementation

    private fun loginApiCall() {
//        val filter = HashMap<String, String>()
        val diseaseService = ServiceBuilder1.buildService(PrescriptionInterface::class.java)
        var call: Call<List<SignupResponse>> = diseaseService.loginPatient(mobileNumber!!)


        (call as  Call<List<SignupResponse>>?)?.enqueue(object : Callback<List<SignupResponse>>{
            override fun onFailure(response:  Call<List<SignupResponse>>, error: Throwable) {
//                navigateToActivity(Intent(applicationContext, Login_Page::class.java))
                showLongToast("Check your internet connection")
                Log.d("Errormessage", error.message + "error")

            }

            override fun onResponse(response: Call<List<SignupResponse>>, resp: retrofit2.Response<List<SignupResponse>>) {
                if (resp.isSuccessful) {
                    var id = resp.body()!!.size
                    if (id.equals(0)) {
                        val alertDialogBuilder = AlertDialog.Builder(this@Login_Page)
                        alertDialogBuilder.setMessage("This number " + mobileNumber + " is not being registered. Do you want to register it?")
                        alertDialogBuilder.setPositiveButton("Yes") { dialogInterface, which ->
                            navigateToActivity(Intent(applicationContext, SignUp_Page::class.java))
                        }
                        alertDialogBuilder.setNegativeButton("No") { dialogInterface, which ->
                        }
                        alertDialogBuilder.show()
                    } else {

                        //Passing value through bundle
                        var intent = Intent(this@Login_Page,Otp_Page::class.java)
                        var bundle:Bundle = Bundle()
                        bundle.putString("mobile_no",mobileNumber)
                        intent.putExtras(bundle)
//                         intent.putExtra("mobile_no",mobileNumber)
                        startActivity(intent)
//                        navigateToActivity(Intent(applicationContext, Otp_Page::class.java))

                    }
                }
            }

        });
    }
        private fun onSignupFailed() {
            btn_otp_send!!.isEnabled = true
        }

        private fun validate(): Boolean {
            var valid = true
            mobileNumber = mobile_number!!.text.toString().trim { it <= ' ' }
            if (mobileNumber!!.isEmpty()) {
                mobile_layout!!.error = "Enter mobile number"
                valid = false
            } else if (mobileNumber!!.length < 10) {
                mobile_layout!!.error = "Enter Valid mobile number"
                valid = false
            } else {
                mobile_layout!!.error = null
                valid = true
                loginApiCall()
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
