package com.soargtechnologies.myscope.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.soargtechnologies.myscope.R
import kotlinx.android.synthetic.main.activity_pdfopenfile.*

class PDFopenfile_Edu :AppCompatActivity(){

    lateinit var sharedpreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfopenfile)
        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        var format = sharedpreferences.getString("buffer",null)
//        format.encodeToByteArray()

//        val abc = format!!.encodeToByteArray()
//        var pureBase64Encoded = format!!.substring(format.indexOf(",") + 1);

        var decodedBytes = Base64.decode(format, Base64.DEFAULT)

//
//
//        val s: String = String(abc);
////        var uri: Uri = Uri.parse(s)
//        var uri1: Uri = Uri.parse(s)
//        pdf.fromUri(uri1).load()
        pdf.fromBytes(decodedBytes).load()
        pdfclose.setOnClickListener(View.OnClickListener {

            finish()
            //     val intent = Intent(this,Prescription_AddImage_PDF::class.java)
            //     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            //     startActivity(intent)
        })
    }

}
