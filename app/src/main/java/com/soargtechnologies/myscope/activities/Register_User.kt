package com.soargtechnologies.myscope.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.TaskExecutors
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.Login_Page
import com.soargtechnologies.myscope.services.PrescriptionInterface
import kotlinx.android.synthetic.main.login_page_main.*
import kotlinx.android.synthetic.main.otppage_main.*
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class Register_User : BaseActivity(), GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    var userFirstName: TextView? = null
    var userLastName: TextView? = null
    var userEmail: TextView? = null

    var firstName: String = "";
    var lastName: String = "";
    var mobileNumber: String = "";
    var emailId: String = "";

    private var registerCall: Call<SignupResponse>? = null
    private var request: PrescriptionInterface? = null

    private var mVerificationId: String? = null
    //firebase auth object
    private var mAuth: FirebaseAuth? = null
    var progressBar: ProgressBar? = null


    var userId: TextView? = null
    var btn_otp_send: Button? = null
    private var googleApiClient: GoogleApiClient? = null
    private var gso: GoogleSignInOptions? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_register_main)

        userFirstName = findViewById(R.id.first_name)
        userLastName = findViewById(R.id.last_name)
        userEmail = findViewById(R.id.email)
        userId = findViewById(R.id.userId)

        progressBar = findViewById(R.id.progressBar)
        //initializing objects
        mAuth = FirebaseAuth.getInstance()

        val mobileNumber = mobile_number.getText().toString().trim { it <= ' ' }
//        val intent = intent
//        val mobileNumber = intent.getStringExtra("mobile_no")
        sendVerificationCode(mobileNumber)

        btn_otp_send = findViewById(R.id.btn_otp_send)
        btn_otp_send!!.setOnClickListener(this)
        btn_verify_otp!!.setOnClickListener(this)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso!!)
                .build()
        btn_verify_otp.setVisibility(View.GONE);
        otp_layout.setVisibility(View.GONE);
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) { //verification successful we will start the profile activity
//            verificationOtp()
//            navigateToActivity(Intent(applicationContext, Navigation_Drawer_Blogs::class.java))

        }
        val opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient)
        if (opr.isDone) {
            val result = opr.get()
            handleSignInResult(result)
        } else {
            opr.setResultCallback { googleSignInResult -> handleSignInResult(googleSignInResult) }
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount
            userFirstName!!.text = account!!.givenName
            userLastName!!.text = account.familyName
            userEmail!!.text = account.email
            userId!!.text = account.id
            //            try{
//                Glide.with(this).load(account.getPhotoUrl()).into(profileImage);
//            }catch (NullPointerException e){
//                Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
//            }
        } else {
            gotoMainActivity()
        }
    }

    private fun gotoMainActivity() {
        val intent = Intent(this, Login_Page::class.java)
        startActivity(intent)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_otp_send -> {

                val Phonenumber = mobile_number.getText().toString().trim { it <= ' ' }

                if (validate() == false) {

//                    registerApiCall(firstName, lastName, mobileNumber, emailId);

                    return
                }

                var bundle: Bundle = Bundle()
                        bundle.putString("mobile_no", mobileNumber)
                        intent.putExtras(bundle)
                         intent.putExtra("mobile_no",mobileNumber)

                navigateToActivity(Intent(applicationContext,Navigation_Drawer_Blogs::class.java))
                btn_verify_otp.setVisibility(View.VISIBLE);
                otp_layout.setVisibility(View.VISIBLE);


            }
            R.id.btn_verify_otp -> {
                val code = edt_otp!!.text.toString().trim()

                if (validate1() == false) {
                    return
                }
                verifyVerificationCode(code)
//                registerApiCall(firstName, lastName, mobileNumber, emailId);
//                navigateToActivity(Intent(applicationContext,Navigation_Drawer_Blogs::class.java))
            }
        }
    }
    private fun validate1(): Boolean {
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
            Toast.makeText(this@Register_User, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun verifyVerificationCode(otpNumber: String) {

        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, otpNumber)
        //signing the user
        signInWithPhoneAuthCredential(credential)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this@Register_User) { task ->
                    if (task.isSuccessful) { //verification successful we will start the profile activity
                        val intent = Intent(this@Register_User, Navigation_Drawer_Blogs::class.java)
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
        }
        return valid


    }

    private fun registerApiCall(firstname: String, lastname: String, mobileno: String, email: String) {

        var login = SignupResponse(firstname, lastname, mobileno, email)
        request = APIClient.getClient!!.create(PrescriptionInterface::class.java)

        try {
            var resp: SignupResponse = login

            registerCall = request?.createPatient(resp)

            (registerCall as Call<SignupResponse>).enqueue(object : Callback<SignupResponse> {
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    showLongToast("Recheck and save again")
                    Log.d("Errormessage", t.message + "error")
                }

                override fun onResponse(call: Call<SignupResponse>, resp: Response<SignupResponse>) {
                    if (resp.isSuccessful && !resp.equals("")) {
//                        showShortToast("Api call is working")
                        navigateToActivity(Intent(applicationContext, Navigation_Drawer_Blogs::class.java))
                    }
                }
            });
        } catch (e: JSONException) {
            e.printStackTrace();
        }


    }
}