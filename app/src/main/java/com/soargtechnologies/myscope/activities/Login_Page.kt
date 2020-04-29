package com.soargtechnologies.myscope.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.medical_documents.Medical_Documents_HomePage
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import com.soargtechnologies.myscope.services.PrescriptionInterface
import kotlinx.android.synthetic.main.login_page_main.*
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import java.util.*


@Suppress("DEPRECATION")
class Login_Page : AppCompatActivity(), View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
//    var loginButton: TextView? = null

    private var signInButton: SignInButton? = null
    private var googleApiClient: GoogleApiClient? = null
    var first_name: String? = null
    var last_name: String? = null
    var email: String? = null
    var idToken: String? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null

    var mobileNumber: String? = null
    var alertdialog: AlertDialog? = null

//    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page_main)

        btn_otp_send!!.setOnClickListener(this)
        change_mobile_btn!!.setOnClickListener(this)
        btn_register!!.setOnClickListener(this)
        btn_facebook!!.setOnClickListener(this)

        firebaseAuth = FirebaseAuth.getInstance()
        //this is where we start the Auth state Listener to listen for whether the user is signed in or not
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            // Get signedIn user
            val user = firebaseAuth.currentUser
            //if user is signed in, we call a helper method to save the user details to Firebase
            if (user != null) { // User is signed in
// you could place other firebase code
//logic to save the user details to Firebase
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.uid)
            } else { // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out")
            }
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id)) //you can also use R.string.default_web_client_id
                .requestEmail()
                .build()
        googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        signInButton = findViewById(R.id.btn_google)
        signInButton!!.setOnClickListener(View.OnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(intent, RC_SIGN_IN)
        })
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_otp_send -> {
                val Phonenumber = mobile_number.getText().toString().trim { it <= ' ' }


                if (validate() == false) {
                    onSignupFailed()
                    return
                }
                loginApiCall()
//                navigateToActivity(Intent(applicationContext,Otp_Page::class.java))


            }
            R.id.btn_register -> {
//                navigateToActivity(Intent(applicationContext, SignUp_Page::class.java))
                val intent = Intent(applicationContext, SignUp_Page::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)

            }
            R.id.change_mobile_btn -> {

//                navigateToActivity(Intent(applicationContext, MobileChange_Activity::class.java))
                val intent = Intent(applicationContext, MobileChange_Activity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)

            }
            R.id.btn_facebook -> {

                val intent = Intent(applicationContext, Facebook_Activity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)

//                navigateToActivity(Intent(applicationContext,Facebook_Activity::class.java))
            }
        }
    }

    // Login API call implementation

    private fun loginApiCall() {
//        val filter = HashMap<String, String>()
        val diseaseService = ServiceBuilder.buildService(PrescriptionInterface::class.java)

//        var builder = retrofit.create(PrescriptionInterface::class.java)
        var call = diseaseService.loginPatient(mobileNumber!!)


        (call as Call<List<SignupResponse>>?)?.enqueue(object : Callback<List<SignupResponse>> {
            override fun onFailure(response: Call<List<SignupResponse>>, error: Throwable) {
//                navigateToActivity(Intent(applicationContext, Login_Page::class.java))
//                showLongToast("Check your internet connection")
                Toast.makeText(applicationContext, "Check your internet connection", Toast.LENGTH_LONG).show()
                Log.d("Errormessage", error.message + "error")

            }

            override fun onResponse(response: Call<List<SignupResponse>>, resp: retrofit2.Response<List<SignupResponse>>) {
                if (resp.isSuccessful && resp.body() != null) {
                    var id = resp.body()!!.size
                    if (id.equals(0)) {
                        val alertDialogBuilder = AlertDialog.Builder(this@Login_Page)
                        alertDialogBuilder.setMessage("This number " + mobileNumber + " is not being registered. Do you want to register it?")
                        alertDialogBuilder.setPositiveButton("Yes") { dialogInterface, which ->
                            //                            navigateToActivity(Intent(applicationContext, SignUp_Page::class.java))
                            val intent = Intent(applicationContext, SignUp_Page::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                        }
                        alertDialogBuilder.setNegativeButton("No") { dialogInterface, which ->
                        }
                        alertDialogBuilder.show()
                    } else {
                        val pref = applicationContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE) // 0 - for private mode
                        val editor: SharedPreferences.Editor = pref.edit()
                        editor.putString("mobile_no", mobileNumber)
                        editor.commit()
                        //Passing value through bundle
                        var intent = Intent(this@Login_Page, Navigation_Drawer_Blogs::class.java)
//                        var bundle: Bundle = Bundle()
//                        bundle.putString("mobile_no", mobileNumber)
//                        intent.putExtras(bundle)
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
        }
        return valid


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result!!)
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount
            idToken = account!!.idToken
            first_name = account.givenName
            last_name = account.familyName
            email = account.email

            // you can store user data to SharedPreference
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuthWithGoogle(credential)
        } else { // Google Sign In failed, update UI appropriately
//            Log.e(TAG, "Login Unsuccessful. $result")
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(credential: AuthCredential) {
        firebaseAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful)
                    if (task.isSuccessful) {
                        Toast.makeText(this@Login_Page, "Login successful", Toast.LENGTH_SHORT).show()
                        gotoProfile()
                    } else {
                        Log.w(TAG, "signInWithCredential" + task.exception!!.message)
                        task.exception!!.printStackTrace()
                        Toast.makeText(this@Login_Page, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }


    private fun gotoProfile() {
        val intent = Intent(this@Login_Page, Register_User::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) { //verification successful we will start the profile activity
            val intent = Intent(this@Login_Page, Navigation_Drawer_Blogs::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        if (authStateListener != null) {
            FirebaseAuth.getInstance().signOut()
        }
        firebaseAuth!!.addAuthStateListener(authStateListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (authStateListener != null) {
            firebaseAuth!!.removeAuthStateListener(authStateListener!!)
        }
    }

    companion object {
        private const val TAG = "Login_Page"
        private const val RC_SIGN_IN = 1
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
    }
}
