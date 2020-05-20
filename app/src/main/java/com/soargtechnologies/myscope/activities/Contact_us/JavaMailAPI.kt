package com.soargtechnologies.myscope.activities.Contact_us

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast

import java.util.Properties

import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class JavaMailAPI(private val mContext: Context, private val mEmail: String, private val mSubject: String, private val mMessage: String) : AsyncTask<Void, Void, Void>() {
    private var mSession: Session? = null

    private var mProgressDialog: ProgressDialog? = null

    override fun onPreExecute() {
        super.onPreExecute()
        //Show progress dialog while sending email
        mProgressDialog = ProgressDialog.show(mContext, "Sending message", "Please wait...", false, false)
    }

    override fun onPostExecute( void: Void?) {
        super.onPostExecute(void)
        //Dismiss progress dialog when message successfully send
        mProgressDialog!!.dismiss()

        //Show success toast
        Toast.makeText(mContext, "Mail Sent", Toast.LENGTH_SHORT).show()
    }




    override fun doInBackground(vararg params: Void): Void? {
        //Creating properties
        val props = Properties()

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"
        props["mail.socketFactory.fallback"] = "false"

        //Creating a new session
        mSession = Session.getDefaultInstance(props,
                object : javax.mail.Authenticator() {
                    //Authenticating the password
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD)
                    }
                })

        try {
            //Creating MimeMessage object
            val mm = MimeMessage(mSession)

            //Setting sender address
            mm.setFrom(InternetAddress(Utils.EMAIL))
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, InternetAddress(mEmail))
            //Adding subject
            mm.subject = mSubject
            //Adding message
            mm.setText(mMessage)
            //Sending email
            Transport.send(mm)


        } catch (e: MessagingException) {
            e.printStackTrace()
        }

        return null
    }
}
