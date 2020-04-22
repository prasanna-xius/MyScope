package com.soargtechnologies.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.soargtechnologies.myscope.R
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONException
import java.util.*

class Facebook_Activity : BaseActivity() {
    private var loginButton: LoginButton? = null
    private var circleImageView: CircleImageView? = null
    private var txtemail: TextView? = null
    private var txtname: TextView? = null
    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.facebook_main)
        loginButton = findViewById<View>(R.id.login_button) as LoginButton
        txtemail = findViewById<View>(R.id.email_fb) as TextView
        txtname = findViewById<View>(R.id.name_fb) as TextView
        circleImageView = findViewById<View>(R.id.fb_profile) as CircleImageView
        callbackManager = CallbackManager.Factory.create()


        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
//        loginButton!!.setReadPermissions(Arrays.asList("email", "public_profile"))
        checkingLoginStatus()
        loginButton!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                navigateToActivity(Intent(applicationContext,Navigation_Drawer_Blogs::class.java))

            }
            override fun onCancel() {}
            override fun onError(error: FacebookException) {}
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    var tokenTracker: AccessTokenTracker = object : AccessTokenTracker() {
        override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken, currentAccessToken: AccessToken) {
            if (currentAccessToken == null) {
//                txtname!!.text = ""
//                txtemail!!.text = ""
//                circleImageView!!.setImageResource(0)
//                Toast.makeText(this@Facebook_Activity, "User Loout", Toast.LENGTH_SHORT).show()
                navigateToActivity(Intent(applicationContext,Navigation_Drawer_Blogs::class.java))


            } else {
                loaduserprofile(currentAccessToken)
            }
        }
    }

    private fun loaduserprofile(newAccesToken: AccessToken) {
        val request = GraphRequest.newMeRequest(newAccesToken) { `object`, response ->
            try {
                val first_name = `object`.getString("first_name")
                val last_name = `object`.getString("last_name")
                val email = `object`.getString("email")
                val id = `object`.getString("id")
                val image_url = "https://graph.facebook.com/$id/picture?type=normal"
                txtemail!!.text = email
                txtname!!.text = "$first_name $last_name"
//                val requestOptions = RequestOptions()
//                requestOptions.doAnimate()
                Glide.with(this@Facebook_Activity).load(image_url).into(circleImageView)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val parameters = Bundle()
        parameters.putString("fileds", "first_name,last_name,email,id")
        request.parameters = parameters
        request.executeAsync()
    }
    private fun checkingLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loaduserprofile(AccessToken.getCurrentAccessToken())
        }
    }

}