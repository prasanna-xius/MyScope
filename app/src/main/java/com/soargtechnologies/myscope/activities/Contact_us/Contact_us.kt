package com.soargtechnologies.myscope.activities.Contact_us

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_contact.*

import kotlinx.android.synthetic.main.activity_contact_us.*
import kotlinx.android.synthetic.main.activity_contact_us.toolbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_contact_us.*
import kotlinx.android.synthetic.main.content_contact_us.messageId
import kotlinx.android.synthetic.main.content_contact_us.nameId
import kotlinx.android.synthetic.main.content_contact_us.subjectId
import kotlinx.android.synthetic.main.signuppage_main.*

class Contact_us : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
//        setSupportActionBar(toolbar)

        activitiesToolbar()
        header!!.text = "Contact Us"

        btn_contact_us.setOnClickListener { view ->
    //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            sendMail()
        }


    }

    private fun sendMail() {
        val mail = "sanksharma7@gmail.com"

        val subject = subject.text.toString().trim()
        val message = message.text.toString().trim ()  + "\n"  +   name.text.toString().trim()

//        + "from" +  edt_email!!.text.toString().trim()

//        edt_email!!.text.toString().trim()

        val javaMailAPI = JavaMailAPI(this, mail, subject, message)

        javaMailAPI.execute()
        //
          }

}
