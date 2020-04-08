package com.example.myscope.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.myscope.R
import com.example.myscope.activities.medical_history.Diseases
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import com.google.android.gms.security.ProviderInstaller
import kotlinx.android.synthetic.main.activity_contact__us.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.family_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.net.ssl.SSLContext

class Contact_Us : BaseActivity() {

    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
   // var client = shruthi.budarapu@gmail.com


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact__us)

        activitiesToolbar()

        header!!.text = "Family History"


        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
        showLongToast(mobile_no.toString())




        btn_contact_us.setOnClickListener {

            assignValuestoVariable()


        }

    }

    private fun assignValuestoVariable() {
        val name1 = name.text.toString().trim()
        val email = email.text.toString().trim()
        val subject1 = subject.text.toString().trim()

        val message1 = message.text.toString().trim()

        validateInput(name, name1)
        validateInput(message, message1)
        validateInput(subject, subject1)


        if ((name1 != "") && (message1 != "") && (subject1 != ""))
        {

            showLongToast("save the details")


            apicall(email , subject1 , message1)
        }

    }

    private fun apicall(email: String, subject1 : String , message: String) {


        val mIntent = Intent(Intent.ACTION_SEND)

          mIntent.data = Uri.parse("mailto:")

        mIntent.type = "Text/Plain"

        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email) )

        mIntent.putExtra(Intent.EXTRA_SUBJECT , subject1)

        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {

            startActivity(Intent.createChooser(mIntent , "shruthi.buddarapu@gmail.com"))
        }

        catch(e : Exception) {
            Toast.makeText(this , e.message , Toast.LENGTH_LONG ).show()
        }
    }

}
