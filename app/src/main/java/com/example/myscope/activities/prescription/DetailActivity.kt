package com.example.myscope.activities.prescription

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myscope.R

class DetailActivity : AppCompatActivity() {

    var img: ImageView? = null

    var nameTxt: EditText? = null
    var posTxt: EditText? = null

    var updateBtn: Button? = null
    var deleteBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.isprescribed_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val i = intent
        val prescription_id = i.extras!!.getInt("prescription_id")

        nameTxt = findViewById<View>(R.id.doctor_name1) as EditText
        posTxt = findViewById<View>(R.id.hosp_name1) as EditText


    }

}