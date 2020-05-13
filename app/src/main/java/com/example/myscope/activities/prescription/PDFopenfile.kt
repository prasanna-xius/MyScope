package com.example.myscope.activities.prescription

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.example.myscope.R
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.android.synthetic.main.activity_pdfopenfile.*
import kotlinx.android.synthetic.main.custom_dialog.*

class PDFopenfile : AppCompatActivity() {
    lateinit var sharedpreferences:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfopenfile)
        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        var format = sharedpreferences.getString("buffer",null)

        var uri1: Uri = Uri.parse(format)
        pdf.fromUri(uri1)
                .defaultPage(0)
                .spacing(10)
                .load()
    }
}
